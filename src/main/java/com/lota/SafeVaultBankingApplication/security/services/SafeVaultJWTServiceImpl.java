package com.lota.SafeVaultBankingApplication.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SafeVaultJWTServiceImpl implements SafeVaultJWTService{
    @Override
    public String generateTokenFor(Object principal) {
        return null;
    }
}
