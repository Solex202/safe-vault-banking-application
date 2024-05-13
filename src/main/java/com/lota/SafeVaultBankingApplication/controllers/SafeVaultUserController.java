package com.lota.SafeVaultBankingApplication.controllers;

import com.lota.SafeVaultBankingApplication.services.SafeVaultUserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class SafeVaultUserController {

    private final SafeVaultUserService safeVaultUserService;

    @PostMapping("/process-phoneNumber")
    public ResponseEntity<?> processPhoneNumber(@RequestBody String phoneNumber){

        safeVaultUserService.processUserPhoneNumber(phoneNumber);

        return new ResponseEntity<>(HttpStatus.CONTINUE);
    }
}
