package com.cau.socdoc.repository;

import java.util.concurrent.ExecutionException;

public interface LikeRepository {
    void saveLike(String userId, String hospitalId);
    void deleteLike(String userId, String hospitalId) throws ExecutionException, InterruptedException;
    boolean existsLikeByUserIdAndHospitalId(String userId, String hospitalId) throws ExecutionException, InterruptedException;
    int findLikeCountByHospitalId(String hospitalId) throws ExecutionException, InterruptedException;
}
