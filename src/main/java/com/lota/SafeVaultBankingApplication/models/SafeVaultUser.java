package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.enums.AccountType;
import com.lota.SafeVaultBankingApplication.models.enums.Authority;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
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
//    @NonNull
    private String email;
    private String passcode;
    private String firstname;
    private String lastname;
    private String image;
    @NonNull
    private String phoneNumber;
    private LocalDateTime dateCreated;
    private String otp;
    private boolean isOtpVerified;
    private boolean isEmailVerified;
    private String nin;
    private String isNinValid;
    private LocalDateTime otpCreatedTime;
    private LocalDateTime dateUpdated;

    private List<String> accountNumber;
    private Set<Authority> authorities;
}
