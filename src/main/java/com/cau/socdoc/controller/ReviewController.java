package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;
import com.cau.socdoc.service.ReviewService;
import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Api(tags = "review")
@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    // 유저의 리뷰 조회
    @Operation(summary = "[리뷰] 유저 리뷰 조회", description = "특정 유저가 작성한 리뷰를 조회합니다.")
    @GetMapping("/read/user/{userId}")
    public ApiResponse<List<ResponseReviewDto>> readReviewByUserId(@PathVariable String userId) throws ExecutionException, InterruptedException, IOException {
        return ApiResponse.success(reviewService.readReview(userId, 0), ResponseCode.REVIEW_READ_SUCCESS.getMessage());
    }

    // 병원의 리뷰 조회
    @Operation(summary = "[리뷰] 병원 리뷰 조회", description = "특정 병원에 작성된 리뷰를 조회합니다.")
    @GetMapping("/read/hospital/{hospitalId}")
    public ApiResponse<List<ResponseReviewDto>> readReviewByHospitalId(@PathVariable String hospitalId) throws ExecutionException, InterruptedException, IOException {
        return ApiResponse.success(reviewService.readReview(hospitalId, 1), ResponseCode.REVIEW_READ_SUCCESS.getMessage());
    }

    // 리뷰 생성
    @Operation(summary = "[리뷰] 리뷰 생성", description = "리뷰를 생성합니다.")
    @PostMapping("/create")
    public ApiResponse<String> createReview(@RequestBody @Valid CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException {
        return ApiResponse.success(reviewService.createReview(createReviewDto), ResponseCode.REVIEW_CREATE_SUCCESS.getMessage());
    }

    // 리뷰 수정
    @Operation(summary = "[리뷰] 리뷰 수정", description = "리뷰를 수정합니다.")
    @PutMapping("/update")
    public ApiResponse<Void> updateReview(@RequestBody @Valid UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException {
        reviewService.updateReview(updateReviewDto);
        return ApiResponse.success(null, ResponseCode.REVIEW_UPDATE_SUCCESS.getMessage());
    }

    // 리뷰 삭제
    @Operation(summary = "[리뷰] 리뷰 삭제", description = "리뷰를 삭제합니다.")
    @DeleteMapping("/delete/reviewId/{reviewId}")
    public ApiResponse<Void> deleteReview(@PathVariable String reviewId) throws ExecutionException, InterruptedException {
        reviewService.deleteReview(reviewId);
        return ApiResponse.success(null, ResponseCode.REVIEW_DELETE_SUCCESS.getMessage());
    }
}
