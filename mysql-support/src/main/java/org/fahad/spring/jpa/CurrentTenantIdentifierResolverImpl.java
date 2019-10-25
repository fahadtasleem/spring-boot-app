package org.fahad.spring.jpa;

import org.fahad.spring.core.request.UserContextProvider;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

  public static final String DEFAULT_TENANT_ID = "t0";

  @Override
  public String resolveCurrentTenantIdentifier() {
      String tenantId = UserContextProvider.getTenantId();
      return StringUtils.isEmpty(tenantId)?DEFAULT_TENANT_ID:tenantId;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
