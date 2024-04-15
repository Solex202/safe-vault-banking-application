package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.enums.Authority;
import jakarta.validation.constraints.Email;
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
    @Email
    @NonNull
    private String email;
    private String passcode;
    private String firstname;
    private String lastname;
    @NonNull
    private String phoneNumber;
    private LocalDateTime dateCreated;
    private String otp;
    private boolean isOtpVerified;
    private boolean isEmailVerified;
    private LocalDateTime otpCreatedTime;
    private LocalDateTime dateUpdated;

    private Set<Authority> authorities;
}
