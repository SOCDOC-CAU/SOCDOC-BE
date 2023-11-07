package com.cau.socdoc.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateReviewDto {

    private String reviewId; // 리뷰 고유 ID
    private String content; // 내용
    private int rating; // 1 ~ 5
}
