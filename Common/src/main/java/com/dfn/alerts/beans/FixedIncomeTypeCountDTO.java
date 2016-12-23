package com.dfn.alerts.beans;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 5/22/13
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class FixedIncomeTypeCountDTO {

    private long count = 0;
    private int instrumentTypeId = -1;
    private int bondType = -1;
    private float outsatndingSize = -1;

    public long getCount() {

        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(int instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    public int getBondType() {
        return bondType;
    }

    public void setBondType(int bondType) {
        this.bondType = bondType;
    }

    public float getOutsatndingSize() {
        return outsatndingSize;
    }

    public void setOutsatndingSize(float outsatndingSize) {
        this.outsatndingSize = outsatndingSize;
    }
}
