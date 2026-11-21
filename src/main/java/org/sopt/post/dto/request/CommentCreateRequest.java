package org.sopt.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 작성 요청")
public record CommentCreateRequest(
	@Schema(description = "댓글 내용", example = "코디가 정말 멋져요!") String content
) {
}
