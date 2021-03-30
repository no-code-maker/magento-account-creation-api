package com.magento.account.creation.dao.impl;

import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.exception.AccountCreationRetryableException;
import com.magento.account.creation.exception.AccountCreationSystemException;
import com.magento.account.creation.model.error.ErrorResponse;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.util.AccountCreationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author Rajeev Krishna
 * @description Implementation layer for magento account creation api.
 */
@Service
@Slf4j
public class AccountCreationDaoImpl implements AccountCreationDao {

    @Value("${SERVICE_GET_URL}")
    private String serviceGetUrl;

    @Value("${SERVICE_POST_URL}")
    private String servicePostUrl;

    public String getAccountCreationSessionFormKey(CloseableHttpClient closeableHttpClient,
                                                   BasicResponseHandler basicResponseHandler) {

        int count = 0;
        String formKey;

        while (true) {
            try (CloseableHttpResponse response = closeableHttpClient.execute(
                    new HttpGet(this.serviceGetUrl))) {
                String responseString = basicResponseHandler.handleResponse(response);

                if (StringUtils.isNotBlank(responseString)) {
                    formKey = AccountCreationUtil.findFormKey(responseString);
                } else {
                    throw new AccountCreationRetryableException();
                }
                return formKey;
            } catch (AccountCreationRetryableException acrEx) {
                if (++count == AccountCreationConstants.ACCT_CREATION_GET_MAX_RETRIES) {
                    throw new AccountCreationRetryableException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                            AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION +
                                    acrEx.getCause()));
                } else {
                    continue;
                }
            } catch (Exception ex) {
                throw new AccountCreationSystemException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION +
                                ex.getCause()));
            }
        }
    }

    public AccountCreationResponse createAccountPost(CloseableHttpClient closeableHttpClient, String formKey,
                                                     AccountCreationRequest accountCreationRequest) {
        try {

            HttpPost httpPost = new HttpPost(this.servicePostUrl);
            httpPost.setEntity(AccountCreationUtil.generateFormEntity(formKey, accountCreationRequest));

            try (CloseableHttpResponse response = closeableHttpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new AccountCreationSystemException(
                            AccountCreationConstants.ERR_DOWNSTREAM_FAIL_MESSAGE);
                }
            } catch (Exception ex) {
                throw new AccountCreationSystemException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION +
                                ex.getCause()));
            }

        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException ex) {
                throw new AccountCreationSystemException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION +
                                ex.getCause()));
            }

        }

        AccountCreationResponse accountCreationResponse = AccountCreationUtil
                .createAccountCreationResponseObject(accountCreationRequest);
        return accountCreationResponse;
    }

}
