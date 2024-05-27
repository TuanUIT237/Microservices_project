package com.tuan.ebankservice.service;

import com.tuan.ebankservice.dto.userdto.UserCreationRequest;
import com.tuan.ebankservice.repository.httpclient.UserClient;
import com.tuan.ebankservice.util.StringUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserClient userClient;
    public String createUser(String name, String passwordRandom, String email, String citizenIdCard){
        String username = name.replace(" ","");
        String firstName = name.substring(0,name.indexOf(" "));
        String lastName = name.replace(firstName + " ","");
        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .citizenIdCard(citizenIdCard)
                .password(passwordRandom)
                .build();
        var userResponse = userClient.createUser(userCreationRequest);
        return userResponse.getResult().getId();
    }
    public List<String> getRegistrationTokens(String userid){
        return userClient.getRegistrationTokens(userid);
    }
}
