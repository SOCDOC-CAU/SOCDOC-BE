package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CreateReviewDto implements Serializable {

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String userId; // 작성한 유저 ID

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String hospitalId; // 리뷰남긴 병원 ID

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String content; // 내용

    //@Range(min = 1, max = 5, message = MessageUtil.ONE_TO_FIVE)
    private String rating; // 별점

    private MultipartFile files; // 사진
}
