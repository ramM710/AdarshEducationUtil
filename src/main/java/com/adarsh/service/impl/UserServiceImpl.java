package com.adarsh.service.impl;

import com.adarsh.dao.UserDAO;
import com.adarsh.generics.service.GenericServiceImpl;
import com.adarsh.model.User;
import com.adarsh.service.UserService;

/**
 *
 * @author Ram Mishra
 */
public class UserServiceImpl
        extends GenericServiceImpl<UserDAO, User, Integer>
        implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        super(userDAO);
        this.userDAO = userDAO;
    }

}
