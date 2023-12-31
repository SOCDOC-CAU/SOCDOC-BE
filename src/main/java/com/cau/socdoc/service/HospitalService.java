package com.cau.socdoc.service;

import com.cau.socdoc.dto.response.ResponseDetailHospitalDto;
import com.cau.socdoc.dto.response.ResponsePharmacyDto;
import com.cau.socdoc.dto.response.ResponseSimpleHospitalDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HospitalService {

    // 병원 관련
    ResponseDetailHospitalDto findHospitalDetailById(String hospitalId, String userId) throws ExecutionException, InterruptedException;
    List<ResponseSimpleHospitalDto> findHospitalByTypeAndAddress(String type, String address1, String address2, int pageNum, int sortType) throws ExecutionException, InterruptedException;
    List<ResponseSimpleHospitalDto> findHospitalByAddress(String address1, String address2, int pageNum, int sortType) throws ExecutionException, InterruptedException;
    List<ResponseSimpleHospitalDto> findHospitalByLike(String userId) throws ExecutionException, InterruptedException;
    List<ResponsePharmacyDto> findPharmacyByHospitalId(String hospitalId) throws ExecutionException, InterruptedException;

    // 좋아요 관련
    void saveLike(String userId, String hospitalId) throws ExecutionException, InterruptedException;
    void deleteLike(String userId, String hospitalId) throws ExecutionException, InterruptedException;
    List<ResponseSimpleHospitalDto> findHospitalByMain(String address1, String address2, String code1, String code2, String code3, String code4) throws ExecutionException, InterruptedException;
}
