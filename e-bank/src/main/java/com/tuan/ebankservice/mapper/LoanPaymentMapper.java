package com.tuan.ebankservice.mapper;

import com.tuan.ebankservice.dto.loandto.LoanPaymentRequest;
import com.tuan.ebankservice.dto.loandto.LoanPaymentResponse;
import com.tuan.ebankservice.entity.LoanPayment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanPaymentMapper {
    LoanPayment toLoanPayment(LoanPaymentRequest request);
    LoanPaymentResponse toLoanPaymentResponse(LoanPayment payment);
}
