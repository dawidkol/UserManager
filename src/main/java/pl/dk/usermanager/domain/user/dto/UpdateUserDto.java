package pl.dk.usermanager.domain.user.dto;

public record UpdateUserDto(String currentEmail, String currentPassword, String newEmail, String newPassword) {
}
