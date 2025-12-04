package se.sti.employee_registry.user.dto;

import java.util.UUID;

public record AdminCommandDTO(
        UUID id,
        boolean isAccountNonExistent,
        boolean isAccountNonLocked,
        boolean isCredentialsNonExpired,
        boolean isEnabled
) {}
