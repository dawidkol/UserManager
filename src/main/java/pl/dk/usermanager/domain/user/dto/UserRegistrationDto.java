package pl.dk.usermanager.domain.user.dto;

import lombok.Builder;

@Builder
public record UserRegistrationDto(String username, String password) {
}
