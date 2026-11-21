package org.sopt.user.dto.request;

import org.sopt.user.entity.Department;
import org.sopt.user.entity.Position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 생성 요청")
public record UserCreateRequest(
	@NotBlank(message = "이름은 필수입니다.")
	@Schema(description = "사용자 이름", example = "김민수", maxLength = 50)
	String name,
	@NotNull(message = "부서는 필수입니다.")
	@Schema(description = "부서 코드", allowableValues = {"SALES_1", "SALES_2", "MARKETING", "HR", "DEVELOPMENT", "DESIGN"}, example = "DEVELOPMENT")
	Department department,
	@NotNull(message = "직급은 필수입니다.")
	@Schema(description = "직급 코드", allowableValues = {"STAFF", "ASSOCIATE", "ASSISTANT_MANAGER", "MANAGER", "DEPUTY_GENERAL_MANAGER", "GENERAL_MANAGER", "DIRECTOR"}, example = "STAFF")
	Position position,
	@Schema(description = "프로필 소개", example = "편안하고 실용적인 코디를 좋아합니다.", maxLength = 500)
	String description,
	@Schema(description = "업로드 완료 후 받은 프로필 이미지 객체 키", example = "images/profile/abc123.png")
	String profileImageKey
) {
}
