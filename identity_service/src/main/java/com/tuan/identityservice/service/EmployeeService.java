package com.tuan.identityservice.service;
import com.tuan.identityservice.constant.PredefinedRole;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserResponse;
import com.tuan.identityservice.dto.UserDto.UserUpdateRequest;
import com.tuan.identityservice.entity.Role;
import com.tuan.identityservice.entity.User;
import com.tuan.identityservice.exception.AppException;
import com.tuan.identityservice.exception.ErrorCode;
import com.tuan.identityservice.mapper.UserMapper;
import com.tuan.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmployeeService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createEmployee(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.builder().name(PredefinedRole.EMPLOYEE_ROLE).build());
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }


    public UserResponse updateEmployee(UserUpdateRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(!Objects.isNull(request.getPassword()))
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }


    public List<UserResponse> getEmployees() {
        return userRepository.findAll().stream()
                .map((userMapper::toUserResponse))
                .toList();
    }


    public UserResponse getEmployee(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<String> deleteEmployees(List<String> request) {
        List<String> deleteEmployeeSuccess = new ArrayList<>();
        request.forEach(id ->{
            boolean isFound;
            isFound = userRepository.existsById(id);
            if(isFound){
                userRepository.deleteById(id);
                deleteEmployeeSuccess.add(id);
            }
        });
        return deleteEmployeeSuccess;
    }

}
