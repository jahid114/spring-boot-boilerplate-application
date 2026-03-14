package dev.jahid.user_auth_db_config_boilerplate.utility.audit;

import dev.jahid.user_auth_db_config_boilerplate.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( authentication == null || !authentication.isAuthenticated() )
            return Optional.empty();

        if( authentication.getPrincipal().getClass().equals( CustomUserDetails.class ) &&
                ( (CustomUserDetails) authentication.getPrincipal() ).getId() != null )
            return Optional.of( ( (CustomUserDetails) authentication.getPrincipal() ).getId() );

        return Optional.empty();
    }
}
