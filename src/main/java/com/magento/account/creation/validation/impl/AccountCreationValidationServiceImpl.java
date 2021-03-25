package com.magento.account.creation.validation.impl;

import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.exception.RequestValidationException;
import com.magento.account.creation.model.error.ErrorResponse;
import com.magento.account.creation.validation.AccountCreationValidationService;
import com.magento.account.creation.model.request.AccountCreationRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationValidationServiceImpl implements AccountCreationValidationService {

    @Override
    public void validate(AccountCreationRequest accountCreationRequest) {

            validateMandatoryNameField(accountCreationRequest.getFirstName(), AccountCreationConstants.FIRST_NAME);
            validateMandatoryNameField(accountCreationRequest.getLastName(), AccountCreationConstants.LAST_NAME);
            validateNonMandatoryNameField(accountCreationRequest.getMiddleName(), AccountCreationConstants.MIDDLE_NAME);
            validateEmail(accountCreationRequest.getEmailAddress(), AccountCreationConstants.EMAIL);
            validatePasswords(accountCreationRequest.getPassword(), accountCreationRequest.getConfirmPassword(),
                    AccountCreationConstants.PASSWORD);
    }

    private void validateMandatoryNameField(String nameValue, String fieldName){
        // validate name written in every language via unicode property.
        // Excludes numbers and special character.
        if(StringUtils.isNotBlank(nameValue)) {
            if (nameValue.length() > AccountCreationConstants.STRING_FIELD_SIZE){
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                        fieldName + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
            }
            if (!nameValue.matches("^[\\p{L} .'-]+$")){
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                        fieldName + AccountCreationConstants.FIELD_REGEX));
            }
        } else {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                    fieldName + AccountCreationConstants.FIELD_VALUE_NULL));
        }
    }

    private void validateNonMandatoryNameField(String nameValue, String fieldName){
        // validate name written in every language via unicode property.
        // Excludes numbers and special character.
        if(StringUtils.isNotBlank(nameValue)) {
            if (nameValue.length() > AccountCreationConstants.STRING_FIELD_SIZE){
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                        fieldName + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
            }
            if (!nameValue.matches("^[\\p{L} .'-]+$")){
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                        fieldName + AccountCreationConstants.FIELD_REGEX));
            }
        }
    }

    private void validateEmail(String email, String fieldName){

        if (StringUtils.isNotBlank(email) && email.length() > AccountCreationConstants.STRING_FIELD_SIZE){
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                    fieldName + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
        }
        if(!EmailValidator.getInstance().isValid(email)) {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                    fieldName + AccountCreationConstants.FIELD_REGEX));
        }
    }

    private void validatePasswords(String password, String confirmPassword, String fieldName){

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)){

            if (password.length() > AccountCreationConstants.STRING_FIELD_SIZE ||
                    confirmPassword.length() > AccountCreationConstants.STRING_FIELD_SIZE){
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                       AccountCreationConstants.ERR_CODE_VALIDATION +
                       fieldName  + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
            }

            if (!password.equals(confirmPassword)){
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                        fieldName + AccountCreationConstants.PASSWORD_MATCH));
            }
        }
    }
}
