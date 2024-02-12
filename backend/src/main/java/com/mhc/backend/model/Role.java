package com.mhc.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static com.mhc.backend.model.Permission.HR_ADMIN_READ;
import static com.mhc.backend.model.Permission.HR_ADMIN_CREATE;
import static com.mhc.backend.model.Permission.VENDOR_ADMIN_READ;
import static com.mhc.backend.model.Permission.VENDOR_ADMIN_UPDATE;
import static com.mhc.backend.model.Permission.HR_USER_READ;
import static com.mhc.backend.model.Permission.HR_USER_CREATE;
import static com.mhc.backend.model.Permission.VENDOR_USER_READ;
import static com.mhc.backend.model.Permission.VENDOR_USER_UPDATE;


@RequiredArgsConstructor
public enum Role {

    HR_ADMIN(Set.of(
            HR_ADMIN_READ,
            HR_ADMIN_CREATE
    )),
    VENDOR_ADMIN(Set.of(
            VENDOR_ADMIN_READ,
            VENDOR_ADMIN_UPDATE
    )),
    HR_USER(Set.of(
            HR_USER_READ,
            HR_USER_CREATE
    )),
    VENDOR_USER(Set.of(
            VENDOR_USER_READ,
            VENDOR_USER_UPDATE
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }


}
