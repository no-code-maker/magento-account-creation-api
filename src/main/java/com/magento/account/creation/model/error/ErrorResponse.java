package com.magento.account.creation.model.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -3603081557950085827L;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int status;

    private String error;

    private String timestamp;

    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(HttpStatus status, String errorDescription) {
        this.timestamp = LocalDateTime.now().format(formatter);
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = errorDescription;

    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
