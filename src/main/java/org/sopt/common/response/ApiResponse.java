package org.sopt.common.response;

import org.sopt.common.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@RequiredArgsConstructor
@Schema(description = "모든 API에서 공통으로 사용하는 응답 형식")
public class ApiResponse<T> {
	@Schema(description = "HTTP 상태 코드. 성공 응답은 실제 HTTP 상태 코드, 오류 응답은 4xx 또는 5xx 상태 코드입니다.")
	private final int status;
	@Schema(description = "성공 또는 오류 코드. 예: USER_SEARCHED, INVALID_INPUT_VALUE")
	private final String code;
	@Schema(description = "결과 또는 오류 안내 메시지")
	private final String message;
	@Schema(description = "요청 결과 데이터. 오류 응답에서는 null입니다.")
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
