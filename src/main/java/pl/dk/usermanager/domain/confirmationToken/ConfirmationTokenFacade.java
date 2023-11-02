package pl.dk.usermanager.domain.confirmationToken;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.dk.usermanager.domain.user.User;
import pl.dk.usermanager.domain.user.UserRepository;
import pl.dk.usermanager.domain.user.dto.UserDto;

import java.util.UUID;

@AllArgsConstructor
public class ConfirmationTokenFacade {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    public ConfirmationToken saveConfirmationToken(UserDto userDto) {
        User user = findUserById(userDto.id());
        ConfirmationToken tokenToSave = ConfirmationToken.builder()
                .token(generateConfirmationToken())
                .user(user)
                .build();
        return confirmationTokenRepository.save(tokenToSave);
    }

    public void confirmToken(String token) {
        ConfirmationToken tokenFromDb = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
        Boolean active = findUserById(tokenFromDb.getUser().getId()).getActive();
        if (!active) {
            userRepository.activeUser(tokenFromDb.getUser().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The account has already been activated");
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token owner not found in app"));
    }

    private String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }
}
