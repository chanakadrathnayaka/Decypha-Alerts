package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.dataaccess.orm.impl.FundClass;

import java.util.Comparator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 12/10/13
 * Time: 4:58 PM
 */
public class SortMapFundClassComparator implements Comparator<Map.Entry> {

    private String language;

    public SortMapFundClassComparator(String language) {
        this.language = language;
    }

    public int compare(Map.Entry o1, Map.Entry o2) {
        FundClass class1 = (FundClass)o1.getValue();
        FundClass class2 = (FundClass)o2.getValue();
        if(class1 == null || class1.getShortDescription() == null || class1.getShortDescription().get(language) == null){
            return -1;
        }
        if(class2 == null || class2.getShortDescription() == null || class2.getShortDescription().get(language) == null){
            return 1;
        }
        return (class1.getShortDescription().get(language)).compareTo(class2.getShortDescription().get(language));
    }

}
