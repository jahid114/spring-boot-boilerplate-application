package dev.jahid.user_auth_db_config_boilerplate.document.response;

import dev.jahid.user_auth_db_config_boilerplate.document.DocumentType;
import dev.jahid.user_auth_db_config_boilerplate.document.model.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private String fileName;
    private String fileURL;
    private DocumentType documentType;
    
    public DocumentResponse( Document document, String fileURL ) {
        this.id = document.getId();
        this.fileName = document.getFileName();
        this.fileURL = fileURL;
        this.documentType = document.getDocumentType();
    }
}