package com.magenta.account.creation.dao.impl;

import com.magento.account.creation.model.request.AccountCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test Methods for {@link com.magento.account.creation.dao.impl.AccountCreationDaoImpl}
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AccountCreationDaoImplTest {

    @Before
    public void setup(){}

    @After
    public void tearDown(){}


    @Test
    public void testCreateAccount_Successful(){
        log.info(">> testCreateAccount()");

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setFirstName("Tom");
        accountCreationRequest.setMiddleName("Sam");
        accountCreationRequest.setLastName("Harry");
        //accountCreationRequest.



    }



}
