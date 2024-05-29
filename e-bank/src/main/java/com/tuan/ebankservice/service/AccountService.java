package com.tuan.ebankservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuan.ebankservice.constant.PredefinedAccount;
import com.tuan.ebankservice.constant.PredefinedTransaction;
import com.tuan.ebankservice.dto.accountdto.*;
import com.tuan.ebankservice.dto.notificationdto.MessageAccountRequest;
import com.tuan.ebankservice.dto.transactiondto.TransactionRequest;
import com.tuan.ebankservice.dto.userprofiledto.ProfileGetUserIdRequest;
import com.tuan.ebankservice.entity.Account;
import com.tuan.ebankservice.exception.AppException;
import com.tuan.ebankservice.exception.ErrorCode;
import com.tuan.ebankservice.mapper.AccountMapper;
import com.tuan.ebankservice.repository.AccountRepository;
import com.tuan.ebankservice.repository.httpclient.ProfileClient;
import com.tuan.ebankservice.util.AccountStatus;
import com.tuan.ebankservice.util.StringUtil;
import com.tuan.ebankservice.util.TransactionType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    TransactionService transactionService;
    SendNotificationService sendNotificationService;
    ProfileClient profileClient;
    UserService userService;

    @NonFinal
    @Value("${app.openfee}")
    protected BigDecimal OPEN_FEE;

    public AccountResponse createAccount(AccountCreationRequest request) {
        if (accountRepository.existsByName(request.getName())) throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        Account account = accountMapper.toAccount(request);
        ProfileGetUserIdRequest profileGetUserIdRequest = ProfileGetUserIdRequest.builder()
                .citizenIdCard(request.getCitizenIdCard())
                .fullName(request.getName())
                .build();
        String userId = profileClient.getUserId(profileGetUserIdRequest);
        String passwordRandom = null;
        if (!(StringUtils.hasText(userId))) {
            passwordRandom = StringUtil.getRandomNumberAsString(6);
            userId = userService.createUser(
                    request.getName(), passwordRandom, request.getEmail(), request.getCitizenIdCard());
        }

        account.setUserId(userId);

        BigDecimal balance = request.getBalance();

        String status = (balance.compareTo(OPEN_FEE) > 0)
                ? AccountStatus.ACTIVATED.toString()
                : AccountStatus.NOT_ACTIVATED.toString();
        account.setStatus(status);
        AccountResponse accountResponse = accountMapper.toAccountResponse(accountRepository.save(account));
        accountResponse.setUserId(userId);
        accountResponse.setPassword(passwordRandom);

        return accountResponse;
    }

    public AccountResponse updateAccount(String id, AccountUpdateRequest request) {
        Account account =
                accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        accountMapper.updateAccount(account, request);
        return accountMapper.toAccountResponse(account);
    }

    public String deleteAccount(List<String> id) {
        id.forEach(accountRepository::deleteById);
        return PredefinedTransaction.DELETE_TRANSACTION_SUCCESS;
    }

    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    public AccountResponse getAccount(String id) {
        Account account =
                accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        return accountMapper.toAccountResponse(account);
    }

    public String cancelAccount(String id) {
        Account account =
                accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        account.setStatus(AccountStatus.CANCEL.name());
        account.setCancelDate(LocalDateTime.now());
        accountRepository.save(account);
        return PredefinedAccount.CANCEL_ACCOUNT_SUCCESS;
    }

    public void checkCanceledAccount(Account account) {
        if (account.getStatus().equals(AccountStatus.CANCEL.name()) || Objects.nonNull(account.getCancelDate()))
            throw new AppException(ErrorCode.ACCOUNT_CANCELED);
    }

    private void updateBalanceAndSendMessage(Account account, BigDecimal amount, TransactionType transactionType)
            throws JsonProcessingException {
        List<String> registrationTokens = userService.getRegistrationTokens(account.getUserId());
        MessageAccountRequest notificationRequest = MessageAccountRequest.builder()
                .datePayment(LocalDateTime.now())
                .paymentType(transactionType.toString())
                .amount(amount)
                .registrationTokens(registrationTokens)
                .balanceAfterPayment(account.getBalance())
                .build();
        sendNotificationService.sendBalance(account.getUserId(), notificationRequest);
        accountRepository.save(account);
    }

    @Transactional
    public String deposit(CreditDebitAccountRequest request) throws JsonProcessingException {

        Account account = accountRepository
                .findById(request.getAccountNumber())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        checkCanceledAccount(account);

        BigDecimal balance = account.getBalance().add(request.getAmount());
        account.setBalance(balance);

        updateBalanceAndSendMessage(account, request.getAmount(), TransactionType.DEPOSIT);
        // Save transaction
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumberto(request.getAccountNumber())
                .amount(request.getAmount())
                .balanceAfterTransfer(balance)
                .description(PredefinedAccount.DEPOSIT_ACCOUNT + request.getAmount())
                .build();
        transactionService.saveTransaction(transactionRequest, TransactionType.DEPOSIT.toString());

        return PredefinedTransaction.DEPOSIT_SUCCESS;
    }

    @Transactional
    public String withdraw(CreditDebitAccountRequest request) throws JsonProcessingException {
        Account account = accountRepository
                .findById(request.getAccountNumber())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));

        checkCanceledAccount(account);

        if (account.getBalance().compareTo(BigDecimal.valueOf(50000)) < 0
                && account.getBalance().subtract(request.getAmount()).compareTo(BigDecimal.valueOf(50000)) < 0)
            throw new AppException(ErrorCode.ACCOUNT_NOT_ENOUGH);
        BigDecimal balance = account.getBalance().subtract(request.getAmount());
        account.setBalance(balance);

        updateBalanceAndSendMessage(account, request.getAmount(), TransactionType.WITHDRAW);
        // Save transaction
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumberfrom(request.getAccountNumber())
                .amount(request.getAmount())
                .balanceAfterTransfer(balance)
                .description(PredefinedAccount.WITHDRAW_ACCOUNT + request.getAmount())
                .build();
        transactionService.saveTransaction(transactionRequest, TransactionType.WITHDRAW.toString());

        return PredefinedTransaction.WITHDRAW_SUCCESS;
    }

    @Transactional
    public String transfer(TransferRequest request) throws JsonProcessingException {
        Account sourceAccount = accountRepository
                .findById(request.getSourceAccountNumber())
                .orElseThrow(() -> new AppException(ErrorCode.SOURCE_ACCOUNT_NUMBER_NOT_EXISTED));

        Account destinationAccount = accountRepository
                .findById(request.getDestinationAccountNumber())
                .orElseThrow(() -> new AppException(ErrorCode.DESTINATION_ACCOUNT_NUMBER_NOT_EXISTED));

        if (sourceAccount.equals(destinationAccount))
            throw new AppException(ErrorCode.SOURCE_AND_DESTINATION_ARE_SIMILAR);
        if (sourceAccount.getBalance().subtract(request.getAmount()).intValue() <= 50000)
            throw new AppException(ErrorCode.SOURCE_ACCOUNT_NOT_ENOUGH);

        checkCanceledAccount(sourceAccount);
        checkCanceledAccount(destinationAccount);

        BigDecimal balanceAfterTransfer = sourceAccount.getBalance().subtract(request.getAmount());
        sourceAccount.setBalance(balanceAfterTransfer);
        updateBalanceAndSendMessage(sourceAccount, request.getAmount(), TransactionType.TRANSFER);
        //
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));
        receive(destinationAccount, request.getAmount());

        // Save transaction
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumberfrom(request.getSourceAccountNumber())
                .accountNumberto(request.getDestinationAccountNumber())
                .amount(request.getAmount())
                .balanceAfterTransfer(balanceAfterTransfer)
                .description(request.getDestinationAccountNumber() + ";" + request.getContent())
                .build();
        transactionService.saveTransaction(transactionRequest, TransactionType.TRANSFER.toString());

        return PredefinedTransaction.TRANSACTION_SUCCESS;
    }

    @Transactional
    public void receive(Account account, BigDecimal amount) throws JsonProcessingException {
        MessageAccountRequest notificationRequest = MessageAccountRequest.builder()
                .datePayment(LocalDateTime.now())
                .paymentType(TransactionType.RECEIVE.toString())
                .amount(amount)
                .balanceAfterPayment(account.getBalance())
                .build();
        sendNotificationService.sendBalance(account.getId(), notificationRequest);
        accountRepository.save(account);
    }
}
