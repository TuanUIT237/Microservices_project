package com.tuan.ebankservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.tuan.ebankservice.dto.userdto.UserCreationRequest;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuan.ebankservice.dto.loandto.*;
import com.tuan.ebankservice.dto.notificationdto.MessageLoanRequest;
import com.tuan.ebankservice.dto.userprofiledto.ProfileGetUserIdRequest;
import com.tuan.ebankservice.entity.Loan;
import com.tuan.ebankservice.entity.LoanPayment;
import com.tuan.ebankservice.exception.AppException;
import com.tuan.ebankservice.exception.ErrorCode;
import com.tuan.ebankservice.mapper.LoanMapper;
import com.tuan.ebankservice.mapper.LoanPaymentMapper;
import com.tuan.ebankservice.repository.LoanRepository;
import com.tuan.ebankservice.repository.httpclient.ProfileClient;
import com.tuan.ebankservice.util.LoanStatus;
import com.tuan.ebankservice.util.StringUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanService {
    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(12);

    LoanRepository loanRepository;
    LoanMapper loanMapper;
    LoanPaymentService loanPaymentService;
    LoanPaymentMapper loanPaymentMapper;
    ProfileClient profileClient;
    UserService userService;
    SendNotificationService sendNotificationService;

    @Transactional
    public LoanResponse createLoan(LoanCreationRequest request) {
        ProfileGetUserIdRequest profileGetUserIdRequest = ProfileGetUserIdRequest.builder()
                .citizenIdCard(request.getCitizenIdCard())
                .fullName(request.getName())
                .build();
        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                .username(request.getName())
                .email(request.getEmail())
                .citizenIdCard(request.getCitizenIdCard())
                .phone(request.getPhone())
                .build();
        var result = userService.createUser(profileGetUserIdRequest, userCreationRequest, request.getName());
        String userId = result.get(0);
        Loan loan = loanMapper.toLoan(request);
        loan.setUserId(userId);
        loan.setMonthlyInstallmentAmount(calculateMonthlyInstallmentAmount(request));
        loan.setRemainingPrincipal(request.getPrincipalLoanAmount());
        loan.setInterestToBePaid(getTotalInterest(request));
        loan.setPrincipalToBePaid(getPrincipalToPaid(request));
        loan.setDueDate(LocalDate.now().plusMonths(request.getInstallmentCount()));

        LoanResponse loanResponse = loanMapper.toLoanResponse(loanRepository.save(loan));
        loanResponse.setUserId(userId);
        loanResponse.setPassword(result.get(1));

        return loanResponse;
    }

    public BigDecimal calculateMonthlyInstallmentAmount(LoanCreationRequest request) {
        return getPrincipalToPaid(request)
                .divide(BigDecimal.valueOf(request.getInstallmentCount()), RoundingMode.CEILING);
    }

    public LoanResponse updateLoan(String id, LoanUpdateRequest request) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LOAN_NOT_EXISTED));
        loanMapper.updateLoan(loan, request);
        return loanMapper.toLoanResponse(loanRepository.save(loan));
    }

    private BigDecimal getTotalInterest(LoanCreationRequest request) {
        return request.getPrincipalLoanAmount()
                .multiply(INTEREST_RATE)
                .multiply(BigDecimal.valueOf(request.getInstallmentCount()))
                .divide(BigDecimal.valueOf(1200), RoundingMode.CEILING);
    }

    private BigDecimal getPrincipalToPaid(LoanCreationRequest request) {
        return request.getPrincipalLoanAmount().add(getTotalInterest(request));
    }

    private BigDecimal monthlyPrincipal(Loan loan) {
        return loan.getPrincipalLoanAmount()
                .divide(BigDecimal.valueOf(loan.getInstallmentCount()), RoundingMode.CEILING);
    }

    private BigDecimal calculateLateFee(Loan loan) {
        BigDecimal totalInterestRate = INTEREST_RATE.add(INTEREST_RATE.multiply(BigDecimal.valueOf(30 / 100)));
        BigDecimal dayLateCount = lateDayCount(loan).divide(BigDecimal.valueOf(365), RoundingMode.UP);
        return loan.getRemainingPrincipal().multiply(totalInterestRate).multiply(dayLateCount);
    }

    public void updateLoanLate(Loan loan) throws JsonProcessingException {
        loan.setRemainingPrincipal(
                loan.getRemainingPrincipal().add(calculateLateFee(loan)).setScale(0, RoundingMode.CEILING));
        loan.setStatus(LoanStatus.LATE.name());
        loanRepository.save(loan);
        List<String> registrationTokens = userService.getRegistrationTokens(loan.getUserId());
        MessageLoanRequest notificationRequest = MessageLoanRequest.builder()
                .datePayment(LocalDateTime.now())
                .amount(calculateLateFee(loan))
                .paymentType("LATE")
                .registrationTokens(registrationTokens)
                .remainingDebt(loan.getRemainingPrincipal())
                .build();
        sendNotificationService.sendDebtLoan(loan.getUserId(), notificationRequest);
    }

    private BigDecimal lateDayCount(Loan loan) {
        LocalDate dueDate = loan.getDueDate();
        return BigDecimal.valueOf(ChronoUnit.DAYS.between(dueDate, LocalDate.now()));
    }

    public LoanResponse getLoan(String id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LOAN_NOT_EXISTED));
        return loanMapper.toLoanResponse(loan);
    }

    @Transactional
    public LoanPaymentResponse payInstallment(LoanPaymentRequest request) throws JsonProcessingException {
        Loan loan = loanRepository
                .findById(request.getLoanId())
                .orElseThrow(() -> new AppException(ErrorCode.LOAN_NOT_EXISTED));
        if (loan.getRemainingPrincipal().equals(BigDecimal.ZERO)) throw new AppException(ErrorCode.LOAN_PAID_OFF);
        if (loan.getMonthlyInstallmentAmount().compareTo(request.getAmount()) != 0)
            throw new AppException(ErrorCode.AMOUNT_LOAN_INVALID);
        loan.setRemainingPrincipal(
                loan.getRemainingPrincipal().subtract(monthlyPrincipal(loan)).setScale(0, RoundingMode.CEILING));
        LoanPayment loanPayment = loanPaymentService.createLoanPayment(request);
        return addLoanPaymentResponse(loan, loanPayment, request.getAmount());
    }

    private LoanPaymentResponse addLoanPaymentResponse(Loan loan, LoanPayment loanPayment, BigDecimal amount)
            throws JsonProcessingException {
        loan.getLoanPayments().add(loanPayment);
        loanRepository.save(loan);
        List<String> registrationTokens = userService.getRegistrationTokens(loan.getUserId());
        // send notification
        MessageLoanRequest notificationRequest = MessageLoanRequest.builder()
                .datePayment(LocalDateTime.now())
                .amount(amount)
                .paymentType("PAY")
                .registrationTokens(registrationTokens)
                .remainingDebt(loan.getRemainingPrincipal())
                .build();
        sendNotificationService.sendDebtLoan(loan.getId(), notificationRequest);
        LoanPaymentResponse loanPaymentResponse = loanPaymentMapper.toLoanPaymentResponse(loanPayment);
        loanPaymentResponse.setLoanId(loan.getId());
        loanPaymentResponse.setPaymentDate(LocalDateTime.now());
        return loanPaymentResponse;
    }

    @Transactional
    public LoanPaymentResponse payLoanOff(LoanPaymentRequest request) throws JsonProcessingException {
        Loan loan = loanRepository
                .findById(request.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not existed"));
        if (loan.getRemainingPrincipal().equals(BigDecimal.ZERO)) throw new AppException(ErrorCode.LOAN_PAID_OFF);
        if (loan.getRemainingPrincipal().compareTo(request.getAmount()) < 0)
            throw new AppException(ErrorCode.AMOUNT_LOAN_OFF_INVALID);
        loan.setRemainingPrincipal(BigDecimal.ZERO);
        loan.setStatus(LoanStatus.PAID.name());
        LoanPayment loanPayment = loanPaymentService.createLoanPayment(request);
        return addLoanPaymentResponse(loan, loanPayment, loan.getRemainingPrincipal());
    }

    public void isLate(Loan loan) {
        if (loan.getDueDate().isAfter(LocalDate.now())) throw new AppException(ErrorCode.LATE_LOAN);
    }

    public void isDueDay(Loan loan) {
        if (loan.getDueDate().isEqual(LocalDate.now())) throw new AppException(ErrorCode.DUE_DATE);
    }

    public void findLoanLate() {
        List<Loan> loans = loanRepository.findByDueDateBefore(LocalDate.now());
        loans.forEach(loan -> {
            try {
                updateLoanLate(loan);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
