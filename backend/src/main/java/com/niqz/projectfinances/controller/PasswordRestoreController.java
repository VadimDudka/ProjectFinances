package com.niqz.projectfinances.controller;

import com.niqz.projectfinances.dto.request.PasswordRestoreRequest;
import com.niqz.projectfinances.dto.request.PasswordUpdateRequest;
import com.niqz.projectfinances.dto.response.PasswordRestoreTokenDataResponse;
import com.niqz.projectfinances.exception.token.ExpiredPasswordRestoreTokenException;
import com.niqz.projectfinances.exception.token.PasswordRestoreTokenNotFoundException;
import com.niqz.projectfinances.exception.token.PasswordRestoreTokenSaveException;
import com.niqz.projectfinances.exception.user.UserNotFoundException;
import com.niqz.projectfinances.exception.user.UserUpdateException;
import com.niqz.projectfinances.service.PasswordRestoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Slf4j
@RestController
@RequestMapping("/api/password-restore")
@RequiredArgsConstructor
public class PasswordRestoreController {

    private final PasswordRestoreService passwordRestoreService;

    @PostMapping("/token")
    public ResponseEntity<?> processPasswordRestore(@RequestBody PasswordRestoreRequest passwordRestoreRequest) throws MessagingException, PasswordRestoreTokenSaveException, UserNotFoundException {
        passwordRestoreService.processPasswordRestore(passwordRestoreRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/{tokenValue}")
    public PasswordRestoreTokenDataResponse getDataFromToken(@PathVariable String tokenValue) throws ExpiredPasswordRestoreTokenException, PasswordRestoreTokenNotFoundException {
        return passwordRestoreService.getDataFromToken(tokenValue);
    }

    @PutMapping("/password")
    public ResponseEntity<?> processPasswordUpdate(@RequestBody PasswordUpdateRequest passwordUpdateRequest) throws UserUpdateException, MessagingException {
        passwordRestoreService.processPasswordUpdate(passwordUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
