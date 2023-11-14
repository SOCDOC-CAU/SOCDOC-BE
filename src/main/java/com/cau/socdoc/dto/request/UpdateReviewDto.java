package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateReviewDto {

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String reviewId; // 리뷰 고유 ID

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String content; // 내용

    @DecimalMin(value = "1", message = MessageUtil.ONE_TO_FIVE)
    @DecimalMax(value = "5", message = MessageUtil.ONE_TO_FIVE)
    private int rating; // 1 ~ 5
}
