package com.magento.account.creation.rest.resource;

import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.exception.AccountCreationSystemException;
import com.magento.account.creation.exception.RequestValidationException;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationAbstractResponse;
import com.magento.account.creation.model.response.AccountCreationErrorResponse;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.service.AccountCreationService;
import com.magento.account.creation.util.AccountCreationUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@RestController
public class AccountCreationResource {

    private final AccountCreationService accountCreationService;

    @Autowired
    AccountCreationResource(AccountCreationService accountCreationService) {
        this.accountCreationService = accountCreationService;

    }

    @PostMapping("/magento/account/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Create Account", notes = "Magento Account Creation", response = AccountCreationResponse.class)
    public AccountCreationAbstractResponse createAccount(
            @HeaderParam(value = AccountCreationConstants.CORRELATION_ID) String correlationId,
            @Valid @Validated @RequestBody AccountCreationRequest accountCreationRequest,
            @Context HttpServletResponse response) {

        AccountCreationErrorResponse accountCreationErrorResponse = null;

        try {
            this.accountCreationService.createAccount(accountCreationRequest);
        } catch (RequestValidationException ex) {
            if (ex.getErrorResponse() != null) {
                response.setStatus(ex.getErrorResponse().getStatus());
                accountCreationErrorResponse = new AccountCreationErrorResponse(ex.getErrorResponse());
                accountCreationErrorResponse.setStatusDescription("Failed to create account");
            } else {
                accountCreationErrorResponse = new AccountCreationErrorResponse(
                        AccountCreationUtil.getEmptyValidationResponse());
            }

        } catch (AccountCreationSystemException ex) {




        } finally {

        }
        return accountCreationErrorResponse;
    }
}