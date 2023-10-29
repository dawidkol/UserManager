package pl.dk.usermanager.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserLoginDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private UserFacade userFacade;
    private UserDtoMapper userDtoMapper;
    private CustomInMemoryDatabaseForUserFacadeTest repository;
    private PasswordEncoder passwordEncoder;
    private UserFacadeTestData testData;


    @BeforeEach
    void init() {
        repository = new CustomInMemoryDatabaseForUserFacadeTest();
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
        Long id = user.getId();
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
        User user = repository.userList.get(0);
        UpdateUserDto dataToUpdate = UpdateUserDto.builder()
                .currentEmail(user.getEmail())
                .currentPassword("hardPass")
                .newEmail("newemail@test.pl")
                .newPassword("newPassword")
                .build();

        //when
        UserDto updatedUser = userFacade.updateUser(dataToUpdate);

        //then
        assertAll(
                () -> assertEquals(dataToUpdate.newEmail(), updatedUser.email())
        );
    }

    @Test
    void shouldThrowPasswordNotMatchExceptionWhenGivenCurrenPasswordIsInvalid() {
        //given
        User user = repository.userList.get(1);
        UpdateUserDto dataToUpdate = UpdateUserDto.builder()
                .currentEmail(user.getEmail())
                .currentPassword("wrongPassword")
                .newEmail("newemail@test.pl")
                .newPassword("newPassword")
                .build();

        //when then
        assertThrows(UpdateUserNotPossibleException.class, () -> userFacade.updateUser(dataToUpdate));
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenGivenCurrentEmailNotExistsInDb() {
        //given
        UpdateUserDto dataToUpdate = UpdateUserDto.builder()
                .currentEmail("notexisting@email.com")
                .currentPassword("wrongPassword")
                .newEmail("newemail@test.com")
                .newPassword("newPassword")
                .build();

        //when then
        assertThrows(UsernameNotFoundException.class, () -> userFacade.updateUser(dataToUpdate));
    }

    @Test
    void shouldDeleteUserWhenGivenCorrectData() {
        //given
        UpdateUserDto userToDelete = UpdateUserDto.builder()
                .currentEmail("johndoe@gmail.com")
                .currentPassword("hardPass")
                .build();

        //when
        userFacade.deleteUser(userToDelete);

        //then
        assertAll(
                () -> assertEquals(9, repository.userList.size())
        );
    }




}