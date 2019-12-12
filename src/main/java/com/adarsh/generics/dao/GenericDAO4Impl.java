package com.adarsh.generics.dao;

import com.adarsh.generics.dao.SearchIntf;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * GenericDAO4Impl provides the implementation for all the methods specified in
 * <code>GenericDAO4</code>.
 * <p/>
 * <ol>Provides Methods implementation specified in GenericDAO4 interface <li>
 * find(ID id):T => This method will find the object of T type based on id of ID
 * type. </li> <li> find(ID[] ids):T[] =>This method will find the array object
 * of T type based on array of id of ID type. </li> <li> find(List<ID>
 * ids):List<T> =>This method will find the array object of T type based on
 * array of id of ID type. </li> <li> getReference(ID id):T => This method will
 * get the reference and create a proxy of object of T type with identifier as
 * id of ID type. </li> <li> T[] getReferences(ID[] ids):T[] => This method will
 * get the array of reference and create a proxy of object of T type with
 * identifier as id of ID type. </li> <li> getReferences(List<ID> ids):List =>
 * This method will get the list of reference and create a proxy of object of T
 * type with identifier as id of ID type. </li> <li> save(T t): boolean => This
 * methods will save or update the object to database </li> <li> save(T[] ts):
 * boolean[] => This methods will save or update the list of object to
 * database</li> <li> remove(T t):boolean => This methods will remove the object
 * from database </li> <li> remove(T[] ts):void => This methods will remove the
 * list of objects from database</li> <li> removeById(ID id):boolean => This
 * methods will remove the object from database based on the id</li> <li>
 * removeByIds(ID[] ids):void => This methods will remove the list of objects
 * from database based on the id</li> <li> findAll():List<T> => This methods
 * findall the objects of the perticular type T</li> </ol>
 * <p/>
 * <p>
 * It makes use of generics in a complex way. Basic Generic Tutorial
 * http://docs.oracle.com/javase/tutorial/extra/TOC.html </p>
 *
 * @param <T> specifies any model.
 * @param <ID> specifies the identifier type.
 * <p/>
 * @author Ram Mishra
 */
