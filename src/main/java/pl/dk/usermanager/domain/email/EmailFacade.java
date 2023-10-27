package pl.dk.usermanager.domain.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.dk.usermanager.domain.user.dto.SuccessRegistrationUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;

@AllArgsConstructor
public class EmailFacade {

    private final JavaMailSender javaMailSender;

    public void sendConfirmationMail(SuccessRegistrationUserDto userDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userDto.email());
        simpleMailMessage.setSubject("User Manager - Potwierdzenie rejestracji ");
        simpleMailMessage.setText("Rejestracja przebiegła pomyślnie. Dziękujemy za dołączenie od naszego serwisu");
        javaMailSender.send(simpleMailMessage);
    }
}
