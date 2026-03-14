package dev.jahid.user_auth_db_config_boilerplate.utility.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse {
    private Long id;
    private String name;
}
