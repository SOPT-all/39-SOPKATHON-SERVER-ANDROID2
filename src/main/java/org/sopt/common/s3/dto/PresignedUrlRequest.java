package org.sopt.common.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PresignedUrlRequest(
	@Schema(description = "업로드할 이미지의 MIME 타입. 발급된 URL로 S3 PUT 요청을 보낼 때도 동일한 Content-Type 헤더를 사용해야 합니다.", allowableValues = {"image/jpeg", "image/png", "image/webp"}, example = "image/png")
	@NotBlank String contentType
) {
}
