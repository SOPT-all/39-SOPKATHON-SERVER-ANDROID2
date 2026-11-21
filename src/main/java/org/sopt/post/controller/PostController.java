package org.sopt.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.request.CommentCreateRequest;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.*;
import org.sopt.post.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
	private final PostService postService;
	private final PostDetailService postDetailService;
	private final LikeService likeService;
	private final CommentService commentService;
    private final CommentQueryService commentQueryService;

	@PostMapping
	public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
		@PathVariable("userId") Long userId,
		@Valid @RequestBody PostCreateRequest request
	) {
		PostCreateResponse response = postService.createPost(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(SuccessCode.POST_CREATED, response));
	}

	@GetMapping("/{postId}")
	public ApiResponse<PostDetailResponse> getPostDetail(
		@PathVariable("userId") Long userId,
		@PathVariable("postId") Long postId
	) {
		return ApiResponse.success(
			SuccessCode.POST_RETRIEVED,
			postDetailService.getPostDetail(userId, postId)
		);
	}

	@PostMapping("/{postId}/like")
	public ResponseEntity<ApiResponse<PostLikeResponse>> likePost(
		@PathVariable("userId") Long userId,
		@PathVariable("postId") Long postId
	) {
		PostLikeResponse response = likeService.likePost(userId, postId);
		return ResponseEntity.status(org.sopt.post.code.SuccessCode.SUCCESS.getStatus())
			.body(ApiResponse.success(org.sopt.post.code.SuccessCode.SUCCESS, response));
	}

	@DeleteMapping("/{postId}/like")
	public ResponseEntity<ApiResponse<PostLikeResponse>> unlikePost(
		@PathVariable("userId") Long userId,
		@PathVariable("postId") Long postId
	) {
		PostLikeResponse response = likeService.unlikePost(userId, postId);
		return ResponseEntity.status(org.sopt.post.code.SuccessCode.LIKE_DELETED.getStatus())
			.body(ApiResponse.success(org.sopt.post.code.SuccessCode.LIKE_DELETED, response));
	}

	@PostMapping("/{postId}/comments")
	public ResponseEntity<ApiResponse<CommentCreateResponse>> createComment(
		@PathVariable("userId") Long userId,
		@PathVariable("postId") Long postId,
		@RequestBody CommentCreateRequest request
	) {
		CommentCreateResponse response = commentService.createComment(userId, postId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(SuccessCode.COMMENT_CREATED, response));
	}

    @GetMapping("/{postId}/comments")
    public ApiResponse<CommentPageResponse> getComments(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String cursor
    ) {
        return ApiResponse.success(
                SuccessCode.COMMENT_RETRIEVED,
                commentQueryService.getComments(userId, postId, limit, cursor)
        );
    }
}
