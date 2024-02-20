package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor@NoArgsConstructor@Builder
public class SafeVaultUserProfile {

    @Id
    private String id;
    //personal information
    private String dob;
    private int age;
    private String gender;
    private String motherMaidenName;
    private String image;
    //contact information
    private String phoneNumber;
    private String address;
    private String stateOfResidence;
    private String stateOfOrigin;
    //employment information
    private String occupation;
    private String employerName;

    private NextOfKin nextOfKin;
    private String bvn;
}
