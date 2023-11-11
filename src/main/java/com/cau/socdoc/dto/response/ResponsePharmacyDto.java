package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponsePharmacyDto {

    private String name;
    private String address;
}
