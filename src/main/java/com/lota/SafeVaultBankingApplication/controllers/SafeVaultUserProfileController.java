package com.lota.SafeVaultBankingApplication.controllers;


import com.lota.SafeVaultBankingApplication.dtos.request.CreateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.request.UpdateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.CreateProfileResponse;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;
import com.lota.SafeVaultBankingApplication.services.SafeVaultUserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class SafeVaultUserProfileController {

    private final SafeVaultUserProfileService safeVaultUserProfileService;

    @PostMapping("/create-profile")
    public ResponseEntity<?> createProfile(@AuthenticationPrincipal String userId, @RequestBody CreateProfileRequest request){
        CreateProfileResponse response = safeVaultUserProfileService.createUserProfile(userId, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal String userId, @RequestBody UpdateProfileRequest request){
        SafeVaultUserProfile response = safeVaultUserProfileService.updateUserProfile(userId, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
