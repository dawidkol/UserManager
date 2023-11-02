package pl.dk.usermanager.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDto(
        Long id,
        @NotNull
        @Email
        String email
) {
}
