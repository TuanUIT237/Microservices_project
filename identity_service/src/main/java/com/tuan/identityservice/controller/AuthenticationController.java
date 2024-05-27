package com.tuan.identityservice.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.tuan.identityservice.dto.APIRespone.ApiResponse;
import com.tuan.identityservice.dto.AutheticationDto.*;
import com.tuan.identityservice.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .status("success")
                .result(result)
                .build();
    }

    @PostMapping("/token")
    ApiResponse<VerifyTokenResponse> token(@RequestBody VerifyTokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.token(request);
        return ApiResponse.<VerifyTokenResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<String>builder()
                .status("success")
                .result("You have successfully logged out")
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .status("success")
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
