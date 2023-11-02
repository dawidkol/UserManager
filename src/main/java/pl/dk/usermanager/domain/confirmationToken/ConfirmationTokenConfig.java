package pl.dk.usermanager.domain.confirmationToken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dk.usermanager.domain.user.UserRepository;

@Configuration
class ConfirmationTokenConfig {
    @Bean
    ConfirmationTokenFacade confirmationTokenFacade(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository) {
        return new ConfirmationTokenFacade(confirmationTokenRepository, userRepository);
    }
}
