package com.lota.SafeVaultBankingApplication.controllers;


import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.dtos.response.ViewTransactionResponseDto;
import com.lota.SafeVaultBankingApplication.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get-transaction/{transactionId}")
    public ResponseEntity<?> viewTransaction(@PathVariable String transactionId){
        ViewTransactionResponseDto responseDto = transactionService.viewTransaction(transactionId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/view-transactions}")
    public ResponseEntity<?> viewAllTransactions(@RequestParam int page, @RequestParam int size){
        Page<ViewTransactionResponseDto> responseDto = transactionService.viewAllTransactions(page, size);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
