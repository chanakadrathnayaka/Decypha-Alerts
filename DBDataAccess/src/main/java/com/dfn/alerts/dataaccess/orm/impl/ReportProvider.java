package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.constants.IConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/20/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "REPORT_PROVIDER")
public class ReportProvider implements Serializable {

    private String id;
    private String desc;

    private Map<String,String> description = null;

    @Id
    @Column(name = "id",length = 100, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "description", length = 100, nullable = false)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;

        if(desc != null && desc.contains(Character.toString(IConstants.Delimiter.VL))){
            this.description = new HashMap<String, String>(4);
            StringTokenizer strTkn = new StringTokenizer(desc, Character.toString(IConstants.Delimiter.VL));

            while (strTkn.hasMoreTokens()){

                String [] keyVal = strTkn.nextToken().split(":");

                if(keyVal != null && keyVal.length == 2)
                {
                    this.description.put(keyVal[0],keyVal[1]);
                }

            }
        }
    }

    @Transient
    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }
}
