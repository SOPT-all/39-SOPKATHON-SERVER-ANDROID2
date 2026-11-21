package org.sopt.common.s3.dto;

public record ImageUpload(String key, String uploadUrl, String imageUrl, String contentType) {
}
