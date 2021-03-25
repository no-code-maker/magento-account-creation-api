package com.magento.account.creation.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description AccountCreationAbstractResponse model class is a generic response for account creation request.
 */
@Data
public class AccountCreationAbstractResponse<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8260390572730361655L;

    private String statusDescription;

    private T result;

    public  AccountCreationAbstractResponse( T result) {
        this.result = result;
    }
}
