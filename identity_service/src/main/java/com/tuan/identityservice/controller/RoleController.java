package com.tuan.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tuan.identityservice.dto.APIRespone.ApiResponse;
import com.tuan.identityservice.dto.RoleDto.RoleRequest;
import com.tuan.identityservice.dto.RoleDto.RoleResponse;
import com.tuan.identityservice.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping("/create")
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .status("success")
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping("")
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .status("success")
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/delete")
    ApiResponse<String> deleteRole(@RequestBody String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.<String>builder()
                .status("success")
                .result("Role has been deleted")
                .build();
    }
}
