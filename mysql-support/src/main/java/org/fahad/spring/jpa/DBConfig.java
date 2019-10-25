package org.fahad.spring.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DBConfig {
    private String moduleName;
    private String tenantId;
}
