package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountCreationSystemException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -991589395872686884L;

    private final ErrorResponse errorResponse;

    private final String errorMessage;

    public AccountCreationSystemException(final String errorMessage) {

        this.errorMessage = errorMessage;
        this.errorResponse = null;
    }

    public AccountCreationSystemException(final ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        this.errorMessage = null;
    }
}