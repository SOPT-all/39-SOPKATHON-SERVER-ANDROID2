package org.sopt.post.dto.response;

public record PurchaseLinkResponse(
	Long clothesId,
	String name,
	String category,
	String imageUrl,
	String purchaseUrl
) {
}
