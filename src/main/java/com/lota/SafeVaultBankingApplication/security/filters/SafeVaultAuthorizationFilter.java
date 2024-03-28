package com.lota.SafeVaultBankingApplication.security.filters;

import com.lota.SafeVaultBankingApplication.security.services.SafeVaultJWTService;
import com.mongodb.client.model.Collation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static com.lota.SafeVaultBankingApplication.security.SecurityUtils.getAuthenticationWhiteList;

@AllArgsConstructor
public class SafeVaultAuthorizationFilter extends OncePerRequestFilter {

    private final SafeVaultJWTService safeVaultJWTService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Collection<String> authenticationWhiteList = getAuthenticationWhiteList();

        boolean isEndpointPublic = authenticationWhiteList.contains(request.getServletPath());

        if (isEndpointPublic){
            filterChain.doFilter(request, response);
        }
        else{
            String authorizationHeader = request.getHeader("Authorization");
            String token = extractTokenFrom(authorizationHeader);
        }
    }

    private String extractTokenFrom(String authorizationHeader) {
        boolean isAuthorizationHeaderPresent = authorizationHeader != null;
        String token = null;

        if (isAuthorizationHeaderPresent) token = authorizationHeader.substring("Bearer".length());
        return token;
    }
}
