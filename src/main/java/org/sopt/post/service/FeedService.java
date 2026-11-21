package org.sopt.post.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.clothes.entity.Clothes;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.post.dto.response.FeedItemResponse;
import org.sopt.post.dto.response.FeedResponse;
import org.sopt.post.dto.response.FeedUserResponse;
import org.sopt.post.dto.response.PurchaseLinkResponse;
import org.sopt.post.entity.FeedPeriod;
import org.sopt.post.entity.FeedSort;
import org.sopt.post.entity.Post;
import org.sopt.post.entity.PostClothes;
import org.sopt.post.repository.CommentRepository;
import org.sopt.post.repository.FeedCursor;
import org.sopt.post.repository.LikeRepository;
import org.sopt.post.repository.PostClothesRepository;
import org.sopt.post.repository.PostCountProjection;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {
	private static final int MAX_LIMIT = 50;
	private static final ZoneId RESPONSE_ZONE = ZoneId.of("Asia/Seoul");

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostClothesRepository postClothesRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;
	private final ImageStorageService imageStorageService;

	@Transactional(readOnly = true)
	public FeedResponse getFeed(
		Long viewerId,
		FeedSort sort,
		FeedPeriod period,
		int limit,
		String rawCursor
	) {
		if (limit < 1 || limit > MAX_LIMIT) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		User viewer = userRepository.findById(viewerId)
			.orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
		FeedCursor cursor = rawCursor == null ? null : FeedCursor.decode(rawCursor, sort);
		LocalDateTime popularSince = sort == FeedSort.POPULAR ? LocalDateTime.now().minusDays((period == null ? FeedPeriod.WEEK : period).getDays()) : null;

		// 한 건 더 조회해 다음 페이지 존재 여부를 확인한다.
		List<Post> result = postRepository.findVisiblePosts(
			viewerId, viewer.getDepartment(), sort, popularSince, cursor, limit + 1);
		boolean hasMore = result.size() > limit;
		List<Post> posts = hasMore ? result.subList(0, limit) : result;

		if (posts.isEmpty()) {
			return new FeedResponse(List.of(), null, false);
		}

		List<Long> postIds = posts.stream().map(Post::getId).toList();
		Map<Long, Long> likeCounts = countMap(likeRepository.countLikesByPostIds(postIds));
		Map<Long, Long> commentCounts = countMap(commentRepository.countCommentsByPostIds(postIds));
		Set<Long> likedPostIds = new HashSet<>(likeRepository.findLikedPostIds(viewerId, postIds));
		Map<Long, List<PurchaseLinkResponse>> purchaseLinks = loadPurchaseLinks(postIds);

		List<FeedItemResponse> items = posts.stream()
			.map(post -> toFeedItem(post, likeCounts, commentCounts, likedPostIds, purchaseLinks))
			.toList();

		String nextCursor = null;
		if (hasMore) {
			Post lastPost = posts.get(posts.size() - 1);
			nextCursor = FeedCursor.encode(
				sort,
				likeCounts.getOrDefault(lastPost.getId(), 0L),
				lastPost.getCreatedAt(),
				lastPost.getId()
			);
		}
		return new FeedResponse(items, nextCursor, hasMore);
	}

	private Map<Long, Long> countMap(List<PostCountProjection> projections) {
		return projections.stream().collect(Collectors.toMap(
			PostCountProjection::getPostId,
			PostCountProjection::getTotalCount
		));
	}

	private Map<Long, List<PurchaseLinkResponse>> loadPurchaseLinks(List<Long> postIds) {
		Map<Long, List<PurchaseLinkResponse>> linksByPost = new HashMap<>();
		for (PostClothes postClothes : postClothesRepository.findAllByPost_IdIn(postIds)) {
			Clothes clothes = postClothes.getClothes();
			linksByPost.computeIfAbsent(postClothes.getPost().getId(), ignored -> new ArrayList<>())
				.add(new PurchaseLinkResponse(
					clothes.getId(),
					clothes.getName(),
					clothes.getCategory().getLabel(),
					imageStorageService.getImageUrl(clothes.getImageKey()),
					clothes.getPurchaseUrl()
				));
		}
		return linksByPost;
	}

	private FeedItemResponse toFeedItem(
		Post post,
		Map<Long, Long> likeCounts,
		Map<Long, Long> commentCounts,
		Set<Long> likedPostIds,
		Map<Long, List<PurchaseLinkResponse>> purchaseLinks
	) {
		return new FeedItemResponse(
			post.getId(),
			toFeedUser(post.getAuthor()),
			toFeedUser(post.getTargetUser()),
			imageStorageService.getImageUrl(post.getImageKey()),
			post.getTitle(),
			post.getDescription(),
			purchaseLinks.getOrDefault(post.getId(), List.of()),
			Math.toIntExact(likeCounts.getOrDefault(post.getId(), 0L)),
			likedPostIds.contains(post.getId()),
			Math.toIntExact(commentCounts.getOrDefault(post.getId(), 0L)),
			post.getCreatedAt().atZone(RESPONSE_ZONE).toOffsetDateTime()
		);
	}

	private FeedUserResponse toFeedUser(User user) {
		return new FeedUserResponse(user.getId(), user.getName(), user.getPosition().getLabel());
	}
}
