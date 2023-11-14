package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateUserAddressDto {

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String userId;

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String address1;

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String address2;
}
