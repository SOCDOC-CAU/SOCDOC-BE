package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.CreateUserDto;
import com.cau.socdoc.dto.request.UpdateUserAddressDto;
import com.cau.socdoc.dto.request.UpdateUserNameDto;
import com.cau.socdoc.dto.response.ResponseUserInfoDto;
import com.cau.socdoc.service.UserService;
import com.cau.socdoc.util.api.ApiResponse;
import com.cau.socdoc.util.api.ResponseCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@Api(tags = "user")
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    // 유저 정보 조회
    @Operation(summary = "[유저] 유저 정보 조회", description = "Firebase uid인 userId를 통해 유저 정보를 조회합니다.")
    @GetMapping
    public ApiResponse<ResponseUserInfoDto> getUserInfo(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return ApiResponse.success(userService.getUserInfo(userId), ResponseCode.USER_READ_SUCCESS.getMessage());
    }

    // 로그인
    @Operation(summary = "[유저] 로그인", description = "Firebase Auth에서 발급받는 authToken을 통해 유저 정보를 조회하고 로그인합니다. 만약 유저 정보가 없다면 유저 정보를 새로 생성하고 정보를 반환합니다.")
    @PostMapping("/login")
    public ApiResponse<ResponseUserInfoDto> login(@RequestHeader String authToken) throws FirebaseAuthException, ExecutionException, InterruptedException{
        // uid를 통해 유저 정보를 가져온다.
        String uid = firebaseAuth.verifyIdToken(authToken).getUid();
        if (userService.getUserInfo(uid) == null) { // 가입되어 있지 않다면 신규 회원 가입
            userService.createUser(CreateUserDto.builder()
                    .userId(uid)
                    .userName(firebaseAuth.getUser(uid).getDisplayName())
                    .userEmail(firebaseAuth.getUser(uid).getEmail())
                    .address1("서울특별시") // 기본 주소는 서울특별시 동작구
                    .address2("동작구")
                    .build());
        }
        return ApiResponse.success(userService.getUserInfo(uid), ResponseCode.USER_LOGIN_SUCCESS.getMessage());
    }

    // 유저 주소 수정
    @Operation(summary = "[유저] 유저 주소 수정", description = "유저 주소를 수정합니다.")
    @PutMapping("/update/address")
    public ApiResponse<Void> updateUserAddress(@RequestBody @Valid UpdateUserAddressDto updateUserAddressDto) throws ExecutionException, InterruptedException {
        userService.updateUserAddress(updateUserAddressDto);
        return ApiResponse.success(null, ResponseCode.USER_UPDATE_SUCCESS.getMessage());
    }

    // 유저 이름 수정
    @Operation(summary = "[유저] 유저 이름 수정", description = "유저 이름을 수정합니다.")
    @PutMapping("/update/name")
    public ApiResponse<Void> updateUserName(@RequestBody @Valid UpdateUserNameDto updateUserNameDto) throws ExecutionException, InterruptedException {
        userService.updateUserName(updateUserNameDto);
        return ApiResponse.success(null, ResponseCode.USER_UPDATE_SUCCESS.getMessage());
    }

    // 유저 탈퇴
    @Operation(summary = "[유저] 유저 탈퇴", description = "유저를 탈퇴시킵니다.")
    @DeleteMapping
    public ApiResponse<Void> deleteUser(@RequestParam String userId) throws FirebaseAuthException {
        userService.deleteUser(userId);
        return ApiResponse.success(null, ResponseCode.USER_DELETE_SUCCESS.getMessage());
    }
}
