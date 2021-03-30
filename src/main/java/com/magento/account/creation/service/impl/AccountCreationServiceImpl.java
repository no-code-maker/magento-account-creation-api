package com.magento.account.creation.service.impl;

import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.service.AccountCreationService;
import com.magento.account.creation.validation.AccountCreationValidationService;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationServiceImpl implements AccountCreationService {

    private AccountCreationDao accountCreationDao;

    private AccountCreationValidationService accountCreationValidationService;

    @Autowired
    public AccountCreationServiceImpl(AccountCreationDao accountCreationDao,
                                      AccountCreationValidationService accountCreationValidationService) {
        this.accountCreationDao = accountCreationDao;
        this.accountCreationValidationService = accountCreationValidationService;
    }

    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest) {

        AccountCreationRequest accountCreationValidatedRequest = this.accountCreationValidationService.validate(accountCreationRequest);

        CloseableHttpClient closeableHttpClient = createHttpClient();

        String formKey = this.accountCreationDao.getAccountCreationSessionFormKey(closeableHttpClient,
                new BasicResponseHandler());

        return this.accountCreationDao
                .createAccountPost(closeableHttpClient, formKey, accountCreationValidatedRequest);
    }

    private CloseableHttpClient createHttpClient() {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        return HttpClientBuilder.create()/*.setRedirectStrategy(new LaxRedirectStrategy())*/
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setMaxRedirects(100).build())
                .setDefaultCookieStore(cookieStore)
                .setRetryHandler(new StandardHttpRequestRetryHandler())
                .build();
    }
}
