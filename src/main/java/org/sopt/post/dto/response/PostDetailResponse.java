package org.sopt.post.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record PostDetailResponse(
	Long postId,
	FeedUserResponse author,
	FeedUserResponse targetUser,
	String imageUrl,
	String title,
	String description,
	List<OutfitItemResponse> outfitItems,
	List<PostPurchaseLinkResponse> purchaseLinks,
	int likeCount,
	boolean liked,
	int commentCount,
	List<CommentPreviewResponse> commentsPreview,
	OffsetDateTime createdAt
) {
}
