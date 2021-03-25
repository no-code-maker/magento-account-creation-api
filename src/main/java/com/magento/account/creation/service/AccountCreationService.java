package com.magento.account.creation.service;

import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;

/**
 * @author Rajeev Krishna
 * @description Service level interface for magento account creation api.
 */
public interface AccountCreationService {

    AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest);
}
