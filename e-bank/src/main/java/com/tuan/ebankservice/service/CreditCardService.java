package com.tuan.ebankservice.service;

import com.tuan.ebankservice.constant.PredefinedCreditCard;
import com.tuan.ebankservice.dto.creditcarddto.*;
import com.tuan.ebankservice.dto.userdto.UserCreationRequest;
import com.tuan.ebankservice.dto.userprofiledto.ProfileGetUserIdRequest;
import com.tuan.ebankservice.entity.CreditCard;
import com.tuan.ebankservice.entity.CreditCardPayment;
import com.tuan.ebankservice.exception.AppException;
import com.tuan.ebankservice.exception.ErrorCode;
import com.tuan.ebankservice.mapper.CreditCardMapper;
import com.tuan.ebankservice.mapper.CreditCardPaymentMapper;

import com.tuan.ebankservice.repository.CreditCardRepository;
import com.tuan.ebankservice.repository.httpclient.ProfileClient;
import com.tuan.ebankservice.repository.httpclient.UserClient;
import com.tuan.ebankservice.util.CreditCardStatus;
import com.tuan.ebankservice.util.CreditPaymentType;
import com.tuan.ebankservice.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CreditCardService {
    @NonFinal
    @Value("${app.interest_credit}")
    protected float INTEREST;
    CreditCardRepository creditCardRepository;
    CreditCardMapper creditCardMapper;
    CreditCardPaymentService creditCardPaymentService;
    CreditCardPaymentMapper creditCardPaymentMapper;
    ProfileClient profileClient;
    UserService userService;

    public CreditCard getCreditCard(String id){
        return creditCardRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CREDIT_CARD_NOT_EXISTED)
        );
    }
    public List<CreditCardResponse> getAllCreditCards(){
        return creditCardRepository.findAll().stream().map(creditCardMapper::toCreditCardResponse).toList();
    }
    public CreditCardResponse createCreditCard(CreditCardCreationRequest request){
        ProfileGetUserIdRequest profileGetUserIdRequest = ProfileGetUserIdRequest.builder()
                .citizenIdCard(request.getCitizenIdCard())
                .fullName(request.getName())
                .build();
        String userId = profileClient.getUserId(profileGetUserIdRequest);
        String passwordRandom = null;
        if(!(StringUtils.hasText(userId))) {
            passwordRandom = StringUtil.getRandomNumberAsString(6);
            userId = userService.createUser(request.getName(),
                    passwordRandom,
                    request.getEmail(),
                    request.getCitizenIdCard());
        }

        CreditCard creditCard = creditCardMapper.toCreditCard(request);
        creditCard.setUserId(userId);
        creditCard.setTotalLimit(request.getLimit());
        creditCard.setAvailableLimit(request.getLimit());
        creditCard.setCurrentDebt(BigDecimal.ZERO);
        creditCard.setMinimumPaymentAmount(calculateMinimumPaymentAmount(request.getLimit()));
        creditCard.setCutoffDate(request.getDayOfCutOff());

        CreditCardResponse creditCardResponse = creditCardMapper.toCreditCardResponse(creditCardRepository.save(creditCard));
        creditCardResponse.setUserId(userId);
        creditCardResponse.setPassword(passwordRandom);

        return creditCardResponse;
    }
    public BigDecimal calculateMinimumPaymentAmount(BigDecimal limit){
        return BigDecimal.valueOf(INTEREST * limit.floatValue());
    }

    public void checkLimited(CreditCard creditCard){
        if(creditCard.getTotalLimit().compareTo(creditCard.getCurrentDebt()) == 0)
            throw new AppException(ErrorCode.CREDIT_CARD_LIMITED);
    }
    public void checkExpiredDate(CreditCard creditCard){
        if(creditCard.getExpireDate().isEqual(LocalDate.now()))
            throw new AppException(ErrorCode.CREDIT_CARD_EXPIRED);
    }
    public void checkCancelled(CreditCard creditCard){
        if(creditCard.getStatus().equals(CreditCardStatus.PASSIVE.toString()))
            throw new AppException(ErrorCode.CREDIT_CARD_CANCELLED);
    }
    public void checkCvv(CreditCard creditCard, String cvv){
        if(!creditCard.getCvv().equals(cvv))
            throw new AppException(ErrorCode.CVV_INVALID);
    }
    public void checkCurrentDebt(CreditCard creditCard){
        if(!(creditCard.getCurrentDebt().compareTo(BigDecimal.ZERO) ==0))
            throw new RuntimeException("You are in debt is " + creditCard.getCurrentDebt());
    }
    @Transactional
    public CreditCardPaymentResponse spendMoney(CreditCardSpendRequest request){
        CreditCard creditCard = getCreditCard(request.getCreditCardId());
        //Check Cvv
        checkCvv(creditCard,request.getCvvNo());
        //Check expired date
        checkExpiredDate(creditCard);
        //check limit
        checkLimited(creditCard);
        // check cancelled
        checkCancelled(creditCard);
        if(creditCard.getAvailableLimit().compareTo(request.getAmount())<0)
            throw new AppException(ErrorCode.AVAILABLE_LIMIT_LESS_AMOUNT);

        creditCard.setCurrentDebt(creditCard.getCurrentDebt().add(request.getAmount())
                .setScale(0,RoundingMode.CEILING));
        creditCard.setAvailableLimit(creditCard.getAvailableLimit().subtract(request.getAmount())
                .setScale(0,RoundingMode.CEILING));


        CreditCardPayment creditCardPayment = creditCardPaymentService.createCreditCardPayment(
                CreditCardPaymentRequest.builder()
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .build(), CreditPaymentType.SPEND.toString());

        return addCreditCardPayment(creditCard, creditCardPayment);
    }
    @Transactional
    public CreditCardPaymentResponse addLimit(CreditCardAddRequest request){
        CreditCard creditCard = getCreditCard(request.getCreditCardId());
        //Check Cvv
        checkCvv(creditCard,request.getCvvNo());
        //Check expired date
        checkExpiredDate(creditCard);
        //check limit
        checkLimited(creditCard);
        // check cancelled
        checkCancelled(creditCard);

        creditCard.setCurrentDebt(creditCard.getCurrentDebt().subtract(request.getAmount())
                .setScale(0,RoundingMode.CEILING)
        );
        creditCard.setAvailableLimit(creditCard.getAvailableLimit().add(request.getAmount())
                .setScale(0,RoundingMode.CEILING)
        );


        CreditCardPayment creditCardPayment = creditCardPaymentService.createCreditCardPayment(
                CreditCardPaymentRequest.builder()
                        .description(request.getDescription())
                        .amount(request.getAmount())
                        .build(), CreditPaymentType.ADD.toString());

        return addCreditCardPayment(creditCard, creditCardPayment);
    }
    @Transactional
    public CreditCardPaymentResponse addCreditCardPayment(CreditCard creditCard, CreditCardPayment creditCardPayment) {
        creditCard.getCreditCardPayments().add(creditCardPayment);
        creditCardRepository.save(creditCard);
        CreditCardPaymentResponse creditCardPaymentResponse =
                creditCardPaymentMapper.toCreditCardPaymentResponse(creditCardPayment);
        creditCardPaymentResponse.setCreditCardId(creditCard.getId());

        LocalDateTime paymentDate= creditCardPaymentService.getCreditCardPayment(creditCardPayment.getId()).getPaymentDate();
        creditCardPaymentResponse.setPaymentDate(paymentDate);

        return creditCardPaymentResponse;
    }
    public CreditCardResponse updateCreditCard(String id, CreditCardUpdateRequest request){
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CREDIT_CARD_NOT_EXISTED));
        creditCardMapper.updateCreditCard(creditCard, request);
        return creditCardMapper.toCreditCardResponse(creditCard);
    }
    @Transactional
    public CreditCardPaymentResponse refundMoney(CreditCardRefundRequest request){
        CreditCardPayment oldCreditCardPayment = creditCardPaymentService.getCreditCardPayment(request.getCreditCardPaymentId());
        CreditCard creditCard = getCreditCard(creditCardPaymentService.getCreditCardId(oldCreditCardPayment.getId()));
        //Check expired date
        checkExpiredDate(creditCard);
        creditCard.setCurrentDebt(creditCard.getCurrentDebt().subtract(oldCreditCardPayment.getAmount())
                .setScale(0,RoundingMode.CEILING));
        creditCard.setAvailableLimit(creditCard.getAvailableLimit().add(oldCreditCardPayment.getAmount())
                .setScale(0,RoundingMode.CEILING));

        CreditCardPayment creditCardPayment = creditCardPaymentService.createCreditCardPayment(CreditCardPaymentRequest.builder()
                .description(request.getDescription())
                .amount(oldCreditCardPayment.getAmount())
                .build(),CreditPaymentType.REFUND.name());

        return addCreditCardPayment(creditCard, creditCardPayment);
    }
    public String cancelCreditCard(String id){
        CreditCard creditCard = getCreditCard(id);
        checkCancelled(creditCard);
        checkCurrentDebt(creditCard);
        creditCard.setCancelDate(LocalDateTime.now());
        creditCard.setStatus(CreditCardStatus.PASSIVE.toString());
        creditCardRepository.save(creditCard);
        return PredefinedCreditCard.CREDIT_CARD_CANCELLED;
    }
    public List<CreditCardPaymentResponse> findCreditCardPaymentByDate(CreditPaymentHistoryRequest request){
        getCreditCard(request.getCreditCardId());
        return creditCardPaymentService.findCreditCardPaymentbyDate(request);
    }


}
