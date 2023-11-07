package com.cau.socdoc.util.exception;

import com.cau.socdoc.util.api.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ApiResponse<Void> handleUserException(UserException e) {
        return ApiResponse.fail(e.getResponseCode());
    }

    @ExceptionHandler(ReviewException.class)
    public ApiResponse<Void> handleReviewException(ReviewException e) {
        return ApiResponse.fail(e.getResponseCode());
    }

    @ExceptionHandler(HospitalException.class)
    public ApiResponse<Void> handleHospitalException(HospitalException e) {
        return ApiResponse.fail(e.getResponseCode());
    }
}
