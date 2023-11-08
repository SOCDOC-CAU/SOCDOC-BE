package com.cau.socdoc.repository;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.ReviewException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    public static final String COLLECTION_NAME = "review";
    public static final String USER_ID = "userId";
    public static final String HOSPITAL_ID = "hospitalId";
    public static final String CONTENT = "content";
    public static final String RATING = "rating";

    @Value("@{IMAGE_DIL}")
    private String IMAGE_DIR;

    @Override
    public Map<String, Review> readReview(String id, int type) throws ExecutionException, InterruptedException {
        Map<String, Review> reviews = new HashMap<>();
        CollectionReference reviewCollection = FirestoreClient.getFirestore().collection(COLLECTION_NAME);
        Query query;
        if(type == 0){ // 유저 ID로 리뷰 조회
            query = reviewCollection.whereEqualTo(USER_ID, id);
        } else { // 병원 ID로 리뷰 조회
            query = reviewCollection.whereEqualTo(HOSPITAL_ID, id);
        }
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        for(QueryDocumentSnapshot document : querySnapshot){
            reviews.put(document.getId(), document.toObject(Review.class));
        }
        return reviews;
    }

    @Override
    public String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException, IOException {
        Firestore db = FirestoreClient.getFirestore();
        Review review = Review.of(createReviewDto.getUserId(), createReviewDto.getHospitalId(), createReviewDto.getContent(), createReviewDto.getRating());
        ApiFuture<DocumentReference> docRef = db.collection(COLLECTION_NAME).add(review);

        // 수신한 이미지 디렉토리에 저장
        createReviewDto.getImage().transferTo(new File(IMAGE_DIR+ docRef.get().getId() + ".png"));
        return docRef.get().getId();
    }

    @Override
    public void updateReview(UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(updateReviewDto.getReviewId());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (!document.exists()) {
            throw new ReviewException(ResponseCode.REVIEW_NOT_FOUND);
        }
        docRef.update(CONTENT, updateReviewDto.getContent());
        docRef.update(RATING, updateReviewDto.getRating());
    }

    @Override
    public void deleteReview(String reviewId) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).document(reviewId).delete();
    }
}
