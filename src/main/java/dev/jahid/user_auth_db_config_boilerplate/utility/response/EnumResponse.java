package dev.jahid.user_auth_db_config_boilerplate.utility.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnumResponse {

    private String name;
    private String constant;
}