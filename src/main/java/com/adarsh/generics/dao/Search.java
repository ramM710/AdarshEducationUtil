package com.adarsh.generics.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

/**
 * The class provides provides the underlying framework by which the user can
 * apply various constraint that needs to be applied to a particular hibernate
 * call.
 *
 * All this filters are saved in the Search Object and then on call of
 * search(SearchIntf) in GenericDAO4Impl.
 *
 * Search basically takes simple inputs and converts the same into complex
 * hibernate query. <ol>The features that it support are:- <li>Equals
 * restrictions</li> <li>Greater Than</li> <li>greater and equal than</li>
 * <li>less than </li> <li>Less and equal than</li> <li>Is Null</li> <li>is not
 * Null</li> ....
 *
 * All the methods that it supports can be found in SearchIntf
 * <p>
 * It makes use of generics in a complex way. Basic Generic Tutorial
 * http://docs.oracle.com/javase/tutorial/extra/TOC.html </p>
 *
 * @see SearchIntf
 * @see Filter
 * @see GenericDAO4Impl
 * @author mahendra
 */
public class Search<T> implements SearchIntf<T> {

    private Class<T> classType;

    private List<Criterion> criterionList;
//    private List<SimpleExpression> criterionList;

    private ProjectionList projections;

    private List<Order> orders;

    protected boolean disjunction;

    protected int firstResult;

    protected int maxResults;

    protected int page;

    protected boolean distinct;

    protected int resultMode;

    protected List<String> filters;

    protected Map<String, String> alias;

    protected ResultTransformer resultTransformer;

    public Search(Class<T> classType) {
        this.classType = classType;
        this.criterionList = new ArrayList<Criterion>();
        this.projections = Projections.projectionList();
        this.orders = new ArrayList<Order>();
        this.filters = new ArrayList<String>();
        this.alias = new HashMap<String, String>();
    }

    @Override
    public void addFilterAnd(Filter filter) {
        criterionList.add(Restrictions.eq(filter.getColumnName(), filter.getValue()));
    }

    @Override
    public void addFilterLike(Filter filter) {
        criterionList.add(Restrictions.like(filter.getColumnName(), filter.getValue()));
    }

    @Override
    public void addFilterNull(String propertyName) {
        criterionList.add(Restrictions.isNull(propertyName));
    }

    @Override
    public void addFilterEqual(String propertyName, Object value) {
        criterionList.add(Restrictions.eq(propertyName, value));
    }

    @Override
    public void addFiltergt(String propertyName, Object value) {
        criterionList.add(Restrictions.gt(propertyName, value));
    }

    @Override
    public void addFilterlt(String propertyName, Object value) {
        criterionList.add(Restrictions.lt(propertyName, value));
    }

    @Override
    public void addFilterBetween(String propertyName, Object lo, Object hi) {
        propertyName = checkForEmbedded(propertyName);
//        criterionList.add(Restrictions.between(propertyName, lo, hi));
        criterionList.add(Restrictions.ge(propertyName, lo));
        criterionList.add(Restrictions.le(propertyName, hi));
    }

    private String checkForEmbedded(String propertyName) {
        String property = "";
        if (propertyName.contains("@")) {
            //crit.createAlias("entity.embedded", "embeddedObj");
            String[] properties = propertyName.split(".@");
            for (int i = 0; i < properties.length - 1; i++) {
                String[] prev = properties[i].split("\\.");
                String[] post = properties[i + 1].split("\\.");
                //get previous properties...
                for (int j = 0; j < prev.length - 1; j++) {
                    property = property + prev[j] + ".";
                }
                property = property + post[0] + "Obj";
                //get previous properties...
                for (int j = 1; j < post.length; j++) {
                    property = property + "." + post[j];
                }
                alias.put(prev[prev.length - 1] + "." + post[0], post[0] + "Obj");
            }
            return property;
        } else {
            return propertyName;
        }

    }

    @Override
    public void addFilterIsNull(String propertyName) {
        criterionList.add(Restrictions.isNull(propertyName));
    }

    @Override
    public List<Criterion> getCriterionList() {
        return criterionList;
    }

    @Override
    public Class<T> getClassType() {
        return classType;
    }

    @Override
    public void addSort(String propertyName, boolean desc) {
        if (desc) {
            this.orders.add(Order.desc(propertyName));
        } else {
            this.orders.add(Order.asc(propertyName));
        }
    }

    @Override
    public void setFirstResult(int i) {
        this.firstResult = i;
    }

    @Override
    public void setMaxResults(int i) {
        this.maxResults = i;
    }

    @Override
    public void addFilterGreaterOrEqual(String propertyName, Object value) {
        criterionList.add(Restrictions.ge(propertyName, value));
    }

    @Override
    public void addFilterLessOrEqual(String propertyName, Object value) {
        criterionList.add(Restrictions.le(propertyName, value));
    }

    @Override
    public void addSortDesc(String propertyName) {
        this.orders.add(Order.desc(propertyName));
    }

    @Override
    public void addField(String propertyName) {
        projections.add(Projections.property(propertyName));
    }

    @Override
    public void addFilterNotEmpty(String propertyName) {
        criterionList.add(Restrictions.isNotEmpty(propertyName));
    }

    @Override
    public void addFilterEmpty(String propertyName) {
        criterionList.add(Restrictions.isEmpty(propertyName));
    }

    @Override
    public void addFilterOr(Filter[] filters) {
        List<Criterion> orFilters = new ArrayList<Criterion>();
        for (Filter filter : filters) {
            orFilters.add(Restrictions.eq(filter.getColumnName(), filter.getValue()));
        }
        criterionList.add(Restrictions.or(orFilters.toArray(new Criterion[orFilters.size()])));
    }

    @Override
    public void addFilterLessThan(String propertyName, Object value) {
        criterionList.add(Restrictions.lt(propertyName, value));
    }

    @Override
    public void addFilterSome(String propertyName, Filter equalFilter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addFilterIn(String propertyName, List<Object> values) {
        criterionList.add(Restrictions.and(Restrictions.in(propertyName, values)));
    }

    @Override
    public void addFilterNotNull(String propertyName) {
        criterionList.add(Restrictions.isNotNull(propertyName));
    }

    @Override
    public int getFirstResult() {
        return firstResult;
    }

    @Override
    public int getMaxResults() {
        return maxResults;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public ProjectionList getProjections() {
        return projections;
    }

    @Override
    public int getResultMode() {
        return resultMode;
    }

    @Override
    public void sqlCriterion(String sql, Object[] values, Type[] types) {
        criterionList.add(Restrictions.sqlRestriction(sql, values, types));
    }

    @Override
    public void enableFilter(String filterName) {
        filters.add(filterName);
    }

    @Override
    public List<String> getFilters() {
        return filters;
    }

    @Override
    public Map<String, String> getAlias() {
        return alias;
    }

    @Override
    public void addAlias(String key, String value) {
        alias.put(key, value);
    }

    @Override
    public void addDistinct(String propertyName) {
        projections.add(Projections.distinct(Projections.property(propertyName)));
    }

    public void setResultTransformer(ResultTransformer resultTransformer) {
        this.resultTransformer = resultTransformer;
    }

    @Override
    public ResultTransformer getResultTransformer() {
        return resultTransformer;
    }
}
