package com.magento.account.creation.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description Account model class holds all the account creation fields.
 */
@Data
@Builder
public class Account implements Serializable {

    String firstName;

    String middleName;

    String lastName;

    String emailAddress;

    boolean subscribed;
}
