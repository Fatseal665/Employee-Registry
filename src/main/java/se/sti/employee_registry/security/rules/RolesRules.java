package se.sti.employee_registry.security.rules;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import se.sti.employee_registry.user.authority.UserRole;

import java.util.Set;

public record RolesRules (
        @NotEmpty
        @Pattern(
                regexp = "^(EMPLOYEE|ADMIN)",
                message = "Must be a valid role"
        )
        Set<UserRole> roles
){
}
