package com.niqz.projectfinances.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuplicateUserEmailException extends Exception {

    private final String email;
}
