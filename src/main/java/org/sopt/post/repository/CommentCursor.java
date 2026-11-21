package org.sopt.post.repository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

import org.sopt.common.exception.BaseException;
import org.sopt.post.code.ErrorCode;

public record CommentCursor(LocalDateTime createdAt, Long commentId) {
	public static CommentCursor decode(String value) {
		try {
			String decoded = new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
			String[] parts = decoded.split("\\|", -1);
			if (parts.length != 2) {
				throw new IllegalArgumentException();
			}
			return new CommentCursor(LocalDateTime.parse(parts[0]), Long.parseLong(parts[1]));
		} catch (RuntimeException exception) {
			throw new BaseException(ErrorCode.INVALID_CURSOR);
		}
	}

	public static String encode(LocalDateTime createdAt, Long commentId) {
		String value = "%s|%d".formatted(createdAt, commentId);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
	}
}
