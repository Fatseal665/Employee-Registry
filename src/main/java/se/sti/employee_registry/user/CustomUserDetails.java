package se.sti.employee_registry.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.sti.employee_registry.user.dto.CustomUserDetailsDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final CustomUserDetailsDTO customUserDTO;

    public CustomUserDetails(CustomUserDetailsDTO customUserDTO) {
        this.customUserDTO = customUserDTO;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> authorities = new HashSet<>();

        customUserDTO.roles().value().forEach(
                userRole -> authorities.addAll(userRole.getGrantedAuthorities())
        );
        return Collections.unmodifiableSet(authorities);
    }

    @Override
    public String getPassword() {
        return customUserDTO.password().value();
    }

    @Override
    public String getUsername() {
        return customUserDTO.email().value();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customUserDTO.isAccountNonExistent();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customUserDTO.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customUserDTO.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return customUserDTO.isEnabled();
    }

    public CustomUserDetailsDTO getCustomUserDTO() {
        return customUserDTO;
    }

}
