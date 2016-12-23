package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.constants.ApplicationSettingsKeys;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 11/15/13
 * Time: 5:43 PM
 */
public class CriteriaGenerator {

    private static final Logger LOG = LogManager.getLogger(CriteriaGenerator.class);

    //region news

    public void generateCriteria(Criteria criteria, Map<String,String> requestData){
        int newsType = Integer.parseInt(requestData.get(IConstants.CustomDataField.NEWS_FILTER_TYPE));

        setSearchCriteria(criteria,requestData);

        switch (newsType){
            case IConstants.NewsTypes.INDIVIDUALS_SANP_NEWS:
                setIndividualSnapshotCriteria(criteria);
                break;
            default:
                break;
        }

        //Set other filtering criteria
        setOtherFilteringCriteria(criteria, requestData);
    }

    /**
     * Set individual listing page news search criteria
     *
     * @param criteria criteria
     */
    private void setIndividualSnapshotCriteria(Criteria criteria) {
        criteria.add(Restrictions.isNotNull(IConstants.NewsBoColumns.INDIVIDUAL_CODE));
    }

    /**
     * Set search criteria based on the request data
     * Create a Disjunction [OR] for each param and combine them using Conjunction [AND]
     *
     * @param criteria    Criteria
     * @param requestData request data
     */
    public void setSearchCriteria(Criteria criteria, Map<String, String> requestData) {
        List<Disjunction> combineData = new ArrayList<Disjunction>();

        if (requestData.get(IConstants.MIXDataField.S) != null) {
            Disjunction symbolQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.SYMBOL, requestData.get(IConstants.MIXDataField.S));
            combineData.add(symbolQuery);
        } else if (requestData.get(IConstants.MIXDataField.CID) != null) {
            Disjunction compIdQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.COMPANY_ID, requestData.get(IConstants.MIXDataField.CID));
            combineData.add(compIdQuery);
        }
        if ((requestData.get(IConstants.MIXDataField.E)) != null) {
            Disjunction exchangeQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.EXCHANGE, requestData.get(IConstants.MIXDataField.E));
            combineData.add(exchangeQuery);
        }

        if (requestData.get(IConstants.MIXDataField.CC) != null && !(requestData.get(IConstants.CustomDataField.NEWS_FILTER_TYPE).equals(String.valueOf(IConstants.NewsTypes.GET_NEWS_BY_ID)))) {
            Disjunction countryQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.COUNTRY, requestData.get(IConstants.MIXDataField.CC));
            combineData.add(countryQuery);
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            Disjunction geoRegionCodeQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.GEO_REGION_CODE, requestData.get(IConstants.MIXDataField.GEO));
            combineData.add(geoRegionCodeQuery);
        }

        if (requestData.get(IConstants.MIXDataField.AST) != null) {
            Disjunction assetClassQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.ASSET_CLASS, requestData.get(IConstants.MIXDataField.AST));
            combineData.add(assetClassQuery);
        }

        if (requestData.get(IConstants.MIXDataField.EDT) != null) {
            Disjunction editorialCodeQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.EDITORIAL_CODE, requestData.get(IConstants.MIXDataField.EDT));
            combineData.add(editorialCodeQuery);
        }

        if (requestData.get(IConstants.MIXDataField.INDST) != null) {
            Disjunction industryCodeQuery = this.setEndLikeCriteria(IConstants.NewsBoColumns.INDUSTRY_CODE, requestData.get(IConstants.MIXDataField.INDST));
            combineData.add(industryCodeQuery);
        }

        if (requestData.get(IConstants.MIXDataField.INDV) != null) {
            Disjunction individualCodeQuery = this.setLikeWithCommaCriteria(IConstants.NewsBoColumns.INDIVIDUAL_CODE, requestData.get(IConstants.MIXDataField.INDV));
            combineData.add(individualCodeQuery);
        }

        setHotNewsCriteria(combineData, requestData.get(ApplicationSettingsKeys.NewsKeys.PRIORITY_SORT_TYPE));

        if (requestData.get(IConstants.CustomDataField.NEWS_FROM_DATE_TIME) != null && requestData.get(IConstants.CustomDataField.NEWS_FROM_DATE_TIME) != null) {
            Disjunction newsDateQuery = this.setDateBetweenCriteria(IConstants.NewsBoColumns.NEWS_DATE, requestData.get(IConstants.CustomDataField.NEWS_FROM_DATE_TIME), requestData.get(IConstants.CustomDataField.NEWS_TO_DATE_TIME));
            combineData.add(newsDateQuery);
        }

        if(requestData.get(IConstants.CustomDataField.NEWS_ID_LIST) != null){
            Disjunction idQuery = setInCriteria(IConstants.NewsBoColumns.NEWS_ID, requestData.get(IConstants.CustomDataField.NEWS_ID_LIST));
            combineData.add(idQuery);
        }

        if(requestData.get(IConstants.CustomDataField.NEWS_ID_LIST_EXCLUDE) != null){
            Disjunction newsDateQuery = this.setNotInCriteria(IConstants.NewsBoColumns.NEWS_ID, requestData.get(IConstants.CustomDataField.NEWS_ID_LIST_EXCLUDE));
            combineData.add(newsDateQuery);
        }

        if(requestData.get(IConstants.MIXDataField.SCODE) != null
                && !requestData.get(IConstants.MIXDataField.SCODE).trim().equals(String.valueOf(IConstants.NewsSourceType.ALL_NEWS_SOURCES.getDefaultValue()))){
            Disjunction newsDateQuery = this.setInCriteriaString(IConstants.NewsBoColumns.NEWS_SOURCE_ID, requestData.get(IConstants.MIXDataField.SCODE));
            combineData.add(newsDateQuery);
        }

        if (requestData.get(IConstants.MIXDataField.EDITION_ID) != null && requestData.get(IConstants.MIXDataField.SECTION_ID) != null &&
                !(requestData.get(IConstants.CustomDataField.NEWS_FILTER_TYPE).equals(String.valueOf(IConstants.NewsTypes.GET_NEWS_BY_ID)))) {
            Disjunction topNewsQuery = this.setTopNewsLikeCriteria(requestData.get(IConstants.MIXDataField.EDITION_ID),requestData.get(IConstants.MIXDataField.SECTION_ID));
            combineData.add(topNewsQuery);
            Disjunction isTopStory = this.setEqualCriteria(IConstants.NewsBoColumns.IS_TOP_STORY,1);
            combineData.add(isTopStory);
        }

        if(requestData.get(IConstants.CustomDataField.HEAD_TEXT)!=null){
            Disjunction headLineSearchQuery = this.setIgnoreCaseLikeCriteria(IConstants.NewsBoColumns.NEWS_HEADLINE_LN, requestData.get(IConstants.CustomDataField.HEAD_TEXT));
            combineData.add(headLineSearchQuery);
        }

