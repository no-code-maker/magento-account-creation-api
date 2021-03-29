package com.magento.account.creation.dao;

import com.magento.account.creation.model.request.AccountCreationRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @author Rajeev Krishna
 * @description Interface for magento account creation api.
 */
public interface AccountCreationDao {

    String getAccountCreationSessionFormKey(CloseableHttpClient closeableHttpClient, BasicResponseHandler basicResponseHandler);

    void createAccountPost(CloseableHttpClient closeableHttpClient, String formKey, AccountCreationRequest accountCreationRequest);
}
