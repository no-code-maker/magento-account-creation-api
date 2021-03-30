package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description Exception for retrying.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountCreationRetryableException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -991589395872686884L;

    private final ErrorResponse errorResponse;

    private final String errorMessage;

    public AccountCreationRetryableException(final String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorResponse = null;
    }

    public AccountCreationRetryableException(final ErrorResponse errorResponse) {
        this.errorMessage = null;
        this.errorResponse = errorResponse;
    }

}