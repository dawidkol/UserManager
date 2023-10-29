package pl.dk.usermanager.domain.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import pl.dk.usermanager.domain.email.EmailService;

@Configuration
class UserConfig {

    @Bean
    UserFacade userFacade(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        UserDtoMapper userDtoMapper = new UserDtoMapper(passwordEncoder);
        return new UserFacade(userRepository, userDtoMapper, passwordEncoder);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
