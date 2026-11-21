package org.sopt.post.service;

import java.time.ZoneId;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.sopt.clothes.entity.Clothes;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.post.dto.response.CommentPreviewResponse;
import org.sopt.post.dto.response.FeedUserResponse;
import org.sopt.post.dto.response.OutfitItemResponse;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.dto.response.PostPurchaseLinkResponse;
import org.sopt.post.entity.Comment;
import org.sopt.post.entity.Post;
import org.sopt.post.entity.PostClothes;
import org.sopt.post.entity.PostVisibility;
import org.sopt.post.repository.CommentRepository;
import org.sopt.post.repository.LikeRepository;
import org.sopt.post.repository.PostClothesRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDetailService {
	private static final int COMMENTS_PREVIEW_LIMIT = 3;
	private static final ZoneId RESPONSE_ZONE = ZoneId.of("Asia/Seoul");

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostClothesRepository postClothesRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;
	private final ImageStorageService imageStorageService;

	@Transactional(readOnly = true)
	public PostDetailResponse getPostDetail(Long viewerId, Long postId) {
		User viewer = userRepository.findById(viewerId)
			.orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));

		// 공개 범위에 접근할 수 없는 게시글은 존재하지 않는 게시글과 같은 응답으로 처리함
		if (!isVisibleTo(post, viewer)) {
			throw new BaseException(ErrorCode.POST_NOT_FOUND);
		}

		List<PostClothes> postClothes = postClothesRepository.findAllByPost_IdOrderByIdAsc(postId);
		List<OutfitItemResponse> outfitItems = postClothes.stream()
			.map(PostClothes::getClothes)
			.map(this::toOutfitItem)
			.toList();
		List<PostPurchaseLinkResponse> purchaseLinks = outfitItems.stream()
			.filter(item -> item.purchaseUrl() != null && !item.purchaseUrl().isBlank())
			.map(item -> new PostPurchaseLinkResponse(item.purchaseUrl()))
			.toList();

		List<Comment> recentComments = commentRepository
			.findTop3ByPost_IdOrderByCreatedAtDescIdDesc(postId);
		List<CommentPreviewResponse> commentsPreview = recentComments.stream()
			.limit(COMMENTS_PREVIEW_LIMIT)
			.map(this::toCommentPreview)
			.toList();

		return new PostDetailResponse(
			post.getId(),
			toFeedUser(post.getAuthor()),
			toFeedUser(post.getTargetUser()),
			imageStorageService.getImageUrl(post.getImageKey()),
			post.getTitle(),
			post.getDescription(),
			outfitItems,
			purchaseLinks,
			Math.toIntExact(likeRepository.countByPostId(postId)),
			likeRepository.existsByUser_IdAndPost_Id(viewerId, postId),
			Math.toIntExact(commentRepository.countByPost_Id(postId)),
			commentsPreview,
			post.getCreatedAt().atZone(RESPONSE_ZONE).toOffsetDateTime()
		);
	}

	private boolean isVisibleTo(Post post, User viewer) {
		return switch (post.getVisibility()) {
			case PUBLIC -> true;
			case DEPARTMENT -> post.getAuthor().getDepartment() == viewer.getDepartment();
			case PRIVATE -> post.getTargetUser().getId().equals(viewer.getId());
		};
	}

	private OutfitItemResponse toOutfitItem(Clothes clothes) {
		return new OutfitItemResponse(
			clothes.getId(),
			clothes.getName(),
			clothes.getCategory().name(),
			imageStorageService.getImageUrl(clothes.getImageKey()),
			clothes.getPurchaseUrl()
		);
	}

	private CommentPreviewResponse toCommentPreview(Comment comment) {
		return new CommentPreviewResponse(
			comment.getId(),
			comment.getUser().getName(),
			comment.getContent(),
			comment.getCreatedAt().atZone(RESPONSE_ZONE).toOffsetDateTime()
		);
	}

	private FeedUserResponse toFeedUser(User user) {
		return new FeedUserResponse(user.getId(), user.getName(), user.getPosition().getLabel());
	}
}
