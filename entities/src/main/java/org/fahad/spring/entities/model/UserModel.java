package org.fahad.spring.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.fahad.spring.entities.User;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    private String id;
    private String name;
    private int age;

    public User createUser(){
        User user = new User();
        user.setAge(this.getAge());
        user.setName(this.getName());
        if(this.getId() != null) {
            user.setId(Long.valueOf(this.getId()));
        }
        return user;
    }
}
