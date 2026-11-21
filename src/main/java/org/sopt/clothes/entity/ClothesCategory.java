package org.sopt.clothes.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothesCategory {
	TOP("상의"),
	BOTTOM("하의");

	private final String label;
}
