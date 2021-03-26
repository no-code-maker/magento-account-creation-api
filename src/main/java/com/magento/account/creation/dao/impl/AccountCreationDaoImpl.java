package com.magento.account.creation.dao.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.dao.AccountCreationDao;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;


/**
 * @author Rajeev Krishna
 * @description Implementation layer for magento account creation api.
 */
@Service
public class AccountCreationDaoImpl implements AccountCreationDao {

    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest) {

        try{
        final WebClient webClient = new WebClient();

        final HtmlPage htmlPage = webClient.getPage(AccountCreationConstants.SERVICE_URL);

        final HtmlForm form = htmlPage.getHtmlElementById("form-validate");

        HtmlTextArea firstName = htmlPage.getHtmlElementById("firstname");

        //final HtmlTextArea firstName = form.getTextAreaByName("firstname");
        firstName.setText(accountCreationRequest.getFirstName());

        final HtmlTextInput middleName = form.getInputByName("middlename");
        final HtmlTextInput lastName = form.getInputByName("lastname");
        final HtmlTextInput email = form.getInputByName("email");
        final HtmlTextInput password = form.getInputByName("password");
        final HtmlTextInput confirmPassword = form.getInputByName("confirmation");
        final HtmlCheckBoxInput selectedSubscription = form.getInputByName("is_subscribed");
        final HtmlButton button = form.getButtonByName("Register");

        firstName.setText(accountCreationRequest.getFirstName());
        lastName.setText(accountCreationRequest.getLastName());
        middleName.setText(accountCreationRequest.getMiddleName());
        email.setText(accountCreationRequest.getEmailAddress());
        password.setText(accountCreationRequest.getPassword());
        confirmPassword.setText(accountCreationRequest.getConfirmPassword());
        selectedSubscription.setChecked(accountCreationRequest.isSubscribed());


        // Now submit the form by clicking the button and get back the second page.
        final HtmlPage page2 = button.click();

        page2.toString();

        } catch (IOException ioEx) {

        }

        return null;
    }
}
