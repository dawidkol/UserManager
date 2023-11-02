package pl.dk.usermanager.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserLoginDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import java.util.Optional;


@AllArgsConstructor
public class UserFacade {

    public static final String REFRESH_TOKEN_MESSAGE = "User from token dont exists in app, refresh your token";
    public static final String ACTIVE_ACCOUNT_MESSAGE = "Active your account";
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        User userToSave = userDtoMapper.mapToUser(userRegistrationDto);
        User savedUser = userRepository.save(userToSave);
        return userDtoMapper.mapToUserDto(savedUser);
    }
    public void updateUser(UpdateUserDto updateUserDto) {
        User userInDbFromSecurityContextHolder = findUserInDbFromSecurityContextHolder();
        String email = userInDbFromSecurityContextHolder.getUsername();
        if (userInDbFromSecurityContextHolder.isEnabled()) {
            UpdateUserDto userToUpdateWithEncodedPassword = createUserToUpdateWithEncodedPassword(updateUserDto);
            userRepository.updateUser(userToUpdateWithEncodedPassword, email);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ACTIVE_ACCOUNT_MESSAGE);
        }
    }

    public Optional<UserLoginDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userDtoMapper::mapToUserLoginDto);
    }

    public void deleteUser() {
        User userFromDb = findUserInDbFromSecurityContextHolder();
        if (userFromDb.isEnabled()) {
            userRepository.delete(userFromDb);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Active your account");
        }
    }

    private User findUserInDbFromSecurityContextHolder() {
        String userFromSecurityContext = getUserFromSecurityContext();
        return userRepository.findByEmail(userFromSecurityContext)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, REFRESH_TOKEN_MESSAGE));
    }

    private String getUserFromSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private UpdateUserDto createUserToUpdateWithEncodedPassword(UpdateUserDto updateUserDto) {
        return UpdateUserDto.builder()
                .newEmail(updateUserDto.newEmail())
                .newPassword(passwordEncoder.encode(updateUserDto.newPassword()))
                .build();
    }
}
