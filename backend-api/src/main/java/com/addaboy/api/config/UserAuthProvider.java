package com.addaboy.api.config;

import com.addaboy.api.dto.UserDto;
import com.addaboy.api.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 *  Provider used before to return the authentication bean
 */
@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-value}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());       // Encoded the secret Key to base-64 (Helps to avoid having secret key as plain text in the JVM)
    }


    public String createToken(String login) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);        // The JWT will be valid for one hour (1 hour)

        return JWT.create()
                .withIssuer(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     *  To verify the JWT we first decode the JWT:
     *      If the Validity date is exceed it will through and exception
     *      Also Check if the User Exist in the database
     */
    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                .build();


        DecodedJWT decoded = verifier.verify(token);

        /**
         * Also Check if the User is Available in the database
         */
        UserDto user = userService.findByLogin(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }


}
