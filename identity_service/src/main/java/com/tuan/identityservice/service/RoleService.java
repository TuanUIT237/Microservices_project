package com.tuan.identityservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tuan.identityservice.dto.RoleDto.RoleRequest;
import com.tuan.identityservice.dto.RoleDto.RoleResponse;
import com.tuan.identityservice.entity.Role;
import com.tuan.identityservice.mapper.RoleMapper;
import com.tuan.identityservice.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
