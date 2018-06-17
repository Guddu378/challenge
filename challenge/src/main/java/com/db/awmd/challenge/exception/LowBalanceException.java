package com.db.awmd.challenge.exception;

public class LowBalanceException extends AmountTransferException {

    public LowBalanceException(String message) {
        super(message);
    }
}
