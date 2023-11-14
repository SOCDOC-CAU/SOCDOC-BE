package com.cau.socdoc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private String userId;
    private String userName;
    private String userEmail;
    private String address1; // 서울시
    private String address2; // 동작구

    public static User of(String userId, String userName, String userEmail, String address1, String address2) {
        User user = new User();
        user.userId = userId;
        user.userName = userName;
        user.userEmail = userEmail;
        user.address1 = address1;
        user.address2 = address2;
        return user;
    }
}
