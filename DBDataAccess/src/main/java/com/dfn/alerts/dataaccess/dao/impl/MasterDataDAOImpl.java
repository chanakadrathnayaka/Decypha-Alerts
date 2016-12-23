package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.NewsHeadLines;
import com.dfn.alerts.beans.SectorDTO;
import com.dfn.alerts.beans.SectorSnapshotDTO;
import com.dfn.alerts.beans.ProductClassificationDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.*;
import com.dfn.alerts.dataaccess.dao.MasterDataDAO;
import com.dfn.alerts.dataaccess.orm.impl.earnings.ApplicationSetting;
import com.dfn.alerts.dataaccess.utils.CriteriaGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.jdbc.UncategorizedSQLException;

import javax.sql.DataSource;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.MasterDataType.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 2/5/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MasterDataDAOImpl implements MasterDataDAO {

    private static final Logger LOG = LogManager.getLogger(MasterDataDAOImpl.class);

    private static final String SECTOR_SEARCH = "from Sector order by sectorType";
    private static final String COUNTRY_SEARCH = "from Country";
    private static final String CITY_SEARCH = "from City";
    private static final String NEWS_SOURCE_SEARCH = "from NewsSource";
    private static final String CAL_EVENTS_TITLE_SEARCH = "from CalEventsTitle";
    private static final String FUND_SEARCH = "from FundClass";
    private static final String SUKUK_TYPES_SEARCH = "from SukukTypes";
    private static final String BOND_TYPES_SEARCH = "from BondTypes";
    private static final String INSTRUMENT_TYPES_SEARCH = "from InstrumentTypeDTO";
    private static final String COUNTRY_INDICATORS_SEARCH = "from CountryIndicatorDTO";
    public static final String TIME_ZONE_SEARCH = "from TimeZone order by timeZoneId";
    private static final String SMART_SEARCH_PAGE_SEARCH = "from SmartSearchPage";
    private static final String IPO_STATUS_SEARCH = "from IpoStatus";
    private static final String IPO_SUBSCRIPTION_TYPE_SEARCH = "from IpoSubscriptionType";
    private static final String SCREENER_CRITERION_SEARCH = "from ScreenerCriterion";
    private static final String SCREENER_CATEGORY_SEARCH = "from ScreenerCategory order by description";
    private static final String MA_STATUS_SEARCH = "from MAStatus";
    private static final String MA_DEAL_TYPE_SEARCH = "from MADealTypes";
    private static final String PAYMENT_METHODS_SEARCH = "from PaymentMethods";
    private static final String COMPANY_TYPES_SEARCH = "from CompanyTypes";
    private static final String REPORT_TYPES_SEARCH = "from ReportType";
    private static final String FILE_PUBLISHERS_SEARCH = "from FilePublisher";
    private static final String SUPPORTED_CURRENCIES = "select currency_id from currency_master where is_supported = 1 order by currency_id asc";
    private static final String EXCHANGE_METADATA = "from ExchangeMetaData";
    private static final String STOCK_METADATA = "from StockMetaData";
    private static final String ACCOUNT_TYPE = "from AccountType";
    private static final String SALES_REP = "from SalesRepresentative";
    private static final String EMBEDDED_OPTIONS = "from EmbeddedOptionType";
    private static final String PERIOD_DESCRIPTIONS = "from PeriodDescriptions";
    private static final String RATIO_GROUP_DESCRIPTIONS = "from RatioGroupDescriptions";
    private static final String CORPORATE_ACTION_ATTRIBUTES = "from CorporateActionAttributes";
    private static final String EDITORIAL_DESCRIPTIONS = "from EditorialDescriptions";
    private static final String PRODUCT_CLASSIFICATION = "from ProductClassification order by productClassificationLevel,description";

    private CriteriaGenerator criteriaGenerator;

    private SessionFactory sessionFactory = null;

    public List<Object> getAll(int metadataType) {
        List<Object> returnObj = null;

        switch (metadataType) {
            case MASTER_DATA_APP_SETTINGS: {
                returnObj = getAllSettings();
                break;
            }
            case MASTER_DATA_REPORT_CATEGORIES: {
                returnObj = getReportCategories();
                break;
            }
            case MASTER_DATA_REPORT_PROVIDERS: {
                returnObj = getReportProvides();
                break;
            }
            case MASTER_DATA_REGION_COUNTRY: {
                returnObj = getAllRegionCountries();
                break;
            }
            case MASTER_DATA_CORPORATE_ACTION_TYPES: {
                returnObj = getCorporateActionTypes();
                break;
            }
            case MASTER_DATA_SECTORS: {
                returnObj = getSectorList();
                break;
            }
            case MASTER_DATA_COUNTRY: {
                returnObj = getCountryList();
                break;
            }
            case MASTER_DATA_CAL_EVENTS_TITLE: {
                returnObj = getCalEventsTitle();
                break;
            }
            case MASTER_DATA_FUND_CLASS: {
                returnObj = getAllFundClasses();
                break;
            }
            case MASTER_DATA_SUKUK_TYPES: {
                returnObj = getAllSukukTypes();
                break;
            }
            case MASTER_DATA_BOND_TYPES: {
                returnObj = getAllBondTypes();
                break;
            }
            case MASTER_DATA_INSTRUMENT_TYPES: {
                returnObj = getAllInstrumentTpes();
                break;
            }
            case MASTER_DATA_COUNTRY_INDICATORS: {
                returnObj = getCountryIndicators();
                break;
            }
            case MASTER_DATA_GLOBAL_EDITION_SETTINGS: {
                returnObj = getGlobalEditionSettings();
                break;
            }
            case MASTER_DATA_TIME_ZONES: {
                returnObj = getTimeZones();
                break;
            }
            case MASTER_DATA_IPO_STATUS: {
                returnObj = getAllIpoStatusData();
                break;
            }
            case MASTER_DATA_IPO_SUBSCRIPTION_TYPE: {
                returnObj = getAllIpoSubscriptionType();
                break;
            }
            case MASTER_DATA_SCREENER_CATEGORIES: {
                returnObj = getScreenerCategoryList();
                break;
            }
            case MASTER_DATA_MA_STATUS: {
                returnObj = getMAStatus();
                break;
            }
            case MASTER_DATA_MA_DEAL_TYPES: {
                returnObj = getMADealType();
                break;
            }
            case MASTER_DATA_PAYMENT_METHODS: {
                returnObj = getPaymentMethods();
                break;
            }
            case MASTER_DATA_REPORT_TYPES: {
                returnObj = getReportTypes();
                break;
            }
            case MASTER_DATA_FILE_PUBLISHERS: {
                returnObj = getFilePublishers();
                break;
            }
            case MASTER_DATA_EXCHANGE_METADATA: {
                returnObj = getExchangeMetaData();
                break;
            }
            case MASTER_DATA_STOCK_METADATA: {
                returnObj = getStockMetaData();
                break;
            }
            case MASTER_DATA_ACCOUNT_TYPE: {
                returnObj = getAccountType();
                break;
            }
            case MASTER_DATA_SALES_REP: {
                returnObj = getSalesRep();
                break;
            }
            case MASTER_DATA_CITY: {
                returnObj = getCityList();
                break;
            }
            case MASTER_DATA_NEWS_SOURCES: {
                returnObj = getNewsSourceList();
                break;
            }
            case MASTER_DATA_COMPANY_TYPES: {
                returnObj = getCompanyTypes();
                break;
            }
            case MASTER_DATA_EMBEDDED_OPTION_TYPES:{
                returnObj = getDataList(EMBEDDED_OPTIONS);
                break;
            }
            case MASTER_DATA_PERIOD_DESCRIPTIONS:
                returnObj = getPeriodDescriptions();
                break;
            case MASTER_DATA_RATIO_GROUP_DESCRIPTIONS:
                returnObj = getRatioGroupDescriptions();
                break;
            case MASTER_DATA_CORPORATE_ACTION_ATTRIBUTES:
                returnObj = getCorporateActionAttributes();
                break;
            case MASTER_DATA_EDITORIAL_DESCRIPTIONS:
                returnObj = getEditorialDescriptions();
                break;
            case MASTER_DATA_PRODUCT_CLASSIFICATION:
                returnObj = getProductClassification();
                break;
            default:
                break;
        }
        return returnObj;
    }

    /**
     * Method to execute hibernate (select from 'table')
     *
     * @param query query
     * @return Object list
     */
    private List<Object> getDataList(String query){
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(query);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public Object get(int metadataType, Object obj) {
        Object returnObj = null;

        switch (metadataType) {
            case MASTER_DATA_APP_SETTINGS:
                returnObj = getSetting((String) obj);
                break;
            case MASTER_DATA_COMBO_BOX:
                returnObj = getComboBox(obj.toString());
                break;
            case MASTER_DATA_SMART_PAGES:
                returnObj = getSmartSearchPages();
                break;
            case MASTER_DATA_SCREENER_TYPE:
                returnObj = getScreenerCriterion();
                break;
            case MASTER_DATA_SECTOR_SNAPSHOT:
                returnObj = getSectorSnapShotList((String) obj);
                break;
            case MASTER_DATA_SUPPORTED_CURRENCIES:
                returnObj = getSupportedCurrencies();
                break;
            case IConstants.ControlPathTypes.NEWS_SEARCH_HIBERNATE:
                returnObj = searchNews((Map<String, String>) obj);
                break;
            case MASTER_DATA_NEWS_COUNT:
                returnObj = getNewsCount((Map<String, String>) obj);
                break;
            case MASTER_DATA_ANNOUNCEMENT_COUNT:
                returnObj = getAnnouncementCount((Map<String, String>) obj);
                break;
            case MASTER_DATA_ANNOUNCEMENTS:
                returnObj = getAnnouncements((Map<String, String>) obj);
                break;
            default:
                break;
        }

        return returnObj;
    }

    public int update(int controlPath, Object sqlQuery, Object data) {
        return 0;
    }

    private Object searchNews(Map<String, String> requestData) {
        Session session = null;
        List<Object> newsObjList;
        List<Integer> newsIdList;
        List<Map<String, String>> newsList;
        NewsHeadLines newsHeadLines = null;
        Map<String, String> aNews;

        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(News.class);
            this.criteriaGenerator.generateCriteria(criteria, requestData);
            newsObjList = criteria.list();
            if (newsObjList != null) {
                newsList = new ArrayList<Map<String, String>>(newsObjList.size());
                newsIdList = new ArrayList<Integer>(newsObjList.size());

                for (Object obj : newsObjList) {
                    aNews = ((News) obj).accessNewsHeadLine();
                    newsList.add(aNews);
                    newsIdList.add(Integer.valueOf(aNews.get(DBConstants.NewsDatabaseColumns.NEWS_ID)));
                }
                newsHeadLines = new NewsHeadLines();
                newsHeadLines.setNews(newsList);
                newsHeadLines.setNewsIdList(newsIdList);
            }
        } catch (HibernateException hie) {
            if(LOG.isDebugEnabled()){
                LOG.debug("Error occurred while retrieving data from RDBMS.." + hie);
            }
        } catch (Exception ignored){
            //

        }finally {
            if (session != null) {
                session.close();
            }
        }
        return newsHeadLines;
    }

    private long getAnnouncementCount(Map<String, String> requestData){
        Session session = null;
        long count = -1;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(Announcements.class);
            this.criteriaGenerator.setAnnouncementCriteria(criteria, requestData);
            count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        } catch (Exception e) {
            LOG.error("error in getAnnouncementCount()", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }

    private long getNewsCount(Map<String, String> requestData){
        Session session = null;
        long count = -1;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(News.class);
            this.criteriaGenerator.setSearchCriteria(criteria, requestData);
            count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        } catch (Exception e) {
            LOG.error("error in getNewsCount()", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return count;
    }


    /**
     * get announcements in RDBMS
     * @param requestData search criterias
     * @return Announcements
     */
    private List<Map<String, String>> getAnnouncements(Map<String, String> requestData){
        Session session = null;
        List announcementsObjList;
        List<Map<String, String>> announcementsList = null;

        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(Announcements.class);
            this.criteriaGenerator.setAnnouncementCriteria(criteria, requestData);
            announcementsObjList = criteria.list();
            if (announcementsObjList != null) {
                announcementsList = new ArrayList<Map<String, String>>(announcementsObjList.size());

            }
        } catch (Exception e) {
            LOG.error("error in getAnnouncements()", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return announcementsList;
    }

    private List<Object> getAllSettings() {

        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from ApplicationSetting");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private Object getSetting(String key) {
        Session session = null;
        ApplicationSetting applicationSetting = null;
        try {
            session = this.sessionFactory.openSession();
            applicationSetting = (ApplicationSetting) session.get(ApplicationSetting.class, key);
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return applicationSetting;
    }

    private Object getComboBox(String key) {
        Session session = null;
        List<Option> optionList = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(Option.class);
            criteria.add(Restrictions.eq("comboType", key));
            optionList = criteria.list();
        } catch (UncategorizedSQLException ignored) {
            //
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return optionList;
    }

    private List<Object> getReportCategories() {

        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from ReportCategories");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getReportProvides() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from ReportProvider");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * @return
     */
    private List<Object> getCorporateActionTypes() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from CorporateActionType");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return list;
    }

    /**
     * Load Sector Master List from SECTOR Table
     *
     * @return List<object>
     */
    private List<Object> getSectorList() {
        List<Object> list = new ArrayList<Object>();
        List<Object> sectorDTOList = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(SECTOR_SEARCH);
            list = q.list();

            for (Object obj : list) {
                Sector sector = (Sector) obj;
                sectorDTOList.add(new SectorDTO(sector.getSectorCode(), sector.getSectorType(), sector.getShortDescription(), null, sector.getAssociatedTickerSerial()));
            }
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return sectorDTOList;
    }

    /**
     * Load sector master list with there snapshots for a given source id
     *
     * @param sourceId
     * @return
     */
    private List<Object> getSectorSnapShotList(String sourceId) {
        List<Object> list = new ArrayList<Object>();
        List<Object> sectorDTOList = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(Sector.class);
            criteria.add(Restrictions.eq("sectorType", sourceId));
            list = criteria.list();

            if (list != null) {
                for (Object obj : list) {
                    Sector sector = (Sector) obj;
                    Set<SectorSnapshot> sectorSnapshots = sector.getSectorSnapshots();
                    Map<String, SectorSnapshotDTO> sectorSnapshotDTOMap = new HashMap<String, SectorSnapshotDTO>(sectorSnapshots.size());

                    for (SectorSnapshot sectorSnapshot : sectorSnapshots) {
                        sectorSnapshotDTOMap.put(IConstants.SectorSnapshotTypes.valueOf(sectorSnapshot.getSectorSnapshotId().getPeriod()).toString(), new SectorSnapshotDTO(sectorSnapshot.getTurnover(), sectorSnapshot.getVolume(), sectorSnapshot.getClose(), sectorSnapshot.getSectorSnapshotId().getPeriod()));
                    }

                    sectorDTOList.add(new SectorDTO(sector.getSectorCode(), sector.getSectorType(), sector.getShortDescription(), sectorSnapshotDTOMap, sector.getAssociatedTickerSerial()));
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Sector list is null");
                }
            }
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return sectorDTOList;
    }

    /**
     * get all exchange meta data
     *
     * @return list of  exchange meta data
     */
    private List<Object> getExchangeMetaData() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(EXCHANGE_METADATA);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * get all stock meta data
     *
     * @return list of  stock meta data
     */
    private List<Object> getStockMetaData() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(STOCK_METADATA);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * get screener category list
     *
     * @return category list
     */
    private List<Object> getScreenerCategoryList() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(SCREENER_CATEGORY_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get all Countries and Regions from database
     *
     * @return list of CurrencyRate objects
     * @see com.dfn.alerts.dataaccess.orm.impl.RegionCountry
     */
    private List<Object> getAllRegionCountries() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from RegionCountry");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getGlobalEditionSettings() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from GlobalEditionSettings");
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All countries (with description) from database
     *
     * @return Country objects
     */
    private List<Object> getCountryList() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(COUNTRY_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All Cities from database
     * @return
     */
    private List<Object> getCityList() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(CITY_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get all news providers from DB
     * @return
     */
    private List<Object> getNewsSourceList() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(NEWS_SOURCE_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getCalEventsTitle() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(CAL_EVENTS_TITLE_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All Fund Classes from Database
     *
     * @return
     */
    private List<Object> getAllFundClasses() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(FUND_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All Sukuk Types from Database
     *
     * @return
     */
    private List<Object> getAllSukukTypes() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(SUKUK_TYPES_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All Sukuk Types from Database
     *
     * @return
     */
    private List<Object> getAllBondTypes() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(BOND_TYPES_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getAllInstrumentTpes() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(INSTRUMENT_TYPES_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getCountryIndicators() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(COUNTRY_INDICATORS_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getTimeZones() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(TIME_ZONE_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Get SmartSearchPage data
     *
     * @return List<Object>
     */
    private List<Object> getSmartSearchPages() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(SMART_SEARCH_PAGE_SEARCH);
            list = q.list();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All IPO status from Database
     *
     * @return
     */
    private List<Object> getAllIpoStatusData() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(IPO_STATUS_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to get All Ipo subscription types from Database
     *
     * @return
     */
    private List<Object> getAllIpoSubscriptionType() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(IPO_SUBSCRIPTION_TYPE_SEARCH);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Get screener criterion data
     *
     * @return List<Object>
     */
    private List<Object> getScreenerCriterion() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(SCREENER_CRITERION_SEARCH);
            list = q.list();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Get merge and acquisition status
     */

    public List<Object> getMAStatus() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(MA_STATUS_SEARCH);
            list = q.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return list;
    }

    /**
     * Get merge and acquisition deal types
     */

    public List<Object> getMADealType() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(MA_DEAL_TYPE_SEARCH);
            list = q.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return list;
    }

    /**
     * Get payment method descriptions
     */
    public List<Object> getPaymentMethods() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(PAYMENT_METHODS_SEARCH);
            list = q.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Get finacial period descriptions
     */
    public List<Object> getPeriodDescriptions() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(PERIOD_DESCRIPTIONS);
            list = q.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public List<Object> getRatioGroupDescriptions() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(RATIO_GROUP_DESCRIPTIONS);
            list = q.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * get all Editorial Descriptions
     *
     * @return List<Object>
     */
    public List<Object> getEditorialDescriptions() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(EDITORIAL_DESCRIPTIONS);
            list = q.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.getCause());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * get company type descriptions
     * @return list of objects
     */
    public List<Object> getCompanyTypes(){
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(COMPANY_TYPES_SEARCH);
            list = q.list();
        }catch (Exception e){
            LOG.error(e.getMessage(), e.getCause());
        }finally {
            if(session != null){
                session.close();
            }
        }
        return list;
    }

    /**
     *get report types for files
     */
    public List<Object> getReportTypes(){
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(REPORT_TYPES_SEARCH);
            list = q.list();
        }catch (Exception e){
            LOG.error(e.getMessage(), e.getCause());
        }finally {
            if(session != null){
                session.close();
            }
        }
        return list;
    }

    /**
     * get file publishers for files
     */
    public List<Object> getFilePublishers(){
        List<Object> list = new ArrayList<Object>();
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(FILE_PUBLISHERS_SEARCH);
            list = q.list();
        }catch (Exception e){
            LOG.error(e.getMessage(), e.getCause());
        }finally {
            if(session != null){
                session.close();
            }
        }
        return list;
    }

    /**
     * Method to load supported currencies from currency master table
     * @return list of supported currencies
     */
    private List<String> getSupportedCurrencies(){
        List<String> supportedCurrencies = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query query = session.createSQLQuery(SUPPORTED_CURRENCIES);
            supportedCurrencies = query.list();

        }catch (Exception e){
           LOG.error(e.getMessage() , e);
        }finally {
            if(session != null){
                session.close();
            }
        }
        return supportedCurrencies;
    }

    /**
     * get account types
     *
     * @return List<Object>
     */
    private List<Object> getAccountType() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(ACCOUNT_TYPE);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * get sales representatives
     *
     * @return List<Object>
     */
    private List<Object> getSalesRep() {
        List<Object> list = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(SALES_REP);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            LOG.error(ignored.getMessage(), ignored);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    private List<Object> getCorporateActionAttributes(){
        List<Object> attributesList = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(CorporateActionAttributes.class);
            List attributesObj = criteria.list();
            if (attributesObj != null) {
                attributesList = attributesObj;
            }
        } catch (Exception ex) {
            LOG.error(" :: Corporate Action Attributes :: " + " Loading getAllCorporateActionAttributes : ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return attributesList;
    }

    /**
     * Load product classification List from PRODUCT_CLASSIFICATION Table     *
     * @return product classification list
     */
    private List<Object> getProductClassification() {
        List<Object> list;
        List<Object> productClassificationDTOList = new ArrayList<Object>();
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(PRODUCT_CLASSIFICATION);
            list = q.list();

            for (Object obj : list) {
                ProductClassification productClassification = (ProductClassification) obj;
                productClassificationDTOList.add(new ProductClassificationDTO(productClassification.getProductClassificationCode(),
                        productClassification.getProductClassificationLevel(), productClassification.getParentClassificationCode(),
                        productClassification.getDescription(), productClassification.getLangSpecificDescription()));
            }
        } catch (UncategorizedSQLException ex) {
            LOG.error("getProductClassification()" + ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return productClassificationDTOList;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCriteriaGenerator(CriteriaGenerator criteriaGenerator) {
        this.criteriaGenerator = criteriaGenerator;
    }
}
