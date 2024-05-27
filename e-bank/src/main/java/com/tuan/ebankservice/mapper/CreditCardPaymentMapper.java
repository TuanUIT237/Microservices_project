package com.tuan.ebankservice.mapper;

import com.tuan.ebankservice.dto.creditcarddto.CreditCardPaymentResponse;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardPaymentRequest;
import com.tuan.ebankservice.entity.CreditCardPayment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardPaymentMapper {
    CreditCardPayment toCreditCardPayment(CreditCardPaymentRequest request);
    CreditCardPaymentResponse toCreditCardPaymentResponse(CreditCardPayment payment);
}
