package org.sopt.post.exception;

import org.sopt.common.exception.BaseException;
import org.sopt.post.code.ErrorCode;

public class UserNotFoundException extends BaseException {
	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
