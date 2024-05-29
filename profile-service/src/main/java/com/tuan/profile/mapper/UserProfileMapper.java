package com.tuan.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tuan.profile.dto.userprofileDto.ProfileCreationRequest;
import com.tuan.profile.dto.userprofileDto.ProfileUpdateRequest;
import com.tuan.profile.dto.userprofileDto.UserProfileResponse;
import com.tuan.profile.entity.UserProfile;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile user);
    void updateProfile(@MappingTarget UserProfile userProfile, ProfileUpdateRequest request);
}
