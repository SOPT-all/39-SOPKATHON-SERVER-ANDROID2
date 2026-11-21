package org.sopt.clothes.service;

import lombok.RequiredArgsConstructor;
import org.sopt.clothes.dto.request.ClothesCreateRequest;
import org.sopt.clothes.dto.response.ClothesCreateResponse;
import org.sopt.clothes.entity.Clothes;
import org.sopt.clothes.entity.ClothesCategory;
import org.sopt.clothes.exception.InvalidClothesCategoryException;
import org.sopt.clothes.repository.ClothesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClothesService {
	private final ClothesRepository clothesRepository;

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
}
