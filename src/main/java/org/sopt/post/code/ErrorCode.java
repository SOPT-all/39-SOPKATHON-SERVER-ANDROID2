package org.sopt.post.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.ResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResultCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
	INVALID_COMMENT(HttpStatus.BAD_REQUEST, "댓글은 1~500자로 입력해 주세요."),
	ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다.");

	private final HttpStatus status;
	private final String message;
}
