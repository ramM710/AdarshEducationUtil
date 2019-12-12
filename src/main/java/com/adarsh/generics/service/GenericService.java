package com.adarsh.generics.service;

import com.adarsh.generics.dao.GenericDAO4;
import com.adarsh.generics.dao.GenericDAO4Impl;
import java.io.Serializable;
import java.util.List;

/**
 * The interface specifies all the basic methods that will be used in all the
 * services.
 *
 * It makes use of generics in a complex way. Basic Generic Tutorial
 * http://docs.oracle.com/javase/tutorial/extra/TOC.html
 *
 * @param <ID> This specifies that the model of type T should have identifier as
 * ID
 * @param <M> This implies that the model should be of type T such that it
 * extends Object
 * @param <DAO> specifies that it should be service for a genericDAO4 such that
 * The DAO should have extended GenericDAO4 with internal parameters as T,ID.
 * Hence it is a generic inside a generic limitation
 *
 * @see GenericDAO4
 * @see GenericDAO4Impl
 * @author vishal gajjar <vishal.g at emeasurematics.com>
 */
public interface GenericService<DAO extends GenericDAO4<M, ID>, M extends Object, ID extends Serializable> {

    void create(M entity);

    void merge(M entity);

    void merge(M entity, ID id);

    void merge(List<M> entity);

    void save(M entity);

    void save(List<M> entityList);

    void delete(M entity);

    void deleteById(ID id);

    void deleteByIds(List<ID> ids);

    M get(boolean isReference, ID id);

    M get(ID id);

    List<M> get(List<ID> ids);

    M load(ID id);

    List<M> getAll();

    void update(M entity);

    void refresh(M Entity);

    void clearSession();

    void deActivateViaHqlQuery(ID id);

    M initializeAndUnproxy(M entity);
}
