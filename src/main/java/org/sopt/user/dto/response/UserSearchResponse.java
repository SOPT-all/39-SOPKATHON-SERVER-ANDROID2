package org.sopt.user.dto.response;

import java.util.List;

public record UserSearchResponse(
	List<UserItem> items,
	String nextCursor,
	boolean hasMore
) {
	public record UserItem(
		Long userId,
		String name,
		String position,
		String department,
		String profileImageUrl
	) {
	}
}
