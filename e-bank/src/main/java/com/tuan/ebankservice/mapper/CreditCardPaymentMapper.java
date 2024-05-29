package com.tuan.ebankservice.mapper;

import org.mapstruct.Mapper;

import com.tuan.ebankservice.dto.creditcarddto.CreditCardPaymentRequest;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardPaymentResponse;
import com.tuan.ebankservice.entity.CreditCardPayment;

@Mapper(componentModel = "spring")
public interface CreditCardPaymentMapper {
    CreditCardPayment toCreditCardPayment(CreditCardPaymentRequest request);

    CreditCardPaymentResponse toCreditCardPaymentResponse(CreditCardPayment payment);
}
