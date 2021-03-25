package com.magento.account.creation.resource;


import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.dao.AccountCreationValidationDao;
import com.magento.account.creation.request.AccountCreationRequest;
import com.magento.account.creation.response.AccountCreationResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Singleton
@Path("/magento/account")
public class AccountCreationResource {

    private AccountCreationDao accountCreationDao;

    private AccountCreationValidationDao accountCreationValidationDao;

    @Autowired
    AccountCreationResource(AccountCreationDao accountCreationDao,
                            AccountCreationValidationDao accountCreationValidationDao){
        this.accountCreationDao = accountCreationDao;
        this.accountCreationValidationDao = accountCreationValidationDao;
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Create Account", notes = "Magento Account Creation", response = AccountCreationResponse.class)
    public AccountCreationResponse createAccount(
            @Valid @Validated @RequestBody AccountCreationRequest accountCreationRequest) {
        AccountCreationResponse httpResponse = null;

        try {

            this.accountCreationDao.createAccount(accountCreationRequest);

        } catch (Exception ex){

        } finally {

        }


        return httpResponse;
    }
}