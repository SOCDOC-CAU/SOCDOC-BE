package com.cau.socdoc.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseDetailHospitalDto {

    private String hpid;
    private String name;
    private String phone;
    private String address;
    private String description;
    private int likeCount;
    private boolean userLiked;
    private List<String> time;
}
