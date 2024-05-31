package com.tuan.ebankservice.service;

import java.util.List;

import com.tuan.ebankservice.dto.userprofiledto.ProfileGetUserIdRequest;
import com.tuan.ebankservice.repository.httpclient.ProfileClient;
import com.tuan.ebankservice.util.StringUtil;
import org.springframework.stereotype.Service;

import com.tuan.ebankservice.dto.userdto.UserCreationRequest;
import com.tuan.ebankservice.repository.httpclient.UserClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserClient userClient;
    ProfileClient profileClient;
    public List<String> createUser(ProfileGetUserIdRequest profileGetUserIdRequest, UserCreationRequest userCreationRequest,String name) {
        String userId = profileClient.getUserId(profileGetUserIdRequest);
        String passwordRandom = null;
        if (!(StringUtils.hasText(userId))) {
            passwordRandom = StringUtil.getRandomNumberAsString(6);
        }
        String username = name.replace(" ", "");
        String firstName = name.substring(0, name.indexOf(" "));
        String lastName = name.replace(firstName + " ", "");
        userCreationRequest.setPassword(passwordRandom);
        userCreationRequest.setFirstName(firstName);
        userCreationRequest.setLastName(lastName);
        userCreationRequest.setUsername(username);
        var userResponse = userClient.createUser(userCreationRequest);
        return List.of(userResponse.getResult().getId(),passwordRandom);
    }

    public List<String> getRegistrationTokens(String userid) {
        return userClient.getRegistrationTokens(userid);
    }
}
