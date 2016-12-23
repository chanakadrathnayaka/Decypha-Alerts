package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.constants.ApplicationSettingsKeys;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.NewsDatabaseColumns.*;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 4/19/13
 * Time: 12:20 PM
 */
public class NewsQueryGenerator {


    private static final Logger LOG = LogManager.getLogger(NewsQueryGenerator.class);

    private static final String QUERY_WHERE = DBConstants.CommonDatabaseParams.QUERY_WHERE;
    private static final String QUERY_AND = DBConstants.CommonDatabaseParams.QUERY_AND;
    private static final String QUERY_ORDER = DBConstants.CommonDatabaseParams.QUERY_ORDER;
    private static final String QUERY_BRACKET_OPEN = DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN;
    private static final String QUERY_BRACKET_CLOSE = DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE;
    private static final String QUERY_COMMA = DBConstants.CommonDatabaseParams.QUERY_COMMA;
    private static final String QUERY_EQUAL = DBConstants.CommonDatabaseParams.QUERY_EQUAL;
    private static final String QUERY_NOT_NULL = DBConstants.CommonDatabaseParams.QUERY_NOT_NULL;
    private static final String QUERY_LIKE = DBConstants.CommonDatabaseParams.QUERY_LIKE;
    private static final String QUERY_PREC = DBConstants.CommonDatabaseParams.QUERY_PREC;
    private static final String QUERY_IN = DBConstants.CommonDatabaseParams.QUERY_IN;
    private static final String QUERY_NOT_IN = DBConstants.CommonDatabaseParams.QUERY_NOT_IN;
    public static final String DESC_ORDER = DBConstants.CommonDatabaseParams.QUERY_DESC_ORDER;
    public static final String SQL_QUESTION_MARK = DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;
    public static final String QUERY_QUOTE = DBConstants.CommonDatabaseParams.QUERY_QUOTE;

    private static final String QUERY_IND_CODE = INDIVIDUAL_CODE;

    private static final String SQL_PLACEHOLDER = DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK;
    private static final String QUERY_NEWS = "SELECT * FROM NEWS ";
    private static final String UNION_ALL = " UNION ALL ";
    private static final String AS = " AS ";
    private static final String SECTION_TYPE = " SECTION_TYPE ";
    private static final String SELECT_ALL_FROM = DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM;
    private static final String SELECT_COUNT_FROM = DBConstants.CommonDatabaseParams.QUERY_SELECT_COUNT_ALL_FROM;
    private static final String PAGINATION_ROWNUM = " ROWNUM <= ";
    public static final int PLACE_HOLDER_LENGTH = 500;

    public static final String TOP_NEWS_HISTORY = DBConstants.TablesORACLE.TOP_NEWS_HISTORY;
    public static final String QUERY_TOP_STORY_TO_BE_UPDATED =  DBConstants.CommonDatabaseParams.QUERY_TOP_STORY_TO_BE_UPDATED;
    public static final String QUERY_UPDATE =  DBConstants.CommonDatabaseParams.QUERY_UPDATE;
    public static final String TABLE_NEWS =  DBConstants.TablesORACLE.TABLE_NEWS;
    public static final String QUERY_SET =  DBConstants.CommonDatabaseParams.QUERY_SET;
    public static final String EQUAL_QUETION_MARK =  DBConstants.CommonDatabaseParams.EQUAL_QUETION_MARK;
    public static final String TOP_NEWS_EDITION_SECTION =  DBConstants.NewsDatabaseColumns.TOP_NEWS_EDITION_SECTION;
    public static final String QUERY_TOP_STORY_UPDATED =  DBConstants.CommonDatabaseParams.QUERY_TOP_STORY_UPDATED;
    public static final String QUERY_TOP_STORY =  DBConstants.CommonDatabaseParams.QUERY_TOP_STORY;
    public static final String QUERY_GREATER_THAN = DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN;
    public static final String TOP_STORY_SYNC_TIME = DBConstants.NewsDatabaseColumns.TOP_STORY_SYNC_TIME;
    public static final String PREVIOUS_DAY = "SYSDATE-1";

    private static final String SPLIT_ASTERIX = "\\*";

    private static final String CUSTOM_SELECT = DBConstants.CommonDatabaseParams.QUERY_SELECT +
            NEWS_ID + QUERY_COMMA +
            SEQ_ID + QUERY_COMMA +
            EXT_ID + QUERY_COMMA +
            NEWS_PROVIDER + QUERY_COMMA +
            ASSET_CLASS + QUERY_COMMA +
            NEWS_DATE + QUERY_COMMA +
            COUNTRY + QUERY_COMMA +
            HOT_NEWS_INDICATOR + QUERY_COMMA +
            TICKER_ID + QUERY_COMMA +
            SOURCE_ID + QUERY_COMMA +
            LAST_UPDATED_TIME + QUERY_COMMA +
            STATUS + QUERY_COMMA +
            LANGUAGE_ID + QUERY_COMMA +
            NEWS_HEADLINE_LN + QUERY_COMMA +
            EXCHANGE + QUERY_COMMA +
            SYMBOL + QUERY_COMMA +
            EDITORIAL_CODE + QUERY_COMMA +
            GEO_REGION_CODE + QUERY_COMMA +
            GOVERNMENT_CODE + QUERY_COMMA +
            INDIVIDUAL_CODE + QUERY_COMMA +
            INDUSTRY_CODE + QUERY_COMMA +
            MARKET_SECTOR_CODE + QUERY_COMMA +
            PRODUCT_SERVICES_CODE + QUERY_COMMA +
            INTERNAL_CLASS_TYPE + QUERY_COMMA +
            APPROVAL_STATUS + QUERY_COMMA +
            URL + QUERY_COMMA +
            UNIQUE_SEQUENCE_ID + QUERY_COMMA +
            COMPANY_ID + QUERY_COMMA +
            NEWS_SOURCE_ID + QUERY_COMMA +
            NEWS_SOURCE_DESC + QUERY_COMMA +
            IS_TOP_STORY + QUERY_COMMA +
            TOP_NEWS_EDITION_SECTION  + QUERY_COMMA+
            LAST_SYNC_TIME;

    private static final String NEWS_MAX_SEQ_ID_QUERY = "SELECT MAX(" + SEQ_ID +
            ") AS SEQ_ID_MAX FROM NEWS WHERE ";

    private static final String NEWS_INSERT_QUERY = "INSERT INTO NEWS (";

    private static final String NEWS_SEARCH_DERBY_DB_UPDATE_QUERY = "SELECT * FROM NEWS WHERE LANGUAGE_ID=? ORDER BY NEWS_DATE DESC ,SEQ_ID DESC  OFFSET ? ROWS";

    public static final String NEWS_EOD_IMDB_DELETE               = "DELETE FROM NEWS WHERE SEQ_ID <= ? AND LANGUAGE_ID= ? ";

    public static final String DELETE_FROM_NEWS_WHERE             = "DELETE FROM NEWS WHERE ";

    public static final String DELETE_DELETED_NEWS                = "DELETE FROM NEWS WHERE NEWS_ID in ({0}) AND LANGUAGE_ID = ?";

    private static final String NEWS_SEARCH_H2_DB_UPDATE_QUERY    = "SELECT * FROM NEWS WHERE LANGUAGE_ID=? ORDER BY NEWS_DATE DESC ,SEQ_ID DESC  OFFSET ?";

    private static final int DEFAULT_NEWS_COUNT = 10;
    private static final int ZERO_LENGTH = 0;
    private static final String EDT_CODES_MACRO_ECONOMY_NEWS = "26,55";
    // Value represents HTIND = 1 in NEWS
    public static final int FIRST_PRIORITY_HOT_NEWS_VALUE = 1;
    // Value represents HTIND = 1 OR 2 in NEWS
    public static final String HOT_NEWS_VALUES = " (1,2) " ;

    private static final String DATE_TIME_FORMAT = IConstants.DateFormats.FORMAT5;

