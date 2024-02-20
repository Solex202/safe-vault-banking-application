package com.lota.SafeVaultBankingApplication.security.services;

public interface SafeVaultJWTService {
    String generateTokenFor(Object principal);
}
