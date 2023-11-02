package pl.dk.usermanager.domain.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfig {
    @Bean
    UserFacade userFacade(UserRepository userRepository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDtoMapper userDtoMapper = new UserDtoMapper(passwordEncoder);
        return new UserFacade(userRepository, userDtoMapper, passwordEncoder);
    }


}
