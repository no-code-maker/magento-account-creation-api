package com.magento.account.creation.model.response;

import com.magento.account.creation.model.error.ErrorResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Rajeev Krishna
 * @description AccountCreationErrorResponse model class is a successful response for account creation request.
 */
@Data
@NoArgsConstructor
public class AccountCreationErrorResponse implements Serializable {

    private static final long serialVersionUID = 7834832650421665220L;

    private String statusDescription;

    private ErrorResponse errorResponse;

}
