package pl.dk.usermanager.domain.user.dto;

import lombok.Builder;

@Builder
record UserLoginDto (String email, String password){
}
