package com.adarsh.generics.dao;

/**
 * This is a class that provides all different filtering mechanishm. Currently
 * supporting simple equals filters.
 *
 * @see Search
 * @author Ram Mishra
 */
public class Filter {

    private String columnName;

    private Object value;

    public Filter(String columnName, Object value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }

    /**
     * Will create a equals filter.
     *
     * @param columnName
     * @param object
     * @return
     */
    public static Filter equal(String columnName, Object object) {
        // TODO: need to store type like equal, or, gt etc....
        return new Filter(columnName, object);
    }
}
