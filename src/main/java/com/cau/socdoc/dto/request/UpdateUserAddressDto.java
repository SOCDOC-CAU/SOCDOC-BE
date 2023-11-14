package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UpdateUserAddressDto {

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String userId;

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String address1;

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String address2;
}
