package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CreateReviewDto {

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String userId; // 작성한 유저 ID

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String hospitalId; // 리뷰남긴 병원 ID

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String content; // 내용


    private int rating; // 1 ~ 5

    private MultipartFile image; // 사진
}
