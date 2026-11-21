package org.sopt.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.response.FeedResponse;
import org.sopt.post.entity.FeedPeriod;
import org.sopt.post.entity.FeedSort;
import org.sopt.post.service.FeedService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/feed")
@RequiredArgsConstructor
@Validated
public class FeedController {
	private final FeedService feedService;

	@GetMapping
	public ApiResponse<FeedResponse> getFeed(
		@PathVariable("userId") Long userId,
		@RequestParam(defaultValue = "LATEST") FeedSort sort,
		@RequestParam(required = false) FeedPeriod period,
		@RequestParam(defaultValue = "20") int limit,
		@RequestParam(required = false) String cursor
	) {
		return ApiResponse.success(
			SuccessCode.FEED_RETRIEVED,
			feedService.getFeed(userId, sort, period, limit, cursor)
		);
	}
}
