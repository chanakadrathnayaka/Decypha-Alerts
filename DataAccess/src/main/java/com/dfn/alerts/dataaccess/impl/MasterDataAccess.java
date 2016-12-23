package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.beans.*;
import com.dfn.alerts.beans.user.ProductClassificationDTO;
import com.dfn.alerts.constants.*;
import com.dfn.alerts.dataaccess.api.ICacheManager;
import com.dfn.alerts.dataaccess.api.IRequestGenerator;
import com.dfn.alerts.dataaccess.api.ISocket;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.orm.impl.*;
import com.dfn.alerts.dataaccess.orm.impl.TimeZone;
import com.dfn.alerts.dataaccess.orm.impl.earnings.ApplicationSetting;
import com.dfn.alerts.dataaccess.orm.impl.tickers.TickerClassLevels;
import com.dfn.alerts.dataaccess.dao.IAppConfigData;
import com.dfn.alerts.dataaccess.dao.MasterDataDAO;
import com.dfn.alerts.dataaccess.dao.MetaDataDAO;
import com.dfn.alerts.dataaccess.dao.UserDetailsDAO;
import com.dfn.alerts.dataaccess.dao.impl.DAOFactory;
import com.dfn.alerts.dataaccess.utils.*;
import com.dfn.alerts.exception.CacheInitFailedException;
import com.dfn.alerts.exception.SocketAccessException;
import com.dfn.alerts.utils.CacheKeyGenerator;
import com.dfn.alerts.utils.CommonUtils;
import com.dfn.alerts.utils.FormatUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;

import static com.dfn.alerts.constants.ApplicationSettingsKeys.SUPPORTED_EXCHANGES;
import static com.dfn.alerts.constants.CacheKeyConstant.*;
import static com.dfn.alerts.constants.DBConstants.MasterDataType.*;
import static com.dfn.alerts.constants.DBConstants.MetaDataType.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 2/5/13
 * Time: 4:45 PM
 */
public class MasterDataAccess {

    /**
     * Log4j instance
     */
    private static final Logger LOG = LogManager.getLogger(MasterDataAccess.class);

    public static final int TIME_TO_LIVE_TWENTY_FOUR_HOURS = 24 * 60 * 60;
    public static final int TIME_TO_LIVE_THIRTY_MINUTES = 15 * 60;

    //region setters

    /**
     * dao factory to communicate with database
     */
    protected DAOFactory hibernateDaoFactory;

    protected DAOFactory imdbDaoFactory;

    protected DAOFactory jdbcDaoFactory;

    /**
     * retrieve category master data from DB and add to the cache as a Page wise Map
     */
    private IRequestGenerator requestGenerator;

    private ISocket socketManager;

    protected Boolean isCacheEnabled = true;

    /**
     * cache manager to communicate with cache layer
     */
    private ICacheManager cacheManager;

    private UserDetailsDataAccess userDetailsDataAccess;

    private int productId;

    private Gson gson;

    public void setUserDetailsDataAccess(UserDetailsDataAccess userDetailsDataAccess) {
        this.userDetailsDataAccess = userDetailsDataAccess;
    }

    public void setHibernateDaoFactory(DAOFactory hibernateDaoFactory) {
        this.hibernateDaoFactory = hibernateDaoFactory;
    }

    public void setImdbDaoFactory(DAOFactory imdbDaoFactory) {
        this.imdbDaoFactory = imdbDaoFactory;
    }

    public void setJdbcDaoFactory(DAOFactory jdbcDaoFactory) {
        this.jdbcDaoFactory = jdbcDaoFactory;
    }

    public void setRequestGenerator(IRequestGenerator requestGenerator) {
        this.requestGenerator = requestGenerator;
    }

    public void setSocketManager(ISocket socketManager) {
        this.socketManager = socketManager;
    }

    public void setCacheEnabled(Boolean cacheEnabled) {
        isCacheEnabled = cacheEnabled;
    }