@Transactional(propagation = Propagation.MANDATORY)
public class GenericDAO4Impl<T, ID extends Serializable>
        implements GenericDAO4<T, ID> {

    private String REMOVE_ALL_HQL = "delete from ";

    private String DEACTIVATE_VIA_HQL;

    public static final List<String> GET_ONLY_ACTIVE_FILTER;

    static {
        // prepare filters
        GET_ONLY_ACTIVE_FILTER = new ArrayList<>(1);
        GET_ONLY_ACTIVE_FILTER.add("getOnlyActive");
    }
    private SessionFactory sessionFactory;

    protected Class<T> classType;

    private Map<Class<T>, String> classIdPropertyNameMap;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.classType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        REMOVE_ALL_HQL += classType.getSimpleName();
        this.classIdPropertyNameMap = new HashMap<>(100);

        this.DEACTIVATE_VIA_HQL = "update " + classType
                + " set active = 0"
                + " where id = :id";
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private String getIdPropertyName(Class<T> classType) {
        String idPropertyName = classIdPropertyNameMap.get(classType);
        if (idPropertyName == null) {
            idPropertyName = sessionFactory
                    .getClassMetadata(classType)
                    .getIdentifierPropertyName();
            classIdPropertyNameMap.put(classType, idPropertyName);
        }
        return idPropertyName;
    }

    @Override
    @DeadLockRetry
    public T find(ID id) {
        return ((T) sessionFactory.getCurrentSession().get(classType, id));
    }

    /**
     * To find all the objects those have id from among the array of ids should
     * be loaded.
     *
     * Uses session with criteria to get array.
     * <p/>
     * @param ids < p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public T[] find(ID[] ids) {
        return ((T[]) sessionFactory.getCurrentSession()
                .createCriteria(classType)
                .add(Restrictions.in(getIdPropertyName(classType), ids))
                .list()
                .toArray());
    }

    /**
     * To find all the objects those have id from among the array of ids should
     * be loaded.
     *
     * Uses session with criteria to get list of all this objects.
     * <p/>
     * @param ids < p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public List<T> find(List<ID> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>(1);
        }
        return (sessionFactory.getCurrentSession()
                .createCriteria(classType)
                .add(Restrictions.in(getIdPropertyName(classType), ids))
                .list());
    }

    /**
     * This method will return a proxy reference of the particular object having
     * identifier as id.
     * <p/>
     * @param id < p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public T getReference(ID id) {
        return ((T) sessionFactory.getCurrentSession().load(classType, id));
    }

    /**
     * This method is same <code> getReference(ID id)</code> but it will return
     * array of objects.
     * <p/>
     * @param ids < p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public T[] getReferences(ID[] ids) {
        final Session session = sessionFactory.getCurrentSession();
        final T[] arrayOfT = (T[]) Array.newInstance(classType, ids.length);
        int index = 0;
        for (ID each : ids) {
            arrayOfT[index] = (T) session.load(classType, each);
            index++;
        }
        return arrayOfT;
    }

    @Override
    @DeadLockRetry
    public List<T> getReferences(List<ID> ids) {
        final Session session = sessionFactory.getCurrentSession();
        final List<T> list = new ArrayList<>(ids.size());
        for (ID each : ids) {
            list.add((T) session.load(classType, each));
        }
        return list;
    }

    /**
     * This method will save or update the entity that is passed. It will return
     * true if the entity is saved else it will return false in case of update
     * operation.
     * <p/>
     * @param t < p/>
     * @return
     */
    @Override
    @DeadLockRetry(retryCount = 3)
    public void save(T t) {
        _save(sessionFactory.getCurrentSession(), t);
    }

    private void _save(Session session, T t) {
        session.saveOrUpdate(t);
    }

    /**
     * This method will save or update the array of objects and will return true
     * if it is saved for the first time or false in case it is updated.
     *
     * @param ts
     * <p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public void save(List<T> ts) {
        final Session session = sessionFactory.getCurrentSession();
        for (T each : ts) {
            _save(session, each);
        }
    }

    @Override
    @DeadLockRetry
    public void merge(T t) {
        sessionFactory.getCurrentSession().merge(t);
    }

    @Override
    @DeadLockRetry
    public void merge(T t, ID id) {
        sessionFactory.getCurrentSession().saveOrUpdate(id.toString(), t);
    }

    /**
     * This method will remove all the objects from the session and databases.
     */
    @Override
    @DeadLockRetry
    public int removeAll() {
        return sessionFactory.getCurrentSession().createQuery(REMOVE_ALL_HQL).executeUpdate();
    }

    /**
     * This method will remove the object from the session and databases.
     *
     * @param t
     * <p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public boolean remove(T t) {
        return _remove(sessionFactory.getCurrentSession(), t);
    }

    private boolean _remove(Session session, T t) {
        session.delete(t);
        return true;
    }

    /**
     * This method will remove the set of objects from the databases
     *
     * @param ts
     */
    @Override
    @DeadLockRetry
    public void remove(List<T> ts) {
        final Session session = sessionFactory.getCurrentSession();
        for (T each : ts) {
            _remove(session, each);
        }
    }

    /**
     * This methods will remove the object that has identifier of type ID with
     * value as id. It will return true if exactly one entity was affected else
     * it will return false specifying some error.
     *
     * @param id
     * <p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public boolean removeById(ID id) {
        final Session session = sessionFactory.getCurrentSession();

        final String query = "delete from " + classType.getName()
                + " where " + sessionFactory.getClassMetadata(classType)
                        .getIdentifierPropertyName()
                + " = :id";
        final Query q = session.createQuery(query);
        q.setParameter("id", id);
        int numberOfRowsAffected = q.executeUpdate();

        //As id are unique there cannot be more then one row hence false... 
        //something went wrong....
        return (numberOfRowsAffected == 1);
    }

    /**
     * This method will remove the objects that has identifiers of type ID with
     * values as ids The method will remove all this objects.
     *
     * @param ids
     */
    @Override
    @DeadLockRetry
    public void removeByIds(List<ID> ids) {
        final Session session = sessionFactory.getCurrentSession();

        final String query = "delete from " + classType.getName()
                + " where " + sessionFactory.getClassMetadata(classType)
                        .getIdentifierPropertyName()
                + " in (:idList)";
        final Query q = session.createQuery(query);
        q.setParameterList("idList", ids);
        int numberOfRowsAffected = q.executeUpdate();

        //As id are unique there cannot be more then one row hence false... 
        //something went wrong....
        if (numberOfRowsAffected != ids.size()) {
            throw new HibernateException("more then one id match found");
        }
    }

    /**
     * This method is responsible for finding all the entities for a particular
     * type.
     *
     * @return
     */
    @Override
    @DeadLockRetry
    public List<T> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(classType).list();
    }

    /**
     * The method will check if the entity is attached or not.
     *
     * @param t
     * <p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public boolean isAttached(T t) {
        return sessionFactory.getCurrentSession().contains(t);
    }

    /**
     * This is a method to just reload the entity.
     *
     * @param t
     */
    @Override
    @DeadLockRetry
    public void refresh(T t) {
        sessionFactory.getCurrentSession().refresh(t);
    }

    /**
     * This methods reloads all the entities.
     *
     * @param ts
     */
    @Override
    @DeadLockRetry
    public void refresh(List<T> ts) {
        final Session session = sessionFactory.getCurrentSession();
        for (T each : ts) {
            session.refresh(each);
        }
    }

    /**
     * This method will flush the current session.
     */
    @Override
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    @DeadLockRetry
    public Filter getFilterFromExample(T t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <RT> List<RT> search(String query, Map<String, Object> param) {
        if (param != null) {
            for (Object object : param.values()) {
                if (object instanceof List) {
                    List objList = (List) object;
                    if (objList.isEmpty()) {
                        return new ArrayList<>();
                    }
                }
            }
        }
        return search(query, param, null, 0);
    }

    @Override
    public <RT> List<RT> search(String query, Map<String, Object> param,
            int maxRecords) {
        if (param != null) {
            for (Object object : param.values()) {
                if (object instanceof List) {
                    List objList = (List) object;
                    if (objList.isEmpty()) {
                        return new ArrayList<>();
                    }
                }
            }
        }

        return search(query, param, null, maxRecords);
    }

    @Override
    public <RT> List<RT> search(String query, Map<String, Object> param,
            List<String> filters) {
        if (param != null) {
            for (Object object : param.values()) {
                if (object instanceof List) {
                    List objList = (List) object;
                    if (objList.isEmpty()) {
                        return new ArrayList<>();
                    }
                }
            }
        }

        return search(query, param, filters, 0);
    }

    /**
     * The method will create fire the HQL query provided by the calling class
     * The expected input for the query is passed using the param object, which
     * is Map.
     * <p>
     * For example:
     * <p>
     * <code>query = select * from Customer where id = :id</code>
     * <p>
     * <code>param.add("id",123);</code>
     *
     * <p>
     * Then the function will consider that the id param has value as 123, and
     * all the customers with id as 123 will be returned.
     *
     * @param <RT>
     * @param query
     * @param param
     * <p/>
     * @return
     */
    @Override
    public <RT> List<RT> search(String query, Map<String, Object> param,
            List<String> filters, int maxRecords) {
        if (param != null) {
            for (Object object : param.values()) {
                if (object instanceof List) {
                    List objList = (List) object;
                    if (objList.isEmpty()) {
                        return new ArrayList<>();
                    }
                }
            }
        }

        final Session session = sessionFactory.getCurrentSession();

        if (null != filters) {
            for (String eachFilter : filters) {
                session.enableFilter(eachFilter);
            }
        }

        final Query q = session.createQuery(query);

        if (maxRecords > 0) {
            q.setMaxResults(maxRecords);
        }

        if (param != null) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                String key = entry.getKey();
                Object parameter = entry.getValue();
                if (parameter instanceof List) {
                    q.setParameterList(key, (List) parameter);
                } else {
                    q.setParameter(key, parameter);
                }
            }
        }

        return q.list();
    }

    /**
     * This method will use the Search class that provides mechanism to add
     * GET_ONLY_ACTIVE_FILTER based on column name and other features.
     *
     * All this GET_ONLY_ACTIVE_FILTER are then added to the criteria at the
     * Generic implementation level. Then the list of return objects are fetched
     * from the hibernate.
     *
     * @param <RT>
     * @param search
     * <p/>
     * @return
     */
    @Override
    @DeadLockRetry
    public <RT> List<RT> search(SearchIntf search) {
        final Session session = sessionFactory.getCurrentSession();

        final Criteria c = session.createCriteria(search.getClassType());

        if (search.getProjections().getLength() != 0) {
            //apply the projection
            c.setProjection(search.getProjections());
        }

        //apply orders
        final List<Order> ord = search.getOrders();
        for (Order order : ord) {
            c.addOrder(order);
        }

        //apply alias..
        final Map<String, String> alias = search.getAlias();
        for (Map.Entry<String, String> entry : alias.entrySet()) {
            c.createAlias(entry.getKey(), entry.getValue());
        }

        //apply criterion
        final List<Criterion> list = search.getCriterionList();
        for (Criterion each : list) {
            c.add(each);
        }

        //enable Filters
        final List<String> filters = search.getFilters();
        for (String eachFilter : filters) {
            session.enableFilter(eachFilter);
        }

        if (search.getFirstResult() != 0) {
            //set first row
            c.setFirstResult(search.getFirstResult());
        }

        if (search.getMaxResults() != 0) {
            //setmaxresults
            c.setMaxResults(search.getMaxResults());
        }

        if (search.getResultTransformer() != null) {
            c.setResultTransformer(search.getResultTransformer());
        }

        return c.list();
    }

    @Override
    @DeadLockRetry
    public int count(SearchIntf search) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria c = session.createCriteria(search.getClassType());

        final ProjectionList projectionList = search.getProjections();
        projectionList.add(Projections.rowCount());
        c.setProjection(projectionList);

        //apply orders
        final List<Order> orders = search.getOrders();
        for (Order order : orders) {
            c.addOrder(order);
        }

        //apply criterion
        final List<Criterion> list = search.getCriterionList();
        for (Criterion each : list) {
            c.add(each);
        }

        if (search.getFirstResult() != 0) {
            //set first result
            c.setFirstResult(search.getFirstResult());
        }

        if (search.getMaxResults() != 0) {
            //setmaxresults
            c.setMaxResults(search.getMaxResults());
        }

        return ((Integer) c.uniqueResult());
    }

    @Override
    public int executeUpdate(String hqlQuery, Map<String, Object> param) {
        final Session session = sessionFactory.getCurrentSession();
        final Query query = session.createQuery(hqlQuery);
        String key;
        Object parameter;
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            key = entry.getKey();
            parameter = entry.getValue();
            if (parameter instanceof List) {
                query.setParameterList(key, (List) parameter);
            } else {
                query.setParameter(key, parameter);
            }
        }

        final Integer returnVal = query.executeUpdate();
        //flushing the session
        session.flush();
        return returnVal;
    }

//    public Session getOpenSession() {
//        return sessionFactory.getCurrentSession();
////        return SessionFactoryUtils.openSession(sessionFactory);
//    }
//
//    private void closeSession(Session session) throws HibernateException {
////        session.flush();
////        session.close();
//    }
    @Override
    @DeadLockRetry
    public T load(ID id) {
        return ((T) sessionFactory.getCurrentSession().load(classType, id));
    }

    @Override
    public void clearSession() {
        sessionFactory.getCurrentSession().clear();
    }

    @Override
    public void deActivateViaHqlQuery(ID id) {
        final Map<String, Object> inputs = new HashMap(2);
        inputs.put("id", id);

        executeUpdate(DEACTIVATE_VIA_HQL, inputs);
    }

    public T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }
}
