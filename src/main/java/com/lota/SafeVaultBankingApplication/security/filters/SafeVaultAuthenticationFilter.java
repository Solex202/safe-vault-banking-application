package com.lota.SafeVaultBankingApplication.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lota.SafeVaultBankingApplication.dtos.request.LoginRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.ApiResponse;
import com.lota.SafeVaultBankingApplication.dtos.response.LoginResponse;
import com.lota.SafeVaultBankingApplication.security.services.SafeVaultJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;
import static com.lota.SafeVaultBankingApplication.utils.AppUtil.*;

//@Component
@AllArgsConstructor
public class SafeVaultAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
     private static final ObjectMapper mapper = new ObjectMapper();

     private final AuthenticationManager authenticationManager;

     private final SafeVaultJWTService jwtService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest = extractAuthenticationCredentialsFrom(request);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);

        return authenticationResult;
    }

    private static LoginRequest extractAuthenticationCredentialsFrom(HttpServletRequest request) {
        try(InputStream inputStream = request.getInputStream()){
            byte [] requestBody = inputStream.readAllBytes();
            return mapper.readValue(requestBody, LoginRequest.class);
        }catch (IOException exception){
            throw new BadCredentialsException(AUTHENTICATION_FAILURE.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtService.generateTokenFor(authResult.getPrincipal().toString());

        LoginResponse authenticationResponse = new LoginResponse();
        authenticationResponse.setToken(token);
        response.setContentType(APPLICATION_JSON);
        response.getOutputStream().write(mapper.writeValueAsBytes(authenticationResponse));
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ApiResponse<String> authResponse = new ApiResponse<>();
        authResponse.setData(failed.getMessage());
        response.setContentType(APPLICATION_JSON);
        response.getOutputStream().write(mapper.writeValueAsBytes(authResponse));
        response.flushBuffer();
    }
}
