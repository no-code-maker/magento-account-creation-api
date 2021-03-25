package com.magento.account.creation.model.request;

import lombok.Data;

/**
 * @author Rajeev Krishna
 * @description AccountCreationRequest model class holds all the account creation user input fields.
 */
@Data
public class AccountCreationRequest {

    String firstName;

    String middleName;

    String lastName;

    String emailAddress;

    String password;

    String confirmPassword;

}
