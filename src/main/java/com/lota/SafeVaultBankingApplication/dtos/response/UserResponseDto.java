package com.lota.SafeVaultBankingApplication.dtos.response;

import com.lota.SafeVaultBankingApplication.models.enums.AccountType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String email;
    private String passcode;
    private String firstname;
    private String lastname;
    private String image;
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
    private AccountType accountType;

}
