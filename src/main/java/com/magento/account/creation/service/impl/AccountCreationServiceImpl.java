package com.magento.account.creation.service.impl;

import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.service.AccountCreationService;
import com.magento.account.creation.validation.AccountCreationValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationServiceImpl implements AccountCreationService {

    private AccountCreationDao accountCreationDao;

    private AccountCreationValidationService accountCreationValidationService;

    @Autowired
    AccountCreationServiceImpl(AccountCreationDao accountCreationDao,
                               AccountCreationValidationService accountCreationValidationService){
        this.accountCreationDao = accountCreationDao;
        this.accountCreationValidationService = accountCreationValidationService;
    }

    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest) {

        this.accountCreationValidationService.validate(accountCreationRequest);

        AccountCreationResponse accountCreationResponse = this.accountCreationDao.createAccount(accountCreationRequest);

        return accountCreationResponse;
    }
}
