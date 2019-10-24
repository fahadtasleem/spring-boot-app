package org.fahad.spring.jpa;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

  public static final String DEFAULT_TENANT_ID = "t1";

  @Override
  public String resolveCurrentTenantIdentifier() {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      String tenantId = attr.getRequest().getHeader("tenantId");
      return StringUtils.isEmpty(tenantId)?DEFAULT_TENANT_ID:tenantId;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