    public void setCacheManager(ICacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    //endregion

    /**
     * Get application setting for given key
     *
     * @param key setting key
     * @return Application setting object
     */
    public ApplicationSetting getSetting(String key) {
        ApplicationSetting applicationSetting;
        Map<String, ApplicationSetting> settingsMap = getAllSettings();

        //Nothing to do
        if (settingsMap == null) {
            return null;
        }
        applicationSetting = settingsMap.get(key);
        return applicationSetting;
    }

    /**
     * Get report category descriptions map from the cache
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> getReportCategoryDescriptions() {
        return (Map<String, Map<String, String>>) cacheManager.get(CACHE_KEY_REPORT_CATEGORY_DESC);
    }

    /**
     * Get report sub category descriptions map from the cache
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> getReportSubCategoryDescriptions() {
        return (Map<String, Map<String, String>>) cacheManager.get(CACHE_KEY_REPORT_SUB_CATEGORY_DESC);
    }

    /**
     * retrieve category master data from DB and add to the cache as a Page wise Map
     *
     * @return cache update status
     */
    public int initReportCategories() {
        MasterDataDAO categoryDataDAO = hibernateDaoFactory.getMasterDataDAO();

        Map<String, PageReportCategories> pageMap = new HashMap<String, PageReportCategories>();
        Map<String, Map<String, String>> categoryDescMap = new HashMap<String, Map<String, String>>();
        Map<String, Map<String, String>> subCategoryDescMap = new HashMap<String, Map<String, String>>();

        PageReportCategories pageReportCategoryItem;
        List<ReportCategory> categoryList;
        ReportCategory category;
        ReportSubCategory subCategory;
        List<ReportSubCategory> subCategoryList;
        String subCategoryIds;
        String categoryIds;
        String subCategoryIdsForPage;

        List<Object> objectList = categoryDataDAO.getAll(MASTER_DATA_REPORT_CATEGORIES);

        for (Object cp : objectList) {

            ReportCategories categoryObj = (ReportCategories) cp;
            String[] pageIdList = categoryObj.getPageIds().split(Character.toString(IConstants.Delimiter.COMMA));
            for (String pageId : pageIdList) {
                pageReportCategoryItem = pageMap.get(pageId); // get page item from the map if exists
                if (pageReportCategoryItem == null) {   // page item does not exists in the map
                    pageReportCategoryItem = new PageReportCategories(pageId);  // create new page object
                    pageMap.put(pageId, pageReportCategoryItem); // add new item to the page map
                }
                categoryList = pageReportCategoryItem.getCategoryList();
                category = pageReportCategoryItem.getCategory(categoryObj.getCategory()); // get category from the category list if exists

                if (category == null) {  //  category does not exists in the List
                    category = new ReportCategory(categoryObj.getCategory(), categoryObj.getCategoryDescription());   // creates new category object from the record and add it to the list
                    category.setDisclosure((categoryObj.isDisclosure()) ? 1 : 0);
                    category.setReport((categoryObj.isReport()) ? 1 : 0);
                    category.setResearch((categoryObj.isResearch()) ? 1 : 0);
                    categoryList.add(category);
                    categoryIds = pageReportCategoryItem.getCategoryIds() + Character.toString(IConstants.Delimiter.COMMA) + Integer.toString(category.getCategoryId());
                    pageReportCategoryItem.setCategoryIds(categoryIds);
                }

                subCategoryList = category.getSubCategoryList();

                // creates new sub category object from the record
                subCategory = new ReportSubCategory(categoryObj.getCategory(), categoryObj.getSubCategory(), categoryObj.getSubCategoryDescription(),
                        categoryObj.isDisclosure(), categoryObj.isReport(), categoryObj.isResearch());
                if (!subCategoryList.contains(subCategory)) {
                    subCategoryList.add(subCategory);  // add to the sub category list
                    subCategoryIds = category.getSubCategoryIds() + Character.toString(IConstants.Delimiter.COMMA) + Integer.toString(subCategory.getSubCategoryId());
                    category.setSubCategoryIds(subCategoryIds);
                    subCategoryIdsForPage = pageReportCategoryItem.getSubCategoryIds() + Character.toString(IConstants.Delimiter.COMMA) + Integer.toString(subCategory.getSubCategoryId());
                    pageReportCategoryItem.setSubCategoryIds(subCategoryIdsForPage);
                }

            }

            categoryDescMap.put(String.valueOf(categoryObj.getCategory()), categoryObj.getCategoryDescription());
            subCategoryDescMap.put(String.valueOf(categoryObj.getSubCategory()), categoryObj.getSubCategoryDescription());

        }

        if (!pageMap.isEmpty()) {
            cacheManager.put(CACHE_KEY_REPORT_CATEGORIES, pageMap, 0);
        }

        if (!categoryDescMap.isEmpty()) {
            cacheManager.put(CACHE_KEY_REPORT_CATEGORY_DESC, categoryDescMap, 0);
        }

        if (!subCategoryDescMap.isEmpty()) {
            cacheManager.put(CACHE_KEY_REPORT_SUB_CATEGORY_DESC, subCategoryDescMap, 0);
        }

        return CacheUpdateStatus.UPDATE_SUCCEEDED;

    }

    @SuppressWarnings("uncheked")
    private void initComboBox() {
        MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
        for (ComboBoxType comboBoxType : ComboBoxType.values()) {
            List<Option> optionList = (List<Option>) applicationSettingsDAO.get(MASTER_DATA_COMBO_BOX, comboBoxType);
            cacheManager.put(getComboBoxCacheKey(comboBoxType), optionList, 0, 0);
        }
    }

    @SuppressWarnings("uncheked")
    public List<Option> getComboBox(ComboBoxType comboBoxType) {
        return (List<Option>) cacheManager.get(getComboBoxCacheKey(comboBoxType));
    }

    private String getComboBoxCacheKey(ComboBoxType comboBoxType) {
        StringBuilder sb = new StringBuilder(CACHE_KEY_PREFIX_COMBO_BOX);
        sb.append(UNDERSCORE_SIGN);
        sb.append(comboBoxType);
        return sb.toString();
    }

    /**
     * load all application settings from cache. Communicate with database if there is no data in cache
     *
     * @return map of application settings. map key = setting key , value = application setting
     */
    @SuppressWarnings("uncheked")
    private Map<String, ApplicationSetting> getAllSettings() {
        Map<String, ApplicationSetting> settingsMap;
        if(isCacheEnabled){
            settingsMap = (Map<String, ApplicationSetting>) cacheManager.get(CACHE_KEY_APPLICATION_SETTINGS);
            if (settingsMap == null) {
                MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
                settingsMap = new HashMap<String, ApplicationSetting>();
                List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_APP_SETTINGS);
                for (Object as : objectList) {
                    settingsMap.put(((ApplicationSetting) as).getSettingKey(), (ApplicationSetting) as);
                }

                if (!settingsMap.isEmpty()) {
                    cacheManager.put(CACHE_KEY_APPLICATION_SETTINGS, settingsMap, 0, 0);
                }
            }
        }else {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            settingsMap = new HashMap<String, ApplicationSetting>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_APP_SETTINGS);
            for (Object as : objectList) {
                settingsMap.put(((ApplicationSetting) as).getSettingKey(), (ApplicationSetting) as);
            }
        }
        return settingsMap;
    }

    /**
     * add ExchangeMetaData cache
     *
     * @return int status
     */
    private int initExchangeMetaData() {

        Map<String, ExchangeMetaData> exchangeMetaDataMap = null;

        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        List<Object> exchangeObjectList = masterDataDAO.getAll(MASTER_DATA_EXCHANGE_METADATA); //load from DB

        if (exchangeObjectList != null) {
            exchangeMetaDataMap = new HashMap<String, ExchangeMetaData>();

            for (Object o : exchangeObjectList) {
                ExchangeMetaData exchangeMetaData = (ExchangeMetaData) o;
                exchangeMetaDataMap.put(exchangeMetaData.getSourceId(), exchangeMetaData);
            }
        }

        this.cacheManager.put(CACHE_KEY_EXCHANGE_METADATA_MAP, exchangeMetaDataMap, 0);
        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * add StockMetaData cache
     *
     * @return int status
     */
    private int initStockMetaData() {

        Map<String, StockMetaData> stockMetaDataMap = null;

        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        List<Object> stockObjectList = masterDataDAO.getAll(MASTER_DATA_STOCK_METADATA); //load from DB

        if (stockObjectList != null) {
            stockMetaDataMap = new HashMap<String, StockMetaData>();
            for (Object o : stockObjectList) {
                StockMetaData stockMetaData = (StockMetaData) o;
                stockMetaDataMap.put(stockMetaData.getSourceId(), stockMetaData);
            }
        }

        this.cacheManager.put(CACHE_KEY_STOCK_METADATA_MAP, stockMetaDataMap, 0);
        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * get Country list from DB and put into the cache
     *
     * @return CacheUpdateStatus.UPDATE_SUCCEEDED
     */
    private int initCountry() {
//        int status;

        Map<String, CountryDTO> countryDTOMap = new HashMap<String, CountryDTO>();
        Map<String, Map<String, String>> countryDesc = new HashMap<String, Map<String, String>>();


        Map<String, String> countryRegionsMap = new HashMap<String, String>();
        Map<String, RegionCountry> regionCountriesMap = new HashMap<String, RegionCountry>();

        Map<String, RegionCountry> macroDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> fiDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> mfDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> priceDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> ipoDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> otherDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> companyDataRegionCountriesMap = new HashMap<String, RegionCountry>();
        Map<String, RegionCountry> editionControlRegionCountriesMap = new HashMap<String, RegionCountry>();

        List<CountryDTO> macroDataCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> priceDataCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> ipoDataCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> otherDataCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> companyDataCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> editionControlCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> fiDataCountryList = new ArrayList<CountryDTO>();
        List<CountryDTO> mfDataCountryList = new ArrayList<CountryDTO>();

        MetaDataDAO metaDataDAO = this.jdbcDaoFactory.getMetaDataDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(CountryDBHelper.getSelectQuery());
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        List<CountryDTO> countryList = (List<CountryDTO>) metaDataDAO.get(META_DATA_GET_COUNTRY_DATA, requestDBDTO);

        Map<String, String> countryExchangeMap = getCountryExchangeMapNoVirtual();

        for (CountryDTO country : countryList) {
            countryDTOMap.put(country.getCountryCode(), country);
            countryDesc.put(country.getCountryCode(), country.getDescription());

            //full region country
            populateEditionControlRegionCountryMap(regionCountriesMap, countryExchangeMap, getRegionMap(), country);

            //populate country region map
            populateCountryRegionMap(countryRegionsMap, getRegionMap(), country);

            if (country.isMacroData()) {
                macroDataCountryList.add(country);
                populateRegionCountryMap(macroDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
            if (country.isFIData()) {
                fiDataCountryList.add(country);
                populateRegionCountryMap(fiDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
            if (country.isMFData()) {
                mfDataCountryList.add(country);
                populateRegionCountryMap(mfDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
            if (country.isPriceData()) {
                priceDataCountryList.add(country);
                populateEditionControlRegionCountryMap(priceDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
            if (country.isIPOData()) {
                ipoDataCountryList.add(country);
                populateEditionControlRegionCountryMap(ipoDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
            if (country.isOtherData()) {
                otherDataCountryList.add(country);
                populateEditionControlRegionCountryMap(otherDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
            if (country.isCompanyData()) {
                companyDataCountryList.add(country);
                populateRegionCountryMap(companyDataRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }

            if (country.getIsEditionControlCountry()) {
                editionControlCountryList.add(country);
                populateEditionControlRegionCountryMap(editionControlRegionCountriesMap, countryExchangeMap, getRegionMap(), country);
            }
        }

        //Country region map format "Country_code" : "regionId~regionCode","regionId~regionCode"
        this.cacheManager.put(CACHE_KEY_COUNTRY_REGIONS, countryRegionsMap, 0);

        this.cacheManager.put(CACHE_KEY_REGION_COUNTRIES, regionCountriesMap, 0);

        //Full Country description Map
        this.cacheManager.put(CACHE_KEY_COUNTRY_DES, countryDesc, 0);

        //Full country list
        this.cacheManager.put(CACHE_KEY_COUNTRY_LIST, countryList, 0);

        // Fil countryDTO map
        this.cacheManager.put(CACHE_KEY_COUNTRY_MAP, countryDTOMap, 0);

        //Macro economy supported country list
        this.cacheManager.put(CACHE_KEY_MACRO_COUNTRY_LIST, macroDataCountryList, 0);

        //price feed supported country list
        this.cacheManager.put(CACHE_KEY_PRICE_COUNTRY_LIST, priceDataCountryList, 0);

        //IPO data supported country list
        this.cacheManager.put(CACHE_KEY_IOP_COUNTRY_LIST, ipoDataCountryList, 0);

        //Other data supported country list
        this.cacheManager.put(CACHE_KEY_OTHER_COUNTRY_LIST, otherDataCountryList, 0);

        //company data supported country list
        this.cacheManager.put(CACHE_KEY_COMPANY_COUNTRY_LIST, companyDataCountryList, 0);

        //edition control supported country list
        this.cacheManager.put(CACHE_KEY_EDITION_CONTROL_COUNTRY_LIST, editionControlCountryList, 0);

        //fi supported country list
        this.cacheManager.put(CACHE_KEY_FI_COUNTRY_LIST, fiDataCountryList, 0);

        //mf supported country list
        this.cacheManager.put(CACHE_KEY_MF_COUNTRY_LIST, mfDataCountryList, 0);

        this.cacheManager.put(CACHE_KEY_MACRO_REGION_COUNTRY_MAP, macroDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_FI_REGION_COUNTRY_MAP, fiDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_MF_REGION_COUNTRY_MAP, mfDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_IOP_REGION_COUNTRY_MAP, ipoDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_PRICE_REGION_COUNTRY_MAP, priceDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_OTHER_REGION_COUNTRY_MAP, otherDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_COMPANY_REGION_COUNTRY_MAP, companyDataRegionCountriesMap, 0);

        this.cacheManager.put(CACHE_KEY_EDITION_CONTROL_REGION_COUNTRY_MAP, editionControlRegionCountriesMap, 0);

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * get city data from db and put into cache
     *
     * @return status
     */
    private int initCity() {
        Map<Integer, CityDTO> cityMap = new HashMap<Integer, CityDTO>();
        Map<String, List<CityDTO>> countryCityList = new HashMap<String, List<CityDTO>>();
        Map<Integer, Map<String, String>> cityDesc = new HashMap<Integer, Map<String, String>>();
        CityDTO cityDTO;

        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();

        List<Object> cityList = masterDataDAO.getAll(MASTER_DATA_CITY);

        for (Object type : cityList) {
            City city = (City) type;
            cityDTO = new CityDTO(city.getCityId(), city.getCountryCode(), city.getShortDescription(), city.getLongDescription());

            cityMap.put(city.getCityId(), cityDTO);

            if (city.getCountryCode() != null) {
                if (!countryCityList.containsKey(city.getCountryCode())) {
                    countryCityList.put(city.getCountryCode(), new ArrayList<CityDTO>());
                }
                countryCityList.get(city.getCountryCode()).add(cityDTO);
            }

            cityDesc.put(city.getCityId(), city.getLongDescription());
        }

        this.cacheManager.put(CACHE_KEY_CITY_MAP, cityMap, 0);
        this.cacheManager.put(CACHE_KEY_COUNTRY_CITY_LIST, countryCityList, 0);
        this.cacheManager.put(CACHE_KEY_CITY_DESC, cityDesc, 0);

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * One country has more than one region
     *
     * @param countryRegionsMap Map to fill
     * @param regionMap         Regions
     * @param country           Country
     */
    private void populateCountryRegionMap(Map<String, String> countryRegionsMap, Map<Integer, RegionCountry> regionMap, CountryDTO country) {
        if (country == null || country.getRegionIds() == null) {
            return;
        }

        RegionCountry region;

        for (String rId : country.getRegionIds().split(String.valueOf(IConstants.Delimiter.COMMA))) {

            if (rId == null || rId.trim().isEmpty()) {
                continue;
            }
            //Region master data map
            region = regionMap.get(Integer.parseInt(rId));

            if (region == null || !(region.getEditionControlRegion() || region.getEditionControlRegionGroup())) {
                continue;
            }
            //First time this is null
            String currentRegions = countryRegionsMap.get(country.getCountryCode());

            StringBuilder regionStr = new StringBuilder();
            if (currentRegions == null) { //first time
                regionStr.append(region.getRegionId());
                regionStr.append(CharUtils.toString(IConstants.Delimiter.TILDE));
                regionStr.append(region.getRegion());
            } else {
                regionStr.append(currentRegions);
                regionStr.append(CharUtils.toString(IConstants.Delimiter.COMMA));
                regionStr.append(region.getRegionId());
                regionStr.append(CharUtils.toString(IConstants.Delimiter.TILDE));
                regionStr.append(region.getRegion());
            }

            //adding or overriding
            countryRegionsMap.put(country.getCountryCode(), regionStr.toString());
        }
    }

    private void populateRegionCountryMap(Map<String, RegionCountry> regionCountryMap, Map<String, String> countryExchangeMap, Map<Integer, RegionCountry> regionMap, CountryDTO country) {

        if (country == null || country.getRegionIds() == null) {
            return;
        }


        for (String rId : country.getRegionIds().split(String.valueOf(IConstants.Delimiter.COMMA))) {


            if (rId == null || rId.trim().isEmpty()) {
                continue;
            }

            //get created region country list
            RegionCountry region = regionCountryMap.get(rId);


            //first time there is not regionCountryMap elements.
            //getting original region details form region master
            //this region does not contain eny country. country list is empty
            if (region == null) {
                region = regionMap.get(Integer.parseInt(rId));
            }

            StringBuilder countryStr;
            StringBuilder exchangeStr;

            if (region != null) {
                //First time : this is null
                String countries = region.getCountries();
                //First time : this is null
                String exchanges = region.getExchanges();

                if (countries == null) {//nothing to think. set country code to region
                    region.setCountries(country.getCountryCode());
                } else {
                    //already have at least one country.
                    //top-up existing country with comma
                    countryStr = new StringBuilder();
                    countryStr.append(countries); //add  country
                    countryStr.append(CharUtils.toString(IConstants.Delimiter.COMMA));  //add comma
                    countryStr.append(country.getCountryCode());  //add country code
                    region.setCountries(countryStr.toString());  //set new "${countryCode} , ${countryCode}" country list to
                }

                if (countryExchangeMap != null && countryExchangeMap.get(country.getCountryCode()) != null) {
                    //set exchanges
                    if (exchanges == null) {
                        region.setExchanges(countryExchangeMap.get(country.getCountryCode()));
                    } else {
                        exchangeStr = new StringBuilder();
                        exchangeStr.append(exchanges);
                        exchangeStr.append(CharUtils.toString(IConstants.Delimiter.COMMA));
                        exchangeStr.append(countryExchangeMap.get(country.getCountryCode()));
                        region.setExchanges(exchangeStr.toString());
                    }
                }

                region.getCountriesList().add(country);

                regionCountryMap.put(Integer.toString(region.getRegionId()), region);

            }


        }

    }

    private void populateEditionControlRegionCountryMap(Map<String, RegionCountry> regionCountryMap, Map<String, String> countryExchangeMap, Map<Integer, RegionCountry> regionMap, CountryDTO country) {

        if (country == null || country.getRegionIds() == null) {
            return;
        }


        for (String rId : country.getRegionIds().split(String.valueOf(IConstants.Delimiter.COMMA))) {


            if (rId == null || rId.trim().isEmpty()) {
                continue;
            }

            //get created region country list
            RegionCountry region = regionCountryMap.get(rId);


            //first time there is not regionCountryMap elements.
            //getting original region details form region master
            //this region does not contain eny country. country list is empty
            if (region == null) {
                region = regionMap.get(Integer.parseInt(rId));
            }

            StringBuilder countryStr;
            StringBuilder exchangeStr;

            if (region != null && (region.getEditionControlRegion() || region.getEditionControlRegionGroup())) {
                //First time : this is null
                String countries = region.getCountries();
                //First time : this is null
                String exchanges = region.getExchanges();

                if (countries == null) {//nothing to think. set country code to region
                    region.setCountries(country.getCountryCode());
                } else {
                    //already have at least one country.
                    //top-up existing country with comma
                    countryStr = new StringBuilder();
                    countryStr.append(countries); //add  country
                    countryStr.append(CharUtils.toString(IConstants.Delimiter.COMMA));  //add comma
                    countryStr.append(country.getCountryCode());  //add country code
                    region.setCountries(countryStr.toString());  //set new "${countryCode} , ${countryCode}" country list to
                }

                if (countryExchangeMap != null && countryExchangeMap.get(country.getCountryCode()) != null) {
                    //set exchanges
                    if (exchanges == null) {
                        region.setExchanges(countryExchangeMap.get(country.getCountryCode()));
                    } else {
                        exchangeStr = new StringBuilder();
                        exchangeStr.append(exchanges);
                        exchangeStr.append(CharUtils.toString(IConstants.Delimiter.COMMA));
                        exchangeStr.append(countryExchangeMap.get(country.getCountryCode()));
                        region.setExchanges(exchangeStr.toString());
                    }
                }

                region.getCountriesList().add(country);

                regionCountryMap.put(Integer.toString(region.getRegionId()), region);

            }


        }

    }

    @SuppressWarnings("uncheked")
    private Map<Integer, RegionCountry> getRegionMap() {
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();

        Map<Integer, RegionCountry> regionMap = (Map<Integer, RegionCountry>) this.cacheManager.get(CACHE_KEY_REGIONS);

        if (regionMap == null) {

            List<Object> allRegionCountries = masterDataDAO.getAll(MASTER_DATA_REGION_COUNTRY);
            regionMap = new HashMap<Integer, RegionCountry>(allRegionCountries.size());

            for (Object r : allRegionCountries) {
                if (r == null) {
                    continue;
                }

                RegionCountry region = (RegionCountry) r;
                regionMap.put(region.getRegionId(), region);
            }

            this.cacheManager.put(CACHE_KEY_REGIONS, regionMap, 0);

        }

        return regionMap;
    }

    /**
     * Get region descriptions map of all regions in RegionCountry table
     *
     * @return Region descriptions map
     */
    @SuppressWarnings("uncheked")
    public Map<String, Map<String, String>> getRegionDescriptions() {
        Map<String, Map<String, String>> regionDescriptionsMap = (Map<String, Map<String, String>>) this.cacheManager.get(CACHE_KEY_REGION_DESCRIPTIONS);

        if (regionDescriptionsMap == null) {

            Map<Integer, RegionCountry> allRegionCountries = this.getRegionMap();
            regionDescriptionsMap = new HashMap<String, Map<String, String>>(allRegionCountries.size());

            for (Object r : allRegionCountries.values()) {
                if (r == null) {
                    continue;
                }

                RegionCountry region = (RegionCountry) r;
                regionDescriptionsMap.put(String.valueOf(region.getRegionId()), region.getRegionDescriptionsList());
            }

            this.cacheManager.put(CACHE_KEY_REGION_DESCRIPTIONS, regionDescriptionsMap, 0);

        }

        return regionDescriptionsMap;
    }

    /**
     * Init global edition drop down settings.This is using to populate the continent part under supported region
     * section
     *
     * @return cache update status
     */
    private int initGlobalEditionSettings() {
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        List<Object> globalEditionSettings = masterDataDAO.getAll(MASTER_DATA_GLOBAL_EDITION_SETTINGS);

        List<GlobalEditionSettings> regionCountriesMap = new ArrayList<GlobalEditionSettings>(globalEditionSettings.size());
        Map<String, String> filteredCountryExgMap = null;
        Map<String, CountryDTO> countryMap = getAllCountries();
        StringTokenizer strTkn;
        List<CountryDTO> countriesList;
        String countryCode;
        CountryDTO country;

        Map<String, String> countryExchangeMap = getCountryExchangeMapNoVirtual();

        for (Object r : globalEditionSettings) {
            if (r == null) {
                continue;
            }

            GlobalEditionSettings globalSettings = (GlobalEditionSettings) r;

            strTkn = new StringTokenizer(globalSettings.getCountries(), Character.toString(IConstants.Delimiter.COMMA));
            countriesList = new ArrayList<CountryDTO>(strTkn.countTokens());
            filteredCountryExgMap = new HashMap<String, String>(strTkn.countTokens());

            while (strTkn.hasMoreTokens()) {
                countryCode = strTkn.nextToken().toUpperCase();
                country = countryMap.get(countryCode);    // get Country object for the country code

                if (country == null) {  // create new country since country does not exists
                    country = new CountryDTO();
                    country.setCountryCode(countryCode);
                }

                if (countryExchangeMap != null && countryExchangeMap.get(countryCode) != null) {
                    filteredCountryExgMap.put(countryCode, countryExchangeMap.get(countryCode));
                }

                countriesList.add(country);
                country = null;
            }

            globalSettings.setCountriesList(countriesList);
            globalSettings.setCountryExchangeMap(filteredCountryExgMap);
            countriesList = null;
            filteredCountryExgMap = null;

            //add to region country map
            regionCountriesMap.add(globalSettings);
        }

        this.cacheManager.put(CACHE_KEY_GLOBAL_EDITION_SETTINGS, regionCountriesMap, 0);

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * Init main edition controls settings.This is using to populate the continent part under supported region
     * section
     *
     * @return cache update status
     */
    private int initEditionControlSettings() {
        List<EditionControlSettingsDTO> editionControlRegionGroupList = new ArrayList<EditionControlSettingsDTO>();
        List<EditionControlSettingsDTO> editionControlRegionList = new ArrayList<EditionControlSettingsDTO>();
        Map<String, RegionCountry> regionCountryMap = getRegionCountryMap();
        Map<String, String> countryExchangeMap = getCountryExchangeMapNoVirtual();
        Map<String, String> filteredCountryExgMap = null;
        List<CountryDTO> countriesList;
        List<String> countryCodes;

        for (RegionCountry regionCountry : regionCountryMap.values()) {
            if (regionCountry.getEditionControlRegion() || regionCountry.getEditionControlRegionGroup()) {
                countriesList = new ArrayList<CountryDTO>(regionCountry.getCountriesList().size());
                countryCodes = new ArrayList<String>(regionCountry.getCountriesList().size());
                filteredCountryExgMap = new HashMap<String, String>();

                for (CountryDTO country : regionCountry.getCountriesList()) {
                    if (country.getIsEditionControlCountry()) {
                        countriesList.add(country);
//                        countriesList.add(new CountryDTO(country.getCountryCode(), country.getDesc(), country.getRegionIds(), country.isMacroData(), country.isPriceData(), country.isIPOData(), country.isOtherData(), country.isCompanyData(), country.getIsEditionControlCountry(), country.getDescription()));
                        countryCodes.add(country.getCountryCode());

                        if (countryExchangeMap != null && countryExchangeMap.get(country.getCountryCode()) != null) {
                            filteredCountryExgMap.put(country.getCountryCode(), countryExchangeMap.get(country.getCountryCode()));
                        }
                    }
                }

                //TODO sort for all the languages
                countriesList = sortCountryList(countriesList, "EN");

                if (regionCountry.getEditionControlRegion()) {
                    editionControlRegionList.add(new EditionControlSettingsDTO(regionCountry.getRegionId(), regionCountry.getRegionDescriptions(), regionCountry.getRegionDescriptionsList(), StringUtils.join(countryCodes, CharUtils.toString(IConstants.Delimiter.COMMA)), countriesList, filteredCountryExgMap, regionCountry.getExchanges()));
                }

                if (regionCountry.getEditionControlRegionGroup()) {
                    editionControlRegionGroupList.add(new EditionControlSettingsDTO(regionCountry.getRegionId(), regionCountry.getRegionDescriptions(), regionCountry.getRegionDescriptionsList(), StringUtils.join(countryCodes, CharUtils.toString(IConstants.Delimiter.COMMA)), countriesList, filteredCountryExgMap, regionCountry.getExchanges()));
                }
            }
        }

        this.cacheManager.put(CACHE_KEY_EDITION_CONTROL_REGIONS, sortRegionList(editionControlRegionList, "EN"), 0);
        this.cacheManager.put(CACHE_KEY_EDITION_CONTROL_REGION_GROUP, sortRegionList(editionControlRegionGroupList, "EN"), 0);

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    private static List<CountryDTO> sortCountryList(List<CountryDTO> countryDTOList, String language) {
        final String lang = language.toUpperCase();

        Collections.sort(countryDTOList, new Comparator<CountryDTO>() {
            public int compare(CountryDTO c1, CountryDTO c2) {
                if (c1.getCountryLangDTOMap().get(lang) != null && c1.getCountryLangDTOMap().get(lang).getShortName() != null
                        && c2.getCountryLangDTOMap().get(lang) != null && c2.getCountryLangDTOMap().get(lang).getShortName() != null) {
                    return (c1.getCountryLangDTOMap().get(lang).getShortName()).compareTo(c2.getCountryLangDTOMap().get(lang).getShortName());
                } else {
                    return (c1.getCountryCode()).compareTo(c2.getCountryCode());

                }
            }
        });

        return countryDTOList;
    }

    private static List<EditionControlSettingsDTO> sortRegionList(List<EditionControlSettingsDTO> regionList, String language) {
        final String lang = language.toUpperCase();

        Collections.sort(regionList, new Comparator<EditionControlSettingsDTO>() {
            public int compare(EditionControlSettingsDTO c1, EditionControlSettingsDTO c2) {
                return ((c1.getRegionDescriptionsMap()).get(lang)).compareTo((c2.getRegionDescriptionsMap()).get(lang));
            }
        });

        return regionList;
    }

    /**
     * Get corporate actions from database and put the processed data in the cache
     *
     * @return status of the cache update process
     */
    private int initCorporateActionsTypes() {

        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        List<Object> corporateActionTypes = masterDataDAO.getAll(MASTER_DATA_CORPORATE_ACTION_TYPES);

        CorporateActionTypes types = new CorporateActionTypes();

        for (Object type : corporateActionTypes) {
            CorporateActionType action = (CorporateActionType) type;

            if (action.getCorpActTypeCategory() == IConstants.CorporateActionCategoryTypes.CAPITAL_EQUITY_DESC) {
                types.addCapitalActionTypes(new CorporateActionTypeItem(action.getCorpActionType().toString(), action.getDescription()));
            } else if (action.getCorpActTypeCategory() == IConstants.CorporateActionCategoryTypes.NON_CAPITAL_DESC) {
                types.addNonCapitalActionTypes(new CorporateActionTypeItem(action.getCorpActionType().toString(), action.getDescription()));
            }
        }

        this.cacheManager.put(CACHE_KEY_CORPORATE_ACTION_TYPES, types, 0);

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * Get Sector Master Data and put to the cache. Include GICS and Exchange sector master data
     *
     * @return status of the cache update process
     */
    private int initSectorTypes() {

        Map<String, List<SectorDTO>> sectorMap = new HashMap<String, List<SectorDTO>>();
        List<SectorDTO> sectorDTOList = null;
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        String previousSectorType = null;

        List<Object> sectorList = masterDataDAO.getAll(MASTER_DATA_SECTORS); //load from DB

        for (Object type : sectorList) {
            SectorDTO sectorDTO = (SectorDTO) type;
            if (previousSectorType == null || !previousSectorType.equalsIgnoreCase(sectorDTO.getSectorType().trim())) { //for for a new sectorType
                if (previousSectorType != null && !sectorDTOList.isEmpty()) { // this is not the first iteration. So add to the previous sectorDTOList to the map
                    sectorDTOList = sortIndustryList(sectorDTOList);
                    sectorMap.put(previousSectorType, sectorDTOList);
                }
                previousSectorType = sectorDTO.getSectorType();
                sectorDTOList = new ArrayList<SectorDTO>();
            }
            sectorDTOList.add(sectorDTO);
        }

        if (previousSectorType != null && !sectorDTOList.isEmpty()) {
            sectorDTOList = sortIndustryList(sectorDTOList);
            sectorMap.put(previousSectorType, sectorDTOList);
        }

        this.cacheManager.put(CACHE_KEY_SECTOR_LIST, sectorMap, 0);
        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /*Sort industry tree*/
    public List<SectorDTO> sortIndustryList(List<SectorDTO> list) {
        Collections.sort(list, new Comparator<SectorDTO>() {
            public int compare(SectorDTO o1, SectorDTO o2) {
                return o1.getShortDescription().get("EN").compareTo(o2.getShortDescription().get("EN"));
            }
        });
        return list;
    }

    private int initScreenerCategories() {

        List<Map<String, String>> categoriesList = new ArrayList<Map<String, String>>();
        Map<String, String> categoryMap;
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();

        List<Object> cateList = masterDataDAO.getAll(MASTER_DATA_SCREENER_CATEGORIES); //load from DB

        for (Object category : cateList) {
            ScreenerCategory screenerCategory = (ScreenerCategory) category;
            categoryMap = new HashMap<String, String>();

            categoryMap.put("cat", screenerCategory.getCategory());
            categoryMap.put("desc", screenerCategory.getDescription());
            categoryMap.put("lang", screenerCategory.getLanguage());

            categoriesList.add(categoryMap);
        }

        this.cacheManager.put(CACHE_KEY_SCREENER_CATEGORY, categoriesList, 0);
        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    @SuppressWarnings("uncheked")
    public PageReportCategories getPageCategories(String pageId, String language) {
        final String lang;
        if (language != null && !StringUtils.isEmpty(language)) {
            lang = language.toUpperCase();
        } else {
            lang = "EN";
        }
        PageReportCategories pageReportCategories = null;
        Object cacheObj;
        Object sortedCacheObj;
        List<ReportCategory> categoryList;
        List<ReportSubCategory> subCategoryList;
        StringBuilder cacheKey = new StringBuilder();
        String cacheKeyString;

        cacheKey.append(CACHE_KEY_REPORT_CATEGORIES).append(IConstants.Delimiter.TILDE).append(lang).append(IConstants.Delimiter.TILDE).append(pageId);
        cacheKeyString = cacheKey.toString();

        sortedCacheObj = cacheManager.get(cacheKeyString);

        if (sortedCacheObj == null) {
            cacheObj = cacheManager.get(CACHE_KEY_REPORT_CATEGORIES);

            if (cacheObj != null) {
                pageReportCategories = ((Map<String, PageReportCategories>) cacheObj).get(pageId);

                if (pageReportCategories.getCategoryList() != null && !pageReportCategories.getCategoryList().isEmpty()) {
                    categoryList = pageReportCategories.getCategoryList();

                    Collections.sort(categoryList, new Comparator<ReportCategory>() {
                        public int compare(ReportCategory m1, ReportCategory m2) {
                            if (m2.getDescription().get(lang) != null) {
                                return (-1) * m2.getDescription().get(lang).compareTo(m1.getDescription().get(lang));
                            } else {
                                return (-1);
                            }
                        }
                    });

                    for (ReportCategory cat : categoryList) {
                        subCategoryList = cat.getSubCategoryList();
                        Collections.sort(subCategoryList, new Comparator<ReportSubCategory>() {
                            public int compare(ReportSubCategory m1, ReportSubCategory m2) {
                                if (m2.getDescription().get(lang) != null) {
                                    return (-1) * m2.getDescription().get(lang).compareTo(m1.getDescription().get(lang));
                                } else {
                                    return -1;
                                }
                            }
                        });
                    }
                }
                this.cacheManager.put(cacheKeyString, pageReportCategories);
            }

        } else {
            pageReportCategories = (PageReportCategories) sortedCacheObj;
        }


        return pageReportCategories;
    }

    /**
     * Method to get All report providers
     *
     * @return ReportProvider objects
     */
    @SuppressWarnings("uncheked")
    public List<ReportProvider> getReportProviders() {
        MasterDataDAO reportProviderDAO = hibernateDaoFactory.getMasterDataDAO();
        Object cacheObj = cacheManager.get(CACHE_KEY_REPORT_PROVIDERS);
        List<ReportProvider> prvList = null;
        if (cacheObj == null) {

            List<Object> objectList = reportProviderDAO.getAll(MASTER_DATA_REPORT_PROVIDERS);
            if (objectList != null) {
                prvList = new ArrayList<ReportProvider>();
                for (Object prv : objectList) {
                    prvList.add((ReportProvider) prv);
                }

                cacheManager.put(CACHE_KEY_REPORT_PROVIDERS, prvList, 0);
            }
        } else {
            prvList = (List<ReportProvider>) cacheObj;
        }
        return prvList;
    }

    /**
     * Get Master data from cache
     *
     * @param cacheKey cache key to get the relevant cached object
     * @return cached object, returns null if not found any cached object to the given key
     */
    @SuppressWarnings("uncheked")
    public Object getMasterData(String cacheKey) {
        return cacheManager.get(cacheKey);
    }

    public Map<String, List<Url>> getMenuItems(String key) {
        Object o = this.cacheManager.get(CACHE_KEY_URLS);
        Map<String, List<Url>> urls = null;
        if (o == null) {
            initUrls();
            o = this.cacheManager.get(CACHE_KEY_URLS);
            urls = (Map<String, List<Url>>) o;
        } else {
            urls = (Map<String, List<Url>>) o;
        }

        return urls;
    }

    /**
     * Get Product Classification from cache
     * @return Map which contains ProductClassificationDTOs
     */
    public Map<String, ProductClassificationDTO> getProductClassification(){
        Map<String, ProductClassificationDTO> productClassificationMap = (Map<String, ProductClassificationDTO>) cacheManager.get(CacheKeyConstant.CACHE_KEY_PRODUCT_CLASSIFICATION);

        if(productClassificationMap == null){
           productClassificationMap = initProductClassification();
        }
        return productClassificationMap;
    }

    /**
     * Initialize Product classification to cache
     * @return map which contains productClassificationDTOs
     */
    private Map<String, ProductClassificationDTO> initProductClassification() {

        Map<String, ProductClassificationDTO> productClassificationMap = null;
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        List<Object> productClassificationList = masterDataDAO.getAll(MASTER_DATA_PRODUCT_CLASSIFICATION);

        if (productClassificationList != null) {
            productClassificationMap = new HashMap<String, ProductClassificationDTO>();

            for (Object o : productClassificationList) {
                ProductClassificationDTO productClassificationDTO = (ProductClassificationDTO) o;
                productClassificationMap.put(productClassificationDTO.getProductClassificationCode(), productClassificationDTO);
            }
        }
        this.cacheManager.put(CACHE_KEY_PRODUCT_CLASSIFICATION, productClassificationMap, 0);
        return productClassificationMap;
    }

    public Map<String, String> getAllTickerCurrency() {
        Map<String, String> tickerCurrencyDataList = (Map<String, String>) this.cacheManager.get(CACHE_KEY_All_TICKER_CURRENCT_DATA);

        if (tickerCurrencyDataList == null) {
            tickerCurrencyDataList = initTickerCurrency();
        }
        return tickerCurrencyDataList;
    }

    private Map<String, String> initTickerCurrency() {
        String sql = MasterDataDbHelper.QUERY_TICKER_CURRENCY;
        MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
        Map<String, String> tickerCurrencyDataList = (Map<String, String>) masterDataDAO.get(IConstants.ControlPathTypes.GET_ALL_TICKER_CURRENCY, sql);

        if (tickerCurrencyDataList != null) {
            this.cacheManager.put(CACHE_KEY_All_TICKER_CURRENCT_DATA, tickerCurrencyDataList, TIME_TO_LIVE_TWENTY_FOUR_HOURS);
        }
        return tickerCurrencyDataList;
    }

    /**
     * get sectors list for Industry menu
     *
     * @return lest
     */
    @SuppressWarnings("uncheked")
    public Map<String, List<SectorDTO>> getIndustryMenuItems() {
        Map<String, List<SectorDTO>> stringListMap;
        Object itemObject = this.cacheManager.get(CACHE_KEY_PREFIX_INDUSTRY_MENU_ITEMS);

        if (itemObject == null) {
            Map<String, List<SectorDTO>> allSectorList = (Map<String, List<SectorDTO>>) this.getMasterData(CacheKeyConstant.CACHE_KEY_SECTOR_LIST);
            stringListMap = new HashMap<String, List<SectorDTO>>();

            if (allSectorList != null) {
//                for (SectorDTO sectorDTO : allSectorList.get(IConstants.SectorTypes.GICS1)) {
//                    sectorList.add(sectorDTO);
//                }
                stringListMap.put(IConstants.SectorTypes.GICS1, allSectorList.get(IConstants.SectorTypes.GICS1));
                stringListMap.put(IConstants.SectorTypes.GICS2, allSectorList.get(IConstants.SectorTypes.GICS2));

                if (!stringListMap.isEmpty()) {
                    this.cacheManager.put(CACHE_KEY_PREFIX_INDUSTRY_MENU_ITEMS, stringListMap, 0);
                }
            }

        } else {
            stringListMap = (Map<String, List<SectorDTO>>) itemObject;
        }

        return stringListMap;
    }

    private int initUrls() {
        Map<String, List<Url>> urls = new HashMap<String, List<Url>>();
        IAppConfigData iAppConfigData = this.hibernateDaoFactory.getAppConfigDataDAO();

        List<URLMetadata> allUrls = iAppConfigData.getAllUrls();

        for (URLMetadata url : allUrls) {
            if (url.getUrl() == null) {
                continue;
            }

            List<Url> urlList = urls.get(url.getMenuGroup());

            if (urlList == null) {
                urlList = new ArrayList<Url>();
                urlList.add(new Url(url.getUrl(), url.getUrlId(), url.getResourceKey(), url.isActive()));
                urls.put(url.getMenuGroup(), urlList);

            } else {
                urlList.add(new Url(url.getUrl(), url.getUrlId(), url.getResourceKey(), url.isActive()));
            }
        }

        this.cacheManager.put(CACHE_KEY_URLS, urls, 0);
        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * init all application settings
     */
    private void initAllSettings() {
        getAllSettings();
    }

    /**
     * Init method to initialize currency rates
     *
     * @return status
     */
    public Map<String, Float> initAndGetCurrencyRates() {
        MetaDataDAO metaDataDAO = this.hibernateDaoFactory.getMetaDataDAO();
        List<Object> allCurrencyRates = metaDataDAO.getAll(META_DATA_CURRENCY_RATES);
        Map<String, Float> currencyPairs = new HashMap<String, Float>(allCurrencyRates.size());

        for (Object currencyRate : allCurrencyRates) {
            String pair = ((CurrencyRate) currencyRate).getCurrencyPair();
            float rate = ((CurrencyRate) currencyRate).getRate();
            currencyPairs.put(pair, rate);
        }

        cacheManager.put(CACHE_KEY_CURRENCY_RATES, currencyPairs, TIME_TO_LIVE_THIRTY_MINUTES);
        return currencyPairs;
    }

    /**
     * Init method to get all Top News, edition meta data
     */
    private Map<String, TopNewsEditionDTO> initAndGetTopNewsEdition() {

        Map<String, TopNewsEditionDTO> editionDTOMap;

        TopNewsEditionDTO editionDTO;

        MetaDataDAO metaDataDAO = this.hibernateDaoFactory.getMetaDataDAO();
        List<Object> topNewsEdtObj = metaDataDAO.getAll(DBConstants.MetaDataType.META_DATA_GET_TOP_NEWS_EDITION_DATA);

        editionDTOMap = new HashMap<String, TopNewsEditionDTO>();
        List<TopNewsEditionDTO> editionDTOList = new ArrayList<TopNewsEditionDTO>();

        for (Object edition : topNewsEdtObj) {
            editionDTO = ((TopNewsEdition) edition).getTopNewsEditionDTO();
            editionDTOMap.put(editionDTO.getEdition(), editionDTO);
            editionDTOList.add(editionDTO);
        }

        this.cacheManager.put(CACHE_KEY_TOP_NEWS_EDITION_MAP, editionDTOMap, 0);
        this.cacheManager.put(CACHE_KEY_TOP_NEWS_EDITION_LIST, editionDTOList, 0);

        return editionDTOMap;
    }

    /**
     * Remove all previous presisted session data.
     */
    private void initSessionData() {
        UserDetailsDAO uerDAO = this.hibernateDaoFactory.getUerDAO();
        uerDAO.removeAllSessionData(productId);
    }

    private void initAllSessionCache() {
        this.userDetailsDataAccess.getActiveUserSessions();
    }

    /**
     * Get Country Map
     *
     * @return all countries
     */
    @SuppressWarnings("uncheked")
    public Map<String, CountryDTO> getAllCountries() {
        return (Map<String, CountryDTO>) this.cacheManager.get(CACHE_KEY_COUNTRY_MAP);
    }

    /**
     * Get Country Map
     *
     * @return all countries
     */
    @SuppressWarnings("uncheked")
    public List<CountryDTO> getAllCountriesList() {
        List<CountryDTO> countryDTOList = (List<CountryDTO>) this.cacheManager.get(CACHE_KEY_COUNTRY_LIST);
        if (countryDTOList == null) {
            initCountry();
            countryDTOList = (List<CountryDTO>) this.cacheManager.get(CACHE_KEY_COUNTRY_LIST);
        }
        return countryDTOList;
    }

    @SuppressWarnings("uncheked")
    public Map<String, Map<String, String>> getCountryDes() {
        return (Map<String, Map<String, String>>) this.cacheManager.get(CACHE_KEY_COUNTRY_DES);
    }

    @SuppressWarnings("uncheked")
    public List<CountryDTO> getMacroCountries() {
        return (List<CountryDTO>) this.cacheManager.get(CACHE_KEY_MACRO_COUNTRY_LIST);
    }

    @SuppressWarnings("uncheked")
    public Map<String, CountryDTO> getAllCountry() {
        return (Map<String, CountryDTO>) this.cacheManager.get(CACHE_KEY_COUNTRY_MAP);
    }

    @SuppressWarnings("uncheked")
    public String getAllCountryJSON() {
        String countryMapJson = (String) this.cacheManager.get(CACHE_KEY_COUNTRY_MAP_JSON);
        if (countryMapJson == null) {
            Map countryMap = getAllCountry();
            if (countryMap != null) {
                countryMapJson = this.gson.toJson(countryMap);
                this.cacheManager.put(CACHE_KEY_COUNTRY_MAP_JSON, countryMapJson);
            }
        }
        return countryMapJson;
    }

    /**
     * Private helper method to get application supported exchanges from cache
     *
     * @return list of app supported exchanges
     */
    @SuppressWarnings("unchecked")
    public List<String> getSupportedExchanges() {
        Map<String, ApplicationSetting> settingsMap = (Map) this.cacheManager.get(CACHE_KEY_APPLICATION_SETTINGS);
        if (settingsMap == null) {
            settingsMap = getAllSettings();
        }
        return settingsMap.get(SUPPORTED_EXCHANGES).getValueList();
    }

    /**
     * Private helper method to get application supported exchanges from cache
     *
     * @return list of app supported exchanges
     */
    @SuppressWarnings("unchecked")
    public List<String> getSubscriptionExchanges() {
        Map<String, ApplicationSetting> settingsMap = (Map) this.cacheManager.get(CACHE_KEY_APPLICATION_SETTINGS);
        if (settingsMap == null) {
            settingsMap = getAllSettings();
        }
        return settingsMap.get(ApplicationSettingsKeys.SUBSCRIPTION_EXCHANGES).getValueList();
    }

    /**
     * Private helper method to get application supported exchanges from cache
     *
     * @return list of app supported exchanges
     */
    @SuppressWarnings("unchecked")
    public List<String> getFeedAvailSupportedExchanges() {
        Map<String, ApplicationSetting> settingsMap = (Map) this.cacheManager.get(CACHE_KEY_APPLICATION_SETTINGS);
        if (settingsMap == null) {
            settingsMap = getAllSettings();
        }
        return settingsMap.get(ApplicationSettingsKeys.FEED_AVAIL_SUPPORTED_EXCHANGES).getValueList();
    }

    /**
     * Utility method to provide all exchanges data. Result is filtered by supported exchanges
     *
     * @return List of Exchange data
     */
    @SuppressWarnings("uncheked")
    private List<MarketDTO> getAllExchangeData() {

        List<MarketDTO> exchangeDataList = (List<MarketDTO>) this.cacheManager.get(CACHE_KEY_All_MARKET_DATA);

        if (exchangeDataList == null) {
            String sql = MasterDataDbHelper.QUERY_MARKET;
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            exchangeDataList = (List<MarketDTO>) masterDataDAO.get(IConstants.ControlPathTypes.APP_SUPPORTED_MARKET_DATA, sql);
            this.cacheManager.put(CACHE_KEY_All_MARKET_DATA, exchangeDataList, 0);
        }

        return exchangeDataList;
    }

    /**
     * Utility Method to get Supported exchange Data list
     *
     * @return List of Exchange data
     */
    @SuppressWarnings("uncheked")
    public List<MarketDTO> getSupportedExchangeData() {
        List<String> supportedExchanges = getSupportedExchanges();

        List<MarketDTO> exchangeDataList = (List<MarketDTO>) this.cacheManager.get(CACHE_KEY_SUPPORTED_MARKET_DATA);

        if (exchangeDataList == null) {
            String sql = MessageFormat.format(MasterDataDbHelper.SQL_GET_SUPPORTED_EXCHANGES, FormatUtils.getQuotedArrayString(supportedExchanges, true));
            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            exchangeDataList = (List<MarketDTO>) masterDataDAO.get(IConstants.ControlPathTypes.APP_SUPPORTED_MARKET_DATA, sql);
            this.cacheManager.put(CACHE_KEY_SUPPORTED_MARKET_DATA, exchangeDataList, 0);
        }

        return exchangeDataList;

    }

    /**
     * Get sector snapshot data for provided source id
     *
     * @param sourceId exchange
     * @return sectors
     */
    @SuppressWarnings("uncheked")
    public List<SectorDTO> getSectorSnapshotData(String sourceId) {
        List<SectorDTO> sectorDTOList = null;
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();

        List<Object> sectorList = (List<Object>) masterDataDAO.get(MASTER_DATA_SECTOR_SNAPSHOT, sourceId); //load from DB

        if (sectorList != null) {
            sectorDTOList = new ArrayList<SectorDTO>(sectorList.size());

            for (Object type : sectorList) {
                SectorDTO sectorDTO = (SectorDTO) type;
                sectorDTOList.add(sectorDTO);
            }
        }

        return sectorDTOList;
    }

    /**
     * Init method to initialize exchange data
     *
     * @return status
     */
    private int initExchangeData() {
        List<String> supportedExchanges = getFeedAvailSupportedExchanges();
        List<MarketDTO> marketDTOList = getAllExchangeData();
        List<MarketDTO> supportedSourceList = new ArrayList<MarketDTO>(supportedExchanges.size());
        String cacheKey;

        Map<String, Map> exchangeDes = new HashMap<String, Map>();
        Map<String, String> displayExgToExchange = new HashMap<String, String>();
        Map<String, String> exchangeToDisplayExg = new HashMap<String, String>();
        Map<String, String> exchangeDecimalPlaces = new HashMap<String, String>();
        List<String> virtualExchanges = new ArrayList<String>(1);

        for (MarketDTO marketDTO : marketDTOList) {

            String sourceId = marketDTO.getExchangeCode();
            exchangeDes.put(sourceId, marketDTO.getLongDescriptions());
            displayExgToExchange.put(marketDTO.getDisplayExchange().toUpperCase(), sourceId);
            exchangeToDisplayExg.put(sourceId, marketDTO.getDisplayExchange());
            exchangeDecimalPlaces.put(sourceId, marketDTO.getDecimalPlaces());

            if (marketDTO.getIsVirtualExchange() != null && marketDTO.getIsVirtualExchange().equals("1")) {
                virtualExchanges.add(marketDTO.getExchangeCode());
            }

            if (!supportedExchanges.contains(sourceId)) {    // only supported exchanges(marketDTO) are stored in cache
                continue;
            }

            cacheKey = CacheKeyGenerator.getMarketDataCacheKey(sourceId);
            cacheManager.put(cacheKey, marketDTO, 0); // put each marketDTO object into the cache
            cacheKey = CacheKeyGenerator.getMarketDataCacheKey(sourceId);
            cacheManager.put(cacheKey, marketDTO, 0); // put each marketDTO object into the cache
            supportedSourceList.add(marketDTO);
        }

        this.cacheManager.put(CACHE_KEY_EXCHANGE_DES, exchangeDes, 0);
        this.cacheManager.put(CACHE_KEY_DISPLAY_EXG_TO_EXG, displayExgToExchange, 0);
        this.cacheManager.put(CACHE_KEY_EXG_TO_DISPLAY_EXG, exchangeToDisplayExg, 0);
        this.cacheManager.put(CACHE_KEY_VIRTUAL_EXGS, virtualExchanges, 0);
        this.cacheManager.put(CACHE_KEY_EXG_DECIMAL, exchangeDecimalPlaces, 0);
        generateCountryExchangeList(supportedSourceList);

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    /**
     * private method to generate country exchange list for give supported exchanges list
     *
     * @param supportedMarketList supported exchanges
     */
    private void generateCountryExchangeList(List<MarketDTO> supportedMarketList) {
        Map<String, String> countryExchangeMap = new HashMap<String, String>(supportedMarketList.size());
        Map<String, String> countryExchangeMapNoVirtual = new HashMap<String, String>(supportedMarketList.size());
        Map<String, String> exchangeCountryMap = new HashMap<String, String>(supportedMarketList.size());
        for (MarketDTO marketDTO : supportedMarketList) {
            String existingSources = countryExchangeMap.get(marketDTO.getCountryCode());
            if (existingSources == null) {
                //new
                countryExchangeMap.put(marketDTO.getCountryCode(), marketDTO.getExchangeCode());
            } else {
                countryExchangeMap.put(marketDTO.getCountryCode(), existingSources + IConstants.Delimiter.COMMA + marketDTO.getExchangeCode());
            }

            exchangeCountryMap.put(marketDTO.getExchangeCode(), marketDTO.getCountryCode());

            if (marketDTO.getIsVirtualExchange().equalsIgnoreCase("0")) {
                String existingSourcesNoVirtual = countryExchangeMapNoVirtual.get(marketDTO.getCountryCode());

                if (existingSourcesNoVirtual == null) {
                    //new
                    countryExchangeMapNoVirtual.put(marketDTO.getCountryCode(), marketDTO.getExchangeCode());
                } else {
                    countryExchangeMapNoVirtual.put(marketDTO.getCountryCode(), existingSourcesNoVirtual + IConstants.Delimiter.COMMA + marketDTO.getExchangeCode());
                }
            } else {
                LOG.debug(" Virtual Exchange no need to add to countryExchangeMapNoVirtual");
            }


        }

        if (!countryExchangeMap.isEmpty()) {
            this.cacheManager.put(CACHE_KEY_COUNTRY_EXCHANGES, countryExchangeMap, 0);
        }

        if (!exchangeCountryMap.isEmpty()) {
            this.cacheManager.put(CACHE_KEY_EXCHANGE_COUNTRY, exchangeCountryMap, 0);
        }

        if (!countryExchangeMapNoVirtual.isEmpty()) {
            this.cacheManager.put(CACHE_KEY_COUNTRY_EXCHANGES_NO_VIRTUAL, countryExchangeMapNoVirtual, 0);
        }
    }

    /**
     * initializing all master data here
     *
     * @return status
     */
    public int initCacheUpdate() {
        LOG.info("Start Initializing master data.....");
        try {
            this.cacheManager.put(CACHE_KEY_MASTER_DATA_INIT_STATUS, CacheUpdateStatus.UPDATE_IN_PROGRESS, 0);

            LOG.info("START : Load all application settings.....");
            initAllSettings();
            LOG.info("END : Load all application settings.....");

            LOG.info("START : Load exchange data.....");
            initExchangeData();
            LOG.info("END : Load exchange data.....");

            LOG.info("Init country data.....STARTED");
            initCountry();
            LOG.info("Init country data.....COMPLETED");

            LOG.info("Init City data.....STARTED");
            initCity();
            LOG.info("Init City data.....COMPLETED");

            LOG.info("Init URLGlobal edition meta data.....STARTED");
            initGlobalEditionSettings();
            LOG.info("Init URLGlobal edition meta data.....COMPLETED");

            LOG.info("Init Edition control settings.....STARTED");
            initEditionControlSettings();
            LOG.info("Init Edition control settings.....COMPLETED");

            LOG.info("Init Report Providers.....STARTED");
            getReportProviders();
            LOG.info("Init Report Providers.....COMPLETED");

            LOG.info("Init Report Categories.....STARTED");
            initReportCategories();
            LOG.info("Init Report Categories.....COMPLETED");

            LOG.info("Init Calendar event category.....STARTED");
            initCalendarEventCategory();
            LOG.info("Init Calendar event category.....COMPLETED");

            LOG.info("Init Calendar event title.....STARTED");
            initCalendarEventTitles();
            LOG.info("Init Calendar event title.....COMPLETED");

            LOG.info("Init Cooperate Action types.....STARTED");
            initCorporateActionsTypes();
            LOG.info("Init Cooperate Action types.....COMPLETED");

            LOG.info("Init Sector types.....STARTED");
            initSectorTypes();
            LOG.info("Init Sector types.....COMPLETED");

            LOG.info("Init Combo Box.....STARTED");
            initComboBox();
            LOG.info("Init Combo Box.....COMPLETED");

            LOG.info("Init Time Zones.....STARTED");
            initTimeZones();
            LOG.info("Init Time Zones.....COMPLETED");

            LOG.info("Init Currency Rates.....STARTED");
            initAndGetCurrencyRates();
            LOG.info("Init Currency Rates.....COMPLETED");

            LOG.info("Init Screener Categories.....STARTED");
            initScreenerCategories();
            LOG.info("Init Screener Categories.....COMPLETED");

            LOG.info("Init URL meta data.....STARTED");
            initUrls();
            LOG.info("Init URL meta data.....COMPLETED");

            LOG.info("Init exchange meta data.....STARTED");
            initExchangeMetaData();
            LOG.info("Init exchange meta data.....COMPLETED");

            LOG.info("Init stock meta data.....STARTED");
            initStockMetaData();
            LOG.info("Init stock meta data.....COMPLETED");

            LOG.info("Init Top News Edition master data.....STARTED");
            initAndGetTopNewsEdition();
            LOG.info("Init Top News Edition master data.....COMPLETED");

            LOG.info("Init session data.....STARTED");
            initSessionData();
            initAllSessionCache();
            LOG.info("Init session data.....COMPLETED");

            LOG.info("Init Country indicator types.....STARTED");
            initAndGetAllCountryIndicatorTypes();
            LOG.info("Init Country indicator types.....COMPLETED");

            LOG.info("Init Country indicator group types.....STARTED");
            initAndGetAllCountryIndicatorGroupTypes();
            LOG.info("Init Country indicator group types.....COMPLETED");

            LOG.info("Init Coupon day count types.....STARTED");
            initAndGetCouponDayCountTypes();
            LOG.info("Init Coupon day count types.....COMPLETED");

            LOG.info("Init Classification codes.....STARTED");
            initClassificationSerials();
            LOG.info("Init Classification codes.....COMPLETED");

            LOG.info("Init Product Classification ..... STARTED");
            initProductClassification();
            LOG.info("Init Product Classification ..... COMPLETED");

            LOG.info("Init Product Classification ..... STARTED");
            initTickerCurrency();
            LOG.info("Init Product Classification ..... COMPLETED");

        } catch (Exception e) {
            LOG.error("Cache Init failed :" + e.getCause(), e);
            throw new CacheInitFailedException(e.getMessage(), e);
        } finally {
            LOG.info("Completed Initializing master data.....");
            this.cacheManager.put(CACHE_KEY_MASTER_DATA_INIT_STATUS, CacheUpdateStatus.UPDATE_SUCCEEDED, 0);
        }

        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    public void forceUpdateCache() {
        LOG.info(" Forcefully updating cache..");
        initCacheUpdate();
    }

    /**
     * use this if any case you need to forcefully update the cache
     *
     * @return status
     */
    public int updateCache() {
        LOG.info("Updating cache");
        return CacheUpdateStatus.UPDATE_SUCCEEDED;
    }

    public Object getEventCategoryList() {
        return this.cacheManager.get(CACHE_KEY_CALENDAR_EVENT_CATEGORIES);
    }

    /**
     *
     */
    private void initCalendarEventCategory() {

        List<Map<String, String>> eventCategoryData;
        String cacheKey = CacheKeyConstant.CACHE_KEY_CALENDAR_EVENT_CATEGORIES;

        Map<String, String> getCalEventRequest = getCalEventRequest(IConstants.FundamentalDataTypes.CALMG.toString());
        eventCategoryData = getSocketResponse(this.requestGenerator.generateRequest(getCalEventRequest, IConstants.
                RequestDataType.CALENDAR_EVENTS_META_DATA));

        if (eventCategoryData != null && !eventCategoryData.isEmpty()) {
            this.cacheManager.put(cacheKey, eventCategoryData, 0);
        }
    }

    /**
     *
     */
    public void initCalendarEventTitles() {

        List<Map<String, String>> calendarEventTitleList;
        Map<String, List<Map<String, String>>> calEventsTitleMap = null;
        List<Map<String, String>> tempTitleList;
        String mainCategoryId;

        Map<String, String> getCalEventTitlesRequest = getCalEventRequest(IConstants.FundamentalDataTypes.CALET.toString());
        calendarEventTitleList = getSocketResponse(this.requestGenerator.generateRequest(getCalEventTitlesRequest, IConstants.
                RequestDataType.CALENDAR_EVENTS_META_DATA));

        if (calendarEventTitleList != null && !calendarEventTitleList.isEmpty()) {
            calEventsTitleMap = new HashMap<String, List<Map<String, String>>>();
            for (Map<String, String> title : calendarEventTitleList) {
                mainCategoryId = title.get(IConstants.CustomDataField.MAIN_GROUP_ID);
                if (calEventsTitleMap.get(mainCategoryId) != null) {
                    calEventsTitleMap.get(mainCategoryId).add(title);
                } else {
                    tempTitleList = new ArrayList<Map<String, String>>();
                    tempTitleList.add(title);
                    calEventsTitleMap.put(mainCategoryId, tempTitleList);
                }
            }
        }

        this.cacheManager.put(CACHE_KEY_CAL_EVENTS_TITLE, calendarEventTitleList, 0);
        this.cacheManager.put(CACHE_KEY_CAL_EVENTS_GROUP_TITLE, calEventsTitleMap, 0);
    }

    private Map<String, String> getCalEventRequest(String scdt) {
        Map<String, String> eventCategoryRequest = new HashMap<String, String>();
        eventCategoryRequest.put(IConstants.MIXDataField.SID, "sid");
        eventCategoryRequest.put(IConstants.MIXDataField.UID, "123");
        eventCategoryRequest.put(IConstants.MIXDataField.RT, "30");
        eventCategoryRequest.put(IConstants.MIXDataField.L, "EN");
        eventCategoryRequest.put(IConstants.MIXDataField.UNC, "0");
        eventCategoryRequest.put(IConstants.MIXDataField.SCDT, scdt);
        eventCategoryRequest.put(IConstants.MIXDataField.SF, "EVENT_START_DATE");
        eventCategoryRequest.put(IConstants.MIXDataField.H, "1");
        eventCategoryRequest.put(IConstants.MIXDataField.SO, "DESC");
        eventCategoryRequest.put(IConstants.MIXDataField.FC, "1");

        return eventCategoryRequest;

    }

    private List<Map<String, String>> getSocketResponse(String request) {
        List<Map<String, String>> calendarEventData = null;
        try {
            ResponseObj response = (ResponseObj) socketManager.getData(request);
            calendarEventData = processResponse(response);
        } catch (SocketAccessException e) {
            //
        }

        return calendarEventData;
    }

    @SuppressWarnings("uncheked")
    private List<Map<String, String>> processResponse(ResponseObj response) {
        String head;
        List<Map<String, String>> dataList = null;
        String type;

        if (response != null && response.getHED() != null) {
            if (((Map) response.getHED().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).containsKey("CALMG")) {
                head = (String) ((Map) response.getHED().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).get(IConstants.FundamentalDataTypes.CALMG.toString());
                type = IConstants.FundamentalDataTypes.CALMG.toString();
            } else {
                head = (String) ((Map) response.getHED().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).get(IConstants.FundamentalDataTypes.CALET.toString());
                type = IConstants.FundamentalDataTypes.CALET.toString();
            }

            if (head != null) {
                String[] headers = head.split("\\|");
                List<String> data = (List) ((Map) response.getDAT().get(IConstants.ResponseTypes.FUNDAMENTAL_DATA)).get(type);
                Map<String, String> dataMap;
                dataList = new ArrayList<Map<String, String>>();

                for (String d : data) {
                    List<String> dataItems = new ArrayList<String>(Arrays.asList(d.split("\\|")));

                    while (dataItems.size() < headers.length) {
                        dataItems.add("");
                    }

                    dataMap = new HashMap<String, String>(3);

                    for (int i = 0; i < headers.length; i++) {
                        dataMap.put(headers[i], dataItems.get(i));

                    }

                    dataList.add(dataMap);

                }
            }
        }

        return dataList;
    }

    /**
     * Method to get All fund classifications
     *
     * @return List of FundClass objects
     */
    @SuppressWarnings("uncheked")
    public Map<String, FundClass> getAllFundClassifications() {
        Map<String, FundClass> fundClassList = null;
        Object cacheObj = null;
        FundClass fundClassObj = null;
        cacheObj = cacheManager.get(CACHE_KEY_FUND_CLASS_DATA);
        if (cacheObj == null) {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            fundClassList = new HashMap<String, FundClass>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_FUND_CLASS);
            for (Object fc : objectList) {
                fundClassObj = (FundClass) fc;
                fundClassList.put(fundClassObj.getFundClass().toString(), fundClassObj);
            }
            if (!fundClassList.isEmpty()) {
                cacheManager.put(CACHE_KEY_FUND_CLASS_DATA, fundClassList, 0, 0);
            }
        } else {
            fundClassList = (Map<String, FundClass>) cacheObj;
        }

        return fundClassList;
    }

    /**
     * Method to sort All fund classifications by short description
     *
     * @param language lang
     * @return FundClass objects Map<String, FundClass>
     */
    @SuppressWarnings("uncheked")
    public Map<String, FundClass> getSortedAllFundClassifications(String language) {
        Map<String, FundClass> fundClassMap = getAllFundClassifications();
        SortMapFundClassComparator comparator = new SortMapFundClassComparator(language);

        return CommonUtils.sortMapByValues(fundClassMap, comparator);
    }

    /**
     * Method to All Sukuk types
     *
     * @return SukukTypes Map
     */
    @SuppressWarnings("uncheked")
    public Map<String, SukukTypes> getAllSukukTypes() {
        Map<String, SukukTypes> sukukTypeList;
        Object cacheObj;
        SukukTypes sukukTypeObj;
        cacheObj = cacheManager.get(CACHE_KEY_PREFIX_SUKUK_TYPES);
        if (cacheObj == null) {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            sukukTypeList = new HashMap<String, SukukTypes>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_SUKUK_TYPES);
            for (Object fc : objectList) {
                sukukTypeObj = (SukukTypes) fc;
                sukukTypeList.put(sukukTypeObj.getTypeId().toString(), sukukTypeObj);
            }

            if (!sukukTypeList.isEmpty()) {
                cacheManager.put(CACHE_KEY_PREFIX_SUKUK_TYPES, sukukTypeList, 0, 0);
            }
        } else {
            sukukTypeList = (Map<String, SukukTypes>) cacheObj;
        }

        return sukukTypeList;
    }

    /**
     * Method to get susuk types short descriptions
     *
     * @return map of bondType shortDescriptions
     */
    public Map<String, Map<String,String>> getSukukTypeShortDescriptions(){
        Map<String, Map<String, String>> sukukTypeDescriptions;
        Map<String, SukukTypes> sukukTypesMap = getAllSukukTypes();
        if(sukukTypesMap != null) {
            sukukTypeDescriptions = new HashMap<String, Map<String, String>>(sukukTypesMap.size());
            for (Map.Entry<String, SukukTypes> entry : sukukTypesMap.entrySet()) {
                String key = entry.getKey();
                SukukTypes value = entry.getValue();
                sukukTypeDescriptions.put(key, value.getShortDescription());
            }
        }else{
            sukukTypeDescriptions  = Collections.emptyMap();
        }
        return sukukTypeDescriptions;
    }

    /**
     * Method to get All Bond Types
     *
     * @return bond types
     */
    @SuppressWarnings("uncheked")
    public Map<String, BondTypes> getAllBondTypes() {
        Map<String, BondTypes> bondTypeList;
        Object cacheObj;
        BondTypes bondTypeObj;
        cacheObj = cacheManager.get(CACHE_KEY_PREFIX_BOND_TYPES);
        if (cacheObj == null) {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            bondTypeList = new HashMap<String, BondTypes>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_BOND_TYPES);
            for (Object fc : objectList) {
                bondTypeObj = (BondTypes) fc;
                bondTypeList.put(bondTypeObj.getTypeId().toString(), bondTypeObj);
            }

            if (!bondTypeList.isEmpty()) {
                cacheManager.put(CACHE_KEY_PREFIX_BOND_TYPES, bondTypeList, 0, 0);
            }
        } else {
            bondTypeList = (Map<String, BondTypes>) cacheObj;
        }

        return bondTypeList;
    }

    /**
     *Method to get Bond types short descriptions
     *
     * @return map of susukType shortDescriptions
     */
    public Map<String, Map<String, String>> getBondTypesShortDescription() {
        Map<String, Map<String, String>> bondTypeDescriptions;
        Map<String, BondTypes> bondTypeList=getAllBondTypes();
        if(bondTypeList!=null){
            bondTypeDescriptions=new HashMap<String, Map<String, String>>(bondTypeList.size());
            for (Map.Entry<String, BondTypes> entry : bondTypeList.entrySet()) {
                String key = entry.getKey();
                BondTypes value = entry.getValue();
                bondTypeDescriptions.put(key, value.getShortDescription());
            }
        }else{
            bondTypeDescriptions=Collections.emptyMap();
        }
        return bondTypeDescriptions;
    }

    /**
     * Method to get All Ticker Embedded Option Types
     *
     * @return embedded options
     */
    @SuppressWarnings("uncheked")
    public Map<String, EmbeddedOptionType> getTickerEmbeddedOptionTypes() {
        Map<String, EmbeddedOptionType> optionTypeMap;
        Object cacheObj;
        EmbeddedOptionType optionType;
        cacheObj = cacheManager.get(CACHE_KEY_PREFIX_EMBEDDED_OPTION_TYPES);
        if (cacheObj == null) {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            optionTypeMap = new HashMap<String, EmbeddedOptionType>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_EMBEDDED_OPTION_TYPES);
            for (Object fc : objectList) {
                optionType = (EmbeddedOptionType) fc;
                optionTypeMap.put(optionType.getOptionId().toString(), optionType);
            }

            if (!optionTypeMap.isEmpty()) {
                cacheManager.put(CACHE_KEY_PREFIX_EMBEDDED_OPTION_TYPES, optionTypeMap, 0, 0);
            }
        } else {
            optionTypeMap = (Map<String, EmbeddedOptionType>) cacheObj;
        }

        return optionTypeMap;
    }

    /**
     * public method to get instrumnet types object.
     * First time its load from database and cache it.
     *
     * @return instrument types
     */
    @SuppressWarnings("uncheked")
    @Deprecated
    public Map<String, InstrumentTypeDTO> getAllInstrumentTypes() {
        Map<String, InstrumentTypeDTO> instrumentTypeDTOMap = null;
        Object cacheObj = cacheManager.get(CACHE_KEY_INSTRUMENT_TYPES);
        if (cacheObj == null) {
            InstrumentTypeDTO instrumentTypeDTO = null;
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_INSTRUMENT_TYPES);
            if (!objectList.isEmpty()) {
                instrumentTypeDTOMap = new HashMap<String, InstrumentTypeDTO>(objectList.size());
                for (Object o : objectList) {
                    instrumentTypeDTO = (InstrumentTypeDTO) o;
                    instrumentTypeDTOMap.put(Integer.toString(instrumentTypeDTO.getInstrumentTypeId()), instrumentTypeDTO);
                }
                cacheManager.put(CACHE_KEY_INSTRUMENT_TYPES, instrumentTypeDTOMap, 0, 0);
            }
        } else {
            instrumentTypeDTOMap = (Map<String, InstrumentTypeDTO>) cacheObj;
        }

        return instrumentTypeDTOMap;
    }

    /**
     * public method to get all assest types with corresponding instrument types .
     *
     * @return asset types
     */
    @SuppressWarnings("uncheked")
    @Deprecated
    public Map<String, String> getAllAssetTypes() {
        Map<String, String> instrumentTypesForAssetClass = null;
        Map<String, List<String>> assetTypes = null;
        Object cacheObj = cacheManager.get(CACHE_KEY_ASSET_CLASSES);

        if (cacheObj == null) {
            assetTypes = new HashMap<String, List<String>>(1);
            Map<String, InstrumentTypeDTO> instrumentTypeDTOMap = getAllInstrumentTypes();

            if (instrumentTypeDTOMap != null && !instrumentTypeDTOMap.isEmpty()) {

                InstrumentTypeDTO instrumentTypeDTO;
                List<String> instrumentTypesList;

                for (String key : instrumentTypeDTOMap.keySet()) {
                    instrumentTypeDTO = instrumentTypeDTOMap.get(key);
                    if (assetTypes.get(instrumentTypeDTO.getAssetClassId().toString()) != null) {
                        instrumentTypesList = assetTypes.get(instrumentTypeDTO.getAssetClassId().toString());
                    } else {
                        instrumentTypesList = new ArrayList<String>(1);
                        assetTypes.put(instrumentTypeDTO.getAssetClassId().toString(), instrumentTypesList);
                    }
                    instrumentTypesList.add(instrumentTypeDTO.getInstrumentTypeId().toString());
                }
                instrumentTypesForAssetClass = new HashMap<String, String>(assetTypes.size());

                for (String assetCls : assetTypes.keySet()) {
                    instrumentTypesForAssetClass.put(assetCls, StringUtils.join(assetTypes.get(assetCls), Character.toString(IConstants.Delimiter.COMMA)));
                }

                cacheManager.put(CACHE_KEY_ASSET_CLASSES, instrumentTypesForAssetClass, 0, 0);
            }
        } else {
            instrumentTypesForAssetClass = (Map<String, String>) cacheObj;
        }

        return instrumentTypesForAssetClass;
    }

    /**
     * Returns a comma separated list of instrument types for given asset class
     *
     * @param assetClassId asset class
     * @return instrument types
     */
    @Deprecated
    public String getInstrumentTypesForAssetClass(Integer assetClassId) {
        String instrumentTypes = null;
        Map<String, String> allInstrumentTypes = getAllAssetTypes();
        if (allInstrumentTypes != null) {
            instrumentTypes = allInstrumentTypes.get(assetClassId.toString());
        }
        return instrumentTypes;
    }

    @SuppressWarnings("uncheked")
    public Map<String, List<CountryIndicatorDTO>> getCountryIndicators() {
        Map<String, List<CountryIndicatorDTO>> instrumentTypeDTOMap = null;
        List<CountryIndicatorDTO> countryIndicatorDTOs;
        Object cacheObj = cacheManager.get(CACHE_KEY_COUNTRY_INDICATORS);
        if (cacheObj == null) {
            CountryIndicatorDTO countryIndicatorDTO = null;
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_COUNTRY_INDICATORS);
            if (!objectList.isEmpty()) {
                instrumentTypeDTOMap = new HashMap<String, List<CountryIndicatorDTO>>(objectList.size());
                for (Object o : objectList) {
                    countryIndicatorDTO = (CountryIndicatorDTO) o;
                    countryIndicatorDTOs = instrumentTypeDTOMap.get(countryIndicatorDTO.getInstrumentTypeId());
                    if (countryIndicatorDTOs == null) {
                        countryIndicatorDTOs = new ArrayList<CountryIndicatorDTO>();
                    }
                    countryIndicatorDTOs.add(countryIndicatorDTO);
                    instrumentTypeDTOMap.put(countryIndicatorDTO.getInstrumentTypeId(), countryIndicatorDTOs);

                }
                cacheManager.put(CACHE_KEY_COUNTRY_INDICATORS, instrumentTypeDTOMap, 0, 0);
            }
        } else {
            instrumentTypeDTOMap = (Map<String, List<CountryIndicatorDTO>>) cacheObj;
        }

        return instrumentTypeDTOMap;
    }

    /**
     * Utility method to provide  exchanges data for give source id
     *
     * @param exchange unique id for exchange
     * @return Exchange data
     */
    public MarketDTO getExchangeData(String exchange) {
        MarketDTO exchangeData;
        String cacheKey;

        //No need to validate. caller has to send valid exchange.
        //null is return if client send wrong/unsupported exchange
        if (exchange == null) {
            return null;
        }

        cacheKey = CacheKeyGenerator.getMarketDataCacheKey(exchange);
        exchangeData = (MarketDTO) this.cacheManager.get(cacheKey);

        if (exchangeData == null) {
            String getExchangeDataQuery = MasterDataDbHelper.QUERY_MARKET + DBConstants.WHERE + DBConstants.DatabaseColumns.SOURCE_ID +
                    DBConstants.CommonDatabaseParams.EQUAL_QUETION_MARK;

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(getExchangeDataQuery);
            requestDBDTO.setParams(exchange.toUpperCase());

            MetaDataDAO masterDataDAO = this.imdbDaoFactory.getMetaDataDAO();
            exchangeData = (MarketDTO) masterDataDAO.get(IConstants.ControlPathTypes.MARKET_DATA, requestDBDTO); //Search Tickers

            if (exchangeData != null) {
                this.cacheManager.put(cacheKey, exchangeData);
            }
        }

        return exchangeData;
    }

    /**
     * Initialize timezone data
     *
     * @return
     */
    private int initTimeZones() {
        int timeZoneUpdateStatus;
        MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
        List<Object> timeZones = masterDataDAO.getAll(MASTER_DATA_TIME_ZONES);
        Map<String, TimeZone> timeZoneMap = new LinkedHashMap<String, TimeZone>();

        if (timeZones != null && !timeZones.isEmpty()) {
            for (Object timeZone : timeZones) {
                TimeZone timeZoneDto = (TimeZone) timeZone;
                timeZoneMap.put(Integer.toString(timeZoneDto.getTimeZoneId()), timeZoneDto);
            }
            this.cacheManager.put(CACHE_KEY_TIME_ZONES, timeZoneMap, TIME_TO_LIVE_TWENTY_FOUR_HOURS);
            timeZoneUpdateStatus = CacheUpdateStatus.UPDATE_SUCCEEDED;
        } else {
            timeZoneUpdateStatus = CacheUpdateStatus.UPDATE_FAILED;
        }
        return timeZoneUpdateStatus;
    }

    /**
     * @return Map of exchange des
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map> getExchangeDes() {
        Map<String, Map> map = (Map) this.cacheManager.get(CACHE_KEY_EXCHANGE_DES);

        if (map == null) {
            initExchangeData();
            map = (Map) this.cacheManager.get(CACHE_KEY_EXCHANGE_DES);
        }

        return map;
    }

    /**
     * @return Exchange Code
     */
    @SuppressWarnings("unchecked")
    public String getExchangesByDisplayExg(String displayExg) {
        if (displayExg == null || displayExg.trim().isEmpty()) {
            return null;
        }

        String exchangeCode = displayExg;
        Map<String, String> displayExgMap = (Map) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_DISPLAY_EXG_TO_EXG);

        if (displayExgMap != null) {
            exchangeCode = displayExgMap.get(displayExg.toUpperCase());
        }

        return exchangeCode;
    }

    /**
     * Get display exchange list from cache
     *
     * @return exg to disp exg map
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getDisplayExgList() {
        return (Map) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_EXG_TO_DISPLAY_EXG);
    }

    /**
     * Get exchange decimal places map from cache
     *
     * @return decimal points per exchange
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getExchangeDecimal() {
        return (Map) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_EXG_DECIMAL);
    }

    /**
     * Get virtual exchange list from cache
     *
     * @return virtual exchanges
     */
    @SuppressWarnings("unchecked")
    public List<String> getVirtualExgList() {
        return (List) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_VIRTUAL_EXGS);
    }

    /**
     * method to get country exchange map
     *
     * @return country exchange map
     */
    @SuppressWarnings("uncheked")
    public Map<String, String> getCountryExchangeMap() {
        Map<String, String> countryExchangeMap = (Map) this.cacheManager.get(CACHE_KEY_COUNTRY_EXCHANGES);

        if (countryExchangeMap == null) {
            initExchangeData();
            countryExchangeMap = (Map) this.cacheManager.get(CACHE_KEY_COUNTRY_EXCHANGES);
        }
        return countryExchangeMap;
    }

    /**
     * Method to get the exchange country map
     *
     * @return exchange country map
     */
    @SuppressWarnings("uncheked")
    public Map<String, String> getExchangeCountryMap() {
        Map<String, String> countryExchangeMap = (Map) this.cacheManager.get(CACHE_KEY_EXCHANGE_COUNTRY);

        if (countryExchangeMap == null) {
            initExchangeData();
            countryExchangeMap = (Map) this.cacheManager.get(CACHE_KEY_EXCHANGE_COUNTRY);
        }
        return countryExchangeMap;
    }

    /**
     * method to get country exchange map
     *
     * @return country exchange map without virtual excahnges
     */
    @SuppressWarnings("uncheked")
    public Map<String, String> getCountryExchangeMapNoVirtual() {
        Map<String, String> countryExchangeMap = (Map) this.cacheManager.get(CACHE_KEY_COUNTRY_EXCHANGES_NO_VIRTUAL);

        if (countryExchangeMap == null) {
            initExchangeData();
            countryExchangeMap = (Map) this.cacheManager.get(CACHE_KEY_COUNTRY_EXCHANGES_NO_VIRTUAL);
        }
        return countryExchangeMap;
    }

    /**
     * returns exchanges for given list of countries
     *
     * @param countries list of countries
     * @return Comma separated string of exchanges
     */
    @SuppressWarnings("unchecked")
    public String getExchangeListByCountries(List<String> countries, boolean sortData) {
        if (countries == null || countries.isEmpty()) {
            return null;
        }

        List<String> exchangeDataList = new ArrayList<String>();
        String exchanges;
        Map<String, String> countryExchangeMap = getCountryExchangeMap();
        if (countryExchangeMap != null) {
            for (String country : countries) {
                exchanges = countryExchangeMap.get(country.toUpperCase());

                if (exchanges == null) {
                    continue;
                }
                exchangeDataList.add(exchanges);
            }
        }
        exchanges = StringUtils.join(exchangeDataList, Character.toString(IConstants.Delimiter.COMMA));
        // sort exchanges if requested
        if (sortData) {
            List<String> exgList = Arrays.asList(exchanges.split(Character.toString(IConstants.Delimiter.COMMA)));
            Collections.sort(exgList);
            exchanges = StringUtils.join(exgList, Character.toString(IConstants.Delimiter.COMMA));
        }
        return exchanges;
    }

    /**
     * returns exchanges list for given list of countries
     *
     * @param countries list of countries
     * @return exchanges as String list
     */
    public List<String> getExchangesAsListByCountries(String countries) {
        if (countries == null || countries.isEmpty()) {
            return null;
        }

        List<String> exchangeDataList = new ArrayList<String>();
        String exchanges;
        String[] exchangeList;
        Map<String, String> countryExchangeMap = getCountryExchangeMap();
        if (countryExchangeMap != null) {
            String[] countryList = countries.split(Character.toString(IConstants.Delimiter.COMMA));
            for (String country : countryList) {
                exchanges = countryExchangeMap.get(country.toUpperCase());

                if (exchanges == null || exchanges.isEmpty()) {
                    continue;
                }

                exchangeList = exchanges.split(Character.toString(IConstants.Delimiter.COMMA));

                for (String exg : exchangeList) {
                    if (!exg.isEmpty()) {
                        exchangeDataList.add(exg);
                    }
                }

            }
        }
        return exchangeDataList;
    }

    /**
     * Utility method to provide exchanges for give country list
     *
     * @param countries      list
     * @param includeVirtual include Virtual exchanges or not
     * @return List of Exchange data
     */
    @SuppressWarnings("unchecked")
    public List<MarketDTO> getExchangesDataByCountries(List<String> countries, boolean includeVirtual) {

        if (countries == null || countries.isEmpty()) {
            return null;
        }

        List<MarketDTO> exchangeDataList = new ArrayList<MarketDTO>();
        String cacheKey;
        MarketDTO exchangeData;
        Map<String, String> countryExchangeMap = getCountryExchangeMap();

        if (!includeVirtual) {
            countryExchangeMap = getCountryExchangeMapNoVirtual();
        }

        for (String country : countries) {
            String exchanges = null;
            if (countryExchangeMap != null) {
                exchanges = countryExchangeMap.get(country);
            }
            if (exchanges == null) {
                continue;
            }
            for (String e : exchanges.split(Character.toString(IConstants.Delimiter.COMMA))) {
                cacheKey = CacheKeyGenerator.getMarketDataCacheKey(e);
                exchangeData = (MarketDTO) this.cacheManager.get(cacheKey);
                if (exchangeData != null /*&& exchangeData.getMainIndex() != null*/) {
                    exchangeDataList.add(exchangeData);
                }
            }
        }
        return exchangeDataList;
    }

    public List<MarketDTO> getSortedExchangesDataByCountries(List<String> countries, String language) {

        if (countries == null || countries.isEmpty()) {
            return null;
        }

        List<MarketDTO> exchangeDataList = new ArrayList<MarketDTO>();
        String cacheKey;
        MarketDTO exchangeData;
        Map<String, String> countryExchangeMap = getCountryExchangeMap();

        for (String country : countries) {
            String exchanges = null;
            if (countryExchangeMap != null) {
                exchanges = countryExchangeMap.get(country);
            }
            if (exchanges == null) {
                continue;
            }
            for (String e : exchanges.split(Character.toString(IConstants.Delimiter.COMMA))) {
                cacheKey = CacheKeyGenerator.getMarketDataCacheKey(e);
                exchangeData = (MarketDTO) this.cacheManager.get(cacheKey);
                if (exchangeData != null) {   //&& exchangeData.getMainIndex() != null
                    exchangeDataList.add(exchangeData);
                }
            }
        }

        exchangeDataList = sortExchangeList(exchangeDataList, language);

        return exchangeDataList;
    }

    /**
     * @param exchangeList exchanges
     * @param language     language
     * @return sort by description
     */
    private static List<MarketDTO> sortExchangeList(List<MarketDTO> exchangeList, String language) {
        final String lang = language.toUpperCase();

        Collections.sort(exchangeList, new Comparator<MarketDTO>() {
            public int compare(MarketDTO m1, MarketDTO m2) {
                return ((m1.getLongDescriptions()).get(lang)).compareTo((m2.getLongDescriptions()).get(lang));
            }
        });

        return exchangeList;
    }

    /**
     * Utility method to provide exchanges for give exchange list
     *
     * @param exchange list
     * @return List of Exchange data
     */
    public List<MarketDTO> getExchangesDataByExchanges(List<String> exchange) {

        if (exchange == null || exchange.isEmpty()) {
            return null;
        }

        List<MarketDTO> exchangeDataList = new ArrayList<MarketDTO>(exchange.size());
        String cacheKey;
        MarketDTO exchangeData;

        for (String e : exchange) {
            cacheKey = CacheKeyGenerator.getMarketDataCacheKey(e);
            exchangeData = (MarketDTO) this.cacheManager.get(cacheKey);

            if (exchangeData != null) {   //&& exchangeData.getMainIndex() != null
                exchangeDataList.add(exchangeData);
            }
        }

        return exchangeDataList;
    }

    /**
     * Utility method to provide exchangeDto map for give exchange list
     *
     * @param exchange list
     * @return List of Exchange data
     */
    public Map<String, MarketDTO> getExchangesDataMapByExchanges(List<String> exchange) {

        if (exchange == null || exchange.isEmpty()) {
            return null;
        }

        Map<String, MarketDTO> exchangeDataMap = new HashMap<String, MarketDTO>();
        String cacheKey;
        MarketDTO exchangeData;

        for (String e : exchange) {
            cacheKey = CacheKeyGenerator.getMarketDataCacheKey(e);
            exchangeData = (MarketDTO) this.cacheManager.get(cacheKey);

            if (exchangeData != null && !exchangeDataMap.containsKey(e)) {     /*&& exchangeData.getMainIndex() != null*/
                exchangeDataMap.put(e, exchangeData);
            }
        }

        return exchangeDataMap;
    }

    /**
     * return Map contains region
     *
     * @return Region Country Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, RegionCountry> getRegionCountryMap() {
        Object cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_REGION_COUNTRIES);
        Map<String, RegionCountry> regionCountryMap = null;
        if (cacheData != null) {
            regionCountryMap = (Map<String, RegionCountry>) cacheData;
        }
        return regionCountryMap;
    }

    @SuppressWarnings("unchecked")
    public List<CountryDTO> getCountryList(IConstants.ExchangeDataType exchangeDataType) {
        Object cacheData;

        switch (exchangeDataType) {
            case PRICE_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_PRICE_COUNTRY_LIST);
                break;
            case MACRO_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_MACRO_COUNTRY_LIST);
                break;
            case IPO_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_IOP_COUNTRY_LIST);
                break;
            case OTHER_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_OTHER_COUNTRY_LIST);
                break;
            case FI_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_FI_COUNTRY_LIST);
                break;
            case MF_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_MF_COUNTRY_LIST);
                break;
            case COMPANY_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_COMPANY_COUNTRY_LIST);
                break;
            case EDITION_CONTROL_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_EDITION_CONTROL_COUNTRY_LIST);
                break;
            default:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_OTHER_COUNTRY_LIST);
                break;
        }

        List<CountryDTO> countries = null;

        if (cacheData != null) {
            countries = (List<CountryDTO>) cacheData;
        }
        return countries;
    }

    /**
     * Get coma separated country list form give type
     *
     * @param exchangeDataType type
     * @return coma separated country list
     */
    public String getCountryListString(IConstants.ExchangeDataType exchangeDataType) {

        String cacheKry = getCacheKeyForCountryListString(exchangeDataType);
        String countries = null;
        Object cacheData = this.cacheManager.get(cacheKry);

        if (cacheData == null) {
            StringBuilder countryStr = new StringBuilder();

            List<CountryDTO> countryList = getCountryList(exchangeDataType);


            for (CountryDTO country : countryList) {
                //add country with comma
                countryStr.append(country.getCountryCode());  //add country code
                countryStr.append(CharUtils.toString(IConstants.Delimiter.COMMA));  //add comma
            }

            countries = CommonUtils.removeLast(countryStr.toString());
            this.cacheManager.put(cacheKry, countries);

        } else {
            countries = (String) cacheData;
        }

        return countries;
    }

    private String getCacheKeyForCountryListString(IConstants.ExchangeDataType exchangeDataType) {

        String cacheKey;

        switch (exchangeDataType) {
            case PRICE_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_PRICE_COUNTRY_LIST_STRING;
                break;
            case MACRO_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_MACRO_COUNTRY_LIST_STRING;
                break;
            case IPO_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_IOP_COUNTRY_LIST_STRING;
                break;
            case OTHER_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_OTHER_COUNTRY_LIST_STRING;
                break;
            case FI_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_FI_COUNTRY_LIST_STRING;
                break;
            case MF_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_MF_COUNTRY_LIST_STRING;
                break;
            case COMPANY_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_COMPANY_COUNTRY_LIST_STRING;
                break;
            case EDITION_CONTROL_DATA:
                cacheKey = CacheKeyConstant.CACHE_KEY_EDITION_CONTROL_COUNTRY_LIST_STRING;
                break;
            default:
                cacheKey = CacheKeyConstant.CACHE_KEY_OTHER_COUNTRY_LIST_STRING;
                break;
        }

        return cacheKey;

    }

    @SuppressWarnings("uncheked")
    public Map<String, RegionCountry> getRegionCountryMap(IConstants.ExchangeDataType exchangeDataType) {
        Object cacheData = null;

        switch (exchangeDataType) {
            case PRICE_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_PRICE_REGION_COUNTRY_MAP);
                break;
            case MACRO_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_MACRO_REGION_COUNTRY_MAP);
                break;
            case IPO_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_IOP_REGION_COUNTRY_MAP);
                break;
            case OTHER_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_OTHER_REGION_COUNTRY_MAP);
                break;
            case COMPANY_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_COMPANY_REGION_COUNTRY_MAP);
                break;
            case EDITION_CONTROL_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_EDITION_CONTROL_REGION_COUNTRY_MAP);
                break;
            case FI_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_FI_REGION_COUNTRY_MAP);
                break;
            case MF_DATA:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_MF_REGION_COUNTRY_MAP);
                break;
            default:
                cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_REGION_COUNTRIES);
                break;

        }
        Map<String, RegionCountry> regionCountryMap = null;

        if (cacheData != null) {
            regionCountryMap = (Map<String, RegionCountry>) cacheData;
        }
        return regionCountryMap;
    }

    /**
     * get city object list for specific country
     *
     * @param countryCode countryCode
     * @return country city list
     */
    @SuppressWarnings("uncheked")
    public List<CityDTO> getCountryCityList(String countryCode) {
        Map<String, List> countryCityMap = (Map<String, List>) this.cacheManager.get(CACHE_KEY_COUNTRY_CITY_LIST);
        List<CityDTO> cityList = null;

        if (countryCityMap != null && countryCityMap.containsKey(countryCode)) {
            cityList = (List<CityDTO>) countryCityMap.get(countryCode);
        }

        return cityList;
    }

    /**
     * get city desc map for all countries
     *
     * @return (country code), (city id), (city description)
     */
    @SuppressWarnings("uncheked")
    public Map<String, List<CityDTO>> getAllCountryCityList() {
        return (Map<String, List<CityDTO>>) this.cacheManager.get(CACHE_KEY_COUNTRY_CITY_LIST);
    }

    /**
     * return Map contains region
     *
     * @return Country Region Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getCountryRegionMap() {
        Object cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_COUNTRY_REGIONS);
        Map<String, String> countryRegionsMap = null;
        if (cacheData != null) {
            countryRegionsMap = (Map<String, String>) cacheData;
        }
        return countryRegionsMap;
    }

    /**
     * get all currency rates
     * Get from database if currency rates are not available in cache
     *
     * @return map of currency rates, key = pair , value = conversion rate
     */
    @SuppressWarnings("unchecked")
    public Map<String, Float> getAllCurrencyRates() {
        Map<String, Float> currencyRates = (Map) cacheManager.get(CACHE_KEY_CURRENCY_RATES);

        if (currencyRates == null || currencyRates.isEmpty()) {
            //if currency map is empty try to load it again
            LOG.error(" Currency map is not available in cache. Report/contact dev team if you see this more that one time");
            LOG.error(" Loading from database");
            currencyRates = initAndGetCurrencyRates();
        } else {
            currencyRates = (Map) cacheManager.get(CACHE_KEY_CURRENCY_RATES);
        }

        return currencyRates;
    }

    /**
     * Get currency rate for given pair
     *
     * @param pair currency pair
     * @return rate pair
     */
    public float getCurrencyRate(String pair) {
        Map<String, Float> currencyRates = getAllCurrencyRates();

        float rate = -1;

        if (currencyRates != null) {
            rate = currencyRates.get(pair);
        }

        return rate;
    }

    /**
     * return List Contains global edition settings
     *
     * @return Global Edition Settings List
     */
    @SuppressWarnings("unchecked")
    public List<GlobalEditionSettings> getGlobalEditionSettings() {
        Object cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_GLOBAL_EDITION_SETTINGS);
        List<GlobalEditionSettings> globalEditionSettingsList = null;
        if (cacheData != null) {
            globalEditionSettingsList = (List<GlobalEditionSettings>) cacheData;
        }
        return globalEditionSettingsList;
    }

    /**
     * return List Contains edition control regions
     *
     * @return edition control settings
     */
    @SuppressWarnings("unchecked")
    public List<EditionControlSettingsDTO> getEditionControlRegions() {
        Object cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_EDITION_CONTROL_REGIONS);
        List<EditionControlSettingsDTO> editionControlSettingsDTOs = null;
        if (cacheData != null) {
            editionControlSettingsDTOs = (List<EditionControlSettingsDTO>) cacheData;
        }
        return editionControlSettingsDTOs;
    }

    /**
     * return List Contains edition control region group
     *
     * @return edition control settings
     */
    @SuppressWarnings("unchecked")
    public List<EditionControlSettingsDTO> getEditionControlRegionGroup() {
        Object cacheData = this.cacheManager.get(CacheKeyConstant.CACHE_KEY_EDITION_CONTROL_REGION_GROUP);
        List<EditionControlSettingsDTO> editionControlSettingsDTOs = null;
        if (cacheData != null) {
            editionControlSettingsDTOs = (List<EditionControlSettingsDTO>) cacheData;
        }
        return editionControlSettingsDTOs;
    }

    /**
     * Get time zones list from cache.
     *
     * @return timezones
     */
    @SuppressWarnings("unchecked")
    public Map<String, TimeZone> getTimeZones() {
        Map<String, TimeZone> timeZoneMap = (Map) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_TIME_ZONES);
        if (timeZoneMap == null) {
            initTimeZones();
            timeZoneMap = (Map) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_TIME_ZONES);
        }
        return timeZoneMap;
    }

    /**
     * Method to Return sorted timezone list
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TimeZone> getSortedTimeZones() {

        List<TimeZone> plusList = new ArrayList<TimeZone>();
        List<TimeZone> minusList = new ArrayList<TimeZone>();
        List<TimeZone> noList = new ArrayList<TimeZone>();
        Map<String, TimeZone> timeZones;
        Collection<TimeZone> timeZoneCollection;

        List<TimeZone> sortedList = (List) cacheManager.get(CACHE_KEY_TIME_ZONES_SORTED);
        if (sortedList == null) {
            timeZones = this.getTimeZones();
            if (timeZones != null) {
                timeZoneCollection = timeZones.values();
                List<TimeZone> list = new ArrayList<TimeZone>(timeZoneCollection);
                for (TimeZone timeZone : list) {
                    if (timeZone.getCorrectedTimezoneOffset().startsWith("+")) {
                        plusList.add(timeZone);
                    } else if (timeZone.getCorrectedTimezoneOffset().startsWith("0")) {
                        noList.add(timeZone);
                    } else {
                        minusList.add(timeZone);
                    }
                }
                Collections.sort(plusList, new Comparator<TimeZone>() {
                    public int compare(TimeZone m1, TimeZone m2) {
                        if (m2.getCorrectedTimezoneOffset() != null) {
                            return m1.getCorrectedTimezoneOffset().compareTo(m2.getCorrectedTimezoneOffset());
                        } else {
                            return -1;
                        }
                    }
                });

                Collections.sort(minusList, new Comparator<TimeZone>() {
                    public int compare(TimeZone m1, TimeZone m2) {
                        if (m2.getCorrectedTimezoneOffset() != null) {
                            return (-1) * m1.getCorrectedTimezoneOffset().compareTo(m2.getCorrectedTimezoneOffset());
                        } else {
                            return -1;
                        }
                    }
                });

                sortedList = new ArrayList<TimeZone>(minusList);
                sortedList.addAll(noList);
                sortedList.addAll(plusList);

                if (!sortedList.isEmpty()) {
                    cacheManager.put(CACHE_KEY_TIME_ZONES_SORTED, sortedList, TIME_TO_LIVE_TWENTY_FOUR_HOURS);
                }
            }
        }

        return sortedList;
    }

    public List<CountryDTO> getSortedCountryList(String language) {
        List<CountryDTO> sortedCountryList = (List<CountryDTO>) cacheManager.get(CACHE_KEY_SORTED_COUNTRY_LIST + "~" + language.toUpperCase());
        if (sortedCountryList == null) {
            sortedCountryList = setSortedCountryList(getAllCountriesList(), language);
        }
        return sortedCountryList;
    }

    /**
     * sort method to the country list & cash
     *
     * @param countries countries
     * @param language  lang
     * @return sorted by desc
     */
    @SuppressWarnings("unchecked")
    private List<CountryDTO> setSortedCountryList(List<CountryDTO> countries, String language) {
        final String lang = language.toUpperCase();
        List<CountryDTO> sortedCountryList = null;
        Object countryList = cacheManager.get(CACHE_KEY_SORTED_COUNTRY_LIST + "~" + lang);

        if (countryList != null) {
            sortedCountryList = (List<CountryDTO>) countryList;
        } else {
            List<CountryDTO> cuntryList = (List<CountryDTO>) countries;
            Collections.sort(cuntryList, new Comparator<CountryDTO>() {
                public int compare(CountryDTO m1, CountryDTO m2) {
//                    return ((m1.getDescription()).get(lang)).compareTo((m2.getDescription()).get(lang));

                    if (m1.getCountryLangDTOMap().get(lang) != null && m1.getCountryLangDTOMap().get(lang).getShortName() != null
                            && m2.getCountryLangDTOMap().get(lang) != null && m2.getCountryLangDTOMap().get(lang).getShortName() != null) {
                        return ((m1.getCountryLangDTOMap().get(lang).getShortName()).compareTo((m2.getCountryLangDTOMap().get(lang).getShortName())));
                    } else {
                        return ((m1.getCountryCode())).compareTo((m2.getCountryCode()));
                    }
                }
            });

            this.cacheManager.put(CACHE_KEY_SORTED_COUNTRY_LIST + "~" + lang, cuntryList, 0);
            sortedCountryList = cuntryList;
        }

        return sortedCountryList;
    }

    /**
     * Method for get sorted region country list
     *
     * @param regionCountry region countries
     * @param language      lang
     * @return sorted countries
     */
    @SuppressWarnings("unchecked")
    public List<CountryDTO> sortRegionCountryList(RegionCountry regionCountry, String language) {
        final String lang = language.toUpperCase();
        List<CountryDTO> sortedCountryList = null;

//        Object cashKeyRegion = (Object) regionCountry.getRegion();

        Object countryListRegion = cacheManager.get(CACHE_KEY_SORTED_COUNTRY_LIST_REGION + "~" + lang + "~" + regionCountry.getRegion());

        if (countryListRegion != null) {
            sortedCountryList = (List<CountryDTO>) countryListRegion;
        } else {
            List<CountryDTO> countryList = regionCountry.getCountriesList();
            Collections.sort(countryList, new Comparator<CountryDTO>() {
                public int compare(CountryDTO m1, CountryDTO m2) {
                    if (m1.getCountryLangDTOMap().get(lang) != null && m1.getCountryLangDTOMap().get(lang).getShortName() != null
                            && m2.getCountryLangDTOMap().get(lang) != null && m2.getCountryLangDTOMap().get(lang).getShortName() != null) {
                        return (m1.getCountryLangDTOMap().get(lang).getShortName()).compareTo(m2.getCountryLangDTOMap().get(lang).getShortName());
                    } else {
                        return (m1.getCountryCode().compareTo(m2.getCountryCode()));
                    }
                }
            });

            this.cacheManager.put(CACHE_KEY_SORTED_COUNTRY_LIST_REGION + IConstants.Delimiter.TILDE + lang + IConstants.Delimiter.TILDE + regionCountry.getRegion(), countryList, 0);
            sortedCountryList = countryList;
        }
        return sortedCountryList;
    }

    /**
     * Method for get sorted country list
     *
     * @param regionCountry    RegionCountry
     * @param language         String
     * @param exchangeDataType IConstants.ExchangeDataType
     * @return List<Country>
     */
    @SuppressWarnings("unchecked")
    public List<CountryDTO> sortRegionCountryList(RegionCountry regionCountry, String language, IConstants.ExchangeDataType exchangeDataType) {
        final String lang = language.toUpperCase();
        List<CountryDTO> sortedCountryList = null;

        Object countryListRegion = cacheManager.get(CACHE_KEY_SORTED_COUNTRY_LIST_REGION + "~" + lang + "~" + regionCountry.getRegion() + "~" + exchangeDataType.toString());

        if (countryListRegion != null) {
            sortedCountryList = (List<CountryDTO>) countryListRegion;
        } else {
            List<CountryDTO> countryList = regionCountry.getCountriesList();
            Collections.sort(countryList, new Comparator<CountryDTO>() {
                public int compare(CountryDTO m1, CountryDTO m2) {
                    if (m1.getCountryLangDTOMap().get(lang) != null && m1.getCountryLangDTOMap().get(lang).getShortName() != null
                            && m2.getCountryLangDTOMap().get(lang) != null && m2.getCountryLangDTOMap().get(lang).getShortName() != null) {
                        return (m1.getCountryLangDTOMap().get(lang).getShortName()).compareTo(m2.getCountryLangDTOMap().get(lang).getShortName());
                    } else {
                        return (m1.getCountryCode().compareTo(m2.getCountryCode()));
                    }
                }
            });

            this.cacheManager.put(CACHE_KEY_SORTED_COUNTRY_LIST_REGION + "~" + lang + "~" + regionCountry.getRegion() + "~" + exchangeDataType.toString(), countryList, 0);
            sortedCountryList = countryList;
        }
        return sortedCountryList;
    }

    /**
     * Method to get All Ipo Status
     *
     * @return List of IpoStatus objects
     */
    @SuppressWarnings("uncheked")
    public Map<String, IpoStatus> getAllIPOStatusData() {
        Map<String, IpoStatus> ipoStatusList;
        Object cacheObj;
        IpoStatus ipoStatusObj;

        cacheObj = cacheManager.get(CACHE_KEY_IPO_STATUS_DATA);

        if (cacheObj == null) {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            ipoStatusList = new LinkedHashMap<String, IpoStatus>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_IPO_STATUS);
            for (Object fc : objectList) {
                ipoStatusObj = (IpoStatus) fc;
                ipoStatusList.put(ipoStatusObj.getStatusId().toString(), ipoStatusObj);
            }

            if (!ipoStatusList.isEmpty()) {
                cacheManager.put(CACHE_KEY_IPO_STATUS_DATA, ipoStatusList, 0, 0);
            }
        } else {
            ipoStatusList = (Map<String, IpoStatus>) cacheObj;
        }

        return ipoStatusList;
    }

    /**
     * Method to get All Ipo Subscription Types
     *
     * @return List of IpoSubscriptionType objects
     */
    @SuppressWarnings("uncheked")
    public Map<String, IpoSubscriptionType> getAllIPOSubscriptionTypes() {
        Map<String, IpoSubscriptionType> ipoSubscriptionTypeList = null;
        Object cacheObj = null;
        IpoSubscriptionType ipoSubscriptionTypeObj = null;

        cacheObj = cacheManager.get(CACHE_KEY_IPO_SUBSCRIPTION_TYPE_DATA);

        if (cacheObj == null) {
            MasterDataDAO applicationSettingsDAO = hibernateDaoFactory.getMasterDataDAO();
            ipoSubscriptionTypeList = new HashMap<String, IpoSubscriptionType>();
            List<Object> objectList = applicationSettingsDAO.getAll(MASTER_DATA_IPO_SUBSCRIPTION_TYPE);
            for (Object fc : objectList) {
                ipoSubscriptionTypeObj = (IpoSubscriptionType) fc;
                ipoSubscriptionTypeList.put(ipoSubscriptionTypeObj.getSubscriptionType().toString(), ipoSubscriptionTypeObj);
            }

            if (!ipoSubscriptionTypeList.isEmpty()) {
                cacheManager.put(CACHE_KEY_IPO_SUBSCRIPTION_TYPE_DATA, ipoSubscriptionTypeList, 0, 0);
            }
        } else {
            ipoSubscriptionTypeList = (Map<String, IpoSubscriptionType>) cacheObj;
        }

        return ipoSubscriptionTypeList;
    }

    /**
     * Get merge and acquisitions status sorted map
     */
    @SuppressWarnings("uncheked")
    public Map<String, MAStatus> getMAStatus(final String language) {
        final String lang = language.toUpperCase();
        Object cacheObj;
        MAStatus maStatusObj;
        Map<String, MAStatus> maStatus = new LinkedHashMap<String, MAStatus>();
        String cacheKey = CACHE_KEY_MA_STATUS_DESCRIPTIONS + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;
        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_MA_STATUS);

            Collections.sort(objectList, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    MAStatus m1, m2;
                    m1 = (MAStatus) o1;
                    m2 = (MAStatus) o2;
                    return m1.getDescription().get(lang).compareTo(m2.getDescription().get(lang));
                }
            });

            for (Object status : objectList) {
                maStatusObj = (MAStatus) status;
                maStatus.put(maStatusObj.getStatusId(), maStatusObj);
            }

            this.cacheManager.put(cacheKey, maStatus);
        } else {
            maStatus = (Map<String, MAStatus>) cacheObj;
        }
        return maStatus;
    }

    /**
     * Get merge and acquisition deal types
     */
    @SuppressWarnings("uncheked")
    public Map<String, MADealTypes> getMADealTypes(String language) {
        final String lang = language.toUpperCase();
        Map<String, MADealTypes> maDealTypes = new LinkedHashMap<String, MADealTypes>();
        Object cacheObj;
        MADealTypes maDealTypesObj;
        String cacheKey = CACHE_KEY_MA_DEAL_TYPE_DESCRIPTIONS + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_MA_DEAL_TYPES);

            Collections.sort(objectList, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    MADealTypes m1, m2;
                    m1 = (MADealTypes) o1;
                    m2 = (MADealTypes) o2;
                    return m1.getDescription().get(lang).compareTo(m2.getDescription().get(lang));
                }
            });

            for (Object deal : objectList) {
                maDealTypesObj = (MADealTypes) deal;
                maDealTypes.put(maDealTypesObj.getDealId(), maDealTypesObj);
            }

            this.cacheManager.put(cacheKey, maDealTypes);

        } else {
            maDealTypes = (Map<String, MADealTypes>) cacheObj;
        }

        return maDealTypes;
    }

    /**
     * getReportTypeDesc map
     *
     * @param language String
     * @return String, ReportType map
     */
    @SuppressWarnings("uncheked")
    public Map<String, ReportType> getReportTypeDesc(String language) {

        final String lang = language.toUpperCase();
        Object cacheObj;
        ReportType reportTypesObj;
        Map<String, ReportType> reportTypes = new LinkedHashMap<String, ReportType>();
        String cacheKey = CACHE_KEY_REPORT_TYPES + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_REPORT_TYPES);

            for (Object obj : objectList) {
                reportTypesObj = (ReportType) obj;
                reportTypes.put(reportTypesObj.getReportTypeId(), reportTypesObj);
            }
            this.cacheManager.put(cacheKey, reportTypes);
        } else {
            reportTypes = (Map<String, ReportType>) cacheObj;
        }
        return reportTypes;
    }

    /**
     * get file publishers map
     *
     * @param language String
     * @return Integer, FilePublisher map
     */
    @SuppressWarnings("uncheked")
    public Map<Integer, FilePublisher> getFilePublishers(String language) {

        final String lang = language.toUpperCase();
        Object cacheObj;
        FilePublisher filePublisherObj;
        Map<Integer, FilePublisher> filePublishers = new LinkedHashMap<Integer, FilePublisher>();
        String cacheKey = CACHE_KEY_FILE_PUBLISHERS + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_FILE_PUBLISHERS);

            for (Object obj : objectList) {
                filePublisherObj = (FilePublisher) obj;
                filePublishers.put(filePublisherObj.getCompanyId(), filePublisherObj);
            }
            this.cacheManager.put(cacheKey, filePublishers);
        } else {
            filePublishers = (Map<Integer, FilePublisher>) cacheObj;
        }
        return filePublishers;
    }

    /**
     * Get financial Period descriptions
     */
    @SuppressWarnings("uncheked")
    public Map<String, PeriodDescriptions> getPeriodDescriptions(String language) {
        final String lang = language.toUpperCase();
        Object cacheObj;
        PeriodDescriptions periodDescriptionsObj;
        Map<String, PeriodDescriptions> periodDescriptionsMap = new HashMap<String, PeriodDescriptions>();
        String cacheKey = CACHE_KEY_PERIOD_DESCRIPTIONS + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_PERIOD_DESCRIPTIONS);

            for (Object obj : objectList) {
                periodDescriptionsObj = (PeriodDescriptions) obj;
                periodDescriptionsMap.put(periodDescriptionsObj.getPeriodId(), periodDescriptionsObj);
            }
            this.cacheManager.put(cacheKey, periodDescriptionsMap);
        } else {
            periodDescriptionsMap = (Map<String, PeriodDescriptions>) cacheObj;
        }
        return periodDescriptionsMap;
    }

    public Map<String, RatioGroupDescriptions> getRatioGroupsDescriptions(String language) {
        final String lang = language.toUpperCase();
        Object cacheObj;
        RatioGroupDescriptions ratioGroupDescriptionsObj;
        Map<String, RatioGroupDescriptions> ratioGroupDescriptionsMap = new LinkedHashMap<String, RatioGroupDescriptions>();
        String cacheKey = CACHE_KEY_FINANCIAL_RATIO_DESCRIPTIONS + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_RATIO_GROUP_DESCRIPTIONS);

            for (Object obj : objectList) {
                ratioGroupDescriptionsObj = (RatioGroupDescriptions) obj;
                ratioGroupDescriptionsMap.put(ratioGroupDescriptionsObj.getGroupId(), ratioGroupDescriptionsObj);
            }
            this.cacheManager.put(cacheKey, ratioGroupDescriptionsMap);
        } else {
            ratioGroupDescriptionsMap = (Map<String, RatioGroupDescriptions>) cacheObj;
        }
        return ratioGroupDescriptionsMap;
    }

    /**
     *
     *get all EditorialDescriptions
     *
     * @param language String
     * @return EditorialDescriptions
     */
    public Map<String, EditorialDescriptions> getEditorialDescriptions(String language) {
        final String lang = language.toUpperCase();
        Object cacheObj;
        EditorialDescriptions editorialDescriptionsObj;
        Map<String, EditorialDescriptions> editorialDescriptionsMap = new LinkedHashMap<String, EditorialDescriptions>();
        String cacheKey = CACHE_KEY_EDITORIAL_DESCRIPTIONS + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_EDITORIAL_DESCRIPTIONS);

            Collections.sort(objectList, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    EditorialDescriptions e1 = (EditorialDescriptions) o1;
                    EditorialDescriptions e2 = (EditorialDescriptions) o2;
                    if (e1.getDescription().get(lang) != null) {
                        return (e1.getDescription().get(lang)).compareTo(e2.getDescription().get(lang));
                    } else {
                        return -1;
                    }
                }
            });

            for (Object obj : objectList) {
                editorialDescriptionsObj = (EditorialDescriptions) obj;
                editorialDescriptionsMap.put(editorialDescriptionsObj.getEditorialCode(), editorialDescriptionsObj);
            }

            this.cacheManager.put(cacheKey, editorialDescriptionsMap);
        } else {
            editorialDescriptionsMap = (Map<String, EditorialDescriptions>) cacheObj;
        }
        return editorialDescriptionsMap;
    }

    /**
     * get company type descriptions
     *
     * @param language String
     * @return map (string,companyDto) sorted
     */
    @SuppressWarnings("uncheked")
    public Map<String, CompanyTypeDTO> getCompanyTypes(String language) {

        final String lang = language.toUpperCase();
        Object cacheObj;
        CompanyTypes companyType;
        CompanyTypeDTO companyTypeDTO;
        Map<String, CompanyTypeDTO> companyTypesMap = new LinkedHashMap<String, CompanyTypeDTO>();
        String cacheKey = CACHE_KEY_COMPANY_TYPES + CharUtils.toString(IConstants.Delimiter.TILDE) + lang;

        cacheObj = cacheManager.get(cacheKey);
        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_COMPANY_TYPES);

            Collections.sort(objectList, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    CompanyTypes c1, c2;
                    c1 = (CompanyTypes) o1;
                    c2 = (CompanyTypes) o2;

                    return c1.getCompanyTypeDesc().get(lang).compareTo(c2.getCompanyTypeDesc().get(lang));
                }
            });

            for (Object obj : objectList) {
                companyType = (CompanyTypes) obj;
                companyTypeDTO = new CompanyTypeDTO(companyType.getCompanyTypeId(), companyType.getCompanyTypeDesc());
                companyTypesMap.put(companyTypeDTO.getCompanyTypeId(), companyTypeDTO);
            }

            this.cacheManager.put(cacheKey, companyTypesMap);
        } else {
            companyTypesMap = (Map<String, CompanyTypeDTO>) cacheObj;
        }
        return companyTypesMap;
    }

    @SuppressWarnings("uncheked")
    public List<String> getSupportedCurrencies() {
        List<String> supportedCurrencies = (List<String>) this.cacheManager.get(CacheKeyConstant.CACHE_KEY_SUPPORTED_CURRENCIES);
        if (supportedCurrencies == null || supportedCurrencies.isEmpty()) {
            supportedCurrencies = (List<String>) this.hibernateDaoFactory.getMasterDataDAO().get(MASTER_DATA_SUPPORTED_CURRENCIES, null);
            if (supportedCurrencies != null) {
                this.cacheManager.put(CACHE_KEY_SUPPORTED_CURRENCIES, supportedCurrencies);
            }
        }

        return supportedCurrencies;
    }

    @SuppressWarnings("uncheked")
    public Map<String, ExchangeMetaData> getExchangeMetaData() {

        Map<String, ExchangeMetaData> exchangeMetaDataMap = null;
        Object cacheObj = cacheManager.get(CACHE_KEY_EXCHANGE_METADATA_MAP);

        if (cacheObj == null) {
            initExchangeMetaData();
            cacheObj = cacheManager.get(CACHE_KEY_EXCHANGE_METADATA_MAP);
        }
        exchangeMetaDataMap = (Map<String, ExchangeMetaData>) cacheObj;
        return exchangeMetaDataMap;
    }

    @SuppressWarnings("uncheked")
    public Map<String, StockMetaData> getStockMetaData() {

        Map<String, StockMetaData> stockMetaDataMap = null;
        Object cacheObj = cacheManager.get(CACHE_KEY_STOCK_METADATA_MAP);

        if (cacheObj == null) {
            initStockMetaData();
            cacheObj = cacheManager.get(CACHE_KEY_STOCK_METADATA_MAP);
        }
        stockMetaDataMap = (Map<String, StockMetaData>) cacheObj;
        return stockMetaDataMap;
    }

    /**
     * Get top news edition persist meta data                                                `
     *
     * @return edition vs TopNewsEditionDTO
     */
    @SuppressWarnings("uncheked")
    public Map<String, TopNewsEditionDTO> getTopNewsEdition() {
        Map<String, TopNewsEditionDTO> editionDTOMap;
        Object edtObj = this.cacheManager.get(CACHE_KEY_TOP_NEWS_EDITION_MAP);

        if (edtObj != null) {
            editionDTOMap = (Map<String, TopNewsEditionDTO>) edtObj;
        } else {
            editionDTOMap = initAndGetTopNewsEdition();
        }
        return editionDTOMap;
    }

    /**
     * Get account types
     *
     * @return List<Object> object
     */
    @SuppressWarnings("uncheked")
    public Map<String, AccountType> getAccountType(String language) {
        String cacheKey = CACHE_KEY_ACCOUNT_TYPES + CharUtils.toString(IConstants.Delimiter.TILDE) + language;
        Object cacheObj;
        AccountType accountTypeObj;
        Map<String, AccountType> accountTypeMap = new LinkedHashMap<String, AccountType>();

        cacheObj = cacheManager.get(cacheKey);

        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_ACCOUNT_TYPE);

            for (Object obj : objectList) {
                accountTypeObj = (AccountType) obj;
                accountTypeMap.put(accountTypeObj.getAccountTypeId(), accountTypeObj);
            }

            this.cacheManager.put(cacheKey, accountTypeMap);
        } else {
            accountTypeMap = (Map<String, AccountType>) cacheObj;
        }

        return accountTypeMap;
    }

    /**
     * Get account types
     *
     * @return List<Object> object
     */
    @SuppressWarnings("uncheked")
    public Map<String, SalesRepresentative> getSalesRep() {
        String cacheKey = CACHE_KEY_SALES_REP;
        Object cacheObj;
        SalesRepresentative salesRepObj;
        Map<String, SalesRepresentative> salesRepMap = new LinkedHashMap<String, SalesRepresentative>();

        cacheObj = cacheManager.get(cacheKey);

        if (cacheObj == null) {
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();
            List<Object> objectList = masterDataDAO.getAll(MASTER_DATA_SALES_REP);

            for (Object obj : objectList) {
                salesRepObj = (SalesRepresentative) obj;
                salesRepMap.put(salesRepObj.getSalesRepId() + "", salesRepObj);
            }

            this.cacheManager.put(cacheKey, salesRepMap);
        } else {
            salesRepMap = (Map<String, SalesRepresentative>) cacheObj;
        }

        return salesRepMap;
    }

    /**
     * get News Provider data from db and put into cache
     *
     * @return news sources
     */
    @SuppressWarnings("uncheked")
    public Map<String, NewsSourceDTO> getAllNewsSources() {
        Map<String, NewsSourceDTO> newsSourcesMap = null;
        Object newsSourcesObj = this.cacheManager.get(CACHE_KEY_NEWS_SOURCES);

        if (newsSourcesObj == null) {

            newsSourcesMap = new HashMap<String, NewsSourceDTO>();
            NewsSourceDTO newsSourceDTO = null;
            MasterDataDAO masterDataDAO = this.hibernateDaoFactory.getMasterDataDAO();

            List<Object> newsSrcList = masterDataDAO.getAll(MASTER_DATA_NEWS_SOURCES);

            for (Object type : newsSrcList) {
                NewsSource newsSource = (NewsSource) type;
                newsSourceDTO = new NewsSourceDTO(newsSource.getSourceId(), newsSource.getDescription());

                newsSourcesMap.put(newsSource.getSourceId(), newsSourceDTO);

            }

            this.cacheManager.put(CACHE_KEY_NEWS_SOURCES, newsSourcesMap, 0);
        } else {
            newsSourcesMap = (Map<String, NewsSourceDTO>) newsSourcesObj;
        }

        return newsSourcesMap;
    }

    //region generate country indicators

    @SuppressWarnings("unchecked")
    public  Map<String,Map<String, CountryIndicatorGroupDTO>> initAndGetAllCountryIndicatorGroupTypes() {

        Map<String,Map<String, CountryIndicatorGroupDTO>> countryIndicatorGroups;
        Object countryIndicatorGroupsObj = this.cacheManager.get(CACHE_KEY_COUNTRY_INDICATOR_GROUP_TYPES);

        if (countryIndicatorGroupsObj == null) {
            LOG.debug("Initializing Country Indicator Groups....");
            ApplicationSetting supportedLanguagesSetting = this.getSetting(ApplicationSettingsKeys.KEY_SUPPORTED_LANGUAGES);
            List<String> supportedLanguages = supportedLanguagesSetting.getValueList();

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(MacroEconomyDBHelper.getAllCountryIndicatorGroupTypesQuery());
            requestDBDTO.setSupportedLang(supportedLanguages);

            countryIndicatorGroups = ( Map<String,Map<String, CountryIndicatorGroupDTO>>) jdbcDaoFactory.getMetaDataDAO().get(META_DATA_COUNTRY_INDICATOR_GROUPS, requestDBDTO);
            if (countryIndicatorGroups != null && !countryIndicatorGroups.isEmpty()) {
                cacheManager.put(CACHE_KEY_COUNTRY_INDICATOR_GROUP_TYPES, countryIndicatorGroups, 0);
                LOG.debug("Initializing Country Indicator Groups....Completed | Count : " + countryIndicatorGroups.size());
            }
        } else {
            countryIndicatorGroups = ( Map<String,Map<String, CountryIndicatorGroupDTO>>) countryIndicatorGroupsObj;
        }

        return countryIndicatorGroups;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, CountryIndicatorTypeDTO>> initAndGetAllCountryIndicatorTypes() {

        Map<String, Map<String, CountryIndicatorTypeDTO>> countryIndicators;
        Object indicatorTypes = this.cacheManager.get(CACHE_KEY_COUNTRY_INDICATOR_TYPES);

        if (indicatorTypes == null) {
            LOG.debug("Initializing Country Indicator Types...");
            ApplicationSetting supportedLanguagesSetting = this.getSetting(ApplicationSettingsKeys.KEY_SUPPORTED_LANGUAGES);
            List<String> supportedLanguages = supportedLanguagesSetting.getValueList();

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(MacroEconomyDBHelper.getAllCountryIndicatorTypesQuery());
            requestDBDTO.setSupportedLang(supportedLanguages);

            countryIndicators = (Map<String, Map<String, CountryIndicatorTypeDTO>>) jdbcDaoFactory.getMetaDataDAO().get(META_DATA_COUNTRY_INDICATOR_TYPES, requestDBDTO);
            if (countryIndicators != null && !countryIndicators.isEmpty()) {
                cacheManager.put(CACHE_KEY_COUNTRY_INDICATOR_TYPES, countryIndicators, 0);
                LOG.debug("Initializing Country Indicator Types...Completed | Count : " + countryIndicators.size());
            }
        } else {
            countryIndicators = (Map<String, Map<String, CountryIndicatorTypeDTO>>) indicatorTypes;
        }

        return countryIndicators;
    }

    //endregion

    /**
     * get fund asset descriptions
     *
     * @param language language only supports EN
     * @return Map - <asset id, long desc>
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getFundAssetDescriptions(String language) {
        Map<String, String> fundAssetDescriptions;
        Object obj = this.cacheManager.get(CACHE_KEY_INVESTMENT_FUND_ASSET);

        if (obj == null) {
            List<String> languages = new ArrayList<String>(1);
            languages.add(language);

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(MasterDataDbHelper.SQL_GET_INVESTMENT_FUND_ASSETS.replaceAll("#lang#", language));
            requestDBDTO.setSupportedLang(languages);
            fundAssetDescriptions = (Map<String, String>) jdbcDaoFactory.getMetaDataDAO().get(DBConstants.MetaDataType.META_DATA_LOAD_KEY_VALUE, requestDBDTO);

            if (fundAssetDescriptions != null) {
                this.cacheManager.put(CACHE_KEY_INVESTMENT_FUND_ASSET, fundAssetDescriptions, 0);
            }

        } else {
            fundAssetDescriptions = (Map<String, String>) obj;
        }

        return fundAssetDescriptions;
    }

    /**
     * get fund sector descriptions
     *
     * @param language language only supports EN
     * @return Map - <sector, long desc>
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getFundSectorDescriptions(String language) {
        Map<String, String> fundSectorDescriptions;
        Object obj = this.cacheManager.get(CACHE_KEY_INVESTMENT_FUND_SECTOR);

        if (obj == null) {
            List<String> languages = new ArrayList<String>(1);
            languages.add(language);

            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(MasterDataDbHelper.SQL_GET_INVESTMENT_FUND_SECTORS.replaceAll("#lang#", language));
            requestDBDTO.setSupportedLang(languages);
            fundSectorDescriptions = (Map<String, String>) jdbcDaoFactory.getMetaDataDAO().get(DBConstants.MetaDataType.META_DATA_LOAD_KEY_VALUE, requestDBDTO);

            if (fundSectorDescriptions != null) {
                this.cacheManager.put(CACHE_KEY_INVESTMENT_FUND_SECTOR, fundSectorDescriptions, 0);
            }

        } else {
            fundSectorDescriptions = (Map<String, String>) obj;
        }

        return fundSectorDescriptions;
    }

    //region Coupon Day Count Types

    /**
     * Init and get all coupon day count types master data
     *
     * @return dayCountId - CouponDayCountTypeDTO
     */
    @SuppressWarnings("unchecked")
    public Map<String, CouponDayCountTypeDTO> initAndGetCouponDayCountTypes() {
        Map<String, CouponDayCountTypeDTO> dayCountTypeDTOMap;

        String cacheKey = CACHE_KEY_COUPON_DAY_COUNT_TYPES;
        Object obj = this.cacheManager.get(cacheKey);

        if (obj == null) {
            LOG.debug("Loading CouponDayCountTypes from DB...");
            RequestDBDTO requestDBDTO = new RequestDBDTO();
            requestDBDTO.setQuery(MasterDataDbHelper.SQL_GET_ALL_COUPON_DAY_COUNT_MASTER);
            dayCountTypeDTOMap = (Map<String, CouponDayCountTypeDTO>) jdbcDaoFactory.getMetaDataDAO().get(DBConstants.MetaDataType.META_DATA_LOAD_COUPON_DAY_COUNT, requestDBDTO);

            if (dayCountTypeDTOMap != null) {
                this.cacheManager.put(cacheKey, dayCountTypeDTOMap, 0);
            }

        } else {
            dayCountTypeDTOMap = (Map<String, CouponDayCountTypeDTO>) obj;
        }

        return dayCountTypeDTOMap;
    }

    /**
     * load classification serials from Oracle DB & add to cache
     * NOTE : This is not updated by SP(One-time sync)
     * Used in market financial average ratios
     * GICS CODE vs CLASSIFICATION SERIAL - irrespective of gics level
     */
    @SuppressWarnings("unchecked")
    private void initClassificationSerials() {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(MasterDataDbHelper.SQL_GET_ALL_CLASSIFICATION_SERIALS);
        Map<String, Integer> classificationSerialMap =
                (Map<String, Integer>) jdbcDaoFactory.getMetaDataDAO().get(DBConstants.MetaDataType.META_DATA_GET_CLASSIFICATION_SERIALS, requestDBDTO);
        if (classificationSerialMap != null) {
            this.cacheManager.put(CACHE_KEY_CLASSIFICATION_SERIAL_MAP, classificationSerialMap, 0);
        }
    }

    /**
     * get classification serials
     *
     * @return GICS CODE vs CLASSIFICATION SERIAL - irrespective of gics level
     */
    @SuppressWarnings("unchecked")
    public Map<String, Integer> getClassificationSerials() {
        Map<String, Integer> classificationSerialMap = null;

        Object obj = this.cacheManager.get(CACHE_KEY_CLASSIFICATION_SERIAL_MAP);

        if (obj == null) {
            initClassificationSerials();
            obj = this.cacheManager.get(CACHE_KEY_CLASSIFICATION_SERIAL_MAP);
        }
        if (obj != null) {
            classificationSerialMap = (Map<String, Integer>) obj;
        }

        return classificationSerialMap;
    }

    /**
     * Get ticker data supported language
     *
     * @return supported languages
     */
    private List<String> getSupportedLanguages() {
        List<String> languages = new ArrayList<String>();
        ApplicationSetting langSettings = getSetting(ApplicationSettingsKeys.KEY_SUPPORTED_LANGUAGES);
        if (langSettings != null) {
            languages = langSettings.getValueList();
        }
        return languages;
    }

    //endregion

    public int deleteCache(Object notifications) {
        int status = 0;
        Notification notification = (Notification) notifications;
        if (notification.getInformationType().equalsIgnoreCase(IConstants.FundamentalDataTypes.FILE.name())
                || notification.getInformationType().equalsIgnoreCase(IConstants.FundamentalDataTypes.CP.name())) {

            List<String> cacheKeysToDelete = DataAccessUtils.searchCacheKeys(CacheKeyConstant.CACHE_KEY_FILE_PUBLISHERS +
                    IConstants.Delimiter.TILDE + IConstants.Delimiter.S_ASTERISK, cacheManager);
            status = deleteFromCache(cacheKeysToDelete);
        }
        return status;
    }

    private int deleteFromCache(List<String> cacheKeysToDelete) {
        int status = 0;
        if (cacheKeysToDelete != null) {
            // delete one by one
            for (String cacheKey : cacheKeysToDelete) {
                cacheManager.delete(cacheKey);
                status++;
            }
        }
        return status;
    }

    public Map<String, MarketDTO> getCountryExchangeData() {
        List<MarketDTO> exchangeDataList = getAllExchangeData();
        Map<String, MarketDTO> countryExchangeMap = null;
        if (exchangeDataList != null) {
            countryExchangeMap = new HashMap<String, MarketDTO>();
            for (MarketDTO marketDTO : exchangeDataList) {
                if (marketDTO.getCountryCode() != null) {
                    countryExchangeMap.put(marketDTO.getCountryCode().trim(), marketDTO);
                }
            }
        }
        return countryExchangeMap;
    }

    //region TICKER CLASSES

    /**
     * Get all Ticker classes
     *
     * @return all ticker classes
     */
    @SuppressWarnings("unchecked")
    public List<TickerClassLevels> getAllTickerClasses() {
        List<TickerClassLevels> tickerClassLevelsList;
        if(isCacheEnabled) {
            String cacheKey = CACHE_KEY_TICKER_CLASSES;
            Object obj = this.cacheManager.get(cacheKey);

            if (obj == null) {
                LOG.debug("Loading getAllTickerClasses from DB...");
                tickerClassLevelsList = getTickerClassLevels();
                if (tickerClassLevelsList != null) {
                    this.cacheManager.put(cacheKey, tickerClassLevelsList, 0);
                }
            } else {
                tickerClassLevelsList = (List<TickerClassLevels>) obj;
            }
        }else{
            tickerClassLevelsList = getTickerClassLevels();
        }
        return tickerClassLevelsList;
    }

    public String getEquityTickerL3Classes() {
        String equityTickerClassesAsString;
        if(isCacheEnabled){
            String cacheKey = CACHE_KEY_EQUITY_L3_TICKER_CLASSES;
            Object obj = this.cacheManager.get(cacheKey);
            if(obj == null){
                equityTickerClassesAsString = getEquityTickerL3ClassesAsString();
                this.cacheManager.put(cacheKey, equityTickerClassesAsString, 0);
            }else{
                equityTickerClassesAsString = (String)obj;
            }
        }else{
            equityTickerClassesAsString = getEquityTickerL3ClassesAsString();
        }
        return equityTickerClassesAsString;
    }

    public String getEquityCommonStockTickerL3Classes() {
        String equityTickerClassesAsString;
        if(isCacheEnabled){
            String cacheKey = CACHE_KEY_EQUITY_COMMON_STOCK_L3_TICKER_CLASSES;
            Object obj = this.cacheManager.get(cacheKey);
            if(obj == null){
                equityTickerClassesAsString = getEquityCommonStockTickerL3ClassesAsString();
                this.cacheManager.put(cacheKey, equityTickerClassesAsString, 0);
            }else{
                equityTickerClassesAsString = (String)obj;
            }
        }else{
            equityTickerClassesAsString = getEquityCommonStockTickerL3ClassesAsString();
        }
        return equityTickerClassesAsString;
    }

    private List<TickerClassLevels> getTickerClassLevels(){
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(MasterDataDbHelper.QUERY_TICKER_CLASSIFICATION);
        requestDBDTO.setSupportedLang(getSupportedLanguages());
        return (List<TickerClassLevels>) jdbcDaoFactory.getMetaDataDAO().get(DBConstants.MetaDataType.META_DATA_GET_TICKER_CLASSIFICATIONS, requestDBDTO);
    }

    private String getEquityTickerL3ClassesAsString(){
        List<TickerClassLevels> tickerClasses = getAllTickerClasses();
        List<Integer> equityTickerClasses = new ArrayList<Integer>(10);
        for(TickerClassLevels level : tickerClasses){
            if(level.isEquity()){
                equityTickerClasses.add(level.getLevel3());
            }
        }
        return StringUtils.join(equityTickerClasses, IConstants.COMMA);
    }

    private String getEquityCommonStockTickerL3ClassesAsString(){
        List<TickerClassLevels> tickerClasses = getAllTickerClasses();
        List<Integer> equityTickerClasses = new ArrayList<Integer>(10);
        for(TickerClassLevels level : tickerClasses){
            if(level.isEquityCommonStock()){
                equityTickerClasses.add(level.getLevel3());
            }
        }
        return StringUtils.join(equityTickerClasses, IConstants.COMMA);
    }

    /**
     * Get all L2 Ticker Classifications
     *
     * @return ticker class l2
     */
    @SuppressWarnings("unchecked")
    public Map<String, TickerClassificationDTO> getAllIndexL2TickerClassifications(String language) {
        String cacheKey = CACHE_KEY_INDEX_L2_TICKER_CLASSES;
        Object obj = this.cacheManager.get(cacheKey);
        Map<String, TickerClassificationDTO> class2Data;
        if(obj == null) {
            List<TickerClassLevels> tickerClassLevelsList = getAllTickerClasses();
            Map<String, TickerClassificationDTO> indexTickerClass = new HashMap<String, TickerClassificationDTO>(10);
            for (TickerClassLevels level : tickerClassLevelsList) {
                if(level.isIndex()){
                    String l2 = String.valueOf(level.getLevel2());
                    if (!indexTickerClass.containsKey(l2)) {
                        TickerClassificationDTO tickerClassificationDTO = new TickerClassificationDTO();
                        tickerClassificationDTO.setTypeId(level.getLevel2());
                        tickerClassificationDTO.setDescription(level.getLevel2Description(language));
                        indexTickerClass.put(l2, tickerClassificationDTO);
                    }
                }
            }
            class2Data = getSortedTickerClassifications(indexTickerClass, language);
            this.cacheManager.put(cacheKey, class2Data, 0);
        }else{
            class2Data = (Map<String, TickerClassificationDTO>)obj;
        }
        return class2Data;
    }

    /**
     * sort ticker classes
     * @param classificationDTOMap l2 map
     * @param language lang
     * @return sorted map
     */
    private Map<String, TickerClassificationDTO> getSortedTickerClassifications(Map<String, TickerClassificationDTO> classificationDTOMap, final String language) {

        Map<String, TickerClassificationDTO> tickerClassificationDTOMap = new LinkedHashMap<String, TickerClassificationDTO>();
        List<TickerClassificationDTO> classificationDTOs = new ArrayList<TickerClassificationDTO>(classificationDTOMap.values());

        Collections.sort(classificationDTOs, new Comparator<TickerClassificationDTO>() {
            @Override
            public int compare(TickerClassificationDTO c1, TickerClassificationDTO c2) {
                if (c1.getLangDescMap() != null && c2.getLangDescMap() != null
                        && c1.getLangDescMap().get(language.toUpperCase()) != null
                        && c2.getLangDescMap().get(language.toUpperCase()) != null) {
                    return c1.getLangDescMap().get(language.toUpperCase()).compareTo(c2.getLangDescMap().get(language.toUpperCase()));
                } else {
                    return -1;
                }
            }
        });

        for (TickerClassificationDTO classificationDTO : classificationDTOs) {
            tickerClassificationDTOMap.put(String.valueOf(classificationDTO.getTypeId()), classificationDTO);
        }

        return tickerClassificationDTOMap;
    }

    //endregion


    public  Map<Integer,Map<String,String>> getFundClassificationMap(){
        Map<String, FundClass> fundClassList = getAllFundClassifications();
        Map<Integer,Map<String,String>> descriptionMap = new HashMap <Integer, Map<String, String>>();

        for(Map.Entry<String,FundClass> entry: fundClassList.entrySet()){
            String key = entry.getKey();
            FundClass value= entry.getValue();
            descriptionMap.put(Integer.parseInt(key),value.getShortDescription());
        }

        return descriptionMap ;
    }
}
