package pl.dk.usermanager.infrastructure.user.controller;

import jdk.jshell.EvalException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;
import pl.dk.usermanager.infrastructure.security.jwt.JwtAuthenticationException;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @PutMapping("/edit")
    ResponseEntity<UserDto> editUser(@RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userFacade.updateUser(updateUserDto));
    }
}