//        String node = null;
//
//        if(requestData.get(IConstants.CustomDataField.NODE_ID).equalsIgnoreCase("NODE_1"))
//        {
//            node = IConstants.NewsBoColumns.NODE_1_STATUS;
//        } else if(requestData.get(IConstants.CustomDataField.NODE_ID).equalsIgnoreCase("NODE_2"))
//        {
//            node = IConstants.NewsBoColumns.NODE_1_STATUS;
//        }
//
//        if(node != null) {
//            Disjunction nodeStatus = this.setEqualCriteria(node, IConstants.NEWS_NODE_STATUS_NOT_AVAILABLE_IN_IMDB);
//            combineData.add(nodeStatus);
//        }

        addOracleFilter(requestData, combineData);


        if (requestData.get(IConstants.MIXDataField.L) != null) {
            Disjunction langQuery = this.setEqualCriteria(IConstants.NewsBoColumns.LANGUAGE_ID, requestData.get(IConstants.MIXDataField.L));
            combineData.add(langQuery);
        }



        //Combine all [OR] with [AND]
        Conjunction con = Restrictions.conjunction();
        for (Disjunction dis : combineData) {
            con.add(dis);
        }
        //set generated Criterion strings to criteria value
        criteria.add(con);

        //Set News status filter
        setStatusFilter(criteria);
        //
        addApprovalStatusFilter(criteria);


    }

    private void addOracleFilter(Map<String, String> requestData, List<Disjunction> combineData){
        Timestamp minNewsDate = null;
        if(requestData.containsKey(IConstants.CustomDataField.MIN_NEWS_DATE)){
            minNewsDate = Timestamp.valueOf(requestData.get(IConstants.CustomDataField.MIN_NEWS_DATE));
        }

        if(minNewsDate != null){
            Disjunction nodeFilter = this.setLessThanCriteria(IConstants.NewsBoColumns.NEWS_DATE, minNewsDate);
            combineData.add(nodeFilter);
        }
    }

    /**
     * Set Filtering criteria commonly apply for all requests
     *
     * @param criteria    generated criteria
     * @param requestData request data
     */
    private void setOtherFilteringCriteria(Criteria criteria, Map<String, String> requestData) {
        int pageIndex = 0;
        int offSet = 0;
        int imdbPageLimit = 0;
        int imdbOffSet = 0;

        if(requestData.containsKey(IConstants.SORT_FIELD) && requestData.containsKey(IConstants.SORT_ORDER)) {
            if (requestData.get(IConstants.SORT_ORDER).equalsIgnoreCase(IConstants.DEFAULT_SORT_ORDER_ASC)) {
                criteria.addOrder(Order.asc(requestData.get(IConstants.SORT_FIELD).toUpperCase()));
            }else{
                criteria.addOrder(Order.desc(requestData.get(IConstants.SORT_FIELD).toUpperCase()));
            }
        }else{
            criteria.addOrder(Order.desc(IConstants.NewsBoColumns.NEWS_DATE));
            criteria.addOrder(Order.desc(IConstants.NewsBoColumns.SEQ_ID));
        }

        if (requestData.get(IConstants.CustomDataField.PAGE_INDEX) != null) {
            pageIndex = Integer.parseInt(requestData.get(IConstants.CustomDataField.PAGE_INDEX));
            if(pageIndex>0){
                pageIndex = pageIndex -1;
            }
        }

        int newsCount = Integer.parseInt(requestData.get(ApplicationSettingsKeys.NewsKeys.COUNT));
        if (requestData.get(ApplicationSettingsKeys.NewsKeys.ORACLE_OFFSET) != null) {
            offSet = Integer.parseInt(requestData.get(ApplicationSettingsKeys.NewsKeys.ORACLE_OFFSET));
        }

        if(requestData.get(ApplicationSettingsKeys.NewsKeys.IMDB_NEWS_MAX_PAGE_COUNT)!=null){
            imdbPageLimit = Integer.parseInt(requestData.get(ApplicationSettingsKeys.NewsKeys.IMDB_NEWS_MAX_PAGE_COUNT));
        }

        pageIndex =  (imdbPageLimit < 0 || pageIndex - imdbPageLimit == 0) ? 0 : pageIndex - imdbPageLimit;

        setPaginationCriteria(criteria, pageIndex, newsCount, offSet);
    }

    /**
     * Set Hot news indicator priority
     *
     * @param combineData  Disjunction list
     * @param prioritySortType priority sort type
     */
    private void setHotNewsCriteria(List<Disjunction> combineData, String prioritySortType) {
        int sortOrder = Integer.valueOf(prioritySortType);
        Criterion criterion = null;

        switch (sortOrder){
             /*HOT_NEWS_INDICATOR NOT CONSIDERED*/
            case IConstants.NewsSortType.NEWS_SORT_DATE_DESC_ORDER:
                break;
             /*HOT_NEWS_INDICATOR IN (1,2) ORDER BY SEQ_ID DESC*/
            case IConstants.NewsSortType.NEWS_SORT_PRIORITY_DATE_DESC_ORDER:
                criterion = Restrictions.in(IConstants.NewsBoColumns.HOT_NEWS_INDICATOR, new Integer[]{1, 2});
                break;
            /*HOT_NEWS_INDICATOR =1 ORDER BY SEQ_ID DESC*/
            case IConstants.NewsSortType.NEWS_SORT_FIRST_PRIORITY_DATE_DESC_ORDER:
                criterion = Restrictions.eq(IConstants.NewsBoColumns.HOT_NEWS_INDICATOR, 1);
                break;
        }

        if(criterion != null){
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(criterion);
            combineData.add(disjunction);
        }
    }

    //endregion

    //region announcements

    public void setAnnouncementCriteria(Criteria criteria, Map<String, String> requestData) {
        List<Disjunction> combineData = new ArrayList<Disjunction>();
        IConstants.AnnouncementType announceType = IConstants.AnnouncementType.valueOf(requestData.get(IConstants.DataType.ANNOUNCEMENTS_TYPE));
        switch (announceType) {
            case EXCHANGE_SYMBOL:
                if (requestData.containsKey(IConstants.MIXDataField.S) && requestData.get(IConstants.MIXDataField.S) != null) {
                    Disjunction symbolQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.TICKER_ID, requestData.get(IConstants.MIXDataField.S));
                    combineData.add(symbolQuery);
                }
                if (requestData.containsKey(IConstants.MIXDataField.E) && requestData.get(IConstants.MIXDataField.E) != null) {
                    Disjunction exchangeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(exchangeQuery);
                }
                break;
            case EXCHANGE:
                if (requestData.containsKey(IConstants.MIXDataField.E) && requestData.get(IConstants.MIXDataField.E) != null) {
                    Disjunction exchangeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(exchangeQuery);
                }
                break;
            case GICSL2:
                if (requestData.containsKey(IConstants.MIXDataField.GICSL2) && requestData.get(IConstants.MIXDataField.GICSL2) != null) {
                    Disjunction gicsL2CodeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(gicsL2CodeQuery);
                }
                break;
            case COUNTRY:
                if (requestData.containsKey(IConstants.MIXDataField.CC) && requestData.get(IConstants.MIXDataField.CC) != null
                        && !requestData.get(IConstants.MIXDataField.CC).trim().isEmpty()) {
                    Disjunction exchangeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(exchangeQuery);
                }
                break;
            case REGION:
                if (requestData.containsKey(IConstants.MIXDataField.GEO) && requestData.get(IConstants.MIXDataField.GEO) != null &&
                    !requestData.get(IConstants.MIXDataField.GEO).trim().isEmpty()) {
                    Disjunction exchangeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(exchangeQuery);
                }
                break;
            case RELATED_ANN:
                if (requestData.get(IConstants.MIXDataField.S) != null && requestData.get(IConstants.MIXDataField.E) != null
                                            && requestData.get(DBConstants.AnnouncementDatabaseColumns.ANN_ID)!=null) {

                    Disjunction symbolQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.TICKER_ID, requestData.get(IConstants.MIXDataField.S));
                    combineData.add(symbolQuery);
                    Disjunction exchangeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(exchangeQuery);
                    Disjunction annIdQuery = setNotEqualCriteria(IConstants.AnnouncementBoColumns.ANNOUNCEMENT_ID, Integer.parseInt(requestData.get(DBConstants.AnnouncementDatabaseColumns.ANN_ID)));
                    combineData.add(annIdQuery);
                }
                break;
            case RELATED_EXCHNAGE_ANN:
                if (requestData.get(IConstants.MIXDataField.E) != null && requestData.get(DBConstants.AnnouncementDatabaseColumns.ANN_ID)!=null) {
                    Disjunction exchangeQuery = setLikeWithCommaCriteria(IConstants.AnnouncementBoColumns.SOURCE_ID, requestData.get(IConstants.MIXDataField.E));
                    combineData.add(exchangeQuery);
                    Disjunction annIdQuery = setNotEqualCriteria(IConstants.AnnouncementBoColumns.ANNOUNCEMENT_ID, Integer.parseInt(requestData.get(DBConstants.AnnouncementDatabaseColumns.ANN_ID)));
                    combineData.add(annIdQuery);
                    Disjunction symbolQuery = setIsNullCriteria(IConstants.AnnouncementBoColumns.TICKER_ID);
                    combineData.add(symbolQuery);
                }
                break;
            case ANN_BY_ID:
                if(requestData.get(IConstants.CustomDataField.ANN_ID_LIST)!= null){
                    Disjunction symbolQuery = setInCriteria(IConstants.AnnouncementBoColumns.ANNOUNCEMENT_ID, requestData.get(IConstants.CustomDataField.ANN_ID_LIST));
                    combineData.add(symbolQuery);
                }
                break;

        }

        //Combine all [OR] with [AND]
        Conjunction con = Restrictions.conjunction();
        for (Disjunction dis : combineData) {
            con.add(dis);
        }
        //set generated Criterion strings to criteria value
        criteria.add(con);

        Criterion langFilter = Restrictions.eq(IConstants.AnnouncementBoColumns.LANGUAGE_ID, requestData.get(IConstants.MIXDataField.L));
        //set language filter
        criteria.add(langFilter);

        //set other announcement filters
        setAnnouncementFilters(criteria,requestData);
    }

    /**
     *
     */
    private void setAnnouncementFilters(Criteria criteria, Map<String,String> requestData){
        int pageIndex = 0;
        int offSet = 0;
        int annCount = Integer.parseInt(requestData.get(IConstants.CustomDataField.ANNOUNCEMENT_COUNT));
        int imdbPageLimit = 0;

        //Set sort filter
        setAnnouncementSortCriteria(criteria);

        if (requestData.get(IConstants.MIXDataField.PGI) != null) {
            pageIndex = Integer.parseInt(requestData.get(IConstants.MIXDataField.PGI));
        } else {
            annCount --;
        }

        if (requestData.get(IConstants.CustomDataField.IM_M_PGI) != null) {
            imdbPageLimit = Integer.parseInt(requestData.get(IConstants.CustomDataField.IM_M_PGI));
        }

        if (requestData.get(IConstants.CustomDataField.ODB_OFFSET) != null) {
            offSet = Integer.parseInt(requestData.get(IConstants.CustomDataField.ODB_OFFSET));
        }

        int pPageIndex = (imdbPageLimit < 0 || pageIndex - imdbPageLimit == 0) ? 0 : pageIndex - imdbPageLimit - 1;

        setPaginationCriteria(criteria, pPageIndex,annCount,offSet);
    }

    /**
     * Set sort criteria
     *
     * @param criteria criteria
     */
    private void setAnnouncementSortCriteria(Criteria criteria) {
        criteria.addOrder(Order.desc(IConstants.AnnouncementBoColumns.DATE));
    }






    //endregion

    //region common

    /**
     * Create [like %,-,% or] disjunction using Criterion list
     *
     * @param columnName column name
     * @param values     values
     * @return Disjunction
     */
    private Disjunction setLikeWithCommaCriteria(String columnName, String values) {
        Disjunction disjunction = null;
        String[] valuesList;

        if (values != null && !values.trim().isEmpty()) {
            valuesList = values.split(Character.toString(IConstants.Delimiter.COMMA));
            List<Criterion> cList = new ArrayList<Criterion>();
            for (String val : valuesList) {
                String value = "%," + val + ",%";
                Criterion c = Restrictions.like(columnName, value);
                cList.add(c);
            }
            disjunction = Restrictions.disjunction();
            for (Criterion cr : cList) {
                disjunction.add(cr);
            }
        }
        return disjunction;
    }

    /**
     * Create [like %text% ] disjunction using Criterion
     *
     * @param columnName     column name
     * @param searchText     searchText
     * @return Disjunction
     */
    private Disjunction setIgnoreCaseLikeCriteria(String columnName, String searchText) {
        String searchValue = IConstants.PERCENTAGE_MARK + searchText.trim() + IConstants.PERCENTAGE_MARK;
        Criterion c = Restrictions.ilike(columnName, searchValue);  // ignore case 'ilike'
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);

        return disjunction;
    }

    /**
     * Create [like ,-% or] disjunction using Criterion list
     *
     * @param columnName column name
     * @param values     values
     * @return Disjunction
     */
    private Disjunction setEndLikeCriteria(String columnName, String values) {
        Disjunction disjunction = null;
        String[] valuesList;

        if (values != null && !values.trim().isEmpty()) {
            valuesList = values.split(Character.toString(IConstants.Delimiter.COMMA));
            List<Criterion> cList = new ArrayList<Criterion>();
            for (String val : valuesList) {
                String value = "," + val + "%";
                Criterion c = Restrictions.like(columnName, value);
                cList.add(c);
            }
            disjunction = Restrictions.disjunction();
            for (Criterion cr : cList) {
                disjunction.add(cr);
            }
        }
        return disjunction;
    }

    /**
     * Generate equal Criteria
     *
     * @param columnName column name
     * @param value      value
     * @return Disjunction
     */
    private Disjunction setEqualCriteria(String columnName, Object value) {
        Criterion c = Restrictions.eq(columnName, value);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Generate equal Criteria
     *
     * @param columnName column name
     * @param value      value
     * @return Disjunction
     */
    private Disjunction setLessThanCriteria(String columnName, Object value) {
        Criterion c = Restrictions.lt(columnName, value);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Generate not equal Criteria
     *
     * @param columnName column name
     * @param value      value
     * @return Disjunction
     */
    private Disjunction setNotEqualCriteria(String columnName, Object value) {
        Criterion c = Restrictions.ne(columnName, value);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * get a "is null" Criterion
     *
     * @param columnName String
     * @return Disjunction
     */
    private Disjunction setIsNullCriteria(String columnName) {
        Criterion c = Restrictions.isNull(columnName);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Generate not in Criteria for INT
     *
     * @param columnName column name
     * @param value      value
     * @return Disjunction
     */
    private Disjunction setInCriteria(String columnName, Object value) {
        String valueList = ((String)value);
        Criterion c = Restrictions.in(columnName, generateIntegerListFromString(valueList));
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Generate not in Criteria for STRING
     *
     * @param columnName column name
     * @param value      value
     * @return Disjunction
     */
    private Disjunction setInCriteriaString(String columnName, Object value) {
        String valueList = ((String)value);
        Criterion c = Restrictions.in(columnName, valueList.split(String.valueOf(IConstants.Delimiter.COMMA)));
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Generate not in Criteria
     *
     * @param columnName column name
     * @param value      value
     * @return Disjunction
     */
    private Disjunction setNotInCriteria(String columnName, Object value) {
        String valueList = ((String)value);
        Criterion c = Restrictions.not(Restrictions.in(columnName, generateIntegerListFromString(valueList)));
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Generate date between Criteria
     *
     * @param columnName column name
     * @param minDate    minDate
     * @param maxDate    maxDate
     * @return Disjunction
     */
    private Disjunction setDateBetweenCriteria(String columnName, String minDate, String maxDate) {
        Date startDate = CommonUtils.convertToDate(minDate);
        Date endDate = CommonUtils.convertToDate(maxDate);

        Criterion c = Restrictions.between(columnName, startDate, endDate);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    /**
     * Set pagination criteria and limit results criteria
     *
     * @param criteria     criteria
     * @param pageIndex    page index
     * @param resultsCount max results count
     * @param offSet       offset
     */
    private void setPaginationCriteria(Criteria criteria, int pageIndex, int resultsCount, int offSet) {
        LOG.info("fetching from : " + (offSet + (pageIndex * resultsCount)) + ", max results " + (resultsCount + 1) + " from oracle");
        criteria.setFirstResult(offSet + (pageIndex * resultsCount)).setMaxResults(resultsCount + 1);
    }

    /**
     * Generate Integer list from string
     *
     * @param arrStr comma separated string
     * @return
     */
    private List<Integer> generateIntegerListFromString(String arrStr) {
        String[] array = arrStr.split(Character.toString(IConstants.Delimiter.COMMA));
        List<Integer> intValues = new ArrayList<Integer>(array.length);

        for(String str:array){
            intValues.add(Integer.valueOf(str));
        }
        return intValues;
    }


    /**
     * Set Top news edtion,section like criteria
     *
     * @param topNewsEdition top news edition
     * @param topNewsSection top news section
     * @return
     */
    private Disjunction setTopNewsLikeCriteria(String topNewsEdition, String topNewsSection) {
        String topNewsLikeStr = "%("+topNewsEdition+","+topNewsSection+")%";
        Criterion c = Restrictions.like(IConstants.NewsBoColumns.TOP_NEWS_EDITION_SECTION, topNewsLikeStr);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(c);
        return disjunction;
    }

    // Exclude deleted news in search results
    public void setStatusFilter(Criteria criteria) {
        criteria.add(Restrictions.not(Restrictions.eq(IConstants.NewsBoColumns.STATUS, IConstants.DCPStatus.DELETED.getStatus())));
    }

    private void addApprovalStatusFilter(Criteria criteria){
        criteria.add(Restrictions.eq(IConstants.NewsBoColumns.APPROVAL_STATUS,Integer.parseInt(IConstants.NewsApprovalStatus.PUBLISHED.getStatus())));
    }

    //endregion
}
