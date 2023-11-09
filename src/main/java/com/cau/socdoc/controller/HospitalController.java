package com.cau.socdoc.controller;

import com.cau.socdoc.service.HospitalService;
import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "hospital")
@RequiredArgsConstructor
@RequestMapping("/api/hospital")
@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    // 병원 정보 읽기
    @GetMapping("/read")
    public ApiResponse<Void> readHospital() throws IOException {
        // hospitalService.findHospitalByAddress();
        return ApiResponse.success(null, ResponseCode.HOSPITAL_READ_SUCCESS.getMessage());
    }

}
