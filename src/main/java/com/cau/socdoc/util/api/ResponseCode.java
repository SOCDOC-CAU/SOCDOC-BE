package com.cau.socdoc.util.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {

    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 요청입니다."),

    // 403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, false, "권한이 없습니다."),

    // 404 Not Found
    HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, false, "병원을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "유저를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, false, "리뷰를 찾을 수 없습니다."),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, false, "허용되지 않은 메소드입니다."),

    // 409 Conflict

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버에 오류가 발생하였습니다."),
    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "이미지 업로드에 실패하였습니다."),

    // 200 OK
    HOSPITAL_READ_SUCCESS(HttpStatus.OK, true, "병원 정보 조회 성공"),
    USER_READ_SUCCESS(HttpStatus.OK, true, "유저 정보 조회 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, true, "유저 정보 수정 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, true, "유저 정보 삭제 성공"),
    REVIEW_READ_SUCCESS(HttpStatus.OK, true, "리뷰 정보 조회 성공"),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, true, "리뷰 정보 수정 성공"),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, true, "리뷰 정보 삭제 성공"),

    // 201 Created
    USER_CREATE_SUCCESS(HttpStatus.CREATED, true, "유저 생성 성공"),
    REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, true, "리뷰 생성 성공");

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
