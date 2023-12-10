package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseSimpleHospitalDto {

    private String hpid;
    private String name;
    private String address;
    private double rating;
    private double latitude;
    private double longitude;
}
