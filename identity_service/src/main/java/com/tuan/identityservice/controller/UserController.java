package com.tuan.identityservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.tuan.identityservice.dto.APIRespone.ApiResponse;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserResponse;
import com.tuan.identityservice.dto.UserDto.UserUpdateRequest;
import com.tuan.identityservice.dto.UserDto.UsersDeleteRequest;
import com.tuan.identityservice.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/")
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .status("success")
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .result(userService.getUser(userId))
                .build();
    }

    @PutMapping("/update")
    UserResponse updateUser(@RequestBody UserUpdateRequest request) {
        return userService.updateUser(request);
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .status("success")
                .result("User has been deleted")
                .build();
    }

    @PostMapping("/delete")
    ApiResponse<String> deleteUsers(@RequestBody UsersDeleteRequest request) {
        userService.deleteUsers(request);
        return ApiResponse.<String>builder()
                .status("success")
                .result("Users have been deleted")
                .build();
    }

    @PostMapping("/registrationtokens")
    public List<String> getRegistrationTokens(@RequestBody String userid) {
        return userService.getRegistrationToken(userid);
    }
}
