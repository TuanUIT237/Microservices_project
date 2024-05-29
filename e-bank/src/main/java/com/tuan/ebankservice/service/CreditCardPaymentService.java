package com.tuan.ebankservice.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tuan.ebankservice.dto.creditcarddto.CreditCardPaymentRequest;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardPaymentResponse;
import com.tuan.ebankservice.dto.creditcarddto.CreditPaymentHistoryRequest;
import com.tuan.ebankservice.entity.CreditCardPayment;
import com.tuan.ebankservice.exception.AppException;
import com.tuan.ebankservice.exception.ErrorCode;
import com.tuan.ebankservice.mapper.CreditCardPaymentMapper;
import com.tuan.ebankservice.repository.CreditCardPaymentRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditCardPaymentService {
    CreditCardPaymentRepository creditCardPaymentRepository;
    CreditCardPaymentMapper creditCardPaymentMapper;

    public CreditCardPayment createCreditCardPayment(CreditCardPaymentRequest request, String type) {
        CreditCardPayment creditCardPayment = CreditCardPayment.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .paymentType(type)
                .build();
        return creditCardPaymentRepository.save(creditCardPayment);
    }

    public String getCreditCardId(String id) {
        return creditCardPaymentRepository.findByCreditCardId(id);
    }

    public CreditCardPayment getCreditCardPayment(String id) {
        return creditCardPaymentRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CREDIT_CARD_PAYMENT_NOT_EXISTED));
    }

    public List<CreditCardPaymentResponse> findCreditCardPaymentbyDate(CreditPaymentHistoryRequest request) {
        String creditCardId = request.getCreditCardId();
        LocalDateTime start = request.getStartDate().atStartOfDay();
        LocalDateTime end = request.getEndDate().atTime(23, 59);
        checkBetweenStartandEnd(start, end);
        return creditCardPaymentRepository.findAllByCreditCardIdAndPaymentDateBetween(creditCardId, start, end).stream()
                .map((creditCardPayment -> {
                    CreditCardPaymentResponse creditCardPaymentResponse =
                            creditCardPaymentMapper.toCreditCardPaymentResponse(creditCardPayment);
                    creditCardPaymentResponse.setCreditCardId(getCreditCardId(creditCardPayment.getId()));
                    return creditCardPaymentResponse;
                }))
                .toList();
    }

    private void checkBetweenStartandEnd(LocalDateTime start, LocalDateTime end) {
        if (ChronoUnit.DAYS.between(start, end) > 30)
            throw new AppException(ErrorCode.BETWEEN_START_AND_END_DATE_INVALID);
    }
}
