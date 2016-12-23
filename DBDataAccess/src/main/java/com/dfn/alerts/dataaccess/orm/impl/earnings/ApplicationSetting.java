package com.dfn.alerts.dataaccess.orm.impl.earnings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/31/13
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "INFO_SETTINGS")
public class ApplicationSetting implements Serializable {

    public static final String TYPE = "SETTING";

    private String settingKey;

    private String value;

    private List<String> valueList = new ArrayList<String>();

    @Transient
    private static final String SETTING_DELIMITER = ",";


    @Id
    @Column(name = "SETTING_KEY", length = 200, nullable = false)
    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }


    @Column(name = "VALUE", length = 3000)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if(value != null && value.contains(SETTING_DELIMITER)){
            valueList.addAll(Arrays.asList(value.split(SETTING_DELIMITER)));
        }
    }

    @Transient
    public List<String> getValueList() {
        return valueList;
    }
}
