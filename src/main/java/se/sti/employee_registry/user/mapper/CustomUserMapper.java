package se.sti.employee_registry.user.mapper;

import org.springframework.stereotype.Component;
import se.sti.employee_registry.user.CustomUser;
import se.sti.employee_registry.user.dto.CustomUserCreationDTO;
import se.sti.employee_registry.user.dto.CustomUserResponseDTO;

@Component
public class CustomUserMapper {

    public CustomUser toEntity(CustomUserCreationDTO customUserCreationDTO) {

        return new CustomUser(
                customUserCreationDTO.firstName(),
                customUserCreationDTO.lastName(),
                customUserCreationDTO.email(),
                customUserCreationDTO.password(),
                customUserCreationDTO.isCredentialsNonExpired(),
                customUserCreationDTO.isAccountNonLocked(),
                customUserCreationDTO.isCredentialsNonExpired(),
                customUserCreationDTO.isEnabled(),
                customUserCreationDTO.roles()
        );
    }

    public CustomUserResponseDTO toResponseDTO(CustomUser customUser) {
        return new CustomUserResponseDTO(
                customUser.getFirstName(),
                customUser.getLastName(),
                customUser.getEmail()
        );
    }

}
