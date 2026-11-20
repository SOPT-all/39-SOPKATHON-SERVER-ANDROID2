package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public interface ResultCode {
	HttpStatus getStatus();
	String getMessage();
}
