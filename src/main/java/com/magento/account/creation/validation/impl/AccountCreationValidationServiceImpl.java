package com.magento.account.creation.validation.impl;

import com.magento.account.creation.constants.AccountCreationConstants;
import com.magento.account.creation.exception.RequestValidationException;
import com.magento.account.creation.model.error.ErrorResponse;
import com.magento.account.creation.model.request.AccountCreationRequest;
import com.magento.account.creation.validation.AccountCreationValidationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationValidationServiceImpl implements AccountCreationValidationService {

    @Override
    public AccountCreationRequest validate(AccountCreationRequest accountCreationRequest) {

        if (accountCreationRequest != null) {
            validateMandatoryNameField(accountCreationRequest.getFirstName(), AccountCreationConstants.FIRST_NAME);
            validateMandatoryNameField(accountCreationRequest.getLastName(), AccountCreationConstants.LAST_NAME);

            if (accountCreationRequest.getMiddleName() != null) {
                validateNonMandatoryNameField(accountCreationRequest.getMiddleName());
            } else {
                accountCreationRequest.setMiddleName(StringUtils.EMPTY);
            }

            validateEmail(accountCreationRequest.getEmailAddress());
            validatePasswords(accountCreationRequest.getPassword(), accountCreationRequest.getConfirmPassword()
            );
        } else {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                            AccountCreationConstants.REQUEST_OBJECT_VALUE_NULL));
        }

        return accountCreationRequest;
    }

    private void validateMandatoryNameField(String nameValue, String fieldName) {
        // validate name written in every language via unicode property.
        // Excludes numbers and special character.
        if (StringUtils.isNotBlank(nameValue)) {
            if (nameValue.length() > AccountCreationConstants.STRING_FIELD_SIZE) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                fieldName + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
            }
            if (!nameValue.matches("^[\\p{L} .'-]+$")) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                fieldName + AccountCreationConstants.FIELD_REGEX + nameValue));
            }
        } else {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                            fieldName + AccountCreationConstants.FIELD_VALUE_NULL));
        }
    }

    private void validateNonMandatoryNameField(String nameValue) {
        // validate name written in every language via unicode property.
        // Excludes numbers and special character.
        if (StringUtils.isNotBlank(nameValue)) {
            if (nameValue.length() > AccountCreationConstants.STRING_FIELD_SIZE) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                AccountCreationConstants.MIDDLE_NAME + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
            }
            if (!nameValue.matches("^[\\p{L} .'-]+$")) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                AccountCreationConstants.MIDDLE_NAME + AccountCreationConstants.FIELD_REGEX + nameValue));
            }
        }
    }

    private void validateEmail(String email) {

        if (StringUtils.isNotBlank(email) && email.length() > AccountCreationConstants.STRING_FIELD_SIZE) {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                            AccountCreationConstants.EMAIL + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                            AccountCreationConstants.EMAIL + AccountCreationConstants.FIELD_REGEX + email));
        }
    }

    private void validatePasswords(String password, String confirmPassword) {

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword)) {

            if (password.length() > AccountCreationConstants.STRING_FIELD_SIZE ||
                    confirmPassword.length() > AccountCreationConstants.STRING_FIELD_SIZE) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                AccountCreationConstants.PASSWORD + AccountCreationConstants.ERR_FIELD_LENGTH_HIGH));
            }
            if (password.length() < AccountCreationConstants.PASSWORD_MIN_FIELD_SIZE ||
                    confirmPassword.length() < AccountCreationConstants.PASSWORD_MIN_FIELD_SIZE) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                AccountCreationConstants.PASSWORD + AccountCreationConstants.ERR_FIELD_LENGTH_LOW));
            }

            if (!password.equals(confirmPassword)) {
                throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                        AccountCreationConstants.ERR_CODE_VALIDATION +
                                AccountCreationConstants.PASSWORD + AccountCreationConstants.FIELD_PASS_MATCH));
            }
        } else {
            throw new RequestValidationException(new ErrorResponse(HttpStatus.BAD_REQUEST,
                    AccountCreationConstants.ERR_CODE_VALIDATION +
                            AccountCreationConstants.PASSWORD + AccountCreationConstants.REQUEST_OBJECT_VALUE_NULL));
        }
    }
}
