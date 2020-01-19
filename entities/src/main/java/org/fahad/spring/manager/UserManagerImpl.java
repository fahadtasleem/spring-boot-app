package org.fahad.spring.manager;

import org.fahad.spring.core.request.UserContextProvider;
import org.fahad.spring.entities.User;
import org.fahad.spring.entities.model.UserModel;
import org.fahad.spring.entities.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(transactionManager = "tenantTransactionManager", propagation = Propagation.REQUIRED)
public class UserManagerImpl implements UserManager{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getById(String id){
        Optional<User> u = userRepository.findById(Long.valueOf(id));
        return u.get();
    }

    @Override
    public User save(UserModel userModel) {
        User user = userModel.createUser();
        return userRepository.save(user);
    }

    @Async("asyncExecutor")
    @Override
    public CompletableFuture<User> getAsyncUser() throws InterruptedException {
        System.out.println(UserContextProvider.getTenantId());
        Thread.sleep(10*1000L);    //Intentional delay
        return CompletableFuture.completedFuture(new User());
    }
}
