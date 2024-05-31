package com.tuan.ebankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tuan.ebankservice.dto.creditcarddto.CreditCardCreationRequest;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardResponse;
import com.tuan.ebankservice.dto.creditcarddto.CreditCardUpdateRequest;
import com.tuan.ebankservice.entity.CreditCard;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CreditCardMapper {
    CreditCard toCreditCard(CreditCardCreationRequest request);

    CreditCardResponse toCreditCardResponse(CreditCard creditCard);

    void updateCreditCard(@MappingTarget CreditCard creditCard, CreditCardUpdateRequest request);
}
