package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;

@Getter
@Builder
public class ResponseReviewDto implements Serializable {

    private String reviewId; // 리뷰 고유 ID
    private String name; // 유저명 혹은 병원명
    private String content; // 내용
    private String createdAt; // 생성일
    private int rating; // 1 ~ 5
    private byte[] files; // 사진
}
