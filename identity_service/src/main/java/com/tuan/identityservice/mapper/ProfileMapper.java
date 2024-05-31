package com.tuan.identityservice.mapper;

import org.mapstruct.Mapper;

import com.tuan.identityservice.dto.ProfileDto.ProfileCreationRequest;
import com.tuan.identityservice.dto.ProfileDto.ProfileUpdateRequest;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserUpdateRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);

    ProfileUpdateRequest toProfileUpdateRequest(UserUpdateRequest request);
}
