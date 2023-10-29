package pl.dk.usermanager.domain.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.dk.usermanager.domain.user.dto.UserDto;

@AllArgsConstructor
public class EmailFacade {

    public static final String EMAIL_SUBJECT = "User Manager - Potwierdzenie rejestracji";
    public static final String EMAIL_MESSAGE = "Rejestracja przebiegła pomyślnie." +
            " Dziękujemy za dołączenie od naszego serwisu";

    private final JavaMailSender javaMailSender;

    public void sendConfirmationMail(UserDto userDto) {
        SimpleMailMessage email = createEmail(userDto);
        javaMailSender.send(email);
    }

    private SimpleMailMessage createEmail(UserDto userDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userDto.email());
        simpleMailMessage.setSubject(EMAIL_SUBJECT);
        simpleMailMessage.setText(EMAIL_MESSAGE);
        return simpleMailMessage;
    }
}
