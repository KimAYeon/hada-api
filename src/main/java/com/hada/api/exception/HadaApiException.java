package com.hada.api.exception;

public class HadaApiException {

	private int errorCode;
	private String message;
	
	public HadaApiException(String message) {
        this.message = message;
    }
	
	public HadaApiException(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
	
	public String getMessage() {
		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
