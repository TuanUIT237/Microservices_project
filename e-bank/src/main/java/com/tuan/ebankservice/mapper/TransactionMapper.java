package com.tuan.ebankservice.mapper;

import com.tuan.ebankservice.dto.accountdto.CreditDebitAccountRequest;
import com.tuan.ebankservice.dto.transactiondto.TransactionRequest;
import com.tuan.ebankservice.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(TransactionRequest request);
}
