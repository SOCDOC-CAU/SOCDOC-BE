package com.cau.socdoc.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSimpleHospitalDto {

    private String address1;
    private String address2;
    private int pageNum;
}
