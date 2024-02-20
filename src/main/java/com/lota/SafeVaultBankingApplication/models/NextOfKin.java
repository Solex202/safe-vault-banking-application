package com.lota.SafeVaultBankingApplication.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class NextOfKin {

    private String id;
    private String firstName;
    private String lastname;
    private String emailAddress;
    private String phoneNumber;
    private String address;

    @DBRef
    private SafeVaultUser safeVaultUser;

}
