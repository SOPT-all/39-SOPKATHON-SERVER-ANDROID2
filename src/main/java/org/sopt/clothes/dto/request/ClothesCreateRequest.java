package org.sopt.clothes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClothesCreateRequest(
	@NotBlank @Size(max = 255) String name,
	@NotBlank @Size(max = 255) String imageKey,
	@NotBlank @Size(max = 255) String purchaseLink,
	@NotNull String category
) {
}
