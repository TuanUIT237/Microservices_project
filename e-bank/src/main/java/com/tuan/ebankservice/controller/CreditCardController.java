package com.tuan.ebankservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuan.ebankservice.dto.apiresponse.ApiResponse;
import com.tuan.ebankservice.dto.creditcarddto.*;
import com.tuan.ebankservice.mapper.CreditCardMapper;

import com.tuan.ebankservice.service.CreditCardService;

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
@RequestMapping("/credit")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditCardController {
    CreditCardService creditCardService;
    CreditCardMapper creditCardMapper;
    @PostMapping("/create")
    ResponseEntity<ApiResponse<CreditCardResponse>> createCreditCard(@RequestBody @Valid CreditCardCreationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<CreditCardResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message((HttpStatus.CREATED.name()))
                .result(creditCardService.createCreditCard(request))
                .build());
    }
    @PutMapping("/update/{credit_card_id}")
    ResponseEntity<ApiResponse<CreditCardResponse>> updateCreditCard(@PathVariable String credit_card_id,@RequestBody @Valid CreditCardUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<CreditCardResponse>builder()
                .code(HttpStatus.OK.value())
                .message((HttpStatus.OK.name()))
                .result(creditCardService.updateCreditCard(credit_card_id, request))
                .build());
    }

    @PostMapping("/spend")
    ResponseEntity<ApiResponse<CreditCardPaymentResponse>> spendMoney(@RequestBody @Valid CreditCardSpendRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<CreditCardPaymentResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .result(creditCardService.spendMoney(request))
                        .build());
    }
    @PostMapping("/refund")
    ResponseEntity<ApiResponse<CreditCardPaymentResponse>> refundMoney(@RequestBody @Valid CreditCardRefundRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<CreditCardPaymentResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .result(creditCardService.refundMoney(request))
                        .build());
    }

    @GetMapping("/{creditcardid}")
    ResponseEntity<ApiResponse<CreditCardResponse>> getCreditCard(@PathVariable @Valid String creditcardid){
        return ResponseEntity.status(HttpStatus.FOUND).body(ApiResponse.<CreditCardResponse>builder()
                .code(HttpStatus.FOUND.value())
                .message((HttpStatus.FOUND.name()))
                .result(creditCardMapper.toCreditCardResponse(creditCardService.getCreditCard(creditcardid)))
                .build());
    }
    @GetMapping("/")
    ResponseEntity<ApiResponse<List<CreditCardResponse>>> getAllCreditCards(){
        return ResponseEntity.status(HttpStatus.FOUND).body(ApiResponse.<List<CreditCardResponse>>builder()
                .code(HttpStatus.FOUND.value())
                .message((HttpStatus.FOUND.name()))
                .result(creditCardService.getAllCreditCards())
                .build());
    }

    @GetMapping("/history-payment")
    ResponseEntity<ApiResponse<List<CreditCardPaymentResponse>>> historyPayment(@RequestBody @Valid CreditPaymentHistoryRequest request){
        return ResponseEntity.status(HttpStatus.FOUND).body(ApiResponse.<List<CreditCardPaymentResponse>>builder()
                .code(HttpStatus.FOUND.value())
                .message((HttpStatus.FOUND.name()))
                .result(creditCardService.findCreditCardPaymentByDate(request))
                .build());
    }
    @PutMapping("/cancel/{creditcardid}")
    ResponseEntity<ApiResponse<String>> cancelCreditCard(@PathVariable String creditcardid){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message((HttpStatus.OK.name()))
                .result(creditCardService.cancelCreditCard(creditcardid))
                .build());
    }
}
