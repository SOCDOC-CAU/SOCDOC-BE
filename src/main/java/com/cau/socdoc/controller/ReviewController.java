package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;
import com.cau.socdoc.service.ReviewService;
import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/read/user/{userId}")
    public ApiResponse<List<ResponseReviewDto>> readReviewByUserId(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(reviewService.readReviewByUserId(userId), ResponseCode.REVIEW_READ_SUCCESS.getMessage());
    }

    @GetMapping("/read/hospital/{hospitalId}")
    public ApiResponse<List<ResponseReviewDto>> readReviewByHospitalId(@PathVariable String hospitalId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(reviewService.readReviewByHospitalId(hospitalId), ResponseCode.REVIEW_READ_SUCCESS.getMessage());
    }

    @PostMapping("/create")
    public ApiResponse<String> createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException {
        return ApiResponse.success(reviewService.createReview(createReviewDto), ResponseCode.REVIEW_CREATE_SUCCESS.getMessage());
    }

    @PutMapping("/update")
    public ApiResponse<Void> updateReview(UpdateReviewDto updateReviewDto) throws Exception {
        reviewService.updateReview(updateReviewDto);
        return ApiResponse.success(null, ResponseCode.REVIEW_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteReview(String reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.success(null, ResponseCode.REVIEW_DELETE_SUCCESS.getMessage());
    }
}
