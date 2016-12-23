package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.beans.*;
import com.dfn.alerts.beans.tickers.*;
import com.dfn.alerts.beans.tickers.snapshot.EquityTickerSnapshotDTO;
import com.dfn.alerts.beans.tickers.snapshot.IndexSnapshotDTO;
import com.dfn.alerts.constants.*;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.dao.MetaDataDAO;
import com.dfn.alerts.dataaccess.dao.impl.DAOFactory;
import com.dfn.alerts.dataaccess.orm.impl.earnings.ApplicationSetting;
import com.dfn.alerts.dataaccess.utils.CountryDBHelper;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.dataaccess.utils.DataAccessUtils;
import com.dfn.alerts.dataaccess.utils.FundRatiosDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.*;
import com.dfn.alerts.utils.FormatUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;
import static com.dfn.alerts.constants.DBConstants.MetaDataType.*;
import static com.dfn.alerts.constants.DBConstants.TablesIMDB.*;

/**
 * Created by IntelliJ IDEA.
 * User: Duminda A
 * Date: 04/04/13
 * Time: 5:05 PM
 */
public class TickerDataAccess extends DataAccess {

    public static final String QUERY_INDEX_PROVIDERS = QUERY_SELECT + QUERY_DISTINCT + PROVIDER + FROM + TABLE_INDICES;
    private static final String QUERY_SPACE = " ";
    private static final String QUERY_TICKER = QUERY_SELECT_ALL_FROM + TABLE_TICKERS + QUERY_SPACE;
    private static final String QUERY_FUND_TICKER = QUERY_SELECT_ALL_FROM + TABLE_FUND_TICKERS + QUERY_SPACE;
    private static final String QUERY_FIXED_INCOME_TICKER = QUERY_SELECT_ALL_FROM + TABLE_FIXED_INCOME_TICKERS + QUERY_SPACE;
    private static final String QUERY_FUND_TICKER_COUNT = QUERY_SELECT_COUNT_ALL_FROM + TABLE_FUND_TICKERS;
    private static final String QUERY_FIXED_INCOME_TICKER_COUNT = QUERY_SELECT_COUNT_ALL_FROM + TABLE_FIXED_INCOME_TICKERS;
    private static final String QUERY_FIXED_INCOME_TICKER_COUNT_GROUP_BY = QUERY_GROUP_BY + INSTRUMENT_TYPE_ID + QUERY_COMMA + BOND_TYPE;
    private static final String QUERY_FIXED_INCOME_TICKER_INSTRUMENT_TYPE_FILTER = INSTRUMENT_TYPE_ID + QUERY_IN +
            QUERY_BRACKET_OPEN + IConstants.InstrumentTypes.getFixedIncomeInstrumentTypes() + QUERY_BRACKET_CLOSE;
    private static final String QUERY_FIXED_INCOME_TREASURY_BILLS_TICKER_INSTRUMENT_TYPE_FILTER = INSTRUMENT_TYPE_ID + QUERY_EQUAL +
            IConstants.InstrumentTypes.TREASURY_BILLS.getDefaultValues();
    private static final String QUERY_INDEX_FILTER = INSTRUMENT_TYPE_ID + QUERY_EQUAL + IConstants.InstrumentTypes.INDICES.getDefaultValues();
    //todo : refactor
    private static final String QUERY_COUNT_FIXED_INCOME_TICKER = "SELECT COUNT(*) AS TYPE_COUNT FROM FIXED_INCOME_TICKERS ";
    private static final String ZERO = "0";
    private static final String QUERY_FI_LATEST_TRADED = " SELECT DISTINCT T.* FROM FIXED_INCOME_TICKERS T ";
    private static final String QUERY_FI_LATEST_TRADED_DATE = " MAXDATE FROM FIXED_INCOME_TICKERS ";
    private static final String QUERY_MAX_TRADED_DATE = " MAX(LAST_TRADED_DATE) ";
    private static final String QUERY_FI_OUTSTANDING_FILTER = " MATURITY_DATE > CURRENT_DATE ";
    private static final String QUERY_FI_LAST_TRADED_TIME_FILTER = " T.LAST_TRADED_DATE >= MAXDATE ";
    private static final String QUERY_FIXED_INCOME_TICKER_COUNT_SELECT = " SELECT COUNT(*) AS TYPE_COUNT, FI.INSTRUMENT_TYPE_ID ,BOND_TYPE ,SUM(AMOUNT_OUTSTANDING) as TOTAL_AMOUNT_OUTSTANDING ";
    private static final String QUERY_FIXED_INCOME_TICKER_COUNT_FROM = " FROM FIXED_INCOME_TICKERS FI ";
    private static final String QUERY_FIXED_INCOME_TICKER_COUNT_WHERE = " WHERE MATURITY_DATE > CURRENT_DATE AND COUNTRY_CODE = ";
    private static final String QUERY_COUNTRY_TICKER_SELECT = "SELECT TICKER_SERIAL,TICKER_ID,COUNTRY_CODE,CURRENCY_ID,DECIMAL_PLACES,DISPLAY_TICKER, LAST_UPDATED_TIME,SOURCE_ID,STATUS,TICKER_SHORT_DES_LN,TICKER_LONG_DES_LN,UNIT,INSTRUMENT_TYPE_ID FROM COUNTRY_TICKERS WHERE COUNTRY_CODE = ";
    private static final String QUERY_COUNTRY_TICKER_SNAPSHOT_SELECT = "SELECT TICKER_SERIAL,TICKER_ID,COUNTRY_CODE,CURRENCY_ID,DECIMAL_PLACES,DISPLAY_TICKER, LAST_UPDATED_TIME,SOURCE_ID,STATUS,TICKER_SHORT_DES_LN,TICKER_LONG_DES_LN,UNIT,HISTORY_DATA,INSTRUMENT_TYPE_ID FROM COUNTRY_TICKERS WHERE COUNTRY_CODE = ";
    private static final String QUERY_TICKER_SNAPSHOT = "SELECT TS.* , T.* FROM TICKER_SNAPSHOT TS , TICKERS T ";
    private static final String QUERY_TICKER_SNAPSHOT_FILTER = " (TS.TICKER_ID = T.TICKER_ID AND TS.SOURCE_ID = T.SOURCE_ID AND T.STATUS = 1 ) ";
    private static final String QUERY_TS_SOURCE_ID_FILTER = "  TS.SOURCE_ID =";
    private static final String QUERY_AS_A = " AS A ";
    private static final String QUERY_FUND_TICKER_JOIN_BENCHMARK = "SELECT * FROM FUND_TICKERS ft JOIN FUND_BENCHMARKS fb on (ft.TICKER_SERIAL = fb.TICKER_SERIAL ) ";
    private static final String QUERY_FUND_TICKER_JOIN_BENCHMARK_COUNT = "SELECT COUNT(*) FROM FUND_TICKERS ft JOIN FUND_BENCHMARKS fb on (ft.TICKER_SERIAL = fb.TICKER_SERIAL ) ";
    private static final String QUERY_T_DOT = " T.";
    private static final String QUERY_FUND_BENCHMARK_STATUS_FILTER = " ft.STATUS = 1 ";
    //Indexes
    private static final String QUERY_INDEX_TICKER_COUNT = QUERY_SELECT_COUNT_ALL_FROM + TABLE_INDICES;
    private static final String QUERY_INDEX_TICKER_SELECT = QUERY_SELECT_ALL_FROM + TABLE_INDICES + QUERY_SPACE;
    //company filter query for operational kpi
    private static final String QUERY_KPI_FILTERED_COMPANIES = "SELECT COMPANY_ID, MAX(COMPANY_NAME_LAN) AS COMPANY_NAME_LAN FROM (SELECT COMPANY_ID, COMPANY_NAME_LAN, abs(? - SLA_LEVEL) as SLA_LEVEL FROM tickers WHERE COUNTRY_CODE = ? AND GICS_L3_CODE = ? AND SLA_LEVEL IS NOT NULL AND COMPANY_ID != ?) T GROUP BY COMPANY_ID ORDER BY MIN(T.SLA_LEVEL) ASC";
    private static final String DEFAULT_LANG = "EN";
    private static final int PER_SOURCE_INDEX_LIST_TTL = 86400; //60*60*24
    private static final int INDEX_PROVIDERS_TTL = 1800; //60*30

    //ticker class l3 for equity
    private static final String  TICKER_CLASS_L3_EQUITY= "62";

    private static final Logger LOG = LogManager.getLogger(TickerDataAccess.class);
    private DAOFactory imdbDaoFactory;
    private MasterDataAccess masterDataAccess;
    private int dbType = DBConstants.DatabaseTypes.DB_TYPE_DERBY;

    private DAOFactory jdbcDaoFactory;

    public void setImdbDaoFactory(DAOFactory imdbDaoFactory) {
        this.imdbDaoFactory = imdbDaoFactory;
    }

    public void setMasterDataAccess(MasterDataAccess masterDataAccess) {
        this.masterDataAccess = masterDataAccess;
    }

    public void setJdbcDaoFactory(DAOFactory jdbcDaoFactory) {
        this.jdbcDaoFactory = jdbcDaoFactory;
    }

    /**
     * Get ticker data supported language
     *
     * @return supported languages
     */
    public List<String> getSupportedLanguages() {
        List<String> languages = new ArrayList<String>();
        ApplicationSetting langSettings = this.masterDataAccess.getSetting(ApplicationSettingsKeys.KEY_SUPPORTED_LANGUAGES_TICKER_DATA);
        if (langSettings != null) {
            languages = langSettings.getValueList();
        }
        return languages;
    }

    /**
     * Generate cache key
     *
     * @param requestData request data
     * @return cacheKey string
     */
    protected String generateCacheKey(Map<String, String> requestData) {
        StringBuilder cachekey = new StringBuilder();
        if (requestData.get(IConstants.CustomDataField.CACHE_KEY_TYPE) != null) {
            cachekey.append(requestData.get(IConstants.CustomDataField.CACHE_KEY_TYPE));
        }
        if (requestData.get(IConstants.MIXDataField.L) != null) {
            cachekey.append(CharUtils.toString(IConstants.Delimiter.TILDE));
            cachekey.append(requestData.get(IConstants.MIXDataField.L).toUpperCase());
        }
        if (requestData.get(IConstants.MIXDataField.S) != null) {
            cachekey.append(CharUtils.toString(IConstants.Delimiter.TILDE));
            cachekey.append(requestData.get(IConstants.MIXDataField.S));
        }
        return cachekey.toString();
    }

    public Object getMemoryData(Map<String, String> requestData) {
        throw new NoSuchMethodError();
    }

    protected Object getSocketResponse(String request, boolean isJsonResponse) {
        throw new NoSuchMethodError();
    }

    @Override
    protected Object getSocketResponse(String request, int timeout, boolean isJsonResponse) {
        return null;
    }

    protected Object processResponse(ResponseObj response) {
        throw new NoSuchMethodError();
    }

    @Override
    public int updateData(DataAccessRequestDTO requestDTO) {
        return 0;
    }

    @Override
    public void initUpdateData() {
        //
    }

