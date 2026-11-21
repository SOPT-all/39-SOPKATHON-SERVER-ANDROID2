package org.sopt.post.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.ResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResultCode {
	SUCCESS(HttpStatus.CREATED, "좋아요 등록에 성공했습니다.");

	private final HttpStatus status;
	private final String message;
}
