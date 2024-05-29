package com.tuan.ebankservice.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuan.ebankservice.dto.apiresponse.ApiResponse;
import com.tuan.ebankservice.dto.loandto.*;
import com.tuan.ebankservice.service.LoanService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanController {
    LoanService loanService;

    @PostMapping("/create")
    ResponseEntity<ApiResponse<LoanResponse>> createLoan(@RequestBody @Valid LoanCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LoanResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.name())
                        .result(loanService.createLoan(request))
                        .build());
    }

    @GetMapping("/{loanid}")
    ResponseEntity<ApiResponse<LoanResponse>> getLoan(@PathVariable String loanid) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ApiResponse.<LoanResponse>builder()
                        .code(HttpStatus.FOUND.value())
                        .message(HttpStatus.FOUND.name())
                        .result(loanService.getLoan(loanid))
                        .build());
    }

    @PostMapping("/loanoff")
    ResponseEntity<ApiResponse<LoanPaymentResponse>> payLoanOff(@RequestBody @Valid LoanPaymentRequest request)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LoanPaymentResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .result(loanService.payLoanOff(request))
                        .build());
    }

    @PostMapping("/payment")
    ResponseEntity<ApiResponse<LoanPaymentResponse>> payInstallment(@RequestBody @Valid LoanPaymentRequest request)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LoanPaymentResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .result(loanService.payInstallment(request))
                        .build());
    }

    @PutMapping("/update/{loan_id}")
    ResponseEntity<ApiResponse<LoanResponse>> updateLoan(
            @PathVariable String loan_id, @RequestBody @Valid LoanUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LoanResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .result(loanService.updateLoan(loan_id, request))
                        .build());
    }
}
