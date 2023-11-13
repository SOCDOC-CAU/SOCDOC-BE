package com.cau.socdoc.dto.response.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SameName {

    private String[] region;
    private String keyword;
    private String selected_region;
}
