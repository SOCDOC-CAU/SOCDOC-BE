package com.cau.socdoc.service;

import com.cau.socdoc.dto.request.CreateUserDto;
import com.cau.socdoc.dto.request.UpdateUserAddressDto;
import com.cau.socdoc.dto.request.UpdateUserNameDto;
import com.cau.socdoc.dto.response.ResponseUserInfoDto;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.concurrent.ExecutionException;

public interface UserService {

    void createUser(CreateUserDto createUserDto);
    ResponseUserInfoDto getUserInfo(String userId) throws ExecutionException, InterruptedException;
    void updateUserAddress(UpdateUserAddressDto updateUserAddressDto) throws ExecutionException, InterruptedException;
    void updateUserName(UpdateUserNameDto updateUserNameDto) throws ExecutionException, InterruptedException;
    void deleteUser(String userId) throws FirebaseAuthException;
}
