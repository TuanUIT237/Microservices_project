package com.tuan.ebankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tuan.ebankservice.dto.accountdto.AccountCreationRequest;
import com.tuan.ebankservice.dto.accountdto.AccountResponse;
import com.tuan.ebankservice.dto.accountdto.AccountUpdateRequest;
import com.tuan.ebankservice.entity.Account;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    Account toAccount(AccountCreationRequest request);

    AccountResponse toAccountResponse(Account account);

    void updateAccount(@MappingTarget Account account, AccountUpdateRequest request);
}
