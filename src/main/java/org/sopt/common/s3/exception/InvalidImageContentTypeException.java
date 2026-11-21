package org.sopt.common.s3.exception;

public class InvalidImageContentTypeException extends IllegalArgumentException {

	public InvalidImageContentTypeException() {
		super("Content-Type must be an image media type");
	}

	public InvalidImageContentTypeException(Throwable cause) {
		super("Content-Type must be an image media type", cause);
	}
}
