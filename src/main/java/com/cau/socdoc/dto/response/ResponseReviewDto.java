package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

@Getter
@Builder
public class ResponseReviewDto {

    private String reviewId; // 리뷰 고유 ID
    private String userName; // 유저명
    private String content; // 내용
    private String createdAt; // 생성일
    private int rating; // 1 ~ 5
    @Nullable
    private MultipartFile image; // 사진
}
