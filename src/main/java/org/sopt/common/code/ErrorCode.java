package org.sopt.common.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResultCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상하지 못한 서버 에러"),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "@Valid 검증 실패"),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "요청 파라미터 타입 불일치"),
	MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "필수 파라미터 누락"),
	INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "JSON 파싱 실패, 필수 Body 누락"),
	VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "메서드 파라미터 검증 실패"),
	CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "Bean Validation 제약 조건 위반"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 경로 또는 정적 리소스"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	CLOTHES_NOT_FOUND(HttpStatus.NOT_FOUND, "옷 정보를 찾을 수 없습니다."),
	INVALID_POST_TARGET(HttpStatus.BAD_REQUEST, "공개 범위에 맞는 대상 사용자 정보를 입력해 주세요."),
	METHOD_NOT_ALLOWED(HttpStatus.UNAUTHORIZED, "지원하지 않는 HTTP 메서드"),
	MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "필수 요청 헤더 누락"),
    INVALID_IMAGE_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "이미지 Content-Type이 올바르지 않습니다"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "요청 인자가 올바르지 않습니다"),
    INVALID_CURSOR(HttpStatus.BAD_REQUEST, "페이지 커서가 올바르지 않습니다.");


    private final HttpStatus status;
	private final String message;
}
