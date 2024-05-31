package com.tuan.profile.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.tuan.profile.dto.userprofileDto.ProfileUpdateRequest;
import com.tuan.profile.dto.userprofileDto.UserProfileResponse;
import com.tuan.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/{profileid}")
    UserProfileResponse getProfile(@PathVariable String profileid) {
        return userProfileService.getProfile(profileid);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    List<UserProfileResponse> getProfiles() {
        return userProfileService.getProfiles();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    UserProfileResponse updateProfiles(@RequestBody ProfileUpdateRequest request) {
        return userProfileService.updateProfile(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    void deleteProfiles(@RequestBody List<String> request) {
        userProfileService.deleteProfile(request);
    }
}
