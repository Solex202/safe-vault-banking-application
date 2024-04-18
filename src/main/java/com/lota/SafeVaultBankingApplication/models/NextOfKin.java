package com.lota.SafeVaultBankingApplication.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NextOfKin {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private String relationship;

    @DBRef
    private SafeVaultUser safeVaultUser;

}
