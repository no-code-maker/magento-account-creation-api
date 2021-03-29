package com.magento.account.creation.dao;

import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;

/**
 * @author Rajeev Krishna
 * @description Interface for magento account creation api.
 */
public interface AccountCreationDao {

    String getAccountCreationSessionFormKey();

    void createAccountPost(String formKey, AccountCreationRequest accountCreationRequest);
}
