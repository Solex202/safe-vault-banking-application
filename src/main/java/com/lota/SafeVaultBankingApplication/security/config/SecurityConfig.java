package com.lota.SafeVaultBankingApplication.security.config;

import com.lota.SafeVaultBankingApplication.security.filters.SafeVaultAuthenticationFilter;
import com.lota.SafeVaultBankingApplication.security.filters.SafeVaultAuthorizationFilter;
import com.lota.SafeVaultBankingApplication.security.services.SafeVaultJWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.lota.SafeVaultBankingApplication.models.enums.Authority.USER;
import static com.lota.SafeVaultBankingApplication.security.SecurityUtils.getAuthenticationWhiteList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SafeVaultJWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SafeVaultAuthenticationFilter authenticationFilter = new SafeVaultAuthenticationFilter(authenticationManager, jwtService);
        authenticationFilter.setFilterProcessesUrl("/auth/login");
        String[] AUTH_WHITELIST = getAuthenticationWhiteList().toArray(String[]::new);
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(new SafeVaultAuthorizationFilter(jwtService), SafeVaultAuthenticationFilter.class)
                .authorizeHttpRequests(c->c.requestMatchers( "/auth/login", "/users", "/v3/api-docs/**", "/v3/api-docs.yaml", "/h2-console/**").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll())
                .authorizeHttpRequests(c->c.requestMatchers(GET, "/api/v1/reservation").hasAnyAuthority(USER.name())
                        .requestMatchers("/events", "/events**").hasAnyAuthority(USER.name()))
                .authorizeHttpRequests(c->c.anyRequest().hasAnyAuthority(USER.name()))
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html","/favicon**", "/favicon.ico", "/h2-console/**"
        );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return  new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:8080/*", "*");
            }
        };
    }
}
