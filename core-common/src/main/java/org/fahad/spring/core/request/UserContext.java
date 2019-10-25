package org.fahad.spring.core.request;

import lombok.Data;

@Data
public class UserContext {
    private String userId;
    private String tenantId;
}
