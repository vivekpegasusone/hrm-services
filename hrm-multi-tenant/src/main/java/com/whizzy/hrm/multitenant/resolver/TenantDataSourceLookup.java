package com.whizzy.hrm.multitenant.resolver;

import com.whizzy.hrm.multitenant.converter.CryptoService;
import com.whizzy.hrm.multitenant.domain.Tenant;
import com.whizzy.hrm.multitenant.service.TenantService;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.whizzy.hrm.multitenant.util.Constants.MASTER;
import static com.whizzy.hrm.multitenant.util.Constants.POOL_NAME_SUFFIX;
import static org.springframework.util.StringUtils.hasLength;

@Component
public class TenantDataSourceLookup extends MapDataSourceLookup {

    private static final Logger log = LoggerFactory.getLogger(TenantDataSourceLookup.class);

    private final CryptoService cryptoService;
    private final ApplicationContext applicationContext;

    public TenantDataSourceLookup(CryptoService cryptoService, ApplicationContext applicationContext) {
        super();
        this.cryptoService = cryptoService;
        this.applicationContext = applicationContext;
    }

    @EventListener (ContextRefreshedEvent.class)
    public void onContextRefresh(ContextRefreshedEvent contextRefreshedEvent) {
        TenantService tenantService = applicationContext.getBean(TenantService.class);
        List<Tenant> tenants = tenantService.findByActive(true);
        tenants.forEach(t -> {
            HikariDataSource dataSource = createDataSource(t, t.getName());
            addDataSource(t.getName(), dataSource);
        });
        log.info("Application context started at {}. Total {} tenant data sources found.", contextRefreshedEvent.getApplicationContext().getStartupDate(), tenants.size());
    }

    private HikariDataSource createDataSource(Tenant tenant, String schema) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(tenant.getUrl());
        dataSource.setUsername(tenant.getUsername());
        dataSource.setPassword(cryptoService.decrypt(tenant.getPassword()));
        dataSource.setDriverClassName(tenant.getDriverClass());
        dataSource.setMinimumIdle(tenant.getMinIdle());
        dataSource.setMaximumPoolSize(tenant.getMaxPoolSize());
        dataSource.setMaxLifetime(tenant.getMaxLifeTime());
        dataSource.setKeepaliveTime(tenant.getKeepAliveTime());
        dataSource.setConnectionTimeout(tenant.getConnectionTimeout());
        dataSource.setPoolName((hasLength(schema) ? schema : MASTER).concat(POOL_NAME_SUFFIX));
        return dataSource;
    }
}
