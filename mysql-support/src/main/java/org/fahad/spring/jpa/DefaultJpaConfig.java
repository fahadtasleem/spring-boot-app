package org.fahad.spring.jpa;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

public class DefaultJpaConfig {

    private String[] packagesToScan;

    protected DefaultJpaConfig(String[] packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Bean(
            name = {"tenantJpaVendorAdapter"}
    )
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(
            name = {"tenantTransactionManager"}
    )
    public JpaTransactionManager transactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory tenantEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManagerFactory);
        return transactionManager;
    }

    @Bean(
            name = {"datasourceBasedMultiTenantConnectionProvider"}
    )
    @ConditionalOnBean(
            name = {"multiTenantDataSourceFactory"}
    )
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new DataSourceBasedMultiTenantConnectionProviderImpl();
    }

    @Bean(
            name = {"currentTenantIdentifierResolver"}
    )
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    @Bean(
            name = {"tenantEntityManagerFactory"}
    )
    @ConditionalOnBean(
            name = {"datasourceBasedMultiTenantConnectionProvider"}
    )
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("datasourceBasedMultiTenantConnectionProvider") MultiTenantConnectionProvider connectionProvider,
            @Qualifier("currentTenantIdentifierResolver") CurrentTenantIdentifierResolver tenantResolver,
            @Qualifier("tenantJpaVendorAdapter") JpaVendorAdapter tenantJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setPackagesToScan(this.packagesToScan);
        emfBean.setJpaVendorAdapter(tenantJpaVendorAdapter);
        emfBean.setPersistenceUnitName("tenant-persistence-unit");
        Map<String, Object> properties = new HashMap();
        properties.put("hibernate.multiTenancy", MultiTenancyStrategy.DATABASE);
        properties.put("hibernate.multi_tenant_connection_provider", connectionProvider);
        properties.put("hibernate.tenant_identifier_resolver", tenantResolver);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        emfBean.setJpaPropertyMap(properties);
//        log.info("tenantEntityManagerFactory set up successfully!!");
        return emfBean;
    }
}
