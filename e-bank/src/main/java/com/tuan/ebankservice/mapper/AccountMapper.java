package com.tuan.ebankservice.mapper;

import com.tuan.ebankservice.dto.accountdto.AccountCreationRequest;
import com.tuan.ebankservice.dto.accountdto.AccountResponse;
import com.tuan.ebankservice.dto.accountdto.AccountUpdateRequest;
import com.tuan.ebankservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountCreationRequest request);
    AccountResponse toAccountResponse(Account account);
    void updateAccount(@MappingTarget Account account, AccountUpdateRequest request);
}
