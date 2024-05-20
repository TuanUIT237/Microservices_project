package com.tuan.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tuan.profile.dto.userprofileDto.ProfileCreationRequest;
import com.tuan.profile.dto.userprofileDto.ProfileGetUserIdRequest;
import com.tuan.profile.dto.userprofileDto.ProfileUpdateRequest;
import com.tuan.profile.dto.userprofileDto.UserProfileResponse;
import com.tuan.profile.entity.UserProfile;
import com.tuan.profile.mapper.UserProfileMapper;
import com.tuan.profile.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse updateProfile(ProfileUpdateRequest request) {
        UserProfile userProfile = userProfileRepository
                .findById(request.getId())
                .orElseThrow(() -> new RuntimeException("User profile not existed"));
        userProfileMapper.updateProfile(userProfile, request);

        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

    public List<UserProfileResponse> getProfiles() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toUserProfileResponse)
                .toList();
    }

    public String findUserIdByName(ProfileGetUserIdRequest request) {
        int index = request.getFullName().indexOf(" ");
        String firstName = request.getFullName().substring(0, index);
        String lastName = request.getFullName().replace(firstName + " ", "");
        UserProfile userProfile = userProfileRepository.findByFirstNameAndLastNameAndCitizenIdCard(
                firstName, lastName, request.getCitizenIdCard());
        if (userProfile == null) return null;
        return userProfile.getUserId();
    }

    public String deleteProfile(String id) {
        userProfileRepository.deleteById(id);
        return "Profile has been deleted";
    }
}
