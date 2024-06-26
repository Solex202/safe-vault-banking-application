package com.lota.SafeVaultBankingApplication.security.filters;

import com.lota.SafeVaultBankingApplication.security.services.SafeVaultJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

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
            UserDetails userDetails = safeVaultJWTService.extraUserDetailsFrom(token);
            authorize(userDetails);
            filterChain.doFilter(request, response);
        }
    }

    private void authorize(UserDetails userDetails) {

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private String extractTokenFrom(String authorizationHeader) {
        boolean isAuthorizationHeaderPresent = authorizationHeader != null;
        String token = null;

        if (isAuthorizationHeaderPresent) token = authorizationHeader.substring("Bearer".length());
        return token;
    }
}
