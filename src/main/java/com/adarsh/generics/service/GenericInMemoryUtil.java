package com.adarsh.generics.service;

import com.adarsh.generics.dao.GenericDAO4;
import com.adarsh.generics.model.ModelIntf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahendra Korat
 */
public class GenericInMemoryUtil<DAO extends GenericDAO4<M, ID>, M extends ModelIntf, ID extends Serializable> {

    public interface Callback<M extends ModelIntf, ID extends Serializable> {

        String getName(M m);

        void removeLazyLoad(M m);

        List<M> getAllBasedOnSelectionPriority();
    }
    private volatile Object sync;

    private final Callback callback;

    private Map<ID, M> all = new LinkedHashMap<>(10);

    public GenericInMemoryUtil(Callback callback) {
        this.callback = callback;
    }

    public synchronized List<M> getAllFromMemory(DAO dao) {
        if (all.isEmpty()) {
            loadAllAndStoreInMemory(dao);
        }

        return (new ArrayList<>(all.values()));
    }

    public synchronized void loadAllAndStoreInMemory(DAO dao) {

        sync = new Object();

        // clear old values
        all.clear();

        // load new values from DB
        List<M> allValuesFromDB = callback.getAllBasedOnSelectionPriority();

        // store it based on new values
        for (M m : allValuesFromDB) {
            callback.removeLazyLoad(m);
            all.put((ID) m.getId(), m);
        }

        sync = null;
    }

    public synchronized M getFromMemoryById(DAO dao, ID id) {
        if (all.isEmpty()) {
            loadAllAndStoreInMemory(dao);
        }

        return all.get(id);
    }

    public synchronized M getFromMemoryByName(DAO dao, String name) {
        if (all.isEmpty()) {
            loadAllAndStoreInMemory(dao);
        }

        for (M m : all.values()) {
            if (callback.getName(m).equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public synchronized void updateInMemory(M m) {
        sync = new Object();
        all.put((ID) m.getId(), m);
        sync = null;
    }

}
