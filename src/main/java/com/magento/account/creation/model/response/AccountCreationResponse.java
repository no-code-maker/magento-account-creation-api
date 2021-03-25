package com.magento.account.creation.model.response;

import com.magento.account.creation.model.Account;

import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description AccountCreationAbstractResponse model class is a successful response for account creation request.
 */
public class AccountCreationResponse  extends AccountCreationAbstractResponse<Account> implements Serializable {

    public AccountCreationResponse(Account account) {
        super(account);
    }
}
