package com.cau.socdoc.util.exception;

import com.cau.socdoc.util.api.ResponseCode;

public class HospitalException extends BaseException {
    public HospitalException(ResponseCode responseCode) {
        super(responseCode);
    }
}
