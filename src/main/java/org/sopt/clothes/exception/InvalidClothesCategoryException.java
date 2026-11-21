package org.sopt.clothes.exception;

import org.sopt.clothes.code.ErrorCode;
import org.sopt.common.exception.BaseException;

public class InvalidClothesCategoryException extends BaseException {
	public InvalidClothesCategoryException() {
		super(ErrorCode.INVALID_CLOTHES_CATEGORY);
	}
}
