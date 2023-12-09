package com.cau.socdoc.service.impl;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;
import com.cau.socdoc.repository.ReviewRepository;
import com.cau.socdoc.repository.UserRepository;
import com.cau.socdoc.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    @Value("${IMAGE_DIR}")
    private static String IMAGE_DIR;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 리뷰 조회
    @Transactional(readOnly = true)
    public List<ResponseReviewDto> readReview(String userId, int type) throws ExecutionException, InterruptedException, IOException {
        Map<String, Review> reviews = reviewRepository.readReview(userId, type);
        log.info("리뷰 조회 완료: " + userId + " " + reviews.size() + "개");
        List<ResponseReviewDto> responseReviewDtos = new ArrayList<>();
        for (String reviewId : reviews.keySet()) {
            ResponseReviewDto responseReviewDto = ResponseReviewDto.builder()
                    .reviewId(reviewId)
                    .userName(userRepository.findNameById(reviews.get(reviewId).getUserId()))
                    .createdAt(reviews.get(reviewId).getCreatedAt().toString())
                    .rating(reviews.get(reviewId).getRating())
                    .content(reviews.get(reviewId).getContent())
                    .image(readImage(reviewId))
                    .build();
            responseReviewDtos.add(responseReviewDto);
        }
        return responseReviewDtos;
    }

    // 리뷰 생성
    @Transactional
    public String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException, IOException {
        Review review = Review.of(createReviewDto.getUserId(), createReviewDto.getHospitalId(), createReviewDto.getContent(), Integer.parseInt(createReviewDto.getRating()));
        String reviewId = reviewRepository.createReview(review, createReviewDto.getFiles());
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
    public void deleteReview(String reviewId) {
        reviewRepository.deleteReview(reviewId);
        log.info("리뷰 삭제 완료: " + reviewId);
    }

    private MultipartFile readImage(String imageName) throws IOException { // 로컬에 존재하는 리뷰 이미지 읽어오기
        // /src/main/resources/static/images/ 경로에 저장된 리뷰 이미지를 읽어옴
        log.info("이미지 읽어오기 시작: " + imageName);
        File file = new File(IMAGE_DIR + imageName + ".png");
        if (file.exists()) {
            log.info("이미지 있음: " + imageName);
            DiskFileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            return new CommonsMultipartFile(fileItem);
        } else {
            log.info("이미지 없음: " + imageName);
            return null;
        }
    }
}
