package com.dfn.alerts.dataaccess.beans;

/**
 * ALERTS
 * Developer chanakar
 * Date 2016-12-22 10:38
 */

/**
 * Created as an attribute of {@link ResponseObj}
 */
public class DataNotification {
    private int OT;
    private ContributionItem[] CL;
    private String ITID;
    private long TSID;

    public int getOT() {
        return OT;
    }

    public void setOT(int OT) {
        this.OT = OT;
    }

    public ContributionItem[] getCL() {
        return CL;
    }

    public void setCL(ContributionItem[] CL) {
        this.CL = CL;
    }

    public String getITID() {
        return ITID;
    }

    public void setITID(String ITID) {
        this.ITID = ITID;
    }

    public long getTSID() {
        return TSID;
    }

    public void setTSID(long TSID) {
        this.TSID = TSID;
    }
}
