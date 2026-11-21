package org.sopt.common.s3;

public record ImageUpload(String key, String uploadUrl, String imageUrl, String contentType) {
}
