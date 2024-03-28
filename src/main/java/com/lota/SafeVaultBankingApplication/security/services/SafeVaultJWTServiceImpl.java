package com.lota.SafeVaultBankingApplication.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.lota.SafeVaultBankingApplication.utils.AppUtil.EMAIL;
import static java.time.Instant.now;

@Component
@RequiredArgsConstructor
public class SafeVaultJWTServiceImpl implements SafeVaultJWTService{

    @Value("${jwt.token.validity}")
    private String jwtSigningKey;

    @Value("${jwt.signing.key}")
    private String jwtTokenValidity;

    private final UserDetailsService userDetailsService;
    @Override
    public String generateTokenFor(String email) {
        return JWT.create()
                .withClaim(EMAIL,email)
                .withExpiresAt(now().plusSeconds(Long.parseLong(jwtTokenValidity)))
                .withIssuedAt(now())
                .sign(Algorithm.HMAC512(jwtSigningKey));
    }

    @Override
    public UserDetails extraUserDetailsFrom(String token) {

        JWTVerifier verifier = JWT.require(HMAC512(jwtSigningKey))
                .withClaimPresence(EMAIL)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        Claim claim = decodedJWT.getClaim(EMAIL);
        String email = claim.toString();

        return userDetailsService.loadUserByUsername(email);
    }
}
