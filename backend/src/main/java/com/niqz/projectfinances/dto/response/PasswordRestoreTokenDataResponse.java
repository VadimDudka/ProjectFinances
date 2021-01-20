package com.niqz.projectfinances.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PasswordRestoreTokenDataResponse {
    private final String email;
}
