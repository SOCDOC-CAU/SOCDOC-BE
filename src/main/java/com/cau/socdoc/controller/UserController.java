package com.cau.socdoc.controller;

import com.cau.socdoc.dto.request.CreateUserDto;
import com.cau.socdoc.dto.request.UpdateUserAddressDto;
import com.cau.socdoc.dto.request.UpdateUserNameDto;
import com.cau.socdoc.dto.response.ResponseUserInfoDto;
import com.cau.socdoc.service.UserService;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.UserException;
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
    @Operation(summary = "[유저] 유저 정보 조회", description = "userId를 통해 유저 정보를 조회합니다.")
    @GetMapping("/info/user/{userId}")
    public ResponseUserInfoDto getUserInfo(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return userService.getUserInfo(userId);
    }

    // 로그인
    @Operation(summary = "[유저] 로그인", description = "Firebase Auth idToken을 통해 유저 정보를 조회합니다. 만약 유저 정보가 없다면 유저 정보를 생성하고 정보를 반환합니다.")
    @PostMapping("/login")
    public ResponseUserInfoDto login(@RequestHeader String firebaseToken) {
        // uid를 통해 유저 정보를 가져온다.
        try {
            String uid = firebaseAuth.verifyIdToken(firebaseToken).getUid();
            if (userService.getUserInfo(uid) == null) {
                userService.createUser(CreateUserDto.builder()
                        .userId(uid)
                        .userName(firebaseAuth.getUser(uid).getDisplayName())
                        .userEmail(firebaseAuth.getUser(uid).getEmail())
                        .address1("서울특별시")
                        .address2("동작구")
                        .build());
            }
            return userService.getUserInfo(uid);
        } catch (FirebaseAuthException e) {
            throw new UserException(ResponseCode.USER_NOT_FOUND);
        } catch (ExecutionException | InterruptedException e) {
            throw new UserException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 유저 주소 수정
    @Operation(summary = "[유저] 유저 주소 수정", description = "userId를 통해 유저 주소를 수정합니다.")
    @PutMapping("/update/address")
    public void updateUserAddress(@RequestBody @Valid UpdateUserAddressDto updateUserAddressDto) throws ExecutionException, InterruptedException {
        userService.updateUserAddress(updateUserAddressDto);
    }

    // 유저 이름 수정
    @Operation(summary = "[유저] 유저 이름 수정", description = "userId를 통해 유저 이름을 수정합니다.")
    @PutMapping("/update/name")
    public void updateUserName(@RequestBody @Valid UpdateUserNameDto updateUserNameDto) throws ExecutionException, InterruptedException {
        userService.updateUserName(updateUserNameDto);
    }

    // 유저 탈퇴
    @Operation(summary = "[유저] 유저 탈퇴", description = "userId를 통해 유저를 탈퇴시킵니다.")
    @DeleteMapping("/delete/user/{userId}")
    public void deleteUser(@PathVariable String userId) throws FirebaseAuthException {
        userService.deleteUser(userId);
    }
}
