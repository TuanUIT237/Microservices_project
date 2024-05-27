package com.tuan.ebankservice.service;

import com.tuan.ebankservice.dto.transactiondto.BankStatementRequest;
import com.tuan.ebankservice.dto.transactiondto.TransactionRequest;
import com.tuan.ebankservice.entity.Transaction;
import com.tuan.ebankservice.mapper.TransactionMapper;
import com.tuan.ebankservice.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;
    public void saveTransaction(TransactionRequest request, String type){
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setType(type);
        transactionRepository.save(transaction);
    }

    public List<Transaction> transactionHistory(BankStatementRequest request) {
        LocalDateTime start = request.getStartDate().atStartOfDay();
        LocalDateTime end = request.getEndDate().atStartOfDay().plusDays(1);

        return transactionRepository.findAllByAccountNumberfromAndTransferDateBetween(request.getAccountNumber(), start, end);
    }
}
