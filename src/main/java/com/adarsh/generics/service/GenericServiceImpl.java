package com.adarsh.generics.service;

import com.adarsh.generics.dao.GenericDAO4;
import com.adarsh.generics.dao.GenericDAO4Impl;
import java.io.Serializable;
import java.util.List;

/**
 * The class provides implementation for the basic methods that, are specified
 * in GenericService, will be used in all the services.
 *
 * It makes use of generics in a complex way. Basic Generic Tutorial
 * http://docs.oracle.com/javase/tutorial/extra/TOC.html
 *
 * @param <ID> This specifies that the model of type T should have identifier as
 * ID
 * @param <T> This implies that the model should be of type T such that it
 * extends EmModelIntf
 * @param <DAO> specifies that it should be service for a GenericDAO4 such that
 * The DAO should have extended GenericDAO4 with internal parameters as T, ID.
 * Hence it is a generic inside a generic limitation
 *
 * @see GenericDAO4
 * @see GenericDAO4Impl
 * @author mahendra
 */
public class GenericServiceImpl<DAO extends GenericDAO4<M, ID>, M extends Object, ID extends Serializable>
        implements GenericService<DAO, M, ID> {

    protected final DAO dao;

    public GenericServiceImpl(final DAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(M entity) {
        dao.save(entity);
    }

    @Override
    public void merge(M entity) {
        dao.merge(entity);
    }

    @Override
    public void merge(M entity, ID id) {
        dao.merge(entity, id);
    }

    @Override
    public void merge(List<M> entities) {
        for (M m : entities) {
            dao.merge(m);
        }
    }

    @Override
    public void save(M entity) {
        dao.save(entity);
    }

    @Override
    public void delete(M entity) {
        dao.remove(entity);
    }

    @Override
    public M get(ID id) {
        return dao.find(id);
    }

    @Override
    public List<M> get(List<ID> ids) {
        return dao.find(ids);
    }

    @Override
    public M get(boolean isReferenced, ID id) {
        if (isReferenced) {
            return dao.getReference(id);
        }
        return dao.find(id);
    }

    @Override
    public List<M> getAll() {
        return dao.findAll();
    }

    @Override
    public void update(M entity) {
        dao.save(entity);
    }

    @Override
    public void save(List<M> entityList) {
        dao.save(entityList);
    }

    @Override
    public void refresh(M Entity) {
        dao.refresh(Entity);
    }

    @Override
    public M load(ID id) {
        return dao.load(id);
    }

    @Override
    public void clearSession() {
        dao.clearSession();
    }

    @Override
    public void deActivateViaHqlQuery(ID id) {
        dao.deActivateViaHqlQuery(id);
    }

    @Override
    public void deleteByIds(List<ID> ids) {
        dao.removeByIds(ids);
    }

    @Override
    public void deleteById(ID id) {
        dao.removeById(id);
    }

    @Override
    public M initializeAndUnproxy(M entity) {
        return dao.initializeAndUnproxy(entity);
    }
}
