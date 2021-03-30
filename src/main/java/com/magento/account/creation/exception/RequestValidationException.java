package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestValidationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7328684733459995628L;

    private ErrorResponse errorResponse;

    private String errorMessage;

    public RequestValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RequestValidationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
