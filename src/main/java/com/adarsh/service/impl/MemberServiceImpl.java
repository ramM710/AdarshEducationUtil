package com.adarsh.service.impl;

import com.adarsh.generics.service.GenericServiceImpl;
import com.adarsh.model.MemberDetails;
import com.adarsh.dao.MemberDAO;
import com.adarsh.service.MemberService;

/**
 *
 * @author Ram M
 */
public class MemberServiceImpl
        extends GenericServiceImpl<MemberDAO, MemberDetails, Integer>
        implements MemberService {

    private final MemberDAO memberDAO;

    public MemberServiceImpl(MemberDAO memberDAO) {
        super(memberDAO);
        this.memberDAO = memberDAO;
    }
}
