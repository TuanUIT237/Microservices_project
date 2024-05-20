package com.tuan.gateway.service;

import com.tuan.gateway.dto.apiresponse.ApiResponse;
import com.tuan.gateway.dto.authenticationdto.VerifyTokenRequest;
import com.tuan.gateway.dto.authenticationdto.VerifyTokenResponse;
import com.tuan.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class IdentityService {
    IdentityClient identityClient;
    public Mono<ApiResponse<VerifyTokenResponse>> token(String token){
        return identityClient.token(VerifyTokenRequest.builder()
                .token(token)
                .build());
    }
}
