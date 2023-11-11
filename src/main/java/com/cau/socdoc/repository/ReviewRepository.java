package com.cau.socdoc.repository;

import com.cau.socdoc.domain.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ReviewRepository {

    Map<String, Review> readReview(String id, int type) throws ExecutionException, InterruptedException;
    String createReview(Review review, MultipartFile image) throws ExecutionException, InterruptedException, IOException;
    void updateReview(String reviewId, String contents, int rating) throws ExecutionException, InterruptedException;
    void deleteReview(String reviewId);
}
