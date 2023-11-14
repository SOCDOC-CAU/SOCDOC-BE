package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CreateUserDto {

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String userId;

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String userName;

    @Email(message = MessageUtil.INVALID_EMAIL_FORMAT)
    private String userEmail;

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String address1; // 서울시

    @NotBlank(message = MessageUtil.NOT_BLANK)
    private String address2; // 동작구
}
