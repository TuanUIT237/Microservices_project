package com.tuan.ebankservice.mapper;

import org.mapstruct.Mapper;

import com.tuan.ebankservice.dto.transactiondto.TransactionRequest;
import com.tuan.ebankservice.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(TransactionRequest request);
}
