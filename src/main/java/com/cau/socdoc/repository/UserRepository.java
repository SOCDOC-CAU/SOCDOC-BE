package com.cau.socdoc.repository;

import com.cau.socdoc.domain.User;

import java.util.concurrent.ExecutionException;

public interface UserRepository {

    String createUser(String userId, String userName, String userEmail, String address1, String address2);
    String findNameById(String userId) throws ExecutionException, InterruptedException;
    User findUserById(String userId) throws ExecutionException, InterruptedException;
    void updateUserAddress(String userId, String address1, String address2) throws ExecutionException, InterruptedException;
    void updateUserName(String userId, String userName) throws ExecutionException, InterruptedException;
    void deleteUser(String userId);
}
