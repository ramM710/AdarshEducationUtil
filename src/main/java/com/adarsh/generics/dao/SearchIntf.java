package com.adarsh.generics.dao;

import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

/**
 * The interface specifies all the method that will are supported by Search
 * logic in hibernate4 genericDao implementation. This feature simplifies the
 * complex query that are generated behind the scenes and breaks down to simple
 * search methods.
 * <p>
 * It makes use of generics in a complex way. Basic Generic Tutorial
 * http://docs.oracle.com/javase/tutorial/extra/TOC.html
 * </p>
 *
 * @see Search
 * @see GenericDAO4Impl
 * @author mahendra
 */
public interface SearchIntf<T> {

    Class<T> getClassType();

    List<Criterion> getCriterionList();

    void addFilterLike(Filter filter);

    void addFilterAnd(Filter filter);

    void addFilterNull(String propertyName);

    void addFilterEqual(String propertyName, Object value);

    void addFiltergt(String propertyName, Object value);

    void addFilterlt(String propertyName, Object value);

    void addFilterBetween(String propertyName, Object lo, Object hi);

    void addFilterIsNull(String propertyName);

    void addSort(String propertyName, boolean b);

    void setFirstResult(int i);

    void setMaxResults(int i);

    void addFilterGreaterOrEqual(String propertyName, Object value);

    void addFilterLessOrEqual(String propertyName, Object value);

    void addSortDesc(String propertyName);

    void addField(String propertyName);

    void addFilterNotEmpty(String propertyName);

    void addFilterEmpty(String propertyName);

    void addFilterOr(Filter[] filters);

    void addFilterLessThan(String propertyName, Object value);

    void addFilterSome(String propertyName, Filter equalFilter);

    void addFilterIn(String propertyName, List<Object> values);

    void addFilterNotNull(String propertyName);

    int getFirstResult();

    int getMaxResults();

    void enableFilter(String filterName);

    List<String> getFilters();

    List<Order> getOrders();

    int getPage();

    ProjectionList getProjections();

    int getResultMode();

    void sqlCriterion(String sql, Object[] values, Type[] types);

    Map<String, String> getAlias();

    void addAlias(String key, String value);

    void addDistinct(String propertyName);

    ResultTransformer getResultTransformer();
}
