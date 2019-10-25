package org.fahad.spring.jpa;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

  public static final String DEFAULT_TENANT_ID = "t0";

  @Override
  public String resolveCurrentTenantIdentifier() {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      String tenantId = DEFAULT_TENANT_ID;
      if(attr!=null && attr.getRequest()!=null) {
          tenantId = attr.getRequest().getHeader("tenantId");
      }
      return StringUtils.isEmpty(tenantId)?DEFAULT_TENANT_ID:tenantId;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
