package org.sopt.post.controller;

import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.response.FeedResponse;
import org.sopt.post.entity.FeedPeriod;
import org.sopt.post.entity.FeedSort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "피드", description = "동료들의 코디 게시물을 정렬·기간별로 둘러봅니다.")
@RequestMapping(value = "/api/v1/users/{userId}/feed", produces = MediaType.APPLICATION_JSON_VALUE)
public interface FeedApi {
	@GetMapping
	@Operation(summary = "피드 조회", description = "정렬과 기간 조건에 맞는 게시물 목록을 조회합니다. 첫 페이지에서는 cursor를 생략하고, 다음 페이지는 응답의 nextCursor를 사용하세요.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "피드 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(
			mediaType = MediaType.APPLICATION_JSON_VALUE,
			examples = {
				@io.swagger.v3.oas.annotations.media.ExampleObject(
					name = "최신 피드 조회",
					description = "sort=LATEST로 조회했을 때의 예시입니다.",
					value = "{\"status\":200,\"code\":\"FEED_RETRIEVED\",\"message\":\"피드가 성공적으로 조회되었습니다.\",\"data\":{\"items\":[{\"postId\":105,\"author\":{\"userId\":42,\"name\":\"김민수\",\"position\":\"사원\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/spring-look.png\",\"title\":\"출근길 봄 코디\",\"description\":\"가벼운 트렌치코트 코디입니다.\",\"purchaseLinks\":[{\"clothesId\":103,\"name\":\"베이지 트렌치코트\",\"category\":\"상의\",\"imageUrl\":\"https://images.example.com/clothes/trench-coat.png\",\"purchaseUrl\":\"https://shop.example.com/products/coat-123\"}],\"likeCount\":12,\"liked\":false,\"commentCount\":3,\"createdAt\":\"2026-11-22T09:15:00+09:00\"},{\"postId\":103,\"author\":{\"userId\":27,\"name\":\"박지우\",\"position\":\"대리\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/office-look.png\",\"title\":\"차분한 오피스 룩\",\"description\":\"네이비 재킷으로 단정하게 매치했어요.\",\"purchaseLinks\":[{\"clothesId\":87,\"name\":\"네이비 재킷\",\"category\":\"상의\",\"imageUrl\":\"https://images.example.com/clothes/navy-jacket.png\",\"purchaseUrl\":\"https://shop.example.com/products/jacket-87\"}],\"likeCount\":4,\"liked\":false,\"commentCount\":1,\"createdAt\":\"2026-11-22T08:40:00+09:00\"},{\"postId\":101,\"author\":{\"userId\":56,\"name\":\"최하늘\",\"position\":\"주임\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/casual-look.png\",\"title\":\"가벼운 주말 코디\",\"description\":\"편안한 셔츠와 데님 조합입니다.\",\"purchaseLinks\":[{\"clothesId\":65,\"name\":\"스트라이프 셔츠\",\"category\":\"상의\",\"imageUrl\":\"https://images.example.com/clothes/stripe-shirt.png\",\"purchaseUrl\":\"https://shop.example.com/products/shirt-65\"}],\"likeCount\":9,\"liked\":true,\"commentCount\":5,\"createdAt\":\"2026-11-22T07:55:00+09:00\"}],\"nextCursor\":\"TEFURVNUfDZ8MjAyNi0xMS0yMlQwNzo1NXwxMDE\",\"hasMore\":true}}"
				),
				@io.swagger.v3.oas.annotations.media.ExampleObject(
					name = "인기 피드 조회",
					description = "sort=POPULAR로 조회했을 때의 예시입니다.",
					value = "{\"status\":200,\"code\":\"FEED_RETRIEVED\",\"message\":\"피드가 성공적으로 조회되었습니다.\",\"data\":{\"items\":[{\"postId\":99,\"author\":{\"userId\":27,\"name\":\"박지우\",\"position\":\"대리\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/popular-look.png\",\"title\":\"주말 데일리 코디\",\"description\":\"편하게 입기 좋은 데일리 룩입니다.\",\"purchaseLinks\":[{\"clothesId\":76,\"name\":\"블랙 와이드 팬츠\",\"category\":\"하의\",\"imageUrl\":\"https://images.example.com/clothes/wide-pants.png\",\"purchaseUrl\":\"https://shop.example.com/products/pants-76\"}],\"likeCount\":25,\"liked\":true,\"commentCount\":8,\"createdAt\":\"2026-11-22T09:15:00+09:00\"},{\"postId\":97,\"author\":{\"userId\":42,\"name\":\"김민수\",\"position\":\"사원\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/date-look.png\",\"title\":\"저녁 약속 코디\",\"description\":\"부드러운 니트로 포인트를 줬습니다.\",\"purchaseLinks\":[{\"clothesId\":74,\"name\":\"크림색 니트\",\"category\":\"상의\",\"imageUrl\":\"https://images.example.com/clothes/cream-knit.png\",\"purchaseUrl\":\"https://shop.example.com/products/knit-74\"}],\"likeCount\":19,\"liked\":false,\"commentCount\":6,\"createdAt\":\"2026-11-21T18:40:00+09:00\"},{\"postId\":88,\"author\":{\"userId\":56,\"name\":\"최하늘\",\"position\":\"주임\"},\"targetUser\":{\"userId\":12,\"name\":\"이서연\",\"position\":\"대리\"},\"imageUrl\":\"https://images.example.com/posts/weekend-look.png\",\"title\":\"여유로운 주말 룩\",\"description\":\"활동하기 편한 데님 코디입니다.\",\"purchaseLinks\":[{\"clothesId\":61,\"name\":\"스트레이트 데님\",\"category\":\"하의\",\"imageUrl\":\"https://images.example.com/clothes/straight-denim.png\",\"purchaseUrl\":\"https://shop.example.com/products/denim-61\"}],\"likeCount\":14,\"liked\":false,\"commentCount\":4,\"createdAt\":\"2026-11-20T20:30:00+09:00\"}],\"nextCursor\":\"UE9QVUxBUnwxNHwyMDI2LTExLTIwVDIwOjMwfDg4\",\"hasMore\":true}}"
				)
			}
		)),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "sort, period, limit 또는 cursor 값이 올바르지 않음", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 정렬값", value = "{\"status\":400,\"code\":\"INVALID_TYPE_VALUE\",\"message\":\"요청 파라미터 타입 불일치\",\"data\":null}"), @io.swagger.v3.oas.annotations.media.ExampleObject(name = "잘못된 cursor", value = "{\"status\":400,\"code\":\"INVALID_CURSOR\",\"message\":\"페이지 커서가 올바르지 않습니다.\",\"data\":null}")})),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음 (USER_NOT_FOUND)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "사용자 없음", value = "{\"status\":404,\"code\":\"USER_NOT_FOUND\",\"message\":\"사용자를 찾을 수 없습니다.\",\"data\":null}"))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "예상하지 못한 서버 오류", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "서버 오류", value = "{\"status\":500,\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"예상하지 못한 서버 에러\",\"data\":null}")))
	})
	ApiResponse<FeedResponse> getFeed(
		@PathVariable("userId") Long userId,
		@Parameter(description = "정렬 기준: LATEST(최신순), POPULAR(인기순)", example = "LATEST") @RequestParam(defaultValue = "LATEST") FeedSort sort,
		@Parameter(description = "조회 기간 -> 생략하면 전체 기간이며 WEEK는 최근 7일입니다.", example = "WEEK") @RequestParam(required = false) FeedPeriod period,
		@Parameter(description = "한 번에 가져올 게시물 수", example = "20") @RequestParam(defaultValue = "20") int limit,
		@Parameter(description = "다음 페이지 조회 커서 -> 첫 페이지에서는 생략합니다.") @RequestParam(required = false) String cursor
	);
}
