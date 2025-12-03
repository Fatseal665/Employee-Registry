package se.sti.employee_registry.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import se.sti.employee_registry.security.rules.*;


public record CustomUserCreationDTO(
        @Valid
        FirstnameRules firstName,

        @Valid
        LastnameRules lastName,

        @Valid
        EmailRules email,

        @Valid
        PasswordRules password,

        @NotNull boolean isAccountNonExistent,
        @NotNull boolean isAccountNonLocked,
        @NotNull boolean isCredentialsNonExpired,
        @NotNull boolean isEnabled,

        @Valid
        RolesRules roles

) {}
