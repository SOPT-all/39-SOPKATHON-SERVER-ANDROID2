package org.sopt.post.repository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.post.entity.FeedSort;

public record FeedCursor(FeedSort sort, long likeCount, LocalDateTime createdAt, Long postId) {
	public static FeedCursor decode(String value, FeedSort expectedSort) {
		try {
			String decoded = new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
			String[] parts = decoded.split("\\|", -1);
			if (parts.length != 4 || !FeedSort.valueOf(parts[0]).equals(expectedSort)) {
				throw new IllegalArgumentException();
			}
			return new FeedCursor(
				FeedSort.valueOf(parts[0]),
				Long.parseLong(parts[1]),
				LocalDateTime.parse(parts[2]),
				Long.parseLong(parts[3])
			);
		} catch (RuntimeException exception) {
			throw new BaseException(ErrorCode.INVALID_CURSOR);
		}
	}

	public static String encode(FeedSort sort, long likeCount, LocalDateTime createdAt, Long postId) {
		String value = "%s|%d|%s|%d".formatted(sort.name(), likeCount, createdAt, postId);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
	}
}
