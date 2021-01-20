package com.niqz.projectfinances.dto;

import com.niqz.projectfinances.dto.request.SignUpRequest;
import com.niqz.projectfinances.model.UserModel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public final class User extends UserModel implements UserDetails {

    private User(UserModel userModel) {
        super(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getUsername(),
                userModel.getEmail(),
                userModel.getPassword());
    }

    private User(SignUpRequest request) {
        super(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword());
    }

    public static User build(UserModel userModel) {
        return userModel == null ? null : new User(userModel);
    }

    public static User build(SignUpRequest request) {
        return request == null ? null : new User(request);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
