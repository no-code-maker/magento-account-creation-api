package com.magenta.account.creation.dao.impl;

import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.dao.impl.AccountCreationDaoImpl;
import com.magento.account.creation.exception.AccountCreationRetryableException;
import com.magento.account.creation.exception.AccountCreationSystemException;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Test Methods for {@link com.magento.account.creation.dao.impl.AccountCreationDaoImpl}
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AccountCreationDaoImplTest {

    @Mock
    private static CloseableHttpClient closeableHttpClient;
    @Mock
    private static CloseableHttpResponse closeableHttpResponse;
    @Mock
    private static StatusLine statusLine;
    @InjectMocks
    private final AccountCreationDao accountCreationDao = new AccountCreationDaoImpl();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private BasicResponseHandler basicResponseHandler;

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAccountCreationSessionFormKey_Successful() throws Exception {
        log.info(">> testGetAccountCreationSessionFormKey_Successful()");

        String serviceGetUrl = "http://107.23.133.112/customer/account/create/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("serviceGetUrl"), serviceGetUrl);

        when(closeableHttpClient.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponse);
        when(basicResponseHandler.handleResponse(closeableHttpResponse)).thenReturn("<input type=\"hidden\" name=\"form_key\" value=\"99999999\" />");
        Assert.assertEquals("99999999", accountCreationDao.getAccountCreationSessionFormKey(closeableHttpClient, basicResponseHandler));

        log.info("<< testGetAccountCreationSessionFormKey_Successful()");
    }

    @Test
    public void testGetAccountCreationSessionFormKey_RetryableCase() throws Exception {
        log.info(">> testGetAccountCreationSessionFormKey_RetryableCase()");

        expectedException.expect(AccountCreationRetryableException.class);
        String serviceGetUrl = "http://107.23.133.112/customer/account/create/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("serviceGetUrl"), serviceGetUrl);

        when(closeableHttpClient.execute(Mockito.any(HttpGet.class))).thenReturn(closeableHttpResponse);
        when(basicResponseHandler.handleResponse(closeableHttpResponse)).thenReturn(null);
        accountCreationDao.getAccountCreationSessionFormKey(closeableHttpClient, basicResponseHandler);

        log.info("<< testGetAccountCreationSessionFormKey_RetryableCase()");
    }

    @Test
    public void testGetAccountCreationSessionFormKey_Exception() throws Exception {
        log.info(">> testGetAccountCreationSessionFormKey_Exception()");

        expectedException.expect(AccountCreationSystemException.class);
        String serviceGetUrl = "http://107.23.133.112/customer/account/create/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("serviceGetUrl"), serviceGetUrl);

        when(closeableHttpClient.execute(Mockito.any(HttpGet.class))).thenThrow(new AccountCreationSystemException("Exception Occurred"));
        accountCreationDao.getAccountCreationSessionFormKey(closeableHttpClient, basicResponseHandler);
        log.info("<< testGetAccountCreationSessionFormKey_Exception()");
    }

    @Test
    public void testCreateAccountPost_Successful() throws Exception {
        log.info(">> testCreateAccountPost_Successful()");

        String servicePostUrl = "http://107.23.133.112/customer/account/createpost/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("servicePostUrl"), servicePostUrl);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        when(closeableHttpClient.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponse);
        when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);

        AccountCreationResponse accountCreationResponse = accountCreationDao
                .createAccountPost(closeableHttpClient, "9999999", accountCreationRequest);

        Assert.assertEquals(accountCreationResponse.getAccount().getFirstName(), accountCreationRequest.getFirstName());
        Assert.assertEquals(accountCreationResponse.getAccount().getMiddleName(), accountCreationRequest.getMiddleName());
        Assert.assertEquals(accountCreationResponse.getAccount().getLastName(), accountCreationRequest.getLastName());
        Assert.assertEquals(accountCreationResponse.getAccount().getEmailAddress(), accountCreationRequest.getEmailAddress());
        Assert.assertEquals(accountCreationResponse.getAccount().isSubscribed(), accountCreationRequest.isSubscribed());

        log.info("<< testCreateAccountPost_Successful()");
    }

    @Test
    public void testCreateAccountPost_NonSuccessful_ResponseCode() throws Exception {
        log.info(">> testCreateAccountPost_NonSuccessful_ResponseCode()");

        String servicePostUrl = "http://107.23.133.112/customer/account/createpost/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("servicePostUrl"), servicePostUrl);

        expectedException.expect(AccountCreationSystemException.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        when(closeableHttpClient.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponse);

        accountCreationDao.createAccountPost(closeableHttpClient, "9999999", accountCreationRequest);
        log.info("<< testCreateAccountPost_NonSuccessful_ResponseCode()");
    }


    @Test
    public void testCreateAccountPost_ExceptionOccurred() throws Exception {
        log.info(">> testCreateAccountPost_ExceptionOccurred()");

        String servicePostUrl = "http://107.23.133.112/customer/account/createpost/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("servicePostUrl"), servicePostUrl);

        expectedException.expect(AccountCreationSystemException.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        when(closeableHttpClient.execute(Mockito.any(HttpPost.class))).thenReturn(null);

        accountCreationDao.createAccountPost(closeableHttpClient, "9999999", accountCreationRequest);
        log.info("<< testCreateAccountPost_ExceptionOccurred()");
    }

    @Test
    public void testCreateAccountPost_ReturnStatusNonOK() throws Exception {
        log.info(">> testCreateAccountPost_ExceptionOccurred()");

        String servicePostUrl = "http://107.23.133.112/customer/account/createpost/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("servicePostUrl"), servicePostUrl);

        expectedException.expect(AccountCreationSystemException.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        when(closeableHttpClient.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponse);
        when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(404);

        accountCreationDao.createAccountPost(closeableHttpClient, "9999999", accountCreationRequest);
        log.info("<< testCreateAccountPost_ExceptionOccurred()");
    }

    @Test
    public void testCreateAccountPost_HttpClientCloseableException() throws Exception {
        log.info(">> testCreateAccountPost_ExceptionOccurred()");

        String servicePostUrl = "http://107.23.133.112/customer/account/createpost/";

        FieldSetter.setField(accountCreationDao,
                AccountCreationDaoImpl.class.getDeclaredField("servicePostUrl"), servicePostUrl);

        expectedException.expect(AccountCreationSystemException.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        when(closeableHttpClient.execute(Mockito.any(HttpPost.class))).thenReturn(closeableHttpResponse);
        when(closeableHttpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(404);

        accountCreationDao.createAccountPost(closeableHttpClient, "9999999", accountCreationRequest);
        log.info("<< testCreateAccountPost_ExceptionOccurred()");
    }
}
