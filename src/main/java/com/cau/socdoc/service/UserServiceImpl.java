package com.cau.socdoc.service;

import com.cau.socdoc.domain.User;
import com.cau.socdoc.dto.request.CreateUserDto;
import com.cau.socdoc.dto.request.UpdateUserAddressDto;
import com.cau.socdoc.dto.request.UpdateUserNameDto;
import com.cau.socdoc.dto.response.ResponseUserInfoDto;
import com.cau.socdoc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    @Transactional
    public void createUser(CreateUserDto createUserDto) {
        userRepository.createUser(createUserDto.getUserId(), createUserDto.getUserName(), createUserDto.getUserEmail(), createUserDto.getAddress1(), createUserDto.getAddress2());
    }

    @Transactional(readOnly = true)
    public ResponseUserInfoDto getUserInfo(String userId) throws ExecutionException, InterruptedException {
        User user = userRepository.findUserById(userId);
        if(user == null) {
            return null;
        }
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
    }

    @Transactional
    public void updateUserName(UpdateUserNameDto updateUserNameDto) throws ExecutionException, InterruptedException {
        userRepository.updateUserName(updateUserNameDto.getUserId(), updateUserNameDto.getUserName());
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }
}
