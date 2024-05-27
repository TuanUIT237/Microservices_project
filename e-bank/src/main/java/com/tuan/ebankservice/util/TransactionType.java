package com.tuan.ebankservice.util;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    RECEIVE,
}
