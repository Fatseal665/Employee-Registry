package se.sti.employee_registry.user.mapper;

import org.springframework.stereotype.Component;
import se.sti.employee_registry.security.rules.*;
import se.sti.employee_registry.user.CustomUser;
import se.sti.employee_registry.user.dto.CustomUserCreationDTO;
import se.sti.employee_registry.user.dto.CustomUserDetailsDTO;
import se.sti.employee_registry.user.dto.CustomUserResponseDTO;

@Component
public class CustomUserMapper {

    public CustomUser toEntity(CustomUserCreationDTO customUserCreationDTO) {

        return new CustomUser(
                customUserCreationDTO.firstName().firstName(),
                customUserCreationDTO.lastName().lastName(),
                customUserCreationDTO.email().email(),
                customUserCreationDTO.password().password(),
                customUserCreationDTO.isCredentialsNonExpired(),
                customUserCreationDTO.isAccountNonLocked(),
                customUserCreationDTO.isCredentialsNonExpired(),
                customUserCreationDTO.isEnabled(),
                customUserCreationDTO.roles().roles()
        );
    }

    public CustomUserResponseDTO toResponseDTO(CustomUser customUser) {
        return new CustomUserResponseDTO(
                new FirstnameRules(customUser.getFirstName()),
                new LastnameRules(customUser.getLastName()),
                new EmailRules(customUser.getEmail()),
                customUser.isAccountNonExistent(),
                customUser.isAccountNonLocked(),
                customUser.isCredentialsNonExpired(),
                customUser.isEnabled(),
                new RolesRules(customUser.getRoles())
        );
    }
    public CustomUserDetailsDTO toUserDetailsDTO(CustomUser customUser) {
        return new CustomUserDetailsDTO(
                new EmailRules(customUser.getEmail()),
                new PasswordRules(customUser.getPassword()),
                customUser.isAccountNonExistent(),
                customUser.isAccountNonLocked(),
                customUser.isAccountNonLocked(),
                customUser.isEnabled(),
                new RolesRules(customUser.getRoles())
        );
    }

}
