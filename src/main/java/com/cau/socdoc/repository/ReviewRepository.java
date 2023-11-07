package com.cau.socdoc.repository;

import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;

import java.util.List;

public interface ReviewRepository {

    List<ResponseReviewDto> readReviewByUserId(String userId);
    List<ResponseReviewDto> readReviewByHospitalId(String hospitalId);
    String createReview(CreateReviewDto createReviewDto);
    void updateReview(UpdateReviewDto updateReviewDto);
    void deleteReview(String reviewId);
}
