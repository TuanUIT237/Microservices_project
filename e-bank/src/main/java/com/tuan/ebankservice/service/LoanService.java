package com.tuan.ebankservice.service;

import com.tuan.ebankservice.dto.loandto.*;
import com.tuan.ebankservice.entity.Loan;
import com.tuan.ebankservice.entity.LoanPayment;
import com.tuan.ebankservice.exception.AppException;
import com.tuan.ebankservice.exception.ErrorCode;
import com.tuan.ebankservice.mapper.LoanMapper;

import com.tuan.ebankservice.mapper.LoanPaymentMapper;
import com.tuan.ebankservice.repository.LoanRepository;
import com.tuan.ebankservice.util.LoanStatus;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanService {
    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(12);
    private final int INSTALLMENT_COUNT_LIMIT = 360;
    LoanRepository loanRepository;
    LoanMapper loanMapper;
    LoanPaymentService loanPaymentService;
    LoanPaymentMapper loanPaymentMapper;
    public LoanResponse createLoan(LoanCreationRequest request){
        Loan loan = loanMapper.toLoan(request);
        loan.setMonthlyInstallmentAmount(calculateMonthlyInstallmentAmount(request));
        loan.setRemainingPrincipal(request.getPrincipalLoanAmount());
        loan.setInterestToBePaid(getTotalInterest(request));
        loan.setPrincipalToBePaid(getPrincipalToPaid(request));
        loan.setDueDate(LocalDate.now().plusMonths(request.getInstallmentCount()));
        loan.setStatus(LoanStatus.CONTINUING.name());
        return loanMapper.toLoanResponse(loanRepository.save(loan));
    }
    public BigDecimal calculateMonthlyInstallmentAmount(LoanCreationRequest request){
        return getPrincipalToPaid(request).divide(BigDecimal.valueOf(request.getInstallmentCount()), RoundingMode.CEILING);
    }
    public LoanResponse updateLoan(String id,LoanUpdateRequest request){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.LOAN_NOT_EXISTED));
        loanMapper.updateLoan(loan,request);
        return loanMapper.toLoanResponse(loan);
    }
    private BigDecimal getTotalInterest(LoanCreationRequest request){
        return request.getPrincipalLoanAmount().multiply(INTEREST_RATE)
                .multiply(BigDecimal.valueOf(request.getInstallmentCount()))
                .divide(BigDecimal.valueOf(1200),RoundingMode.CEILING);
    }

    private BigDecimal getPrincipalToPaid(LoanCreationRequest request) {
        return request.getPrincipalLoanAmount().add(getTotalInterest(request));
    }
    private BigDecimal monthlyPrincipal(Loan loan){
        return loan.getPrincipalLoanAmount().divide(BigDecimal.valueOf(loan.getInstallmentCount()),RoundingMode.CEILING);
    }
    private BigDecimal calculateLateFee(Loan loan) {
        BigDecimal totalInterestRate = INTEREST_RATE.add(INTEREST_RATE.multiply(BigDecimal.valueOf(30 / 100)));
        BigDecimal dayLateCount = lateDayCount(loan).divide(BigDecimal.valueOf(365),RoundingMode.UP);
        return loan.getRemainingPrincipal().multiply(totalInterestRate).multiply(dayLateCount);
    }
    public void updateLoanLate(LoanUpdateRequest request){
        Loan loan = loanRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Loan not existed"));
        loan.setRemainingPrincipal(loan.getRemainingPrincipal().add(calculateLateFee(loan))
                .setScale(0,RoundingMode.CEILING));
        loan.setStatus(LoanStatus.LATE.name());
        loanMapper.updateLoan(loan, request);
    }
    private BigDecimal lateDayCount(Loan loan){
        LocalDate dueDate = loan.getDueDate();
        return BigDecimal.valueOf(ChronoUnit.DAYS.between(dueDate, LocalDate.now()));
    }
    public LoanResponse getLoan(String id){
        Loan loan = loanRepository.findById(id).orElseThrow(()-> new RuntimeException("Loan not existed"));
        return loanMapper.toLoanResponse(loan);
    }
    @Transactional
    public LoanPaymentResponse payInstallment(LoanPaymentRequest request){
        Loan loan = loanRepository.findById(request.getLoanId()).orElseThrow(()-> new RuntimeException("Loan not existed"));
        if(loan.getRemainingPrincipal().equals(BigDecimal.ZERO))
            throw new AppException(ErrorCode.LOAN_PAID_OFF);
        if(loan.getMonthlyInstallmentAmount().compareTo(request.getAmount()) != 0)
            throw new AppException(ErrorCode.AMOUNT_LOAN_INVALID);
        loan.setRemainingPrincipal(loan.getRemainingPrincipal().subtract(monthlyPrincipal(loan))
                .setScale(0,RoundingMode.CEILING));
        LoanPayment loanPayment = loanPaymentService.createLoanPayment(request);
        return addLoanPaymentResponse(loan,loanPayment);
    }
    private LoanPaymentResponse addLoanPaymentResponse(Loan loan, LoanPayment loanPayment){
        loan.getLoanPayments().add(loanPayment);
        loanRepository.save(loan);

        LoanPaymentResponse loanPaymentResponse = loanPaymentMapper.toLoanPaymentResponse(loanPayment);
        loanPaymentResponse.setLoanId(loan.getId());
        return loanPaymentResponse;
    }
    @Transactional
    public LoanPaymentResponse payLoanOff(LoanPaymentRequest request){
        Loan loan = loanRepository.findById(request.getLoanId()).orElseThrow(()-> new RuntimeException("Loan not existed"));
        if(loan.getRemainingPrincipal().equals(BigDecimal.ZERO))
            throw new AppException(ErrorCode.LOAN_PAID_OFF);
        if(loan.getRemainingPrincipal().compareTo(request.getAmount()) >= 0)
            throw new AppException(ErrorCode.AMOUNT_LOAN_INVALID);
        loan.setRemainingPrincipal(BigDecimal.ZERO);
        loan.setStatus(LoanStatus.PAID.name());
        LoanPayment loanPayment = loanPaymentService.createLoanPayment(request);
        return addLoanPaymentResponse(loan,loanPayment);
    }
    public void isLate(Loan loan){
        if(loan.getDueDate().isAfter(LocalDate.now()))
            throw new AppException(ErrorCode.LATE_LOAN);
    }
    public void isDueDay(Loan loan){
        if(loan.getDueDate().isEqual(LocalDate.now()))
            throw new AppException(ErrorCode.DUE_DATE);
    }
}