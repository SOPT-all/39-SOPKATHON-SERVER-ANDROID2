package org.sopt.post.entity;

import org.sopt.clothes.entity.Clothes;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_clothes", uniqueConstraints = @UniqueConstraint(name = "uk_post_clothes_post_clothes", columnNames = {"post_id", "clothes_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostClothes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "clothes_id", nullable = false)
	private Clothes clothes;

	public PostClothes(Post post, Clothes clothes) {
		this.post = post;
		this.clothes = clothes;
	}
}
