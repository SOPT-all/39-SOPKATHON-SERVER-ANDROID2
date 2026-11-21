package org.sopt.clothes.dto.response;

import java.util.List;

public record ClothesListResponse(
	List<ClothesItemResponse> top,
	List<ClothesItemResponse> bottom
) {
	public record ClothesItemResponse(
		Long clothesId,
		String name,
		String imageUrl,
		String purchaseLink
	) {
	}
}
