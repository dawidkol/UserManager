package pl.dk.usermanager.domain.confirmationToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import pl.dk.usermanager.domain.user.CustomInMemoryUserRepository;
import pl.dk.usermanager.domain.user.User;
import pl.dk.usermanager.domain.user.UserFacadeTestData;
import pl.dk.usermanager.domain.user.UserRepository;
import pl.dk.usermanager.domain.user.dto.UserDto;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmationTokenFacadeTest {

    private CustomInMemoryConfirmationTokenRepository tokenRepository;
    private ConfirmationTokenFacade tokenFacade;
    private CustomInMemoryUserRepository userRepository;


    @BeforeEach
    void init() {
        tokenRepository = new CustomInMemoryConfirmationTokenRepository();
        userRepository = new CustomInMemoryUserRepository();
        tokenFacade = new ConfirmationTokenFacade(tokenRepository, userRepository);
        UserFacadeTestData userTesData = new UserFacadeTestData();
        tokenRepository.saveAll(new TokenTestData().getTokens());
        userRepository.saveAll(userTesData.getUsers());

    }


    @Test
    void shouldSaveTokenInDb() {
        //given
        User user = userRepository.getUserList().get(0);
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getPassword())
                .build();
        //when
        tokenFacade.saveConfirmationToken(userDto);
        List<ConfirmationToken> tokendsDb = tokenRepository.getTokens();


        //then
        assertAll(
                () -> assertEquals(2, tokendsDb.size()),
                () -> assertEquals(2, tokendsDb.get(1).getId())
        );
    }

    @Test
    void shouldThrow404WhenUserNotExistsInDb() {
        UserDto userDto = UserDto.builder()
                .id(100L)
                .email("simpleUser@gmail.com")
                .build();

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> tokenFacade.saveConfirmationToken(userDto));
    }

    @Test
    void shouldThrow409WhenUserIsAlreadyActivated() {
       //given

        String activatedUserToken = tokenRepository.getTokens().get(0).getToken();

        //when
        //then
        assertThrows(ResponseStatusException.class, () -> tokenFacade.confirmToken(activatedUserToken));
    }
}