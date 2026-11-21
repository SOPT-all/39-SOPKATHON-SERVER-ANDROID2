package org.sopt.user.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record UserProfileResponse(
	Long userId,
	String name,
	String position,
	String department,
	String profileImageUrl,
	long postCount,
	long suggestedOutfitCount,
	List<ReceivedOutfitResponse> receivedOutfits
) {
	public record ReceivedOutfitResponse(
		Long postId,
		String title,
		String imageUrl,
		String description,
		LocalDateTime createdAt
	) {
	}
}
