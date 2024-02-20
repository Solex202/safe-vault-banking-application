package com.lota.SafeVaultBankingApplication.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

import static com.lota.SafeVaultBankingApplication.utils.AppUtil.EMAIL;
import static java.time.Instant.now;

@Configuration
@RequiredArgsConstructor
public class SafeVaultJWTServiceImpl implements SafeVaultJWTService{

    @Value("${jwt.token.validity}")
    private String jwtSigningKey;

    @Value("${jwt.signing.key}")
    private String jwtTokenValidity;
    @Override
    public String generateTokenFor(String email) {
        return JWT.create()
                .withClaim(EMAIL,email)
                .withExpiresAt(now().plusSeconds(Long.parseLong(jwtTokenValidity)))
                .withIssuedAt(now())
                .sign(Algorithm.HMAC512(jwtSigningKey));
    }
}
