package org.sopt.post.dto.response;

import java.time.OffsetDateTime;

public record CommentCreateResponse(
	Long commentId,
	Long postId,
	String content,
	OffsetDateTime createdAt,
	int commentCount
) {
}
