package com.tuan.identityservice.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tuan.identityservice.constant.PredefinedRole;
import com.tuan.identityservice.entity.Role;
import com.tuan.identityservice.entity.User;
import com.tuan.identityservice.repository.RoleRepository;
import com.tuan.identityservice.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername(PredefinedRole.ADMIN_ROLE).isEmpty()) {
                var roles = new HashSet<Role>();
                roleRepository.save(
                        Role.builder().name(PredefinedRole.USER_ROLE).build());

                Role adminRole = Role.builder().name(PredefinedRole.ADMIN_ROLE).build();
                roleRepository.save(adminRole);

                roles.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("Admin user has been created with default password: please change!");
            }
        };
    }
}
