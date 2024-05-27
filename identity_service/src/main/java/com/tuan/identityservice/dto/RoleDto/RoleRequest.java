package com.tuan.identityservice.dto.RoleDto;

import java.util.Set;

import com.tuan.identityservice.entity.Permission;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String name;
    String description;
    Set<Permission> permission;
}
