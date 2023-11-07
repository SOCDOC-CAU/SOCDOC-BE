package com.cau.socdoc.repository;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ReviewRepository {

    List<Review> readReviewByUserId(String userId) throws ExecutionException, InterruptedException;
    List<Review> readReviewByHospitalId(String hospitalId) throws ExecutionException, InterruptedException;
    String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException;
    void updateReview(UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException;
    void deleteReview(String reviewId);
}
