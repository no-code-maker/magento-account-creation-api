package com.magento.account.creation.util;

import com.magento.account.creation.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;

public class AccountCreationUtil {

    public static ErrorResponse getEmptyValidationResponse() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                "Request Validation Error");

        return errorResponse;

    }



}
