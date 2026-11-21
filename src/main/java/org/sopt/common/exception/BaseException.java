package org.sopt.common.exception;

import org.sopt.common.code.ErrorCode;

import lombok.Getter;
import org.sopt.common.code.ResultCode;

@Getter
public class BaseException extends RuntimeException {
	private final ResultCode errorCode;

	public BaseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
