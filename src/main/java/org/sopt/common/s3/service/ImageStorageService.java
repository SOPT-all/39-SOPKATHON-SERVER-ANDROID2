package org.sopt.common.s3.service;

import java.time.Duration;
import java.util.UUID;

import org.sopt.common.s3.dto.ImageUpload;
import org.sopt.common.s3.exception.InvalidImageContentTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.sopt.common.config.S3Properties;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class ImageStorageService {

	private static final Duration UPLOAD_URL_EXPIRATION = Duration.ofMinutes(5);
	private static final String IMAGE_PREFIX = "images/";

	private final S3Presigner presigner;
	private final S3Properties properties;

	public ImageStorageService(S3Presigner presigner, S3Properties properties) {
		this.presigner = presigner;
		this.properties = properties;
	}

	public ImageUpload createUpload(String contentType) {
		MediaType mediaType;
		try {
			mediaType = MediaType.parseMediaType(contentType);
		} catch (RuntimeException exception) {
			throw new InvalidImageContentTypeException(exception);
		}
		if (!"image".equalsIgnoreCase(mediaType.getType()) || mediaType.isWildcardSubtype()) {
			throw new InvalidImageContentTypeException();
		}

		String key = IMAGE_PREFIX + UUID.randomUUID();
		PutObjectRequest putRequest = PutObjectRequest.builder()
			.bucket(properties.bucket())
			.key(key)
			.build();
		PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
			.signatureDuration(UPLOAD_URL_EXPIRATION)
			.putObjectRequest(putRequest)
			.build();

		return new ImageUpload(
			key,
			presigner.presignPutObject(presignRequest).url().toString(),
			getImageUrl(key),
			contentType
		);
	}

	public String getImageUrl(String key) {
		if (key == null || key.isBlank() || !key.startsWith(IMAGE_PREFIX) || key.contains("..")) {
			throw new IllegalArgumentException("Invalid image key");
		}
		return "https://%s.s3.%s.amazonaws.com/%s".formatted(
			properties.bucket(), properties.region(), key);
	}
}
