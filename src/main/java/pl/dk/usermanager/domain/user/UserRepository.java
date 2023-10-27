package pl.dk.usermanager.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface UserRepository extends JpaRepository<User, Long> {
}
