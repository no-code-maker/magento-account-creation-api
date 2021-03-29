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
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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

    CloseableHttpClient httpClient;

    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest) {

        String formKey = getAccountCreationSession(AccountCreationConstants.SERVICE_GET_URL);

        createAccountPost(AccountCreationConstants.SERVICE_POST_URL, formKey, accountCreationRequest);

        return AccountCreationUtil.createAccountCreationResponseObject(accountCreationRequest);
    }

    private String getAccountCreationSession(String acctCreationGetURL) {

        int count = 0;

        String formKey = null;

        while (true) {

            try (CloseableHttpResponse response = createHttpClient().execute(
                    new HttpGet(acctCreationGetURL))) {
                String responseString = new BasicResponseHandler().handleResponse(response);

                if (StringUtils.isNotBlank(responseString)) {
                    formKey = AccountCreationUtil.findFormKey(responseString);
                } else {
                    new AccountCreationRetryableException();
                }
                return formKey;
            } catch (AccountCreationRetryableException acrEx) {
                if (++count == AccountCreationConstants.ACCT_CREATION_GET_MAX_RETRIES) throw acrEx;
            } catch (Exception ex) {
                throw new AccountCreationSystemException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION +
                                ex.getCause()));
            }
        }
    }

    private void createAccountPost(String url, String formKey,
                                   AccountCreationRequest accountCreationRequest) {
        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(AccountCreationUtil.generateFormEntity(formKey, accountCreationRequest));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
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
                if (this.httpClient != null) {
                    this.httpClient.close();
                }
            } catch (IOException ex) {
                throw new AccountCreationSystemException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION +
                                ex.getCause()));
            }

        }
    }

    public CloseableHttpClient createHttpClient() {

        final BasicCookieStore cookieStore = new BasicCookieStore();
        this.httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy())
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setMaxRedirects(100).build())
                .setDefaultCookieStore(cookieStore)
                .setRetryHandler(new StandardHttpRequestRetryHandler())
                .build();

        return this.httpClient;

    }

}
