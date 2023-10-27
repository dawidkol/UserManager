package pl.dk.usermanager.domain.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
//import pl.dk.usermanager.domain.email.EmailService;

@Configuration
class UserConfig {

    @Bean
    UserFacade userFacade(UserRepository userRepository) {
        UserDtoMapper userDtoMapper = new UserDtoMapper();
        return new UserFacade(userRepository, userDtoMapper);
    }




}
