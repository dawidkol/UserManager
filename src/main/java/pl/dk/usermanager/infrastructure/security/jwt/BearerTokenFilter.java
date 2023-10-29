package pl.dk.usermanager.infrastructure.security.jwt;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import java.io.IOException;
import java.text.ParseException;

@AllArgsConstructor
@Slf4j
class BearerTokenFilter extends HttpFilter {

    private final Logger logger = LoggerFactory.getLogger(BearerTokenFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();
    private final AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
    private final JwtService jwtService;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            logger.debug("Missing Authorization header or empty Bearer token");
            chain.doFilter(request, response);
        } else {
            String compactJwt = authorizationHeader.substring(BEARER_PREFIX.length());
            SignedJWT signedJwt;
            try {
                signedJwt = SignedJWT.parse(compactJwt);
                verifyJwt(signedJwt);
                setSecurityContext(signedJwt);
                chain.doFilter(request, response);
            } catch (JwtAuthenticationException e) {
                logger.debug(e.getMessage());
                failureHandler.onAuthenticationFailure(request, response, e);
            } catch (ParseException e) {
                JwtAuthenticationException authException = new JwtAuthenticationException("Bearer token could not be parsed");
                logger.debug(e.getMessage());
                failureHandler.onAuthenticationFailure(request, response, authException);
            }
        }
    }

    private void setSecurityContext(SignedJWT signedJwt) {
        Authentication authentication = jwtService.createAuthentication(signedJwt);
        SecurityContext securityContext = securityContextHolderStrategy.getContext();
        securityContext.setAuthentication(authentication);
    }

    private void verifyJwt(SignedJWT signedJwt) {
        jwtService.verifySignature(signedJwt);
        jwtService.verifyUsername(signedJwt);
        jwtService.verifyExpirationTime(signedJwt);
    }
}
