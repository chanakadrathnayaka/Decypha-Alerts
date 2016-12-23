package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 3/1/13
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "COMBO_BOX")
public class Option implements Serializable{

    private Long id;

    private String comboType;

    private String label;

    private String value;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VALUE", length = 45, nullable = false)
    public String getValue() {
        return value;
    }

    @Column(name = "LABEL_KEY", length = 45, nullable = false)
     public String getLabel() {
        return label;
    }

    @Column(name = "COMBO_TYPE", length = 45, nullable = false)
     public String getComboType() {
        return comboType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }
}
