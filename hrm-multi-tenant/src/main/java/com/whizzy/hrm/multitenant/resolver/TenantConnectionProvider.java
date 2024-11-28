package com.whizzy.hrm.multitenant.resolver;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serial;

/**
 * This class is responsible for providing tenant-specific connection handling in a multi-tenant
 * application. The tenant distinction is realized by using tenant id.
 */
@Component
public class TenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    @Serial
    private static final long serialVersionUID = -5010963750867167334L;

    private final DataSource dataSource;
    private final TenantDataSourceLookup dataSourceLookup;

    public TenantConnectionProvider(DataSource dataSource, TenantDataSourceLookup dataSourceLookup) {
        super();
        this.dataSource = dataSource;
        this.dataSourceLookup = dataSourceLookup;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return dataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return dataSourceLookup.getDataSource(tenantIdentifier);
    }
}
