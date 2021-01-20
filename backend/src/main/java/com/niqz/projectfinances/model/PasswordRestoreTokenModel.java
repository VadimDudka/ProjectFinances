package com.niqz.projectfinances.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasswordRestoreTokenModel {
    private Integer id;
    private Integer userId;
    private String value;
    private OffsetDateTime expiryDate;

    public PasswordRestoreTokenModel(Integer userId, int hoursUntilExpire) {
        this.userId = userId;
        this.value = UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
        this.expiryDate = OffsetDateTime.now().plusHours(hoursUntilExpire);
    }
}
