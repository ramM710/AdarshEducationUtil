package com.adarsh.dao.impl;

import com.adarsh.dao.UserDAO;
import com.adarsh.model.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author empc46
 */
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDAOImpl() {
        this.jdbcTemplate = null;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("");
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from test_server.user", (ResultSet rs) -> {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getString("name"), rs.getInt("mobile_no"),
                        rs.getString("address"), rs.getString("user_name"),
                        rs.getString("password")));
            }
            return users;
        });
    }

}
