package com.adarsh.model;

import com.adarsh.generics.model.GenericModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Ram
 */
@Entity
@Table(name = "menu_list")
public class MenuBarHeader extends GenericModel<Serializable> {

    @Column(name = "menu_name")
    private String menuName;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
