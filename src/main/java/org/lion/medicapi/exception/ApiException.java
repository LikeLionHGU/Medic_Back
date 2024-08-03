package org.lion.medicapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private final String code;
    private final String message;
    private final Object data;

    public ApiException(ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
        this.data = null;
    }

    public ApiException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
        this.data = data;
    }

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ApiException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
