package com.cau.socdoc.util;

import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.HospitalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

    // 유효성 검사 관련
    public static final String NOT_EMPTY = "빈 값이 입력되었습니다.";
    public static final String ONE_TO_FIVE = "null 값이 입력되었습니다.";
    public static final String LARGER_THAN_ZERO = "0보다 큰 값을 입력해주세요.";
    public static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";

    // 유저
    public static final String COLLECTION_USER = "user";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String ADDRESS1 = "address1";
    public static final String ADDRESS2 = "address2";

    // 병원
    public static final String COLLECTION_HOSPITAL = "hospital";
    public static final String TYPE_HOSPITAL = "dgidIdName";
    public static final String ADDRESS1_HOSPITAL = "address1";
    public static final String ADDRESS2_HOSPITAL = "address2";
    public static final String DUTY_NAME = "dutyName";

    // 리뷰
    public static final String COLLECTION_REVIEW = "review";
    public static final String HOSPITAL_ID = "hospitalId";
    public static final String CONTENT = "content";
    public static final String RATING = "rating";

    // 좋아요
    public static final String COLLECTION_LIKE = "like";

    public static String codeToHospitalType(String type){
        switch(type){
            case "D001":
                return "내과";
            case "D002":
                return "소아청소년과";
            case "D003":
                return "신경과";
            case "D004":
                return "정신건강의학과";
            case "D005":
                return "피부과";
            case "D006":
                return "외과";
            case "D007":
                return "흉부외과";
            case "D008":
                return "정형외과";
            case "D009":
                return "신경외과";
            case "D010":
                return "성형외과";
            case "D011":
                return "산부인과";
            case "D012":
                return "안과";
            case "D013":
                return "이비인후과";
            case "D014":
                return "비뇨기과";
            case "D016":
                return "재활의학과";
            case "D017":
                return "마취통증의학과";
            case "D018":
                return "영상의학과";
            case "D019":
                return "치료방사선과";
            case "D020":
                return "임상병리과";
            case "D021":
                return "해부병리과";
            case "D022":
                return "가정의학과";
            case "D023":
                return "핵의학과";
            case "D024":
                return "응급의학과";
            case "D026":
                return "치과";
            case "D034":
                return "구강악안면외과";
            default: // 잘못된 병원코드
                throw new HospitalException(ResponseCode.BAD_REQUEST);
        }
    }
}
