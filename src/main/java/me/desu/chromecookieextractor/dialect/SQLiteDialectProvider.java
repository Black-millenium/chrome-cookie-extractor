package me.desu.chromecookieextractor.dialect;

import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.Optional;

public class SQLiteDialectProvider implements DialectResolver.JdbcDialectProvider {
    @Override
    public Optional<Dialect> getDialect(JdbcOperations operations) {
        return Optional.of(new SQLiteDialect());
    }
}
