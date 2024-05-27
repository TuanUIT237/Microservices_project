package com.tuan.identityservice.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String password;

    @ManyToMany
    Set<Role> roles;
    @OneToMany
    @JoinColumn(name = "userid")
    List<RegistrationToken> registrationTokens;
}
