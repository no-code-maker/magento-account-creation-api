package com.magento.account.creation.exception;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class RequestValidationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7328684733459995628L;

    private ErrorResponse errorResponse;


    public RequestValidationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
