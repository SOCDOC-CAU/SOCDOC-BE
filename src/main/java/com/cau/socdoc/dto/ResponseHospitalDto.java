package com.cau.socdoc.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseHospitalDto {

    private String dutyTime3s;
    private String dutyTime5s;
    private String dutyTime1s;
    private String dutyAddr;
    private int dutyTime4c;
    private int dutyEryn;
    private int dutyTime6c;
    private String hpid;
    private double wgs84Lon;
    private int dutyTime2c;
    private String dutyTel1;
    private String dutyEmclsName;
    private String dutyTime4s;
    private int rnum;
    private String dutyTime6s;
    private String dutyDivNam;
    private String dutyTime2s;
    private String dutyDiv;
    private String dutyName;
    private int dutyTime3c;
    private int dutyTime5c;
    private int postCdn2;
    private String postCdn1;
    private int dutyTime1c;
    private double wgs84Lat;
    private String dutyEmcls;
}
