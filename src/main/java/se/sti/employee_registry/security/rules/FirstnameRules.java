package se.sti.employee_registry.security.rules;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FirstnameRules (
    @Size(min = 2,max = 25, message = "Too long/short first name")
    @NotBlank(message = "Can't be blank")
    String firstName
){
}
