package dev.jahid.user_auth_db_config_boilerplate.document;

import lombok.Data;

@Data
public class DocumentDTO {
    private Long id;
    private byte[] fileData;
    private String fileName;
    private String contentType;
    private DocumentType documentType;
}
