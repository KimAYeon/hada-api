package com.hada.api.exception;

import org.apache.http.HttpStatus;

public enum HadaApiErrorCode implements HttpStatus {
	
    SELF_REQUEST(422),
	EXISTED_EMAIL(423),
	INVALID_TOKEN(424),
	FAIL_ENCRYPT(425),
	FAIL_DECRYPT(426),
	FAIL_UPLODAD_PROFILE(427),
	NOT_GET_ORIENTATION(428);

    private int code;

    HadaApiErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
