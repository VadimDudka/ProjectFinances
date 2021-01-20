package com.niqz.projectfinances.exception.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class PasswordRestoreTokenSaveException extends Exception {
    public PasswordRestoreTokenSaveException(Throwable cause) {
        super(cause);
    }
}
