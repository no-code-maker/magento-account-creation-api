package com.magento.account.creation.dao.impl;

import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.request.AccountCreationRequest;
import com.magento.account.creation.response.AccountCreationResponse;
import org.springframework.stereotype.Service;


/**
 * @author Rajeev Krishna
 * @description Implementation layer for magento account creation api.
 */
@Service
public class AccountCreationDaoImpl implements AccountCreationDao {

    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest) {
        return null;
    }
}
