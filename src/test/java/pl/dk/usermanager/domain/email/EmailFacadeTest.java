package pl.dk.usermanager.domain.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.dk.usermanager.domain.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;


class EmailFacadeTest {
    private EmailFacade emailFacade;
    private JavaMailSender javaMailSender;

    @BeforeEach
    void init() {
        javaMailSender = Mockito.mock(JavaMailSender.class);
        emailFacade = new EmailFacade(javaMailSender);
    }

    @Test
    void shouldSendConfirmationMail() {
        //given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("user1@test.pl")
                .build();

        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        expectedMailMessage.setTo(userDto.email());
        expectedMailMessage.setSubject(EmailFacade.EMAIL_SUBJECT);
        expectedMailMessage.setText(EmailFacade.EMAIL_MESSAGE);

        // when
        emailFacade.sendConfirmationMail(userDto);

        // then
        assertAll(
                () -> Mockito.verify(javaMailSender, Mockito.times(1)).send(any(SimpleMailMessage.class)),
                () -> Mockito.verify(javaMailSender).send(Mockito.eq(expectedMailMessage))
        );


    }
}