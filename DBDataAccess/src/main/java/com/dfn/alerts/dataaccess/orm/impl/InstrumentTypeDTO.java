package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.utils.CommonUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 5/29/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "INSTRUMENT_TYPES", uniqueConstraints = @UniqueConstraint(columnNames ={ "INSTRUMENT_TYPE_ID" }))
public class InstrumentTypeDTO implements Serializable{

    private Integer assetClassId;
    private Integer instrumentTypeId;
    private String assetCLassDescription;
    private String instrumentTypeDescriptionLn;
    private Map<String , String> instrumentTypeDescriptionDes;

    @Column(name = "ASSET_CLASS_ID")
    public Integer getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Integer assetClassId) {
        this.assetClassId = assetClassId;
    }
    @Id
    @Column(name = "INSTRUMENT_TYPE_ID")
    public Integer getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(Integer instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }
    @Column(name = "ASSET_CLASS_DESCRIPTION")
    public String getAssetCLassDescription() {
        return assetCLassDescription;
    }

    public void setAssetCLassDescription(String assetCLassDescription) {
        this.assetCLassDescription = assetCLassDescription;
    }
    @Column(name = "INSTRUMENT_TYPE_DESCRIPTION")
    public String getInstrumentTypeDescriptionLn() {
        return instrumentTypeDescriptionLn;
    }

    public void setInstrumentTypeDescriptionLn(String instrumentTypeDescriptionLn) {
        this.instrumentTypeDescriptionDes = CommonUtils.getLanguageDescriptionMap(instrumentTypeDescriptionLn);
        this.instrumentTypeDescriptionLn = instrumentTypeDescriptionLn;
    }

    @Transient
    public Map<String, String> getInstrumentTypeDescriptionDes() {
        return instrumentTypeDescriptionDes;
    }

}
