package pl.dk.usermanager.infrastructure.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.dk.usermanager.domain.user.UserFacade;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;


@Service
public class JwtService {

    private static final int EXP_TIME_SEC = 30 * 24 * 60 * 60;
    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;
    private final JWSSigner signer;
    private final JWSVerifier verifier;
    private final UserFacade userFacade;

    public JwtService(@Value("${jws.sharedKey}") String sharedKey, UserFacade userFacade) {
        try {
            signer = new MACSigner(sharedKey.getBytes());
            verifier = new MACVerifier(sharedKey.getBytes());
            this.userFacade = userFacade;
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }


    //    String createSignedJWT(String username, List<String> authorities) {
    String createSignedJWT(String username) {
        JWSHeader header = new JWSHeader(jwsAlgorithm);
        LocalDateTime nowPlus1Hour = LocalDateTime.now().plusSeconds(EXP_TIME_SEC);
        Date expirationDate = Date.from(nowPlus1Hour.atZone(ZoneId.systemDefault()).toInstant());
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(expirationDate)
//                .claim("authorities", authorities)
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return signedJWT.serialize();
    }

    void verifySignature(SignedJWT signedJWT) {
        try {
            signedJWT.verify(verifier);
        } catch (JOSEException e) {
            throw new JwtAuthenticationException("JWT verification failed for token %s".formatted(signedJWT.serialize()));
        }
    }

    void verifyExpirationTime(SignedJWT signedJWT) {
        try {
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            LocalDateTime expirationDateTime = jwtClaimsSet
                    .getDateClaim("exp")
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (LocalDateTime.now().isAfter(expirationDateTime)) {
                throw new JwtAuthenticationException("Token Expired at %s".formatted(expirationDateTime));
            }
        } catch (ParseException e) {
            throw new JwtAuthenticationException("Token does not have exp claim");
        }
    }

    void verifyUsername(SignedJWT signedJWT) {
        try {
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            String email = jwtClaimsSet.getSubject();
            if (userFacade.findByEmail(email).isEmpty()) {
                throw new JwtAuthenticationException("Invalid username in token");
            }
        } catch (ParseException e) {
            throw new JwtAuthenticationException("Token does not have username");
        }

    }

    Authentication createAuthentication(SignedJWT signedJWT) {
        String subject;
//        List<String> authorities;
        try {
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            subject = jwtClaimsSet.getSubject();
//            authorities = jwtClaimsSet.getStringListClaim("authorities");
        } catch (ParseException e) {
            throw new JwtAuthenticationException("Missing claims sub or authorities");
        }
//        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).toList();
        return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
    }
}
