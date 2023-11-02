package pl.dk.usermanager.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserLoginDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    @Spy
    private UserFacade userFacade;
    private UserDtoMapper userDtoMapper;

    private CustomInMemoryUserRepository repository;
    private PasswordEncoder passwordEncoder;
    private UserFacadeTestData testData;


    @BeforeEach
    void init() {
        repository = new CustomInMemoryUserRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        userDtoMapper = new UserDtoMapper(passwordEncoder);
        userFacade = new UserFacade(repository, userDtoMapper, passwordEncoder);
        testData = new UserFacadeTestData();
        repository.saveAll(testData.users);

    }


    @Test
    void shouldSaveUserInDb() {
        //given
        UserRegistrationDto user = UserRegistrationDto.builder()
                .email("user1@gmail.com")
                .password("password1")
                .build();

        //when
        UserDto userDto = userFacade.registerUser(user);

        //then
        assertAll(
                () -> assertEquals(11, repository.userList.size()),
                () -> assertEquals(user.email(), userDto.email()),
                () -> assertEquals(11, userDto.id())
        );
    }

    @Test
    void shouldFindUserByGivenEmail() {
        //given
        User user = testData.users.get(1);
        String email = user.getEmail();
        String password = user.getPassword();

        //when
        UserLoginDto userLoginDto = userFacade.findByEmail(email).orElseThrow();

        //then
        assertAll(
                () -> assertEquals(email, userLoginDto.email()),
                () -> assertEquals(password, userLoginDto.password())
        );
    }

    @Test
    void shouldReturnEmptyOptional() {
        //given
        UserDto notExistingUser = UserDto.builder()
                .email("notexisting@email.com")
                .build();

        //when
        Optional<UserLoginDto> emptyOptional = userFacade.findByEmail(notExistingUser.email());

        //then
        assertEquals(Optional.empty(), emptyOptional);

    }

    @Test
    void shouldUpdateUser() {
        //given
        UpdateUserDto newUserData
                = UpdateUserDto.builder()
                .newEmail("newemail@test.com")
                .newPassword("newPassword")
                .build();
        User user = repository.userList.get(0);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        userFacade.updateUser(newUserData);

        User updatedUser = repository.userList.get(0);

        //then
        assertAll(
                () -> assertEquals(newUserData.newEmail(), updatedUser.getUsername()),
                () -> assertTrue(passwordEncoder.matches(newUserData.newPassword(), updatedUser.getPassword()))
        );
    }

    @Test
    void shouldThrowUnauthorizedAccountIsNotActivate() {
        //given
        UpdateUserDto newUserData
                = UpdateUserDto.builder()
                .newEmail("newemail@test.com")
                .newPassword("newPassword")
                .build();

        User user = repository.userList.get(1);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //then
        assertThrows(ResponseStatusException.class, () -> userFacade.updateUser(newUserData));
        SecurityContextHolder.clearContext();
    }


    @Test
    void shouldDeleteUserWhenUserIsEnabled() {
        //given
        User user = repository.userList.get(0);

        //when
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userFacade.deleteUser();

        //then
        assertEquals(9, repository.userList.size());
    }

}


