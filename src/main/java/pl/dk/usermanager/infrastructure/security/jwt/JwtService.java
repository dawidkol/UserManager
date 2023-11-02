package pl.dk.usermanager.infrastructure.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;


@Service
public class JwtService {

    @Value("${app.token.expiration-time-sec}")
    private int EXP_TIME_SEC;
    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;
    private final JWSSigner signer;
    private final JWSVerifier verifier;


    public JwtService(@Value("${jws.sharedKey}") String sharedKey) {
        try {
            signer = new MACSigner(sharedKey.getBytes());
            verifier = new MACVerifier(sharedKey.getBytes());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    String createSignedJWT(String username) {
        JWSHeader header = new JWSHeader(jwsAlgorithm);
        LocalDateTime nowPlusExpirationTime = LocalDateTime.now().plusSeconds(EXP_TIME_SEC);
        Date expirationDate = Date.from(nowPlusExpirationTime.atZone(ZoneId.systemDefault()).toInstant());
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(expirationDate)
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
            boolean verified = signedJWT.verify(verifier);
            if (!verified) {
                throw new JwtAuthenticationException("JWT verification failed for token %s".formatted(signedJWT.serialize()));
            }
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
            throw new JwtAuthenticationException("Invalid token");
        }
    }


    Authentication createAuthentication(SignedJWT signedJWT) {
        String subject;
        try {
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            subject = jwtClaimsSet.getSubject();
        } catch (ParseException e) {
            throw new JwtAuthenticationException("Missing claims sub or authorities");
        }
        return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
    }
}
