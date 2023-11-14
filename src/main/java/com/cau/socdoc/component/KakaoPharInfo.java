package com.cau.socdoc.component;

import com.cau.socdoc.dto.response.kakao.ResponseKakaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoPharInfo {

    private final WebClient webClient;

    public ResponseKakaoDto getPharInfo(String token, double x, double y) {
        String X = Double.toString(x);
        String Y = Double.toString(y);
        String url = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=PM9" + "&y=" + X + "&x=" + Y + "&radius=1000";
        log.info(url);
        Flux<ResponseKakaoDto> response = webClient.get()
                .uri(url)
                .header("Authorization", "KakaoAK " + token)
                .retrieve()
                .bodyToFlux(ResponseKakaoDto.class);
        return response.blockFirst();
    }
}