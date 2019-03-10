package com.Alikhan.springboot.web;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Spring configuration for database
 */
@org.springframework.context.annotation.Configuration


public class DatabaseConfiguration {
    private static final String DB = "todo";
    private static final String HOST = "localhost";

    public static final String USERNAME = "todo_admin";
    public static final String PASSWORD = "saul";


    @Bean
    public DataSource dataSource() {
        return getDataSrouce();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    private static DataSource getDataSrouce() {
        String url = String.format("jdbc:postgresql://%s/%s",HOST,DB);
        return DataSourceBuilder
                .create()
                .username(USERNAME)
                .password(PASSWORD)
                .url(url)
                .build();
    }
}
