package org.sopt.common.s3.controller;

import org.sopt.common.response.ApiResponse;
import org.sopt.common.s3.dto.PresignedUrlRequest;
import org.sopt.common.s3.dto.PresignedUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "이미지 업로드", description = "이미지를 S3에 직접 업로드할 수 있는 Presigned URL을 발급합니다.")
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PresignedUrlApi {
	@PostMapping(value = "/presigned-url", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "이미지 업로드용 Presigned URL 발급", description = "발급된 URL로 PUT 업로드할 때 요청한 Content-Type을 그대로 전송해야 합니다." +"(URL은 5분간 유효합니다.)")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Presigned URL 발급 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "업로드 URL 발급", value = "{\"status\":200,\"code\":\"PRESIGNED_URL_ISSUED\",\"message\":\"Presigned URL 발급 성공\",\"data\":{\"presignedUrl\":\"https://style-bucket.s3.ap-northeast-2.amazonaws.com/images/4c8b2f44-3c1d-4e15-a3ad-7e73985bbf1a?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=300\",\"imageKey\":\"images/4c8b2f44-3c1d-4e15-a3ad-7e73985bbf1a\"}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Content-Type 누락 또는 지원하지 않는 이미지 형식", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "필수값 누락", value = "{\"status\":400,\"code\":\"INVALID_INPUT_VALUE\",\"message\":\"@Valid 검증 실패\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "지원하지 않는 이미지 형식", value = "{\"status\":400,\"code\":\"INVALID_IMAGE_CONTENT_TYPE\",\"message\":\"이미지 Content-Type이 올바르지 않습니다\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<PresignedUrlResponse>> issue(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "허용된 이미지 MIME 타입을 지정합니다.",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
				name = "PNG 이미지",
				value = "{\"contentType\":\"image/png\"}"
			))
		) @Valid @RequestBody PresignedUrlRequest request
	);
}
