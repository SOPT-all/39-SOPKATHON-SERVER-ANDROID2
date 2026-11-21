package org.sopt.common.s3.controller;

import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.common.s3.dto.ImageUpload;
import org.sopt.common.s3.dto.PresignedUrlRequest;
import org.sopt.common.s3.dto.PresignedUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PresignedUrlController implements PresignedUrlApi {

	private final ImageStorageService imageStorageService;

	public ResponseEntity<ApiResponse<PresignedUrlResponse>> issue(PresignedUrlRequest request) {
		ImageUpload upload = imageStorageService.createUpload(request.contentType());
		return ResponseEntity.status(SuccessCode.PRESIGNED_URL_ISSUED.getStatus())
			.body(ApiResponse.success(SuccessCode.PRESIGNED_URL_ISSUED,
				new PresignedUrlResponse(upload.uploadUrl(), upload.key())));
	}
}
