package dev.jahid.user_auth_db_config_boilerplate.document.model;

import dev.jahid.user_auth_db_config_boilerplate.document.DocumentType;
import dev.jahid.user_auth_db_config_boilerplate.utility.audit.AuditableEntity;
import dev.jahid.user_auth_db_config_boilerplate.utility.enums.EntityName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Table( name = "documents" )
@Entity
@Data
public class Document extends AuditableEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column( name = "file_name", length = 300 )
    private String fileName;

    @Column( name = "base_directory", length = 500 )
    private String baseDirectory;

    @Column( name = "sub_directory", length = 500)
    private String subDirectory;

    @Column( name = "content_type" )
    private String contentType;

    @Enumerated( EnumType.STRING )
    @Column( name = "uploaded_for_entity" )
    private EntityName uploadedForEntity;

    @Column( name = "uploaded_for_id" )
    private Long uploadedForId;

    @Enumerated( EnumType.STRING )
    @Column( name = "document_type" )
    private DocumentType documentType;


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Document that = ( Document ) o;
        return id != null && id.equals( that.id );
    }

    @Override
    public int hashCode() {
        if ( id == null )
            return System.identityHashCode( this );
        return Objects.hash( id );
    }
}
