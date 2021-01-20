package com.niqz.projectfinances.repository;

import com.niqz.projectfinances.dto.User;
import com.niqz.projectfinances.exception.user.UserUpdateException;
import com.niqz.projectfinances.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/user.properties")
public class UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${saveUser}")
    private String SAVE;
    @Value("${findUserByUsername}")
    private String FIND_BY_USERNAME;
    @Value("${findUserByEmail}")
    private String FIND_BY_EMAIL;
    @Value("${existUserByUsername}")
    private String EXIST_BY_USERNAME;
    @Value("${existUserByEmail}")
    private String EXIST_BY_EMAIL;
    @Value("${updateUserPasswordByEmail}")
    private String UPDATE_PASSWORD_BY_EMAIL;

    public void save(User user) {
        namedParameterJdbcTemplate.update(
                SAVE,
                new BeanPropertySqlParameterSource(user));
    }

    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(
                    User.build(
                            namedParameterJdbcTemplate.queryForObject(
                                    FIND_BY_USERNAME,
                                    new MapSqlParameterSource()
                                            .addValue("username", username),
                                    new BeanPropertyRowMapper<>(UserModel.class))));
        } catch (DataAccessException e) {
            log.warn("User with username '{}' not found. Exception message: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    User.build(
                            namedParameterJdbcTemplate.queryForObject(
                                    FIND_BY_EMAIL,
                                    new MapSqlParameterSource()
                                            .addValue("email", email),
                                    new BeanPropertyRowMapper<>(UserModel.class))));
        } catch (DataAccessException e) {
            log.warn("User with email '{}' not found. Exception message: {}", email, e.getMessage());
            return Optional.empty();
        }
    }

    public boolean existByUsername(String username) {
        try {
            return null != namedParameterJdbcTemplate.queryForObject(
                    EXIST_BY_USERNAME,
                    new MapSqlParameterSource()
                            .addValue("username", username),
                    String.class);
        } catch (DataAccessException e) {
            log.warn("User with username '{}' not found. Exception message: {}", username, e.getMessage());
            return false;
        }
    }

    public boolean existByEmail(String email) {
        try {
            return null != namedParameterJdbcTemplate.queryForObject(
                    EXIST_BY_EMAIL,
                    new MapSqlParameterSource()
                            .addValue("email", email),
                    String.class);
        } catch (DataAccessException e) {
            log.warn("User with email '{}' not found. Exception message: {}", email, e.getMessage());
            return false;
        }
    }

    public void updatePasswordByEmail(String email, String newPassword) throws UserUpdateException {
        try {
            namedParameterJdbcTemplate.update(
                    UPDATE_PASSWORD_BY_EMAIL,
                    new MapSqlParameterSource()
                            .addValue("email", email)
                            .addValue("newPassword", newPassword));
        } catch (DataAccessException e) {
            log.warn("Failed to update password for user with email '{}'. Exception message: {}", email, e.getMessage());
            throw new UserUpdateException();
        }
    }
}
