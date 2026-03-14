package dev.jahid.user_auth_db_config_boilerplate.utility.enums;

import lombok.Getter;

@Getter
public enum ApprovalStatus {

    APPROVED( "Approved" ),
    REJECTED( "Rejected" ),
    PENDING( "Pending" );

    private final String value;

    ApprovalStatus( String value ) {
        this.value = value;
    }
}