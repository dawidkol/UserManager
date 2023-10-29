package pl.dk.usermanager.infrastructure.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @PutMapping("/edit")
    ResponseEntity<UserDto> editUser(@RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userFacade.updateUser(updateUserDto));
    }

    @DeleteMapping("/delete")
    ResponseEntity<?> deleteUser(@RequestBody UpdateUserDto updateUserDto) {
        userFacade.deleteUser(updateUserDto);
        return ResponseEntity.noContent().build();
    }
}
