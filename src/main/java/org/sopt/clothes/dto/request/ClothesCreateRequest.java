package org.sopt.clothes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "옷 등록 요청")
public record ClothesCreateRequest(
	@Schema(description = "옷 이름", example = "베이지 트렌치코트", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank @Size(max = 255) String name,
	@Schema(description = "업로드 완료 후 받은 이미지 객체 키", example = "images/clothes/coat123.png", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank @Size(max = 255) String imageKey,
	@Schema(description = "상품 구매 링크", example = "https://shop.example.com/products/123", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank @Size(max = 255) String purchaseLink,
	@Schema(description = "옷 분류", allowableValues = {"TOP", "BOTTOM"}, example = "TOP", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull String category
) {
}
