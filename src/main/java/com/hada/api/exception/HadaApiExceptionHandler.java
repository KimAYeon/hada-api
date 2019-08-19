package com.hada.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HadaApiExceptionHandler {
	
	@ExceptionHandler(HadaBadRequestException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public HadaApiException handleHadaApiException(HadaBadRequestException exception) {
		return new HadaApiException(exception.getMessage(), exception.getHttpStatus());
	}
	
	@ExceptionHandler(HadaTokenException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public HadaApiException handleHadaTokenException(HadaTokenException exception) {
		return new HadaApiException(exception.getMessage(), exception.getHttpStatus());
	}
	
	@ExceptionHandler(HadaEncryptException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public HadaApiException handleHadaEncryptException(HadaEncryptException exception) {
		return new HadaApiException(exception.getMessage(), exception.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public HadaApiException handleException(Exception exception) {
		return new HadaApiException(exception.getMessage(), 500);
	}
}
