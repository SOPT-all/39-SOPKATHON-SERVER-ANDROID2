package org.sopt.clothes.controller;

import org.sopt.clothes.dto.request.ClothesCreateRequest;
import org.sopt.clothes.dto.response.ClothesCreateResponse;
import org.sopt.clothes.dto.response.ClothesListResponse;
import org.sopt.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "옷", description = "옷 정보를 등록하고 코디할 때 해당 목록을 조회합니다.")
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ClothesApi {
	@PostMapping(value = "/clothes", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "옷 등록", description = "옷 이름, 이미지 키, 구매 링크, 카테고리를 등록합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "옷 등록 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "옷 등록 성공", value = "{\"status\":201,\"code\":\"CLOTHES_CREATED\",\"message\":\"옷 추가에 성공했습니다.\",\"data\":{\"clothesId\":103}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이름·이미지 키 누락 또는 category가 TOP/BOTTOM이 아님", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "필수값 누락", value = "{\"status\":400,\"code\":\"INVALID_INPUT_VALUE\",\"message\":\"@Valid 검증 실패\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 카테고리", value = "{\"status\":400,\"code\":\"INVALID_CLOTHES_CATEGORY\",\"message\":\"올바르지 않은 옷 카테고리입니다.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<ClothesCreateResponse>> createClothes(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "등록할 옷의 이름·이미지 키·구매 링크·분류",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
				name = "트렌치코트",
				value = "{\"name\":\"베이지 트렌치코트\",\"imageKey\":\"images/clothes/trench-coat-01.png\",\"purchaseLink\":\"https://shop.example.com/products/coat-123\",\"category\":\"TOP\"}"
			))
		) @Valid @RequestBody ClothesCreateRequest request
	);

	@GetMapping("/clothes")
	@Operation(summary = "내 옷 목록 조회", description = "등록된 옷을 상의(top)와 하의(bottom)로 구분해 조회합니다(List)")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "옷 목록 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "옷장 목록", value = "{\"status\":200,\"code\":\"CLOTHES_LIST_FOUND\",\"message\":\"옷 목록 조회에 성공했습니다.\",\"data\":{\"top\":[{\"clothesId\":103,\"name\":\"베이지 트렌치코트\",\"imageUrl\":\"https://images.example.com/clothes/trench-coat.png\",\"purchaseLink\":\"https://shop.example.com/products/coat-123\"}],\"bottom\":[{\"clothesId\":87,\"name\":\"네이비 슬랙스\",\"imageUrl\":\"https://images.example.com/clothes/navy-slacks.png\",\"purchaseLink\":\"https://shop.example.com/products/pants-87\"}]}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<ClothesListResponse>> getClothes();
}
