package org.sopt.post.dto.response;

public record FeedUserResponse(
	Long userId,
	String name,
	String position
) {
}
