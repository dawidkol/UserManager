package pl.dk.usermanager.domain.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.dk.usermanager.domain.user.dto.UserDto;

public class EmailFacade {

    public static final String EMAIL_SUBJECT = "User Manager - Registration confirmation";

    private final JavaMailSender javaMailSender;

    public EmailFacade(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${app.confirmation-link}")
    private String link;


    public void sendConfirmationMail(UserDto userDto, String confirmationToken) {
        SimpleMailMessage email = createEmail(userDto, confirmationToken);
        javaMailSender.send(email);
    }

    private SimpleMailMessage createEmail(UserDto userDto, String confirmationToken) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userDto.email());
        simpleMailMessage.setSubject(EMAIL_SUBJECT);
        simpleMailMessage.setText(createMessage(confirmationToken));
        simpleMailMessage.setBcc();
        return simpleMailMessage;
    }

    private String createMessage(String token) {
        return """
                Thank you for registering on our website.
                To activate your account, click on the link below :
                %s%s
                """
                .trim()
                .formatted(link, token);
    }
}
