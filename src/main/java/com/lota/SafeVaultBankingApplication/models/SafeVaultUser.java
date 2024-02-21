package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.enums.Authority;
import lombok.*;
import org.springframework.data.annotation.Id;

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

    private Set<Authority> authorities;
}
