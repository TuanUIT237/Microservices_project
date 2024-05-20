package com.tuan.ebankservice.constant;

import lombok.Getter;

@Getter
public class PredefinedAccount {
    public static final String DEPOSIT_ACCOUNT = "Deposit account with amount";
    public static final String WITHDRAW_ACCOUNT = "Withdraw account with amount";
    public static final String TRANSFER_ACCOUNT = "Transfer account with amount";
    public static final String CANCEL_ACCOUNT_SUCCESS = "Account has been canceled successful";
    private PredefinedAccount(){}
}
