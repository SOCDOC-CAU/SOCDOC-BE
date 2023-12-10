package com.cau.socdoc.repository.impl;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.repository.ReviewRepository;
import com.cau.socdoc.util.MessageUtil;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.ReviewException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    @Value("${IMAGE_DIR}")
    private String IMAGE_DIR;

    @Override
    public Map<String, Review> readReview(String id, int type) throws ExecutionException, InterruptedException {
        Map<String, Review> reviews = new HashMap<>();
        CollectionReference reviewCollection = FirestoreClient.getFirestore().collection(MessageUtil.COLLECTION_REVIEW);
        Query query;
        if(type == 0){ // 유저 ID로 리뷰 조회
            query = reviewCollection.whereEqualTo(MessageUtil.USER_ID, id);
        } else { // 병원 ID로 리뷰 조회
            query = reviewCollection.whereEqualTo(MessageUtil.HOSPITAL_ID, id);
        }
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        for(QueryDocumentSnapshot document : querySnapshot){
            reviews.put(document.getId(), document.toObject(Review.class));
        }
        return reviews;
    }

    @Override
    public String createReview(Review review, MultipartFile image) throws ExecutionException, InterruptedException, IOException {
        Firestore db = FirestoreClient.getFirestore();
        // 이미 유저가 이 병원에 이미 리뷰를 남겼는지 확인
        CollectionReference reviewCollection = db.collection(MessageUtil.COLLECTION_REVIEW);
        Query query = reviewCollection.whereEqualTo(MessageUtil.USER_ID, review.getUserId()).whereEqualTo(MessageUtil.HOSPITAL_ID, review.getHospitalId());
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        if(!querySnapshot.isEmpty()){
            throw new ReviewException(ResponseCode.REVIEW_ALREADY_EXIST);
        }

        // 수신한 이미지 디렉토리에 저장
        ApiFuture<DocumentReference> docRef = db.collection(MessageUtil.COLLECTION_REVIEW).add(review);
        if(image != null){
            try {
            // 이미지 로컬 저장
            String fileName = docRef.get().getId() + ".jpg";
            File file = new File(IMAGE_DIR + fileName);
            image.transferTo(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        reCalculateHospitalRating(review.getHospitalId());
        return docRef.get().getId();
    }

    @Override
    public void updateReview(String reviewId, String contents, int rating) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(MessageUtil.COLLECTION_REVIEW).document(reviewId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (!document.exists()) {
            throw new ReviewException(ResponseCode.REVIEW_NOT_FOUND);
        }
        docRef.update(MessageUtil.CONTENT, contents);
        docRef.update(MessageUtil.RATING, rating);
        reCalculateHospitalRating(document.getString(MessageUtil.HOSPITAL_ID));
    }

    @Override
    public void deleteReview(String reviewId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // 특정 리뷰 id를 가진 리뷰를 찾아 병원 id를 가져온다.
        DocumentReference docRef = db.collection(MessageUtil.COLLECTION_REVIEW).document(reviewId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (!document.exists()) {
            throw new ReviewException(ResponseCode.REVIEW_NOT_FOUND);
        }

        // 리뷰를 삭제한다.
        db.collection(MessageUtil.COLLECTION_REVIEW).document(reviewId).delete();
        reCalculateHospitalRating(document.getString(MessageUtil.HOSPITAL_ID));
    }

    // 병원의 평점을 다시 계산하여 저장한다.
    private void reCalculateHospitalRating(String hospitalId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        // 리뷰 컬렉션을 조회하여 병원 id가 일치하는 리뷰를 가져온다.
        CollectionReference reviewCollection = db.collection(MessageUtil.COLLECTION_REVIEW);
        Query query = reviewCollection.whereEqualTo(MessageUtil.HOSPITAL_ID, hospitalId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        if(querySnapshot.isEmpty()){
            throw new ReviewException(ResponseCode.REVIEW_NOT_FOUND);
        }

        double sum = 0;
        for(QueryDocumentSnapshot document : querySnapshot){
            sum += document.toObject(Review.class).getRating();
        }
        double answer = sum / querySnapshot.size();
        answer = Math.round(answer * 10) / 10.0; // 소수점 첫째자리까지 반올림

        // 이제 병원의 rating을 업데이트
        DocumentReference docRef = db.collection(MessageUtil.COLLECTION_HOSPITAL).document(hospitalId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (!document.exists()) {
            throw new ReviewException(ResponseCode.HOSPITAL_NOT_FOUND);
        }
        docRef.update(MessageUtil.RATING, answer);
    }

    @Override
    public double getReviewAverage(String hospitalId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference reviewCollection = db.collection(MessageUtil.COLLECTION_REVIEW);
        Query query = reviewCollection.whereEqualTo(MessageUtil.HOSPITAL_ID, hospitalId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        if(querySnapshot.isEmpty()){
            return 0.0;
        }
        double sum = 0;
        for(QueryDocumentSnapshot document : querySnapshot){
            sum += document.toObject(Review.class).getRating();
        }
        double answer = sum / querySnapshot.size();
        return Math.round(answer * 10) / 10.0;
    }
}
