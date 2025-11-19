package se.sti.employee_registry.user.dto;

public record CustomUserLoginDTO(
        String email,
        String password
) {
}
