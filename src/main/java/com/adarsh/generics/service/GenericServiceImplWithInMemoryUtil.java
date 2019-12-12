package com.adarsh.generics.service;

import com.adarsh.generics.dao.GenericDAO4;
import com.adarsh.generics.model.ModelIntf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahnedra Korat
 */
public abstract class GenericServiceImplWithInMemoryUtil<DAO extends GenericDAO4<M, ID>, M extends ModelIntf, ID extends Serializable>
        extends GenericServiceImpl<DAO, M, ID>
        implements GenericServiceWithInMemoryUtil<DAO, M, ID>, GenericInMemoryUtil.Callback<M, ID> {

    private final GenericInMemoryUtil<DAO, M, ID> emInMemoryUtil;

    public GenericServiceImplWithInMemoryUtil(final DAO dao) {
        super(dao);
        emInMemoryUtil = new GenericInMemoryUtil<>(this);
    }

    @Override
    public List<M> getAllFromMemory() {
        return emInMemoryUtil.getAllFromMemory(dao);
    }

    @Override
    public List<M> getAllActiveFromMemory() {
        final List<M> allFromMemory = getAllFromMemory();
        final List<M> returnList = new ArrayList<>();
        for (M m : allFromMemory) {
            if (m.isActive()) {
                returnList.add(m);
            }
        }
        return returnList;
    }

    @Override
    public void loadAllAndStoreInMemory() {
        emInMemoryUtil.loadAllAndStoreInMemory(dao);
    }

    @Override
    public M getFromMemoryById(ID id) {
        return emInMemoryUtil.getFromMemoryById(dao, id);
    }

    @Override
    public M getFromMemoryByName(String name) {
        return emInMemoryUtil.getFromMemoryByName(dao, name);
    }

    @Override
    public void updateInMemory(M m) {
        emInMemoryUtil.updateInMemory(m);
    }
}
