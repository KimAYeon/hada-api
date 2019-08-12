package com.hada.api.exception;

public class HadaBadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public int httpStatus;

	public HadaBadRequestException(String message, HadaApiErrorCode httpStatus) {
        super(message);
        this.httpStatus = httpStatus.getCode();
    }

	public int getHttpStatus() {
		return httpStatus;
	}
	
}
