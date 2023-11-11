package com.cau.socdoc.repository.impl;

import com.cau.socdoc.domain.Like;
import com.cau.socdoc.repository.LikeRepository;
import com.cau.socdoc.util.MessageUtil;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    // 특정 유저가 특정 병원에 좋아요
    public void saveLike(String userId, String hospitalId) {
        Like like = Like.of(userId, hospitalId);
        FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).add(like);
    }

    // 특정 유저가 특정 병원에 좋아요 취소
    public void deleteLike(String userId, String hospitalId) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).whereEqualTo(MessageUtil.USER_ID, userId).whereEqualTo(MessageUtil.HOSPITAL_ID, hospitalId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).document(querySnapshot.get(0).getId()).delete();
    }

    // 특정 유저가 특정 병원에 좋아요를 눌렀는지 여부를 조회
    public boolean existsLikeByUserIdAndHospitalId(String userId, String hospitalId) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).whereEqualTo(MessageUtil.USER_ID, userId).whereEqualTo(MessageUtil.HOSPITAL_ID, hospitalId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return !querySnapshot.isEmpty();
    }

    // 특정 병원이 받은 좋아요 수 조회
    public int findLikeCountByHospitalId(String hospitalId) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_LIKE).whereEqualTo(MessageUtil.HOSPITAL_ID, hospitalId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return querySnapshot.size();
    }
}
