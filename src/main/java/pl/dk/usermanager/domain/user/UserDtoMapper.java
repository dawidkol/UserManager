package pl.dk.usermanager.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserLoginDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

@AllArgsConstructor
class UserDtoMapper {
    private PasswordEncoder passwordEncoder;

    User mapToUser(UserRegistrationDto userRegistrationDto) {
        return User.builder()
                .email(userRegistrationDto.email())
                .password(passwordEncoder.encode(userRegistrationDto.password()))
                .active(false)
                .build();
    }

    UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
    UserLoginDto mapToUserLoginDto(User user) {
        return UserLoginDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

    }



}
