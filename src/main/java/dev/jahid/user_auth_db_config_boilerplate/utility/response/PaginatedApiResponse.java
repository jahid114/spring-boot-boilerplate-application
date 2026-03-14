package dev.jahid.user_auth_db_config_boilerplate.utility.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PaginatedApiResponse<T>{

    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private String message;
    private Integer status;
    private T data;

    public PaginatedApiResponse( T data, Integer currentPage, Integer totalPages, Long totalElements ) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
        this.message = "Successfully fetched data";
        this.status = HttpStatus.OK.value();
    }
}
