package org.sopt.post.service;

import java.time.ZoneId;

import lombok.RequiredArgsConstructor;
import org.sopt.post.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.post.dto.request.CommentCreateRequest;
import org.sopt.post.dto.response.CommentCreateResponse;
import org.sopt.post.entity.Comment;
import org.sopt.post.entity.Post;
import org.sopt.post.entity.PostVisibility;
import org.sopt.post.repository.CommentRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
	private static final ZoneId RESPONSE_ZONE = ZoneId.of("Asia/Seoul");

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public CommentCreateResponse createComment(Long userId, Long postId, CommentCreateRequest request) {
		String content = request == null ? null : request.content();
		if (content == null || content.isBlank() || content.length() > 500) {
			throw new BaseException(ErrorCode.INVALID_COMMENT);
		}

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
		if (!isVisibleTo(post, user)) {
			throw new BaseException(ErrorCode.POST_NOT_FOUND);
		}

		Comment comment = commentRepository.save(new Comment(user, post, content));
		int commentCount = Math.toIntExact(commentRepository.countByPostId(postId));
		return new CommentCreateResponse(
			comment.getId(),
			postId,
			comment.getContent(),
			comment.getCreatedAt().atZone(RESPONSE_ZONE).toOffsetDateTime(),
			commentCount
		);
	}

	private boolean isVisibleTo(Post post, User viewer) {
		return switch (post.getVisibility()) {
			case PUBLIC -> true;
			case DEPARTMENT -> post.getAuthor().getDepartment() == viewer.getDepartment();
			case PRIVATE -> post.getTargetUser().getId().equals(viewer.getId());
		};
	}
}
