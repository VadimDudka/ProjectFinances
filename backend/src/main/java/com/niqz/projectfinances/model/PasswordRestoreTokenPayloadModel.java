package com.niqz.projectfinances.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PasswordRestoreTokenPayloadModel {
    private String email;
    private OffsetDateTime expiryDate;
}
