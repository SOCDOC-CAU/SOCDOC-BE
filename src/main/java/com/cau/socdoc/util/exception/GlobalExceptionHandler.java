package com.cau.socdoc.util.exception;

import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    @ExceptionHandler(LikeException.class)
    public ApiResponse<Void> handleLikeException(LikeException e) {
        return ApiResponse.fail(e.getResponseCode());
    }

    @ExceptionHandler(ExecutionException.class)
    public ApiResponse<Void> handleExecutionException(ExecutionException e) {
        return ApiResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InterruptedException.class)
    public ApiResponse<Void> handleInterruptedException(InterruptedException e) {
        return ApiResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // 요청의 유효성 검사 실패 시
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request로 응답 반환
    public ApiResponse<Map<String, String>> handleInValidRequestException(MethodArgumentNotValidException e) {
        // 에러가 발생한 객체 내 필드와 대응하는 에러 메시지를 map에 저장하여 반환
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.fail(ResponseCode.BAD_REQUEST, errors);
    }
}
