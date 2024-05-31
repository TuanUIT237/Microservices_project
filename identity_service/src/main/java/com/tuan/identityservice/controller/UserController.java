package com.tuan.identityservice.controller;

import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.tuan.identityservice.dto.APIRespone.ApiResponse;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserResponse;
import com.tuan.identityservice.dto.UserDto.UserUpdateRequest;
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
    @GetMapping("/userinfo")
    ApiResponse<UserResponse> getUserInfo() {
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .result(userService.getUserInfo())
                .build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
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
    @PostAuthorize("returnObject.username==authentication.name")
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .result(userService.getUser(userId))
                .build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PutMapping("/update")
    UserResponse updateUser(@RequestBody UserUpdateRequest request) {
        return userService.updateUser(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @DeleteMapping("/delete")
    ApiResponse<List<String>> deleteUsers(@RequestBody List<String> request) {
        String message = "List user has been deleted successful";
        var result = userService.deleteUsers(request);
        if(result.isEmpty())
        {
            message = "No user have been deleted";
            result = null;
        }
        return ApiResponse.<List<String>>builder()
                .status("success")
                .message(message)
                .result(result)
                .build();
    }

    @PostMapping("/registration-tokens")
    public List<String> getRegistrationTokens(@RequestBody String userid) {
        return userService.getRegistrationToken(userid);
    }
}