    @SuppressWarnings("unchecked")
    public Map<String, PriceSnapshotAdjustedDTO> getAdjustedPriceSnapshotData(List<String> tickerSerials) {

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(QUERY_SELECT_ALL_FROM + TABLE_TICKER_SNAPSHOT_ADJUSTED + QUERY_WHERE + TICKER_SERIAL + QUERY_IN +
                QUERY_BRACKET_OPEN +
                StringUtils.join(tickerSerials, IConstants.Delimiter.COMMA) +
                QUERY_BRACKET_CLOSE);
        return (Map<String, PriceSnapshotAdjustedDTO>) masterDataDAO.get(IConstants.ControlPathTypes.AJUSTED_PRICE_SNAPSHOT_SEARCH, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public List<TickerDTO> getAllUnsubscribedTickers(boolean isDelayed, String countries){
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(QUERY_SELECT_ALL_FROM + DBUtils.getTickerSnapshotTableName(isDelayed) + " ts" + QUERY_JOIN +
                DBConstants.TablesIMDB.TABLE_TICKERS + " t" + QUERY_ON + QUERY_BRACKET_OPEN + "t." + DBConstants.DatabaseColumns.TICKER_SERIAL + QUERY_EQUAL +
                "ts." + DBConstants.DatabaseColumns.TICKER_SERIAL + QUERY_BRACKET_CLOSE +
                QUERY_WHERE + DBConstants.DBTickerSnapshotColumns.SUBSCRIPTION_STATUS + QUERY_EQUAL + QUERY_QUOTE + CMConstants.CM_SUBSCRIPTION_STATUS_NOT_SUBSCRIBED + QUERY_QUOTE +
                QUERY_AND + DBConstants.DatabaseColumns.COUNTRY_CODE + QUERY_IN + QUERY_BRACKET_OPEN + QUERY_QUOTE + countries.replaceAll(QUERY_COMMA, QUERY_QUOTE + QUERY_COMMA + QUERY_QUOTE) +
                QUERY_QUOTE + QUERY_BRACKET_CLOSE);
        return (List<TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_ALL_UNSUBSCRIBED_TICKERS, requestDBDTO);
    }

    /**
     * Retrieve data about companies filtered by the given countries and the given company IDs
     *
     * @param commaSeparatedCountryCodes data are filtered by these countries
     * @param companyIds   data are filtered by these companies
     * @return map
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public Map<String, CompanyDTO> getCompaniesByCompanyIdAndCountry(String commaSeparatedCountryCodes, Collection<String> companyIds) {

        Map<String, CompanyDTO> filteredCompanyDataMap = null;

        if (!companyIds.isEmpty()) {
            String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
            List<String> supportedLanguages = getSupportedLanguages();
            String queryCompany = CompanyDBHelper.getCompaniesByCompanyIds(companyIds, instrumentTypes, supportedLanguages);

            if(queryCompany != null) {

                String quotedCountryCodes = QUERY_QUOTE + commaSeparatedCountryCodes.replaceAll(QUERY_COMMA, QUERY_QUOTE + QUERY_COMMA + QUERY_QUOTE) + QUERY_QUOTE;
                StringBuilder query = new StringBuilder(queryCompany);

                query.append(QUERY_AND);
                query.append(DBConstants.DatabaseColumns.COUNTRY_CODE).append(QUERY_IN);
                query.append(QUERY_BRACKET_OPEN);
                query.append(quotedCountryCodes);
                query.append(QUERY_BRACKET_CLOSE);

                MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
                RequestDBDTO requestDBDTO = new RequestDBDTO();
                requestDBDTO.setQuery(query.toString());
                requestDBDTO.setSupportedLang(supportedLanguages);

                filteredCompanyDataMap = (Map<String, CompanyDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_COMPANIES, requestDBDTO );
            }
        }
        return filteredCompanyDataMap;
    }

    /**
     * Get equity Ticker for a company
     *
     * @param companyId companyId
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    public Collection<EquityTickerDTO> searchEquityByCompany(int companyId) {
        Collection<EquityTickerDTO> equityTickerDTOs = null;
        String instrumentTypes = this.masterDataAccess.getEquityTickerL3Classes();
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = EquityTickerDBHelper.getSearchEquityBYCompanyQuery(companyId, instrumentTypes, getSupportedLanguages());

        Map<String, EquityTickerDTO> equityTickerDTOMap = (Map<String, EquityTickerDTO>)masterDataDAO.get(IConstants.ControlPathTypes.GET_EQUITY_TICKERS, requestDBDTO);

        if(equityTickerDTOMap != null){
            equityTickerDTOs = equityTickerDTOMap.values();
        }
        return equityTickerDTOs;
    }

    /**
     * Get Ticker for a sector and source
     *
     * @param sectorCode sector name
     * @param sourceId   exchange name
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    //todo : move to equity snapshot db helper
    public Set<EquityTickerSnapshotDTO> getSectorConstituents(String sourceId, String sectorCode) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_TICKER_SNAPSHOT);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.SECTOR_ID).append(QUERY_EQUAL);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(sectorCode.toUpperCase());
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_TS_SOURCE_ID_FILTER);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(sourceId);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_TICKER_SNAPSHOT_FILTER);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DBTickerSnapshotColumns.MARKETCAP).append(QUERY_DESC_ORDER);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        //Search Tickers
        return (HashSet<EquityTickerSnapshotDTO>) masterDataDAO.get(IConstants.ControlPathTypes.EQUITY_SNAPSHOT_SEARCH, queryBuilder.toString());
    }

    /**
     * Get Ticker for a sector and source
     *
     * @param baseCompanyId       base company id
     * @param gics4Code           base company gics 4 code to select peer companies
     * @param countryCode         country code of the base company
     * @param templateId          base company templete id
     * @param currencyCode        base company country code
     * @param isListedOnly        is listed stocks only
     * @param relatedCompanyCount required ticker list count
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    //todo : move to equity db helper
    public Collection<TickerDTO> getRelatedCompanies(int baseCompanyId, String gics4Code, String countryCode, int templateId,
                                               String currencyCode, boolean isListedOnly, int relatedCompanyCount) {

        StringBuilder queryBuilder = new StringBuilder(QUERY_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.TEMPLATE_ID);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(templateId);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);

        if (currencyCode != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_CURRENCY_ID);
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(currencyCode.trim());
            queryBuilder.append(QUERY_QUOTE);
        }

        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
        queryBuilder.append(QUERY_NOT_EQUAL);
        queryBuilder.append(baseCompanyId);

        if (gics4Code != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.GICSL4_CODE);
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(gics4Code.trim());
            queryBuilder.append(QUERY_QUOTE);
        }

        if (countryCode != null && !countryCode.isEmpty()) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.COUNTRY_CODE);
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(countryCode.trim());
            queryBuilder.append(QUERY_QUOTE);
        }

        if (isListedOnly) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.TICKER_SERIAL);
            queryBuilder.append(QUERY_GREATER_THAN);
            queryBuilder.append(ZERO);
        }

        queryBuilder.append(DBUtils.getImdbPaginationQuery(0, relatedCompanyCount, dbType));

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        //Search Tickers
        Map<String, TickerDTO> tickersMap = (Map<String, TickerDTO>)masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS, requestDBDTO);
        return tickersMap.values();
    }

    /**
     * Method to get full market tickers
     *
     * @param sourceId exchange
     * @return all active symbols listed in exchange
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    //todo : move to equity db helper
    public Collection<TickerDTO> getFullMarketTickers(String sourceId) {
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        Collection<TickerDTO> result = null;

        if (instrumentTypes != null) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(QUERY_TICKER);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(QUERY_STATUS_FILTER).append(QUERY_AND).append(DBConstants.DatabaseColumns.TICKER_CLASS_L3).append(QUERY_IN);
            queryBuilder.append(QUERY_BRACKET_OPEN);
            queryBuilder.append(instrumentTypes);
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(DBConstants.DatabaseColumns.SOURCE_ID);
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(sourceId.toUpperCase());
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(QUERY_ORDER);
            queryBuilder.append(DBConstants.DatabaseColumns.TICKER_ID);

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(queryBuilder.toString());
            requestDBDTO.setSupportedLang(getSupportedLanguages());

            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            result = (( Map<String, TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS, requestDBDTO)).values();
        }
        return result;
    }

    /**
     * Get Ticker for a requested index ticker
     *
     * @param indexTickerSerial sector name
     * @param sourceId          exchange name
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    //todo : move to equity snapshot db helper
    public Set<EquityTickerSnapshotDTO> getIndexConstituents(long indexTickerSerial, String sourceId) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        //Search Tickers
        return (Set<EquityTickerSnapshotDTO>) masterDataDAO.get(IConstants.ControlPathTypes.EQUITY_SNAPSHOT_SEARCH,
                DBUtils.getIndexConstituentsSql(indexTickerSerial, sourceId));
    }

    /**
     * Get constituent snapshots
     *
     * @param indexConstituents  constituents
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    //todo : move to equity snapshot db helper
    public Set<EquityTickerSnapshotDTO> getIndexConstituents(String indexConstituents) {
        Set<EquityTickerSnapshotDTO> snapshotDTOs;
        if (indexConstituents != null && !indexConstituents.isEmpty()) {
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            String query = DBUtils.getIndexConstituentsSql(indexConstituents);
            snapshotDTOs = (Set<EquityTickerSnapshotDTO>) masterDataDAO.get(IConstants.ControlPathTypes.EQUITY_SNAPSHOT_SEARCH, query);
        } else {
            snapshotDTOs = Collections.emptySet();
        }
        return snapshotDTOs;
    }

    /**
     * Get index data for a particular source id[language specific]
     *
     * @param sourceId source id
     * @param language language
     * @return index dto list
     */
    @SuppressWarnings("unchecked") //TODO Change query to get data from INDEX_TICKERS not from TICKERS
    public Set<com.dfn.alerts.beans.tickers.IndexDTO> getIndicesData(String sourceId, final String language) {
        Set indicesSet = null;
        List indicesList;

        Map<String, String> cacheKeyReq = new HashMap<String, String>();
        cacheKeyReq.put(IConstants.CustomDataField.CACHE_KEY_TYPE, CacheKeyConstant.CACHE_KEY_PER_SOURCE_INDEX_LIST);
        cacheKeyReq.put(IConstants.MIXDataField.L, language);
        cacheKeyReq.put(IConstants.MIXDataField.S, sourceId);

        String indexListCacheKey = this.generateCacheKey(cacheKeyReq);
        Object indexObj = this.cacheManager.get(indexListCacheKey);

        if (indexObj != null) {
            indicesList = (List) indexObj;
        } else {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(QUERY_TICKER);
            queryBuilder.append(QUERY_WHERE);
            queryBuilder.append(DBConstants.DatabaseColumns.SOURCE_ID).append(QUERY_EQUAL);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(sourceId);
            queryBuilder.append(QUERY_QUOTE);
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(QUERY_INDEX_FILTER);
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(QUERY_STATUS_FILTER);

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(queryBuilder.toString());
            requestDBDTO.setSupportedLang(getSupportedLanguages());

            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            indicesList = (List<com.dfn.alerts.beans.tickers.IndexDTO>) masterDataDAO.get(IConstants.ControlPathTypes.INDEX_SEARCH, requestDBDTO);

            if (indicesList != null) {
                Collections.sort(indicesList, new Comparator<com.dfn.alerts.beans.tickers.IndexDTO>() {

                    public int compare(com.dfn.alerts.beans.tickers.IndexDTO o1, com.dfn.alerts.beans.tickers.IndexDTO o2) {
                        if (o1.getTickerLangDTOMap() != null && o2.getTickerLangDTOMap() != null
                                && o1.getTickerLangDTOMap().get(language.toUpperCase()) != null
                                && o1.getTickerLangDTOMap().get(language.toUpperCase()).getTickerLongDesc() != null
                                && o2.getTickerLangDTOMap().get(language.toUpperCase()) != null
                                && o2.getTickerLangDTOMap().get(language.toUpperCase()).getTickerLongDesc() != null) {

                            return o1.getTickerLangDTOMap().get(language.toUpperCase()).getTickerLongDesc().compareTo(o2.getTickerLangDTOMap().get(language.toUpperCase()).getTickerLongDesc());
                        } else {
                            return (o1.getTickerId()).compareTo(o2.getTickerId());
                        }
                    }
                });
                this.cacheManager.put(indexListCacheKey, indicesList, PER_SOURCE_INDEX_LIST_TTL);
            }
        }
        if (indicesList != null) {
            indicesSet = new LinkedHashSet<IndexDTO>(indicesList);
        }

        return indicesSet;
    }

    /**
     * Get index data for given ticker serials
     *
     * @return index dto list
     */
    @SuppressWarnings("unchecked")
    public Map<String, com.dfn.alerts.beans.tickers.IndexDTO> getIndicesData(List<String> tickerSerialList, List<String> supportedLanguages) {


        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(IndexTickerDBHelper.getIndicesByTickerSerial(tickerSerialList));
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map) masterDataDAO.get(IConstants.ControlPathTypes.INDEX_MAP, requestDBDTO);

    }

    public com.dfn.alerts.beans.tickers.IndexDTO getIndexData(String symbol, String exchange){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(IndexTickerDBHelper.getIndexBySymbolExchange(symbol, exchange));
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (com.dfn.alerts.beans.tickers.IndexDTO) masterDataDAO.get(IConstants.ControlPathTypes.INDEX_DTO_SEARCH, requestDBDTO);
    }

    /**
     * Get all index Providers from IMDB
     * ** Search all distinct INDEX PROVIDERS----
     * @param language language
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, CompanyDTO> getAllIndexProviders(String language) {
        Map<String,CompanyDTO> indexProviders;
        Map<String,CompanyDTO> providerData = null;

        Object providerObj = cacheManager.get(CacheKeyConstant.CACHE_KEY_INDEX_PROVIDERS);

        if (providerObj == null) {

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(QUERY_INDEX_PROVIDERS);
            List<String> supportedLang = new ArrayList<String>();
            supportedLang.add(language);
            requestDBDTO.setSupportedLang(supportedLang);
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

            List<String> providerList = (List<String>) masterDataDAO.get(IConstants.ControlPathTypes.INDEX_PROVIDERS, requestDBDTO);
            if (providerList != null && !providerList.isEmpty()) {
                indexProviders = getCompaniesByCompanyId(providerList, language);
                providerData = getSortedIndexProviders(indexProviders,language);
                this.cacheManager.put(CacheKeyConstant.CACHE_KEY_INDEX_PROVIDERS, providerData, INDEX_PROVIDERS_TTL);
            }
        } else {
            providerData = (Map<String, CompanyDTO>) providerObj;
        }

        return providerData;
    }

    /**
     * Sort Index Providers
     *
     * @param indexProviders
     * @param language
     * @return
     */
    private Map<String, CompanyDTO> getSortedIndexProviders(Map<String, CompanyDTO> indexProviders, final String language) {

        Map<String, CompanyDTO> companyDTOMap = new LinkedHashMap<String, CompanyDTO>();
        List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>(indexProviders.values());

        Collections.sort(companyDTOList, new Comparator<CompanyDTO>() {
            @Override
            public int compare(CompanyDTO c1, CompanyDTO c2) {
                if (c1.getCompanyLangDTOMap() != null && c2.getCompanyLangDTOMap() != null
                        && c1.getCompanyLangDTOMap().get(language.toUpperCase()) != null
                        && c1.getCompanyLangDTOMap().get(language.toUpperCase()).getCompanyName() != null
                        && c2.getCompanyLangDTOMap().get(language.toUpperCase()) != null
                        && c2.getCompanyLangDTOMap().get(language.toUpperCase()).getCompanyName() != null) {

                    return c1.getCompanyLangDTOMap().get(language.toUpperCase()).getCompanyName().compareTo(c2.getCompanyLangDTOMap().get(language.toUpperCase()).getCompanyName());
                } else {
                    return -1;
                }
            }
        });

        for (CompanyDTO companyDTO : companyDTOList) {
            companyDTOMap.put(String.valueOf(companyDTO.getCompanyId()), companyDTO);
        }

        return companyDTOMap;
    }

    /**
     * Get Ticker for a ticker id and source
     *
     * @param countryList symbol name
     * @param sortType    exchange name
     * @param recordCount no of records to fetch
     * @return List<FundTickerDTO> Ticker List
     */
    @SuppressWarnings("unchecked")
    public List<FundTickerDTO> getTopFundData(String countryList, IConstants.FundOrderType sortType, int recordCount) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FUND_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(countryList.replace(",", "','"));
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(sortType.getDefaultValue());
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_ORDER);
        queryBuilder.append(sortType.getDefaultValue());
        queryBuilder.append(QUERY_DESC_ORDER);
        queryBuilder.append(DBUtils.getImdbPaginationQuery(0, recordCount, dbType));

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search Tickers
        return (List<FundTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FUND_TICKER_SEARCH, requestDBDTO);
    }

    /**
     * Search funds according to the required text country and class
     *
     * @param searchMap search parameters
     * @return FundTickerDTO List
     */
    @SuppressWarnings("unchecked")
    public List<FundTickerDTO> searchFunds(Map<String, Object> searchMap) {
        return (List<FundTickerDTO>) searchFunds(searchMap, false).getValues();
    }

    /**
     * Search funds according to the required text country and class
     *
     * @param searchMap search parameters
     * @param loadCount to fetch total result count
     * @return FundTickerDTO List
     */
    @SuppressWarnings("unchecked")
    public SearchObject searchFunds(Map<String, Object> searchMap, boolean loadCount) {
        String language = (searchMap.get(IConstants.LANGUAGE) != null) ? ((String) searchMap.get(IConstants.LANGUAGE)).toUpperCase() : DEFAULT_LANG;

        int benchmark = (Integer) searchMap.get(IConstants.BENCHMARK);

        String sortField = getDBField((String) searchMap.get(IConstants.SORT_FIELD), language);
        String sortOrder = (String) searchMap.get(IConstants.SORT_ORDER);

        int pageIndex = (Integer) searchMap.get(IConstants.PAGE_INDEX);
        int pageSize = (Integer) searchMap.get(IConstants.PAGE_SIZE);

        int start = pageIndex * pageSize;

        int count = -1;

        StringBuilder queryBuilder = new StringBuilder(QUERY_WHERE);
        StringBuilder queryPaginationBuilder = new StringBuilder();

        if (benchmark <= 0) {
            queryBuilder.append(QUERY_STATUS_FILTER);
        } else {
            queryBuilder.append(QUERY_FUND_BENCHMARK_STATUS_FILTER);
        }

        addProfileFiltersToFundSearch(searchMap, queryBuilder);
        addPerformanceFiltersToFundSearch(searchMap, queryBuilder);
        addManagementAndSubscriptionFiltersToFundSearch(searchMap, queryBuilder);

        //add ordering
        queryPaginationBuilder.append(QUERY_ORDER);
        if (sortField == null || sortField.isEmpty()) {
            queryPaginationBuilder.append(DBConstants.DatabaseColumns.TNAV);
        } else {
            if (searchMap.get(IConstants.SORT_FIELD).equals(IConstants.TICKER_LONG)) {
                queryPaginationBuilder.append(QUERY_UPPER).append(QUERY_BRACKET_OPEN);
                queryPaginationBuilder.append(sortField);
                queryPaginationBuilder.append(QUERY_BRACKET_CLOSE);
            } else {
                queryPaginationBuilder.append(sortField);
            }
        }
        //add ordering type
        queryPaginationBuilder.append(QUERY_SPACE).append(sortOrder);
        if (start >= 0) {
            queryPaginationBuilder.append(DBUtils.getImdbPaginationQuery(start, pageSize + 1, dbType));
        }

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        String query;

        if (loadCount) {
            query = (benchmark > 0 ? QUERY_FUND_TICKER_JOIN_BENCHMARK_COUNT : QUERY_FUND_TICKER_COUNT) + queryBuilder.toString();
            count = (Integer) masterDataDAO.get(IConstants.ControlPathTypes.GET_RESULT_COUNT, query);
        }

        query = (benchmark > 0 ? QUERY_FUND_TICKER_JOIN_BENCHMARK : QUERY_FUND_TICKER) + queryBuilder.toString() + queryPaginationBuilder.toString();

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search fixed income
        List<FundTickerDTO> fundTickerDTOs = (List<FundTickerDTO>)
                masterDataDAO.get(IConstants.ControlPathTypes.FUND_TICKER_SEARCH, requestDBDTO);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Fund Ticker Search Query : " + query);
        }

        return new SearchObject(count, fundTickerDTOs);
    }

    private void addProfileFiltersToFundSearch(Map<String, Object> searchMap, StringBuilder queryBuilder){
        String language = (searchMap.get(IConstants.LANGUAGE) != null) ? ((String) searchMap.get(IConstants.LANGUAGE)).toUpperCase() : DEFAULT_LANG;
        String fundName = (String) searchMap.get(IConstants.COMPANY);
        String fundClass = (String) searchMap.get(IConstants.FUND_CLASS);
        String countryCode = (String) searchMap.get(IConstants.COUNTRY_CODE);
        String currency = (String) searchMap.get(IConstants.CURRENCY);

        int benchmark = (Integer) searchMap.get(IConstants.BENCHMARK);

        double sTNavInMil = ((Double[]) searchMap.get(IConstants.TNA_USD))[0];
        double eTNavInMil = ((Double[]) searchMap.get(IConstants.TNA_USD))[1];

        String fundDuration = (String) searchMap.get(IConstants.FUND_DURATION);

        String sEstbDate = ((String[]) searchMap.get(IConstants.ESTB_DATE))[0];
        String eEstbDate = ((String[]) searchMap.get(IConstants.ESTB_DATE))[1];

        //add fund name
        if (fundName != null && !fundName.isEmpty()) {
            queryBuilder.append(QUERY_AND).append(QUERY_BRACKET_OPEN);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.LangSpecificDatabaseColumns.TICKER_SHORT_DESCRIPTION + language, fundName, false, false, false, true, false);
            queryBuilder.append(QUERY_OR);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.LangSpecificDatabaseColumns.TICKER_LONG_DESCRIPTION + language, fundName, false, false, false, true, false);
            queryBuilder.append(QUERY_BRACKET_CLOSE);
        }
        //add fund classification
        if (fundClass != null && !fundClass.equals("-1")) {
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FUND_CLASS, fundClass, true, true);
        }
        //add country
        DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE, countryCode, false, true, true);
        //add currency
        DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.CURRENCY_ID, currency, false, true);
        //add focus
        addFundFocusCountryRegionFiltersToFundSearch(searchMap, queryBuilder);
        //add benchmark
        if (benchmark > 0) {
            DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.BENCHMARK_TICKER, String.valueOf(benchmark), true, true);
        }
        //add t nav
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TNAV_USD, sTNavInMil, eTNavInMil, true);
        //add fund duration
        DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TICKER_CLASS_L2, fundDuration, true, true);
        //add established date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.ESTB_DATE, sEstbDate, eEstbDate, true, dbType);
    }

    private void addFundFocusCountryRegionFiltersToFundSearch(Map<String, Object> searchMap, StringBuilder queryBuilder){
        boolean isFocusCountryOrRegionCriteria = false;
        if (searchMap.get(IConstants.MATCH_EACH_COUNTRY_OR_REGION) != null) {
            isFocusCountryOrRegionCriteria = (Boolean) searchMap.get(IConstants.MATCH_EACH_COUNTRY_OR_REGION);
        }

        String focusedCountries = (String) searchMap.get(IConstants.FOCUSED_COUNTRIES);
        String focusedRegions = (String) searchMap.get(IConstants.FOCUSED_REGIONS);

        // focused country OR focused region LIKE criteria
        if (isFocusCountryOrRegionCriteria) {
            if ((focusedCountries != null && !focusedCountries.isEmpty()) || (focusedRegions != null && !focusedRegions.isEmpty())) {
                queryBuilder.append(QUERY_AND).append(QUERY_BRACKET_OPEN);
                if (focusedCountries != null && !focusedCountries.isEmpty()) {
                    List<String> focusCountriesAsList = Arrays.asList(focusedCountries.toUpperCase().split(","));
                    DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FOCUSED_COUNTRIES, focusCountriesAsList, true, false, false, QUERY_OR);
                    if(focusedRegions != null && !focusedRegions.isEmpty()) {
                        queryBuilder.append(QUERY_OR);
                    }
                }

                if (focusedRegions != null && !focusedRegions.isEmpty()) {
                    List<String> focusRegionsAsList = Arrays.asList(focusedRegions.toUpperCase().split(","));
                    DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FOCUSED_REGIONS, focusRegionsAsList, true, false, false, QUERY_OR);
                }
                queryBuilder.append(QUERY_BRACKET_CLOSE);
            }
        }else{//FOCUSED_COUNTRIES = 'AAA,BBB' (exact match is required)
            DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FOCUSED_COUNTRIES, focusedCountries, false, true);
            DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FOCUSED_REGIONS, focusedRegions, false, true);
        }
    }

    private void addPerformanceFiltersToFundSearch(Map<String, Object> searchMap, StringBuilder queryBuilder){
        double sChange3m = ((Double[]) searchMap.get(IConstants.CHANGE_PER_3M))[0];
        double eChange3m = ((Double[]) searchMap.get(IConstants.CHANGE_PER_3M))[1];
        double sYearToDateChangePct = ((Double[]) searchMap.get(IConstants.CHANGE_PER_YTD))[0];
        double eYearToDateChangePct = ((Double[]) searchMap.get(IConstants.CHANGE_PER_YTD))[1];
        double sOneYearChangePct = ((Double[]) searchMap.get(IConstants.CHANGE_PER_YEAR))[0];
        double eOneYearChangePct = ((Double[]) searchMap.get(IConstants.CHANGE_PER_YEAR))[1];

        double sMonthlyReturn3Y = ((Double[]) searchMap.get(IConstants.MONTHLY_RETURN_3Y))[0];
        double eMonthlyReturn3Y = ((Double[]) searchMap.get(IConstants.MONTHLY_RETURN_3Y))[1];
        double sMonthlyReturn5Y = ((Double[]) searchMap.get(IConstants.MONTHLY_RETURN_5Y))[0];
        double eMonthlyReturn5Y = ((Double[]) searchMap.get(IConstants.MONTHLY_RETURN_5Y))[1];
        double sAnnualReturn3Y = ((Double[]) searchMap.get(IConstants.ANNUAL_RETURN_3Y))[0];
        double eAnnualReturn3Y = ((Double[]) searchMap.get(IConstants.ANNUAL_RETURN_3Y))[1];
        double sAnnualReturn5Y = ((Double[]) searchMap.get(IConstants.ANNUAL_RETURN_5Y))[0];
        double eAnnualReturn5Y = ((Double[]) searchMap.get(IConstants.ANNUAL_RETURN_5Y))[1];
        double sStdDevAnnualReturn3Y = ((Double[]) searchMap.get(IConstants.STD_DEVIATION_ANNUAL_RETURN_3Y))[0];
        double eStdDevAnnualReturn3Y = ((Double[]) searchMap.get(IConstants.STD_DEVIATION_ANNUAL_RETURN_3Y))[1];
        double sStdDevAnnualReturn5Y = ((Double[]) searchMap.get(IConstants.STD_DEVIATION_ANNUAL_RETURN_5Y))[0];
        double eStdDevAnnualReturn5Y = ((Double[]) searchMap.get(IConstants.STD_DEVIATION_ANNUAL_RETURN_5Y))[1];

        //add 3 month change
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.CHG_3M, sChange3m, eChange3m, true);
        //add per change year to date
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.CHG_PER_YTD, sYearToDateChangePct, eYearToDateChangePct, true);
        //add per change year
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.CHG_PER_1Y, sOneYearChangePct, eOneYearChangePct, true);
        //add 3 year monthly return
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.THREE_YEAR_MONTHLY_RETURN, sMonthlyReturn3Y, eMonthlyReturn3Y, true);
        //add 5 year monthly return
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FIVE_YEAR_MONTHLY_RETURN, sMonthlyReturn5Y, eMonthlyReturn5Y, true);
        //add 3 year annual return
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.THREE_YEAR_ANNUAL_RETURN, sAnnualReturn3Y, eAnnualReturn3Y, true);
        //add 5 year annual return
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FIVE_YEAR_ANNUAL_RETURN, sAnnualReturn5Y, eAnnualReturn5Y, true);
        //add 3 year annual return std dev
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.THREE_YR_ANNUAL_RETURN_STD_DEV, sStdDevAnnualReturn3Y, eStdDevAnnualReturn3Y, true);
        //add 5 year annual return std dev
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.FIVE_YR_ANNUAL_RETURN_STD_DEV, sStdDevAnnualReturn5Y, eStdDevAnnualReturn5Y, true);
    }

    private void addManagementAndSubscriptionFiltersToFundSearch(Map<String, Object> searchMap, StringBuilder queryBuilder){
        String fundIssuer = (String) searchMap.get(IConstants.ISSUER_NAME);
        String fundManager = (String) searchMap.get(IConstants.MANAGED_COMPANIES);

        String managedCountries = (String) searchMap.get(IConstants.MANAGED_COUNTRY);
        String issuedCountries = (String) searchMap.get(IConstants.ISSUED_COUNTRY);

        String fee1 = (String) searchMap.get(IConstants.FUND_FEE1);
        String fee2 = (String) searchMap.get(IConstants.FUND_FEE2);

        //fund issuer
        DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.ISSUED_COMPANIES, fundIssuer, true, false, false, true);
        //fund manager
        DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.MANAGED_COMPANIES, fundManager, true, false, false, true);

        // managed countries
        if (managedCountries != null && !managedCountries.isEmpty()) {
            List<String> managedCountriesAsList = Arrays.asList(managedCountries.toUpperCase().split(","));
            queryBuilder.append(QUERY_AND);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.MANAGED_COUNTRIES, managedCountriesAsList, true, false, false, QUERY_OR);
        }
        if (issuedCountries != null && !issuedCountries.isEmpty()) {
            String c = issuedCountries.toUpperCase();
            List<String> issuedCountriesAsList = Arrays.asList(c.split(","));
            queryBuilder.append(QUERY_AND).append(QUERY_BRACKET_OPEN);
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.COUNTRY_CODE, c, false, false);
            queryBuilder.append(QUERY_OR);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.ISSUED_COUNTRIES, issuedCountriesAsList, true, false, false, QUERY_OR);
            queryBuilder.append(QUERY_BRACKET_CLOSE);
        }

        //add fees 1
        if (!fee1.isEmpty() && fee1.indexOf("|") > 0) {
            String[] d = fee1.split("\\|");
            String[] dd = d[1].split(",");

            String column = getDBField(d[0], null);
            double startValue = dd[0].isEmpty() ? -1 : Double.parseDouble(dd[0]);
            double endValue = dd.length > 1 ? (dd[1].isEmpty() ? -1 : Double.parseDouble(dd[1])) : -1;
            DBUtils.addBetweenQueryPhrase(queryBuilder, column, startValue, endValue, true);
        }

        //add fees 2
        if (!fee2.isEmpty() && fee2.indexOf("|") > 0) {
            String[] d = fee2.split("\\|");
            String[] dd = d[1].split(",");

            String column = getDBField(d[0], null);
            double startValue = dd[0].isEmpty() ? -1 : Double.parseDouble(dd[0]);
            double endValue = dd.length > 1 ? (dd[1].isEmpty() ? -1 : Double.parseDouble(dd[1])) : -1;
            DBUtils.addBetweenQueryPhrase(queryBuilder, column, startValue, endValue, true);
        }
    }

    /**
     * Search treasury bills according to tenor, country, currency, coupon rate, issuing size, issuing date, maturity date
     *
     * @param searchMap parameters
     * @return FixedIncomeTickerDTO List of treasury bills
     */
    @SuppressWarnings("unchecked")
    public List<FixedIncomeTickerDTO> searchFixedIncome(Map<String, Object> searchMap) {
        return (List<FixedIncomeTickerDTO>) searchFixedIncome(searchMap, false).getValues();
    }

    /**
     * Search funds according to the required text country and class
     *
     * @param searchMap search parameters
     * @param loadCount to fetch total result count
     * @return FixedIncomeTickerDTO List
     */
    @SuppressWarnings("unchecked")
    public SearchObject searchFixedIncome(Map<String, Object> searchMap, boolean loadCount) {
        String bondType = (String) searchMap.get(IConstants.BOND_TYPE);
        String sukukType = (String) searchMap.get(IConstants.SUKUK_TYPE);

        //should be TICKER_COUNTRY_CODE
        String countryOfIssueList = (String) searchMap.get(IConstants.TICKER_COUNTRY);

        String issuerName = (String) searchMap.get(IConstants.ISSUER_NAME);

        String industry = (String) searchMap.get(IConstants.INDUSTRY);
        int couponFrequency = (Integer) searchMap.get(IConstants.COUPON_FREQUENCY);
        String currency = (String) searchMap.get(IConstants.CURRENCY);
        int sMaturityPeriod = ((Integer[]) searchMap.get(IConstants.MATURITY_PERIOD))[0];
        int eMaturityPeriod = ((Integer[]) searchMap.get(IConstants.MATURITY_PERIOD))[1];
        double sCouponRate = ((Double[]) searchMap.get(IConstants.COUPON_RATE))[0];
        double eCouponRate = ((Double[]) searchMap.get(IConstants.COUPON_RATE))[1];
        double sIssuingSize = ((Double[]) searchMap.get(IConstants.AMOUNT_ISSUED_USD_LATEST))[0];
        double eIssuingSize = ((Double[]) searchMap.get(IConstants.AMOUNT_ISSUED_USD_LATEST))[1];
        String sIssuingDate = ((String[]) searchMap.get(IConstants.DATE_OF_ISSUE))[0];
        String eIssuingDate = ((String[]) searchMap.get(IConstants.DATE_OF_ISSUE))[1];
        String sMaturityDate = ((String[]) searchMap.get(IConstants.MATURITY_DATE))[0];
        String eMaturityDate = ((String[]) searchMap.get(IConstants.MATURITY_DATE))[1];

        String sAuctionDate = null;
        String eAuctionDate = null;
        if (searchMap.containsKey(IConstants.AUCTION_DATE)) {
            sAuctionDate = ((String[]) searchMap.get(IConstants.AUCTION_DATE))[0];
            eAuctionDate = ((String[]) searchMap.get(IConstants.AUCTION_DATE))[1];
        }

        String language = ((String) searchMap.get(IConstants.LANGUAGE)).toUpperCase();

        String sortField = getDBField((String) searchMap.get(IConstants.SORT_FIELD), null);
        String sortOrder = (String) searchMap.get(IConstants.SORT_ORDER);

        int pageIndex = (Integer) searchMap.get(IConstants.PAGE_INDEX);
        int pageSize = (Integer) searchMap.get(IConstants.PAGE_SIZE);

        int start = pageIndex * pageSize;

        int count = -1;

        StringBuilder queryBuilder = new StringBuilder(QUERY_WHERE);
        StringBuilder queryPaginationBuilder = new StringBuilder();

        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_INSTRUMENT_TYPE_FILTER).append(QUERY_AND).append(QUERY_STATUS_FILTER);

        //add instrument type
        if (!bondType.equals(IConstants.ZERO) && !sukukType.equals(IConstants.ZERO)) {
            //and (BOND_TYPE in () or SUKUK_TYPE in ())
            queryBuilder.append(QUERY_AND).append(QUERY_BRACKET_OPEN);
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.BOND_TYPE, bondType, true, false);
            queryBuilder.append(QUERY_OR);
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.SUKUK_TYPE, sukukType, true, false);
            queryBuilder.append(QUERY_BRACKET_CLOSE);
        } else {
            if (!bondType.equals(IConstants.ZERO)) {
                DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.BOND_TYPE, bondType, true, true);
            }
            if (!sukukType.equals(IConstants.ZERO)) {
                DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.SUKUK_TYPE, sukukType, true, true);
            }
        }

        //add issuer country
        DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE, countryOfIssueList, false, true);

        //add issuer industry - gicsl3
        if (!industry.isEmpty()) {
            String gicsColumn = QUERY_SUBSTR_FUNC + QUERY_BRACKET_OPEN + DBConstants.DatabaseColumns.GICS_L4_CODE +
                    QUERY_COMMA + 1 + QUERY_COMMA + 6 + QUERY_BRACKET_CLOSE;
            DBUtils.addInQueryPhrase(queryBuilder, gicsColumn, industry, false, true);
        }

        //add coupon frequency
        if (couponFrequency > 0) {
            DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.COUPON_FREQUENCY, String.valueOf(couponFrequency), true, true);
        }

        //issuer name
        if (!issuerName.isEmpty()) {
            queryBuilder.append(QUERY_AND).append(QUERY_BRACKET_OPEN);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.LangSpecificDatabaseColumns.COMPANY_NAME + language,issuerName,false, false, false, true, false);
            queryBuilder.append(QUERY_OR);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.LangSpecificDatabaseColumns.TRADING_NAME + language,issuerName,false, false, false, true, false);
            queryBuilder.append(QUERY_BRACKET_CLOSE);
        }

        //add currency
        DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.CURRENCY_ID, currency, false, true);

        //add maturity period
        String maturityPeriodColumn = QUERY_CAST_FUNC + QUERY_BRACKET_OPEN + DBConstants.DatabaseColumns.MATURITY_PERIOD +
                QUERY_AS_NUMERIC + QUERY_BRACKET_CLOSE;
        DBUtils.addBetweenQueryPhrase(queryBuilder, maturityPeriodColumn, sMaturityPeriod, eMaturityPeriod, true);

        //add coupon rate
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.COUPON_RATE, sCouponRate, eCouponRate, true);

        //add issuing size
        //FOR SCREENER Issuing Size -> AMOUNT_ISSUED_USD_LATEST
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.AMOUNT_ISSUED_USD_LATEST, sIssuingSize, eIssuingSize, true);

        //add issuing date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.DATE_OF_ISSUE, sIssuingDate, eIssuingDate, true, dbType);

        //add maturity date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.MATURITY_DATE, sMaturityDate, eMaturityDate, true, dbType);

        //add auction date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.AUCTION_DATE, sAuctionDate, eAuctionDate, true, dbType);

        //add ordering
        queryPaginationBuilder.append(QUERY_ORDER);
        if (sortField == null || sortField.isEmpty()) {
            queryPaginationBuilder.append(DBConstants.DatabaseColumns.MATURITY_DATE);
        } else {
            if (sortField.equalsIgnoreCase(DBConstants.DatabaseColumns.MATURITY_PERIOD)) {
                queryPaginationBuilder.append(QUERY_CAST_FUNC).append(QUERY_BRACKET_OPEN).append(sortField).append(QUERY_AS_NUMERIC).append(QUERY_BRACKET_CLOSE);
            } else {
                queryPaginationBuilder.append(sortField);
            }
        }
        //add ordering type
        queryPaginationBuilder.append(QUERY_SPACE).append(sortOrder);
        if (start >= 0) {
            if (start == 0) {
                queryPaginationBuilder.append(DBUtils.getImdbPaginationQuery(0, pageSize + 1, dbType));
            } else {
                queryPaginationBuilder.append(DBUtils.getImdbPaginationQuery(start, pageSize + 1, dbType));
            }
        }

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        String query;

        if (loadCount) {
            query = QUERY_FIXED_INCOME_TICKER_COUNT + queryBuilder.toString();
            count = (Integer) masterDataDAO.get(IConstants.ControlPathTypes.GET_RESULT_COUNT, query);
        }

        query = QUERY_FIXED_INCOME_TICKER + queryBuilder.toString() + queryPaginationBuilder.toString();

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search fixed income
        List<FixedIncomeTickerDTO> fixedIncomeTickerDTOs = (List<FixedIncomeTickerDTO>)
                masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_SEARCH, requestDBDTO);

        return new SearchObject(count, fixedIncomeTickerDTOs);
    }

    @SuppressWarnings("unchecked")
    public SearchObject searchIndexes(Map<String, Object> searchMap, boolean loadCount){

        String language = ((String) searchMap.get(IConstants.LANGUAGE)).toUpperCase();

        String sortField = getDBField((String) searchMap.get(IConstants.SORT_FIELD), language);
        String sortOrder = (String) searchMap.get(IConstants.SORT_ORDER);

        int pageIndex = Integer.valueOf((String) searchMap.get(IConstants.PAGE_INDEX));
        int pageSize = Integer.valueOf((String) searchMap.get(IConstants.PAGE_SIZE));

        int start = pageIndex * pageSize;

        int count = -1;

        StringBuilder queryBuilder = new StringBuilder(QUERY_WHERE);
        StringBuilder queryPaginationBuilder = new StringBuilder();

        boolean isFirstQuery = true;
        if (searchMap.containsKey(IConstants.SYMBOL)) {
            String symbol = (String) searchMap.get(IConstants.SYMBOL);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TICKER_ID,symbol,false, false, false, true, false);
            isFirstQuery = false;
        }

        if (searchMap.containsKey(IConstants.SYMBOL_DESC)) {
            String symbolDesc = (String) searchMap.get(IConstants.SYMBOL_DESC);
            if(!isFirstQuery){
                queryBuilder.append(QUERY_AND);
            }
            queryBuilder.append(QUERY_BRACKET_OPEN);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.LangSpecificDatabaseColumns.TICKER_LONG_DESCRIPTION + language,symbolDesc,false, false, false, true, false);
            queryBuilder.append(QUERY_OR);
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.LangSpecificDatabaseColumns.TICKER_SHORT_DESCRIPTION + language,symbolDesc,false, false, false, true, false);
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            isFirstQuery = false;
        }

        if (searchMap.containsKey(IConstants.TYPE)) {
            String type = (String) searchMap.get(IConstants.TYPE);
            DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TICKER_CLASS_L2, type, true, !isFirstQuery);
            isFirstQuery = false;
        }

        if (searchMap.containsKey(IConstants.EXCHANGES)) {
            if(!isFirstQuery){
                queryBuilder.append(QUERY_AND);
            }
            String exchanges = (String) searchMap.get(IConstants.EXCHANGES);
            List<String> exchangeList = Arrays.asList(exchanges.split(String.valueOf(IConstants.Delimiter.COMMA)));
            DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.EXCHANGES,exchangeList,true, false, false, QUERY_OR);
            isFirstQuery = false;
        }

        boolean isCountryAvailable = false;
        if (searchMap.containsKey(IConstants.COUNTRIES) || searchMap.containsKey(IConstants.REGIONS)) {

            if(!isFirstQuery){
                queryBuilder.append(QUERY_AND);
            }

            queryBuilder.append(QUERY_BRACKET_OPEN);
            if(searchMap.containsKey(IConstants.COUNTRIES)){
                String exchanges = (String) searchMap.get(IConstants.COUNTRIES);
                List<String> countryList = Arrays.asList(exchanges.split(String.valueOf(IConstants.Delimiter.COMMA)));
                DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.COUNTRIES, countryList, true, false, false, QUERY_OR);
                isCountryAvailable = true;
            }
            if (searchMap.containsKey(IConstants.REGIONS)) {
                if (isCountryAvailable) {
                    queryBuilder.append(QUERY_OR);
                }
                String exchanges = (String) searchMap.get(IConstants.REGIONS);
                List<String> regionList = Arrays.asList(exchanges.split(String.valueOf(IConstants.Delimiter.COMMA)));
                DBUtils.addLikeQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.REGIONS, regionList, true, false, false, QUERY_OR);
            }
            queryBuilder.append(QUERY_BRACKET_CLOSE);
            isFirstQuery = false;
        }

        if (searchMap.containsKey(IConstants.PROVIDERS))   {
            String providers = String.valueOf(searchMap.get(IConstants.PROVIDERS));
            DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.PROVIDER, providers, true, !isFirstQuery);
        }

        //add ordering
        queryPaginationBuilder.append(QUERY_ORDER);
        if (sortField == null || sortField.isEmpty()) {
            queryPaginationBuilder.append(DBConstants.DatabaseColumns.LAST_TRADE_DATE);
        } else {
            if (sortField.endsWith(IConstants.Delimiter.UNDERSCORE + language)) {
                queryPaginationBuilder.append(DBConstants.CommonDatabaseParams.QUERY_UPPER)
                        .append(QUERY_BRACKET_OPEN)
                        .append(sortField)
                        .append(QUERY_BRACKET_CLOSE);
            } else {
                queryPaginationBuilder.append(sortField);
            }
        }

        //add ordering type
        queryPaginationBuilder.append(QUERY_SPACE).append(sortOrder);

        //get null values of ordering type at the end of the result set
        queryPaginationBuilder.append(NULLS_LAST);

        if (start >= 0) {
            if (start == 0) {
                queryPaginationBuilder.append(DBUtils.getImdbPaginationQuery(0, pageSize + 1, dbType));
            } else {
                queryPaginationBuilder.append(DBUtils.getImdbPaginationQuery(start, pageSize + 1, dbType));
            }
        }

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        String query;

        if (loadCount) {
            query = QUERY_INDEX_TICKER_COUNT + queryBuilder.toString();
            count = (Integer) masterDataDAO.get(IConstants.ControlPathTypes.GET_RESULT_COUNT, query);
        }

        query = QUERY_INDEX_TICKER_SELECT + queryBuilder.toString() + queryPaginationBuilder.toString();

        if(LOG.isDebugEnabled()){
            LOG.debug("#####--INDEX-SEARCH-QUERY--#### | " + query + " | #####--INDEX-SEARCH-QUERY--####");
        }
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search fixed income
        List<com.dfn.alerts.beans.tickers.IndexDTO> indexDTOs =
                (List<com.dfn.alerts.beans.tickers.IndexDTO>) masterDataDAO.get(IConstants.ControlPathTypes.INDEX_DATA_SEARCH, requestDBDTO);

        return new SearchObject(count,indexDTOs);

    }

    /**
     * get the mapping between database columns
     *
     * @return mapped DB column name
     */
    private String getDBField(String parameter, String language) {

        if (parameter.equalsIgnoreCase(IConstants.MATURITY_DATE)) {
            return DBConstants.DatabaseColumns.MATURITY_DATE;
        } else if (parameter.equalsIgnoreCase(IConstants.DATE_OF_ISSUE)) {
            return DBConstants.DatabaseColumns.DATE_OF_ISSUE;
        } else if (parameter.equalsIgnoreCase(IConstants.SHORT_DESC_LAN)) {
            return DBConstants.DatabaseColumns.SHORT_DESC_LAN;
        } else if (parameter.equalsIgnoreCase(IConstants.COUNTRY_CODE)) {
            return DBConstants.DatabaseColumns.COUNTRY_CODE;
        } else if (parameter.equalsIgnoreCase(IConstants.TICKER_COUNTRY)) {
            return DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE;
        } else if (parameter.equalsIgnoreCase(IConstants.MATURITY_PERIOD)) {
            return DBConstants.DatabaseColumns.MATURITY_PERIOD;
        } else if (parameter.equalsIgnoreCase(IConstants.ISSUE_WGT_AVG_RATE)) {
            return DBConstants.DatabaseColumns.ISSUE_WGT_AVG_RATE;
        } else if (parameter.equalsIgnoreCase(IConstants.AMOUNT_ISSUED)) {
            return DBConstants.DatabaseColumns.AMOUNT_ISSUED;
        } else if (parameter.equalsIgnoreCase(IConstants.AMOUNT_ISSUED_USD)) {
            return DBConstants.DatabaseColumns.AMOUNT_ISSUED_USD;
        } else if (parameter.equalsIgnoreCase(IConstants.AMOUNT_ISSUED_USD_LATEST)) {
            return DBConstants.DatabaseColumns.AMOUNT_ISSUED_USD_LATEST;
        } else if (parameter.equalsIgnoreCase(IConstants.REQUIRED_AMOUNT)) {
            return DBConstants.DatabaseColumns.REQUIRED_AMOUNT;
        } else if (parameter.equalsIgnoreCase(IConstants.ISSUE_MIN_RATE)) {
            return DBConstants.DatabaseColumns.ISSUE_MIN_RATE;
        } else if (parameter.equalsIgnoreCase(IConstants.ISSUE_MAX_RATE)) {
            return DBConstants.DatabaseColumns.ISSUE_MAX_RATE;
        } else if (parameter.equalsIgnoreCase(IConstants.COUPON_RATE)) {
            return DBConstants.DatabaseColumns.COUPON_RATE;
        } else if (parameter.equalsIgnoreCase(IConstants.COMPANY)) {
            return DBConstants.DatabaseColumns.COMPANY_NAME_LAN;
        } else if (parameter.equalsIgnoreCase(IConstants.COMPANY_ID)) {
            return DBConstants.DatabaseColumns.COMPANY_ID;
        } else if (parameter.equalsIgnoreCase(IConstants.COUPON_FREQUENCY)) {
            return DBConstants.DatabaseColumns.COUPON_FREQUENCY;
        } else if (parameter.equalsIgnoreCase(IConstants.CHANGE_PER_3M)) {
            return DBConstants.DatabaseColumns.CHG_PER_3M;
        } else if (parameter.equalsIgnoreCase(IConstants.CHANGE_PER_YEAR)) {
            return DBConstants.DatabaseColumns.CHG_PER_1Y;
        } else if (parameter.equalsIgnoreCase(IConstants.TNA_USD)) {
            return DBConstants.DatabaseColumns.TNAV_USD;
        } else if (parameter.equalsIgnoreCase(IConstants.FUND_CLASS)) {
            return DBConstants.DatabaseColumns.FUND_CLASS;
        } else if (parameter.equalsIgnoreCase(IConstants.ADMIN_FEE)) {
            return DBConstants.DatabaseColumns.ADMIN_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.MGT_FEE)) {
            return DBConstants.DatabaseColumns.MGT_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.PERFORMANCE_FEE)) {
            return DBConstants.DatabaseColumns.PERFORMANCE_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.REDEMPTION_FEE)) {
            return DBConstants.DatabaseColumns.REDEMPTION_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.SUBSCRIPTION_FEE)) {
            return DBConstants.DatabaseColumns.SUBSCRIPTION_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.CUSTODIAN_FEE)) {
            return DBConstants.DatabaseColumns.CUSTODIAN_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.SUBSEQUENT_SUBS)) {
            return DBConstants.DatabaseColumns.SUBSEQUENT_SUBS;
        } else if (parameter.equalsIgnoreCase(IConstants.MIN_SUBSCRIPTION)) {
            return DBConstants.DatabaseColumns.MIN_SUBSCRIPTION;
        } else if (parameter.equalsIgnoreCase(IConstants.SERVICE_FEE)) {
            return DBConstants.DatabaseColumns.SERVICE_FEE;
        } else if (parameter.equalsIgnoreCase(IConstants.OTHER_EXP)) {
            return DBConstants.DatabaseColumns.OTHER_EXP;
        } else if (parameter.equalsIgnoreCase(IConstants.NEXT_COUPON_DATE)) {
            return DBConstants.DatabaseColumns.NEXT_COUPON_DATE;
        } else if (parameter.equalsIgnoreCase(IConstants.AUCTION_DATE)) {
            return DBConstants.DatabaseColumns.AUCTION_DATE;
        } else if (parameter.equalsIgnoreCase(IConstants.COMP_NAME)) {
            return DBConstants.DatabaseColumns.COMPANY_NAME_LAN + IConstants.Delimiter.UNDERSCORE + language;
        } else if (parameter.equalsIgnoreCase(IConstants.GICS_L3_DESC)) {
            return DBConstants.DatabaseColumns.GICSL3_LAN + IConstants.Delimiter.UNDERSCORE + language;
        } else if (parameter.equalsIgnoreCase(IConstants.CITY)) {
            return DBConstants.DatabaseColumns.CITY_SHORT_DESC + IConstants.Delimiter.UNDERSCORE + language;
        } else if (parameter.equalsIgnoreCase(IConstants.COUNTRY)) {
            return DBConstants.DatabaseColumns.COUNTRY_DESC + IConstants.Delimiter.UNDERSCORE + language;
        } else if (parameter.equalsIgnoreCase(IConstants.TICKER_LONG)) {
            return DBConstants.DatabaseColumns.TICKER_LONG_DESCRIPTION + IConstants.Delimiter.UNDERSCORE + language;
        } else if (parameter.equalsIgnoreCase(IConstants.WEB)) {
            return DBConstants.DatabaseColumns.WEB;
        } else if (parameter.equalsIgnoreCase(IConstants.COMPANY_TYPE)) {
            return DBConstants.DatabaseColumns.COMPANY_TYPE;
        } else if (parameter.equalsIgnoreCase(IConstants.LATEST_DATE)) {
            return DBConstants.MergeAndAcquireColumns.COMPLETION_DATE;
        } else if (parameter.equalsIgnoreCase(IConstants.SYMBOL)){
            return DBConstants.DatabaseColumns.TICKER_ID;
        }else if (parameter.equalsIgnoreCase(IConstants.TICKER_CLASS_L2)){
            return DBConstants.DatabaseColumns.TICKER_CLASS_L2;
        }else {
            return IConstants.EMPTY;
        }
    }

    /**
     * Search treasury bills according to tenor, country, currency, coupon rate, issuing size, issuing date, maturity date
     *
     * @param searchMap parameters
     * @return FixedIncomeTickerDTO List of treasury bills
     */
    @SuppressWarnings("unchecked")
    public List<FixedIncomeTickerDTO> searchTreasuryBills(Map<String, Object> searchMap) {
        return (List<FixedIncomeTickerDTO>) searchTreasuryBills(searchMap, false).getValues();
    }

    /**
     * Search treasury bills according to tenor, country, currency, coupon rate, issuing size, issuing date, maturity date
     *
     * @param searchMap parameters
     * @param loadCount to fetch total result count
     * @return SearchObject List of treasury bills with total result count
     */
    @SuppressWarnings("unchecked")
    public SearchObject searchTreasuryBills(Map<String, Object> searchMap, boolean loadCount) {

        int pageIndex = (Integer) searchMap.get(IConstants.PAGE_INDEX);
        int pageSize = (Integer) searchMap.get(IConstants.PAGE_SIZE);

        String tenor = (String) searchMap.get(IConstants.MATURITY_PERIOD);
        String issuedCountry = (String) searchMap.get(IConstants.TICKER_COUNTRY);
        String currency = (String) searchMap.get(IConstants.CURRENCY);

        double sWAvgRate = ((Double[]) searchMap.get(IConstants.ISSUE_WGT_AVG_RATE))[0];
        double eWAvgRate = ((Double[]) searchMap.get(IConstants.ISSUE_WGT_AVG_RATE))[1];

        double sIssuingSize = ((Double[]) searchMap.get(IConstants.AMOUNT_ISSUED_USD_LATEST))[0];
        double eIssuingSize = ((Double[]) searchMap.get(IConstants.AMOUNT_ISSUED_USD_LATEST))[1];

        String sIssuingDate = null;
        String eIssuingDate = null;
        if (searchMap.containsKey(IConstants.DATE_OF_ISSUE)) {
            sIssuingDate = ((String[]) searchMap.get(IConstants.DATE_OF_ISSUE))[0];
            eIssuingDate = ((String[]) searchMap.get(IConstants.DATE_OF_ISSUE))[1];
        }

        String sAuctionDate = null;
        String eAuctionDate = null;
        if (searchMap.containsKey(IConstants.AUCTION_DATE)) {
            sAuctionDate = ((String[]) searchMap.get(IConstants.AUCTION_DATE))[0];
            eAuctionDate = ((String[]) searchMap.get(IConstants.AUCTION_DATE))[1];
        }

        String sMaturityDate = ((String[]) searchMap.get(IConstants.MATURITY_DATE))[0];
        String eMaturityDate = ((String[]) searchMap.get(IConstants.MATURITY_DATE))[1];

        String sortField = getDBField((String) searchMap.get(IConstants.SORT_FIELD), null);
        String sortOrder = (String) searchMap.get(IConstants.SORT_ORDER);

        int count = -1;

        int start = pageIndex * pageSize;

        StringBuilder queryBuilder = new StringBuilder(QUERY_WHERE);
        StringBuilder queryPaginationBuilder = new StringBuilder();

        queryBuilder.append(QUERY_FIXED_INCOME_TREASURY_BILLS_TICKER_INSTRUMENT_TYPE_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);

        //add tenor
        DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.MATURITY_PERIOD, tenor, false, true);

        //add country
        DBUtils.addInQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE, issuedCountry, false, true);

        //add currency
        DBUtils.addEqualQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.CURRENCY_ID, currency, false, true);

        //add weighted avg rate
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.ISSUE_WGT_AVG_RATE, sWAvgRate, eWAvgRate, true);

        //add issue size
        DBUtils.addBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.AMOUNT_ISSUED_USD_LATEST, sIssuingSize, eIssuingSize, true);

        //add issue date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.DATE_OF_ISSUE, sIssuingDate, eIssuingDate, true, dbType);

        //add auction date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.AUCTION_DATE, sAuctionDate, eAuctionDate, true, dbType);

        //add maturity date
        DBUtils.addDateBetweenQueryPhrase(queryBuilder, DBConstants.DatabaseColumns.MATURITY_DATE, sMaturityDate, eMaturityDate, true, dbType);

        //add pagination
        queryPaginationBuilder.append(QUERY_ORDER);
        if (sortField == null || sortField.isEmpty()) {
            queryPaginationBuilder.append(DBConstants.DatabaseColumns.MATURITY_DATE);
        } else {
            if (sortField.equalsIgnoreCase(DBConstants.DatabaseColumns.MATURITY_PERIOD)) {
                queryPaginationBuilder.append(QUERY_CAST_FUNC);
                queryPaginationBuilder.append(QUERY_BRACKET_OPEN);
                queryPaginationBuilder.append(sortField);
                queryPaginationBuilder.append(QUERY_AS_NUMERIC);
                queryPaginationBuilder.append(QUERY_BRACKET_CLOSE);
            } else {
                queryPaginationBuilder.append(sortField);
            }
        }

        queryPaginationBuilder.append(QUERY_SPACE).append(sortOrder);

        queryPaginationBuilder.append(QUERY_SPACE).append(DBUtils.getImdbPaginationQuery(start, pageSize + 1, dbType));

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        String query;

        if (loadCount) {
            query = QUERY_FIXED_INCOME_TICKER_COUNT + queryBuilder.toString();
            count = (Integer) masterDataDAO.get(IConstants.ControlPathTypes.GET_RESULT_COUNT, query);
        }

        query = QUERY_FIXED_INCOME_TICKER + queryBuilder.toString() + queryPaginationBuilder.toString();

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search treasury bills
        List<FixedIncomeTickerDTO> fixedIncomeTickerDTOs = (List<FixedIncomeTickerDTO>)
                masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_SEARCH, requestDBDTO);

        return new SearchObject(count, fixedIncomeTickerDTOs);
    }

    /**
     * Get Fixed Income Ticker for a company
     *
     * @param companyId          company id
     * @param fixedIncomeInsType instrument type id of the required fixed income type
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    public List<FixedIncomeTickerDTO> getFixedIncomeTickersForCompany(int companyId, IConstants.InstrumentTypes fixedIncomeInsType) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(companyId);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID);

        // TODO :: get instrument types from another enum (instrument type group)
        if (fixedIncomeInsType == IConstants.InstrumentTypes.BOND) {
            queryBuilder.append(QUERY_IN);
            queryBuilder.append(QUERY_BRACKET_OPEN);

            queryBuilder.append(IConstants.InstrumentTypes.BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.CONVERTIBLE_BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.GOVERNMENT_BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.CORPORATE_BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.SUKUK.getDefaultValues());

            queryBuilder.append(QUERY_BRACKET_CLOSE);

        } else {
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(fixedIncomeInsType.getDefaultValues());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.MATURITY_DATE);
        queryBuilder.append(QUERY_GREATER_OR_EQUAL_THAN).append(QUERY_BRACKET_OPEN).append(QUERY_QUOTE);
        queryBuilder.append(new java.sql.Date(calendar.getTime().getTime())).append(QUERY_QUOTE).append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.MATURITY_DATE);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search Tickers
        return (List<FixedIncomeTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_SEARCH, requestDBDTO);
    }

    /**
     * Get outstanding fixed income count for a given company
     *
     * @param companyId          - company id that required to get the count
     * @param fixedIncomeInsType - fixed income type(Ex. Treasury Bills)
     * @return Integer outstanding issuance count
     */
    public Integer getFixedIncomeOutstandingIssuancesCount(int companyId, IConstants.InstrumentTypes fixedIncomeInsType) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_COUNT_FIXED_INCOME_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(companyId);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID);

        // TODO :: get instrument types from another enum (instrument type group)
        if (fixedIncomeInsType == IConstants.InstrumentTypes.BOND) {
            queryBuilder.append(QUERY_IN);
            queryBuilder.append(QUERY_BRACKET_OPEN);

            queryBuilder.append(IConstants.InstrumentTypes.BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.CONVERTIBLE_BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.GOVERNMENT_BOND.getDefaultValues());
            queryBuilder.append(QUERY_COMMA);

            queryBuilder.append(IConstants.InstrumentTypes.CORPORATE_BOND.getDefaultValues());

            queryBuilder.append(QUERY_BRACKET_CLOSE);

        } else {
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(fixedIncomeInsType.getDefaultValues());
        }

        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_FI_OUTSTANDING_FILTER);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Integer) masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_OUTSTANDING_COUNT, queryBuilder.toString());
    }

    /**
     * Get Latest Traded Fixed Income Ticker for countries
     *
     * @param countryCodes comma separated list of country codes
     * @return List<FixedIncomeTickerDTO>
     */
    @SuppressWarnings("unchecked")
    public List<FixedIncomeTickerDTO> getLatestTradedFixedIncome(String countryCodes) {
        StringBuilder queryBuilder = new StringBuilder();
        StringBuilder queryBuilderCountryFilter = null;
        queryBuilder.append(QUERY_FI_LATEST_TRADED);
        queryBuilder.append(QUERY_INNER_JOIN);

        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_SELECT);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBUtils.getAddDateQuery(dbType, DBConstants.DateTypes.DAY, -1, QUERY_MAX_TRADED_DATE));
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_FI_LATEST_TRADED_DATE);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_INSTRUMENT_TYPE_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);
        if (!countryCodes.trim().isEmpty()) {
            queryBuilder.append(QUERY_AND);
            queryBuilderCountryFilter = new StringBuilder();
            queryBuilderCountryFilter.append(DBConstants.DatabaseColumns.COUNTRY_OF_ISSUE);
            queryBuilderCountryFilter.append(QUERY_IN);
            queryBuilderCountryFilter.append(QUERY_BRACKET_OPEN);
            queryBuilderCountryFilter.append(QUERY_QUOTE);
            queryBuilderCountryFilter.append(countryCodes.replace(",", "','"));
            queryBuilderCountryFilter.append(QUERY_QUOTE);
            queryBuilderCountryFilter.append(QUERY_BRACKET_CLOSE);
            queryBuilder.append(queryBuilderCountryFilter.toString());
        }
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AS_A);

        queryBuilder.append(QUERY_ON);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_FI_LAST_TRADED_TIME_FILTER);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_INSTRUMENT_TYPE_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);

        if (queryBuilderCountryFilter != null) {
            queryBuilder.append(QUERY_AND);
            queryBuilder.append(QUERY_T_DOT);
            queryBuilder.append(queryBuilderCountryFilter.toString());

        }

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        //Search Tickers
        return (List<FixedIncomeTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_SEARCH, requestDBDTO);
    }

    /**
     * @param countryCode country code
     * @return FixedIncomeTypeCountDTO
     */
    @SuppressWarnings("unchecked")
    public Map<String, FixedIncomeTypeCountDTO> getFixedIncomeTypeCounts(String countryCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_COUNT_SELECT);
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_COUNT_FROM);
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_COUNT_WHERE);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(countryCode);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER_COUNT_GROUP_BY);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, FixedIncomeTypeCountDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_TYPES_COUNT, queryBuilder.toString());
    }

    /**
     * @param countryCode country code
     * @return list of CountryTickerDTO
     */
    @SuppressWarnings("unchecked")
    public List<CountryTickerDTO> getCountryTicker(String countryCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_COUNTRY_TICKER_SELECT);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(countryCode);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (List<CountryTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.COUNTRY_TICKER, queryBuilder.toString());
    }

    /**
     * @param countryCode country code
     * @return list of CountryTickerDTO
     */
    @SuppressWarnings("unchecked")
    public Map<String, CountryTickerDTO> getCountryTickerSnapshot(String countryCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_COUNTRY_TICKER_SNAPSHOT_SELECT);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(countryCode);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, CountryTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.COUNTRY_TICKER_SNAPSHOT, queryBuilder.toString());
    }

    /**
     * get country tickers for give country code and ticker id list
     *
     * @param countryCode country code
     * @param tickerIds   list of ticker ids
     * @return Map, CountryTickerDTO
     */
    @SuppressWarnings("unchecked")
    public Map<String, CountryTickerDTO> getCountryTickerSnapshot(String countryCode, List<String> tickerIds) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_COUNTRY_TICKER_SNAPSHOT_SELECT);
        queryBuilder.append(QUERY_QUOTE).append(countryCode).append(QUERY_QUOTE).append(QUERY_AND);
        queryBuilder.append(TICKER_ID).append(QUERY_IN).append(QUERY_BRACKET_OPEN).append(FormatUtils.getQuotedArrayString(tickerIds, true)).append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        String sql = MessageFormat.format(queryBuilder.toString(), FormatUtils.getQuotedArrayString(tickerIds, true));

        return (Map<String, CountryTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.COUNTRY_TICKER_SNAPSHOT, sql);
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<TickerDTO>> getEquityTickersFromCompanyIdForNews(String companyIdList) {

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(QUERY_TICKER + QUERY_WHERE + DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID + QUERY_IN +
                QUERY_BRACKET_OPEN + IConstants.InstrumentTypes.getEquityAssetClassInstrumentTypes() + QUERY_BRACKET_CLOSE +
                QUERY_AND + DBConstants.DatabaseColumns.COMPANY_ID + QUERY_IN + QUERY_BRACKET_OPEN + companyIdList + QUERY_BRACKET_CLOSE +
                QUERY_AND + QUERY_STATUS_FILTER);
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, List<TickerDTO>>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS_BY_COMPANY_ID_FOR_NEWS, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    /*SELECT TICKER_SERIAL, TICKER_ID, SOURCE_ID, COUNTRY_CODE, TICKER_COUNTRY_CODE
    FROM TICKERS
    WHERE STATUS = 1 AND (
            (
            INSTRUMENT_TYPE_ID IN (0, 60, 61) AND COMPANY_ID IN (
    SELECT DISTINCT COMPANY_ID FROM TICKERS WHERE TICKER_SERIAL IN ()
    )
            )
    OR TICKER_SERIAL IN ()
    )
    UNION
    SELECT TICKER_SERIAL, TICKER_ID, SOURCE_ID, COUNTRY_CODE, TICKER_COUNTRY_CODE
    FROM FUND_TICKERS WHERE TICKER_SERIAL IN () AND STATUS = 1
    UNION
    SELECT TICKER_SERIAL, TICKER_ID, SOURCE_ID, COUNTRY_CODE, TICKER_COUNTRY_CODE
    FROM FIXED_INCOME_TICKERS WHERE TICKER_SERIAL IN () AND STATUS = 1*/
    public Map<String, Object> getTickersFromTickerSerialForNews(String tickerSerials) {
        String query = QUERY_SELECT + TickerDBHelper.getInsertTickerColumnString(getSupportedLanguages()) + QUERY_COMMA +
                DBConstants.DatabaseColumns.TICKER_SERIAL + FROM + DBConstants.TablesIMDB.TABLE_TICKERS + QUERY_WHERE + QUERY_STATUS_FILTER +
                QUERY_AND + QUERY_BRACKET_OPEN + QUERY_BRACKET_OPEN + DBConstants.DatabaseColumns.INSTRUMENT_TYPE_ID + QUERY_IN +
                QUERY_BRACKET_OPEN + IConstants.InstrumentTypes.getEquityAssetClassInstrumentTypes() + QUERY_BRACKET_CLOSE + QUERY_AND +
                DBConstants.DatabaseColumns.COMPANY_ID + QUERY_IN + QUERY_BRACKET_OPEN + QUERY_SELECT + QUERY_DISTINCT +
                DBConstants.DatabaseColumns.COMPANY_ID + FROM + DBConstants.TablesIMDB.TABLE_TICKERS + QUERY_WHERE +
                DBConstants.DatabaseColumns.COMPANY_ID + QUERY_GREATER_THAN + ZERO + QUERY_AND + DBConstants.DatabaseColumns.TICKER_SERIAL +
                QUERY_IN + QUERY_BRACKET_OPEN + tickerSerials + QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE + QUERY_OR +
                DBConstants.DatabaseColumns.TICKER_SERIAL + QUERY_IN + QUERY_BRACKET_OPEN + tickerSerials + QUERY_BRACKET_CLOSE +
                QUERY_BRACKET_CLOSE + QUERY_UNION + QUERY_SELECT + TickerDBHelper.getInsertTickerColumnString(getSupportedLanguages()) +
                QUERY_COMMA + DBConstants.DatabaseColumns.TICKER_SERIAL + FROM + DBConstants.TablesIMDB.TABLE_FUND_TICKERS +
                QUERY_WHERE + QUERY_STATUS_FILTER + QUERY_AND + DBConstants.DatabaseColumns.TICKER_SERIAL + QUERY_IN + QUERY_BRACKET_OPEN +
                tickerSerials + QUERY_BRACKET_CLOSE + QUERY_UNION + QUERY_SELECT + TickerDBHelper.getInsertTickerColumnString(getSupportedLanguages()) +
                QUERY_COMMA + DBConstants.DatabaseColumns.TICKER_SERIAL + FROM + DBConstants.TablesIMDB.TABLE_FIXED_INCOME_TICKERS + QUERY_WHERE +
                QUERY_STATUS_FILTER + QUERY_AND + DBConstants.DatabaseColumns.TICKER_SERIAL + QUERY_IN + QUERY_BRACKET_OPEN + tickerSerials + QUERY_BRACKET_CLOSE;

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return  (Map<String, Object>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS_BY_TICKER_SERIAL_FOR_NEWS, requestDBDTO);
    }

    /**
     * Method to get list of IndexSnapshotDTOs for given exchange
     *
     * @param sourceId exchachange code
     * @return Set<IndexSnapshotDTO>
     */
    @SuppressWarnings("unchecked")
    public Set<IndexSnapshotDTO> getIndicesSnapshot(String sourceId) {
        Set<IndexSnapshotDTO> indicesSnapshotSet = null;
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_SELECT_ALL_FROM).append(DBConstants.TablesIMDB.TABLE_INDEX_SNAPSHOT);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.SOURCE_ID).append(QUERY_EQUAL);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(sourceId);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);


        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        List<IndexSnapshotDTO> list = (List<IndexSnapshotDTO>) masterDataDAO.get(IConstants.ControlPathTypes.INDEX_SNAPSHOT, requestDBDTO);

        if (list != null) {
            indicesSnapshotSet = new HashSet<IndexSnapshotDTO>(list);
        }

        return indicesSnapshotSet;
    }

    /*return a map of FundManager's CompanyId to FundManagersDTO s*/
    @SuppressWarnings("unchecked")
    public Map<String, FundManagersDTO> getFundManagers() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FUND_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, FundManagersDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FUND_MANAGERS_GET, queryBuilder.toString());
    }

    /*return a map of FundIssuers's CompanyId to FundIssuerDTO s*/
    @SuppressWarnings("unchecked")
    public Map<String, FundIssuersDTO> getFundIssuers() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FUND_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, FundIssuersDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FUND_ISSUERS_GET, queryBuilder.toString());
    }

    @SuppressWarnings("unchecked")
    public List<FundTickerDTO> getFundIssuerDetails(String companyId) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FUND_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.ISSUED_COMPANIES);
        queryBuilder.append(QUERY_LIKE);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(companyId);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_QUOTE);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (List<FundTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FUND_ISSUER_DETAILS, queryBuilder.toString());
    }

    @SuppressWarnings("unchecked")
    public List<FundTickerDTO> getFundScreenerMetaData() {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_SELECT);
        queryBuilder.append(DBConstants.DatabaseColumns.MANAGED_COMPANIES);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(DBConstants.DatabaseColumns.ISSUED_COMPANIES);
        queryBuilder.append(FROM);
        queryBuilder.append(DBConstants.TablesIMDB.TABLE_FUND_TICKERS);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (List<FundTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FUND_SCREENER_META, queryBuilder.toString());
    }

    /**
     * get the TICKERS which are used as benchmarks for funds
     *
     * @return list of benchmarks
     */
    @SuppressWarnings("unchecked")
    public Collection<TickerDTO> getFundBenchmarks() {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = FundBenchmarkDBHelper.getAllBenchmarkTickerSerials();
        Set<String> tickerSerials = (Set<String>)masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKER_SERIAL_SET, requestDBDTO);
        int totalCount = tickerSerials.size();
        Map<String, TickerDTO> tickerDTOMap = getTickersFromTickerSerial(tickerSerials, IConstants.AssetType.EQUITY);
        if(tickerDTOMap.size() < totalCount){
            Set<String> foundTickers = tickerDTOMap.keySet();
            tickerSerials.removeAll(foundTickers);
            Map<String, TickerDTO> fundTickerDTOMap = getTickersFromTickerSerial(tickerSerials, IConstants.AssetType.MUTUAL_FUNDS);
            tickerDTOMap.putAll(fundTickerDTOMap);
            if(tickerDTOMap.size() < totalCount){
                foundTickers = fundTickerDTOMap.keySet();
                tickerSerials.removeAll(foundTickers);
                tickerDTOMap.putAll(getTickersFromTickerSerial(tickerSerials, IConstants.AssetType.FIXED_INCOME));
            }
        }
        return tickerDTOMap.values();
    }

    /**
     * Get managed company data for a given company id
     *
     * @param companyId String
     * @return FundManagedCompanyDetailsDTO List
     */
    @SuppressWarnings("unchecked")
    public List<FundTickerDTO> getFundManagedCompanyDetails(String companyId) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FUND_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_AND);
        //managed funds
        queryBuilder.append(DBConstants.DatabaseColumns.MANAGED_COMPANIES);
        queryBuilder.append(QUERY_LIKE);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(companyId);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_QUOTE);

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (List<FundTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FUND_MANAGER_DETAILS, queryBuilder.toString());
    }


    //todo : is benchmark always an index???
    public Object getFundBenchMark(List<String> tickerSerialList, String language) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = FundBenchmarkDBHelper.getBenchmarksForFunds(tickerSerialList, language);
        return masterDataDAO.get(IConstants.ControlPathTypes.FUND_BENCHMARK_MAP, requestDBDTO);
    }

    //region merged

    /**
     * Get (tickerSerial~FundTickerDTO) map for a given list of ticker serials
     *
     * @param tickerSerials ticker serials list
     * @return tickerSerial~FundTickerDTO map
     */
    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getFundTickerDTOListFromTickerSerial(List<String> tickerSerials) {
        return getTickersFromTickerSerial(tickerSerials, IConstants.AssetType.MUTUAL_FUNDS);
    }

    /**
     * Generate cache key
     *
     * @param tickerSerialList list of ticker serials
     * @return TickerDTO Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickersFromTickerSerial(Collection<String> tickerSerialList) {
        return getTickersFromTickerSerial(tickerSerialList, IConstants.AssetType.EQUITY);
    }

    /**
     * Fetch ticker data for given ticker serial list
     *
     * @param tickerSerialList list of ticker serials
     * @return TickerDTO Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickersFromTickerSerial(Collection<String> tickerSerialList, IConstants.AssetType assetType) {
        Map<String, TickerDTO> stringTickerDTOMap;
        if (!tickerSerialList.isEmpty()) {
            if (assetType == null) {
                stringTickerDTOMap = getTickersFromTickerSerial(tickerSerialList, getSupportedLanguages());
            } else {
                stringTickerDTOMap = getTickersFromTickerSerial(assetType, tickerSerialList, getSupportedLanguages());
            }
        } else {
            stringTickerDTOMap = new HashMap<String, TickerDTO>(0);
        }
        return stringTickerDTOMap;
    }

    public Map<String, TickerDTO> getTickersFromTickerSerialAndSourceId(Collection<String> tickerSerialList, String sourceId) {
        Map<String, TickerDTO> stringTickerDTOMap;
        if (!tickerSerialList.isEmpty()) {
                stringTickerDTOMap = getTickersFromTickerSerialAndSourceId(tickerSerialList, sourceId, getSupportedLanguages());
        } else {
            stringTickerDTOMap = new HashMap<String, TickerDTO>(0);
        }
        return stringTickerDTOMap;
    }

    /**
     * get ticker data from all three ticker tables
     *
     * @param tickerId String
     * @param sourceId String
     * @return TickerDTO
     */
    @SuppressWarnings("unchecked")
    public TickerDTO getTickersFromAllTickerTables(String tickerId, String sourceId) {
        Map<String, TickerDTO> tickerDTOMap = getTickerFromSymbolExchange(tickerId, sourceId, getSupportedLanguages());
        return (tickerDTOMap != null && !tickerDTOMap.isEmpty()) ? tickerDTOMap.values().iterator().next() : null;
    }

    /**
     * Fetch ticker data for given ticker serial list
     *
     * @param tickerSerialList list of ticker serials
     * @return Map, EquityTickerDTO
     */
    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getEquityTickersFromTickerSerial(List<String> tickerSerialList) {
        return getTickersFromTickerSerial(tickerSerialList, IConstants.AssetType.EQUITY);
    }

    /**
     * Get Ticker for a ticker id and source
     *
     * @param tickerId symbol name
     * @param sourceId exchange name
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    public EquityTickerDTO getTickerData(String tickerId, String sourceId) {
        EquityTickerDTO ticker = null;
        Map<String, TickerDTO> tickers = getTickerFromSymbolExchange(IConstants.AssetType.EQUITY, tickerId, sourceId, getSupportedLanguages());
        if (tickers != null && !tickers.isEmpty()) {
            ticker = (EquityTickerDTO) tickers.values().iterator().next();
        }
        return ticker;
    }

    @SuppressWarnings("unchecked")
    public Map<String, EquityTickerDTO> getTickerDataByTickerIdSourceIdComposite(Set<String> sourceTicker) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        requestDBDTO.setQuery(EquityTickerDBHelper.getTickersByTickerIdSourceIdComposite(sourceTicker, getSupportedLanguages()));
        return (Map<String, EquityTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_EQUITY_TICKERS_BY_SOURCE_TICKER, requestDBDTO);
    }

    /**
     * Get Fixed Income Ticker for a ticker id and source
     *
     * @param tickerId symbol name
     * @param sourceId exchange name
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    public FixedIncomeTickerDTO getFixedIncomeTicker(String tickerId, String sourceId) {
        FixedIncomeTickerDTO tickerDTO = null;
        Map<String, TickerDTO> tickers = getTickerFromSymbolExchange(IConstants.AssetType.FIXED_INCOME, tickerId, sourceId, getSupportedLanguages());
        if (tickers != null && !tickers.isEmpty()) {
            tickerDTO = (FixedIncomeTickerDTO) tickers.values().iterator().next();
        }
        return tickerDTO;
    }

    /**
     * Get Fixed Income Ticker for a ticker id and source
     *
     * @param tickerId symbol name
     * @param sourceId exchange name
     * @return Ticker List
     */
    @SuppressWarnings("unchecked")
    public FundTickerDTO getFundTicker(String tickerId, String sourceId) {
        FundTickerDTO tickerDTO = null;
        Map<String, TickerDTO> tickers = getTickerFromSymbolExchange(IConstants.AssetType.MUTUAL_FUNDS, tickerId, sourceId, getSupportedLanguages());
        if (tickers != null && !tickers.isEmpty()) {
            tickerDTO = (FundTickerDTO) tickers.values().iterator().next();
        }
        return tickerDTO;
    }

    //endregion

    //region NEW

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickersFromTickerSerial(Collection<String> tickerSerialList, List<String> supportedLanguages) {
        //todo chanaka imdb -> oracle
        MetaDataDAO masterDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(TickerDBHelper.getAllTickersByTickerSerials(tickerSerialList, supportedLanguages));
        requestDBDTO.setSupportedLang(supportedLanguages);
        return (Map<String, TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickersFromCompanyIdList(Collection<String> companyIdList, List<String> supportedLanguages) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(TickerDBHelper.getTickersByCompanyIdList(companyIdList, supportedLanguages));
        requestDBDTO.setSupportedLang(supportedLanguages);
        return (Map<String, TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_LISTED_UNLISTED_TICKERS_BY_COMPANY_ID, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickersFromTickerSerialAndSourceId(Collection<String> tickerSerialList, String sourceId, List<String> supportedLanguages) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        List<String> supportedLanguagesTemp = supportedLanguages;
        int metaDataType;
        if (supportedLanguagesTemp == null) {
            supportedLanguagesTemp = getSupportedLanguages();
        }
        requestDBDTO.setSupportedLang(supportedLanguagesTemp);
        requestDBDTO.setQuery(EquityTickerDBHelper.getTickersByTickerSerials(tickerSerialList, sourceId, supportedLanguagesTemp));
        metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickersFromTickerSerial(IConstants.AssetType assetType, Collection<String> tickerSerialList, List<String> supportedLanguages) {
        MetaDataDAO masterDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        List<String> supportedLanguagesTemp = supportedLanguages;
        if (supportedLanguagesTemp == null) {
            supportedLanguagesTemp = getSupportedLanguages();
        }
        requestDBDTO.setSupportedLang(supportedLanguagesTemp);
        int metaDataType;
        switch (assetType) {
            case FIXED_INCOME:
                requestDBDTO.setQuery(FixedIncomeTickerDBHelper.getTickersByTickerSerials(tickerSerialList, supportedLanguagesTemp));
                metaDataType = IConstants.ControlPathTypes.GET_FIXED_INCOME_TICKERS;
                break;
            case MUTUAL_FUNDS:
                requestDBDTO.setQuery(FundTickerDBHelper.getTickersByTickerSerials(tickerSerialList, supportedLanguagesTemp));
                metaDataType = IConstants.ControlPathTypes.GET_FUND_TICKERS;
                break;
            default:
                requestDBDTO.setQuery(EquityTickerDBHelper.getTickersByTickerSerials(tickerSerialList, supportedLanguagesTemp));
                metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
                break;
        }
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickerFromSymbolExchange(String tickerId, String sourceId, List<String> supportedLanguages) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(TickerDBHelper.getAllTickersBySymbolExchange(tickerId, sourceId, supportedLanguages));
        requestDBDTO.setSupportedLang(supportedLanguages);
        return (Map<String, TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getTickerFromSymbolExchange(IConstants.AssetType assetType, String tickerId, String sourceId, List<String> supportedLanguages) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setSupportedLang(supportedLanguages);
        int metaDataType;
        switch (assetType) {
            case FIXED_INCOME:
                requestDBDTO.setQuery(FixedIncomeTickerDBHelper.getTickersBySymbolExchange(tickerId, sourceId, supportedLanguages));
                metaDataType = IConstants.ControlPathTypes.GET_FIXED_INCOME_TICKERS;
                break;
            case MUTUAL_FUNDS:
                requestDBDTO.setQuery(FundTickerDBHelper.getTickersBySymbolExchange(tickerId, sourceId, supportedLanguages));
                metaDataType = IConstants.ControlPathTypes.GET_FUND_TICKERS;
                break;
            default:
                requestDBDTO.setQuery(EquityTickerDBHelper.getTickersBySymbolExchange(tickerId, sourceId, supportedLanguages));
                metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
                break;
        }
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    public Map<String, EquityTickerSnapshotDTO> getEquityTickerSnapshotDTOByCompanyId(Collection<String> companyIdList, String language){
        List<String> supportedLanguages;
        if (language == null || language.isEmpty()) {
            supportedLanguages = getSupportedLanguages();
        } else {
            supportedLanguages = new ArrayList<String>(1);
            supportedLanguages.add(language.toUpperCase());
        }
        return getEquityTickerSnapshotDTOByCompanyId(companyIdList, supportedLanguages);
    }

    public Map<String, EquityTickerSnapshotDTO> getEquityTickerSnapshotDTOByCompanyId(Collection<String> companyIdList, List<String> supportedLanguages){
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();

        Map<String, EquityTickerSnapshotDTO> equityTickerSnapshotDTOMap = null;
        String query = EquityTickerSnapshotDBHelper.getEquityTickerSnapshotQuery(companyIdList, instrumentTypes);
        if(query != null ){
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(query);
            requestDBDTO.setSupportedLang(supportedLanguages);

            equityTickerSnapshotDTOMap = (Map<String, EquityTickerSnapshotDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_EQUITY_SNAPSHOT_BY_COMPANY, requestDBDTO);
        }
        return equityTickerSnapshotDTOMap;
    }

    /**
     * Method to get companyDTOs for given list of IDs from tickers table only
     * no need to query all 3 tables
     * @param companyIdList company ids
     * @param supportedLanguages languages
     * @return company id vs company DTO
     */
    @SuppressWarnings("unchecked")
    public Map<String, CompanyDTO> getAllCompaniesByCompanyId(Collection<String> companyIdList, List<String> supportedLanguages) {
        return getCompaniesByCompanyId(companyIdList, supportedLanguages);
    }

    public Map<String, CompanyDTO> getCompaniesByCompanyId(Collection<String> companyIdList, String language) {
        List<String> supportedLanguages;
        if (language == null || language.isEmpty()) {
            supportedLanguages = getSupportedLanguages();
        } else {
            supportedLanguages = new ArrayList<String>(1);
            supportedLanguages.add(language.toUpperCase());
        }
        return getCompaniesByCompanyId(companyIdList, supportedLanguages);
    }

    public CompanyDTO getCompanyByCompanyId(String companyId, String language) {
        List<String> companies = new ArrayList<String>(1);
        companies.add(companyId);
        Map<String, CompanyDTO> companyDTOMap = getCompaniesByCompanyId(companies, language);
        return companyDTOMap != null && !companyDTOMap.isEmpty() ? companyDTOMap.values().iterator().next() : null;
    }

    /**
     * Method to get companyDTOs for given list of IDs from tickers table only
     *
     * @param companyIdList      company ids
     * @param supportedLanguages supported languages
     * @return map company id vs company dto
     */
    @SuppressWarnings("unchecked")
    public Map<String, CompanyDTO> getCompaniesByCompanyId(Collection<String> companyIdList, List<String> supportedLanguages) {
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();

        Map<String, CompanyDTO> companyDTOMap = null;
        String query = CompanyDBHelper.getCompaniesByCompanyIds(companyIdList, instrumentTypes, supportedLanguages);
        if(query != null ){
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(query);
            requestDBDTO.setSupportedLang(supportedLanguages);

            companyDTOMap = (Map<String, CompanyDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_COMPANIES, requestDBDTO);
        }
        return companyDTOMap;
    }

    //region company screener

    @SuppressWarnings("unchecked")
    public DataListDTO getCompanyScreenerData(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                              String city, String companyName, String sortOrder, String sortField,
                                              Integer pageIndex, Integer pageSize, String language, String secondaryLanguage,
                                              boolean loadCount, String searchCompanyType) {

        language = language.toUpperCase();
        List<String> supportedLang = new ArrayList<String>(2);
        supportedLang.add(language);
        if(secondaryLanguage != null) {
            supportedLang.add(secondaryLanguage);
        }

        List<Map<String, String>> companyDataList;
        DataListDTO companyDataListDTO = null;
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();

        int count = -1;
        StringBuilder queryPaginationBuilder = new StringBuilder();
        String sortFieldTemp = getDBField(sortField, language);
        if (sortFieldTemp != null && !sortFieldTemp.isEmpty()) {
            queryPaginationBuilder.append(QUERY_ORDER);
            queryPaginationBuilder.append(sortFieldTemp);
            queryPaginationBuilder.append(QUERY_SPACE).append(sortOrder);
        }
        if (pageIndex > -1 && pageSize > -1) {
            int start = pageIndex * pageSize;
            queryPaginationBuilder.append(QUERY_SPACE).append(DBUtils.getImdbPaginationQuery(start, pageSize + 1, dbType));
        }

        String searchQuery = CompanyDBHelper.getCompanyScreenQuery(countries, listingStatus, companyTypes, industries,
                city, companyName, searchCompanyType, language, secondaryLanguage, instrumentTypes);

        String query = searchQuery + queryPaginationBuilder.toString();

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        if (loadCount) {
            String countQuery = CompanyDBHelper.getCompanyScreenCountQuery(countries, listingStatus, companyTypes, industries, city,
                    companyName, searchCompanyType, language, instrumentTypes);
            count = (Integer) masterDataDAO.get(IConstants.ControlPathTypes.GET_RESULT_COUNT, countQuery);
        }

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(supportedLang);

        companyDataList = (List<Map<String, String>>) masterDataDAO.get(IConstants.ControlPathTypes.COMPANY_SCREENER, requestDBDTO);

        if (companyDataList != null && !companyDataList.isEmpty()) {
            if (!loadCount) {
                count = pageIndex * pageSize + companyDataList.size();
            }

            companyDataListDTO = new DataListDTO();
            companyDataListDTO.setDataList(companyDataList);
            companyDataListDTO.setMaxCount(count);
        }
        return companyDataListDTO;
    }

    /**
     * get company dto map of company screening results
     *
     * @param countries         countries
     * @param listingStatus     listingStatus
     * @param industries        industries
     * @param city              city
     * @param companyName       companyName
     * @param sortOrder         sortOrder
     * @param sortField         sortField
     * @param pageIndex         pageIndex
     * @param pageSize          pageSize
     * @param language          language
     * @param loadCount         loadCount
     * @param searchCompanyType isOrdinary
     * @return DataListDTO
     */
    @SuppressWarnings("unchecked")
    public DataListDTO getCompanyScreenerData(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                              String city, String companyName, String sortOrder, String sortField,
                                              Integer pageIndex, Integer pageSize, String language, boolean loadCount, String searchCompanyType) {
        return getCompanyScreenerData(countries, listingStatus, companyTypes, industries, city, companyName, sortOrder, sortField,
                pageIndex, pageSize, language, null, loadCount, searchCompanyType);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getCompanyCountsData(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                            String city, String companyName,  String language, String searchCompanyType) {

        MetaDataDAO masterDataDAO;
        Map<String,String> categorizedCompanyCounts;
        String countQuery;
        Map<String, String> individualCompanyTypeCounts;
        int count;

        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();

        countQuery = CompanyDBHelper.getCompanyTypeCountQuery(countries, listingStatus, null, industries, city, companyName,language.toUpperCase(), instrumentTypes);

        List<String> supportedLang = new ArrayList<String>(1);
        supportedLang.add(language.toUpperCase());

        RequestDBDTO requestDBDTO =  new RequestDBDTO();
        requestDBDTO.setSupportedLang(supportedLang);
        requestDBDTO.setQuery(countQuery);

        masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        LOG.debug("<!--Executing Company Type Count Query : " + countQuery);
        individualCompanyTypeCounts = (Map<String, String>) masterDataDAO.get(IConstants.ControlPathTypes.COMPANY_DATA_COUNT, requestDBDTO);
        categorizedCompanyCounts = new HashMap<String, String>();

        if (individualCompanyTypeCounts != null) {
            for (String companyType : companyTypes) {
                count = 0;
                String[] types = companyType.split(IConstants.Delimiter.HYPHEN);
                for (String aType : types) {
                    if (individualCompanyTypeCounts.containsKey(aType)) {
                        count += Integer.valueOf(individualCompanyTypeCounts.get(aType));
                    }
                }
                categorizedCompanyCounts.put(companyType, String.valueOf(count));
            }
        }

        return categorizedCompanyCounts;
    }

    //endregion

    //endregion

    //region Related instruments

    public Object getData(Map<String, String> requestData, boolean isDirectData, boolean isJsonResponse) {
        Object data = null;
        int controlPath = Integer.parseInt(requestData.get(IConstants.CustomDataField.CONTROL_PATH));

        switch (controlPath) {
            case IConstants.ControlPathTypes.RELATED_INSTRUMENT_SEARCH:
                data = this.getRelatedInstruments(requestData);
                break;
            case IConstants.ControlPathTypes.RELATED_INSTRUMENT_SUBSIDIARY_DATA:
                data = this.getRelatedSubsidiaryData(requestData);
                break;
            default:
                break;
        }

        return data;
    }

    /**
     * Get Stocks, fixed income and funds related to company
     *
     * @param requestData param map
     * @return List<TickerDTO>
     */
    @SuppressWarnings("unchecked")
    public Object getRelatedInstruments(Map<String, String> requestData) {
        int companyId;
        long tickerSerialId;
        Map<IConstants.AssetType, String> queryList;
        MetaDataDAO masterDataDAO;
        Object data = null;

        try {
            companyId = Integer.parseInt(requestData.get(IConstants.MIXDataField.COMPANY_ID));
            tickerSerialId = Long.parseLong(requestData.get(IConstants.MIXDataField.TSID));

            queryList = new HashMap<IConstants.AssetType, String>();
            if (tickerSerialId != 0) {
                queryList.put(IConstants.AssetType.EQUITY, generateRelatedEquityQuery(companyId, tickerSerialId));
            }
            queryList.put(IConstants.AssetType.MUTUAL_FUNDS, generateRelatedFundQuery(companyId));
            queryList.put(IConstants.AssetType.FIXED_INCOME, generateRelatedFixedIncomeQuery(String.valueOf(companyId)));

            masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            //Search related tickers
            data = masterDataDAO.get(IConstants.ControlPathTypes.RELATED_INSTRUMENT_SEARCH, queryList);


        } catch (NumberFormatException nfe) {
            LOG.info("<Related Instruments>" + nfe.getMessage() + nfe.getCause());
        }

        return data;
    }

    /**
     * Search fixed income data using issuer company id
     *
     * @param requestData request data
     * @return list of fixed income tickers
     */
    private Object getRelatedSubsidiaryData(Map<String, String> requestData) {
        return this.searchFixedIncomeByCompanyId(requestData.get(IConstants.MIXDataField.COMPANY_ID_LIST));
    }

    /**
     * Generate related equity query
     *
     * @param companyId    company id
     * @param tickerSerial ticker serial
     * @return String
     */
    private String generateRelatedEquityQuery(int companyId, long tickerSerial) {
        StringBuilder queryBuilder = new StringBuilder();
        //add related equities
        queryBuilder.append(QUERY_TICKER).append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID).append(QUERY_EQUAL).append(companyId);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.TICKER_SERIAL).append(QUERY_NOT_EQUAL).append(tickerSerial);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(INSTRUMENT_TYPE_ID).append(QUERY_IN).append(QUERY_BRACKET_OPEN).
                append(IConstants.InstrumentTypes.getEquityInstrumentTypes()).append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.TICKER_ID);

        return queryBuilder.toString();
    }

    /**
     * Generate related fund query managed and owned funds
     *
     * @param companyId company id
     * @return String
     */
    private String generateRelatedFundQuery(int companyId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FUND_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        // owned funds
        queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(companyId);
        queryBuilder.append(QUERY_OR);
        //managed funds
        queryBuilder.append(DBConstants.DatabaseColumns.MANAGED_COMPANIES);
        queryBuilder.append(QUERY_LIKE);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(companyId);
        queryBuilder.append(QUERY_COMMA);
        queryBuilder.append(QUERY_PREC);
        queryBuilder.append(QUERY_QUOTE);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_ORDER);
        queryBuilder.append(QUERY_UPPER).append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.TICKER_LONG_DES_LN);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        return queryBuilder.toString();
    }

    /**
     * Generate related fixed income query
     *
     * @param companyId company id
     * @return String
     */
    private String generateRelatedFixedIncomeQuery(String companyId) {
        Date date = DataAccessUtils.getUTCDateTimeAsDate();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_FIXED_INCOME_TICKER);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(QUERY_STATUS_FILTER);
        queryBuilder.append(QUERY_AND);
        // owned funds
        queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
        queryBuilder.append(QUERY_IN).append(QUERY_BRACKET_OPEN).append(companyId).append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(DBConstants.DatabaseColumns.MATURITY_DATE);
        queryBuilder.append(QUERY_GREATER_OR_EQUAL_THAN).append(QUERY_BRACKET_OPEN).append(QUERY_QUOTE);
        queryBuilder.append(new java.sql.Date(date.getTime())).append(QUERY_QUOTE).append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_ORDER).append(DBConstants.DatabaseColumns.MATURITY_DATE);
        return queryBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public Object searchFixedIncomeByCompanyId(String companyIdList) {

        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();

        String query = generateRelatedFixedIncomeQuery(companyIdList);
        requestDBDTO.setQuery(query);
        requestDBDTO.setSupportedLang(getSupportedLanguages());

        if (LOG.isDebugEnabled()) {
            LOG.debug(" Subsidiary  related Fixed income search query :" + query);
        }
        //Search fixed income
        return masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_SEARCH, requestDBDTO);
    }

    //endregion

    //region regression

    /**
     * load fi tickers by searching IMDB
     *
     * @param language     lang
     * @param country      country list
     * @param bondType     BOND_TYPE_GOVERNMENT
     * @param maturityDate maturity date
     * @param ytm          ytm > 0
     * @return fi tickers
     */
    public List<FixedIncomeTickerDTO> getFundRatioRiskFreeRateFixedIncomeTickers(String language, String country,
                                                                                  String bondType, String maturityDate, double ytm) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = FundRatiosDBHelper.getFundRatioRiskFreeRateFixedIncomeTickers(language, country, bondType, maturityDate, ytm);
        return (List<FixedIncomeTickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.FIXED_INCOME_TICKER_SEARCH, requestDBDTO);
    }

    /**
     * get benchmarks for fund from IMDB
     *
     * @param fundTickerSerial fund ticker serial
     * @param language         language
     * @return benchmark ticker serial vs benchmark rate
     */
    public Map<String, Double> getBenchmarks(String fundTickerSerial, String language) {
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = FundRatiosDBHelper.getBenchmarksForFund(fundTickerSerial, language);
        return ((Map<String, Map<String, Double>>) masterDataDAO.get(IConstants.ControlPathTypes.BENCHMARKS_FOR_FUNDS, requestDBDTO)).get(fundTickerSerial);
    }

    /**
     * get descriptions for benchmarks
     *
     * @param benchmarkSymbols benchmark symbols
     * @param language         lang
     * @return benchmark ticker serial vs TickerDTO
     */
    public Map<String, TickerDTO> getBenchmarkTickers(Set<String> benchmarkSymbols, String language) {
        RequestDBDTO requestDBDTO = FundRatiosDBHelper.getTickersByTickerSerial(benchmarkSymbols, language);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS, requestDBDTO);
    }

    /**
     * get main index of exchange or if exchange is virtual then get country, exchange -> main index
     *
     * @param sourceId    fund exchange
     * @param countryCode fund country
     * @param language    lang
     * @return main index ticker serials
     */
    public List<Long> getMainIndexesForExchangeOrCountry(String sourceId, String countryCode, String language) {
        RequestDBDTO requestDBDTO = FundRatiosDBHelper.getSourceDTO(sourceId, countryCode, language);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        List<SourceDTO> sourceDTOs = (List<SourceDTO>) masterDataDAO.get(IConstants.ControlPathTypes.SOURCE_FOR_FUND_RATIO, requestDBDTO);
        List<Long> mainIndexes = new ArrayList<Long>(1);
        List<Long> countryMainIndexes = new ArrayList<Long>(1);
        for (SourceDTO source : sourceDTOs) {
            if (source.getSourceId().equalsIgnoreCase(sourceId)) {
                mainIndexes.add(source.getMainIndexTickerSerial());
                break;
            } else {
                countryMainIndexes.add(source.getMainIndexTickerSerial());
            }
        }

        return mainIndexes.isEmpty() ? countryMainIndexes : mainIndexes;
    }

    //endregion

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getEquityTickersFromSourceId(String exchange, List<String> supportedLanguages){
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        List<String> supportedLanguagesTemp = supportedLanguages;
        int metaDataType;
        if (supportedLanguagesTemp == null) {
            supportedLanguagesTemp = getSupportedLanguages();
        }
        RequestDBDTO requestDBDTO = EquityTickerDBHelper.getTickersBySourceId(exchange, instrumentTypes, supportedLanguagesTemp);
        metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getEquityTickersFromSectorAndSourceId(String sectorCode, String exchange, List<String> supportedLanguages){
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        List<String> supportedLanguagesTemp = supportedLanguages;
        int metaDataType;
        if (supportedLanguagesTemp == null) {
            supportedLanguagesTemp = getSupportedLanguages();
        }
        RequestDBDTO requestDBDTO = EquityTickerDBHelper.getTickersBySectorAndSourceId(sectorCode, exchange, instrumentTypes, supportedLanguagesTemp);
        metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getEquityTickersFromGICSL2AndSourceId(String gicsL2Code, String exchange, List<String> supportedLanguages){
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        List<String> supportedLanguagesTemp = supportedLanguages;
        int metaDataType;
        if (supportedLanguagesTemp == null) {
            supportedLanguagesTemp = getSupportedLanguages();
        }
        RequestDBDTO requestDBDTO = EquityTickerDBHelper.getTickersByGicsL2AndSourceId(gicsL2Code, exchange, instrumentTypes, supportedLanguagesTemp);
        metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, TickerDTO> getEquityTickersFromGICSL3AndSourceId(String gicsL3Code, String exchange, List<String> supportedLanguages){
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        List<String> supportedLanguagesTemp = supportedLanguages;
        int metaDataType;
        if (supportedLanguagesTemp == null) {
            supportedLanguagesTemp = getSupportedLanguages();
        }
        RequestDBDTO requestDBDTO = EquityTickerDBHelper.getTickersByGicsL3AndSourceId(gicsL3Code, exchange, instrumentTypes, supportedLanguagesTemp);
        metaDataType = IConstants.ControlPathTypes.GET_EQUITY_TICKERS;
        return (Map<String, TickerDTO>) masterDataDAO.get(metaDataType, requestDBDTO);
    }

    public Map<Integer, Map<String, String>> getAllCompaniesListByCompanyId(Collection<String> companyIdList, List<String> supportedLanguages) {
        Map<String, CompanyDTO> companyDTOList = getAllCompaniesByCompanyId(companyIdList, supportedLanguages);
        Map<Integer, Map<String, String>> descriptionMap = new HashMap<Integer, Map<String, String>>();

        for (Map.Entry<String, CompanyDTO> entry : companyDTOList.entrySet()) {
            String key = entry.getKey();
            CompanyDTO value = entry.getValue();
            descriptionMap.put(Integer.parseInt(key), value.getCompanyName());
        }
        return descriptionMap;

    }

    public Set<String> getFundTickerCountryList() {
        MetaDataDAO metaDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = CountryDBHelper.getFundTickerCountriesQuery();
        Set<String> countryList = (Set<String>) metaDataDAO.get(META_DATA_GET_ALL_FUND_TICKER_COUNTRIES, requestDBDTO);
        return countryList;
    }

    public void updateFundTickerCountries(Set<String> countryList) {
        MetaDataDAO metaDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
        metaDataDAO.insert(META_DATA_UPDATE_FUND_TICKER_COUNTRIES, countryList);
    }

    public double getVWAPFromDB(Long tickerSerial) {
        double vwap = 0;

        MetaDataDAO metaDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = EquityTickerDBHelper.getVWAPForTickersQuery(tickerSerial);

        return (Double) metaDataDAO.get(META_DATA_GET_VWAP_FOR_TICKERS, requestDBDTO);
    }

    /**
     * Only can use for Operational KPI page
     * Returns 3 companies which are in the same country, industry and have the closest sla difference
     *
     * @param countryCode
     * @param gicsL3Code
     * @param companyId
     * @param slaLevel
     * @return
     */
    public Map<String, CompanyDTO> getFilteredCompaniesKpi(String countryCode, String gicsL3Code, int companyId, int slaLevel) {

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        Map<String, Object> customParams = new HashMap<String, Object>(4);
        requestDBDTO.setQuery(QUERY_KPI_FILTERED_COMPANIES);

        customParams.put(IConstants.KPIFilteringFields.COUNTRY_CODE.toString(), countryCode);
        customParams.put(IConstants.KPIFilteringFields.GICS_L3_CODE.toString(), gicsL3Code);
        customParams.put(IConstants.KPIFilteringFields.COMPANY_ID.toString(), companyId);
        customParams.put(IConstants.KPIFilteringFields.SLA_LEVEL.toString(), slaLevel);

        requestDBDTO.setCustomParams(customParams);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();

        return (Map<String, CompanyDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_FILTERED_COMPANIES_FOR_KPI, requestDBDTO);
    }

    /**
     * getLatestUpdatedCompanyProfiles
     *
     * @param selectedCountries  selected countries
     * @param supportedLanguages requested language
     * @return tickerDTOList
     */
    public List<TickerDTO>  getLatestUpdatedCompanyProfiles(String selectedCountries, List<String> supportedLanguages) {
        List<TickerDTO> tickerDTOList = null;
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        String query = CompanyDBHelper.getLatestUpdatedCompanies(selectedCountries, instrumentTypes);

        if (query != null) {
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(query);
            requestDBDTO.setSupportedLang(supportedLanguages);
            tickerDTOList = (List<TickerDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_TICKERS_LIST_BY_COMPANY_ID, requestDBDTO);
        }
        return tickerDTOList;
    }

    /**
     * getCoverageData
     *
     * @param type              coverage type
     * @param selectedCountries selected countries
     * @return CompanyCoverageDTO map
     */
    public Map<String, CompanyCoverageDTO> getCoverageData(IConstants.Coverage type, String selectedCountries) {
        String instrumentTypes = masterDataAccess.getEquityCommonStockTickerL3Classes();
        String query = CompanyDBHelper.getCompanyCoverage(selectedCountries, instrumentTypes, type);
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        return (Map<String, CompanyCoverageDTO>) masterDataDAO.get(IConstants.ControlPathTypes.GET_REGION_COVERAGE, query);
    }

    /**
     * getCompanyIndustryAggregatesData
     * @param type company aggregates type
     * @return country industry aggregates map
     */
    public Map<String, Map<String, Map<String, String>>> getCompanyIndustryAggregatesData(IConstants.CompanyAggregatesTypes type, String countries) {
        String instrumentTypes = masterDataAccess.getEquityTickerL3Classes();
        String query = CompanyDBHelper.getCompanyAggregates(instrumentTypes, type.getDefaultValue(), countries);
        MetaDataDAO metaDataDAO;
        if (IConstants.CompanyAggregatesTypes.MCAP.getDefaultValue().equalsIgnoreCase(type.getDefaultValue())) {
            metaDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            return (Map<String, Map<String, Map<String, String>>>) metaDataDAO.get(IConstants.ControlPathTypes.GET_COMPANY_AGGREGATES, query);
        } else {
            metaDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
            return (Map<String, Map<String, Map<String, String>>>) metaDataDAO.get(META_DATA_COMPANY_AGGREGATES, query);
        }
    }

    /**
     * getCompanySize list
     *
     * @param page               pageno
     * @param order              sort order
     * @param column             sort column
     * @param countries          country list
     * @param supportedLanguages supported languages
     * @return equitySnapShotMap
     */
    public List<Map<String, Object>> getCompanySize(String page, String order, String column, String countries, List<String> supportedLanguages) {
        List<Map<String, Object>> equityTickerSnapshotDTOMap = null;
        String equityTickers = TICKER_CLASS_L3_EQUITY;
        String query = EquityTickerSnapshotDBHelper.getCompanySizeQuery(equityTickers, page, order, column, countries, supportedLanguages.get(0));
        if (query != null) {
            MetaDataDAO metaDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(query);
            requestDBDTO.setSupportedLang(supportedLanguages);
            equityTickerSnapshotDTOMap = (List<Map<String, Object>>) metaDataDAO.get(IConstants.ControlPathTypes.GET_COMPANY_SIZE_MCAP, requestDBDTO);
        }
        return equityTickerSnapshotDTOMap;
    }
}
