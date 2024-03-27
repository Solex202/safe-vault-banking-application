package com.lota.SafeVaultBankingApplication.security.filters;

import com.lota.SafeVaultBankingApplication.security.services.SafeVaultJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class SafeVaultAuthorizationFilter extends OncePerRequestFilter {

    private final SafeVaultJWTService safeVaultJWTService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
