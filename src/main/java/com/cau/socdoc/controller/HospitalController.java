package com.cau.socdoc.controller;

import com.cau.socdoc.dto.RequestAddressDto;
import com.cau.socdoc.dto.ResponseHospitalDto;
import com.cau.socdoc.service.HospitalService;
import com.cau.socdoc.util.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/hospital")
@RequiredArgsConstructor
@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    // 단순 시-구 입력받아 병원 목록 조회
    @GetMapping
    public ApiResponse<List<ResponseHospitalDto>> findHospitalByAddress(@RequestBody RequestAddressDto requestAddressDto) throws IOException {
        return ApiResponse.success(hospitalService.findHospitalByAddress(requestAddressDto), "병원 목록 조회 성공");
    }

}
