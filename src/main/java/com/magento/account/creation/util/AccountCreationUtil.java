package com.magento.account.creation.util;

import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.model.Account;
import com.magento.account.creation.model.error.ErrorResponse;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationResponse;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.http.HttpStatus;

public class AccountCreationUtil {

    public static ErrorResponse getEmptyValidationResponse() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                "Request Validation Error");

        return errorResponse;

    }

    public static ErrorResponse getEmptySystemValidationResponse() {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "System Error Occurred");

        return errorResponse;

    }

    public static String findFormKey(String htmlBody){

        String ss = htmlBody.split("<input type=\"hidden\" name=\"form_key\" value=\"")[1];

        String formKey = ss.split("\" />" ,2)[0];

        return formKey;

    }

    public static HttpEntity generateFormEntity(String formKey, AccountCreationRequest accountCreationRequest){

        return MultipartEntityBuilder.create()
                .addTextBody(AccountCreationConstants.FIELD_FORM_KEY, formKey)
                .addTextBody(AccountCreationConstants.FIELD_SUCCESS_URL, "")
                .addTextBody(AccountCreationConstants.FIELD_ERROR_URL, "")
                .addTextBody(AccountCreationConstants.FIELD_FIRST_NAME, accountCreationRequest.getFirstName())
                .addTextBody(AccountCreationConstants.FIELD_MIDDLE_NAME, accountCreationRequest.getMiddleName())
                .addTextBody(AccountCreationConstants.FIELD_LAST_NAME, accountCreationRequest.getLastName())
                .addTextBody(AccountCreationConstants.FIELD_EMAIL, accountCreationRequest.getEmailAddress())
                .addTextBody(AccountCreationConstants.FIELD_PASSWORD, accountCreationRequest.getPassword())
                .addTextBody(AccountCreationConstants.FIELD_CONFIRM_PASSWORD, accountCreationRequest.getConfirmPassword())
                .build();
    }

    public static AccountCreationResponse createAccountCreationResponseObject(
            AccountCreationRequest accountCreationRequest){

        Account account  = Account.builder().firstName(accountCreationRequest.getFirstName())
                .middleName(accountCreationRequest.getMiddleName())
                .lastName(accountCreationRequest.getLastName())
                .emailAddress(accountCreationRequest.getEmailAddress())
                .subscribed(accountCreationRequest.isSubscribed()).build();

        return new AccountCreationResponse(account);
    }

}
