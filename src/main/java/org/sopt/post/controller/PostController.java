package org.sopt.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.PostCreateResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/{userId}/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
		@PathVariable("userId") Long userId,
		@Valid @RequestBody PostCreateRequest request
	) {
		PostCreateResponse response = postService.createPost(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(SuccessCode.POST_CREATED, response));
	}
}
