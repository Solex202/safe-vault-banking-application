package com.lota.SafeVaultBankingApplication.security;

import java.util.Set;

public class SecurityUtils {

    public static Set<String> getAuthenticationWhiteList(){
        return Set.of(
                "/auth/login",
                "/users",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs.yaml"
        );
    }
}
