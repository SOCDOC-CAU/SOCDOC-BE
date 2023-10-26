package com.cau.socdoc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseHospitalDto {

    private int rnum;
    private String dutyAddr;
    private String dutyDiv;
    private String dutyDivNam;
    private String dutyEmcls;
    private String dutyEmclsName;
    private int dutyEryn;
    private String dutyEtc;
    private String dutyInf;
    private String dutyMapimg;

    private String dutyName;
    private String dutyTel1;
    private String dutyTel3;

    private int dutyTime1c;
    private int dutyTime2c;
    private int dutyTime3c;
    private int dutyTime4c;
    private int dutyTime5c;
    private int dutyTime6c;
    private int dutyTime7c;
    private int dutyTime8c;

    private String dutyTime1s;
    private String dutyTime2s;
    private String dutyTime3s;
    private String dutyTime4s;
    private String dutyTime5s;
    private String dutyTime6s;
    private String dutyTime7s;
    private String dutyTime8s;

    private String hpid;
    private String postCdn1;
    private int postCdn2;
    private double wgs84Lon;
    private double wgs84Lat;
}
