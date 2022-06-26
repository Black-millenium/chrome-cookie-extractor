package me.desu.chromecookieextractor.configuration;

import me.desu.chromecookieextractor.utils.Utils;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.JDBC;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();

        builder.type(SQLiteDataSource.class);
        builder.driverClassName(JDBC.class.getName());
        builder.url("jdbc:sqlite:" + Utils.cookiePath());

        return builder.build();
    }
}
