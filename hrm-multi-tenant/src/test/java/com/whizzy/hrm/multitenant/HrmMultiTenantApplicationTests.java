package com.whizzy.hrm.multitenant;

import com.whizzy.hrm.multitenant.crypto.CryptoService;
import com.whizzy.hrm.multitenant.domain.Tenant;
import com.whizzy.hrm.multitenant.resolver.TenantConnectionProvider;
import com.whizzy.hrm.multitenant.resolver.TenantDataSourceLookup;
import com.whizzy.hrm.multitenant.service.TenantService;
import com.whizzy.hrm.multitenant.util.TenantContext;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.CannotCreateTransactionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HrmMultiTenantApplicationTests {

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantDataSourceLookup tenantDataSourceLookup;

    @Autowired
    private TenantConnectionProvider tenantConnectionProvider;

    @Test
    void findAllTenants() {
        TenantContext.setTenantId("drishti");
        List<Tenant> tenantList = tenantService.findAll();
        assertNotNull(tenantList, "The tenant list can not be null");
        assertEquals(tenantList.size(), 1, "The tenant list must contain 1 record");
    }

    @Test
    void throwExceptionWhenTenantNotFound() {
        TenantContext.clear();
        assertThrows(CannotCreateTransactionException.class, () -> tenantService.findAll(), "The tenant can not be resolved");
    }

    @Test
    void tenantDataSourceConnection() throws SQLException {
        DataSource dataSource = tenantDataSourceLookup.getDataSource("drishti");
        assertNotNull(dataSource, "Datasource can not be null.");
        assertNotNull(dataSource.getConnection(), "Database connection can not be null.");
        assertEquals(((HikariDataSource)dataSource).getPoolName(), "drishti_datasource", "Connection pool name must be equal.");
    }

    @Test
    void selectAnyDataSource() throws SQLException {
        TenantContext.setTenantId("drishti");
        Connection connection = tenantConnectionProvider.getAnyConnection();
        assertEquals(connection.getCatalog(), "tenant_master", "Schema name must match.");
    }

    @Test
    void verfyEncryption() {
        String plainText = "connect@123";
        String cipherText = cryptoService.encrypt(plainText);
        assertNotNull(cipherText, "Encrypted text can not be null.");
        String text = cryptoService.decrypt(cipherText);
        assertNotNull(text, "Decrypted text can not be null.");
        assertEquals(text, plainText, "Decrypted text must match with un encrypted text");
    }
}
