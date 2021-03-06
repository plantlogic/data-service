package edu.csumb.spring19.capstone.models.authentication;

import org.springframework.security.core.GrantedAuthority;

public enum PLRole implements GrantedAuthority {
    APP_ADMIN,
    CONTRACTOR_EDIT,
    CONTRACTOR_VIEW,
    DATA_EDIT,
    DATA_ENTRY,
    DATA_VIEW,
    IRRIGATOR,
    SHIPPER,
    TH_EDIT,
    TH_VIEW,
    USER_MANAGEMENT;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
