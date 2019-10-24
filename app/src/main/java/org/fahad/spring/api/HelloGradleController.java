package org.fahad.spring.api;

import org.fahad.spring.entities.model.UserModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloGradleController {
    @GetMapping(produces = "application/json")
    public UserModel helloWorld(){
        return UserModel.builder().name("Test").age(23).build();
    }
}
