package org.fahad.spring.manager;

import org.fahad.spring.entities.User;
import org.fahad.spring.entities.model.UserModel;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface UserManager {
    User getById(String id);

    User save(UserModel userModel);

    @Async("asyncExecutor")
    CompletableFuture<User> getAsyncUser() throws InterruptedException;
}
