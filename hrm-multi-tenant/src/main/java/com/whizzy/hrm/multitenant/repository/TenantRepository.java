package com.whizzy.hrm.multitenant.repository;

import com.whizzy.hrm.multitenant.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findByActive(boolean isActive);
}
