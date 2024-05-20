package com.tuan.gateway.repository;

import com.tuan.gateway.dto.apiresponse.ApiResponse;
import com.tuan.gateway.dto.authenticationdto.VerifyTokenRequest;
import com.tuan.gateway.dto.authenticationdto.VerifyTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient{
    @PostExchange(url = "/auth/token", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<VerifyTokenResponse>> token(@RequestBody VerifyTokenRequest request);
}
