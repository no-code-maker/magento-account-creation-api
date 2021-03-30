package com.magento.account.creation.model.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.magento.account.creation.constants.AccountCreationConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -3603081557950085827L;

    private int status;

    private String error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String path;

    private ErrorResponse() {
    }

    public ErrorResponse(HttpStatus status, String errorDescription) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = errorDescription;
        this.path = AccountCreationConstants.SERVICE_PATH;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
