package com.cau.socdoc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    private String userId;
    private String hospitalId;
    private String createdAt;

    public static Like of(String userId, String hospitalId) {
        Like like = new Like();
        like.userId = userId;
        like.hospitalId = hospitalId;
        like.createdAt = LocalDateTime.now().toString();
        return like;
    }
}