    private int dbType = DBConstants.DatabaseTypes.DB_TYPE_H2;

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    /**
     * get equity news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getEquityNewsQuery(Map<String, String> requestData) /*throws IllegalArgumentException */ {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        if (requestData.get(IConstants.MIXDataField.S) != null && requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(getExchangeSymbolFilterQuery(requestData.get(IConstants.MIXDataField.S),
                    requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(getAssetClassFilterQuery(Integer.toString(IConstants.AssetType.EQUITY.getDefaultValue())));
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(getAssetClassFilterQuery(Integer.toString(IConstants.AssetType.EQUITY.getDefaultValue())));
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(getAssetClassFilterQuery(Integer.toString(IConstants.AssetType.EQUITY.getDefaultValue())));
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        } else if (requestData.get(IConstants.MIXDataField.CID) != null) {
            queryBuilder.append(getCompanyIdFilterQuery(requestData.get(IConstants.MIXDataField.CID)));
        } else if(requestData.get(IConstants.MIXDataField.TSID) != null){
            queryBuilder.append(getTickerSerialFilterQuery(requestData.get(IConstants.MIXDataField.TSID)));
        }
        return queryBuilder.toString();
    }

    /**
     * get macro economy news query
     * select * from NEWS where NEWS.COUNTRY IN ('US','EG') AND NEWS.EDITORIAL_CODE IN ('aa','bb')
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getMacroEconomyNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getEditorialCodeFilterQuery(requestData.get(IConstants.MIXDataField.EDT)));
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }
        return queryBuilder.toString();
    }

    /**
     * get fixed income news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getFixedIncomeNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getAssetClassFilterQuery(requestData.get(IConstants.MIXDataField.AST)));
        if (requestData.get(IConstants.MIXDataField.S) != null && requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getExchangeSymbolFilterQuery(requestData.get(IConstants.MIXDataField.S), requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }
        return queryBuilder.toString();
    }

    /**
     * get treasury bills news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getMoneyMarketNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getAssetClassFilterQuery(requestData.get(IConstants.MIXDataField.AST)));
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }
        //TODO add filter according to company id
        return queryBuilder.toString();
    }

    /**
     * get commodity news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getCommodityNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getAssetClassFilterQuery(requestData.get(IConstants.MIXDataField.AST)));
        if (requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }

        return queryBuilder.toString();
    }

    /**
     * get mutual funds news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getFundsNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getAssetClassFilterQuery(requestData.get(IConstants.MIXDataField.AST)));
        if (requestData.get(IConstants.MIXDataField.S) != null && requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getExchangeSymbolFilterQuery(requestData.get(IConstants.MIXDataField.S), requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
        } else if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }
        return queryBuilder.toString();
    }

    /**
     * get country news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getCountryNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        }
        return queryBuilder.toString();
    }

    /**
     * get currency news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getCurrencyNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getAssetClassFilterQuery(requestData.get(IConstants.MIXDataField.AST)));
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        }
        return queryBuilder.toString();
    }

    /**
     * get industry news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getIndustryNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder();
        if (requestData.get(IConstants.MIXDataField.INDST) != null) {
            queryBuilder.append(QUERY_NEWS);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(INDUSTRY_CODE);
            queryBuilder.append(QUERY_LIKE);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(requestData.get(IConstants.MIXDataField.INDST));
            queryBuilder.append(QUERY_PREC);
            queryBuilder.append(QUERY_QUOTE);
        } else {
            throw new IllegalArgumentException("Industry code can't be null for this type of filter.");
        }
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }
        return queryBuilder.toString();
    }

    /**
     * get sector news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getSectorNewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(MARKET_SECTOR_CODE);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(requestData.get(IConstants.MIXDataField.MKT));
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
        queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
        }
        return queryBuilder.toString();
    }

    /**
     * get individual news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getIndividualNews(Map<String, String> requestData) {
        StringBuilder queryBuilder;
        String query;

        if(requestData.get(IConstants.MIXDataField.INDV)!=null){
            queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);

            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
            queryBuilder.append(getLikeOrQuery(Arrays.asList(requestData.get(IConstants.MIXDataField.INDV)), INDIVIDUAL_CODE));
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);

            if (requestData.get(IConstants.MIXDataField.CC) != null) {
                queryBuilder.append(QUERY_AND);
                queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
            }

            if (requestData.get(IConstants.MIXDataField.SCODE) != null
                    && !requestData.get(IConstants.MIXDataField.SCODE).trim().equals(String.valueOf(IConstants.NewsSourceType.ALL_NEWS_SOURCES.getDefaultValue()))) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
                queryBuilder.append(getNewsSourceQuery(requestData.get(IConstants.MIXDataField.SCODE)));
            }

            query = queryBuilder.toString();

        }else{
            throw new IllegalArgumentException("Individual code can't be null for this type of news filter");
        }

        return query;
    }

    /**
     * get all news query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getAllNews(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        boolean isFirstFilter = true;

        if (requestData.get(IConstants.MIXDataField.S) != null && requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(getExchangeSymbolFilterQuery(requestData.get(IConstants.MIXDataField.S), requestData.get(IConstants.MIXDataField.E)));
            isFirstFilter = false;
        }

        if (requestData.get(IConstants.MIXDataField.E) != null) {
            queryBuilder.append(getExchangeFilterQuery(requestData.get(IConstants.MIXDataField.E)));
            isFirstFilter = false;
        }

       /* if (requestData.get(IConstants.MIXDataField.CID) != null) {
            queryBuilder.append(getCompanyIdFilterQuery(requestData.get(IConstants.MIXDataField.CID)));
            isFirstFilter = false;
        }

        if(requestData.get(IConstants.MIXDataField.INDV) != null){
            queryBuilder.append(getIndividualFilterQuery(requestData.get(IConstants.MIXDataField.INDV)));
            isFirstFilter = false;
        }*/

        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            if (!isFirstFilter) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            }
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
            isFirstFilter = false;
        } else if (requestData.get(IConstants.MIXDataField.GEO) != null) {
            if (!isFirstFilter) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            }
            queryBuilder.append(getRegionFilterQuery(requestData.get(IConstants.MIXDataField.GEO)));
            isFirstFilter = false;
        }

        if(requestData.get(IConstants.CustomDataField.HEAD_TEXT)!=null){
            if (!isFirstFilter) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            }
            queryBuilder.append(getHeadLineSearchQuery(requestData.get(IConstants.CustomDataField.HEAD_TEXT)));
            isFirstFilter = false;
        }

        if (requestData.get(IConstants.MIXDataField.SCODE) != null
                && !requestData.get(IConstants.MIXDataField.SCODE).trim().equals(String.valueOf(IConstants.NewsSourceType.ALL_NEWS_SOURCES.getDefaultValue()))) {
            if (!isFirstFilter) {
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
            }
            queryBuilder.append(getNewsSourceQuery(requestData.get(IConstants.MIXDataField.SCODE)));
        }

        return queryBuilder.toString();
    }

    /**
     * get individual news by country query
     *
     * @param requestData filter params
     * @return query string
     */
    public static String getIndividualsNewsByCountry(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(QUERY_IND_CODE);
            queryBuilder.append(QUERY_NOT_NULL);
        }else{
            throw new IllegalArgumentException("Country code can't be nulll for this type of news filter");
        }
        return queryBuilder.toString();
    }

    /**
     * filter by editorial query query
     *
     * @param editorialCodes editorial codes
     * @return query string
     */
    private static String getEditorialCodeFilterQuery(String editorialCodes) {
        StringBuilder queryBuilder = new StringBuilder();
        String[] editorialCodeList = editorialCodes.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        queryBuilder.append(getLikeOrQuery(Arrays.asList(editorialCodeList), EDITORIAL_CODE));
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);

        return queryBuilder.toString();
    }

    /**
     * filter by asset class
     * @param assetClass asset class
     * @return query
     */
    private static String getAssetClassFilterQuery(String assetClass) {
        StringBuilder strBuilder = new StringBuilder(ASSET_CLASS);
        strBuilder.append(QUERY_LIKE);
        strBuilder.append(QUERY_QUOTE);
        strBuilder.append(QUERY_PREC);
        strBuilder.append(QUERY_COMMA);
        strBuilder.append(assetClass);
        strBuilder.append(QUERY_COMMA);
        strBuilder.append(QUERY_PREC);
        strBuilder.append(QUERY_QUOTE);
        return strBuilder.toString();
    }

    /**
     * filter by symbol
     * @param symbol ticker_id
     * @param exchange source_id
     * @return query
     */
    private static String getExchangeSymbolFilterQuery(String symbol, String exchange) {
        String[] exchangeList;
        StringBuilder strBuilder = new StringBuilder(SYMBOL);
        strBuilder.append(QUERY_LIKE).append(QUERY_QUOTE).append(QUERY_PREC).append(QUERY_COMMA);
        strBuilder.append(symbol);
        strBuilder.append(QUERY_COMMA).append(QUERY_PREC).append(QUERY_QUOTE);

        strBuilder.append(QUERY_AND);

        exchangeList = exchange.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(getLikeOrQuery(Arrays.asList(exchangeList), EXCHANGE));

        return strBuilder.toString();
    }

    /**
     * filter by exchange
     * @param exchange exchange
     * @return query (EXCHANGE like '%,LKCSE,%' OR EXCHANGE like '%,DFM,%')
     */
    private static String getExchangeFilterQuery(String exchange) {
        StringBuilder strBuilder = new StringBuilder();
        String[] exchangeList = exchange.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        strBuilder.append(getLikeOrQuery(Arrays.asList(exchangeList), EXCHANGE));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return strBuilder.toString();
    }


    /**
     * filter by individual
     * @param inv individual
     * @return query (INDV like '%,125,%' OR INDV like '%,156,%')
     */
    private static String getIndividualFilterQuery(String inv) {
        StringBuilder strBuilder = new StringBuilder();
        String[] indvList = inv.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        strBuilder.append(getLikeOrQuery(Arrays.asList(indvList), INDIVIDUAL_CODE));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return strBuilder.toString();
    }

    /**
     * Filter news by company id
     *
     * @param companyId comma separated company ids
     * @return query (COMPANY_ID like '%,28197,%') OR (COMPANY_ID like '%,30000,%')
     */
    private static String getCompanyIdFilterQuery(String companyId) {
        StringBuilder strBuilder = new StringBuilder();
        String[] idArray = companyId.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        strBuilder.append(getLikeOrQuery(Arrays.asList(idArray), COMPANY_ID));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return strBuilder.toString();
    }
    /**
     * Filter news by company id
     *
     * @param tickerSerial comma separated ticker serials
     * @return query (TICKER_ID like '%,28197,%') OR (TICKER_ID like '%,30000,%')
     */
    private static String getTickerSerialFilterQuery(String tickerSerial) {
        StringBuilder strBuilder = new StringBuilder();
        String[] idArray = tickerSerial.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        strBuilder.append(getLikeOrQuery(Arrays.asList(idArray), TICKER_ID));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return strBuilder.toString();
    }

    /**
     * Search headline by text
     *
     * @param searchString search text
     * @return query
     */
    public static String getHeadLineSearchQuery(String searchString) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_UPPER)
                .append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN)
                .append(NEWS_HEADLINE_LN)
                .append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE)
                .append(DBConstants.CommonDatabaseParams.QUERY_LIKE)
                .append(DBConstants.CommonDatabaseParams.QUERY_QUOTE)
                .append(DBConstants.CommonDatabaseParams.QUERY_PREC)
                .append(searchString.trim().toUpperCase())
                .append(DBConstants.CommonDatabaseParams.QUERY_PREC)
                .append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);

        return queryBuilder.toString();
    }

    /**
     * filter by countries
     * @param countries comma seperated country codes
     * @return query
     */
    private static String getCountryFilterQuery(String countries) {
        StringBuilder strBuilder = new StringBuilder();
        String[] countryList = countries.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        strBuilder.append(getLikeOrQuery(Arrays.asList(countryList), COUNTRY));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return strBuilder.toString();
    }

    /**
     * filter by region(filtered by using the countries in the given region)
     * @param region region
     * @return query
     */
    private static String getRegionFilterQuery(String region) {
        StringBuilder strBuilder = new StringBuilder();
        String[] countryList = region.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        strBuilder.append(getLikeOrQuery(Arrays.asList(countryList), GEO_REGION_CODE));
        strBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        return strBuilder.toString();
    }

    /**
     * Filter by News Source
     *
     * @param newsSourceId News source Id
     * @see com.dfn.alerts.constants.IConstants.NewsSourceType
     * @return Query
     */
    private static String getNewsSourceQuery(String newsSourceId) {
        return  getInQuery(DBConstants.NewsDatabaseColumns.NEWS_SOURCE_ID, newsSourceId);
    }

    /**
     * get max seq_id for news relative to language
     * @param language news language
     * @return SELECT MAX(SEQ_ID) AS SEQ_ID_MAX FROM NEWS WHERE LANGUAGE_ID = 'EN'
     */
    public static String getMaxSeqIdQuery(String language) {
        StringBuilder stringBuilder = new StringBuilder(NEWS_MAX_SEQ_ID_QUERY);
        stringBuilder.append(LANGUAGE_ID);
        stringBuilder.append(QUERY_EQUAL).append(QUERY_QUOTE);
        stringBuilder.append(language);
        stringBuilder.append(QUERY_QUOTE);
        return stringBuilder.toString();
    }

    /**
     * get query to load excess imdb records
     * @return NEWS_SEARCH_DB_UPDATE_QUERY
     */
    public String getNewsToTransferQuery() {
        switch (dbType){
            case DBConstants.DatabaseTypes.DB_TYPE_DERBY:
                return NEWS_SEARCH_DERBY_DB_UPDATE_QUERY;
            case DBConstants.DatabaseTypes.DB_TYPE_H2:
                return NEWS_SEARCH_H2_DB_UPDATE_QUERY;
            default:
                return null;
        }
    }

    public static String getNewsDeleteQuery(){
        return NEWS_EOD_IMDB_DELETE;
    }

    /**
     * get query to delete excess imdb records
     * @return NEWS_EOD_IMDB_DELETE
     */
    public String getNewsDeleteOnUpdateQuery(int count) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(DELETE_FROM_NEWS_WHERE);
        String prefix = IConstants.EMPTY;

        int val = count / PLACE_HOLDER_LENGTH;
        int remainder = count % PLACE_HOLDER_LENGTH;

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        if (val > 0) {
            for (int j = 0; j < val; j++) {
                queryBuilder.append(NEWS_ID);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
                for (int i = 0; i < PLACE_HOLDER_LENGTH; i++) {
                    queryBuilder.append(prefix);
                    queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
                    prefix = DBConstants.CommonDatabaseParams.QUERY_COMMA;
                }
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
                if (j < val - 1) {
                    queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
                }
            }
        }

        prefix = IConstants.EMPTY;
        if (remainder > 0) {
            if(val>0){
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
            }
            queryBuilder.append(NEWS_ID);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
            for (int k = 0; k < remainder; k++) {
                queryBuilder.append(prefix);
                queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
                prefix = DBConstants.CommonDatabaseParams.QUERY_COMMA;
            }
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        }

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);


        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(LANGUAGE_ID);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_EQUAL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);

        return queryBuilder.toString();
    }

    /**
     * get news update query
     * @return query
     */
    public static String getNewsInsertQuery() {
        StringBuilder queryBuilder = new StringBuilder(NEWS_INSERT_QUERY);
        String prefix = IConstants.EMPTY;
        for (Field field : DBConstants.NewsDatabaseColumns.class.getFields()) {
            queryBuilder.append(prefix);
            prefix = QUERY_COMMA;
            queryBuilder.append(field.getName());
        }
        queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_VALUES);
        prefix = IConstants.EMPTY;
        for (int i = 0; i < DBConstants.NewsDatabaseColumns.class.getFields().length; i++) {
            queryBuilder.append(prefix);
            prefix = QUERY_COMMA;
            queryBuilder.append(SQL_PLACEHOLDER);
        }
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        if (LOG.isInfoEnabled()) {
            LOG.info(queryBuilder.toString());
        }
        return queryBuilder.toString();
    }

    /**
     * get news priority filter
     * @param priorities priorities
     * @return query
     */
    @SuppressWarnings("unused")
    private static String getNewsPriorityFilter(String priorities) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_AND);
        String prefix = IConstants.EMPTY;
        queryBuilder.append(HOT_NEWS_INDICATOR).append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        String[] priorityList = priorities.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        for (String priority : priorityList) {
            queryBuilder.append(prefix);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(priority);
            queryBuilder.append(QUERY_QUOTE);
            prefix = QUERY_COMMA;
        }
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        return queryBuilder.toString();
    }

    /**
     * filter by date
     * @param fromDate from
     * @param toDate to
     * @return date filter
     */
    private String getNewsTimeFilter(String fromDate, String toDate) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_BRACKET_OPEN);
        queryBuilder.append(NEWS_DATE).append(DBConstants.CommonDatabaseParams.QUERY_BETWEEN);
        if(dbType == DBConstants.DatabaseTypes.DB_TYPE_H2){
            queryBuilder.append(DBUtils.getDateFunctionQuery(fromDate, DATE_TIME_FORMAT, dbType));
        }else{
            queryBuilder.append(QUERY_QUOTE).append(fromDate).append(QUERY_QUOTE);
        }
        queryBuilder.append(QUERY_AND);
        if(dbType == DBConstants.DatabaseTypes.DB_TYPE_H2){
            queryBuilder.append(DBUtils.getDateFunctionQuery(toDate, DATE_TIME_FORMAT, dbType));
        }else{
            queryBuilder.append(QUERY_QUOTE).append(toDate).append(QUERY_QUOTE);
        }
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        return queryBuilder.toString();
    }

    /**
     * Get Top news history search query
     * ~ NEWS_EDITION_SECTION LIKE %(topNewsEdition,topNewsSection)% AND IS_TOP_STORY = 1
     *
     * @param requestData request data
     * @return query
     */
    public static String getTopNewsSearchQuery(Map<String, String> requestData) {
        if (requestData.get(IConstants.MIXDataField.EDITION_ID) != null && requestData.get(IConstants.MIXDataField.SECTION_ID) != null) {
            StringBuilder queryBuilder = new StringBuilder(SELECT_ALL_FROM).append(TABLE_NEWS).append(QUERY_WHERE)
                    .append(DBConstants.DatabaseColumns.TOP_NEWS_EDITION_SECTION)
                    .append(DBConstants.CommonDatabaseParams.QUERY_LIKE)
                    .append(QUERY_QUOTE).append(QUERY_PREC).append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN.trim())
                    .append(requestData.get(IConstants.MIXDataField.EDITION_ID))
                    .append(QUERY_COMMA)
                    .append(requestData.get(IConstants.MIXDataField.SECTION_ID))
                    .append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE.trim()).append(QUERY_PREC).append(QUERY_QUOTE)
                    .append(QUERY_AND)
                    .append(QUERY_TOP_STORY);
            return queryBuilder.toString();
        } else {
            throw new IllegalArgumentException(" Top news edition and section can't be null for this type of request..");
        }

    }

    /**
     * Get Total News Count Query
     *
     *
     * @param requestData request data
     * @return query
     */
    public static String getTotalNewsCountQuery(Map<String, String> requestData) {
        boolean isFirstFilter = true;
        StringBuilder queryBuilder = new StringBuilder(SELECT_COUNT_FROM).append(TABLE_NEWS).append(QUERY_WHERE);
        if (requestData.get(IConstants.MIXDataField.CC) != null) {
            queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));
            isFirstFilter = false;
        }

        if (requestData.get(IConstants.CustomDataField.HEAD_TEXT) != null) {
            if (!isFirstFilter) {
                queryBuilder.append(QUERY_AND);
            }
            queryBuilder.append(getHeadLineSearchQuery(requestData.get(IConstants.CustomDataField.HEAD_TEXT)));
            isFirstFilter = false;
        }


        if (requestData.get(IConstants.MIXDataField.SCODE) != null
                && !requestData.get(IConstants.MIXDataField.SCODE).trim().equals(String.valueOf(IConstants.NewsSourceType.ALL_NEWS_SOURCES.getDefaultValue()))) {

            if (!isFirstFilter) {
                queryBuilder.append(QUERY_AND);
            }
            queryBuilder.append(getNewsSourceQuery(requestData.get(IConstants.MIXDataField.SCODE)));
            isFirstFilter = false;
        }


        if (!isFirstFilter) {
            queryBuilder.append(QUERY_AND);
            isFirstFilter = false;
        }

        addApprovalStatusFilter(queryBuilder);

        queryBuilder.append(QUERY_AND);

        addStatusFilter(queryBuilder);

        return queryBuilder.toString();
    }

    /**
     * Get not in query
     *
     * @param idList comma separated id list
     * @return news_id not in (a,b,c..)
     */
    public static String getNewsIdNotInQuery(String idList){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(NEWS_ID);
        queryBuilder.append(QUERY_NOT_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(idList);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        return queryBuilder.toString();
    }

    /**
     * Get In Query
     * X IN ('123') OR X IN ('456')
     *
     * @param dataStr comma separated data list
     * @return in query
     */
    public static String getInQuery(String field,String dataStr){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(field);
        queryBuilder.append(QUERY_IN);
        String prefix = IConstants.EMPTY;
        String[] sourceList = dataStr.split(CharUtils.toString(IConstants.Delimiter.COMMA));
        queryBuilder.append(QUERY_BRACKET_OPEN);
        for (String priority : sourceList) {
            queryBuilder.append(prefix);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(priority);
            queryBuilder.append(QUERY_QUOTE);
            prefix = QUERY_COMMA;
        }
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        return queryBuilder.toString();
    }

    private static void addOracleFilter(Map<String, String> requestData, StringBuilder queryBuilder){
        if (requestData.containsKey(IConstants.CustomDataField.MIN_NEWS_DATE)) {
            String minDate = requestData.get(IConstants.CustomDataField.MIN_NEWS_DATE);
            queryBuilder.append(QUERY_AND).append(DBConstants.NewsDatabaseColumns.NEWS_DATE)
                    .append(DBConstants.CommonDatabaseParams.QUERY_LESS_THAN)
                    .append("TO_TIMESTAMP").append(QUERY_BRACKET_OPEN).append(QUERY_QUOTE).append(minDate).append(QUERY_QUOTE)
                    .append(QUERY_COMMA).append("'yyyy-MM-dd hh24:mi:ss.ff'").append(QUERY_BRACKET_CLOSE);
        }
    }


    private static void addApprovalStatusFilter(StringBuilder queryBuilder) {
        queryBuilder.append(DBConstants.NewsDatabaseColumns.APPROVAL_STATUS)
                .append(DBConstants.CommonDatabaseParams.QUERY_EQUAL)
                .append(IConstants.NewsApprovalStatus.PUBLISHED.getStatus());
    }

    public static void addStatusFilter(StringBuilder queryBuilder) {
        queryBuilder.append(DBConstants.NewsDatabaseColumns.STATUS)
                .append(DBConstants.CommonDatabaseParams.QUERY_NOT_EQUALS)
                .append(QUERY_QUOTE)
                .append(IConstants.DCPStatus.DELETED.getStatus())
                .append(QUERY_QUOTE);
    }

    /**
     * add filtering & pagination
     *
     * @param requestData search filters
     * @param query       query
     * @return query
     */
    public String getFilteredQuery(Map<String, String> requestData, String query, Integer requestDBType) {
        int newsCount = DEFAULT_NEWS_COUNT;
        int pageIndex;
        StringBuilder queryBuilder;
        String filteredQuery;

        int offset = 0;
        int imdbOffSet = 0;

        if (requestData.get(IConstants.CustomDataField.CUSTOM_QUERY) == null) {
            queryBuilder = new StringBuilder();
            queryBuilder.append(query);
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(getLanguageFilter(requestData.get(IConstants.MIXDataField.L)));

            if(requestData.get(IConstants.CustomDataField.NEWS_ID_LIST_EXCLUDE)!=null){
                queryBuilder.append(QUERY_AND);
                queryBuilder.append(getNewsIdNotInQuery(requestData.get(IConstants.CustomDataField.NEWS_ID_LIST_EXCLUDE)));
            }

            if (requestData.get(IConstants.CustomDataField.NEWS_FROM_DATE_TIME) != null && requestData.get(IConstants.CustomDataField.NEWS_FROM_DATE_TIME) != null) {
                queryBuilder.append(QUERY_AND);
                queryBuilder.append(getNewsTimeFilter(requestData.get(IConstants.CustomDataField.NEWS_FROM_DATE_TIME), requestData.get(IConstants.CustomDataField.NEWS_TO_DATE_TIME)));
            }

            int sortOrder = Integer.valueOf(requestData.get(ApplicationSettingsKeys.NewsKeys.PRIORITY_SORT_TYPE));

            switch (sortOrder){
                /*HOT_NEWS_INDICATOR NOT APPLICABLE*/
                case IConstants.NewsSortType.NEWS_SORT_DATE_DESC_ORDER:
                    break;
                /*HOT_NEWS_INDICATOR IN (1,2) ORDER BY SEQ_ID DESC*/
                case IConstants.NewsSortType.NEWS_SORT_PRIORITY_DATE_DESC_ORDER:
                    queryBuilder.append(QUERY_AND);
                    queryBuilder.append(HOT_NEWS_INDICATOR).append(QUERY_IN).append(HOT_NEWS_VALUES);
                    break;
                 /*HOT_NEWS_INDICATOR =1 ORDER BY SEQ_ID DESC*/
                case IConstants.NewsSortType.NEWS_SORT_FIRST_PRIORITY_DATE_DESC_ORDER:
                    queryBuilder.append(QUERY_AND);
                    queryBuilder.append(HOT_NEWS_INDICATOR).append(QUERY_EQUAL).append(FIRST_PRIORITY_HOT_NEWS_VALUE);
                    break;
                default:
                    break;

            }

            if(requestDBType != null && requestDBType == DBConstants.DatabaseTypes.DB_TYPE_ORACLE){
                addOracleFilter(requestData, queryBuilder);
            }

            queryBuilder.append(QUERY_AND);
            addApprovalStatusFilter(queryBuilder);

            if (requestData.containsKey(IConstants.SORT_FIELD) && requestData.containsKey(IConstants.SORT_ORDER)) {
                queryBuilder.append(QUERY_ORDER)
                        .append(requestData.get(IConstants.SORT_FIELD)).append(" ")
                        .append(requestData.get(IConstants.SORT_ORDER).toUpperCase());
            } else {
                queryBuilder.append(QUERY_ORDER)
                        .append(NEWS_DATE)
                        .append(DESC_ORDER)
                        .append(DBConstants.CommonDatabaseParams.QUERY_COMMA)
                        .append(SEQ_ID).append(DESC_ORDER);
            }

            if (requestData.get(IConstants.CustomDataField.PAGE_INDEX) != null && requestData.get(ApplicationSettingsKeys.NewsKeys.COUNT) != null) {
                pageIndex = Integer.parseInt(requestData.get(IConstants.CustomDataField.PAGE_INDEX));
                newsCount = Integer.parseInt(requestData.get(ApplicationSettingsKeys.NewsKeys.COUNT));

                if(requestData.get(ApplicationSettingsKeys.NewsKeys.IMDB_OFFSET)!=null){
                    imdbOffSet = Integer.valueOf(requestData.get(ApplicationSettingsKeys.NewsKeys.IMDB_OFFSET));
                }

                if (pageIndex >= 0) {

                    offset = imdbOffSet + pageIndex * newsCount;
                }

                /**
                 * Add one to check whether there is any other news available other than the requested amount
                 * This is using to handle pagination at client side
                 */
                newsCount += 1;
            } else if (requestData.get(ApplicationSettingsKeys.NewsKeys.COUNT) != null) {
                newsCount = Integer.parseInt(requestData.get(ApplicationSettingsKeys.NewsKeys.COUNT));
            }

            if(requestDBType == null || requestDBType != DBConstants.DatabaseTypes.DB_TYPE_ORACLE){
                queryBuilder.append(DBUtils.getImdbPaginationQuery(offset, newsCount, dbType));
            }

            filteredQuery = queryBuilder.toString();
        } else {
            filteredQuery = query;
        }
        return filteredQuery;
    }

    /**
     * add language filter
     *
     * @param languageId language
     * @return query
     */
    private static String getLanguageFilter(String languageId) {
        return LANGUAGE_ID + QUERY_EQUAL + QUERY_QUOTE + languageId + QUERY_QUOTE;
    }

    /**
     * get all news sections query
     *
     * @param requestData search filters
     * @return query
     */
    public String getAllNewsSectionsQuery(Map<String, String> requestData) {
        String tempQuery;

        StringBuilder queryBuilder = new StringBuilder(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.EQUITY_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        String equityQuery = getEquityNewsQuery(requestData);
        queryBuilder.append(equityQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();

        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));

        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(UNION_ALL);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.MACRO_ECONOMY_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        requestData.put(IConstants.MIXDataField.EDT, EDT_CODES_MACRO_ECONOMY_NEWS);
        String macroEconomyQuery = getMacroEconomyNewsQuery(requestData);
        queryBuilder.append(macroEconomyQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();

        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        requestData.remove(IConstants.MIXDataField.EDT);

        queryBuilder.append(UNION_ALL);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.FIXED_INCOME_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.FIXED_INCOME.getDefaultValue()));
        String fixedIncomeQuery = getFixedIncomeNewsQuery(requestData);
        queryBuilder.append(fixedIncomeQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();
        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        queryBuilder.append(UNION_ALL);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.MONEY_MARKET_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.MONEY_MARKET.getDefaultValue()));
        String moneyMarketQuery = getMoneyMarketNewsQuery(requestData);
        queryBuilder.append(moneyMarketQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();
        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        queryBuilder.append(UNION_ALL);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.FUNDS_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.MUTUAL_FUNDS.getDefaultValue()));
        String fundsNewsQuery = getFundsNewsQuery(requestData);
        queryBuilder.append(fundsNewsQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();
        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        queryBuilder.append(UNION_ALL);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.COMMODITY_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.COMMODITY.getDefaultValue()));
        String commodityNewsQuery = getCommodityNewsQuery(requestData);
        queryBuilder.append(commodityNewsQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();
        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        queryBuilder.append(UNION_ALL);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.COUNTRY_DATA_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        String countryNewsQuery = getCountryNewsQuery(requestData);
        queryBuilder.append(countryNewsQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();
        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        queryBuilder.append(UNION_ALL);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(CUSTOM_SELECT);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(IConstants.NewsTypes.CURRENCY_NEWS);
        queryBuilder.append(AS);
        queryBuilder.append(SECTION_TYPE);
        requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.FOREX.getDefaultValue()));
        String currencyNewsQuery = getCurrencyNewsQuery(requestData);
        queryBuilder.append(currencyNewsQuery.split(SPLIT_ASTERIX)[1]);
        tempQuery = queryBuilder.toString();
        queryBuilder.setLength(ZERO_LENGTH);
        queryBuilder.append(getFilteredQuery(requestData, tempQuery, null));
        queryBuilder.append(QUERY_BRACKET_CLOSE);


        String allNewsQuery = queryBuilder.toString();

        if (LOG.isInfoEnabled()) {
            LOG.info(" <!--ALL News Query--> :" + allNewsQuery + "<!--ALL News Query--/>");
        }

        return allNewsQuery;
    }

    public String getAllNewsSectionsOracleQuery(Map<String, String> requestData) {
        String tempQuery;
        int dbType = DBConstants.DatabaseTypes.DB_TYPE_ORACLE;
        boolean addUnionAll = false;
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> metadata = new HashMap<String, String>();

        String metadataString = requestData.get(IConstants.CustomDataField.NEWS_SECTION_METADATA);
        String[] metadataArr = metadataString.split(CharUtils.toString(IConstants.Delimiter.COMMA));

        for (String s : metadataArr) {
            if (s.contains(IConstants.Delimiter.HYPHEN)) {
                String[] ss = s.split(IConstants.Delimiter.HYPHEN);
                metadata.put(ss[0], ss[1]);
            }
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.EQUITY_NEWS))) {
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.EQUITY_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            String equityQuery = getEquityNewsQuery(requestData);
            queryBuilder.append(equityQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.EQUITY_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.MACRO_ECONOMY_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.MACRO_ECONOMY_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            requestData.put(IConstants.MIXDataField.EDT, EDT_CODES_MACRO_ECONOMY_NEWS);
            String macroEconomyQuery = getMacroEconomyNewsQuery(requestData);
            queryBuilder.append(macroEconomyQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.MACRO_ECONOMY_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            requestData.remove(IConstants.MIXDataField.EDT);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.FIXED_INCOME_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.FIXED_INCOME_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.FIXED_INCOME.getDefaultValue()));
            String fixedIncomeQuery = getFixedIncomeNewsQuery(requestData);
            queryBuilder.append(fixedIncomeQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.FIXED_INCOME_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.MONEY_MARKET_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.MONEY_MARKET_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.MONEY_MARKET.getDefaultValue()));
            String moneyMarketQuery = getMoneyMarketNewsQuery(requestData);
            queryBuilder.append(moneyMarketQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.MONEY_MARKET_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.FUNDS_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.FUNDS_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.MUTUAL_FUNDS.getDefaultValue()));
            String fundsNewsQuery = getFundsNewsQuery(requestData);
            queryBuilder.append(fundsNewsQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.FUNDS_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.COMMODITY_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.COMMODITY_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.COMMODITY.getDefaultValue()));
            String commodityNewsQuery = getCommodityNewsQuery(requestData);
            queryBuilder.append(commodityNewsQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.COMMODITY_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.COUNTRY_DATA_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.COUNTRY_DATA_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            String countryNewsQuery = getCountryNewsQuery(requestData);
            queryBuilder.append(countryNewsQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.COUNTRY_DATA_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

            addUnionAll = true;
        }

        if (metadata.containsKey(String.valueOf(IConstants.NewsTypes.CURRENCY_NEWS))) {
            if (addUnionAll) {
                queryBuilder.append(UNION_ALL);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(SELECT_ALL_FROM);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(CUSTOM_SELECT);
            queryBuilder.append(QUERY_COMMA);
            queryBuilder.append(IConstants.NewsTypes.CURRENCY_NEWS);
            queryBuilder.append(AS);
            queryBuilder.append(SECTION_TYPE);
            requestData.put(IConstants.MIXDataField.AST, Integer.toString(IConstants.AssetType.FOREX.getDefaultValue()));
            String currencyNewsQuery = getCurrencyNewsQuery(requestData);
            queryBuilder.append(currencyNewsQuery.split(SPLIT_ASTERIX)[1]);
            tempQuery = queryBuilder.toString();
            queryBuilder.setLength(ZERO_LENGTH);
            queryBuilder.append(getFilteredQuery(requestData, tempQuery,dbType));
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(PAGINATION_ROWNUM);
            queryBuilder.append(metadata.get(String.valueOf(IConstants.NewsTypes.CURRENCY_NEWS)));
            queryBuilder.append(QUERY_BRACKET_CLOSE);

        }

        String allNewsQuery = queryBuilder.toString();

        if (LOG.isInfoEnabled()) {
            LOG.info(" <!--ALL News Query--> :" + allNewsQuery + "<!--ALL News Query--/>");
        }

        return allNewsQuery;
    }

    /**
     * get ipo news query
     *
     * @param requestData search filters
     * @return query
     */
    public static String getIPONewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(EDITORIAL_CODE);
        queryBuilder.append(QUERY_LIKE);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(requestData.get(IConstants.MIXDataField.EDT));
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));

        return queryBuilder.toString();
    }

    /**
     * get mergers & acquisition news
     *
     * @param requestData search filters
     * @return query
     */
    public static String getMANewsQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(EDITORIAL_CODE);
        queryBuilder.append(QUERY_LIKE);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(requestData.get(IConstants.MIXDataField.EDT));
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(getCountryFilterQuery(requestData.get(IConstants.MIXDataField.CC)));

        return queryBuilder.toString();
    }

    /**
     * Create query to search news from comma separated newsId list
     *
     * @param requestData request data
     * @return news search query
     *         eg: - SELECT * FROM NEWS WHERE NEWS_ID IN (8498456,8498438) AND LANGUAGE_ID=''
     */
    public static String getNewsByIdQuery(Map<String, String> requestData) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        if(requestData.containsKey(IConstants.MIXDataField.L)) {
            queryBuilder.append(getLanguageFilter(requestData.get(IConstants.MIXDataField.L)));
            queryBuilder.append(QUERY_AND);
        }
        queryBuilder.append(NEWS_ID);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(requestData.get(IConstants.CustomDataField.NEWS_ID_LIST));
        queryBuilder.append(QUERY_BRACKET_CLOSE);

        queryBuilder.append(QUERY_AND);
        addApprovalStatusFilter(queryBuilder);

        return queryBuilder.toString();
    }

    /**
     * Get news headlines for the editorial code from the oracle data base
     * @param requestData
     * @return query string
     */
    public static String getEditorialCodeNews(Map<String, String> requestData){
        StringBuilder queryBuilder = new StringBuilder(QUERY_NEWS).append(QUERY_WHERE);
        queryBuilder.append(getEditorialCodeFilterQuery(requestData.get(IConstants.MIXDataField.EDT)));
        return queryBuilder.toString();
    }

    /**
     * Generate like or query
     *
     * @param paramList param list
     * @param dbColumn  db column
     * @return query
     */
    private static String getLikeOrQuery(List<String> paramList, String dbColumn) {
        String queryLike = IConstants.EMPTY;
        StringBuilder queryBuilder = new StringBuilder();
        for (String paramVal : paramList) {
            if (!paramVal.trim().isEmpty()) {
                queryBuilder.append(queryLike);
                queryBuilder.append(dbColumn);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_LIKE);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_PREC);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_COMMA);
                queryBuilder.append(paramVal);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_COMMA);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_PREC);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_QUOTE);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
                queryLike = DBConstants.CommonDatabaseParams.QUERY_OR;
            }
        }
        return queryBuilder.toString();
    }

    //region IMDB update all

    private static final String NEWS_MAX_DATE_ORACLE_QUERY = "SELECT MAX(" + NEWS_DATE +
            ") AS NEWS_DATE_MAX FROM " + DBConstants.TablesORACLE.TABLE_NEWS + " WHERE " + LANGUAGE_ID + " = ?";

    public static final String KEY_UPDATE = "NEWS_UPDATE_";
    public static final String KEY_INSERT = "NEWS_INSERT_";
    public static final String KEY_NEWS_INDV_INSERT = "NEWS_INDV_INSERT_";
    public static final String KEY_NEWS_INDV_DELETE = "NEWS_INDV_DELETE_";
    private static Map<String,String> QUERY_MAP = new HashMap<String, String>();

    public static final String INSERT_PLACE_HOLDERS_WITH_TAGGING         = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";   //30
    public static final String INSERT_PLACE_HOLDERS_WITHOUT_TAGGING      = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";   //25
    public static final String INSERT_PLACE_HOLDERS_WITH_TAGGING_IMDB    = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";  //33
    public static final String INSERT_PLACE_HOLDERS_WITHOUT_TAGGING_IMDB = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";   //28
    public static final String INSERT_PLACE_HOLDERS_NEWS_INDV = "?,?";   //2

    /**
     * get max news data for news in oracle relative to language
     * @return SELECT MAX(NEWS_DATE) AS NEWS_DATE_MAX FROM NEWS WHERE LANGUAGE_ID = ?
     */
    public static String getMaxNewsDateInOracleQuery() {
        return NEWS_MAX_DATE_ORACLE_QUERY;
    }

    /**
     * Get News Update Query for Insert/Update all news
     *
     * @param isTagFields is manually tagging fields should be enabled
     * @return Insert query
     */
    public static String getAllNewsUpdateQuery(boolean isTagFields , int dbType) {
        String updateQuery;
        String key = KEY_UPDATE + Boolean.valueOf(isTagFields).toString().toUpperCase() + dbType;

        if (QUERY_MAP.get(key)==null) {
            StringBuilder queryBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_UPDATE).
                    append(DBConstants.TablesIMDB.TABLE_NEWS).
                    append(DBConstants.CommonDatabaseParams.QUERY_SET);

            queryBuilder.append(EXT_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(NEWS_PROVIDER).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(ASSET_CLASS).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(NEWS_DATE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(HOT_NEWS_INDICATOR).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(SOURCE_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(LAST_UPDATED_TIME).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(STATUS).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(NEWS_HEADLINE_LN).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(EDITORIAL_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(GEO_REGION_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(GOVERNMENT_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(INDIVIDUAL_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(INDUSTRY_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(MARKET_SECTOR_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(PRODUCT_SERVICES_CODE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(INTERNAL_CLASS_TYPE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(APPROVAL_STATUS).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(URL).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(UNIQUE_SEQUENCE_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(NEWS_SOURCE_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(NEWS_SOURCE_DESC).append(QUERY_EQUAL).append(SQL_PLACEHOLDER).append(QUERY_COMMA)
                    .append(SEQ_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER);

            if(dbType == IConstants.DB_TYPE_IMDB){
                queryBuilder.append(QUERY_COMMA).append(TOP_NEWS_EDITION_SECTION).append(QUERY_EQUAL).append(SQL_PLACEHOLDER)
                        .append(QUERY_COMMA).append(IS_TOP_STORY).append(QUERY_EQUAL).append(SQL_PLACEHOLDER)
                        .append(QUERY_COMMA).append(LAST_SYNC_TIME).append(QUERY_EQUAL).append(SQL_PLACEHOLDER);
            }

//            if(dbType == IConstants.DB_TYPE_ORACLE) {
//                queryBuilder.append(QUERY_COMMA)
//                        .append("NODE_1_STATUS")
//                        .append(QUERY_EQUAL)
//                        .append(SQL_PLACEHOLDER)
//                        .append(QUERY_COMMA)
//                        .append("NODE_2_STATUS")
//                        .append(QUERY_EQUAL).append(SQL_PLACEHOLDER);
//            }

            if(isTagFields){
                queryBuilder.append(QUERY_COMMA).append(COUNTRY).append(QUERY_EQUAL).append(SQL_PLACEHOLDER)
                        .append(QUERY_COMMA).append(TICKER_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER)
                        .append(QUERY_COMMA).append(EXCHANGE).append(QUERY_EQUAL).append(SQL_PLACEHOLDER)
                        .append(QUERY_COMMA).append(SYMBOL).append(QUERY_EQUAL).append(SQL_PLACEHOLDER)
                        .append(QUERY_COMMA).append(COMPANY_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER);
            }

            queryBuilder.append(QUERY_WHERE).append(NEWS_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER);
            queryBuilder.append(QUERY_AND).append(LANGUAGE_ID).append(QUERY_EQUAL).append(SQL_PLACEHOLDER);

            updateQuery = queryBuilder.toString();

            QUERY_MAP.put(key, updateQuery);

        }else{
            updateQuery = QUERY_MAP.get(key);
        }

        LOG.debug("<!--News Insert/Update | Update Query : "+updateQuery);

        return updateQuery;
    }



    /**
     * Get news insert query [ Only for a specific language ]
     *
     * @return Query
     */
    public static String getAllNewsInsertQuery(boolean isTagFields, int dbType) {
        String insertQuery;
        String key = KEY_INSERT + Boolean.valueOf(isTagFields).toString().toUpperCase() + dbType;

        if (QUERY_MAP.get(key) == null) {

            StringBuilder queryBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO).
                    append(DBConstants.TablesIMDB.TABLE_NEWS).
                    append(QUERY_BRACKET_OPEN);

            queryBuilder.append(EXT_ID).append(QUERY_COMMA)
                    .append(NEWS_PROVIDER).append(QUERY_COMMA)
                    .append(ASSET_CLASS).append(QUERY_COMMA)
                    .append(NEWS_DATE).append(QUERY_COMMA)
                    .append(HOT_NEWS_INDICATOR).append(QUERY_COMMA)
                    .append(SOURCE_ID).append(QUERY_COMMA)
                    .append(LAST_UPDATED_TIME).append(QUERY_COMMA)
                    .append(STATUS).append(QUERY_COMMA)
                    .append(NEWS_HEADLINE_LN).append(QUERY_COMMA)
                    .append(EDITORIAL_CODE).append(QUERY_COMMA)
                    .append(GEO_REGION_CODE).append(QUERY_COMMA)
                    .append(GOVERNMENT_CODE).append(QUERY_COMMA)
                    .append(INDIVIDUAL_CODE).append(QUERY_COMMA)
                    .append(INDUSTRY_CODE).append(QUERY_COMMA)
                    .append(MARKET_SECTOR_CODE).append(QUERY_COMMA)
                    .append(PRODUCT_SERVICES_CODE).append(QUERY_COMMA)
                    .append(INTERNAL_CLASS_TYPE).append(QUERY_COMMA)
                    .append(APPROVAL_STATUS).append(QUERY_COMMA)
                    .append(URL).append(QUERY_COMMA)
                    .append(UNIQUE_SEQUENCE_ID).append(QUERY_COMMA)
                    .append(NEWS_SOURCE_ID).append(QUERY_COMMA)
                    .append(NEWS_SOURCE_DESC).append(QUERY_COMMA)
                    .append(SEQ_ID);
            if(dbType == IConstants.DB_TYPE_IMDB){
                queryBuilder.append(QUERY_COMMA).append(TOP_NEWS_EDITION_SECTION)
                        .append(QUERY_COMMA).append(IS_TOP_STORY)
                        .append(QUERY_COMMA).append(LAST_SYNC_TIME);
            }

//                    if(IConstants.DB_TYPE_ORACLE == dbType){
//                        queryBuilder.append(QUERY_COMMA)
//                        .append("NODE_1_STATUS")
//                        .append(QUERY_COMMA)
//                        .append("NODE_2_STATUS");
//                    }

            if(isTagFields){
                queryBuilder.append(QUERY_COMMA).append(COUNTRY)
                        .append(QUERY_COMMA).append(TICKER_ID)
                        .append(QUERY_COMMA).append(EXCHANGE)
                        .append(QUERY_COMMA).append(SYMBOL)
                        .append(QUERY_COMMA).append(COMPANY_ID);
            }

            queryBuilder.append(QUERY_COMMA).append(NEWS_ID);
            queryBuilder.append(QUERY_COMMA).append(LANGUAGE_ID);

            queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_VALUES);
            if(isTagFields){
                if(dbType == IConstants.DB_TYPE_ORACLE) {
                    queryBuilder.append(INSERT_PLACE_HOLDERS_WITH_TAGGING);
                }else {
                    queryBuilder.append(INSERT_PLACE_HOLDERS_WITH_TAGGING_IMDB);
                }
            }else{
                if(dbType == IConstants.DB_TYPE_ORACLE) {
                    queryBuilder.append(INSERT_PLACE_HOLDERS_WITHOUT_TAGGING);
                }else {
                    queryBuilder.append(INSERT_PLACE_HOLDERS_WITHOUT_TAGGING_IMDB);
                }
            }
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);

            insertQuery = queryBuilder.toString();

            QUERY_MAP.put(key, insertQuery);

        }else{
            insertQuery = QUERY_MAP.get(key);
        }

        LOG.debug("<!--News Insert/Update |Insert Query : "+insertQuery);

        return insertQuery;
    }

    /**
     * Get news individual insert query [ Only for a specific language ]
     * @param dbType db type
     * @return query
     */
    public static String getNewsIndividualsInsertQuery(int dbType) {
        String insertQuery;
        String key = KEY_NEWS_INDV_INSERT + dbType;

        if (QUERY_MAP.get(key) == null) {
            insertQuery = new StringBuilder(DBConstants.CommonDatabaseParams.QUERY_INSERT_INTO).
                    append(DBConstants.TablesORACLE.TABLE_NEWS_INDIVIDUALS).
                    append(QUERY_BRACKET_OPEN).append(NEWS_ID).append(QUERY_COMMA).
                    append("INDIVIDUAL_ID").append(DBConstants.CommonDatabaseParams.SQL_VALUES).
                    append(INSERT_PLACE_HOLDERS_NEWS_INDV).
                    append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE).toString();
            QUERY_MAP.put(key, insertQuery);
        } else {
            insertQuery = QUERY_MAP.get(key);
        }
        LOG.debug("<!--News Individuals Insert/Update |Insert Query : " + insertQuery);
        return insertQuery;
    }

    /**
     * Get news individual delete query [ Only for a specific language ]
     * @param dbType db type
     * @return query
     */
    public static String getNewsIndividualsDeleteQuery(int dbType, int size) {
        StringBuilder queryBuilder = new StringBuilder(DBConstants.CommonDatabaseParams.DELETE_FROM).
                append(DBConstants.TablesORACLE.TABLE_NEWS_INDIVIDUALS).
                append(QUERY_WHERE).append(NEWS_ID).append(QUERY_IN).
                append(DBConstants.CommonDatabaseParams.SQL_BRACKET_OPEN);

        for (int i = 0; i < size - 1; i++) {
            queryBuilder.append(SQL_PLACEHOLDER).append(QUERY_COMMA);
        }

        queryBuilder.append(SQL_PLACEHOLDER).append(DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE);
        String deleteQuery = queryBuilder.toString();
        LOG.debug("<!--News Individuals Insert/Update |Insert Query : " + deleteQuery);
        return deleteQuery;
    }

    public static List<String> getNewsSearchByNewsIdQueryList(List<Integer> newsList) {
        int val = newsList.size() / PLACE_HOLDER_LENGTH;
        int remainder = newsList.size() % PLACE_HOLDER_LENGTH;
        List<String> queries = new ArrayList<String>(val);
        String query = "SELECT NEWS_ID FROM NEWS WHERE NEWS_ID IN ({0}) AND LANGUAGE_ID = ?";
        int start = 0;
        int end = 0;
        for(int i = 0; i < newsList.size(); i++){
            end++;
            if((end + 1) % PLACE_HOLDER_LENGTH == 0){
                queries.add(MessageFormat.format(query, StringUtils.join(new ArrayList<Integer>(newsList).subList(start, end), ",")));
                start = i + 1;
            }
        }
        if(remainder > 0){
            queries.add(MessageFormat.format(query, StringUtils.join(new ArrayList<Integer>(newsList).subList(start, end), ",")));
        }
        return queries;
    }

    /**
     * Get the query for Search seq ids of available news in IMDB
     * SEQ_ID in (?,?..) OR SEQ_ID in (?,?..) .. Logic is added to avoid the limitation in 'IN ()' clause
     * in a sql statement
     *
     * @param newsListSize news seq ld list size
     * @return query
     */
    public static String getNewsSearchByNewsIdQuery(int newsListSize) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("SELECT NEWS_ID FROM NEWS WHERE");
        String prefix = IConstants.EMPTY;

        int val = newsListSize / PLACE_HOLDER_LENGTH;
        int remainder = newsListSize % PLACE_HOLDER_LENGTH;

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        if (val > 0) {
            for (int j = 0; j < val; j++) {
                queryBuilder.append(DBConstants.NewsDatabaseColumns.NEWS_ID);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN);
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
                for (int i = 0; i < PLACE_HOLDER_LENGTH; i++) {
                    queryBuilder.append(prefix);
                    queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
                    prefix = DBConstants.CommonDatabaseParams.QUERY_COMMA;
                }
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
                if (j < val - 1) {
                    queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
                    prefix = IConstants.EMPTY;
                }
            }
        }

        prefix = IConstants.EMPTY;
        if (remainder > 0) {
            if(val>0){
                queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
            }
            queryBuilder.append(DBConstants.NewsDatabaseColumns.NEWS_ID);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_IN);
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
            for (int k = 0; k < remainder; k++) {
                queryBuilder.append(prefix);
                queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);
                prefix = DBConstants.CommonDatabaseParams.QUERY_COMMA;
            }
            queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        }

        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(DBConstants.NewsDatabaseColumns.LANGUAGE_ID).append(DBConstants.CommonDatabaseParams.QUERY_EQUAL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.SQL_QUESTION_MARK);

        return queryBuilder.toString();
    }

    //endregion

    //region top news history update query

    public static final String QUERY_GET_TOP_STORIES_HISTORY_FOR_UPDATE =  SELECT_ALL_FROM + TOP_NEWS_HISTORY +
            QUERY_WHERE  + QUERY_TOP_STORY_TO_BE_UPDATED;

    public static final String QUERY_GET_TOP_STORIES_HISTORY_FOR_UPDATE_IMDB = SELECT_ALL_FROM + TABLE_NEWS + QUERY_WHERE
            + TOP_STORY_SYNC_TIME + QUERY_GREATER_THAN + PREVIOUS_DAY;

    public static final String QUERY_UPDATE_TOP_NEWS =  QUERY_UPDATE + TABLE_NEWS + QUERY_SET + TOP_NEWS_EDITION_SECTION +
            EQUAL_QUETION_MARK + QUERY_COMMA + QUERY_TOP_STORY + QUERY_WHERE + NEWS_ID + EQUAL_QUETION_MARK;

    public static final String QUERY_MARK_UPDATED_TOP_STORIES = QUERY_UPDATE + TOP_NEWS_HISTORY + QUERY_SET + QUERY_TOP_STORY_UPDATED+
            QUERY_WHERE + NEWS_ID +  QUERY_IN + QUERY_BRACKET_OPEN + SQL_QUESTION_MARK + QUERY_BRACKET_CLOSE;

    /**
     * Returns to the query to search Top News in "TOP_NEWS_HISTORY" which need to be updated in NEWS table.
     *
     * @return query
     */
    public static String getQueryToSelectTopStoriesToBeUpdated (){
        return QUERY_GET_TOP_STORIES_HISTORY_FOR_UPDATE;
    }

    /**
     * Returns to the query to search Top News in "News" which need to be updated in IMDB.NEWS table.
     *
     * @return query
     */
    public static String getQueryToSelectTopStoriesToBeUpdatedInIMDB (){
        return QUERY_GET_TOP_STORIES_HISTORY_FOR_UPDATE_IMDB;
    }

    /**
     * Returns the query to update Top stories in news table.
     *
     * @return query
     */
    public static String getQueryForTopStoriesUpdate(){
        return QUERY_UPDATE_TOP_NEWS;
    };

    /**
     * Get query to update TOP_NEWS_HISTORY table to mark Top Stories as Updated in NEWS table.
     *
     * @return query
     */
    public static String getQueryToMarkTopStoriesAsUpdated(){
        return QUERY_MARK_UPDATED_TOP_STORIES;
    }


    //endregion
}
