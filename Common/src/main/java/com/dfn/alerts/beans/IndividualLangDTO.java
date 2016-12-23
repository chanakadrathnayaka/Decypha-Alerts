package com.dfn.alerts.beans;

import java.io.Serializable;

/**
 * Created by Danuran on 2/25/2015.
 */
public class IndividualLangDTO implements Serializable {

    private String name;
    private String individualName;
    private String prefix;

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public String getIndividualName(){
        return individualName;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
