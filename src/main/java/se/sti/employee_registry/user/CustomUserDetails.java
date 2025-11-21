package se.sti.employee_registry.user;

import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import se.sti.employee_registry.user.dto.CustomUserDetailsDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final CustomUserDetailsDTO customUser;

    public CustomUserDetails(CustomUserDetailsDTO customUser) {
        this.customUser = customUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> authorities = new HashSet<>();

        customUser.roles().roles().forEach(
                userRole -> authorities.addAll(userRole.getGrantedAuthorities())
        );
        return Collections.unmodifiableSet(authorities);
    }

    @Override
    public String getPassword() {
        return customUser.password().password();
    }

    @Override
    public String getUsername() {
        return customUser.email().email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customUser.isAccountNonExistent();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return customUser.isEnabled();
    }

    public CustomUserDetailsDTO getCustomUser() {
        return customUser;
    }

}
