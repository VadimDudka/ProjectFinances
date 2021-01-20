package com.niqz.projectfinances.exception;

import com.niqz.projectfinances.exception.user.DuplicateUserEmailException;
import com.niqz.projectfinances.exception.user.DuplicateUserUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateUserUsernameException.class)
    public ResponseEntity<?> handleDuplicateUserUsernameException(DuplicateUserUsernameException e) {
        return new ResponseEntity<>(
                Map.of(
                        "type", "DUPLICATE_USERNAME", // Connected to frontend!
                        "username", e.getUsername()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateUserEmailException.class)
    public ResponseEntity<?> handleDuplicateUserEmailException(DuplicateUserEmailException e) {
        return new ResponseEntity<>(
                Map.of(
                        "type", "DUPLICATE_EMAIL", // Connected to frontend!
                        "email", e.getEmail()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
