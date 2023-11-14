package com.cau.socdoc.dto.request;

import com.cau.socdoc.util.MessageUtil;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class CreateUserDto {

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String userId;

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String userName;

    @Email(message = MessageUtil.INVALID_EMAIL_FORMAT)
    private String userEmail;

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String address1; // 서울시

    @NotEmpty(message = MessageUtil.NOT_EMPTY)
    private String address2; // 동작구
}
