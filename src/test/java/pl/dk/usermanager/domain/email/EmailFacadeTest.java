package pl.dk.usermanager.domain.email;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.dk.usermanager.domain.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmailFacadeTest {


    private EmailFacade emailFacade;

    @BeforeEach
    void init() {
        emailFacade = mock(EmailFacade.class);
    }


    @Test
    void shouldSendConfirmationEmail() {

        //given
        UserDto userDto = UserDto.builder().id(1L).email("dawid.kolano@gmail.com").build();
        emailFacade.sendConfirmationMail(userDto);

    }
}