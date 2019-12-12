package com.adarsh.util;

import com.adarsh.model.MemberDetails;
import com.adarsh.model.User;
import com.adarsh.service.MemberService;
import com.adarsh.service.UserService;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author Ram Mishra
 */
public class DataExecutor {

    private final UserService userService;

    private final MemberService memberService;

    private final JdbcTemplate jdbcTemplate;

    private final PlatformTransactionManager transactionManager;

    public DataExecutor(UserService userService,
            MemberService memberService,
            JdbcTemplate jdbcTemplate,
            PlatformTransactionManager transactionManager) {
        this.userService = userService;
        this.memberService = memberService;
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    public Boolean validateLogin(String userName, String userPassword) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute((TransactionStatus ts) -> {
            List<User> users = userService.getAll();
            if (!users.isEmpty()) {
                for (User user : users) {
                    if (user.getUserName().equals(userName) && user.getPassword().equals(userPassword)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    public void addNewMember(MemberDetails memberDetail) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus ts) {
                memberService.save(memberDetail);
            }
        });
    }
}
