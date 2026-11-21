package org.sopt.common.exception;

import org.sopt.common.code.ResultCode;

import lombok.Getter;
import org.sopt.common.code.ResultCode;

@Getter
public class BaseException extends RuntimeException {
	private final ResultCode errorCode;

	public BaseException(ResultCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
