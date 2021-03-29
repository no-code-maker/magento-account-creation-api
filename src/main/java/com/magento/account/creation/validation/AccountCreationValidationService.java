package com.magento.account.creation.validation;

import com.magento.account.creation.model.request.AccountCreationRequest;

/**
 * @author Rajeev Krishna
 * @description Validation Interface for Magento Account Creation Request.
 */
public interface AccountCreationValidationService {

    AccountCreationRequest validate(AccountCreationRequest accountCreationRequest);
}
