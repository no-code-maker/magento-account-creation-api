package com.magenta.account.creation.rest.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.exception.AccountCreationSystemException;
import com.magento.account.creation.exception.RequestValidationException;
import com.magento.account.creation.model.Account;
import com.magento.account.creation.model.error.ErrorResponse;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import com.magento.account.creation.rest.resource.AccountCreationResource;
import com.magento.account.creation.service.AccountCreationService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test Methods for {@link com.magento.account.creation.rest.resource.AccountCreationResource}
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AccountCreationResourceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private AccountCreationService accountCreationService;
    @InjectMocks
    private AccountCreationResource accountCreationResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateAccount_Successful() throws JsonProcessingException {
        log.info(">> testCreateAccount_Successful()");

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        Account account = Account.builder().firstName("Tom").middleName("C")
                .lastName("Lord").emailAddress("tom.lord@tomlord.com").subscribed(true).build();

        AccountCreationResponse accountCreationResponse = new AccountCreationResponse();
        accountCreationResponse.setAccount(account);

        when(accountCreationService.createAccount(accountCreationRequest)).thenReturn(accountCreationResponse);
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.OK.value());

        ResponseEntity<String> responseEntity = accountCreationResource.createAccount(
                "9999999999", accountCreationRequest, httpServletResponse);

        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

        log.info("<< testCreateAccount_Successful()");

    }

    @Test
    public void testCreateAccount_FailedValidation_EmptyValidationResponse() {

        log.info(">> testCreateAccount_FailedValidation_EmptyValidationResponse()");

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        when(accountCreationService.createAccount(accountCreationRequest)).thenThrow(new RequestValidationException(
                AccountCreationConstants.ERR_CODE_VALIDATION));
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.BAD_REQUEST.value());
        ResponseEntity<String> responseEntity =
                accountCreationResource.createAccount("9999999999", accountCreationRequest, httpServletResponse);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        log.info("<<testCreateAccount_FailedValidation_EmptyValidationResponse()");
    }

    @Test
    public void testCreateAccount_FailedValidation_Response() {

        log.info(">> testCreateAccount_FailedValidation_Response()");

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();

        when(accountCreationService.createAccount(accountCreationRequest)).thenThrow(
                new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION)));
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.BAD_REQUEST.value());
        ResponseEntity<String> responseEntity =
                accountCreationResource.createAccount("9999999999", accountCreationRequest, httpServletResponse);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        log.info("<< testCreateAccount_FailedValidation_Response()");
    }

    @Test
    public void testCreateAccount_SystemException_Response() {

        log.info(">> testCreateAccount_SystemException_Response()");

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();

        when(accountCreationService.createAccount(accountCreationRequest)).thenThrow(
                new AccountCreationSystemException(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        AccountCreationConstants.ERR_CODE_SYSTEM_EXCEPTION)));

        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity<String> responseEntity =
                accountCreationResource.createAccount("9999999999", accountCreationRequest, httpServletResponse);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        log.info("<< testCreateAccount_SystemException_Response()");
    }

    @Test
    public void testCreateAccount_SystemException_EmptyResponse() {

        log.info(">> testCreateAccount_SystemException_EmptyResponse()");

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();

        when(accountCreationService.createAccount(accountCreationRequest)).thenThrow(AccountCreationSystemException.class);
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity<String> responseEntity =
                accountCreationResource.createAccount("9999999999", accountCreationRequest, httpServletResponse);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        log.info("<< testCreateAccount_SystemException_EmptyResponse()");
    }
}
