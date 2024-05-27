package com.tuan.ebankservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuan.ebankservice.dto.accountdto.*;
import com.tuan.ebankservice.dto.apiresponse.ApiResponse;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardCreationRequest;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardResponse;
import com.tuan.ebankservice.service.AccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("/create")
    ApiResponse<AccountResponse> createAccount(@RequestBody @Valid AccountCreationRequest request){
        return ApiResponse.<AccountResponse>builder()
                .message(HttpStatus.CREATED.name())
                .code(HttpStatus.CREATED.value())
                .result(accountService.createAccount(request))
                .build();
    }

    @GetMapping("/")
    ApiResponse<List<AccountResponse>> getAllAccounts() {
        return ApiResponse.<List<AccountResponse>>builder()
                .message(HttpStatus.FOUND.name())
                .code(HttpStatus.FOUND.value())
                .result(accountService.getAllAccounts())
                .build();
    }
    @PutMapping("/update/{account_id}")
    ResponseEntity<ApiResponse<AccountResponse>> updateAccount(@PathVariable String account_id, @RequestBody @Valid AccountUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<AccountResponse>builder()
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .result(accountService.updateAccount(account_id,request))
                .build());
    }
    @DeleteMapping("/delete")
    ResponseEntity<ApiResponse<String>> deleteAccount(@RequestBody @Valid List<String> request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .result(accountService.deleteAccount(request))
                .build());
    }
    @GetMapping("/{account_number}")
    ApiResponse<AccountResponse> getAccount(@PathVariable String account_number) {
        return ApiResponse.<AccountResponse>builder()
                .message(HttpStatus.FOUND.name())
                .code(HttpStatus.FOUND.value())
                .result(accountService.getAccount(account_number))
                .build();
    }
    @PostMapping("/deposit")
    ApiResponse<String> deposit(@RequestBody @Valid CreditDebitAccountRequest request) throws JsonProcessingException {
        return ApiResponse.<String>builder()
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .result(accountService.deposit(request))
                .build();
    }
    @PostMapping("/withdraw")
    ApiResponse<String> withdraw(@RequestBody @Valid CreditDebitAccountRequest request) throws JsonProcessingException {
        return ApiResponse.<String>builder()
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .result(accountService.withdraw(request))
                .build();
    }
    @PostMapping("/transfer")
    ApiResponse<String> transfer(@RequestBody @Valid TransferRequest request) throws JsonProcessingException {
        return ApiResponse.<String>builder()
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .result(accountService.transfer(request))
                .build();
    }
    @PutMapping("/cancel/{account_id}")
    ApiResponse<String> cancel(@PathVariable String account_id){
        return ApiResponse.<String>builder()
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .result(accountService.cancelAccount(account_id))
                .build();
    }
}
