package com.dfn.alerts.dataaccess.dao;

import com.dfn.alerts.beans.SearchObject;
import com.dfn.alerts.beans.financial.FinancialLineItem;
import com.dfn.alerts.dataaccess.orm.impl.FinancialSegment;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Priyabashitha L.P.
 * Date: 4/22/2015
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FinancialSegmentDAO extends CommonDAO {

    SearchObject screenFinancialSegmentation(Map<String, Object> filterMap);

    /*
   * method to get financial data by company id
   */
    List<FinancialSegment> getSegmentDataByCompany(int companyId);

    /**
     * get all the financial line items, key-fieldId value-FinancialLineItem Object
     * @return map
     */
    Map<String, List<FinancialLineItem>> getFinancialLineItems();

    /**
     * get segment data
     * this will return business(segment type:6) and product(segment type:5), segment and standard segment codes
     * @return
     */
    List<Object> getFinancialSegments();

    /**
     * get all financial periods for screen
     * @return
     */
    Set<String> getFinancialPeriods();

    /**
     * get segment code along with description
     */
    Map<String,String> getBusinessActivityDescription();

    /**
     * get gics code with description
     * @return
     */
    Map<String,String> getProductDescription();

}
