package com.adarsh.dao.impl;

import com.adarsh.dao.UserDAO;
import com.adarsh.generics.dao.GenericDAO4Impl;
import com.adarsh.model.User;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ram M
 */
public class UserDAOImpl extends GenericDAO4Impl<User, Integer> implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        super.setSessionFactory(sessionFactory);
    }

    public UserDAOImpl() {
        this.jdbcTemplate = null;
    }

}
