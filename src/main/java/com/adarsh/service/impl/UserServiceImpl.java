package com.adarsh.service.impl;

import com.adarsh.dao.UserDAO;
import com.adarsh.model.User;
import com.adarsh.service.UserService;
import java.util.List;

/**
 *
 * @author empc46
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserServiceImpl() {
        this.userDAO = null;
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

}
