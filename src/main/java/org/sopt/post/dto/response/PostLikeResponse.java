package org.sopt.post.dto.response;

public record PostLikeResponse(Long postId, boolean liked, long likeCount) {
}
