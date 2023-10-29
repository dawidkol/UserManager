package pl.dk.usermanager.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dk.usermanager.domain.user.dto.*;

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
                .orElseThrow(RuntimeException::new);
        boolean checkPassword = passwordEncoder.matches(updateUserDto.currentPassword(), currentUser.getPassword());
        if (checkPassword) {
            User userToUpdate = createUserToUpdate(updateUserDto, currentUser);
            User updatedUser = userRepository.save(userToUpdate);
            return userDtoMapper.mapToUserDto(updatedUser);
        } else {
            throw new UpdateUserNotPossibleException("User update not possible");
        }
    }

    private User createUserToUpdate(UpdateUserDto updateUserDto, User currentUser) {
        return User.builder()
                .id(currentUser.getId())
                .email(updateUserDto.newEmail())
                .password(passwordEncoder.encode(updateUserDto.newPassword()))
                .build();
    }

    public Optional<UserLoginDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userDtoMapper::mapToUserLoginDto);
    }


}
