package dev.jahid.user_auth_db_config_boilerplate.utility.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private String message;
    private int status;
    private T data;

    public ApiResponse( String message, int status, T data ) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ApiResponse( String message, T data ) {
        this.message = message;
        this.data = data;
        this.status = HttpStatus.OK.value();
    }

    public ApiResponse( String message, int status ) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse( String message ) {
        this.message = message;
        this.status = HttpStatus.OK.value();
    }
}
