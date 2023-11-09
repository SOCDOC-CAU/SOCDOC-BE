package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.CreateUserDto;
import com.cau.socdoc.dto.request.UpdateUserAddressDto;
import com.cau.socdoc.dto.request.UpdateUserNameDto;
import com.cau.socdoc.dto.response.ResponseUserInfoDto;
import com.cau.socdoc.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    // 유저 정보 조회
    @GetMapping("/info/user/{userId}")
    public ResponseUserInfoDto getUserInfo(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return userService.getUserInfo(userId);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseUserInfoDto login(String idToken) throws FirebaseAuthException, ExecutionException, InterruptedException {
        // uid를 통해 유저 정보를 가져온다.
        String uid = firebaseAuth.verifyIdToken(idToken).getUid();

        // 유저 정보가 없다면 유저 정보를 생성한다.
        if(userService.getUserInfo(uid) == null) {
            userService.createUser(CreateUserDto.builder()
                    .userId(uid)
                    .userName(firebaseAuth.getUser(uid).getDisplayName())
                    .userEmail(firebaseAuth.getUser(uid).getEmail())
                    .address1("서울")
                    .address2("동작")
                    .build());
        }
        return userService.getUserInfo(uid);
    }

    // 유저 주소 수정
    @PutMapping("/update/address")
    public void updateUserAddress(UpdateUserAddressDto updateUserAddressDto) throws ExecutionException, InterruptedException {
        userService.updateUserAddress(updateUserAddressDto);
    }

    // 유저 이름 수정
    @PutMapping("/update/name")
    public void updateUserName(UpdateUserNameDto updateUserNameDto) throws ExecutionException, InterruptedException {
        userService.updateUserName(updateUserNameDto);
    }

    // 유저 탈퇴
    @DeleteMapping("/delete/user/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
}
