package com.lota.SafeVaultBankingApplication.controllers;

import com.lota.SafeVaultBankingApplication.dtos.response.UserResponseDto;
import com.lota.SafeVaultBankingApplication.services.SafeVaultUserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class SafeVaultUserController {

    private final SafeVaultUserService safeVaultUserService;

    @PostMapping("/process-phoneNumber")
    public ResponseEntity<?> processPhoneNumber(@RequestParam String phoneNumber){

        safeVaultUserService.processUserPhoneNumber(phoneNumber);

        return new ResponseEntity<>(HttpStatus.CONTINUE);
    }

    @PostMapping("/process-email")
    public ResponseEntity<?> processEmail(@AuthenticationPrincipal String userId, @RequestBody String email){

        safeVaultUserService.processUserEmail(userId, email);

        return new ResponseEntity<>(HttpStatus.CONTINUE);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@AuthenticationPrincipal String userId, @RequestBody String otp){

        String response = safeVaultUserService.validateOtp(userId, otp);

        return new ResponseEntity<>(response, HttpStatus.CONTINUE);
    }

    @PostMapping("/regenerate-otp")
    public ResponseEntity<?> regenerateOtp(@AuthenticationPrincipal String userId){

        String response = safeVaultUserService.regenerateOtp(userId);

        return new ResponseEntity<>(response, HttpStatus.CONTINUE);
    }

    @GetMapping("")
    public ResponseEntity<?> viewUser(@RequestParam String userId){

        UserResponseDto response = safeVaultUserService.viewCustomer(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/view-all")
    public ResponseEntity<?> viewAllUser(@RequestParam int page , @RequestParam int size){

        List<UserResponseDto> response = safeVaultUserService.viewAllCustomers(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
