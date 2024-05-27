package com.tuan.identityservice.mapper;

import org.mapstruct.Mapper;

import com.tuan.identityservice.dto.PermissionDto.PermissionRequest;
import com.tuan.identityservice.dto.PermissionDto.PermissionResponse;
import com.tuan.identityservice.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
