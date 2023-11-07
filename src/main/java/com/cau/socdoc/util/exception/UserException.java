package com.cau.socdoc.util.exception;

import com.cau.socdoc.util.api.ResponseCode;

public class UserException extends BaseException{
    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
