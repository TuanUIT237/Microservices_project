package com.tuan.ebankservice.service;

import com.tuan.ebankservice.constant.PredefinedAccount;
import com.tuan.ebankservice.constant.PredefinedTransaction;
import com.tuan.ebankservice.dto.accountdto.*;
import com.tuan.ebankservice.dto.notificationdto.NotificationRequest;
import com.tuan.ebankservice.dto.transactiondto.TransactionRequest;
import com.tuan.ebankservice.dto.userdto.UserCreationRequest;
import com.tuan.ebankservice.dto.userprofiledto.ProfileGetUserIdRequest;
import com.tuan.ebankservice.entity.Account;
import com.tuan.ebankservice.exception.AppException;
import com.tuan.ebankservice.exception.ErrorCode;
import com.tuan.ebankservice.mapper.AccountMapper;
import com.tuan.ebankservice.repository.AccountRepository;
import com.tuan.ebankservice.repository.httpclient.ProfileClient;
import com.tuan.ebankservice.repository.httpclient.UserClient;
import com.tuan.ebankservice.util.AccountStatus;
import com.tuan.ebankservice.util.StringUtil;
import com.tuan.ebankservice.util.TransactionType;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    TransactionService transactionService;
    SendNotificationService sendNotificationService;
    ProfileClient profileClient;
    UserClient userClient;
    @NonFinal
    @Value("${app.openfee}")
    protected BigDecimal OPEN_FEE;
    public AccountResponse createAccount(AccountCreationRequest request){
        if(accountRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        Account account = accountMapper.toAccount(request);
        ProfileGetUserIdRequest profileGetUserIdRequest = ProfileGetUserIdRequest.builder()
                .citizenIdCard(request.getCitizenIdCard())
                .fullName(request.getName())
                .build();
        String userId = profileClient.getUserId(profileGetUserIdRequest);

        if(!(StringUtils.hasText(userId))) {
            String username = request.getName().replace(" ","");
            String firstName = request.getName().substring(0,request.getName().indexOf(" "));
            String lastName = request.getName().replace(firstName + " ","");
            UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .citizenIdCard(request.getCitizenIdCard())
                    .password(StringUtil.getRandomNumberAsString(6))
                    .build();
            userClient.createUser(userCreationRequest);
        }
        account.setUserId(userId);
        BigDecimal balance = request.getBalance();
        String status = (balance.compareTo(OPEN_FEE) > 0) ? AccountStatus.ACTIVATED.toString() : AccountStatus.NOT_ACTIVATED.toString();
        account.setStatus(status);
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public AccountResponse updateAccount(String id, AccountUpdateRequest request){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        accountMapper.updateAccount(account,request);
        return accountMapper.toAccountResponse(account);
    }
    public String deleteAccount(List<String> id){
        id.forEach(accountRepository::deleteById);
        return PredefinedTransaction.DELETE_TRANSACTION_SUCCESS;
    }
    public List<AccountResponse> getAllAccounts(){
        return accountRepository.findAll().stream().map(accountMapper::toAccountResponse).toList();
    }
    public AccountResponse getAccount(String id){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        return accountMapper.toAccountResponse(account);
    }
    public String cancelAccount(String id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        account.setStatus(AccountStatus.CANCEL.name());
        account.setCancelDate(LocalDateTime.now());
        accountRepository.save(account);
        return PredefinedAccount.CANCEL_ACCOUNT_SUCCESS;
    }
    public void checkCanceledAccount(Account account){
        if(account.getStatus().equals(AccountStatus.CANCEL.name()) || Objects.nonNull(account.getCancelDate()))
            throw new AppException(ErrorCode.ACCOUNT_CANCELED);
    }
    public void updateBalanceAndSendMessage(Account account,BigDecimal amount, TransactionType transactionType){
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .accountId(account.getId())
                .datePayment(LocalDateTime.now())
                .paymentType(transactionType.toString())
                .amount(amount)
                .balanceAfterPayment(account.getBalance())
                .build();
        sendNotificationService.sendBalance(notificationRequest);
        accountRepository.save(account);

    }
    @Transactional
    public String deposit(CreditDebitAccountRequest request){

        Account account = accountRepository.findById(request.getAccountNumber())
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        checkCanceledAccount(account);

        BigDecimal balance = account.getBalance().add(request.getAmount());
        account.setBalance(balance);


        updateBalanceAndSendMessage(account, request.getAmount(), TransactionType.DEPOSIT);
        //Save transaction
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
    public String withdraw(CreditDebitAccountRequest request){
        Account account = accountRepository.findById(request.getAccountNumber())
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));

        checkCanceledAccount(account);

        if(account.getBalance().compareTo(BigDecimal.valueOf(50000)) <0
                && account.getBalance().subtract(request.getAmount()).compareTo(BigDecimal.valueOf(50000)) <0)
            throw new AppException(ErrorCode.ACCOUNT_NOT_ENOUGH);
        BigDecimal balance = account.getBalance().subtract(request.getAmount());
        account.setBalance(balance);


        updateBalanceAndSendMessage(account, request.getAmount(), TransactionType.WITHDRAW);
        //Save transaction
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumberfrom(request.getAccountNumber())
                .amount(request.getAmount())
                .balanceAfterTransfer(balance)
                .description(PredefinedAccount.WITHDRAW_ACCOUNT + request.getAmount())
                .build();
        transactionService.saveTransaction(transactionRequest,TransactionType.WITHDRAW.toString());

        return PredefinedTransaction.WITHDRAW_SUCCESS;
    }
    @Transactional
    public String transfer(TransferRequest request){
        Account sourceAccount = accountRepository.findById(request.getSourceAccountNumber())
                .orElseThrow(()-> new AppException(ErrorCode.SOURCE_ACCOUNT_NUMBER_NOT_EXISTED));

        Account destinationAccount = accountRepository.findById(request.getDestinationAccountNumber())
                .orElseThrow(()-> new AppException(ErrorCode.DESTINATION_ACCOUNT_NUMBER_NOT_EXISTED));

        if(sourceAccount.equals(destinationAccount))
            throw new AppException(ErrorCode.SOURCE_AND_DESTINATION_ARE_SIMILAR);
        if(sourceAccount.getBalance().subtract(request.getAmount()).intValue() <= 50000)
            throw new AppException(ErrorCode.SOURCE_ACCOUNT_NOT_ENOUGH);

        checkCanceledAccount(sourceAccount);
        checkCanceledAccount(destinationAccount);

        BigDecimal balanceAfterTransfer = sourceAccount.getBalance().subtract(request.getAmount());
        sourceAccount.setBalance(balanceAfterTransfer);
        updateBalanceAndSendMessage(sourceAccount,request.getAmount(), TransactionType.TRANSFER);
        //
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));
        receive(destinationAccount,request.getAmount());

        //Save transaction
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
    public void receive(Account account, BigDecimal amount){
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .accountId(account.getId())
                .datePayment(LocalDateTime.now())
                .paymentType(TransactionType.RECEIVE.toString())
                .amount(amount)
                .balanceAfterPayment(account.getBalance())
                .build();
        sendNotificationService.sendBalance(notificationRequest);
        accountRepository.save(account);
    }
}
