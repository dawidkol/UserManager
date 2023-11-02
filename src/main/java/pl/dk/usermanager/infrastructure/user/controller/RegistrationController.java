package pl.dk.usermanager.infrastructure.user.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.dk.usermanager.domain.confirmationToken.ConfirmationToken;
import pl.dk.usermanager.domain.confirmationToken.ConfirmationTokenFacade;
import pl.dk.usermanager.domain.email.EmailFacade;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
class RegistrationController {

    private final UserFacade userFacade;
    private final EmailFacade emailFacade;
    private final ConfirmationTokenFacade confirmationTokenFacade;

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        UserDto userDto = userFacade.registerUser(userRegistrationDto);
        ConfirmationToken confirmationToken = confirmationTokenFacade.saveConfirmationToken(userDto);
        emailFacade.sendConfirmationMail(userDto, confirmationToken.getToken());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/v1/user/{id}")
                .buildAndExpand(userDto.id())
                .toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @GetMapping(value = "/confirmation", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> confirmation(@Valid @RequestParam(required = true) String token) {
        confirmationTokenFacade.confirmToken(token);
        String message = """
                {
                "message": Your account has been activated
                }
                """;
        return ResponseEntity.ok(message);
    }
}
