package com.whizzy.hrm.multitenant.util;

import java.util.Optional;

import static com.whizzy.hrm.multitenant.util.Constants.DEFAULT_TENANT;

/*
* This class acts as holder for the current tenant.
*/
public class TenantContext {
    private static final ThreadLocal<String> TENANT = new InheritableThreadLocal<>();

    public static String getTenantId() {
        return Optional.ofNullable(TENANT.get()).orElse(DEFAULT_TENANT);
    }

    public static void setTenantId(String tenant) {
        TENANT.set(tenant);
    }

    public static void clear() {
        TENANT.remove();
    }
}
