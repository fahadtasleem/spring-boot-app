package org.fahad.spring.api;

import org.fahad.spring.entities.User;
import org.fahad.spring.entities.model.UserModel;
import org.fahad.spring.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserManager userManager;

    @GetMapping(path = {""}, produces = "application/json")
    public User getUser(@RequestParam String id){
        return userManager.getById(id);
    }

    @PostMapping(path = {""}, produces = "application/json",consumes =  "application/json")
    public User save(@RequestBody UserModel user){
        return userManager.save(user);
    }

    @GetMapping(path = {"/async"},produces = "application/json")
    public CompletableFuture<User> getAsync() throws InterruptedException {
        return userManager.getAsyncUser();
    }
}
