package com.adarsh.dao.impl;

import com.adarsh.generics.dao.GenericDAO4Impl;
import com.adarsh.model.MemberDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import com.adarsh.dao.MemberDAO;
import org.hibernate.SessionFactory;

/**
 *
 * @author Ram M
 */
public class MemberDAOImpl extends GenericDAO4Impl<MemberDetails, Integer> implements MemberDAO {

    private final JdbcTemplate jdbcTemplate;

    public MemberDAOImpl(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        super.setSessionFactory(sessionFactory);
    }

    public MemberDAOImpl() {
        this.jdbcTemplate = null;
    }

}
