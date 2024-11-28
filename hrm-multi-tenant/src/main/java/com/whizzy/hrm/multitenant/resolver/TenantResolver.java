package com.whizzy.hrm.multitenant.resolver;

import com.whizzy.hrm.multitenant.exception.TenantNotFoundException;
import com.whizzy.hrm.multitenant.util.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * This class translates the current tenant into the schema to be used for the data source.
 */
@Component
public class TenantResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        if(TenantContext.getTenantId() == null) {
            throw new TenantNotFoundException("Not able to resolve tenant.");
        }
        return TenantContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
