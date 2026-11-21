package org.sopt.common.s3.controller;

import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.common.s3.dto.ImageUpload;
import org.sopt.common.s3.dto.PresignedUrlRequest;
import org.sopt.common.s3.dto.PresignedUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Image Upload")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PresignedUrlController {

	private final ImageStorageService imageStorageService;

	@Operation(summary = "이미지 업로드용 Presigned URL 발급", description = "발급된 URL로 PUT 업로드할 때 요청한 Content-Type을 그대로 전송해야 합니다.")
	@PostMapping(path = "/presigned-url")
	public ResponseEntity<ApiResponse<PresignedUrlResponse>> issue(@Valid @RequestBody PresignedUrlRequest request) {
		ImageUpload upload = imageStorageService.createUpload(request.contentType());
		return ResponseEntity.status(SuccessCode.PRESIGNED_URL_ISSUED.getStatus())
			.body(ApiResponse.success(SuccessCode.PRESIGNED_URL_ISSUED,
				new PresignedUrlResponse(upload.uploadUrl(), upload.key())));
	}
}
