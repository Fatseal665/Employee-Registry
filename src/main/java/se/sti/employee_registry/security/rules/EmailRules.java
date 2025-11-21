package se.sti.employee_registry.security.rules;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmailRules (
        @Size(min = 3,max = 254, message = "Too long/short email")
        @NotBlank(message = "Can't be blank")
        @Pattern(
                regexp = "^[\\p{L}\\p{N}._%+-]+@[\\p{L}\\p{N}._%+-]+\\.[\\p{L}]{2,}$",
                message = "Must be valid email"
        )
        String email
){
}
