package org.sopt.clothes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.clothes.code.SuccessCode;
import org.sopt.clothes.dto.request.ClothesCreateRequest;
import org.sopt.clothes.dto.response.ClothesCreateResponse;
import org.sopt.clothes.dto.response.ClothesListResponse;
import org.sopt.clothes.service.ClothesService;
import org.sopt.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClothesController {
	private final ClothesService clothesService;

	@PostMapping(path = "/clothes")
	public ResponseEntity<ApiResponse<ClothesCreateResponse>> createClothes(
		@Valid @RequestBody ClothesCreateRequest request
	) {
		ClothesCreateResponse response = clothesService.createClothes(request);
		return ResponseEntity.status(SuccessCode.CLOTHES_CREATED.getStatus())
			.body(ApiResponse.success(SuccessCode.CLOTHES_CREATED, response));
	}

	@GetMapping(path = "/clothes")
	public ResponseEntity<ApiResponse<ClothesListResponse>> getClothes() {
		return ResponseEntity.status(SuccessCode.CLOTHES_LIST_FOUND.getStatus())
			.body(ApiResponse.success(SuccessCode.CLOTHES_LIST_FOUND, clothesService.getClothes()));
	}
}
