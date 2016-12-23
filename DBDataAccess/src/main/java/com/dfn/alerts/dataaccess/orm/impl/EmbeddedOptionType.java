package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aravindal on 12/05/14.
 */
@Entity
@Table(name = "TICKER_EMBEDDED_OPTIONS")
public class EmbeddedOptionType implements Serializable {

    private Integer optionId;
    private String optionDescEN;
    private String optionDescAR;

    private Map<String,String> accessOptionDesc = new HashMap<String, String>();

    @Transient
    public Map<String, String> accessEmbeddedOptionDesc() {
        return accessOptionDesc;
    }

    @Id
    @Column( name = "OPTION_ID", length=3)
    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;

    }

    @Column( name = "OPTION_DESC_EN")
    public String getOptionDescEN() {
        return optionDescEN;
    }

    public void setOptionDescEN(String optionDescEN) {
        this.optionDescEN = optionDescEN;
        accessOptionDesc.put("EN",optionDescEN);
    }

    @Column( name = "OPTION_DESC_AR")
    public String getOptionDescAR() {
        return optionDescAR;
    }

    public void setOptionDescAR(String optionDescAR) {
        this.optionDescAR = optionDescAR;
        accessOptionDesc.put("AR",optionDescAR);
    }



}
