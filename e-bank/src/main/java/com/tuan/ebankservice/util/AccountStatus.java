package com.tuan.ebankservice.util;

import lombok.Getter;

@Getter
public enum AccountStatus {
    NOT_ACTIVATED,
    ACTIVATED,
    BLOCKING,
    CANCEL;
}
