package com.niqz.projectfinances.service;

import com.niqz.projectfinances.dto.User;
import com.niqz.projectfinances.dto.request.SignInRequest;
import com.niqz.projectfinances.dto.request.SignUpRequest;
import com.niqz.projectfinances.dto.response.SignInResponse;
import com.niqz.projectfinances.exception.user.DuplicateUserEmailException;
import com.niqz.projectfinances.exception.user.DuplicateUserUsernameException;
import com.niqz.projectfinances.repository.UserRepository;
import com.niqz.projectfinances.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtUtils.getToken(authentication);

        return SignInResponse.builder()
                .token(token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public void signUp(SignUpRequest signUpRequest) throws DuplicateUserUsernameException, DuplicateUserEmailException {
        if (userRepository.existByEmail(signUpRequest.getEmail())) {
            throw new DuplicateUserEmailException(signUpRequest.getEmail());
        }
        if (userRepository.existByUsername(signUpRequest.getUsername())) {
            throw new DuplicateUserUsernameException(signUpRequest.getUsername());
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(User.build(signUpRequest));
    }
}
