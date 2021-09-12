package com.swvalerian.springrestapi.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role4Security {
    ADMIN(Set.of(Permission.ADMIN_READ, Permission.ADMIN_WRITE)),
    MODERATOR(Set.of(Permission.ADMIN_READ, Permission.MODER_WRITE)),
    USER(Set.of(Permission.USER_READ));

    private final Set<Permission> permissions;

    Role4Security(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
