package se.sti.employee_registry.user.dto;

import jakarta.validation.constraints.*;
import se.sti.employee_registry.user.authority.UserRole;

import java.util.Set;

public record CustomUserCreationDTO(
        @Size(min = 2,max = 25, message = "Too long/short first name")
        @NotBlank(message = "Can't be blank")
        String firstName,

        @Size(min = 2,max = 25, message = "Too long/short last name")
        @NotBlank(message = "Can't be blank")
        String lastName,

        @Size(min = 2,max = 25, message = "Too long/short email")
        @NotBlank(message = "Can't be blank")
        String email,

        @Pattern(
                regexp = "^" +
                        "(?=.*[a-z])" +
                        "(?=.*[A-Z])" +
                        "(?=.*[0-9])" +
                        "(?=.*[ @$!%*?&])" +
                        ".+$",
                message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character"
        )
        @Size(max = 80, message = "Maximum password length has exceeded")
        String password,

        @NotNull boolean isAccountNonExistent,
        @NotNull boolean isAccountNonLocked,
        @NotNull boolean isCredentialsNonExpired,
        @NotNull boolean isEnabled,

        @NotEmpty
        @Pattern(
                regexp = "^(EMPLOYEE|ADMIN)",
                message = "Must be a valid role"
        )
        Set<UserRole> roles

) {}
