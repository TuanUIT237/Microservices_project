package com.tuan.identityservice.controller;

import com.tuan.identityservice.dto.APIRespone.ApiResponse;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserResponse;
import com.tuan.identityservice.dto.UserDto.UserUpdateRequest;
import com.tuan.identityservice.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    ApiResponse<UserResponse> createEmployee(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .result(employeeService.createEmployee(request))
                .build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/")
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .status("success")
                .result(employeeService.getEmployees())
                .build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .result(employeeService.getEmployee(userId))
                .build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update")
    UserResponse updateUser(@RequestBody UserUpdateRequest request) {
        return employeeService.updateEmployee(request);
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @DeleteMapping("/delete")
    ApiResponse<List<String>> deleteUsers(@RequestBody List<String> request) {
        String message = "List employee has been deleted successful";
        var result = employeeService.deleteEmployees(request);
        if(result.isEmpty())
        {
            message = "No employee have been deleted";
            result = null;
        }
        return ApiResponse.<List<String>>builder()
                .status("success")
                .message(message)
                .result(result)
                .build();
    }

}
