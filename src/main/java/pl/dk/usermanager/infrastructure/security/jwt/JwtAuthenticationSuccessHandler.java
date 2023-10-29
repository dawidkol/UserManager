package pl.dk.usermanager.infrastructure.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        List<String> authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .toList();
        String signedJWT = jwtService.createSignedJWT(authentication.getName());
//        String signedJWT = jwtService.createSignedJWT(authentication.getName(), authorities);
        new ObjectMapper().writeValue(response.getWriter(), new JwtWrapper(signedJWT));
    }

    private record JwtWrapper(String token) {
    }
}
