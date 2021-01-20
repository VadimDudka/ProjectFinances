package com.niqz.projectfinances.service;

import com.niqz.projectfinances.dto.User;
import com.niqz.projectfinances.dto.request.PasswordRestoreRequest;
import com.niqz.projectfinances.dto.request.PasswordUpdateRequest;
import com.niqz.projectfinances.dto.response.PasswordRestoreTokenDataResponse;
import com.niqz.projectfinances.exception.token.ExpiredPasswordRestoreTokenException;
import com.niqz.projectfinances.exception.token.PasswordRestoreTokenNotFoundException;
import com.niqz.projectfinances.exception.token.PasswordRestoreTokenSaveException;
import com.niqz.projectfinances.exception.user.UserNotFoundException;
import com.niqz.projectfinances.exception.user.UserUpdateException;
import com.niqz.projectfinances.model.PasswordRestoreTokenModel;
import com.niqz.projectfinances.model.PasswordRestoreTokenPayloadModel;
import com.niqz.projectfinances.repository.PasswordRestoreTokenRepository;
import com.niqz.projectfinances.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PasswordRestoreService {

    private final MailService mailService;
    private final PasswordRestoreTokenRepository passwordRestoreTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${custom.password.restore.link}")
    private String PASSWORD_RESTORE_LINK;
    @Value("${custom.password.restore.token.expiration}")
    private int EXPIRATION_TIME;

    public void processPasswordRestore(PasswordRestoreRequest passwordRestoreRequest) throws MessagingException, PasswordRestoreTokenSaveException, UserNotFoundException {
        String userEmail = passwordRestoreRequest.getEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        PasswordRestoreTokenModel passwordRestoreTokenModel = new PasswordRestoreTokenModel(user.getId(), EXPIRATION_TIME);
        passwordRestoreTokenRepository.save(passwordRestoreTokenModel);
        mailService.sendPasswordRestoreLink(
                user,
                String.format(PASSWORD_RESTORE_LINK, passwordRestoreTokenModel.getValue()));
    }

    public PasswordRestoreTokenDataResponse getDataFromToken(String tokenValue) throws ExpiredPasswordRestoreTokenException, PasswordRestoreTokenNotFoundException {
        PasswordRestoreTokenPayloadModel payloadModel = passwordRestoreTokenRepository.findPayloadByValue(tokenValue)
                .orElseThrow(PasswordRestoreTokenNotFoundException::new);
        if (payloadModel.getExpiryDate().isBefore(OffsetDateTime.now())) {
            throw new ExpiredPasswordRestoreTokenException();
        }
        return PasswordRestoreTokenDataResponse.builder()
                .email(payloadModel.getEmail())
                .build();
    }

    public void processPasswordUpdate(PasswordUpdateRequest passwordUpdateRequest) throws UserUpdateException, MessagingException {
        passwordUpdateRequest.setNewPassword(
                passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.updatePasswordByEmail(passwordUpdateRequest.getEmail(), passwordUpdateRequest.getNewPassword());
        mailService.sendUpdatedPasswordMessage(passwordUpdateRequest.getEmail());
    }
}
