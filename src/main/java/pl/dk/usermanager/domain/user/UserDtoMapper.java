package pl.dk.usermanager.domain.user;

import pl.dk.usermanager.domain.user.dto.SuccessRegistrationUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;


class UserDtoMapper {
    private static final String REGISTRATION_CONFIRMATION_MESSAGE = "Rejestracja przebiegła pomyślnie. Sprawdź email";

    User mapToUser(UserRegistrationDto userRegistrationDto) {
        return User.builder()
                .email(userRegistrationDto.email())
                .password(userRegistrationDto.password())
                .build();
    }

    UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    SuccessRegistrationUserDto mapToSuccessRegistrationUserDto(User user) {
        return SuccessRegistrationUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .message(REGISTRATION_CONFIRMATION_MESSAGE)
                .build();
    }


}
