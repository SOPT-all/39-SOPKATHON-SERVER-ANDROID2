package org.sopt.clothes.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.ResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResultCode {
	INVALID_CLOTHES_CATEGORY(HttpStatus.BAD_REQUEST, "올바르지 않은 옷 카테고리입니다.");

	private final HttpStatus status;
	private final String message;
}
