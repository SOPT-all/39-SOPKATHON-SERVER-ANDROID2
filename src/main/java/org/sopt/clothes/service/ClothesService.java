package org.sopt.clothes.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.sopt.clothes.dto.request.ClothesCreateRequest;
import org.sopt.clothes.dto.response.ClothesCreateResponse;
import org.sopt.clothes.dto.response.ClothesListResponse;
import org.sopt.clothes.entity.Clothes;
import org.sopt.clothes.entity.ClothesCategory;
import org.sopt.clothes.exception.InvalidClothesCategoryException;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.clothes.repository.ClothesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClothesService {
	private final ClothesRepository clothesRepository;
	private final ImageStorageService imageStorageService;

	@Transactional
	public ClothesCreateResponse createClothes(ClothesCreateRequest request) {
		ClothesCategory category;
		try {
			category = ClothesCategory.valueOf(request.category());
		} catch (IllegalArgumentException exception) {
			throw new InvalidClothesCategoryException();
		}

		Clothes clothes = clothesRepository.save(Clothes.builder()
			.name(request.name())
			.imageKey(request.imageKey())
			.purchaseUrl(request.purchaseLink())
			.category(category)
			.build());
		return new ClothesCreateResponse(clothes.getId());
	}

	@Transactional(readOnly = true)
	public ClothesListResponse getClothes() {
		List<ClothesListResponse.ClothesItemResponse> top = new ArrayList<>();
		List<ClothesListResponse.ClothesItemResponse> bottom = new ArrayList<>();

		for (Clothes clothes : clothesRepository.findAll()) {
			ClothesListResponse.ClothesItemResponse item = toResponse(clothes);
			if (clothes.getCategory() == ClothesCategory.TOP) {
				top.add(item);
			} else {
				bottom.add(item);
			}
		}

		return new ClothesListResponse(top, bottom);
	}

	private ClothesListResponse.ClothesItemResponse toResponse(Clothes clothes) {
		return new ClothesListResponse.ClothesItemResponse(
			clothes.getId(),
			clothes.getName(),
			imageStorageService.getImageUrl(clothes.getImageKey()),
			clothes.getPurchaseUrl()
		);
	}
}
