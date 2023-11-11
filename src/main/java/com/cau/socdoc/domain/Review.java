package com.cau.socdoc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    private String userId; // 작성한 유저 ID
    private String hospitalId; // 리뷰남긴 병원 ID
    private String content; // 내용
    private LocalDate createdAt; // 생성일시
    private int rating; // 1 ~ 5

    public static Review of(String userId, String hospitalId, String content, int rating) {
        Review review = new Review();
        review.userId = userId;
        review.hospitalId = hospitalId;
        review.content = content;
        review.createdAt = LocalDate.now();
        review.rating = rating;
        return review;
    }

    public void update(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }
}
