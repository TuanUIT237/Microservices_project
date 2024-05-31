package com.tuan.ebankservice.constant;

import lombok.Getter;

@Getter
public class PredefinedTransaction {
    public static final String TRANSACTION_SUCCESS = "Transfer successful";
    public static final String WITHDRAW_SUCCESS = "Withdraw successful";
    public static final String DEPOSIT_SUCCESS = "Deposit successful";
    public static final String WITHDRAW_FAIL = "Withdraw failed";
    public static final String DELETE_TRANSACTION_SUCCESS = "Delete transaction successful";

    private PredefinedTransaction() {}
}
