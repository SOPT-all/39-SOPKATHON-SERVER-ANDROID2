package org.sopt.user.controller;

import org.sopt.common.response.ApiResponse;
import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.user.dto.response.UserCreateResponse;
import org.sopt.user.dto.response.UserProfileResponse;
import org.sopt.user.dto.response.UserSearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "프로필", description = "프로필 생성·검색·조회합니다.")
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "프로필 생성", description = "서비스에서 사용할 프로필을 생성합니다. 프로필 사진은 미리 업로드한 imageKey를 전달하세요.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "프로필 생성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "프로필 생성 성공", value = "{\"status\":201,\"code\":\"USER_CREATED\",\"message\":\"프로필이 생성되었습니다.\",\"data\":{\"userId\":42}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이름·부서·직급 등 입력값이 올바르지 않음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "필수값 누락", value = "{\"status\":400,\"code\":\"INVALID_INPUT_VALUE\",\"message\":\"@Valid 검증 실패\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 프로필을 생성한 사용자", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "이미 생성된 프로필", value = "{\"status\":409,\"code\":\"USER_ALREADY_EXISTS\",\"message\":\"이미 프로필을 생성하셨습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<UserCreateResponse>> createUser(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "프로필 생성에 필요한 값",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
				name = "개발팀 신입 프로필",
				value = "{\"name\":\"김민수\",\"department\":\"DEVELOPMENT\",\"position\":\"STAFF\",\"description\":\"편안하고 실용적인 코디를 좋아합니다.\",\"profileImageKey\":\"images/profile/minsu-kim.png\"}"
			))
		) @Valid @RequestBody UserCreateRequest request
	);

	@GetMapping("/{userId}")
	@Operation(summary = "동료 프로필 조회", description = "사용자 ID로 프로필과 게시물·받은 좋아요·대표 코디 정보를 조회합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "동료 프로필 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "프로필 조회 성공", value = "{\"status\":200,\"code\":\"TARGET_USER_FOUND\",\"message\":\"동료 프로필 조회에 성공했습니다.\",\"data\":{\"userId\":42,\"name\":\"김민수\",\"position\":\"사원\",\"department\":\"개발팀\",\"profileImageUrl\":\"https://images.example.com/profile/minsu.png\",\"postCount\":3,\"receivedPostCount\":2,\"receivedLikeCount\":18,\"receivedOutfits\":[{\"postId\":105,\"title\":\"출근길 봄 코디\",\"imageUrl\":\"https://images.example.com/posts/spring-look.png\",\"likeCount\":12}]}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "사용자 없음", value = "{\"status\":404,\"code\":\"USER_NOT_FOUND\",\"message\":\"사용자를 찾을 수 없습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@PathVariable Long userId);

	@GetMapping
	@Operation(summary = "동료 검색", description = "이름 키워드, 직급, 부서로 동료를 검색합니다. 조건은 조합해서 사용할 수 있으며 결과는 커서 기반으로 이어서 조회할 수 있습니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "동료 검색 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "검색 결과", value = "{\"status\":200,\"code\":\"USER_SEARCHED\",\"message\":\"동료 검색에 성공했습니다.\",\"data\":{\"items\":[{\"userId\":42,\"name\":\"김민수\",\"position\":\"사원\",\"department\":\"개발팀\",\"profileImageUrl\":\"https://images.example.com/profile/minsu.png\"}],\"nextCursor\":\"eyJ1c2VySWQiOjQyfQ==\",\"hasMore\":true}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "검색 조건·limit·cursor 값이 올바르지 않음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 limit", value = "{\"status\":400,\"code\":\"INVALID_INPUT_VALUE\",\"message\":\"@Valid 검증 실패\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 cursor", value = "{\"status\":400,\"code\":\"INVALID_CURSOR\",\"message\":\"페이지 커서가 올바르지 않습니다.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<UserSearchResponse>> searchUsers(
		@Parameter(description = "이름 검색어", example = "김민수") @RequestParam(required = false) String keyword,
		@Parameter(description = "직급명 (한글)", example = "과장") @RequestParam(required = false) String position,
		@Parameter(description = "부서명 (한글)", example = "개발팀") @RequestParam(required = false) String department,
		@Parameter(description = "한 번에 가져올 사용자 수", example = "20") @RequestParam(defaultValue = "20") int limit,
		@Parameter(description = "다음 페이지 조회 커서. 첫 페이지에서는 생략합니다.") @RequestParam(required = false) String cursor
	);
}
