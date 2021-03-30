package com.magenta.account.creation.validation.impl;

import com.magento.account.creation.exception.RequestValidationException;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.validation.impl.AccountCreationValidationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test Methods for {@link com.magento.account.creation.validation.impl.AccountCreationValidationServiceImpl}
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AccountCreationValidationServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private AccountCreationValidationServiceImpl accountCreationValidationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testValidate_Successful() {
        log.info(">> testValidate_Successful()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("C");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        AccountCreationRequest validateRequest = accountCreationValidationService.validate(accountCreationRequest);

        Assert.assertEquals(validateRequest.getFirstName(), accountCreationRequest.getFirstName());
        Assert.assertEquals(validateRequest.getMiddleName(), accountCreationRequest.getMiddleName());
        Assert.assertEquals(validateRequest.getLastName(), accountCreationRequest.getLastName());
        Assert.assertEquals(validateRequest.getEmailAddress(), accountCreationRequest.getEmailAddress());
        Assert.assertEquals(validateRequest.getPassword(), accountCreationRequest.getPassword());
        Assert.assertEquals(validateRequest.getConfirmPassword(), accountCreationRequest.getConfirmPassword());
        Assert.assertEquals(validateRequest.isSubscribed(), accountCreationRequest.isSubscribed());

        log.info("<< testValidate_Successful()");
    }

    @Test
    public void testValidateMissingMiddleName_Successful() {
        log.info(">> testValidateMissingMiddleName_Successful()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        AccountCreationRequest validateRequest = accountCreationValidationService.validate(accountCreationRequest);

        Assert.assertEquals(validateRequest.getFirstName(), accountCreationRequest.getFirstName());
        Assert.assertEquals("", validateRequest.getMiddleName());
        Assert.assertEquals(validateRequest.getLastName(), accountCreationRequest.getLastName());
        Assert.assertEquals(validateRequest.getEmailAddress(), accountCreationRequest.getEmailAddress());
        Assert.assertEquals(validateRequest.getPassword(), accountCreationRequest.getPassword());
        Assert.assertEquals(validateRequest.getConfirmPassword(), accountCreationRequest.getConfirmPassword());
        Assert.assertEquals(validateRequest.isSubscribed(), accountCreationRequest.isSubscribed());

        log.info("<< testValidateMissingMiddleName_Successful()");
    }

    @Test
    public void testValidationFailed_AccountCreationObjectNull() {
        log.info(">> testValidationFailed_AccountCreationObjectNull()");

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(null);
    }

    @Test
    public void testValidationFailed_FirstNameFieldNull() {
        log.info(">> testValidationFailed_FirstNameFieldNull()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName(null);
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_InvalidFirstNameField() {
        log.info(">> testValidationFailed_FirstNameFieldNull()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom1");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_LastNameFieldNull() {
        log.info(">> testValidationFailed_LastNameFieldNull()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName(null);
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_InvalidLastNameField() {
        log.info(">> testValidationFailed_InvalidLastNameField()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord1");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_InvalidMiddleNameField() {
        log.info(">> testValidationFailed_InvalidMiddleNameField()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("S2");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_MissingEmail() {
        log.info(">> testValidationFailed_MissingEmail()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Dan");
        accountCreationRequest.setEmailAddress(null);
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_MissingPassword() {
        log.info(">> testValidationFailed_MissingPassword()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword(null);
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_MissingConfirmPassword() {
        log.info(">> testValidationFailed_MissingConfirmPassword()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword(null);
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_PasswordLessThanSixChars() {
        log.info(">> testValidationFailed_PasswordLessThanSixChars()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("12345");
        accountCreationRequest.setConfirmPassword("12345");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_ConfirmPasswordLessThanSixChars() {
        log.info(">> testValidationFailed_PasswordLessThanSixChars()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("12345");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_PasswordMisMatch() {
        log.info(">> testValidationFailed_PasswordLessThanSixChars()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("1234567");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_NameLengthGreaterThanMaxLen() {
        log.info(">> testValidationFailed_PasswordLessThanSixChars()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tomdsfmsdkdnvkdnvklmdkfvndkvdkvfsdmflsfmsdfmdlvmdlvmdlmvldm" +
                "vldmvdnslknlkvndklvlvkclkdkvndkvndknvkjdvkvkd");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_LastNameLengthGreaterThanMaxLen() {
        log.info(">> testValidationFailed_LastNameLengthGreaterThanMaxLen()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lorddsfmsdkdnvkdnvklmdkfvndkvdkvfsdmflsfmsdfmdlvmdlvmdlmvldm"
                + "vldmvdnslknlkvndklvlvkclkdkvndkvndknvkjdvkvkd");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_MiddleNameLengthGreaterThanMaxLen() {
        log.info(">> testValidationFailed_MiddleNameLengthGreaterThanMaxLen()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("Lorddsfmsdkdnvkdnvklmdkfvndkvdkvfsdmflsfmsdfmdlvmdlvmdlmvldm"
                + "vldmvdnslknlkvndklvlvkclkdkvndkvndknvkjdvkvkd");
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_EmailLengthGreaterThanMaxLen() {
        log.info(">> testValidationFailed_EmailLengthGreaterThanMaxLen()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("Lorddsfmsdkdnvkdnvklmdkfvndkvdkvfsdmflsfmsdfmdlvmdlvmdlmvldm" +
                "vldmvdnslknlkvndklvlvkclkdkvndkvndknvkjdvkvkd.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_ConfirmPasswordLengthGreaterThanMaxLen() {
        log.info(">> testValidationFailed_PasswordLengthGreaterThanMaxLen()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("Tom.lord@tomlord.com");
        accountCreationRequest.setPassword("123456");
        accountCreationRequest.setConfirmPassword("Lorddsfmsdkdnvkdnvklmdkfvndkvdkvfsdmflsfmsdfmdlvmdlvmdlmvldm"
                + "vldmvdnslknlkvndklvlvkclkdkvndkvndknvkjdvkvkd");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }

    @Test
    public void testValidationFailed_PasswordLengthGreaterThanMaxLen() {
        log.info(">> testValidationFailed_PasswordLengthGreaterThanMaxLen()");
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName(null);
        accountCreationRequest.setLastName("Lord");
        accountCreationRequest.setEmailAddress("Tom.lord@tomlord.com");
        accountCreationRequest.setPassword("Lorddsfmsdkdnvkdnvklmdkfvndkvdkvfsdmflsfmsdfmdlvmdlvmdlmvldm"
                + "vldmvdnslknlkvndklvlvkclkdkvndkvndknvkjdvkvkd");
        accountCreationRequest.setConfirmPassword("123456");
        accountCreationRequest.setSubscribed(true);

        expectedException.expect(RequestValidationException.class);
        accountCreationValidationService.validate(accountCreationRequest);
    }
}
