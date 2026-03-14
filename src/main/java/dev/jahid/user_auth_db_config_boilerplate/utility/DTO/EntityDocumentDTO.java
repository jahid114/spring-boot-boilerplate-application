package dev.jahid.user_auth_db_config_boilerplate.utility.DTO;

import dev.jahid.user_auth_db_config_boilerplate.document.model.Document;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EntityDocumentDTO {
    private Long id;
    private Document profilePic;
}
