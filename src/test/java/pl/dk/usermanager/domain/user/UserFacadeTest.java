package pl.dk.usermanager.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dk.usermanager.domain.user.dto.UserDto;
import pl.dk.usermanager.domain.user.dto.UserRegistrationDto;

import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private UserFacade userFacade;
    private UserDtoMapper userDtoMapper;
    private CustomInMemoryDatabaseForUserFacadeTest repository;

    @BeforeEach
    void init() {
        repository = new CustomInMemoryDatabaseForUserFacadeTest();
        userDtoMapper = new UserDtoMapper();
        userFacade = new UserFacade(repository, userDtoMapper);
    }


    @Test
    void shouldSaveUserInDb() {
        //given
        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .username("user1")
                .password("password1")
                .build();

        //when
        UserDto userDto = userFacade.registerUser(userRegistrationDto);

        //then
        assertAll(
                () -> assertEquals(1, repository.userList.size()),
                () -> assertEquals(userRegistrationDto.username(), userDto.username()),
                () -> assertEquals(1, userDto.id())
        );


    }

}