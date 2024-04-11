package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.enums.Authority;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SafeVaultUser {

    @Id
    private String id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private LocalDateTime dateCreated;
    private String otp;
    private LocalDateTime otpCreatedTime;
    private LocalDateTime dateUpdated;

    private Set<Authority> authorities;
}
