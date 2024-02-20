package com.lota.SafeVaultBankingApplication.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SafeVaultUser {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
