package com.niqz.projectfinances.controller;

import com.niqz.projectfinances.dto.request.SignInRequest;
import com.niqz.projectfinances.dto.request.SignUpRequest;
import com.niqz.projectfinances.dto.response.SignInResponse;
import com.niqz.projectfinances.exception.user.DuplicateUserEmailException;
import com.niqz.projectfinances.exception.user.DuplicateUserUsernameException;
import com.niqz.projectfinances.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public SignInResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) throws DuplicateUserEmailException, DuplicateUserUsernameException {
        authService.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }

}
