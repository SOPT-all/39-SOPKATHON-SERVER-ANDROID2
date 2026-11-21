package org.sopt.user.entity;

import org.sopt.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	private Position position;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	private Department department;

	@Column(length = 255)
	private String profileImageKey;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Builder
	public User(String name, Position position, Department department, String profileImageKey, String description) {
		this.name = name;
		this.position = position;
		this.department = department;
		this.profileImageKey = profileImageKey;
		this.description = description;
	}
}
