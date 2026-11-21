package org.sopt.post.dto.response;

import java.time.OffsetDateTime;

public record CommentItemResponse(
	Long commentId,
	FeedUserResponse author,
	String content,
	OffsetDateTime createdAt
) {
}
