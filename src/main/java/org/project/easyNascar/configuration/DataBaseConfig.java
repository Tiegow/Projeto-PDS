package org.project.easyNascar.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({FlywayProperties.class})
public class DataBaseConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    //CONFIGURAÇÃO DO FLYWAY PARA MIGRATION
    @Bean(initMethod = "migrate")
    public Flyway flyway(FlywayProperties flywayProperties) {
        flywayProperties.setUrl(url);
        return Flyway.configure()
                .dataSource(flywayProperties.getUrl(),
                        username,
                        password)
                .locations(flywayProperties.getLocations().toArray(String[]::new))
                .baselineOnMigrate(true)
                .load();
    }


    //CONFIGURACAO PARA O DATASOURCE
    @Primary
    @Bean
    public DataSource primaryDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
