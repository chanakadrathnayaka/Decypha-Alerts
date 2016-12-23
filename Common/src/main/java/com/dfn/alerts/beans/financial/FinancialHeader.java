package com.dfn.alerts.beans.financial;

/**
 * Created by Danuran on 4/1/2015.
 */
public class FinancialHeader {
    String head;
    FinancialStyle style;

    public FinancialHeader(String head, FinancialStyle style) {
        this.head = head;
        this.style = style;
    }

    public String getHead() {
        return head;
    }

    public FinancialStyle getStyle() {
        return style;
    }
}
