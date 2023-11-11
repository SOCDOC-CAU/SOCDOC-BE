package com.cau.socdoc.repository;

import com.cau.socdoc.domain.Hospital;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HospitalRepository {

    List<String> findAllHospital() throws ExecutionException, InterruptedException;
    List<Hospital> findHospitalByTypeAndAddress(String type, String address1, String address2, int pageNum) throws ExecutionException, InterruptedException;
    List<Hospital> findHospitalByAddress(String address1, String address2, int pageNum) throws ExecutionException, InterruptedException;
    Hospital findHospitalDetail(String hospitalId) throws ExecutionException, InterruptedException;
    List<Hospital> findHospitalByLike(String userId) throws ExecutionException, InterruptedException;
}
