package org.fahad.spring.launcher;

import lombok.extern.slf4j.Slf4j;
import org.fahad.spring.jpa.DefaultJpaConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.fahad.spring"})
@EntityScan("org.fahad.spring.entities")
public class JpaConfig {//extends DefaultJpaConfig {
    public JpaConfig(){
//        super(new String[]{
//                "org.fahad.spring.entities"
//        });
    }
}
