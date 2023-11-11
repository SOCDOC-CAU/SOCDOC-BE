package com.cau.socdoc.service;

import org.springframework.beans.factory.annotation.Value;

public class OpenApiService {

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

    /* openAPI 데이터 가져와서 firebase에 저장: (2) 병원 진료과목 매핑
    public void changeHospital() throws ExecutionException, InterruptedException {
        List<String> hospitals = hospitalRepository.findAllHospital();
        Firestore db = FirestoreClient.getFirestore();

        for(String id: hospitals){
            try {
                log.info("hospitalId: " + id + " 주소 파싱 시작");
                String type = db.collection("hospital").document(id).get().get().getString("dutyAddr");
                // 진료과목 문자열 ','로 배열로 파싱하기
                List<String> types = List.of(type.split(" ")).subList(0, 2); //
                // 다시 Firebase에 저장
                db.collection("hospital").document(id).update("address1", types.get(0));
                db.collection("hospital").document(id).update("address2", types.get(1));
                log.info("hospitalId: " + id + " 주소 파싱 완료: " + types.get(0) + " " + types.get(1));
            } catch(NullPointerException e){
                log.info("hospitalId: " + id + " 데이터베이스에 없음");
            }
        }

        for(String id: hospitals){
            String hospitalId = id;
            String url = DETAIL_URL + "&HPID=";
            HttpURLConnection conn = (HttpURLConnection) new URL(url + hospitalId).openConnection();
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
            log.info("hospitalId: " + hospitalId + " 조회 완료");

            ObjectMapper objectMapper = new ObjectMapper();
            // 모든 필드의 자료형을 String으로 강제로 변환하는 Mapper
            // log.info(items.toPrettyString());
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonPrettyPrintString);
                JsonNode items = jsonNode.get("response").get("body").get("items").get("item");
                DetailHospital detailHospital = objectMapper.readValue(items.toString(), DetailHospital.class);
                db.collection("hospital").document(hospitalId).update("dgidIdName", detailHospital.getDgidIdName() == null ? "" : detailHospital.getDgidIdName());
            } catch(NullPointerException e){
                log.info("hospitalId: " + hospitalId + " 데이터베이스에 없음");
                continue;
            }
            log.info("hospitalId: " + hospitalId + " firebase에 진료과목 파싱하여 저장 완료");
        }

    }*/
}
