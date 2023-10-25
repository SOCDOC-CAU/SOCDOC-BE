package com.cau.socdoc.service;

import com.cau.socdoc.dto.RequestAddressDto;
import com.cau.socdoc.dto.ResponseHospitalDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
public class HospitalService {

    // 병원 목록 조회
    @Value("${LIST_URL}")
    private String LIST_URL;

    // 특정 병원 상세정보 조회
    @Value("${DETAIL_URL}")
    private String DETAIL_URL;

    // 특정 병원 위치 조회
    @Value("${DETAIL_POS_URL}")
    private String DETAIL_POS_URL;

    // 단순 시-구 입력받아 병원 목록 조회
    public List<ResponseHospitalDto> findHospitalByAddress(RequestAddressDto requestAddressDto) throws IOException {
        String address1 = URLEncoder.encode(requestAddressDto.getAddress1(), "UTF-8");
        String address2 = URLEncoder.encode(requestAddressDto.getAddress2(), "UTF-8");
        HttpURLConnection conn = (HttpURLConnection) new URL(LIST_URL + "&Q0=" + address1 + "&Q1=" + address2).openConnection();
        conn.connect();
        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
        StringBuilder st = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            st.append(line);
        }

        JSONObject xmlJSONObj = XML.toJSONObject(st.toString());
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
        log.info(jsonPrettyPrintString);

        // JSON에서 필요한 정보만 추출하는 로직 작성 필요


        return null;
    }

}
