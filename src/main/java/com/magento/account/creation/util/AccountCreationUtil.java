package com.magento.account.creation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.model.Account;
import com.magento.account.creation.model.error.ErrorResponse;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.model.response.AccountCreationErrorResponse;
import com.magento.account.creation.model.response.AccountCreationResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.http.HttpStatus;

/**
 * @author Rajeev Krishna
 * @description Account Creation Util class for magento account creation api.
 */
@Slf4j
public class AccountCreationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private AccountCreationUtil() {
    }

    public static AccountCreationErrorResponse getEmptyValidationResponse() {

        AccountCreationErrorResponse accountCreationErrorResponse = new AccountCreationErrorResponse();
        accountCreationErrorResponse.setErrorResponse(new ErrorResponse(HttpStatus.BAD_REQUEST, "Request Validation Error"));
        return accountCreationErrorResponse;
    }

    public static AccountCreationErrorResponse getEmptySystemValidationResponse() {

        AccountCreationErrorResponse accountCreationErrorResponse = new AccountCreationErrorResponse();
        accountCreationErrorResponse.setErrorResponse(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "System Error Occurred"));

        return accountCreationErrorResponse;
    }

    public static String findFormKey(String htmlBody) {

        String ss = htmlBody.split("<input type=\"hidden\" name=\"form_key\" value=\"")[1];

        return ss.split("\" />", 2)[0];

    }

    public static HttpEntity generateFormEntity(String formKey, AccountCreationRequest accountCreationRequest) {

        return MultipartEntityBuilder.create()
                .addTextBody(AccountCreationConstants.FIELD_FORM_KEY, formKey)
                .addTextBody(AccountCreationConstants.FIELD_SUCCESS_URL, "")
                .addTextBody(AccountCreationConstants.FIELD_ERROR_URL, "")
                .addTextBody(AccountCreationConstants.FIELD_FIRST_NAME, accountCreationRequest.getFirstName())
                .addTextBody(AccountCreationConstants.FIELD_MIDDLE_NAME, accountCreationRequest.getMiddleName())
                .addTextBody(AccountCreationConstants.FIELD_LAST_NAME, accountCreationRequest.getLastName())
                .addTextBody(AccountCreationConstants.FIELD_EMAIL, accountCreationRequest.getEmailAddress())
                .addTextBody(AccountCreationConstants.FIELD_PASSWORD, accountCreationRequest.getPassword())
                .addTextBody(AccountCreationConstants.FIELD_CONFIRM_PASS, accountCreationRequest.getConfirmPassword())
                .build();
    }

    public static AccountCreationResponse createAccountCreationResponseObject(
            AccountCreationRequest accountCreationRequest) {

        Account account = Account.builder().firstName(accountCreationRequest.getFirstName())
                .middleName(accountCreationRequest.getMiddleName())
                .lastName(accountCreationRequest.getLastName())
                .emailAddress(accountCreationRequest.getEmailAddress())
                .subscribed(accountCreationRequest.isSubscribed()).build();
        AccountCreationResponse accountCreationResponse = new AccountCreationResponse();
        accountCreationResponse.setAccount(account);

        return accountCreationResponse;
    }

    public static String getJsonStringResponse(AccountCreationResponse accountCreationResponse) {

        String jsonStringResponse = null;
        try {
            if (accountCreationResponse != null) {
                jsonStringResponse = objectMapper.writeValueAsString(accountCreationResponse);
            }
        } catch (JsonProcessingException jpEx) {
            log.error("Failed to convert the response object {}", accountCreationResponse);
        }
        return jsonStringResponse;
    }

    public static String getJsonStringErrorResponse(AccountCreationErrorResponse accountCreationErrorResponse) {

        String jsonStringResponse = null;
        try {
            if (accountCreationErrorResponse != null) {
                jsonStringResponse = objectMapper.writeValueAsString(accountCreationErrorResponse);
            }
        } catch (JsonProcessingException jpEx) {
            log.error("Failed to convert the response object {}", accountCreationErrorResponse);
        }
        return jsonStringResponse;
    }
}
