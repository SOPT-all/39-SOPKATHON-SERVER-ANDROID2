package org.sopt.post.exception;

import org.sopt.common.exception.BaseException;
import org.sopt.post.code.ErrorCode;

public class AlreadyLikedException extends BaseException {
	public AlreadyLikedException() {
		super(ErrorCode.ALREADY_LIKED);
	}
}
