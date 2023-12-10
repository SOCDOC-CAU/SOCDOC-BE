package com.cau.socdoc.service.impl;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;
import com.cau.socdoc.repository.HospitalRepository;
import com.cau.socdoc.repository.ReviewRepository;
import com.cau.socdoc.repository.UserRepository;
import com.cau.socdoc.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class ReviewServiceImpl implements ReviewService {

    @Value("${IMAGE_DIR}")
    private String IMAGE_DIR;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;

    // 리뷰 조회
    @Transactional(readOnly = true)
    public List<ResponseReviewDto> readReview(String userId, int type) throws ExecutionException, InterruptedException, IOException {
        Map<String, Review> reviews = reviewRepository.readReview(userId, type);
        log.info("리뷰 조회 완료: " + userId + " " + reviews.size() + "개");
        List<ResponseReviewDto> responseReviewDtos = new ArrayList<>();
        if (type == 0) {// 특정 유저의 리뷰 조회
            for (String reviewId : reviews.keySet()) {
                log.info("리뷰 ID: " + reviewId);
                log.info("리뷰 병원 ID: " + reviews.get(reviewId).getHospitalId());
                ResponseReviewDto responseReviewDto = ResponseReviewDto.builder()
                        .reviewId(reviewId)
                        .name(hospitalRepository.findNameById(reviews.get(reviewId).getHospitalId()))
                        .createdAt(reviews.get(reviewId).getCreatedAt())
                        .rating(reviews.get(reviewId).getRating())
                        .content(reviews.get(reviewId).getContent())
                        .files(readImage(reviewId))
                        .build();
                responseReviewDtos.add(responseReviewDto);
            }
        } else { // 특정 병원의 리뷰 조회
            for (String reviewId : reviews.keySet()) {
                ResponseReviewDto responseReviewDto = ResponseReviewDto.builder()
                        .reviewId(reviewId)
                        .name(userRepository.findNameById(reviews.get(reviewId).getUserId()))
                        .createdAt(reviews.get(reviewId).getCreatedAt())
                        .rating(reviews.get(reviewId).getRating())
                        .content(reviews.get(reviewId).getContent())
                        .files(readImage(reviewId))
                        .build();
                responseReviewDtos.add(responseReviewDto);
            }
        }
        return responseReviewDtos;
    }

    // 리뷰 생성
    @Transactional
    public String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException, IOException {
        Review review = Review.of(createReviewDto.getUserId(), createReviewDto.getHospitalId(), createReviewDto.getContent(), Integer.parseInt(createReviewDto.getRating()));
        String reviewId = reviewRepository.createReview(review, createReviewDto.getFiles().get(0));
        log.info("리뷰 생성 완료: " + reviewId);
        return reviewId;
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException {
        reviewRepository.updateReview(updateReviewDto.getReviewId(), updateReviewDto.getContent(), updateReviewDto.getRating());
        log.info("리뷰 수정 완료: " + updateReviewDto.getReviewId());
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(String reviewId) throws ExecutionException, InterruptedException {
        reviewRepository.deleteReview(reviewId);
        log.info("리뷰 삭제 완료: " + reviewId);
    }

    private byte[] readImage(String imageName) throws IOException {
        String dir = IMAGE_DIR + imageName + ".jpg";
        log.info(dir);
        File file = new File(dir);
        if (!file.exists()) {
            log.info("이미지 없음: " + imageName);
            return new byte[]{};
        }
        return Files.readAllBytes(Paths.get(dir));
    }
}
