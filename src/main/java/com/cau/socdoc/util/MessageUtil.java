package com.cau.socdoc.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

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

    // 리뷰
    public static final String COLLECTION_REVIEW = "review";
    public static final String HOSPITAL_ID = "hospitalId";
    public static final String CONTENT = "content";
    public static final String RATING = "rating";

    // 좋아요
    public static final String COLLECTION_LIKE = "like";
}
