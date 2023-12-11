package com.cau.socdoc.repository.impl;

import com.cau.socdoc.domain.Hospital;
import com.cau.socdoc.domain.Like;
import com.cau.socdoc.repository.HospitalRepository;
import com.cau.socdoc.util.MessageUtil;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.HospitalException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class HospitalRepositoryImpl implements HospitalRepository {

    // 모든 병원 ID 조회
    public List<String> findAllHospital() throws ExecutionException, InterruptedException {
        CollectionReference hospitalCollection = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL);
        List<QueryDocumentSnapshot> querySnapshot = hospitalCollection.get().get().getDocuments();
        return querySnapshot.stream().map(QueryDocumentSnapshot::getId).collect(Collectors.toList());
    }

    // 특정 지역의 특정 진료과목의 병원을 조회 (10개 단위)
    public List<Hospital> findHospitalByTypeAndAddress(String type, String address1, String address2, int pageNum, int sortType) throws ExecutionException, InterruptedException {
        Query query = null;
        if(sortType == 0) { // 별점 내림차순
            query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                    .whereArrayContains(MessageUtil.TYPE_HOSPITAL, type)
                    .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                    .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                    .orderBy(MessageUtil.RATING, Query.Direction.DESCENDING)
                    .limit(pageNum * 10);
        } else if(sortType == 1) { // 이름 오름차순
            query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                    .whereArrayContains(MessageUtil.TYPE_HOSPITAL, type)
                    .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                    .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                    .orderBy(MessageUtil.DUTY_NAME)
                    .limit(pageNum * 10);
        } else {
            throw new HospitalException(ResponseCode.BAD_REQUEST);
        }
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        log.info("특정 지역의 특정 진료과목의 병원을 조회 (10개 단위) querySnapshot: {}", querySnapshot.size());
        List<Hospital> total = querySnapshot.stream().map(document -> document.toObject(Hospital.class)).collect(Collectors.toList());
        if(total.size() > pageNum * 10) {
            return total.subList((pageNum - 1) * 10, pageNum * 10);
        } else {
            return total.subList((pageNum - 1) * 10, total.size());
        }
    }


    public List<Hospital> findHospitalByAddress(String address1, String address2, int pageNum, int sortType) throws ExecutionException, InterruptedException {
        Query query = null;
        if(sortType == 0) { // 별점 내림차순
            log.info("별점 내림차순 정렬 후 특정 지역의 병원을 조회 (10개 단위)");
            query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                    .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                    .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                    .orderBy(MessageUtil.RATING, Query.Direction.DESCENDING)
                    .limit(pageNum * 10);
        } else if(sortType == 1) { // 이름 오름차순
            log.info("이름 오름차순 정렬 후 특정 지역의 병원을 조회 (10개 단위)");
            query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                    .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                    .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                    .orderBy(MessageUtil.DUTY_NAME)
                    .limit(pageNum * 10);
        } else {
            throw new HospitalException(ResponseCode.BAD_REQUEST);
        }
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        log.info("특정 지역의 병원을 조회 (10개 단위) querySnapshot: {}", querySnapshot.size());
        List<Hospital> total = querySnapshot.stream().map(document -> document.toObject(Hospital.class)).collect(Collectors.toList());
        if(total.size() > pageNum * 10) {
            return total.subList((pageNum - 1) * 10, pageNum * 10);
        } else {
            return total.subList((pageNum - 1) * 10, total.size());
        }
    }

    // 특정 병원의 상세정보 조회
    public Hospital findHospitalDetail(String hospitalId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL).document(hospitalId);
        return documentReference.get().get().toObject(Hospital.class);
    }

    // 특정 유저가 좋아요한 병원 조회
    public List<Hospital> findHospitalByLike(String userId) throws ExecutionException, InterruptedException {
        log.info("좋아요한 병원 조회, userId: {}", userId);
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).whereEqualTo(MessageUtil.USER_ID, userId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        log.info("좋아요한 병원 query: {}", querySnapshot.size());
        return querySnapshot.stream().map(document -> document.toObject(Like.class).getHospitalId()).map(hospitalId -> {
            try {
                return findHospitalDetail(hospitalId);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public String findNameById(String hospitalId) throws ExecutionException, InterruptedException {
        log.info("병원 이름 조회, hospitalId: {}", hospitalId);
        DocumentReference documentReference = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL).document(hospitalId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (!document.exists()) {
            throw new HospitalException(ResponseCode.HOSPITAL_NOT_FOUND);
        }
        return document.toObject(Hospital.class).getDutyName();
    }

    // 유저의 메인페이지 4개 병원 분과 코드를 받아 병원 목록 평점 내림차순으로 조회
    @Override
    public List<Hospital> findHospitalByMain(String address1, String address2, String code1, String code2, String code3, String code4) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                .whereArrayContainsAny(MessageUtil.TYPE_HOSPITAL,
                        List.of(MessageUtil.codeToHospitalType(code1), MessageUtil.codeToHospitalType(code2), MessageUtil.codeToHospitalType(code3), MessageUtil.codeToHospitalType(code4)))
                .orderBy(MessageUtil.RATING, Query.Direction.DESCENDING)
                .limit(10);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        log.info("유저의 메인페이지 4개 병원 분과 코드를 받아 병원 목록 조회 querySnapshot: {}", querySnapshot.size());
        return querySnapshot.stream().map(document -> document.toObject(Hospital.class)).collect(Collectors.toList());
    }
}
