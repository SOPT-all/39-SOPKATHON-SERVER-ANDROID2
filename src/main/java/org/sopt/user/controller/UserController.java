package org.sopt.user.controller;

import org.sopt.common.response.ApiResponse;
import org.sopt.common.code.SuccessCode;
import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.user.dto.response.UserCreateResponse;
import org.sopt.user.dto.response.UserProfileResponse;
import org.sopt.user.dto.response.UserSearchResponse;
import org.sopt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

	private final UserService userService;

	public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(UserCreateRequest request) {
		return ResponseEntity.status(SuccessCode.USER_CREATED.getStatus())
			.body(ApiResponse.success(SuccessCode.USER_CREATED, userService.createUser(request)));
	}

	public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(Long userId) {
		return ResponseEntity.ok(
			ApiResponse.success(SuccessCode.TARGET_USER_FOUND, userService.getUserProfile(userId))
		);
	}

	public ResponseEntity<ApiResponse<UserSearchResponse>> searchUsers(
		String keyword,
		String position,
		String department,
		int limit,
		String cursor
	) {
		return ResponseEntity.ok(ApiResponse.success(
			SuccessCode.USER_SEARCHED,
			userService.searchUsers(keyword, position, department, limit, cursor)
		));
	}
}
