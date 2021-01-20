package com.niqz.projectfinances.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInResponse {
    private final String token;
    private final int id;
    private final String username;
    private final String email;
}
