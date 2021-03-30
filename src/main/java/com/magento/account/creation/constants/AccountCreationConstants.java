package com.magento.account.creation.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Rajeev Krishna
 * @description Constant file for holding constants for magento account creation api.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCreationConstants {

    public static final String FIRST_NAME = "[First Name]";

    public static final String LAST_NAME = "[Last Name]";

    public static final String MIDDLE_NAME = "[Middle Name]";

    public static final String EMAIL = "[Email Address]";

    public static final String PASSWORD = "[Password]";

    public static final String CORRELATION_ID = "correlation-id";

    public static final String SERVICE_PATH = "/magento/account/create";

    public static final int CONNECTION_TIMEOUT = 5000;

    public static final int STRING_FIELD_SIZE = 100;

    public static final int PASSWORD_MIN_FIELD_SIZE = 6;

    public static final int ACCT_CREATION_GET_MAX_RETRIES = 2;

    //Form Constants
    public static final String FIELD_FORM_KEY = "form_key";

    public static final String FIELD_SUCCESS_URL = "success_url";

    public static final String FIELD_ERROR_URL = "error_url";

    public static final String FIELD_FIRST_NAME = "firstname";

    public static final String FIELD_MIDDLE_NAME = "middlename";

    public static final String FIELD_LAST_NAME = "lastname";

    public static final String FIELD_EMAIL = "email";

    public static final String FIELD_PASSWORD = "password";

    public static final String FIELD_CONFIRM_PASSWORD = "confirmation";

    //Error Handling
    public static final String REQUEST_SUCCESS = "Successfully created account";

    public static final String REQUEST_FAILED = "Failed to create account";

    public static final String ERR_CODE_VALIDATION = "Request Validation Failed: ";

    public static final String ERR_CODE_SYSTEM_EXCEPTION = "System Error: ";

    public static final String ERR_FIELD_LENGTH_HIGH = " field length is greater than defined value: " + STRING_FIELD_SIZE;

    public static final String ERR_FIELD_LENGTH_LOW = " field length is less than defined value: 6";

    public static final String FIELD_REGEX = " field not matching valid pattern. Input provided: ";

    public static final String FIELD_VALUE_NULL = " mandatory field value is found NULL or empty ";

    public static final String REQUEST_OBJECT_VALUE_NULL = " request object is found NULL or empty ";

    public static final String PASSWORD_MATCH = " does not match";

    public static final String ERR_DOWNSTREAM_FAIL_MESSAGE = "Failed to completed registration due to downstream system failure.";
}
