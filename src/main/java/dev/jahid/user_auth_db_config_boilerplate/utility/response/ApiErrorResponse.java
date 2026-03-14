package dev.jahid.user_auth_db_config_boilerplate.utility.response;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private String timestamp;

    public ApiErrorResponse( int status, String error, String message ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}

