package pl.dk.usermanager.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;


@AllArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        User userToSave = userDtoMapper.mapToUser(userRegistrationDto);
        User savedUser = userRepository.save(userToSave);
        return userDtoMapper.mapToUserDto(savedUser);
    }

}
