package com.cau.socdoc.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserNameDto {

    private String userId;
    private String userName;
}
