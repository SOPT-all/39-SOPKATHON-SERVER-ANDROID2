package org.sopt.post.dto.request;

import java.util.List;

import org.sopt.post.entity.PostVisibility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "코디 게시물 작성 요청")
public record PostCreateRequest(
	@NotBlank
	@Size(max = 255)
	@Schema(description = "게시물 제목", example = "출근길 봄 코디", requiredMode = Schema.RequiredMode.REQUIRED)
	String title,

	// presigned URL로 결과 이미지를 업로드한 뒤 발급받은 스토리지 키
	@NotBlank
	@Size(max = 255)
	@Schema(description = "업로드 완료 후 받은 코디 이미지 객체 키", example = "images/posts/look123.png", requiredMode = Schema.RequiredMode.REQUIRED)
	String imageKey,

	@Size(max = 500)
	@Schema(description = "코디에 대한 설명", example = "가벼운 트렌치코트로 산뜻하게 입어봤어요.")
	String description,

	// 게시글을 볼 수 있는 범위
	@NotNull
	@Schema(description = "공개 범위: PUBLIC(전체 공개), PRIVATE(비공개), DEPARTMENT(같은 부서 공개)", example = "PUBLIC", requiredMode = Schema.RequiredMode.REQUIRED)
	PostVisibility visibility,

	@NotNull
	@Schema(description = "공개 범위 설정에 따라 게시물을 볼 수 있는 대상 사용자 ID", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
	Long targetUserId,

	@NotEmpty
	@Size(max = 10)
	@Schema(description = "코디에 포함된 옷 ID 목록 (최대 10개)", example = "[3, 8]", requiredMode = Schema.RequiredMode.REQUIRED)
	List<@NotNull Long> clothesIds
) {
}
