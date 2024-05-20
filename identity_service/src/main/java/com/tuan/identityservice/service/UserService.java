package com.tuan.identityservice.service;

import java.util.HashSet;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuan.identityservice.constant.PredefinedRole;
import com.tuan.identityservice.dto.ProfileDto.UserProfileResponse;
import com.tuan.identityservice.dto.UserDto.UserCreationRequest;
import com.tuan.identityservice.dto.UserDto.UserResponse;
import com.tuan.identityservice.dto.UserDto.UserUpdateRequest;
import com.tuan.identityservice.dto.UserDto.UsersDeleteRequest;
import com.tuan.identityservice.entity.Role;
import com.tuan.identityservice.entity.User;
import com.tuan.identityservice.exception.AppException;
import com.tuan.identityservice.exception.ErrorCode;
import com.tuan.identityservice.mapper.ProfileMapper;
import com.tuan.identityservice.mapper.UserMapper;
import com.tuan.identityservice.repository.RoleRepository;
import com.tuan.identityservice.repository.UserRepository;
import com.tuan.identityservice.repository.httpcilent.ProfileCilent;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ProfileCilent profileCilent;
    ProfileMapper profileMapper;

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.builder().name(PredefinedRole.USER_ROLE).build());
        user.setRoles(roles);
        userRepository.save(user);

        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());
        UserProfileResponse userProfileResponse = profileCilent.createProfile(profileRequest);

        UserResponse userResponse = userMapper.toUserResponse(user);
        userMapper.toUserResponsefromUserProfile(userResponse, userProfileResponse);
        return userResponse;
    }

    public UserResponse getUserInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UserUpdateRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('admin')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map((userMapper::toUserResponse))
                .toList();
    }

    @PostAuthorize("returnObject.username==authentication.name")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public void deleteUsers(UsersDeleteRequest request) {
        request.getUserids().forEach(userRepository::deleteById);
    }
}
