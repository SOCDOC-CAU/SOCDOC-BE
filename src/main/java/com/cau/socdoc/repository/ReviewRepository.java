package com.cau.socdoc.repository;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ReviewRepository {

    Map<String, Review> readReview(String id, int type) throws ExecutionException, InterruptedException;
    String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException, IOException;
    void updateReview(UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException;
    void deleteReview(String reviewId);
}
