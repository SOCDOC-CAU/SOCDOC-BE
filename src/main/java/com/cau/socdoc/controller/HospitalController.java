package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.RequestLikeDto;
import com.cau.socdoc.dto.response.ResponseDetailHospitalDto;
import com.cau.socdoc.dto.response.ResponsePharmacyDto;
import com.cau.socdoc.dto.response.ResponseSimpleHospitalDto;
import com.cau.socdoc.service.HospitalService;
import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Api(tags = "hospital")
@RequiredArgsConstructor
@RequestMapping("/api/hospital")
@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    // 병원 상세정보 조회
    @Operation(summary = "병원 상세정보 조회", description = "특정 병원의 상세정보를 조회합니다.")
    @GetMapping("/detail")
    public ApiResponse<ResponseDetailHospitalDto> findHospitalDetailById(@RequestParam String hospitalId, @RequestParam String userId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalDetailById(hospitalId, userId), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 특정 지역의 특정 분과 병원 조회
    @Operation(summary = "특정 지역의 특정 분과 병원 조회", description = "병원코드 type과 주소, 페이지번호를 기반으로 특정 지역의 특정 분과 병원 목록을 조회합니다. (예: <서울특별시> <관악구>의 치과)")
    @GetMapping("/list/{type}")
    public ApiResponse<List<ResponseSimpleHospitalDto>> findHospitalByTypeAndAddress(@PathVariable String type, @RequestParam String address1, @RequestParam String address2, @RequestParam int pageNum) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalByTypeAndAddress(type, address1, address2, pageNum), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 특정 지역의 병원 조회
    @Operation(summary = "특정 지역의 병원 조회", description = "주소, 페이지번호를 기반으로 특정 지역의 병원 목록을 조회합니다. (예: <서울특별시> <동작구>의 병원)")
    @GetMapping("/list")
    public ApiResponse<List<ResponseSimpleHospitalDto>> findHospitalByTypeAndAddress(@RequestParam String address1, @RequestParam String address2, @RequestParam int pageNum) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalByAddress(address1, address2, pageNum), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 유저가 좋아요 누른 병원 조회
    @Operation(summary = "유저가 좋아요 누른 병원 조회", description = "유저가 좋아요 누른 병원 목록을 조회합니다.")
    @GetMapping("/like")
    public ApiResponse<List<ResponseSimpleHospitalDto>> findHospitalByLike(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findHospitalByLike(userId), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

    // 유저가 병원에 좋아요 누르기
    @Operation(summary = "유저가 병원에 좋아요 누르기", description = "유저가 특정 병원에 좋아요를 누릅니다.")
    @PostMapping("/like")
    public ApiResponse<Void> likeHospital(@RequestBody @Valid RequestLikeDto dto) throws ExecutionException, InterruptedException {
        hospitalService.saveLike(dto.getUserId(), dto.getHospitalId());
        return ApiResponse.success(null, ResponseCode.LIKE_CREATE_SUCCESS.getMessage());
    }

    // 유저가 병원에 좋아요 취소
    @Operation(summary = "유저가 병원에 좋아요 취소", description = "유저가 특정 병원에 좋아요를 취소합니다.")
    @DeleteMapping("/like")
    public ApiResponse<Void> unlikeHospital(@RequestBody @Valid RequestLikeDto dto) throws ExecutionException, InterruptedException {
        hospitalService.deleteLike(dto.getUserId(), dto.getHospitalId());
        return ApiResponse.success(null, ResponseCode.LIKE_DELETE_SUCCESS.getMessage());
    }

    // 특정 병원 근처 약국 조회
    @Operation(summary = "특정 병원 근처 약국 조회", description = "특정 병원 근처의 약국 목록을 조회합니다.")
    @GetMapping("/pharmacy")
    public ApiResponse<List<ResponsePharmacyDto>> findPharmacyByHospitalId(@RequestParam String hospitalId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(hospitalService.findPharmacyByHospitalId(hospitalId), ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }
}
