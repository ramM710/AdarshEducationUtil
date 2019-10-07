package com.adarsh.dao;

import com.adarsh.model.User;
import java.util.List;

/**
 *
 * @author empc46
 */
public interface UserDAO {

    void save(User user);

    List<User> getAll();
}
