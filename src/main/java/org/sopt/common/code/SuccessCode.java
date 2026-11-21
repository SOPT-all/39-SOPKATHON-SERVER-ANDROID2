package org.sopt.common.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResultCode {
	PRESIGNED_URL_ISSUED(HttpStatus.OK, "Presigned URL 발급 성공"),
	USER_CREATED(HttpStatus.OK, "프로필이 생성되었습니다."),
	TARGET_USER_FOUND(HttpStatus.OK, "동료 프로필 조회에 성공했습니다."),
	POST_CREATED(HttpStatus.CREATED, "게시물이 성공적으로 생성되었습니다.");

	private final HttpStatus status;
	private final String message;
}
