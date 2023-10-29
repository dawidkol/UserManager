package pl.dk.usermanager.domain.user.dto;

import lombok.Builder;

@Builder
public record UserDto(Long id, String email) {
}
