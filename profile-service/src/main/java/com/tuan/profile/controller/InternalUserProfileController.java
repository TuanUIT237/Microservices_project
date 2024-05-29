package com.tuan.profile.controller;

import org.springframework.web.bind.annotation.*;

import com.tuan.profile.dto.userprofileDto.ProfileCreationRequest;
import com.tuan.profile.dto.userprofileDto.ProfileGetUserIdRequest;
import com.tuan.profile.dto.userprofileDto.UserProfileResponse;
import com.tuan.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/create")
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }

    @PostMapping("/userid")
    String getUserId(@RequestBody ProfileGetUserIdRequest request) {
        return userProfileService.findUserIdByName(request);
    }

    @PostMapping("/emailvalid")
    boolean emailExisted(@RequestBody String email) {
        return userProfileService.findEmailExisted(email);
    }

    @PostMapping("/user")
    UserProfileResponse getProfileByUserId(@RequestBody String userId) {
        return userProfileService.getProfileBtUserId(userId);
    }
}
