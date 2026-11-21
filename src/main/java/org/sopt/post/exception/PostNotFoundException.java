package org.sopt.post.exception;

import org.sopt.common.exception.BaseException;
import org.sopt.post.code.ErrorCode;

public class PostNotFoundException extends BaseException {
	public PostNotFoundException() {
		super(ErrorCode.POST_NOT_FOUND);
	}
}
