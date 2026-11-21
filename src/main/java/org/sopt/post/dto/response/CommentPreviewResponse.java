package org.sopt.post.dto.response;

import java.time.OffsetDateTime;

public record CommentPreviewResponse(
	Long commentId,
	String authorName,
	String content,
	OffsetDateTime createdAt
) {
}
