package dev.jahid.user_auth_db_config_boilerplate.utility;

import lombok.Getter;

@Getter
public enum ProjectStatus {

    PENDING( "Pending" ),
    IN_PROGRESS( "In Progress" ),
    COMPLETED( "Completed" );

    private final String value;

    ProjectStatus( String value ) {
        this.value = value;
    }
}