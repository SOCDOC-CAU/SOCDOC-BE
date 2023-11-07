package com.cau.socdoc.repository;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.ReviewException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    public static final String COLLECTION_NAME = "review";
    public static final String USER_ID = "userId";
    public static final String HOSPITAL_ID = "hospitalId";
    public static final String CONTENT = "content";
    public static final String RATING = "rating";

    @Override
    public List<Review> readReviewByUserId(String userId) throws ExecutionException, InterruptedException {
        CollectionReference reviewCollection = FirestoreClient.getFirestore().collection(COLLECTION_NAME);
        // userId 필드가 userId와 동일한 리뷰를 FB에서 가져옴
        Query query = reviewCollection.whereEqualTo(USER_ID, userId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return querySnapshot.stream().map(document -> document.toObject(Review.class)).collect(Collectors.toList());
    }

    @Override
    public List<Review> readReviewByHospitalId(String hospitalId) throws ExecutionException, InterruptedException {
        CollectionReference reviewCollection = FirestoreClient.getFirestore().collection(COLLECTION_NAME);
        Query query = reviewCollection.whereEqualTo(HOSPITAL_ID, hospitalId);
        List<QueryDocumentSnapshot> querySnapshot = query.get().get().getDocuments();
        return querySnapshot.stream().map(document -> document.toObject(Review.class)).collect(Collectors.toList());
    }

    @Override
    public String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Review review = Review.of(createReviewDto.getUserId(), createReviewDto.getHospitalId(), createReviewDto.getContent(), createReviewDto.getRating());
        ApiFuture<DocumentReference> docRef = db.collection(COLLECTION_NAME).add(review);
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
