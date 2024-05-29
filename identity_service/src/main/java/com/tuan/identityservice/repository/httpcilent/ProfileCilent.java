package com.tuan.identityservice.repository.httpcilent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.tuan.identityservice.configuration.AuthenticationRequestInterceptor;
import com.tuan.identityservice.dto.ProfileDto.ProfileCreationRequest;
import com.tuan.identityservice.dto.ProfileDto.ProfileUpdateRequest;
import com.tuan.identityservice.dto.ProfileDto.UserProfileResponse;

import java.util.List;

// don't set domain in url because it isn't run in other environment
@FeignClient(
        name = "profile-service",
        url = "${app.services.profile}",
        configuration = AuthenticationRequestInterceptor.class)
public interface ProfileCilent {
    @PostMapping(value = "/internal/create", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request);

    @PostMapping(value = "/internal/emailvalid", produces = MediaType.APPLICATION_JSON_VALUE)
    boolean emailExisted(@RequestBody String email);

    @PostMapping(value = "/internal/user", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse getProfileByUserId(@RequestBody String userId);

    @PutMapping(value = "/users/update", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse updateProfiles(@RequestBody ProfileUpdateRequest request);
    @DeleteMapping(value = "/users/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    String deleteProfiles(@RequestBody List<String> request);
}
