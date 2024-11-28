package com.whizzy.hrm.multitenant.service;

import com.whizzy.hrm.multitenant.domain.Tenant;

import java.util.List;

public interface TenantService {
    List<Tenant> findAll();
    List<Tenant> findByActive(boolean isActive);
}
