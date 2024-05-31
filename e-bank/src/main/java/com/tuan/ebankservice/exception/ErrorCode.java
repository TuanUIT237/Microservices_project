package com.tuan.ebankservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(8888, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1500, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_NOT_EXISTED(1501, "Account not existed", HttpStatus.NOT_FOUND),
    ACCOUNT_NAME_INVALID(1502, "Account name at least {min} character", HttpStatus.BAD_REQUEST),
    ACCOUNT_BALANCE_INVALID(1503, "Account balance must be over {min}", HttpStatus.BAD_REQUEST),
    AMOUNT_LOAN_INVALID(
            1504, "Amount must be equal or greater than monthly installment amount", HttpStatus.BAD_REQUEST),
    LOAN_PAID_OFF(1505, "Loan has been loan off", HttpStatus.BAD_REQUEST),
    LATE_LOAN(1506, "Loan was lated", HttpStatus.BAD_REQUEST),
    DUE_DATE(1507, "Today is the due date", HttpStatus.BAD_REQUEST),
    CVV_INVALID(1508, "Your CVV information is not valid", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_EXPIRED(1509, "Credit card has expired", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_LIMITED(1510, "Credit card limit has been reached", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_NOT_EXISTED(1511, "Credit card not existed", HttpStatus.NOT_FOUND),
    MIN_DAY_OF_CUTOFF_INVALID(
            1512, "The minimum value of the date must be equal or greater than {value}", HttpStatus.BAD_REQUEST),
    MAX_DAY_OF_CUTOFF_INVALID(
            1513, "The maximum value of the date must be less or equal than {value}", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_LIMIT_INVALID(1514, "Credit card limit must be over {min}", HttpStatus.BAD_REQUEST),
    SOURCE_ACCOUNT_NUMBER_NOT_EXISTED(1515, "Source account number not existed", HttpStatus.BAD_REQUEST),
    DESTINATION_ACCOUNT_NUMBER_NOT_EXISTED(1516, "Destination account number not existed", HttpStatus.BAD_REQUEST),
    SOURCE_ACCOUNT_NOT_ENOUGH(1517, "Source account not enough to transfer", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ENOUGH(1518, "Account not enough to debit", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_PAYMENT_NOT_EXISTED(1519, "Credit card payment not existed", HttpStatus.BAD_REQUEST),
    AMOUNT_INVALID(1520, "Amount must be greater than {min}", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_CANCELLED(1521, "Credit card was cancelled", HttpStatus.BAD_REQUEST),
    AVAILABLE_LIMIT_LESS_AMOUNT(1522, "Available limit must be greater than amount", HttpStatus.BAD_REQUEST),
    CREDIT_PAYMENT_ID_INVALID(1523, "Credit card payment id cannot be blank", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_ID_INVALID(1524, "Credit card id cannot be blank", HttpStatus.BAD_REQUEST),
    CVV_NOT_NULL(1525, "Cvv cannot be blank", HttpStatus.BAD_REQUEST),
    BETWEEN_START_AND_END_DATE_INVALID(
            1526, "The distance between the start and end dates is less than 30", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_LIMIT_NOTNULL(1527, "Credit card limit cannot be blank", HttpStatus.BAD_REQUEST),
    INSTALLMENT_COUNT_MIN_INVALID(1528, "Installment count limit must be greater than {value}", HttpStatus.BAD_REQUEST),
    INSTALLMENT_COUNT_MAX_INVALID(1529, "Installment count limit must be less than {value}", HttpStatus.BAD_REQUEST),
    PRINCIPAL_LOAN_AMOUNT_NOT_NULL(1530, "Principal loan amount cannot be left blank", HttpStatus.BAD_REQUEST),
    PRINCIPAL_LOAN_AMOUNT_INVALID(1531, "Principal loan amount must be greater than {min}", HttpStatus.BAD_REQUEST),
    LOAN_ID_NOT_NULL(1532, "Loan id cannot be left blank", HttpStatus.BAD_REQUEST),
    CREDIT_CARD_ID_NOT_NULL(1533, "Credit card id cannot be left blank", HttpStatus.BAD_REQUEST),
    START_DATE_NOT_NULL(1534, "Start date cannot be left blank", HttpStatus.BAD_REQUEST),
    END_DATE_NOT_NULL(1535, "End date cannot be left blank", HttpStatus.BAD_REQUEST),
    SOURCE_ACCOUNT_NUMBER_NOT_NULL(1536, "Source account number cannot be left blank", HttpStatus.BAD_REQUEST),
    DESTINATION_ACCOUNT_NUMBER_NOT_NULL(
            1537, "Destination account number cannot be left blank", HttpStatus.BAD_REQUEST),
    ACCOUNT_NUMBER_NOT_NULL(1538, "Account number cannot be left blank", HttpStatus.BAD_REQUEST),
    AMOUNT_NOT_NULL(1539, "Amount cannot be null", HttpStatus.BAD_REQUEST),
    SOURCE_AND_DESTINATION_ARE_SIMILAR(
            1540, "The source account and the destination account cannot be the same", HttpStatus.BAD_REQUEST),
    ACCOUNT_EXISTED(1541, "Account existed", HttpStatus.BAD_REQUEST),
    NAME_ACCOUNT_NOT_BLANK(1542, "Name account cannot be left blank", HttpStatus.BAD_REQUEST),
    STATUS_ACCOUNT_NOT_BLANK(1543, "Status account cannot be left blank", HttpStatus.BAD_REQUEST),
    CANCEL_DATE_ACCOUNT_NOT_BLANK(1544, "Cancel date account cannot be left blank", HttpStatus.BAD_REQUEST),
    ACCOUNT_CANCELED(1545, "Account was canceled", HttpStatus.BAD_REQUEST),
    LOAN_NOT_EXISTED(1546, "Loan not existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    AMOUNT_LOAN_OFF_INVALID(
            1547, "Amount must be equal or greater than remaining principal", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
