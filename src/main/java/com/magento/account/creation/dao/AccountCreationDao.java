package com.magento.account.creation.dao;

import com.magento.account.creation.request.AccountCreationRequest;
import com.magento.account.creation.response.AccountCreationResponse;

/**
 * @author Rajeev Krishna
 * @description Interface for magento account creation api.
 */
public interface AccountCreationDao {

    AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest);
}
