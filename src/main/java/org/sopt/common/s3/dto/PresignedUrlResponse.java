package org.sopt.common.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlResponse(
	@Schema(description = "이미지 업로드에 사용할 5분 동안 유효한 S3 PUT URL. 이 URL로 PUT 요청을 보내세요.", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/...") String presignedUrl,
	@Schema(description = "업로드된 이미지의 S3 객체 키", example = "images/123e4567-e89b-12d3-a456-426614174000") String imageKey
) {
}
