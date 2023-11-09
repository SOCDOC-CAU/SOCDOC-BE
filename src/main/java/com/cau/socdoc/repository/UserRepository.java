package com.cau.socdoc.repository;

import com.cau.socdoc.domain.User;

public interface UserRepository {

    String createUser(String userId, String userName, String userEmail, String address1, String address2);
    String findNameById(String userId);
    User findUserById(String userId);
    void updateUserAddress(String userId, String address1, String address2);
    void updateUserName(String userId, String userName);
    void deleteUser(String userId);
}
