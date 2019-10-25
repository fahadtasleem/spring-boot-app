package org.fahad.spring.core.request;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class DefaultWebConfig {

    @Bean
    public FilterRegistrationBean serviceAuthFilter(){
        FilterRegistrationBean<DefaultServiceFilter> registrationBean = new FilterRegistrationBean();
        registrationBean.setName("apiAuth");
        registrationBean.setFilter(new DefaultServiceFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
