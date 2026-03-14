package dev.jahid.user_auth_db_config_boilerplate.utility.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
public class AuditableEntity {

    @CreatedDate
    @Column( name = "created_at" )
    private LocalDateTime createdAt;

    @CreatedBy
    @Column( name = "created_by" )
    private Long createdBy;

    @LastModifiedDate
    @Column( name = "modified_at" )
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column( name = "modified_by" )
    private Long modifiedBy;

    @Column( name = "is_deleted" )
    private Boolean isDeleted = false;
}