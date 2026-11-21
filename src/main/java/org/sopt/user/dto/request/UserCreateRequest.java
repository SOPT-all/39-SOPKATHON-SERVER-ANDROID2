package org.sopt.user.dto.request;

import org.sopt.user.entity.Department;
import org.sopt.user.entity.Position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
	@NotBlank(message = "이름은 필수입니다.")
	String name,
	@NotNull(message = "부서는 필수입니다.")
	Department department,
	@NotNull(message = "직급은 필수입니다.")
	Position position,
	String description,
	String profileImageKey
) {
}
