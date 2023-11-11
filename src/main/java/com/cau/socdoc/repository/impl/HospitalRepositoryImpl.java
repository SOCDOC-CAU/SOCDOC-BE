package com.cau.socdoc.repository.impl;

import com.cau.socdoc.domain.Hospital;
import com.cau.socdoc.domain.Like;
import com.cau.socdoc.repository.HospitalRepository;
import com.cau.socdoc.util.MessageUtil;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class HospitalRepositoryImpl implements HospitalRepository {

    // 모든 병원 ID 조회
    public List<String> findAllHospital() throws ExecutionException, InterruptedException {
        CollectionReference hospitalCollection = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL);
        List<QueryDocumentSnapshot> querySnapshot = hospitalCollection.get().get().getDocuments();
        return querySnapshot.stream().map(QueryDocumentSnapshot::getId).collect(Collectors.toList());
    }

    // 특정 지역의 특정 진료과목의 병원을 조회 (10개 단위)
    public List<Hospital> findHospitalByTypeAndAddress(String type, String address1, String address2, int pageNum) throws ExecutionException, InterruptedException {
        // 이전 페이지의 마지막 dutyName 값을 가져오기
        String lastDutyName = getLastDutyNameOnPreviousPage(type, address1, address2, pageNum);
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                .whereArrayContains(MessageUtil.TYPE_HOSPITAL, type)
                .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                .orderBy(MessageUtil.DUTY_NAME)
                .startAfter(lastDutyName) // 이전 페이지의 마지막 dutyName 이후부터 시작
                .limit(10);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return querySnapshot.stream().map(document -> document.toObject(Hospital.class)).collect(Collectors.toList());
    }

    // 이전 페이지의 마지막 dutyName 값을 가져오기 for 특정 지역의 특정 진료과목의 병원 조회
    private String getLastDutyNameOnPreviousPage(String type, String address1, String address2, int pageNum) throws ExecutionException, InterruptedException {
        if (pageNum > 1) {
            // 이전 페이지의 마지막 hospitalId를 가져오기
            int previousPageStartIdx = (pageNum - 1) * 10;
            Query previousPageQuery = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                    .whereArrayContains(MessageUtil.TYPE_HOSPITAL, type)
                    .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                    .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                    .orderBy(MessageUtil.DUTY_NAME)
                    .startAt(previousPageStartIdx)
                    .limit(pageNum * 10);

            List<QueryDocumentSnapshot> previousPageSnapshot = previousPageQuery.get().get().getDocuments();
            if (!previousPageSnapshot.isEmpty()) {
                return previousPageSnapshot.get(previousPageSnapshot.size() - 1).getString(MessageUtil.DUTY_NAME);
            }
        }
        return null; // 첫 페이지인 경우 null 반환
    }

    // 특정 지역의 병원을 조회 (10개 단위)
    public List<Hospital> findHospitalByAddress(String address1, String address2, int pageNum) throws ExecutionException, InterruptedException {
        // 이전 페이지의 마지막 dutyName 값을 가져오기
        String lastDutyName = getLastDutyNameOnPreviousPage(address1, address2, pageNum);
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                .orderBy(MessageUtil.DUTY_NAME)
                .startAfter(lastDutyName)
                .limit(10);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return querySnapshot.stream().map(document -> document.toObject(Hospital.class)).collect(Collectors.toList());
    }

    // 이전 페이지의 마지막 dutyName 값을 가져오기 for 특정 지역의 병원 조회
    private String getLastDutyNameOnPreviousPage(String address1, String address2, int pageNum) throws ExecutionException, InterruptedException {
        if (pageNum > 1) {
            // 이전 페이지의 마지막 hospitalId를 가져오기
            int previousPageStartIdx = (pageNum - 1) * 10;
            Query previousPageQuery = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL)
                    .whereEqualTo(MessageUtil.ADDRESS1_HOSPITAL, address1)
                    .whereEqualTo(MessageUtil.ADDRESS2_HOSPITAL, address2)
                    .orderBy(MessageUtil.DUTY_NAME)
                    .startAt(previousPageStartIdx)
                    .limit(pageNum * 10);

            List<QueryDocumentSnapshot> previousPageSnapshot = previousPageQuery.get().get().getDocuments();
            if (!previousPageSnapshot.isEmpty()) {
                return previousPageSnapshot.get(previousPageSnapshot.size() - 1).getString(MessageUtil.DUTY_NAME);
            }
        }
        return null; // 첫 페이지인 경우 null 반환
    }

    // 특정 병원의 상세정보 조회
    public Hospital findHospitalDetail(String hospitalId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_HOSPITAL).document(hospitalId);
        return documentReference.get().get().toObject(Hospital.class);
    }

    // 특정 유저가 좋아요한 병원 조회
    public List<Hospital> findHospitalByLike(String userId) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).whereEqualTo(MessageUtil.USER_ID, userId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return querySnapshot.stream().map(document -> document.toObject(Like.class).getHospitalId()).map(hospitalId -> {
            try {
                return findHospitalDetail(hospitalId);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
}
