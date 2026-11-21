package org.sopt.post.service;

import java.time.ZoneId;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.post.dto.response.CommentItemResponse;
import org.sopt.post.dto.response.CommentPageResponse;
import org.sopt.post.dto.response.FeedUserResponse;
import org.sopt.post.entity.Comment;
import org.sopt.post.entity.Post;
import org.sopt.post.entity.PostVisibility;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.UserNotFoundException;
import org.sopt.post.repository.CommentCursor;
import org.sopt.post.repository.CommentRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
	private static final int MAX_LIMIT = 50;
	private static final ZoneId RESPONSE_ZONE = ZoneId.of("Asia/Seoul");

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public CommentPageResponse getComments(Long userId, Long postId, int limit, String rawCursor) {
		if (limit < 1 || limit > MAX_LIMIT) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		User viewer = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		Post post = postRepository.findById(postId)
			.orElseThrow(PostNotFoundException::new);
		if (!isVisibleTo(post, viewer)) {
			throw new PostNotFoundException();
		}

		CommentCursor cursor = rawCursor == null ? null : CommentCursor.decode(rawCursor);
		List<Comment> result = commentRepository.findPageByPostId(
			postId,
			cursor == null ? null : cursor.createdAt(),
			cursor == null ? null : cursor.commentId(),
			PageRequest.of(0, limit + 1)
		);

		boolean hasMore = result.size() > limit;
		List<Comment> comments = hasMore ? result.subList(0, limit) : result;
		List<CommentItemResponse> items = comments.stream().map(this::toResponse).toList();

		String nextCursor = null;
		if (hasMore) {
			Comment lastComment = comments.get(comments.size() - 1);
			nextCursor = CommentCursor.encode(lastComment.getCreatedAt(), lastComment.getId());
		}
		return new CommentPageResponse(items, nextCursor, hasMore);
	}

	private boolean isVisibleTo(Post post, User viewer) {
		return switch (post.getVisibility()) {
			case PUBLIC -> true;
			case DEPARTMENT -> post.getAuthor().getDepartment() == viewer.getDepartment();
			case PRIVATE -> post.getTargetUser().getId().equals(viewer.getId());
		};
	}

	private CommentItemResponse toResponse(Comment comment) {
		User author = comment.getUser();
		return new CommentItemResponse(
			comment.getId(),
			new FeedUserResponse(author.getId(), author.getName(), author.getPosition().getLabel()),
			comment.getContent(),
			comment.getCreatedAt().atZone(RESPONSE_ZONE).toOffsetDateTime()
		);
	}
}
