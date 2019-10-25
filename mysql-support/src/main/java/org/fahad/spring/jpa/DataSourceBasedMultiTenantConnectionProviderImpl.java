package org.fahad.spring.jpa;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

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
