package pl.dk.usermanager.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
        ValidationDto validate = validate(updateUserDto);
        if (validate.logic()) {
            User userToUpdate = createUserToUpdate(updateUserDto, validate.user());
            User updatedUser = userRepository.save(userToUpdate);
            return userDtoMapper.mapToUserDto(updatedUser);
        } else {
            throw new UpdateUserNotPossibleException("Password not match");
        }
    }

    public Optional<UserLoginDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userDtoMapper::mapToUserLoginDto);
    }

    @Transactional
    public void deleteUser(UpdateUserDto updateUserDto) {
        ValidationDto validate = validate(updateUserDto);
        if (validate.logic()) {
            userRepository.deleteByEmail(updateUserDto.currentEmail());
        } else {
            throw new UpdateUserNotPossibleException("Password not match");
        }
    }

    private <T extends UpdateUserDto> ValidationDto validate(T data) {
        User currentUser = userRepository.findByEmail(data.currentEmail())
                .orElseThrow(() -> new UsernameNotFoundException("email not exists in database"));
        boolean checkPassword = passwordEncoder.matches(data.currentPassword(), currentUser.getPassword());
        return ValidationDto.builder()
                .user(currentUser)
                .logic(checkPassword)
                .build();
    }

    private User createUserToUpdate(UpdateUserDto updateUserDto, User currentUser) {
        return User.builder()
                .id(currentUser.getId())
                .email(updateUserDto.newEmail())
                .password(passwordEncoder.encode(updateUserDto.newPassword()))
                .build();
    }


}
