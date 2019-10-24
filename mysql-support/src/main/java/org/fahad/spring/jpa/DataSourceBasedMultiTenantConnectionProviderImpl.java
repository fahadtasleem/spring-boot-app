package org.fahad.spring.jpa;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private Map<String, ConnectionProvider> connectionProviderMap
            = new ConcurrentHashMap<>();

    final String DEFAULT_MODULE = "default";
    final String DEFAULT_TENANT = "t0";

    @Autowired
    MultiTenantDataSourceFactory dataSourceFactory;


    @Override
    protected DataSource selectAnyDataSource() {
        return dataSourceFactory.getDatasourceForTenant(DEFAULT_MODULE,DEFAULT_TENANT);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return dataSourceFactory.getDatasourceForTenant(DEFAULT_MODULE,tenantIdentifier);
    }
}
