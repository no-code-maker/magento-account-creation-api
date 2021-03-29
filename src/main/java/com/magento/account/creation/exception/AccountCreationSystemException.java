package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;
import java.io.Serializable;

@Data
public class AccountCreationSystemException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -991589395872686884L;

    private ErrorResponse errorResponse;

    private String errorMessage;

    public AccountCreationSystemException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AccountCreationSystemException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}