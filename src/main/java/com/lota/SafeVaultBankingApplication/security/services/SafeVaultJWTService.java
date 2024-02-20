package com.lota.SafeVaultBankingApplication.security.services;

public interface SafeVaultJWTService {
    String generateTokenFor(String email);
}
