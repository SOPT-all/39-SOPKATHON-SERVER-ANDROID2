package org.sopt.post.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record FeedItemResponse(
	Long postId,
	FeedUserResponse author,
	FeedUserResponse targetUser,
	String imageUrl,
	String title,
	String description,
	List<PurchaseLinkResponse> purchaseLinks,
	int likeCount,
	boolean liked,
	int commentCount,
	OffsetDateTime createdAt
) {
}
