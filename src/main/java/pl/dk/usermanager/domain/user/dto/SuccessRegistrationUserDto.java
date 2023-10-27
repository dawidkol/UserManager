package pl.dk.usermanager.domain.user.dto;

import lombok.Builder;

@Builder
public record SuccessRegistrationUserDto(Long id, String email, String message) {
}
