package com.adarsh.generics.service;

import com.adarsh.generics.dao.GenericDAO4;
import com.adarsh.generics.model.ModelIntf;
import java.io.Serializable;
import java.util.List;

/**
 * @author Mahendra Korat
 */
public interface GenericServiceWithInMemoryUtil<DAO extends GenericDAO4<M, ID>, M extends ModelIntf, ID extends Serializable>
        extends GenericService<DAO, M, ID> {

    List<M> getAllFromMemory();

    List<M> getAllActiveFromMemory();

    void loadAllAndStoreInMemory();

    M getFromMemoryById(ID id);

    void updateInMemory(M m);

    M getFromMemoryByName(String name);
}
