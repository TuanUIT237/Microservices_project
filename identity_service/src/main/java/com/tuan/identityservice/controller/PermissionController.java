package com.tuan.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tuan.identityservice.dto.APIRespone.ApiResponse;
import com.tuan.identityservice.dto.PermissionDto.PermissionRequest;
import com.tuan.identityservice.dto.PermissionDto.PermissionResponse;
import com.tuan.identityservice.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/create")
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .status("success")
                .result(permissionService.createPermission(request))
                .build();
    }

    @GetMapping("")
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .status("success")
                .result(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/delete")
    ApiResponse<String> deletePermission(@RequestBody String permissionName) {
        permissionService.deletePermission(permissionName);
        return ApiResponse.<String>builder()
                .status("success")
                .result("Permission has been deleted")
                .build();
    }
}
