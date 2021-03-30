package com.magento.account.creation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Rajeev Krishna
 * @description Account model class holds all the account creation fields.
 */
@Data
@Builder
@AllArgsConstructor
public class Account {

    String firstName;

    String middleName;

    String lastName;

    String emailAddress;

    boolean subscribed;
}
