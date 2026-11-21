package org.sopt.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;
import org.sopt.common.response.ApiResponse;
import org.sopt.post.dto.response.FeedResponse;
import org.sopt.post.entity.FeedPeriod;
import org.sopt.post.entity.FeedSort;
import org.sopt.post.service.FeedService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController implements FeedApi {
	private final FeedService feedService;

	public ApiResponse<FeedResponse> getFeed(
		Long userId,
		FeedSort sort,
		FeedPeriod period,
		int limit,
		String cursor
	) {
		return ApiResponse.success(
			SuccessCode.FEED_RETRIEVED,
			feedService.getFeed(userId, sort, period, limit, cursor)
		);
	}
}
