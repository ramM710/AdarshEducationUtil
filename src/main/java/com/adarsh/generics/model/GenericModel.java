package com.adarsh.generics.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Class at the top of the model hierarchy, defining properties that will be
 * inherited by all the models in the project. It represents all the entities
 * that are present in physical yard and also in consideration(hence active
 * attribute).
 *
 * @author Ram Mishra
 */
@MappedSuperclass
public class GenericModel<ID extends Serializable> implements ModelIntf<ID> {

    private static final long serialVersionUID = 1L;

    /*
     * unique Id of object in the yard
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private ID id;

    /*
     * Flag showing wheather the object is active or not
     */
    @Column(name = "active")
    private boolean active;

    public GenericModel() {
    }

    public GenericModel(boolean active) {
        this.active = active;
    }

    public GenericModel(ID id, boolean active) {
        this.id = id;
        this.active = active;
    }

    /**
     * This method will return unique id of object
     *
     * @return: ID
     */
    @Override
    public ID getId() {
        return id;
    }

    /**
     * This method will set the unique id of an object
     *
     * @param ID id
     */
    @Override
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * This method will return whether object is active or not
     *
     * @return: Boolean
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * This method set the active status of an object
     *
     * @return Boolean value
     */
    @Override
    public void setActive(boolean value) {
        this.active = value;
    }
}
