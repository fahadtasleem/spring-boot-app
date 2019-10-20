package org.fahad.spring.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class UserModel {
    private String name;
    private int age;
}
