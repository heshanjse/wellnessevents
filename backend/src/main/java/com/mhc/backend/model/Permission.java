package com.mhc.backend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    HR_ADMIN_READ("hrAdmin:read"),
    HR_ADMIN_CREATE("hrAdmin:create"),
    VENDOR_ADMIN_READ("vendorAdmin:read"),
    VENDOR_ADMIN_UPDATE("vendorAdmin:update"),

    HR_USER_READ("hrUser:read"),
    HR_USER_CREATE("hrUser:create"),
    VENDOR_USER_READ("vendorUser:read"),
    VENDOR_USER_UPDATE("vendorUser:update");

    @Getter
    private final String permission;
}
