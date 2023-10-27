package pl.dk.usermanager.infrastructure.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.dk.usermanager.domain.email.EmailFacade;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.dto.SuccessRegistrationUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
class RegistrationController {

    private final UserFacade userFacade;
    private final EmailFacade emailFacade;
    @PostMapping("/register")
    ResponseEntity<SuccessRegistrationUserDto> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        SuccessRegistrationUserDto userDto = userFacade.registerUser(userRegistrationDto);
        emailFacade.sendConfirmationMail(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/v1/user/{id}")
                .buildAndExpand(userDto.id())
                .toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
}
