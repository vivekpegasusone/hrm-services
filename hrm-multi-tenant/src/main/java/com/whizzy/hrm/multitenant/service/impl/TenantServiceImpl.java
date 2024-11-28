package com.whizzy.hrm.multitenant.service.impl;

import com.whizzy.hrm.multitenant.domain.Tenant;
import com.whizzy.hrm.multitenant.repository.TenantRepository;
import com.whizzy.hrm.multitenant.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {
    private static final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public List<Tenant> findAll() {
         return tenantRepository.findAll();
    }

    @Override
    public List<Tenant> findByActive(boolean isActive) {
        log.info("Fetched data source configuration of all active tenants.");
        return tenantRepository.findByActive(isActive);
    }
}
