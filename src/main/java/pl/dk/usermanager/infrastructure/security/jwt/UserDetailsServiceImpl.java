package pl.dk.usermanager.infrastructure.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.dto.UserLoginDto;

@Service
@AllArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {


    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userFacade.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    }

    private UserDetails createUserDetails(UserLoginDto userLoginDto) {
        return User.builder()
                .username(userLoginDto.email())
                .password(userLoginDto.password())
//                .roles(credentials.roles().toArray(String[]::new))
                .build();
    }
}

