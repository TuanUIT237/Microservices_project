package com.tuan.identityservice.mapper;

import org.mapstruct.Mapper;

import com.tuan.identityservice.dto.RoleDto.RoleRequest;
import com.tuan.identityservice.dto.RoleDto.RoleResponse;
import com.tuan.identityservice.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
