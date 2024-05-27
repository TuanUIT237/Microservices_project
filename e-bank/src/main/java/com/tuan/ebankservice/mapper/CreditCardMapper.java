package com.tuan.ebankservice.mapper;

import com.tuan.ebankservice.dto.creditcarddto.CreditCardCreationRequest;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardResponse;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardUpdateRequest;
import com.tuan.ebankservice.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {
    CreditCard toCreditCard(CreditCardCreationRequest request);
    CreditCardResponse toCreditCardResponse(CreditCard creditCard);
    void updateCreditCard(@MappingTarget CreditCard creditCard, CreditCardUpdateRequest request);
}
