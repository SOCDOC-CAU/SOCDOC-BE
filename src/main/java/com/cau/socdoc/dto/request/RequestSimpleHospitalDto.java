package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class RequestSimpleHospitalDto {

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String address1;

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String address2;

    @DecimalMin(value = "1", message = MessageUtil.LARGER_THAN_ZERO)
    private int pageNum;
}
