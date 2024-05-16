package com.lota.SafeVaultBankingApplication.controllers;


import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionUserController {

    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<?> performTransfer(@AuthenticationPrincipal String userId, @RequestBody FundTransferDto transferDto){

        String response =  transactionService.performTransfer(userId, transferDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
