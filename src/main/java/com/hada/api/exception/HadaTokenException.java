package com.hada.api.exception;

public class HadaTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public int httpStatus;
	
	public HadaTokenException() {
        super("유효하지 않은 토큰 값입니다...");
        this.httpStatus = HadaApiErrorCode.SC_INTERNAL_SERVER_ERROR;
    }

	public HadaTokenException(String message, HadaApiErrorCode httpStatus) {
        super(message);
        this.httpStatus = httpStatus.getCode();
    }

	public int getHttpStatus() {
		return httpStatus;
	}
	
}
