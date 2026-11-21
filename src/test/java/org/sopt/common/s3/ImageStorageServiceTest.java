package org.sopt.common.s3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.sopt.common.config.S3Properties;
import org.sopt.common.s3.dto.ImageUpload;
import org.sopt.common.s3.service.ImageStorageService;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

class ImageStorageServiceTest {

	@Test
	void createsSignedUploadAndPublicImageUrls() {
		try (S3Presigner presigner = S3Presigner.builder()
			.region(Region.AP_NORTHEAST_2)
			.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("test-key", "test-secret")))
			.build()) {
			ImageStorageService service = new ImageStorageService(
				presigner, new S3Properties("sopt39sopkathon", "ap-northeast-2"));

			ImageUpload first = service.createUpload("image/svg+xml");
			ImageUpload second = service.createUpload("image/svg+xml");

			assertNotEquals(first.key(), second.key());
			assertTrue(first.key().startsWith("images/"));
			assertEquals("https://sopt39sopkathon.s3.ap-northeast-2.amazonaws.com/" + first.key(), first.imageUrl());
			assertEquals(first.imageUrl(), service.getImageUrl(first.key()));
			assertTrue(first.uploadUrl().startsWith(first.imageUrl() + "?"));
			assertTrue(first.uploadUrl().contains("X-Amz-Expires=300"));
			assertTrue(first.uploadUrl().contains("X-Amz-SignedHeaders="));
			assertEquals("image/svg+xml", first.contentType());
		}
	}

	@Test
	void rejectsNonImageContentTypesAndInvalidKeys() {
		ImageStorageService service = new ImageStorageService(null, new S3Properties("bucket", "ap-northeast-2"));

		for (String contentType : new String[] {"text/plain", "image/*", "image/", ""}) {
			assertThrows(IllegalArgumentException.class, () -> service.createUpload(contentType));
		}
		assertThrows(IllegalArgumentException.class, () -> service.createUpload(null));
		assertThrows(IllegalArgumentException.class, () -> service.getImageUrl("other/file"));
	}
}
