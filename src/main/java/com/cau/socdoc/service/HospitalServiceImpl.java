package com.cau.socdoc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HospitalServiceImpl implements HospitalService {
    // 병원 목록 조회
    @Value("${LIST_URL}")
    private String LIST_URL;

    // 특정 병원 상세정보 조회
    @Value("${DETAIL_URL}")
    private String DETAIL_URL;

    // 특정 병원 위치 조회
    @Value("${DETAIL_POS_URL}")
    private String DETAIL_POS_URL;


    /* openAPI 데이터 가져와서 firebase에 저장: (1) 병원 목록 조회
    public void findHospitalByAddress() throws IOException {

        for(int i=1; i<=1868; i++) {
            log.info("pageNo" + i + " 조회 시작");
            HttpURLConnection conn = (HttpURLConnection) new URL(LIST_URL + "&Q0=" + "서울" + "&pageNo=" + i).openConnection();
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
            // log.info(jsonPrettyPrintString);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonPrettyPrintString);
            JsonNode items = jsonNode.get("response").get("body").get("items").get("item");

            // firebase에 저장
            log.info("pageNo" + i + " firebase에 저장 시작");
            Firestore db = FirestoreClient.getFirestore();

            for (JsonNode item : items) {
                Hospital hospital = objectMapper.readValue(item.toString(), new TypeReference<>() {});
                db.collection("hospital").document(hospital.getHpid()).set(hospital);
            }
            log.info("pageNo" + i + " firebase에 저장 완료");
        }
    }*/
}
