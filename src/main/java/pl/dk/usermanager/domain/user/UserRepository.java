package pl.dk.usermanager.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET active = true where id = :userId ", nativeQuery = true)
    void activeUser(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET email = :#{#updateUserDto.newEmail}," +
            " password = :#{#updateUserDto.newPassword} WHERE email = :currentUser", nativeQuery = true)
    void updateUser(@Param("updateUserDto") UpdateUserDto updateUserDto, @Param("currentUser") String currentUser);

}
