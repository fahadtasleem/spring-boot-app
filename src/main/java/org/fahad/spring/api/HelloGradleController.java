package org.fahad.spring.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloGradleController {
    @GetMapping
    public String helloWorld(){
        return "Hello Gradle!";
    }
}
