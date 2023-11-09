package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUserInfoDto {

    private String userId;
    private String userName;
    private String userEmail;
    private String address1; // 서울시
    private String address2; // 동작구
}
