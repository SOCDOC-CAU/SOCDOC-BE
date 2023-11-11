package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.RequestLikeDto;
import com.cau.socdoc.dto.request.RequestSimpleHospitalDto;
import com.cau.socdoc.dto.response.ResponseDetailHospitalDto;
import com.cau.socdoc.dto.response.ResponsePharmacyDto;
import com.cau.socdoc.dto.response.ResponseSimpleHospitalDto;
import com.cau.socdoc.service.HospitalService;
import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Api(tags = "hospital")
@RequiredArgsConstructor
@RequestMapping("/api/hospital")
@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    // 병원 상세정보 조회
    @GetMapping("/detail/{hospitalId}")
    public ApiResponse<ResponseDetailHospitalDto> findHospitalDetailById(@PathVariable String hospitalId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalDetailById(hospitalId), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 특정 지역의 특정 분과 병원 조회
    @GetMapping("/list/{type}")
    public ApiResponse<List<ResponseSimpleHospitalDto>> findHospitalByTypeAndAddress(@PathVariable String type, @RequestBody RequestSimpleHospitalDto dto) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalByTypeAndAddress(type, dto.getAddress1(), dto.getAddress2(), dto.getPageNum()), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 특정 지역의 병원 조회
    @GetMapping("/list")
    public ApiResponse<List<ResponseSimpleHospitalDto>> findHospitalByTypeAndAddress(@RequestBody RequestSimpleHospitalDto dto) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalByAddress(dto.getAddress1(), dto.getAddress2(), dto.getPageNum()), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 유저가 좋아요 누른 병원 조회
    @GetMapping("/like/user/{userId}")
    public ApiResponse<List<ResponseSimpleHospitalDto>> findHospitalByLike(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalByLike(userId), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 유저가 병원에 좋아요 누르기
    @PostMapping("/like/user")
    public ApiResponse<Void> likeHospital(@RequestBody RequestLikeDto dto) throws ExecutionException, InterruptedException {
        hospitalService.saveLike(dto.getUserId(), dto.getHospitalId());
        return ApiResponse.success(null, ResponseCode.LIKE_CREATE_SUCCESS.getMessage());
    }

    // 유저가 병원에 좋아요 취소
    @DeleteMapping("/like/user")
    public ApiResponse<Void> unlikeHospital(@RequestBody RequestLikeDto dto) throws ExecutionException, InterruptedException {
        hospitalService.deleteLike(dto.getUserId(), dto.getHospitalId());
        return ApiResponse.success(null, ResponseCode.LIKE_DELETE_SUCCESS.getMessage());
    }

    // 특정 병원 근처 약국 조회
    @GetMapping("/pharmacy/{hospitalId}")
    public ApiResponse<List<ResponsePharmacyDto>> findPharmacyByHospitalId(@PathVariable String hospitalId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findPharmacyByHospitalId(hospitalId), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }
}
