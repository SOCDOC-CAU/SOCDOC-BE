package com.cau.socdoc.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Hospital {

    private int rnum; // 순번
    private String dutyAddr; // 주소
    private String dutyDiv; // 병원분류
    private String dutyDivNam; // 병원분류명
    private String dutyEmcls; // 응급의료기관 코드
    private String dutyEmclsName; // 응급의료기관 코드명
    private int dutyEryn; // 응급실운영여부 (1 또는 2)
    private String dutyEtc; // 비고
    private String dutyMapimg; // 간이약도
    private String dutyInf; // 기관설명상세
    private String hpid; // 기관ID

    private String dutyName; // 기관명
    private String dutyTel1; // 대표전화1
    private String dutyTel3; // 응급실전화

    // 각 요일별 진료시각/종료 시각, 8은 공휴일 기준
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

    private String postCdn1; // 우편번호1
    private int postCdn2; // 우편번호2
    private double wgs84Lon; // 병원경도
    private double wgs84Lat; // 병원위도
    private List<String> dgidIdName; // 진료과목
}
