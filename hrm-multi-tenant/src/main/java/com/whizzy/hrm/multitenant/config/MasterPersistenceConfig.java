package com.whizzy.hrm.multitenant.config;

import com.whizzy.hrm.multitenant.converter.CryptoService;
import com.whizzy.hrm.multitenant.domain.Tenant;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import static com.whizzy.hrm.multitenant.util.Constants.NONE;
import static com.whizzy.hrm.multitenant.util.Constants.MASTER;
import static com.whizzy.hrm.multitenant.util.Constants.POOL_NAME_SUFFIX;
import static com.whizzy.hrm.multitenant.util.Constants.MASTER_PERSISTENT_UNIT;

@Component
@EnableJpaRepositories(
        basePackages = { "com.whizzy.hrm.multitenant.repository"}
)
public class MasterPersistenceConfig {

    @Value("${master.datasource.jdbc-url}")
    private String url;

    @Value("${master.datasource.driver-class-name}")
    private String driverClass;

    @Value("${master.datasource.username}")
    private String username;

    @Value("${master.datasource.password}")
    private String password;

    @Value("${master.datasource.minimum-idle}")
    private int minimumIdle;

    @Value("${master.datasource.maximum-pool-size}")
    private int maximumPoolSize;

    @Value("${master.datasource.max-lifetime}")
    private int maxLifetime;

    @Value("${master.datasource.keepalive-time}")
    private int keepAliveTime;

    @Value("${master.datasource.connection-timeout}")
    private int connectionTimeout;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

        entityManager.setPersistenceUnitName(MASTER_PERSISTENT_UNIT);
        entityManager.setPackagesToScan(Tenant.class.getPackageName());
        entityManager.setDataSource(dataSource);

        entityManager.setJpaVendorAdapter(getJpaVendorAdapter());
        entityManager.setJpaPropertyMap(getJpaProperties());
        return entityManager;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(cryptoService.decrypt(this.password));
        dataSource.setDriverClassName(this.driverClass);
        dataSource.setMinimumIdle(this.minimumIdle);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        dataSource.setMaxLifetime(this.maxLifetime);
        dataSource.setKeepaliveTime(this.keepAliveTime);
        dataSource.setConnectionTimeout(this.connectionTimeout);
        dataSource.setPoolName(MASTER.concat(POOL_NAME_SUFFIX));
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private JpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    private Map<String, Object> getJpaProperties() {
        Map<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
        properties.put(AvailableSettings.HBM2DDL_AUTO, NONE);
        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);

        return properties;
    }
}
