package org.sopt.post.dto.response;

import java.util.List;

public record FeedResponse(
	List<FeedItemResponse> items,
	String nextCursor,
	boolean hasMore
) {
}
