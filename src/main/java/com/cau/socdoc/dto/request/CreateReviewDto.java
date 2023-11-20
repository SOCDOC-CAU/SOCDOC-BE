package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateReviewDto {

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String userId; // 작성한 유저 ID

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String hospitalId; // 리뷰남긴 병원 ID

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String content; // 내용

    @Range(min = 1, max = 5, message = MessageUtil.ONE_TO_FIVE)
    private int rating; // 1 ~ 5

    @Nullable
    private MultipartFile image; // 사진
}
