package com.niqz.projectfinances.repository;

import com.niqz.projectfinances.exception.token.PasswordRestoreTokenSaveException;
import com.niqz.projectfinances.model.PasswordRestoreTokenModel;
import com.niqz.projectfinances.model.PasswordRestoreTokenPayloadModel;
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
@PropertySource("classpath:sql/password-reset-token.properties")
public class PasswordRestoreTokenRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${savePasswordRestoreToken}")
    private String SAVE;
    @Value("${findPasswordRestoreTokenPayloadByValue}")
    private String FIND_PAYLOAD_BY_VALUE;

    public void save(PasswordRestoreTokenModel passwordRestoreTokenModel) throws PasswordRestoreTokenSaveException {
        try {
            namedParameterJdbcTemplate.update(
                    SAVE,
                    new BeanPropertySqlParameterSource(passwordRestoreTokenModel));
        } catch (DataAccessException e) {
            log.warn("Failed to save token {}. Exception message: {}", passwordRestoreTokenModel, e.getMessage());
            throw new PasswordRestoreTokenSaveException(e);
        }
    }

    public Optional<PasswordRestoreTokenPayloadModel> findPayloadByValue(String tokenValue) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    FIND_PAYLOAD_BY_VALUE,
                    new MapSqlParameterSource()
                            .addValue("tokenValue", tokenValue),
                    new BeanPropertyRowMapper<>(PasswordRestoreTokenPayloadModel.class)));
        } catch (DataAccessException e) {
            log.warn("Failed to find payload, associated with token '{}'. Exception message: {}", tokenValue, e.getMessage());
            return Optional.empty();
        }
    }
}
