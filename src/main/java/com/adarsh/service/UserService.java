package com.adarsh.service;

import com.adarsh.dao.UserDAO;
import com.adarsh.generics.service.GenericService;
import com.adarsh.model.User;

/**
 *
 * @author empc46
 */
public interface UserService extends GenericService<UserDAO, User, Integer> {

}
