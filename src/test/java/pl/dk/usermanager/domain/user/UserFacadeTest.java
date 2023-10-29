package pl.dk.usermanager.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private UserFacade userFacade;
    private UserDtoMapper userDtoMapper;
    private CustomInMemoryDatabaseForUserFacadeTest repository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        repository = new CustomInMemoryDatabaseForUserFacadeTest();
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDtoMapper = new UserDtoMapper(passwordEncoder);
        userFacade = new UserFacade(repository, userDtoMapper, passwordEncoder);
    }


    @Test
    void shouldSaveUserInDb() {
        //given
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .email("user1@gmail.com")
                .password("password1")
                .build();

        //when
        UserDto userDto = userFacade.registerUser(userRegistrationDto);

        //then
        assertAll(
                () -> assertEquals(1, repository.userList.size()),
                () -> assertEquals(userRegistrationDto.email(), userDto.email()),
                () -> assertEquals(1, userDto.id())
        );


    }

}