package com.hada.api.exception;

public class HadaEncryptException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public int httpStatus;
	
	public HadaEncryptException() {
        super("암/복호화를 실패하였습니다...");
        this.httpStatus = HadaApiErrorCode.SC_INTERNAL_SERVER_ERROR;
    }

	public HadaEncryptException(String message, HadaApiErrorCode httpStatus) {
        super(message);
        this.httpStatus = httpStatus.getCode();
    }

	public int getHttpStatus() {
		return httpStatus;
	}
	
}
