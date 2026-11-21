package org.sopt.post.dto.response;

import java.util.List;

public record CommentPageResponse(
	List<CommentItemResponse> items,
	String nextCursor,
	boolean hasMore
) {
}
