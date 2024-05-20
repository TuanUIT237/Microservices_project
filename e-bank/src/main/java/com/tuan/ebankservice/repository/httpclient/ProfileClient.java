package com.tuan.ebankservice.repository.httpclient;

import com.tuan.ebankservice.configuration.AuthenticationRequestInterceptor;
import com.tuan.ebankservice.dto.userprofiledto.ProfileGetUserIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "profile-service",url = "${app.services.profile}")
public interface ProfileClient {
    @PostMapping(value = "/internal/userid", produces = MediaType.APPLICATION_JSON_VALUE)
    String getUserId(@RequestBody ProfileGetUserIdRequest request);
}
