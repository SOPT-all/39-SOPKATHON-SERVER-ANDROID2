package org.sopt.clothes.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.ResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResultCode {
	CLOTHES_CREATED(HttpStatus.CREATED, "옷 추가에 성공했습니다.");

	private final HttpStatus status;
	private final String message;
}
