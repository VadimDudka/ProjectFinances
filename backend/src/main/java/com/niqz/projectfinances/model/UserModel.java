package com.niqz.projectfinances.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    protected Integer id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String email;
    protected String password;
}
