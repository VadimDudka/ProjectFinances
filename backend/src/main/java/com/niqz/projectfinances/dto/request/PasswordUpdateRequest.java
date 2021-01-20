package com.niqz.projectfinances.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
    private String email;
    private String newPassword;
}
