package org.sopt.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.request.CommentCreateRequest;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.*;
import org.sopt.post.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {
	private final PostService postService;
	private final PostDetailService postDetailService;
	private final LikeService likeService;
	private final CommentService commentService;
    private final CommentQueryService commentQueryService;

	public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
		Long userId,
		PostCreateRequest request
	) {
		PostCreateResponse response = postService.createPost(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(SuccessCode.POST_CREATED, response));
	}

	public ApiResponse<PostDetailResponse> getPostDetail(
		Long userId,
		Long postId
	) {
		return ApiResponse.success(
			SuccessCode.POST_RETRIEVED,
			postDetailService.getPostDetail(userId, postId)
		);
	}

	public ResponseEntity<ApiResponse<PostLikeResponse>> likePost(
		Long userId,
		Long postId
	) {
		PostLikeResponse response = likeService.likePost(userId, postId);
		return ResponseEntity.status(org.sopt.post.code.SuccessCode.SUCCESS.getStatus())
			.body(ApiResponse.success(org.sopt.post.code.SuccessCode.SUCCESS, response));
	}

	public ResponseEntity<ApiResponse<PostLikeResponse>> unlikePost(
		Long userId,
		Long postId
	) {
		PostLikeResponse response = likeService.unlikePost(userId, postId);
		return ResponseEntity.status(org.sopt.post.code.SuccessCode.LIKE_DELETED.getStatus())
			.body(ApiResponse.success(org.sopt.post.code.SuccessCode.LIKE_DELETED, response));
	}

	public ResponseEntity<ApiResponse<CommentCreateResponse>> createComment(
		Long userId,
		Long postId,
		CommentCreateRequest request
	) {
		CommentCreateResponse response = commentService.createComment(userId, postId, request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(SuccessCode.COMMENT_CREATED, response));
	}

    public ApiResponse<CommentPageResponse> getComments(
            Long userId,
            Long postId,
            int limit,
            String cursor
    ) {
        return ApiResponse.success(
                SuccessCode.COMMENT_RETRIEVED,
                commentQueryService.getComments(userId, postId, limit, cursor)
        );
    }
}
