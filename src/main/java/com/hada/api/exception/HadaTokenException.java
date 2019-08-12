package com.hada.api.exception;

public class HadaTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public int httpStatus;

	public HadaTokenException(String message, HadaApiErrorCode httpStatus) {
        super(message);
        this.httpStatus = httpStatus.getCode();
    }

	public int getHttpStatus() {
		return httpStatus;
	}
	
}
