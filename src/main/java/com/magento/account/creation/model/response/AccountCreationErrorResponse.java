package com.magento.account.creation.model.response;

import com.magento.account.creation.model.Account;
import com.magento.account.creation.model.error.ErrorResponse;

import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description AccountCreationAbstractResponse model class is a successful response for account creation request.
 */
public class AccountCreationErrorResponse extends AccountCreationAbstractResponse<ErrorResponse> implements Serializable {

    public AccountCreationErrorResponse(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
