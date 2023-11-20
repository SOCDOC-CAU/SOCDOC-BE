package com.cau.socdoc.service;

import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;

import java.io.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ReviewService {

    List<ResponseReviewDto> readReview(String userId, int type) throws ExecutionException, InterruptedException, IOException; // 유저 ID로 리뷰 조회
    String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException, IOException; // 리뷰 생성 후 리뷰 고유 ID 리턴
    void updateReview(UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException; // 리뷰 수정
    void deleteReview(String reviewId) throws ExecutionException, InterruptedException; // 리뷰 삭제
}
