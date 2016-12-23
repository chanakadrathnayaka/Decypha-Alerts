package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.utils.CommonUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/4/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "COUNTRY_INDICATORS", uniqueConstraints = @UniqueConstraint(columnNames ={ "INDICATOR_ID" }))
public class CountryIndicatorDTO implements Serializable {

    private Integer indicatorId;
    private String instrumentTypeId;
    private String indicatorDescriptionLn;
    private String seriesTopic;
    private Map<String , String> indicatorDescriptionDes;

    @Id
    @Column(name = "INDICATOR_ID")
    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    @Column(name = "INSTRUMENT_TYPE_ID")
    public String getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(String instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    @Column(name = "INDICATOR_NAME_LAN")
    public String getIndicatorDescriptionLn() {
        return indicatorDescriptionLn;
    }

    @Column(name = "SERIES_TOPIC")
    public String getSeriesTopic() {
        return seriesTopic;
    }

    public void setSeriesTopic(String seriesTopic) {
        this.seriesTopic = seriesTopic;
    }

    public void setIndicatorDescriptionLn(String indicatorDescriptionLn) {
        this.indicatorDescriptionLn = indicatorDescriptionLn;
        this.indicatorDescriptionDes = CommonUtils.getLanguageDescriptionMap(indicatorDescriptionLn);
    }

    @Transient
    public Map<String, String> getIndicatorDescriptionDes() {
        return indicatorDescriptionDes;
    }

}
