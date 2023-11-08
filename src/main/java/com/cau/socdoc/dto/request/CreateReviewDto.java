package com.cau.socdoc.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class CreateReviewDto {

    private String userId; // 작성한 유저 ID
    private String hospitalId; // 리뷰남긴 병원 ID
    private String content; // 내용
    private int rating; // 1 ~ 5
    private MultipartFile image; // 사진
}
