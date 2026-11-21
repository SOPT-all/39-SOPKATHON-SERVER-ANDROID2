package org.sopt.common.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PresignedUrlRequest(
	@Schema(description = "업로드할 이미지 타입. S3 PUT 요청에도 동일한 Content-Type을 사용해야 합니다.", example = "image/png")
	@NotBlank String contentType
) {
}
