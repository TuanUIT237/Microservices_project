package com.tuan.identityservice.mapper;

import org.mapstruct.Mapper;

import com.tuan.identityservice.dto.ProfileDto.ProfileCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
