package org.fahad.spring.manager;

import org.fahad.spring.entities.User;
import org.fahad.spring.entities.model.UserModel;

public interface UserManager {
    User getById(String id);

    User save(UserModel userModel);
}
