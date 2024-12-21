package com.whizzy.hrm.multitenant.config;

import com.whizzy.hrm.multitenant.resolver.TenantConnectionProvider;
import com.whizzy.hrm.multitenant.resolver.TenantResolver;
import com.whizzy.hrm.multitenant.repository.TenantRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.whizzy.hrm.multitenant.util.Constants.NONE;
import static com.whizzy.hrm.multitenant.util.Constants.TENANT_PERSISTENT_UNIT;

@Configuration
@EnableJpaRepositories(
        basePackages = { "com.whizzy.hrm" },
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {TenantRepository.class})
        }
)
public class TenantPersistenceConfig {

    private final JpaProperties jpaProperties;
    private final TenantResolver tenantResolver;
    private final TenantConnectionProvider connectionProvider;

    @Value("${tenant.package.scan}")
    private String tenantPackage;

    public TenantPersistenceConfig(TenantResolver tenantResolver, TenantConnectionProvider connectionProvider, JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
        this.tenantResolver = tenantResolver;
        this.connectionProvider = connectionProvider;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();

        emFactory.setPersistenceUnitName(TENANT_PERSISTENT_UNIT);
        emFactory.setPackagesToScan(this.tenantPackage != null ? this.tenantPackage : "com.whizzy.hrm");

        emFactory.setJpaVendorAdapter(getJpaVendorAdapter());
        emFactory.setJpaPropertyMap(getJpaPropertyMap());
        emFactory.setJpaProperties(additionalProperties());

        return emFactory;
    }

    @Bean
    @Primary
    public JpaTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory emFactory) {
        JpaTransactionManager tenantTransactionManager = new JpaTransactionManager();
        tenantTransactionManager.setEntityManagerFactory(emFactory);
        return tenantTransactionManager;
    }

    private JpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    private Map<String, Object> getJpaPropertyMap() {
        Map<String, Object> propertyMap = new HashMap<>(this.jpaProperties.getProperties());
        propertyMap.put(AvailableSettings.HBM2DDL_AUTO, NONE);
        propertyMap.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
        propertyMap.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this.connectionProvider);
        propertyMap.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this.tenantResolver);
        return propertyMap;
    }

    private Properties additionalProperties() {
        //final Properties hibernateProperties = new Properties();
        // Any other property specific to vendor and not available via AvailableSettings
        return new Properties();
    }
}