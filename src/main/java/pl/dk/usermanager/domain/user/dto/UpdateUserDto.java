package pl.dk.usermanager.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateUserDto(
        @NotNull
        @Email
        String newEmail,
        @NotNull
        @Size(min = 5)
        String newPassword
) {
}
