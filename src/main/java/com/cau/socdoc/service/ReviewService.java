package com.cau.socdoc.service;

import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;
import com.cau.socdoc.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ResponseReviewDto> readReviewByUserId(String userId) { // 유저 ID로 리뷰 조회
        return reviewRepository.readReviewByUserId(userId);
    }

    public List<ResponseReviewDto> readReviewByHospitalId(String hospitalId) { // 병원 ID로 리뷰 조회
        return reviewRepository.readReviewByHospitalId(hospitalId);
    }

    public String createReview(CreateReviewDto createReviewDto) { // 리뷰 생성 후 리뷰 고유 ID 리턴
        return reviewRepository.createReview(createReviewDto);
    }

    public void updateReview(UpdateReviewDto updateReviewDto) { // 리뷰 수정
        reviewRepository.updateReview(updateReviewDto);
    }

    public void deleteReview(String reviewId) { // 리뷰 삭제
        reviewRepository.deleteReview(reviewId);
    }
}
