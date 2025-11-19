package se.sti.employee_registry.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomUserResponseDTO(
        @Size(min = 2,max = 25, message = "Too long/short first name")
        @NotBlank(message = "Can't be blank")
        String firstName,

        @Size(min = 2,max = 25, message = "Too long/short last name")
        @NotBlank(message = "Can't be blank")
        String lastName,

        @Size(min = 2,max = 25, message = "Too long/short email")
        @NotBlank(message = "Can't be blank")
        String email
) {
}
