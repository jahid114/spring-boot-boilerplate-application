package dev.jahid.user_auth_db_config_boilerplate.user.model;

import dev.jahid.user_auth_db_config_boilerplate.document.model.Document;
import dev.jahid.user_auth_db_config_boilerplate.user.Role;
import dev.jahid.user_auth_db_config_boilerplate.user.request.AddUserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Table( name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "name")
    private String name;

    @Column( name = "email" )
    private String email;

    @Column( name = "password" )
    private String password;

    @Column( name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Enumerated( EnumType.STRING )
    @Column( name = "role" )
    private Role role;

    @Column( name = "refresh_token" )
    private String refreshToken;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "profile_pic_id", foreignKey = @ForeignKey( name = "fk_user_profile_pic_id" ) )
    private Document profilePic;

    public User( AddUserRequest addUserRequest ) {
        this.name = addUserRequest.getName();
        this.email = addUserRequest.getEmail();
        this.role = addUserRequest.getRole();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass( this ) != Hibernate.getClass( o ) ) return false;
        User user = (User) o;
        return id != null && Objects.equals( id, user.id );
    }

    @Override
    public int hashCode() {
        if ( this.id == null )
            return System.identityHashCode( this );
        return Objects.hash( this.id );
    }
}
