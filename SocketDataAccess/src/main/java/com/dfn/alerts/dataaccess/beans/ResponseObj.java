package com.dfn.alerts.dataaccess.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hasarindat
 * Date: 11/22/12
 * Time: 11:48 AM
 */
public class ResponseObj implements Serializable{
    
    private int RT;
    private Map<String, Object> HED;
    private Map<String, Object> DAT;
    private Map<String, Object> AVG;
    private Object STAT;
    private Map<String, Object> ROW;
    private Map<String, Object> PGS;
    private Map<String, Object> PGI;
    private Map<String, Object> STYLE;
    private Map<String, Object> LONGDES;
    private Integer LTSID;
    private DataNotification[] FDN;

    public int getRT() {
        return RT;
    }

    public void setRT(int RT) {
        this.RT = RT;
    }

    public Map<String, Object> getHED() {
        return HED;
    }

    public void setHED(Map<String, Object> HED) {
        this.HED = HED;
    }

    public Object getSTAT() {
        return STAT;
    }

    public void setSTAT( Object STAT) {
        this.STAT = STAT;
    }

    public Map<String, Object> getDAT() {
        return DAT;
    }

    public void setDAT(Map<String, Object> DAT) {
        this.DAT = DAT;
    }

    public Map<String, Object> getAVG() {
        return AVG;
    }

    public void setAVG(Map<String, Object> AVG) {
        this.AVG = AVG;
    }

    public Map<String, Object> getROW() {
        return ROW;
    }

    public void setROW(Map<String, Object> ROW) {
        this.ROW = ROW;
    }

    public Map<String, Object> getPGS() {
        return PGS;
    }

    public void setPGS(Map<String, Object> PGS) {
        this.PGS = PGS;
    }

    public Map<String, Object> getSTYLE() {
        return STYLE;
    }

    public void setSTYLE(Map<String, Object> STYLE) {
        this.STYLE = STYLE;
    }

    public Integer getLTSID() {
        return LTSID;
    }

    public void setLTSID(Integer LTSID) {
        this.LTSID = LTSID;
    }

    public DataNotification[] getFDN() {
        return FDN;
    }

    public void setFDN(DataNotification[] FDN) {
        this.FDN = FDN;
    }

    public Map<String, Object> getPGI() {
        return PGI;
    }

    public void setPGI(Map<String, Object> PGI) {
        this.PGI = PGI;
    }

    public Map<String, Object> getLONGDES() {
        return LONGDES;
    }

    public void setLONGDES(Map<String, Object> LONGDES) {
        this.LONGDES = LONGDES;
    }
}
