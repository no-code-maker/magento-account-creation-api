package com.magento.account.creation.dao;

import com.magento.account.creation.request.AccountCreationRequest;

/**
 * @author Rajeev Krishna
 * @description Validation Interface for Magento Account Creation Request.
 */
public interface AccountCreationValidationDao {

    void validate(AccountCreationRequest accountCreationRequest);
}
