package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import com.lota.SafeVaultBankingApplication.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class SafeVaultUserProfile {

    @Id
    private String id;
    //personal information
    private String dob;
    private int age;
    private Gender gender;
    private String motherMaidenName;
    //contact information
    private String address;
    private String stateOfResidence;
    private String lgaOfResidence;
    private String stateOfOrigin;
    //employment information
    private String occupation;
    private String employerName;
    private String employerContact;

    private  NextOfKin nextOfKin;
    private String bvn;
    @DBRef
    private SafeVaultUser safeVaultUser;
}
