package com.cau.socdoc.service;

import com.cau.socdoc.domain.Review;
import com.cau.socdoc.dto.request.CreateReviewDto;
import com.cau.socdoc.dto.request.UpdateReviewDto;
import com.cau.socdoc.dto.response.ResponseReviewDto;
import com.cau.socdoc.repository.ReviewRepository;
import com.cau.socdoc.repository.UserRepository;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.ReviewException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public List<ResponseReviewDto> readReviewByUserId(String userId) throws ExecutionException, InterruptedException { // 유저 ID로 리뷰 조회
        List<Review> reviews = reviewRepository.readReviewByUserId(userId);
        return reviews.stream()
                .map(review -> ResponseReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .userName(userRepository.findNameById(review.getUserId()))
                        .userName("")
                        .createdAt(review.getCreatedAt())
                        .rating(review.getRating())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String createReview(CreateReviewDto createReviewDto) throws ExecutionException, InterruptedException { // 리뷰 생성 후 리뷰 고유 ID 리턴
        try {
            return reviewRepository.createReview(createReviewDto);
        } catch (IOException e){
            throw new ReviewException(ResponseCode.IMAGE_UPLOAD_ERROR);
        }
    }

    @Transactional
    public void updateReview(UpdateReviewDto updateReviewDto) throws ExecutionException, InterruptedException { // 리뷰 수정
        reviewRepository.updateReview(updateReviewDto);
    }

    @Transactional
    public void deleteReview(String reviewId) { // 리뷰 삭제
        reviewRepository.deleteReview(reviewId);
    }

    private MultipartFile readImage(String imageName) throws IOException { // 로컬에 존재하는 리뷰 이미지 읽어오기
        // /src/main/resources/static/images/ 경로에 저장된 리뷰 이미지를 읽어옴
        File file = new File(IMAGE_DIR + imageName + ".png");
        if (file.exists()) {
            DiskFileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            return new CommonsMultipartFile(fileItem);
        } else {
            // 이미지 파일이 존재하지 않을 경우 예외 처리
            throw new FileNotFoundException("이미지 파일을 찾을 수 없습니다.");
        }
    }
}
