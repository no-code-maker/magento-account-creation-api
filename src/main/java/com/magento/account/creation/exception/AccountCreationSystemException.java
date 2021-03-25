package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;

import java.io.Serial;
import java.io.Serializable;

public class AccountCreationSystemException extends RuntimeException implements Serializable {


    @Serial
    private static final long serialVersionUID = -991589395872686884L;

    private ErrorResponse errorResponse;

    public AccountCreationSystemException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}