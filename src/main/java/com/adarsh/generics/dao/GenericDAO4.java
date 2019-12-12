package com.adarsh.generics.dao;

import com.adarsh.generics.dao.SearchIntf;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Filter;

/**
 * This interface provides all the basic methods that will be required while
 * saving entity. This is used such that any DAO that needs to use this methods
 * can use it and the implementation will be present in
 * <code>GenericDAO4Impl.java</code>.
 * <p/>
 * <ol>Provides Basic methods</p> <li> find(ID id):T => This method will find
 * the object of T type based on id of ID type. </li> <li> find(ID[] ids):T[]
 * =>This method will find the array object of T type based on array of id of ID
 * type. </li> <li> find(List<ID> ids):List<T> =>This method will find the array
 * object of T type based on array of id of ID type. </li> <li> getReference(ID
 * id):T => This method will get the reference and create a proxy of object of T
 * type with identifier as id of ID type. </li> <li> T[] getReferences(ID[]
 * ids):T[] => This method will get the array of reference and create a proxy of
 * object of T type with identifier as id of ID type. </li> <li>
 * getReferences(List<ID> ids):List => This method will get the list of
 * reference and create a proxy of object of T type with identifier as id of ID
 * type. </li> <li> save(T t): boolean => This methods will save or update the
 * object to database </li> <li> save(T[] ts): boolean[] => This methods will
 * save or update the list of object to database</li> <li> remove(T t):boolean
 * => This methods will remove the object from database </li> <li> remove(T[]
 * ts):void => This methods will remove the list of objects from database</li>
 * <li> removeById(ID id):boolean => This methods will remove the object from
 * database based on the id</li> <li> removeByIds(ID[] ids):void => This methods
 * will remove the list of objects from database based on the id</li> <li>
 * findAll():List<T> => This methods findall the objects of the perticular type
 * T</li> </ol>
 * <p/>
 * <p>
 * It makes use of generics in a complex way. Basic Generic Tutorial
 * http://docs.oracle.com/javase/tutorial/extra/TOC.html
 * </p>
 *
 * @param <T> specifies any model.
 * @param <ID> specifies the identifier type.
 * <p/>
 * @see GenericDAO4Impl
 * @version 1.0
 * @author Ram Mishra
 */
public interface GenericDAO4<T, ID extends Serializable> {

    T load(ID id);

    T find(ID id);

    T[] find(ID[] ids);

    List<T> find(List<ID> ids);

    T getReference(ID id);

    T[] getReferences(ID[] ids);

    List<T> getReferences(List<ID> ids);

    void save(T t);

    void save(List<T> ts);

    void merge(T t);

    void merge(T t, ID id);

    int removeAll();

    boolean remove(T t);

    void remove(List<T> ts);

    boolean removeById(ID id);

    void removeByIds(List<ID> ids);

    List<T> findAll();

    int count(SearchIntf search);

    boolean isAttached(T t);

    void refresh(T t);

    void refresh(List<T> ts);

    void flush();

    Filter getFilterFromExample(T t);

    <RT extends Object> List<RT> search(String query, Map<String, Object> param);

    <RT extends Object> List<RT> search(String query, Map<String, Object> param, int maxRecords);

    <RT extends Object> List<RT> search(String query, Map<String, Object> param, List<String> filters);

    <RT extends Object> List<RT> search(String query, Map<String, Object> param, List<String> filters, int maxRecords);

    <RT extends Object> List<RT> search(SearchIntf s);

    int executeUpdate(String query, Map<String, Object> param);

    void clearSession();

    void deActivateViaHqlQuery(ID id);

    T initializeAndUnproxy(T entity);
}
