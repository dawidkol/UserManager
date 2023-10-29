package pl.dk.usermanager.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserLoginDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import java.util.Optional;


@AllArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        User userToSave = userDtoMapper.mapToUser(userRegistrationDto);
        User savedUser = userRepository.save(userToSave);
        return userDtoMapper.mapToUserDto(savedUser);
    }

    public UserDto updateUser(UpdateUserDto updateUserDto) {
        User currentUser = userRepository.findByEmail(updateUserDto.currentEmail())
                .orElseThrow(() -> new UsernameNotFoundException("email not exists in database"));
        boolean checkPassword = passwordEncoder.matches(updateUserDto.currentPassword(), currentUser.getPassword());
        if (checkPassword) {
            User userToUpdate = createUserToUpdate(updateUserDto, currentUser);
            User updatedUser = userRepository.save(userToUpdate);
            return userDtoMapper.mapToUserDto(updatedUser);
        }
        else {
            throw new UpdateUserNotPossibleException("Password not match");
        }
    }

    public Optional<UserLoginDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userDtoMapper::mapToUserLoginDto);
    }

    private User createUserToUpdate(UpdateUserDto updateUserDto, User currentUser) {
        return User.builder()
                .id(currentUser.getId())
                .email(updateUserDto.newEmail())
                .password(passwordEncoder.encode(updateUserDto.newPassword()))
                .build();
    }


}
