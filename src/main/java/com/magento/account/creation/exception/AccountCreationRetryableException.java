package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCreationRetryableException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -991589395872686884L;

    private ErrorResponse errorResponse;

    public AccountCreationRetryableException() {
    }

    public AccountCreationRetryableException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}