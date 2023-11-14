package com.cau.socdoc.dto.response.kakao;

import com.cau.socdoc.dto.response.kakao.Document;
import com.cau.socdoc.dto.response.kakao.Meta;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseKakaoDto {

    private Document[] documents;
    private Meta meta;
}
