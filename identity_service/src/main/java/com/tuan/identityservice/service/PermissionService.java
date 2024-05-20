package com.tuan.identityservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tuan.identityservice.dto.PermissionDto.PermissionRequest;
import com.tuan.identityservice.dto.PermissionDto.PermissionResponse;
import com.tuan.identityservice.entity.Permission;
import com.tuan.identityservice.mapper.PermissionMapper;
import com.tuan.identityservice.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void deletePermission(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
