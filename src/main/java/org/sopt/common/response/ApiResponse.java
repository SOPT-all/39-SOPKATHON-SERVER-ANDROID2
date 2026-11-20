package org.sopt.common.response;

import org.sopt.common.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
	private final int status;
	private final String code;
	private final String message;
	private final T data;

	public static <T> ApiResponse<T> success(ResultCode code, T data) {
		return new ApiResponse<>(
			code.getStatus().value(),
			code.toString(),
			code.getMessage(),
			data
		);
	}

	public static ApiResponse<Void> success(ResultCode code) {
		return success(code, null);
	}

	public static ApiResponse<Void> failure(ResultCode code) {
		return new ApiResponse<>(
			code.getStatus().value(),
			code.toString(),
			code.getMessage(),
			null
		);
	}
}
