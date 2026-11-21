package org.sopt.common.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResultCode {
	PRESIGNED_URL_ISSUED(HttpStatus.OK, "Presigned URL 발급 성공"),
	POST_CREATED(HttpStatus.CREATED, "게시물이 성공적으로 생성되었습니다.");

	private final HttpStatus status;
	private final String message;
}
