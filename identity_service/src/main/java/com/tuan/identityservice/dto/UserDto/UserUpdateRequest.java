package com.tuan.identityservice.dto.UserDto;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String userId;
    String password;
    String firstName;
    String lastName;
    List<String> roles;
}
