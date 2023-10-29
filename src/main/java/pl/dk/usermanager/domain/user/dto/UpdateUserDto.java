package pl.dk.usermanager.domain.user.dto;

import lombok.Builder;

@Builder
public record UpdateUserDto(String currentEmail, String currentPassword, String newEmail, String newPassword) {
}
