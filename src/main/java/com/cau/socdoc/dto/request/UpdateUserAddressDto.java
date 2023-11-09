package com.cau.socdoc.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserAddressDto {

    private String userId;
    private String address1;
    private String address2;
}
