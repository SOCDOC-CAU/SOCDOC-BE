package com.cau.socdoc.service.impl;

import com.cau.socdoc.domain.User;
import com.cau.socdoc.dto.request.CreateUserDto;
import com.cau.socdoc.dto.request.UpdateUserAddressDto;
import com.cau.socdoc.dto.request.UpdateUserNameDto;
import com.cau.socdoc.dto.response.ResponseUserInfoDto;
import com.cau.socdoc.repository.UserRepository;
import com.cau.socdoc.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Transactional
    public void createUser(CreateUserDto createUserDto) {
        userRepository.createUser(createUserDto.getUserId(), createUserDto.getUserName(), createUserDto.getUserEmail(), createUserDto.getAddress1(), createUserDto.getAddress2());
        log.info("유저 생성 완료: " + createUserDto.getUserId());
    }

    @Transactional(readOnly = true)
    public boolean existsUserById(String userId) throws ExecutionException, InterruptedException {
        return userRepository.existsUserById(userId);
    }

    @Transactional(readOnly = true)
    public ResponseUserInfoDto getUserInfo(String userId) throws ExecutionException, InterruptedException {
        User user = userRepository.findUserById(userId);
        log.info("유저 정보 조회 완료: " + userId);
        return ResponseUserInfoDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .address1(user.getAddress1())
                .address2(user.getAddress2())
                .build();
    }

    @Transactional
    public void updateUserAddress(UpdateUserAddressDto updateUserAddressDto) throws ExecutionException, InterruptedException {
        userRepository.updateUserAddress(updateUserAddressDto.getUserId(), updateUserAddressDto.getAddress1(), updateUserAddressDto.getAddress2());
        log.info("유저 주소 수정 완료: " + updateUserAddressDto.getAddress1() + " " + updateUserAddressDto.getAddress2());
    }

    @Transactional
    public void updateUserName(UpdateUserNameDto updateUserNameDto) throws ExecutionException, InterruptedException {
        userRepository.updateUserName(updateUserNameDto.getUserId(), updateUserNameDto.getUserName());
        log.info("유저 이름 수정 완료: " + updateUserNameDto.getUserName());
    }

    @Transactional
    public void deleteUser(String userId) throws FirebaseAuthException {
        userRepository.deleteUser(userId);
        log.info("유저 탈퇴 완료: " + userId);
    }
}
