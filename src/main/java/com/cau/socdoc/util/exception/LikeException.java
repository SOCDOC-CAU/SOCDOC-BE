package com.cau.socdoc.util.exception;

import com.cau.socdoc.util.api.ResponseCode;

public class LikeException extends BaseException{
    public LikeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
