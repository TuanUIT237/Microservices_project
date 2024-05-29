package com.tuan.ebankservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuan.ebankservice.dto.apiresponse.ApiResponse;
import com.tuan.ebankservice.dto.transactiondto.BankStatementRequest;
import com.tuan.ebankservice.entity.Transaction;
import com.tuan.ebankservice.service.TransactionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {
    TransactionService transactionService;

    @GetMapping("/")
    ApiResponse<List<Transaction>> getTransactions(@RequestBody @Valid BankStatementRequest request) {
        return ApiResponse.<List<Transaction>>builder()
                .message(HttpStatus.FOUND.name())
                .code(HttpStatus.FOUND.value())
                .result(transactionService.transactionHistory(request))
                .build();
    }
}
