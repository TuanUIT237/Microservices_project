package com.tuan.ebankservice.service;

import org.springframework.stereotype.Service;

import com.tuan.ebankservice.dto.loandto.LoanPaymentRequest;
import com.tuan.ebankservice.entity.LoanPayment;
import com.tuan.ebankservice.mapper.LoanPaymentMapper;
import com.tuan.ebankservice.repository.LoanPaymentRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanPaymentService {
    LoanPaymentMapper loanPaymentMapper;
    LoanPaymentRepository loanPaymentRepository;

    public LoanPayment createLoanPayment(LoanPaymentRequest request) {
        LoanPayment loanPayment = loanPaymentMapper.toLoanPayment(request);
        return loanPaymentRepository.save(loanPayment);
    }
}
