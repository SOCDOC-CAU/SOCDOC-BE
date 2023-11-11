package com.cau.socdoc.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseKakaoDto {

    private Document[] documents;
    private Meta meta;
}
