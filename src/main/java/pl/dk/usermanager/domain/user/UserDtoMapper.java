package pl.dk.usermanager.domain.user;

import org.springframework.stereotype.Service;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;


class UserDtoMapper {

    User mapToUser(UserRegistrationDto userRegistrationDto) {
        return User.builder()
                .username(userRegistrationDto.username())
                .password(userRegistrationDto.password())
                .build();
    }

    UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }




}
