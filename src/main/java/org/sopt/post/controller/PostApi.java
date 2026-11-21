package org.sopt.post.controller;

import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.request.CommentCreateRequest;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.CommentCreateResponse;
import org.sopt.post.dto.response.CommentPageResponse;
import org.sopt.post.dto.response.PostCreateResponse;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.dto.response.PostLikeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@Tag(name = "게시물", description = "게시물, 좋아요, 댓글 생성/조회 API 입니다.")
@RequestMapping(value = "/api/v1/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PostApi {
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "코디 게시물 작성", description = "업로드한 코디 이미지와 제목, 공개 범위, 옷 정보를 등록합니다. 옷 ID는 최대 10개까지 연결할 수 있습니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "게시물 작성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 작성 성공", value = "{\"status\":201,\"code\":\"POST_CREATED\",\"message\":\"게시물이 성공적으로 생성되었습니다.\",\"data\":{\"postId\":105}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청값 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "필수값 누락", value = "{\"status\":400,\"code\":\"INVALID_INPUT_VALUE\",\"message\":\"@Valid 검증 실패\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "공개 대상 오류", value = "{\"status\":400,\"code\":\"INVALID_POST_TARGET\",\"message\":\"공개 범위에 맞는 대상 사용자 정보를 입력해 주세요.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 옷 정보를 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "사용자 없음", value = "{\"status\":404,\"code\":\"USER_NOT_FOUND\",\"message\":\"사용자를 찾을 수 없습니다.\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "옷 정보 없음", value = "{\"status\":404,\"code\":\"CLOTHES_NOT_FOUND\",\"message\":\"옷 정보를 찾을 수 없습니다.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
		@PathVariable("userId") Long userId,
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "게시물과 코디에 포함할 옷 정보",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
				name = "봄 나들이 옷 추천",
				value = "{\"title\":\"출근길 봄 코디\",\"imageKey\":\"images/posts/spring-look-2026.png\",\"description\":\"가벼운 트렌치코트로 산뜻하게 입어봤어요.\",\"visibility\":\"PUBLIC\",\"targetUserId\":12,\"clothesIds\":[3,8]}"
			))
		) @Valid @RequestBody PostCreateRequest request
	);

	@GetMapping("/{postId}")
	@Operation(summary = "게시물 상세 조회", description = "게시물 내용과 작성자, 코디 정보, 좋아요 및 댓글 미리보기를 조회합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시물 상세 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 상세", value = "{\"status\":200,\"code\":\"POST_RETRIEVED\",\"message\":\"게시물 상세 조회에 성공했습니다.\",\"data\":{\"postId\":105,\"author\":{\"userId\":42,\"name\":\"김민수\",\"position\":\"사원\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/spring-look.png\",\"title\":\"출근길 봄 코디\",\"description\":\"가벼운 트렌치코트 코디입니다.\",\"outfitItems\":[{\"clothesId\":103,\"name\":\"베이지 트렌치코트\",\"category\":\"상의\",\"imageUrl\":\"https://images.example.com/clothes/trench-coat.png\",\"purchaseUrl\":\"https://shop.example.com/products/coat-123\"}],\"purchaseLinks\":[{\"url\":\"https://shop.example.com/products/coat-123\"}],\"likeCount\":12,\"liked\":false,\"commentCount\":3,\"commentsPreview\":[{\"commentId\":501,\"authorName\":\"이서연\",\"content\":\"색 조합이 정말 예뻐요!\",\"createdAt\":\"2026-11-22T10:30:00+09:00\"}],\"createdAt\":\"2026-11-22T09:15:00+09:00\"}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 게시물을 찾을 수 없음",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 없음", value = "{\"status\":404,\"code\":\"POST_NOT_FOUND\",\"message\":\"게시글을 찾을 수 없습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ApiResponse<PostDetailResponse> getPostDetail(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId);

	@PostMapping("/{postId}/like")
	@Operation(summary = "게시물 좋아요", description = "해당 게시물에 좋아요를 추가합니다. 이미 좋아요를 누른 상태라면 오류가 반환됩니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "좋아요 등록 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "좋아요 등록", value = "{\"status\":201,\"code\":\"SUCCESS\",\"message\":\"좋아요 등록에 성공했습니다.\",\"data\":{\"postId\":105,\"liked\":true,\"likeCount\":13}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 게시물을 찾을 수 없음",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 없음", value = "{\"status\":404,\"code\":\"POST_NOT_FOUND\",\"message\":\"게시글을 찾을 수 없습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시물 (ALREADY_LIKED)",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "중복 좋아요", value = "{\"status\":409,\"code\":\"ALREADY_LIKED\",\"message\":\"이미 좋아요를 누른 게시글입니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<PostLikeResponse>> likePost(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId);

	@DeleteMapping("/{postId}/like")
	@Operation(summary = "게시물 좋아요 취소", description = "현재 사용자의 해당 게시물 좋아요를 취소합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 취소 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "좋아요 취소", value = "{\"status\":200,\"code\":\"LIKE_DELETED\",\"message\":\"좋아요 삭제에 성공했습니다.\",\"data\":{\"postId\":105,\"liked\":false,\"likeCount\":12}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 게시물을 찾을 수 없음",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 없음", value = "{\"status\":404,\"code\":\"POST_NOT_FOUND\",\"message\":\"게시글을 찾을 수 없습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<PostLikeResponse>> unlikePost(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId);

	@PostMapping(value = "/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "댓글 작성", description = "게시물에 댓글을 작성합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "댓글 작성 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "댓글 작성 성공", value = "{\"status\":201,\"code\":\"COMMENT_CREATED\",\"message\":\"댓글 작성에 성공했습니다.\",\"data\":{\"commentId\":501,\"postId\":105,\"content\":\"색 조합이 정말 예뻐요!\",\"createdAt\":\"2026-11-22T10:30:00+09:00\",\"commentCount\":4}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "댓글은 비어 있을 수 없고 500자를 넘을 수 없음)",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "댓글 입력 오류", value = "{\"status\":400,\"code\":\"INVALID_COMMENT\",\"message\":\"댓글은 1~500자로 입력해 주세요.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 게시물을 찾을 수 없음",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 없음", value = "{\"status\":404,\"code\":\"POST_NOT_FOUND\",\"message\":\"게시글을 찾을 수 없습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ResponseEntity<ApiResponse<CommentCreateResponse>> createComment(
		@PathVariable("userId") Long userId,
		@PathVariable("postId") Long postId,
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "1~500자 댓글 내용",
			content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
				name = "코디 칭찬 댓글",
				value = "{\"content\":\"트렌치코트와 바지 색 조합이 정말 멋져요!\"}"
			))
		) @RequestBody CommentCreateRequest request
	);

	@GetMapping("/{postId}/comments")
	@Operation(summary = "댓글 목록 조회", description = "게시물의 댓글을 커서 기반으로 조회합니다. 다음 페이지가 있으면 응답의 nextCursor를 전달하세요.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "댓글 목록", value = "{\"status\":200,\"code\":\"COMMENT_RETRIEVED\",\"message\":\"댓글 조회에 성공했습니다.\",\"data\":{\"items\":[{\"commentId\":501,\"author\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"content\":\"색 조합이 정말 예뻐요!\",\"createdAt\":\"2026-11-22T10:30:00+09:00\"}],\"nextCursor\":\"MjAyNi0xMS0yMlQxMDozMDowMHw1MDE\",\"hasMore\":true}}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "limit 또는 cursor 형식이 올바르지 않음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 limit", value = "{\"status\":400,\"code\":\"INVALID_INPUT_VALUE\",\"message\":\"@Valid 검증 실패\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 cursor", value = "{\"status\":400,\"code\":\"INVALID_CURSOR\",\"message\":\"댓글 조회 커서가 올바르지 않습니다.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 게시물을 찾을 수 없음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "사용자 없음", value = "{\"status\":404,\"code\":\"USER_NOT_FOUND\",\"message\":\"사용자를 찾을 수 없습니다.\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "게시물 없음", value = "{\"status\":404,\"code\":\"POST_NOT_FOUND\",\"message\":\"게시글을 찾을 수 없습니다.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ApiResponse<CommentPageResponse> getComments(
		@PathVariable("userId") Long userId,
		@PathVariable("postId") Long postId,
		@Parameter(description = "한 번에 가져올 댓글 수", example = "20") @RequestParam(defaultValue = "20") int limit,
		@Parameter(description = "다음 페이지 조회 커서. 첫 페이지에서는 생략합니다.") @RequestParam(required = false) String cursor
	);
}
