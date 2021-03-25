package com.magento.account.creation.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Rajeev Krishna
 * @description Constant file for holding constants for magento account creation api.
 */
@NoArgsConstructor( access  = AccessLevel.PRIVATE)
public class AccountCreationConstants {

    public static final String CORRELATION_ID = "correlation-id";

    public static final String SERVICE_URL = "http://107.23.133.112/customer/account/create/";

    public static final int CONNECTION_TIMEOUT = 5000;
}
