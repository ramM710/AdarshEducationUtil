package com.adarsh.generics.model;

import java.io.Serializable;

/**
 * This interface will be implemented by all models that are required in system.
 * This interface provides specification of methods that should be implemented
 * by all EM-Models in order to provide required functionality.
 *
 * @author Ram Mishra
 */
public interface ModelIntf<ID> extends Serializable {

    /**
     * This method will return unique id of object
     *
     * @return: ID
     */
    ID getId();

    /**
     * This method will set the unique id of an object
     *
     * @param ID id
     */
    void setId(ID id);

    /**
     * This method will return whether object is active or not
     *
     * @return: Boolean
     */
    boolean isActive();

    /**
     * This method set the active status of an object
     *
     * @param Boolean status
     */
    void setActive(boolean value);

    @Override
    String toString();
}
