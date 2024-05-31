package com.tuan.ebankservice.repository.httpclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tuan.ebankservice.dto.apiresponse.ApiResponse;
import com.tuan.ebankservice.dto.userdto.UserCreationRequest;
import com.tuan.ebankservice.dto.userdto.UserResponse;

@FeignClient(name = "identity-service", url = "${app.services.identity}")
public interface UserClient {
    @PostMapping(value = "/users/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request);

    @PostMapping(value = "/users/registration-tokens", produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getRegistrationTokens(@RequestBody String userid);
}
