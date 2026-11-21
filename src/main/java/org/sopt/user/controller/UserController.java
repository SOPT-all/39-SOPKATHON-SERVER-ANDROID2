package org.sopt.user.controller;

import org.sopt.common.response.ApiResponse;
import org.sopt.common.code.SuccessCode;
import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.user.dto.response.UserCreateResponse;
import org.sopt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@Operation(summary = "프로필 생성")
	public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(
		@Valid @RequestBody UserCreateRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.success(SuccessCode.USER_CREATED, userService.createUser(request))
		);
	}
}
