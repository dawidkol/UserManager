package pl.dk.usermanager.domain.confirmationToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dk.usermanager.domain.user.CustomInMemoryUserRepository;
import pl.dk.usermanager.domain.user.UserRepository;

import java.util.UUID;

class ConfirmationTokenFacadeTest {

    private ConfirmationTokenRepository tokenRepository;
    private ConfirmationTokenFacade tokenFacade;
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        tokenRepository = new CustomInMemoryConfirmationTokenRepository();
        userRepository = new CustomInMemoryUserRepository();
        tokenFacade = new ConfirmationTokenFacade(tokenRepository, userRepository);
    }


    @Test
    void shouldSaveTokenInDb() {
        //given
        String token = UUID.randomUUID().toString();

        //when
    }




}