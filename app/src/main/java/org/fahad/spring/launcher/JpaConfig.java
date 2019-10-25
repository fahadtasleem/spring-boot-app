package org.fahad.spring.launcher;

import lombok.extern.slf4j.Slf4j;
import org.fahad.spring.jpa.DefaultJpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * To remove multi tenancy support remove the extends DefaultJpaConfig.
 * Toggle the @EnableJpaRepositories annotation
 * In Manager classes remove the transactionManager.
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.fahad.spring"},
        entityManagerFactoryRef = "tenantEntityManagerFactory", transactionManagerRef = "tenantTransactionManager")
//@EnableJpaRepositories
public class JpaConfig extends DefaultJpaConfig {
    public JpaConfig(){
        super(new String[]{
                "org.fahad.spring"
        });
    }
}
