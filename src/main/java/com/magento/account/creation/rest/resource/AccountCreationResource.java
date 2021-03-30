package com.magento.account.creation.rest.resource;

import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.exception.AccountCreationSystemException;
import com.magento.account.creation.exception.RequestValidationException;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationErrorResponse;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.service.AccountCreationService;
import com.magento.account.creation.util.AccountCreationUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
public class AccountCreationResource {

    private AccountCreationService accountCreationService;

    @Autowired
    public AccountCreationResource(AccountCreationService accountCreationService) {
        this.accountCreationService = accountCreationService;

    }

    @PostMapping("/magento/account/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Create Account", notes = "Magento Account Creation", response = AccountCreationResponse.class)
    public ResponseEntity<String> createAccount(
            @HeaderParam(value = AccountCreationConstants.CORRELATION_ID) String correlationId,
            @Valid @Validated @RequestBody AccountCreationRequest accountCreationRequest,
            @Context HttpServletResponse response) {

        AccountCreationErrorResponse accountCreationErrorResponse = null;
        AccountCreationResponse accountCreationResponse = null;

        ResponseEntity<String> responseEntity;
        String responseJsonString = null;


        try {
            log.info(accountCreationRequest.toString());
            accountCreationResponse = this.accountCreationService.createAccount(accountCreationRequest);

        } catch (RequestValidationException ex) {
            if (ex.getErrorResponse() != null) {
                accountCreationErrorResponse = new AccountCreationErrorResponse();
                accountCreationErrorResponse.setErrorResponse(ex.getErrorResponse());
                accountCreationErrorResponse.setStatusDescription(AccountCreationConstants.REQUEST_FAILED);
            } else {
                accountCreationErrorResponse = AccountCreationUtil.getEmptyValidationResponse();
            }
            response.setStatus(accountCreationErrorResponse.getErrorResponse().getStatus());

        } catch (AccountCreationSystemException ex) {
            if (ex.getErrorResponse() != null) {
                accountCreationErrorResponse = new AccountCreationErrorResponse();
                accountCreationErrorResponse.setErrorResponse(ex.getErrorResponse());
                accountCreationErrorResponse.setStatusDescription(AccountCreationConstants.REQUEST_FAILED);
            } else {
                accountCreationErrorResponse = AccountCreationUtil.getEmptySystemValidationResponse();
            }
            response.setStatus(accountCreationErrorResponse.getErrorResponse().getStatus());

        } finally {
            if (accountCreationErrorResponse != null) {
                responseJsonString = AccountCreationUtil.getJsonStringErrorResponse(accountCreationErrorResponse);
            } else {
                response.setStatus(HttpStatus.SC_CREATED);
                if (accountCreationResponse != null) {
                    accountCreationResponse.setStatusDescription(AccountCreationConstants.REQUEST_SUCCESS);
                    responseJsonString = AccountCreationUtil.getJsonStringResponse(accountCreationResponse);
                }
            }
            responseEntity = ResponseEntity.status(response.getStatus())
                    .body(responseJsonString);
        }
        log.info("{}", responseJsonString);
        return responseEntity;
    }
}