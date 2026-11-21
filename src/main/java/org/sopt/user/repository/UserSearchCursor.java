package org.sopt.user.repository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public record UserSearchCursor(long userId) {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static UserSearchCursor decode(String value) {
		try {
			byte[] decoded = Base64.getDecoder().decode(value);
			JsonNode node = OBJECT_MAPPER.readTree(new String(decoded, StandardCharsets.UTF_8));
			if (node == null || !node.isObject() || node.size() != 1 ||
				!node.has("userId") || !node.get("userId").canConvertToLong() ||
				node.get("userId").longValue() <= 0) {
				throw new IllegalArgumentException();
			}
			return new UserSearchCursor(node.get("userId").longValue());
		} catch (Exception exception) {
			throw new BaseException(ErrorCode.INVALID_CURSOR);
		}
	}

	public static String encode(long userId) {
		String json = "{\"userId\":%d}".formatted(userId);
		return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
	}
}
