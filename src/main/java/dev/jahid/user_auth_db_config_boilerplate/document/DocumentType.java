package dev.jahid.user_auth_db_config_boilerplate.document;

import java.util.HashMap;
import java.util.Map;

public enum DocumentType {

    PROFILE_PHOTO( 0 ),
    PROJECT_CERTIFICATE( 1 ),
    PROJECT_DOCUMENT( 2 ),
    SUPPLIER_INVOICE( 3 ),
    CONTRACTOR_DOCUMENT( 4 ),
    USER_ATTACHMENT( 5 ),
    PG_DOCUMENT( 6 ),
    ;

    private final Integer value;
    private static Map<DocumentType, String> documentTypeStringMap = new HashMap<>();

    static {

        documentTypeStringMap.put( DocumentType.PROFILE_PHOTO, "profile_pic/" );
    }

    DocumentType( Integer val ){
        this.value = val;
    }

    public String getDirectory(){
        return DocumentType.documentTypeStringMap.getOrDefault( this, "" );
    }

    public Integer getValue(){
        return value;
    }
}
