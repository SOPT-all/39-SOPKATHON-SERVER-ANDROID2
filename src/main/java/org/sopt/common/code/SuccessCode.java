package org.sopt.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResultCode {
    // 게시물
    POST_CREATED(HttpStatus.CREATED, "게시물이 성공적으로 생성되었습니다.");

    private final HttpStatus status;
    private final String message;
}
