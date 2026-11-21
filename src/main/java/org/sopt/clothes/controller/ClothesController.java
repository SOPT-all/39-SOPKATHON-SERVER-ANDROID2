package org.sopt.clothes.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.clothes.code.SuccessCode;
import org.sopt.clothes.dto.request.ClothesCreateRequest;
import org.sopt.clothes.dto.response.ClothesCreateResponse;
import org.sopt.clothes.dto.response.ClothesListResponse;
import org.sopt.clothes.service.ClothesService;
import org.sopt.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClothesController implements ClothesApi {
	private final ClothesService clothesService;

	public ResponseEntity<ApiResponse<ClothesCreateResponse>> createClothes(
		ClothesCreateRequest request
	) {
		ClothesCreateResponse response = clothesService.createClothes(request);
		return ResponseEntity.status(SuccessCode.CLOTHES_CREATED.getStatus())
			.body(ApiResponse.success(SuccessCode.CLOTHES_CREATED, response));
	}

	public ResponseEntity<ApiResponse<ClothesListResponse>> getClothes() {
		return ResponseEntity.status(SuccessCode.CLOTHES_LIST_FOUND.getStatus())
			.body(ApiResponse.success(SuccessCode.CLOTHES_LIST_FOUND, clothesService.getClothes()));
	}
}
