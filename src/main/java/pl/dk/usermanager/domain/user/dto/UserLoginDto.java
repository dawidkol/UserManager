package pl.dk.usermanager.domain.user.dto;

import lombok.Builder;

@Builder
public record UserLoginDto(String email, String password) {
}
