package pl.dk.usermanager.domain.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

@Configuration
class EmailConfig {

    @Value("${app.mail.host}")
    private String host;

    @Value("${app.mail.port}")
    private int port;

    @Value("${app.mail.username}")
    private String email;

    @Value("${app.mail.password}")
    private String password;

    @Value("${app.mail.protocol-key}")
    private String protocolKey;

    @Value("${app.mail.protocol-value}")
    private String protocolValue;

    @Value("${app.mail.smtp-key}")
    private String smtpKey;

    @Value("${app.mail.smtp-value}")
    private String smtpValue;

    @Value("${app.mail.starttls-key}")
    private String starttlsKey;

    @Value("${app.mail.starttls-value}")
    private String starttlsValue;

    @Bean
    EmailFacade emailFacade() {
        JavaMailSender javaMailSender = getJavaMailSender();
        return new EmailFacade(javaMailSender);
    }

    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setPort(port);
        javaMailSender.setHost(host);
        javaMailSender.setUsername(email);
        javaMailSender.setPassword(password);
        Properties props = javaMailSender.getJavaMailProperties();
        props.put(protocolKey, protocolValue);
        props.put(smtpKey, smtpValue);
        props.put(starttlsKey, starttlsValue);
        return javaMailSender;
    }
}
