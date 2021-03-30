package com.magenta.account.creation.service.impl;

import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.service.impl.AccountCreationServiceImpl;
import com.magento.account.creation.util.AccountCreationUtil;
import com.magento.account.creation.validation.AccountCreationValidationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Test Methods for {@link com.magento.account.creation.service.impl.AccountCreationServiceImpl}
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AccountCreationServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private AccountCreationServiceImpl accountCreationService;

    @Mock
    private AccountCreationDao accountCreationDao;

    @Mock
    private AccountCreationValidationService accountCreationValidationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        accountCreationService = new AccountCreationServiceImpl(accountCreationDao, accountCreationValidationService);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateAccount_Successful() {
        log.info(">> testCreateAccount_Successful()");

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        String formKey = "999999";

        AccountCreationResponse resultResponse = AccountCreationUtil
                .createAccountCreationResponseObject(accountCreationRequest);

        when(accountCreationValidationService.validate(accountCreationRequest)).thenReturn(accountCreationRequest);

        when(accountCreationDao.getAccountCreationSessionFormKey(any(CloseableHttpClient.class),
                any(BasicResponseHandler.class))).thenReturn(formKey);

        when(accountCreationDao.createAccountPost(any(CloseableHttpClient.class),
                any(String.class), any(AccountCreationRequest.class))).thenReturn(resultResponse);

        AccountCreationResponse accountCreationResponse = accountCreationService.createAccount(accountCreationRequest);

        Assert.assertEquals(accountCreationResponse.getResult().getFirstName(), accountCreationRequest.getFirstName());
        Assert.assertEquals(accountCreationResponse.getResult().getMiddleName(), accountCreationRequest.getMiddleName());
        Assert.assertEquals(accountCreationResponse.getResult().getLastName(), accountCreationRequest.getLastName());
        Assert.assertEquals(accountCreationResponse.getResult().getEmailAddress(), accountCreationRequest.getEmailAddress());
        Assert.assertEquals(accountCreationResponse.getResult().isSubscribed(), accountCreationRequest.isSubscribed());

        log.info("<< testCreateAccount_Successful()");

    }
}
