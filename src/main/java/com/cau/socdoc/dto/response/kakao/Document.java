package com.cau.socdoc.dto.response.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Document {

    private String address_name;
    private String category_group_code;
    private String category_group_name;
    private String category_name;
    private String distance;
    private String id;
    private String phone;
    private String place_name;
    private String place_url;
    private String road_address_name;
    private String x;
    private String y;
}
