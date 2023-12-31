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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Api(tags = "review")
@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    // 유저의 리뷰 조회
    @Operation(summary = "[리뷰] 유저 리뷰 조회", description = "특정 유저가 작성한 리뷰를 조회합니다.")
    @GetMapping("/user")
    public ApiResponse<List<ResponseReviewDto>> readReviewByUserId(@RequestParam String userId) throws ExecutionException, InterruptedException, IOException {
        return ApiResponse.success(reviewService.readReview(userId, 0), ResponseCode.REVIEW_READ_SUCCESS.getMessage());
    }

    // 병원의 리뷰 조회
    @Operation(summary = "[리뷰] 병원 리뷰 조회", description = "특정 병원에 작성된 리뷰를 조회합니다.")
    @GetMapping("/hospital")
    public ApiResponse<List<ResponseReviewDto>> readReviewByHospitalId(@RequestParam String hospitalId) throws ExecutionException, InterruptedException, IOException {
        log.info("요청 받아 병원 리뷰 조회 시작, 병원 ID: " + hospitalId);
        return ApiResponse.success(reviewService.readReview(hospitalId, 1), ResponseCode.REVIEW_READ_SUCCESS.getMessage());
    }

    // 리뷰 생성
    @Operation(summary = "[리뷰] 리뷰 생성", description = "새 리뷰를 생성합니다.")
    @PostMapping
    public ApiResponse<String> createReview(@ModelAttribute CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException, IOException {
        log.info("요청 받아 병원 리뷰 생성 시작");
        log.info("userId: " + createReviewDto.getUserId());
        log.info("hospitalId: " + createReviewDto.getHospitalId());
        log.info("content: " + createReviewDto.getContent());
        log.info("rating " + createReviewDto.getRating());
        log.info("files: " + createReviewDto.getFiles().toString());
        log.info("Dto 내용물 확인 완료, Service로 진입");
        return ApiResponse.success(reviewService.createReview(createReviewDto), ResponseCode.REVIEW_CREATE_SUCCESS.getMessage());
    }

    // 리뷰 수정
    @Operation(summary = "[리뷰] 리뷰 수정", description = "특정 리뷰를 수정합니다.")
    @PutMapping("/update")
    public ApiResponse<Void> updateReview(@RequestBody @Valid UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException {
        reviewService.updateReview(updateReviewDto);
        return ApiResponse.success(null, ResponseCode.REVIEW_UPDATE_SUCCESS.getMessage());
    }

    // 리뷰 삭제
    @Operation(summary = "[리뷰] 리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
    @DeleteMapping
    public ApiResponse<Void> deleteReview(@RequestParam String reviewId) throws ExecutionException, InterruptedException {
        reviewService.deleteReview(reviewId);
        return ApiResponse.success(null, ResponseCode.REVIEW_DELETE_SUCCESS.getMessage());
    }
}
