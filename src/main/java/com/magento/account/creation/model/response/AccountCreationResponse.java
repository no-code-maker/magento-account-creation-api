package com.magento.account.creation.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.magento.account.creation.model.Account;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description AccountCreationResponse model class is a successful response for account creation request.
 */
@Data
public class AccountCreationResponse implements Serializable {

    private static final long serialVersionUID = -408569639810555783L;

    @JsonProperty
    private String statusDescription;

    @JsonProperty
    private Account account;
}
