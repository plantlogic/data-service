package edu.csumb.spring19.capstone.models.authentication;

import org.springframework.security.core.GrantedAuthority;

public enum PLRole implements GrantedAuthority {
    DATA_ENTRY, DATA_VIEW, DATA_EDIT, USER_MANAGEMENT, APP_ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}