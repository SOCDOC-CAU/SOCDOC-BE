package com.cau.socdoc.util.exception;

import com.cau.socdoc.util.api.ResponseCode;

public class ReviewException extends BaseException {
    public ReviewException(ResponseCode responseCode) {
        super(responseCode);
    }
}
