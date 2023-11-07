package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ResponseReviewDto {

    private String reviewId; // 리뷰 고유 ID
    private String userName; // 유저명
    private LocalDate createdAt; // 생성일
    private int rating; // 1 ~ 5
}
