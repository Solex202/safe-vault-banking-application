package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.enums.Authority;
import lombok.*;

import java.util.Set;

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

    private Set<Authority> authorities;
}
