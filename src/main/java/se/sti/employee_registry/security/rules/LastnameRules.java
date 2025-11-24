package se.sti.employee_registry.security.rules;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LastnameRules (
        @Size(min = 2,max = 25, message = "Too long/short last name")
        @NotBlank(message = "Can't be blank")
        String value
){
}
