package pl.dk.usermanager.infrastructure.user.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
class UserController {

    private final UserFacade userFacade;


    @PutMapping("/me/edit")
    ResponseEntity<?> editUser(@Valid @RequestBody UpdateUserDto updateUserDto) {
        userFacade.updateUser(updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me/delete")
    ResponseEntity<?> deleteUser() {
        userFacade.deleteUser();
        return ResponseEntity.noContent().build();
    }


}
