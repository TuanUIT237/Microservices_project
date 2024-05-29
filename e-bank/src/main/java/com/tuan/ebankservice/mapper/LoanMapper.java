package com.tuan.ebankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tuan.ebankservice.dto.loandto.LoanCreationRequest;
import com.tuan.ebankservice.dto.loandto.LoanResponse;
import com.tuan.ebankservice.dto.loandto.LoanUpdateRequest;
import com.tuan.ebankservice.entity.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toLoan(LoanCreationRequest request);

    LoanResponse toLoanResponse(Loan loan);

    void updateLoan(@MappingTarget Loan loan, LoanUpdateRequest request);
}
