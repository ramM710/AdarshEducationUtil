package com.adarsh.service;

import com.adarsh.generics.service.GenericService;
import com.adarsh.model.MemberDetails;
import com.adarsh.dao.MemberDAO;

/**
 *
 * @author Ram M
 */
public interface MemberService extends GenericService<MemberDAO, MemberDetails, Integer> {

}
