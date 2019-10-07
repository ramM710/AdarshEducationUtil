package com.adarsh.service;

import com.adarsh.model.User;
import java.util.List;

/**
 *
 * @author empc46
 */
public interface UserService {

    void save(User user);

    List<User> getAll();
}
