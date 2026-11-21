package org.sopt.common.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlResponse(
	@Schema(description = "이미지 업로드에 사용할 5분 유효 S3 PUT URL") String presignedUrl,
	@Schema(description = "업로드된 이미지의 S3 객체 키", example = "images/123e4567-e89b-12d3-a456-426614174000") String imageKey
) {
}
