package com.persistence.edu.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.persistence.edu.database.jpa.repositories")
@EnableTransactionManagement
public class DBConfig {


        @Bean
        @ConfigurationProperties(prefix = "db")
        public DataSource dataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            entityManagerFactoryBean.setDataSource(dataSource());
            entityManagerFactoryBean.setPackagesToScan("com.persistence.edu.database.jpa.entity");
            entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
            entityManagerFactoryBean.setJpaProperties(jpaProperties());

            return entityManagerFactoryBean;
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

            return transactionManager;
        }

        private JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
            hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");

            return hibernateJpaVendorAdapter;
        }

        private Properties jpaProperties() {
            Properties jpaProperties = new Properties();
            jpaProperties.setProperty("hibernate.show_sql", "true");
            jpaProperties.setProperty("hibernate.format_sql", "true");
            jpaProperties.setProperty("hibernate.use_sql_comments", "true");
            jpaProperties.setProperty("hibernate.show_sql", "true");
            jpaProperties.setProperty("hibernate.grobally_quoted_identifiers", "true");
            jpaProperties.setProperty("hibernate.type.descriptor.sql", "trace");

            return jpaProperties;
        }



}
