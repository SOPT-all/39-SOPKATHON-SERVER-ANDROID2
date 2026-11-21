package org.sopt.post.dto.request;

import java.util.List;

import org.sopt.post.entity.PostVisibility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
	@NotBlank
	@Size(max = 255)
	String title,

	// presigned URL로 결과 이미지를 업로드한 뒤 발급받은 스토리지 키
	@NotBlank
	@Size(max = 255)
	String imageKey,

	@Size(max = 500)
	String description,

	// 게시글을 볼 수 있는 범위
	@NotNull
	PostVisibility visibility,

	@NotNull
	Long targetUserId,

	@NotEmpty
	@Size(max = 10)
	List<@NotNull Long> clothesIds
) {
}
