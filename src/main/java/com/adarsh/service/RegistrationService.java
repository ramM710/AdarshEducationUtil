package com.adarsh.service;

import com.adarsh.dao.RegistrationDAO;
import com.adarsh.generics.service.GenericService;
import com.adarsh.model.MemberDetails;

/**
 *
 * @author Ram Mishra
 */
public interface RegistrationService extends GenericService<RegistrationDAO, MemberDetails, Integer> {

}
