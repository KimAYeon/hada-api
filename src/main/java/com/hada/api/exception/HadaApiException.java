package com.hada.api.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
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
