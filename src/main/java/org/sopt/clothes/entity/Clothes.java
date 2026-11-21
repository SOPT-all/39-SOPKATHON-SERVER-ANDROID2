package org.sopt.clothes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clothes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 255, nullable = false)
	private String imageKey;

	@Column(nullable = false)
	private String purchaseUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ClothesCategory category;

	@Builder
	public Clothes(String name, String imageKey, String purchaseUrl, ClothesCategory category) {
		this.name = name;
		this.imageKey = imageKey;
		this.purchaseUrl = purchaseUrl;
		this.category = category;
	}
}
