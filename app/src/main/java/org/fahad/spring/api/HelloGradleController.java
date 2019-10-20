package org.fahad.spring.api;

import org.fahad.spring.model.UserModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloGradleController {
    @GetMapping(produces = "application/json")
    public UserModel helloWorld(){
        return UserModel.builder().name("My name").age(23).build();
    }
}
