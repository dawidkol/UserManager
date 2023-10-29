package pl.dk.usermanager.domain.user;

import lombok.Builder;

@Builder
record ValidationDto(User user, Boolean logic) {
}
