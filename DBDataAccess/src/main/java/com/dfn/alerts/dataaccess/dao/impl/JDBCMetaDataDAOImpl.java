package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.*;
import com.dfn.alerts.beans.dcms.*;
import com.dfn.alerts.beans.ownershipLimits.OwnershipLimitSeriesDTO;
import com.dfn.alerts.beans.ownershipLimits.StockOwnershipDTO;
import com.dfn.alerts.beans.ownershipLimits.StockOwnershipLimitSeriesDTO;
import com.dfn.alerts.beans.tickers.EquityTickerDTO;
import com.dfn.alerts.beans.tickers.FundBenchmarkDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.tickers.TickerClassLevels;
import com.dfn.alerts.dataaccess.dao.MetaDataDAO;
import com.dfn.alerts.dataaccess.utils.*;
import com.dfn.alerts.dataaccess.utils.tickers.EquityTickerDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.FundBenchmarkDBHelper;
import com.dfn.alerts.dataaccess.utils.tickers.IndexTickerDBHelper;
import com.dfn.alerts.beans.tickers.IndexDTO;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.MetaDataType.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 3/5/13
 * Time: 12:08 PM
 */
public class JDBCMetaDataDAOImpl implements MetaDataDAO {

    private static final Logger LOG = LogManager.getLogger(JDBCMetaDataDAOImpl.class);
    private static final String SP_OUT_PARA = "p_msg_id";
    private static final String SP_PROCE_PREFIX = "{call ";
    private static final String SP_PROCE_POSTFIX = "}";
    private static final String IMAGE_TYPE = ".jpg";
    private static final Integer OUT_STATUS = 0;
    public static final int INITIAL_CAPACITY_TEN = 10;
    public static final int LIST_SIZE = 100;
    public static final int TICKER_LIST_SIZE = 10000;

    public static final String DOWNLOAD_URL = " Download URL: ";
    public static final String QUERY = " QUERY: ";
    public static final String VWAP = "VWAP";

    private static final String SQL_UPDATE_INDIVIDUAL_DOWNLOADED_FLAG = "UPDATE INDIVIDUAL_DETAILS SET DOWNLOADED = 1 WHERE INDIVIDUAL_ID IN (";
    private static final String QUERY_UPDATE_FUND_TICKER_COUNTRIES = "UPDATE COUNTRY SET IS_MF_DATA=? WHERE COUNTRY_CODE IN (";
    private static final String QUERY_UPDATE_NON_FUND_TICKER_COUNTRIES = "UPDATE COUNTRY SET IS_MF_DATA=? WHERE COUNTRY_CODE NOT IN (";

    private DataSource driverManagerDataSource;


    public List<Object> getAll(int metadataType) {
        return Collections.emptyList();
    }

    public Object get(int metadataType, Object id) {
        Object returnObj = null;

        switch (metadataType) {
            case META_DATA_LOAD_IND_IMAGES:
                returnObj = downLoadIndividualImagesAndSaveToDisk((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_COM_IMAGES:
                returnObj = downLoadImagesAndSaveToDisk((RequestDBDTO) id);
                break;
            case META_DATA_DELETE_COM_IMAGES:
            case META_DATA_DELETE_IND_IMAGES:
            case META_DATA_DELETE_SRC_IMAGES:
            case META_DATA_DELETE_COUNTRY_IMAGES:
                returnObj = deleteSavedImages((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_EQUITY_TICKERS:
                returnObj = getAllTickerData((RequestDBDTO) id, IConstants.AssetType.EQUITY);
                break;
            case META_DATA_LOAD_ALL_INDEX_TICKERS:
                returnObj = getAllTickerData((RequestDBDTO) id, IConstants.AssetType.INDEX);
                break;
            case META_DATA_LOAD_ALL_FUND_TICKERS:
                returnObj = getAllTickerData((RequestDBDTO) id, IConstants.AssetType.MUTUAL_FUNDS);
                break;
            case META_DATA_LOAD_ALL_FIXED_INCOME_TICKERS:
                returnObj = getAllTickerData((RequestDBDTO) id, IConstants.AssetType.FIXED_INCOME);
                break;
            case META_DATA_LOAD_INDEX_TICKERS:
                returnObj = getAllIndices((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_SNAP_UPDATED_INDEX_TICKERS:
                returnObj = getAllSnapUpdatedIndices((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_UNLISTED_COMPANIES:
                returnObj = getAllTickerData((RequestDBDTO) id, IConstants.AssetType.EQUITY);
                break;
            case META_DATA_LOAD_ALL_SOURCES:
                returnObj = getAllSources((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_SRC_IMAGES:
                returnObj = downLoadImagesAndSaveToDisk((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_COUNTRY_IMAGES:
                returnObj = downLoadImagesAndSaveToDisk((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_COUNTRY_TICKERS:
                returnObj = getAllCountryTickers((RequestDBDTO) id);
                break;
            case META_DATA_EXECUTE_PROCEDURE:
                returnObj = executeProcedure((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_IPO_TICKERS:
                returnObj = getAllIpoData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_MA_DATA:
                returnObj = getAllMAData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_MA_TICKERS:
                returnObj = getCompanyDataForMA((RequestDBDTO) id);
                break;
            case META_DATA_UPDATE_DATA_SYNC_STATUS:
                returnObj = getDataSynchronizationStatus((RequestDBDTO) id);
                break;
            case META_DATA_GET_ALL_FUND_BENCHMARKS:
                returnObj = getFundBenchmarkData((RequestDBDTO) id);
                break;
            case META_DATA_OWNERSHIP_SERIES_DEF:
                returnObj = getOwnershipLimitDefinitions((RequestDBDTO) id);
                break;
            case META_DATA_MARKET_OWNERSHIP_DATA:
                returnObj = getMarketOwnershipData((RequestDBDTO) id);
                break;
            case META_DATA_STOCK_OWNERSHIP_DEF:
                returnObj = getStockOwnershipLimitDefinitions((RequestDBDTO) id);
                break;
            case META_DATA_STOCK_OWNERSHIP_HISTORY:
                returnObj = getStockOwnershipHistory((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_KPI:
                returnObj = getAllKpiData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_FUND_INVESTMENT_ALLOCATIONS:
                returnObj = getFundInvestmentAllocations((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_FUND_INVESTMENTS_BY_MARKET:
                returnObj = getFundInvestmentsByMarket((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_FILE_DATA:
                returnObj = getAllDocFileData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_COMPANY_DATA:
                returnObj = getAllDocCompanyData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_COUNTRY_DATA:
                returnObj = getAllDocCountryData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_EXCHANGE_DATA:
                returnObj = getAllDocExchangeData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_INDUSTRY_DATA:
                returnObj = getAllDocIndustryData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_PERIOD_DATA:
                returnObj = getAllDocPeriodData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_REGION_DATA:
                returnObj = getAllDocRegionData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_SECTOR_DATA:
                returnObj = getAllDocSectorsData((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_DOC_SYMBOL_DATA:
                returnObj = getAllDocSymbolData((RequestDBDTO) id);
                break;
            case META_DATA_SOURCE_TIME_ZONE_OFFSET:
                returnObj = getSourceTimeZoneOffSet((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_FUND_POSITIONS:
                returnObj = getFundPositions((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_MAX_FUND_POSITIONS:
                returnObj = getFundPositionsForMax((RequestDBDTO) id);
                break;
            case META_DATA_ALL_NEWS_SECTIONS_ORACLE:
                returnObj = searchAllSectionsNews((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_ALL_ADJUSTED_PRICE_SNAPSHOT:
                returnObj = searchAllPriceSnapshotData((RequestDBDTO) id);
                break;
            case META_DATA_GET_INVESTOR_TYPE_VALUES:
                returnObj = getInvestorTypeValuesData((RequestDBDTO) id);
                break;
            case META_DATA_CURRENCY_MASTER_DATA:
                returnObj = getCurrencySearchData((RequestDBDTO) id);
                break;
            case META_DATA_COUNTRY_INDICATOR_TYPES:
                returnObj = getCountryIndicatorTypes((RequestDBDTO) id);
                break;
            case META_DATA_COUNTRY_INDICATOR_GROUPS:
                returnObj = getCountryIndicatorGroups((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_CURRENT_FUND_INVESTMENT_ALLOCATIONS:
                returnObj = getCurrentFundInvestmentAllocations((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_KEY_VALUE:
                returnObj = getKeyValuePairs((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_COUPON_DAY_COUNT:
                returnObj = getAllCouponDayCountValues((RequestDBDTO) id);
                break;
            case META_DATA_LOAD_FUND_POSITIONS_TABLE:
                returnObj = getFundPositionsForTable((RequestDBDTO) id);
                break;
            case META_DATA_GET_COUNTRY_DATA:
                returnObj = getCountryData((RequestDBDTO) id);
                break;
            case META_DATA_GET_SINGLE_COLUMN_DATA_SET:
                returnObj = getSingleColumnDataSet((RequestDBDTO) id);
                break;
            case META_DATA_GET_TICKER_CLASSIFICATIONS:
                returnObj = getTickerClassifications((RequestDBDTO) id);
                break;
            case META_DATA_GET_CLASSIFICATION_SERIALS:
                returnObj = getGicsClassificationSerials((RequestDBDTO) id);
                break;
            case META_DATA_GET_MERGE_AND_ACQUISITION_DETAILS:
                returnObj = selectMADetails((RequestDBDTO) id);
                break;
            case META_DATA_GET_ALL_FUND_TICKER_COUNTRIES:
                returnObj = getFundTickerCountryList((RequestDBDTO) id);
                break;
            case META_DATA_GET_VWAP_FOR_TICKERS:
                returnObj = getVWAPForTickers((RequestDBDTO) id);
                break;
            case META_DATA_COMPANY_KPI:
                returnObj = getCompanyKPI((RequestDBDTO) id);
                break;
            case META_DATA_RELATED_KPI:
                returnObj = getRelatedKPI((RequestDBDTO) id);
                break;
            case META_DATA_GET_IPO_DATA:
                returnObj = selectIPOData((RequestDBDTO) id);
                break;
            case META_DATA_GET_IPO_RESULT_COUNT:
                returnObj = selectRecordCount((String) id);
                break;
            case META_DATA_COMPANY_AGGREGATES:
                returnObj = getCompanyAggregatesData((String) id);
                break;
            case META_DATA_COMPANY_FINANCIAL_COMPANY_SIZE:
                returnObj = getCompanySizeData((RequestDBDTO) id);
                break;
            case META_DATA_COMPANY_FINANCIAL_COMPANY_SIZE_BY_ID:
                returnObj = getCompanySizeDataForCompany((RequestDBDTO) id);
                break;
            case GET_EQUITY_TICKERS:
                returnObj =getEquityTickerDTOMap((RequestDBDTO) id);
            default:
                break;
        }
        return returnObj;
    }

    /**
     *
     * @param requestDBDTO
     * @return
     */
    private Object getRelatedKPI(RequestDBDTO requestDBDTO){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<KpiDTO> kpiDTOList;
        KpiData kpiData = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();

            kpiDTOList = new ArrayList<KpiDTO>(1);
            kpiData = new KpiData();
            while (rs.next()) {
                kpiDTOList.add(KPIDBHelper.getDetailKPIData(rs));
            }
            kpiData.setKpiDTOList(kpiDTOList);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return kpiData;
    }

    /**
     *
     * @param requestDBDTO
     * @return
     */
    private Object getCompanyKPI(RequestDBDTO requestDBDTO){
        Map<String, Object> customParam = requestDBDTO.getCustomParams();
        List<String> companyIds = (List<String>) customParam.get(IConstants.CustomDataField.KPI_COMPANY_LIST);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<KpiDTO> kpiDTOList = null;
        Map<String, List<KpiDTO>> kpiCountryMap = new HashMap<String, List<KpiDTO>>(1);
        KpiDTO kpiDTO;
        String query;

        try {
            connection = driverManagerDataSource.getConnection();
            for (String id : companyIds) {
                query = requestDBDTO.getQuery();
                query = query.replaceAll("#companyId#", id);
                preparedStatement = connection.prepareStatement(query.replace("\\", ""));
                rs = preparedStatement.executeQuery();

                kpiDTOList = new ArrayList<KpiDTO>(1);
                while (rs.next()) {
                    kpiDTO = KPIDBHelper.getKPIData(rs);
                    kpiDTOList.add(kpiDTO);
                }
                kpiCountryMap.put(id, kpiDTOList);
                rs.close();
                preparedStatement.close();
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }

        return kpiCountryMap;
    }

    /**
     * method to connect with db and get result set
     * @param requestDBDTO database request
     * @return result object
     */
    private Object getCompanySizeData(RequestDBDTO requestDBDTO){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        List<CompanySizeDTO> companySizeDTOList = new LinkedList<CompanySizeDTO>();
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            while (rs != null && rs.next()) {
                companySizeDTOList.add(CompanyFinancialDBHelper.getCompanySizeData(rs, requestDBDTO.getSupportedLang()));
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return companySizeDTOList;
    }

    /**
     * method to connect with db and get result set
     * @param requestDBDTO database request
     * @return result object
     */
    private Object getCompanySizeDataForCompany(RequestDBDTO requestDBDTO){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        CompanySizeDTO companySizeDTO = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            while (rs != null && rs.next()) {
                companySizeDTO = CompanyFinancialDBHelper.getCompanySizeDataForCompany(rs);
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return companySizeDTO;
    }

    /**
     * @param metadataType
     * @param data
     * @return
     */
    public boolean insert(int metadataType, Object data) {
        boolean status = false;
        switch (metadataType) {
            case META_DATA_UPDATE_FUND_TICKER_COUNTRIES:
                status = updateFundTickerCountryList((Set<String>) data);
                break;
            default:
                break;
        }
        return status;
    }

    public boolean delete(int metadataType, Object data) {
        return false;
    }

    private int downLoadIndividualImagesAndSaveToDisk(RequestDBDTO requestDBDTO) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(" Start downloading images syncTypes : " + requestDBDTO.getSyncTypes() +
                    DOWNLOAD_URL + requestDBDTO.getDownloadURL() + QUERY + requestDBDTO.getQuery());
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        InputStream inputStream;
        Integer status = OUT_STATUS;
        String query = requestDBDTO.getProcedureName();
        String path = requestDBDTO.getDownloadURL();

        List<String> idList = new ArrayList<String>();

        Map<String, InputStream> results = new HashMap<String, InputStream>();
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                inputStream = rs.getBlob(2).getBinaryStream();
                results.put(rs.getString(1), inputStream);
            }
            for (Map.Entry<String, InputStream> entry : results.entrySet()) {
                InputStream logoContent = entry.getValue();

                FileOutputStream fos = new FileOutputStream(path + entry.getKey() + IMAGE_TYPE);
                int b = 0;
                while ((b = logoContent.read()) != -1) {
                    fos.write(b);
                }
                fos.flush();
                fos.close();
                status++;
                idList.add(entry.getKey());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Completed downloading images |Download Count :" + status);
            }
        } catch (IOException ex) {
            status = IConstants.UpdateStatus.UPDATE_FAILED;
            LOG.error(ex.getMessage(), ex);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } catch (Exception sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Completed downloading images " + DOWNLOAD_URL + requestDBDTO.getDownloadURL() + QUERY + requestDBDTO.getQuery());
            }
        }
        if (status > 0) {
            updateIndividualImageDownloadedFlag(idList);
        }
        return status;
    }

    /**
     * set downloaded flag 1 in db after images have been downloaded
     *
     * @param ids id set
     * @return status
     */
    private int updateIndividualImageDownloadedFlag(List<String> ids) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int status = 1;
        try {
            connection = driverManagerDataSource.getConnection();
            int upper = (int) Math.ceil(ids.size() / 100d);
            for (int i = 0; i < upper; i++) {
                String id = StringUtils.join(ids.subList(i * LIST_SIZE, i == upper - 1 ? ids.size() : (i + 1) * LIST_SIZE - 1), IConstants.Delimiter.COMMA);
                String query = SQL_UPDATE_INDIVIDUAL_DOWNLOADED_FLAG + id + DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE;
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeQuery();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Completed downloading images for ids: " + ids.size() + " for individuals");
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }
        return status;
    }

    /**
     * Download images to a path from database tables
     *
     * @param requestDBDTO request object
     * @return number of images
     */
    private Integer downLoadImagesAndSaveToDisk(RequestDBDTO requestDBDTO) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(" Start downloading images syncTypes : " + requestDBDTO.getSyncTypes() + DOWNLOAD_URL + requestDBDTO.getDownloadURL() + QUERY + requestDBDTO.getQuery());
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        InputStream inputStream;
        Integer status = OUT_STATUS;
        String query = requestDBDTO.getProcedureName();
        String path = requestDBDTO.getDownloadURL();
        Timestamp lastRunTime = requestDBDTO.getLastRunTime();
        int index = 1;

        Map<String, InputStream> results = new HashMap<String, InputStream>();
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(index, lastRunTime, Calendar.getInstance());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                inputStream = rs.getBlob(2).getBinaryStream();
                results.put(rs.getString(1), inputStream);
            }
            for (Map.Entry<String, InputStream> entry : results.entrySet()) {
                InputStream logoContent = entry.getValue();

                FileOutputStream fos = new FileOutputStream(path + entry.getKey() + IMAGE_TYPE);
                int b = 0;
                while ((b = logoContent.read()) != -1) {
                    fos.write(b);
                }
                fos.flush();
                fos.close();
                status++;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Completed downloading images |Download Count :" + status);
            }
        } catch (IOException ex) {
            status = IConstants.UpdateStatus.UPDATE_FAILED;
            LOG.error(ex.getMessage(), ex);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } catch (Exception sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Completed downloading images " + DOWNLOAD_URL + requestDBDTO.getDownloadURL() + QUERY + requestDBDTO.getQuery());
            }
        }
        return status;
    }

    /**
     * Delete saved images that deleted at backend
     *
     * @param requestDBDTO request object
     * @return number of deleted images
     */
    private Integer deleteSavedImages(RequestDBDTO requestDBDTO) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(" Start deleting images syncTypes : " + requestDBDTO.getSyncTypes() + DOWNLOAD_URL + requestDBDTO.getDownloadURL() + QUERY + requestDBDTO.getQuery());
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Integer status = OUT_STATUS;
        String query = requestDBDTO.getQuery();
        String path = requestDBDTO.getDownloadURL();
        Timestamp lastRunTime = requestDBDTO.getLastRunTime();
        int index = 1;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            if (requestDBDTO.isLastRunTimeRequired()) {
                preparedStatement.setTimestamp(index, lastRunTime, Calendar.getInstance());
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String filePath = path + rs.getString(1) + IMAGE_TYPE;
                File logo = new File(filePath);

                if (logo.exists()) {
                    if (logo.delete()) {
                        status++;
                    } else {
                        LOG.error("Error occurred when deleting " + filePath);
                    }
                }
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } catch (Exception sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = IConstants.UpdateStatus.UPDATE_FAILED;
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Completed deleting images" + DOWNLOAD_URL + requestDBDTO.getDownloadURL() + QUERY + requestDBDTO.getQuery());
            }
        }
        return status;
    }

    private List<IndexDTO> getAllIndices(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<IndexDTO> indexDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }

            rs = preparedStatement.executeQuery();
            indexDTOList = new ArrayList<IndexDTO>(TICKER_LIST_SIZE);
            while (rs.next()) {
                indexDTOList.add(IndexTickerDBHelper.getIndexDTO(rs, requestDBDTO.getSupportedLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return indexDTOList;
    }

    private List<com.dfn.alerts.beans.tickers.IndexDTO> getAllSnapUpdatedIndices(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<com.dfn.alerts.beans.tickers.IndexDTO> indexDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }

            rs = preparedStatement.executeQuery();
            indexDTOList = new ArrayList<com.dfn.alerts.beans.tickers.IndexDTO>(TICKER_LIST_SIZE);
            while (rs.next()) {
                indexDTOList.add(IndexTickerDBHelper.getIndexSnapDTO(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return indexDTOList;
    }

    private List<TickerDTO> getAllTickerData(RequestDBDTO requestDBDTO, IConstants.AssetType type) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<TickerDTO> tickerList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }

            rs = preparedStatement.executeQuery();
            tickerList = new ArrayList<TickerDTO>(TICKER_LIST_SIZE);
            while (rs.next()) {
                tickerList.add(DBUtils.getTickerData(rs, type, requestDBDTO.getSupportedLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerList;
    }

    private List<CountryTickerSnapshotDTO> getAllCountryTickers(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<CountryTickerSnapshotDTO> tickerList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));

            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }

            rs = preparedStatement.executeQuery();
            tickerList = new ArrayList<CountryTickerSnapshotDTO>(1000);
            while (rs.next()) {
                tickerList.add(DBUtils.getCountryTickerSnapshot(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerList;
    }

    /**
     * Execute procedure according to the Request DB DTO data
     *
     * @param requestDBDTO method name
     * @return Result count *
     */
    private Integer executeProcedure(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        CallableStatement cbStatement = null;
        Integer status = OUT_STATUS;
        try {
            connection = driverManagerDataSource.getConnection();
            cbStatement = connection.prepareCall(SP_PROCE_PREFIX + requestDBDTO.getProcedureName() + SP_PROCE_POSTFIX);
            cbStatement.registerOutParameter(SP_OUT_PARA, Types.INTEGER);
            SystemDBUtils.setProcedureParams(requestDBDTO.getSyncTypes(), requestDBDTO, cbStatement);
            cbStatement.executeUpdate();
            status = cbStatement.getInt(SP_OUT_PARA);
            LOG.info("<!--Procedure!Params--> P:" + requestDBDTO.getProcedureName() + "~ T:" + requestDBDTO.getTableName() + "~ S:" + requestDBDTO.getSourceId() + "~ GI:" + requestDBDTO.getGicsCode() + "<!--Procedure!Params-->");
            LOG.info(" <!--Procedure--> Executed Procedure : " + requestDBDTO.getProcedureName() + " SUCCESSFULLY with STATUS : " + status.toString() + " <!--Procedure--> ");
        } catch (SQLException sqlE) {
            LOG.error("<!--Procedure!Params--> P:" + requestDBDTO.getProcedureName() + "~ T:" + requestDBDTO.getTableName() + "~ S:" + requestDBDTO.getSourceId() + "~ GI:" + requestDBDTO.getGicsCode() + "<!--Procedure!Params-->");
            LOG.error(" <!--Procedure--> Execution Failed Procedure : " + requestDBDTO.getProcedureName() + "with STATUS :" + status.toString() + " <!--Procedure--> ");
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, cbStatement, null);
        }
        return status;
    }

    /**
     * Search Sources
     *
     * @param requestDBDTO query String
     * @return SourceDTO List *
     */
    private List<SourceDTO> getAllSources(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<SourceDTO> tickerList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            tickerList = new ArrayList<SourceDTO>(LIST_SIZE);
            while (rs.next()) {
                tickerList.add(DBUtils.getSourceData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerList;
    }

    /**
     * Search IPOs
     *
     * @param requestDBDTO query String
     * @return IpoDTO List
     * *
     */
    private List<IpoDTO> getAllIpoData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<IpoDTO> tickerList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            tickerList = new ArrayList<IpoDTO>(LIST_SIZE);
            while (rs.next()) {
                tickerList.add(IpoDBHelper.setIPOData(rs, requestDBDTO.getSupportedLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerList;
    }

    /**
     * Search MAs
     *
     * @param requestDBDTO query String
     * @return MergeAcquireDTO List
     * *
     */
    private List<MergeAcquireDTO> getAllMAData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<MergeAcquireDTO> maList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            maList = new ArrayList<MergeAcquireDTO>(LIST_SIZE);
            while (rs.next()) {
                maList.add(MADBHelper.setMAData(rs,requestDBDTO.getSupportedLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return maList;
    }

    /**
     * Search Company data for MA
     *
     * @param requestDBDTO query String
     * @return MergeAcquireDTO List
     * *
     */
    private Map<Integer, CompanyDTO> getCompanyDataForMA(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<Integer, CompanyDTO> companyDTOMap = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            companyDTOMap = new HashMap<Integer, CompanyDTO>(LIST_SIZE);
            while (rs.next()) {
                MADBHelper.setMACompanyDataTOMap(rs, companyDTOMap);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return companyDTOMap;
    }

    /**
     * method to get MergeAcquireDTO which contains all details for a MA
     * @param requestDBDTO
     * @return : all details available for a MA
     */
    private MergeAcquireDTO selectMADetails(RequestDBDTO requestDBDTO) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        MergeAcquireDTO mergeAcquireDTO = new MergeAcquireDTO();
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setInt(1, Integer.parseInt(requestDBDTO.getParams()));
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                mergeAcquireDTO = MADBHelper.getMADetails(rs, requestDBDTO.getSupportedLang());
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return mergeAcquireDTO;
    }


    /**
     * get fund benchmark data from fund_benchmark
     *
     * @param requestDBDTO query - last update time is not used
     * @return List<FundBenchmarkDTO>
     */
    private List<FundBenchmarkDTO> getFundBenchmarkData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<FundBenchmarkDTO> tickerList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            tickerList = new ArrayList<FundBenchmarkDTO>(LIST_SIZE);
            while (rs.next()) {
                tickerList.add(FundBenchmarkDBHelper.getFundBenchmarks(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerList;
    }

    /**
     * Search KPIs
     *
     * @param requestDBDTO query String
     * @return KpiDTO List
     * *
     */
    private List<KpiDTO> getAllKpiData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<KpiDTO> tickerList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            tickerList = new ArrayList<KpiDTO>(LIST_SIZE);
            while (rs.next()) {
                tickerList.add(KPIDBHelper.setKPIData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerList;
    }

    /**
     * Return list of InvestorTypeValueDTOs
     *
     * @param requestDBDTO query String
     * @return InvestorTypeValueDTO List
     */
    private List<InvestorTypeValueDTO> getInvestorTypeValuesData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<InvestorTypeValueDTO> investorTypeValueDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));

            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }

            rs = preparedStatement.executeQuery();
            investorTypeValueDTOList = new ArrayList<InvestorTypeValueDTO>(LIST_SIZE);
            while (rs.next()) {
                investorTypeValueDTOList.add(DBUtils.setInvestorTypeValuesData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return investorTypeValueDTOList;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        this.driverManagerDataSource = driverManagerDataSource;
    }

    /**
     * @param requestDBDTO
     * @return
     */
    private DataSyncStatusDTO getDataSynchronizationStatus(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        DataSyncStatusDTO dataSyncStatusDTO = null;
        int index = 1;

        if (requestDBDTO != null) {

            try {
                connection = driverManagerDataSource.getConnection();
                preparedStatement = connection.prepareStatement(requestDBDTO.getQuery());
                preparedStatement.setInt(index++, requestDBDTO.getType());
                preparedStatement.setString(index++, requestDBDTO.getId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    dataSyncStatusDTO = DBUtils.getDataSyncStatusData(rs);
                }
            } catch (SQLException sqlE) {
                LOG.error(sqlE.getMessage(), sqlE);
            } finally {
                DBUtils.finalizeDBResources(connection, preparedStatement, rs);
            }
        }
        return dataSyncStatusDTO;
    }

    //--------------------------------------- OWNERSHIP DATA -----------------------------------------------------------

    /**
     * get series definitions for ownership -> market
     *
     * @param requestDBDTO query - last update time is not used
     * @return List<StockOwnershipLimitSeriesDTO>
     */
    private Map<String, OwnershipLimitSeriesDTO> getOwnershipLimitDefinitions(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, OwnershipLimitSeriesDTO> ownershipLimitSeriesDTOs = new HashMap<String, OwnershipLimitSeriesDTO>(4);

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setString(1, requestDBDTO.getParams());
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                OwnershipLimitSeriesDTO ownershipLimitSeriesDTO = OwnershipLimitsDBHelper.getOwnershipLimit(rs);
                ownershipLimitSeriesDTOs.put(String.valueOf(ownershipLimitSeriesDTO.getSeriesId()), ownershipLimitSeriesDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return ownershipLimitSeriesDTOs;
    }

    /**
     * get series definitions for ownership -> stock
     *
     * @param requestDBDTO query
     * @return StockOwnershipDTO
     */
    private Map<String, StockOwnershipLimitSeriesDTO> getStockOwnershipLimitDefinitions(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, StockOwnershipLimitSeriesDTO> stockOwnershipLimitSeriesDTOs = new HashMap<String, StockOwnershipLimitSeriesDTO>(4);

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setInt(1, Integer.parseInt(requestDBDTO.getParams()));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                StockOwnershipLimitSeriesDTO stockOwnershipLimitSeriesDTO = OwnershipLimitsDBHelper.getStockOwnershipSeriesData(rs);
                stockOwnershipLimitSeriesDTOs.put(String.valueOf(stockOwnershipLimitSeriesDTO.getSeriesId()), stockOwnershipLimitSeriesDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return stockOwnershipLimitSeriesDTOs;
    }

    /**
     * get data for market
     *
     * @param requestDBDTO query
     * @return ticker_serial -> column, value
     */
    private Map<String, StockOwnershipDTO> getMarketOwnershipData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, StockOwnershipDTO> dataMap = new HashMap<String, StockOwnershipDTO>();
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setString(1, requestDBDTO.getParams());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                OwnershipLimitsDBHelper.getMarketStockOwnership(rs, dataMap);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return dataMap;
    }

    /**
     * load stock ownership history
     *
     * @param requestDBDTO query
     * @return date -> series_id, value
     */
    private StockOwnershipDTO getStockOwnershipHistory(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        StockOwnershipDTO stockOwnershipDTO = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setLong(1, Long.parseLong(requestDBDTO.getParams()));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                stockOwnershipDTO = OwnershipLimitsDBHelper.getStockOwnershipOnly(rs, stockOwnershipDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return stockOwnershipDTO;
    }

    //region FUND INVESTMENT ALLOCATION

    /**
     * get all fund investment allocations for fund
     *
     * @param requestDBDTO param - fund ticker serial
     * @return FundInvestment
     */
    private FundInvestment getFundInvestmentAllocations(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        FundInvestment fundInvestmentData = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setInt(1, Integer.parseInt(requestDBDTO.getParams()));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            fundInvestmentData = DBUtils.getFundInvestmentAllocation(rs);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return fundInvestmentData;
    }

    /**
     * get all fund investment allocations for fund
     *
     * @param requestDBDTO param - fund ticker serial
     * @return FundInvestment
     */
    private Map<String, List<FundInvestmentAllocationDTO>> getCurrentFundInvestmentAllocations(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, List<FundInvestmentAllocationDTO>> fundInvestmentData = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            fundInvestmentData = new HashMap<String, List<FundInvestmentAllocationDTO>>();
            while (rs.next()) {
                FundInvestmentAllocationDTO dto = DBUtils.getFundInvestmentAllocationDTO(rs);
                String key = String.valueOf(dto.getFundTickerSerial());
                if (!fundInvestmentData.containsKey(key)) {
                    fundInvestmentData.put(key, new ArrayList<FundInvestmentAllocationDTO>());
                }
                fundInvestmentData.get(key).add(dto);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return fundInvestmentData;
    }

    /**
     * get all stock investments for stock - fund positions
     *
     * @param requestDBDTO query
     * @return Map
     */
    private FundPositionsDTO getFundPositions(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        FundPositionsDTO fundPositions = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            fundPositions = FundPositionDBHelper.getFundPositions(rs, requestDBDTO.getParams());
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return fundPositions;
    }

    /**
     * get all stock investments for stock - fund positions for max period
     *
     * @param requestDBDTO query
     * @return Map
     */
    private List<PriceSnapshotAdjustedDTO> searchAllPriceSnapshotData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<PriceSnapshotAdjustedDTO> snapshotAdjustedDTOList = null;
        PriceSnapshotAdjustedDTO snapshotAdjustedDTO;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            snapshotAdjustedDTOList = new ArrayList<PriceSnapshotAdjustedDTO>();
            while (rs.next()) {
                snapshotAdjustedDTO = DBUtils.getAdjustedPriceSnapshotData(rs);
                snapshotAdjustedDTOList.add(snapshotAdjustedDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return snapshotAdjustedDTOList;
    }

    /**
     * get all stock investments for stock - fund positions for max period
     *
     * @param requestDBDTO query
     * @return Map
     */
    private FundPositionsDTO getFundPositionsForMax(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        FundPositionsDTO fundPositions = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            fundPositions = FundPositionDBHelper.getFundPositionsForMax(rs, requestDBDTO.getParams());
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return fundPositions;
    }

    /**
     * get all stock investments for stock - fund positions for max period
     *
     * @param requestDBDTO query
     * @return Map
     */
    private Map<Integer, Map<String, FundPositionItem>> getFundPositionsForTable(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<Integer, Map<String, FundPositionItem>> fundInvestments = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            fundInvestments = new HashMap<Integer, Map<String, FundPositionItem>>(2);
            while (rs.next()) {
                int rank = rs.getInt(DBConstants.DatabaseColumns.RANK);
                String fundTickerSerial = rs.getString(DBConstants.DatabaseColumns.FUND_TICKER_SERIAL);
                if (!fundInvestments.containsKey(rank)) {
                    fundInvestments.put(rank, new HashMap<String, FundPositionItem>());
                }
                fundInvestments.get(rank).put(fundTickerSerial, FundPositionDBHelper.getFundPositionItem(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return fundInvestments;
    }

    /**
     * get all fund investment allocations of stock for exchange
     *
     * @param requestDBDTO param - source id
     * @return MarketFundInvestmentsDTO
     */
    private MarketFundInvestmentsDTO getFundInvestmentsByMarket(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        MarketFundInvestmentsDTO marketFundInvestmentsDTO = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setString(1, requestDBDTO.getParams());
            rs = preparedStatement.executeQuery();
            marketFundInvestmentsDTO = DBUtils.getFundInvestmentsByMarket(rs, requestDBDTO.getPreferredLang());
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return marketFundInvestmentsDTO;
    }

    //endregion

    private Object searchAllSectionsNews(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        NewsHeadLines newsHeadLines = null;
        List<Map<String, String>> newsList = null;
        Map<String, String> newsItem;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            newsList = new ArrayList<Map<String, String>>();
            while (rs.next()) {
                newsItem = DBUtils.setNewsSectionItem(rs);  //set news items attribute/data map
                newsList.add(newsItem);
            }
            newsHeadLines = new NewsHeadLines();
            newsHeadLines.setNews(newsList);
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return newsHeadLines;
    }

    private Map<String, String> getSourceTimeZoneOffSet(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, String> sourceTimeZoneOffSetMap = null;
        String correctedTimeZoneOffset;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            sourceTimeZoneOffSetMap = new HashMap<String, String>();
            while (rs.next()) {
                correctedTimeZoneOffset = CommonUtils.getCorrectedTimezoneOffset(rs.getString(DBConstants.DatabaseColumns.TIMEZONE_OFFSET), rs.getString(DBConstants.DatabaseColumns.ADJUSTED_OFFSET));
                sourceTimeZoneOffSetMap.put(rs.getString(DBConstants.DatabaseColumns.SOURCE_ID), correctedTimeZoneOffset);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return sourceTimeZoneOffSetMap;
    }

    /**
     * Method to get List of CountryDTOs from oracle DB
     *
     * @param requestDBDTO
     * @return
     */
    private List<CountryDTO> getCountryData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<CountryDTO> countryDTOList = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            CountryDTO countryDTO = null;
            countryDTOList = new ArrayList<CountryDTO>();
            while (rs.next()) {
                countryDTO = new CountryDTO();
                CountryDBHelper.setCountryData(countryDTO, rs, requestDBDTO.getSupportedLang());
                countryDTOList.add(countryDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return countryDTOList;
    }


    //region DCMS

    //---------------------DCMS region start------------------------------------

    /**
     * Search Doc File data
     *
     * @param requestDBDTO query String
     * @return DocFileDTO List
     * *
     */
    private List<DocFileDTO> getAllDocFileData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocFileDTO> docFileDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docFileDTOList = new ArrayList<DocFileDTO>(LIST_SIZE);

            while (rs.next()) {
                docFileDTOList.add(DBUtils.setDocFileData(rs, requestDBDTO.getSupportedLang()));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docFileDTOList;
    }

    private List<DocCompaniesDTO> getAllDocCompanyData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocCompaniesDTO> docCompaniesDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docCompaniesDTOList = new ArrayList<DocCompaniesDTO>(LIST_SIZE);

            while (rs.next()) {
                docCompaniesDTOList.add(DBUtils.setDocCompanyData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docCompaniesDTOList;
    }

    private List<DocCountriesDTO> getAllDocCountryData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocCountriesDTO> docCountriesDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docCountriesDTOList = new ArrayList<DocCountriesDTO>(LIST_SIZE);

            while (rs.next()) {
                docCountriesDTOList.add(DBUtils.setDocCountryData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docCountriesDTOList;
    }

    private List<DocExchangesDTO> getAllDocExchangeData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocExchangesDTO> docExchangesDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docExchangesDTOList = new ArrayList<DocExchangesDTO>(LIST_SIZE);

            while (rs.next()) {
                docExchangesDTOList.add(DBUtils.setDocExchangeData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docExchangesDTOList;
    }

    private List<DocIndustriesDTO> getAllDocIndustryData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocIndustriesDTO> docIndustriesDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docIndustriesDTOList = new ArrayList<DocIndustriesDTO>(LIST_SIZE);

            while (rs.next()) {
                docIndustriesDTOList.add(DBUtils.setDocIndustryData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docIndustriesDTOList;
    }

    private List<DocPeriodsDTO> getAllDocPeriodData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocPeriodsDTO> docPeriodsDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docPeriodsDTOList = new ArrayList<DocPeriodsDTO>(LIST_SIZE);

            while (rs.next()) {
                docPeriodsDTOList.add(DBUtils.setDocPeriodData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docPeriodsDTOList;
    }

    private List<DocRegionsDTO> getAllDocRegionData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocRegionsDTO> docRegionsDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docRegionsDTOList = new ArrayList<DocRegionsDTO>(LIST_SIZE);

            while (rs.next()) {
                docRegionsDTOList.add(DBUtils.setDocRegionData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docRegionsDTOList;
    }

    private List<DocSymbolsDTO> getAllDocSymbolData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocSymbolsDTO> docSymbolsDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docSymbolsDTOList = new ArrayList<DocSymbolsDTO>(LIST_SIZE);

            while (rs.next()) {
                docSymbolsDTOList.add(DBUtils.setDocSymbolData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docSymbolsDTOList;
    }

    private List<DocSectorsDTO> getAllDocSectorsData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<DocSectorsDTO> docSectorsDTOList = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            docSectorsDTOList = new ArrayList<DocSectorsDTO>(LIST_SIZE);

            while (rs.next()) {
                docSectorsDTOList.add(DBUtils.setDocSectorData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return docSectorsDTOList;
    }

    private Object getCurrencySearchData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<CurrencyMasterDataDTO> currencyMasterDataDTOs = null;
        CurrencyMasterDataDTO currencyMasterDataDTO;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            currencyMasterDataDTOs = new ArrayList<CurrencyMasterDataDTO>();

            while (rs.next()) {
                currencyMasterDataDTO = new CurrencyMasterDataDTO();
                DBUtils.setCurrencyMasterData(currencyMasterDataDTO, rs, requestDBDTO.getSupportedLang());
                currencyMasterDataDTOs.add(currencyMasterDataDTO);
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return currencyMasterDataDTOs;
    }

    //---------------------DCMS region end------------------------------------

    //endregion DCMS

    //region Macro Economy

    /**
     * Load all country indicator group types master data from Oracle DB
     *
     * @param requestDBDTO request object
     * @return
     */
    public Map<String,Map<String, CountryIndicatorGroupDTO>> getCountryIndicatorGroups(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        CountryIndicatorGroupDTO indicatorGroupDTO;
        Map<String, CountryIndicatorGroupDTO> countryIndicatorGroups;
        Map<String, Map<String, CountryIndicatorGroupDTO>> countryIndicatorGroupsByProvider = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            countryIndicatorGroupsByProvider = new LinkedHashMap<String, Map<String, CountryIndicatorGroupDTO>>();

            while (rs.next()) {
                indicatorGroupDTO = new CountryIndicatorGroupDTO();
                MacroEconomyDBHelper.setCountryIndicatorGroupColumnValues(rs, indicatorGroupDTO, requestDBDTO.getSupportedLang());

                if(countryIndicatorGroupsByProvider.containsKey(String.valueOf(indicatorGroupDTO.getProviderId()))){
                    countryIndicatorGroups = countryIndicatorGroupsByProvider.get(String.valueOf(indicatorGroupDTO.getProviderId()));
                    countryIndicatorGroups.put(String.valueOf(indicatorGroupDTO.getIndicatorGroupId()), indicatorGroupDTO);
                } else {
                    countryIndicatorGroups = new LinkedHashMap<String, CountryIndicatorGroupDTO>();
                    countryIndicatorGroups.put(String.valueOf(indicatorGroupDTO.getIndicatorGroupId()), indicatorGroupDTO);
                    countryIndicatorGroupsByProvider.put(String.valueOf(indicatorGroupDTO.getProviderId()),countryIndicatorGroups);
                }
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return countryIndicatorGroupsByProvider;
    }

    /**
     * Load all country indicator types master data from Oracle DB
     *
     * @param requestDBDTO request object
     * @return
     */
    public Map<String, Map<String, CountryIndicatorTypeDTO>> getCountryIndicatorTypes(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        CountryIndicatorTypeDTO indicatorTypeDTO;
        Map<String, CountryIndicatorTypeDTO> countryIndicatorTypeDTOs;
        Map<String, Map<String, CountryIndicatorTypeDTO>> countryIndicatorTypeMap = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            countryIndicatorTypeMap = new HashMap<String, Map<String, CountryIndicatorTypeDTO>>();

            while (rs.next()) {
                indicatorTypeDTO = new CountryIndicatorTypeDTO();
                MacroEconomyDBHelper.setCountryIndicatorTypeColumnValues(rs, indicatorTypeDTO, requestDBDTO.getSupportedLang());
                String indicatorGroupId = String.valueOf(indicatorTypeDTO.getIndicatorGroupId());

                if (!countryIndicatorTypeMap.containsKey(indicatorGroupId)) {
                    countryIndicatorTypeDTOs = new HashMap<String, CountryIndicatorTypeDTO>();
                } else {
                    countryIndicatorTypeDTOs = countryIndicatorTypeMap.get(indicatorGroupId);
                }

                countryIndicatorTypeDTOs.put(indicatorTypeDTO.getIndicatorCode(), indicatorTypeDTO);
                countryIndicatorTypeMap.put(indicatorGroupId, countryIndicatorTypeDTOs);
            }

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return countryIndicatorTypeMap;
    }
    //endregion


    /**
     * get key value data
     *
     * @param requestDBDTO query
     * @return Map <key, value>
     */
    private Map<String, String> getKeyValuePairs(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, String> keyValueMap = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            if (requestDBDTO.isLastRunTimeRequired()) {
                SystemDBUtils.setLasRunTimeValue(preparedStatement, requestDBDTO.getLastRunTime(), 1);
            }
            rs = preparedStatement.executeQuery();
            keyValueMap = new HashMap<String, String>();
            while (rs.next()) {
                keyValueMap.put(rs.getString(DBConstants.DatabaseColumns.KEY), rs.getString(DBConstants.DatabaseColumns.VALUE));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return keyValueMap;
    }

    /**
     * Load all ticker class data
     *
     * @param requestDBDTO request object
     * @return all ticker classes
     */
    private List<TickerClassLevels> getTickerClassifications(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<TickerClassLevels> tickerClassLevelsList = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            tickerClassLevelsList = new ArrayList<TickerClassLevels>();
            while (rs.next()) {
                tickerClassLevelsList.add(MasterDataDbHelper.getTickerClassData(rs));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerClassLevelsList;
    }

    /**
     * Load all classification serials from classification_codes table
     *
     * @param requestDBDTO request object
     * @return GICS CODE vs CLASSIFICATION SERIAL - irrespective of gics level
     */
    private Map<String, Integer> getGicsClassificationSerials(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, Integer> classificationSerialMap = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            classificationSerialMap = new HashMap<String, Integer>();
            while (rs.next()) {
                String gicsCode = rs.getString(DBConstants.DatabaseColumns.CLASSIFICATION_CODE);
                int serial = rs.getInt(DBConstants.DatabaseColumns.CLASSIFICATION_SERIAL);
                classificationSerialMap.put(gicsCode, serial);
            }
        } catch (SQLException sqlE) {
            LOG.error("Error in getGicsClassificationSerials()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return classificationSerialMap;
    }

    private Object getAllCouponDayCountValues(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, CouponDayCountTypeDTO> countDayCountTypes = null;
        CouponDayCountTypeDTO dayCountTypeDTO;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            countDayCountTypes = new HashMap<String, CouponDayCountTypeDTO>();
            while (rs.next()) {
                dayCountTypeDTO = new CouponDayCountTypeDTO();
                MasterDataDbHelper.setCouponDayCountTypeColumnValues(rs, dayCountTypeDTO);
                countDayCountTypes.put(String.valueOf(dayCountTypeDTO.getDayCountId()), dayCountTypeDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return countDayCountTypes;
    }

    private Set<String> getSingleColumnDataSet(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Set<String> data = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            data = new HashSet<String>();
            while (rs.next()) {
                data.add(rs.getString(1));
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return data;
    }

    /**
     * This method return all the fund tickers available countries
     *
     * @param requestDBDTO
     * @return
     */
    private Set<String> getFundTickerCountryList(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Set<String> countrySet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", IConstants.EMPTY));
            rs = preparedStatement.executeQuery();
            countrySet = new HashSet<String>();
            while (rs.next()) {
                if (rs.getString(DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE).contains(IConstants.COMMA)) {
                    countrySet.addAll(Arrays.asList(rs.getString(DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE).split(IConstants.COMMA)));
                } else {
                    countrySet.add(rs.getString(DBConstants.DatabaseColumns.TICKER_COUNTRY_CODE));
                }
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }

        if (!countrySet.isEmpty() && countrySet.contains(IConstants.EMPTY)) {
            countrySet.remove(IConstants.EMPTY);
        }
        return countrySet;
    }

    /**
     * set is_mf=1 for the country list
     *
     * @return
     */
    private boolean updateFundTickerCountryList(Set<String> countryCodes) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean status = true;
        String countryList = "";

        try {
            connection = driverManagerDataSource.getConnection();
            countryList = DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                    StringUtils.join(countryCodes, DBConstants.CommonDatabaseParams.QUERY_QUOTE + DBConstants.SQL_COMMA + DBConstants.CommonDatabaseParams.QUERY_QUOTE) +
                    DBConstants.CommonDatabaseParams.QUERY_QUOTE;
            String query = QUERY_UPDATE_FUND_TICKER_COUNTRIES + countryList + DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.executeQuery();
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Updated fund ticker countries");
            }
            query = QUERY_UPDATE_NON_FUND_TICKER_COUNTRIES + countryList + DBConstants.CommonDatabaseParams.SQL_BRACKET_CLOSE;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.executeQuery();
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
            status = false;
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }
        return status;
    }

    private double getVWAPForTickers(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        double vwap = 0;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                vwap = rs.getDouble(VWAP);
            }

        } catch (SQLException sqlE) {
            LOG.error("getVwapForTickers() ", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return vwap;
    }

    private IpoData selectIPOData(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        IpoDTO ipoDTO;
        IpoData ipoData = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();

            ipoData = new IpoData();
            while (rs.next()) {
                ipoDTO = IpoDBHelper.setIPOData(rs, requestDBDTO.getSupportedLang());
                ipoData.addIPO(ipoDTO);
            }

        } catch (SQLException sqlE) {
            LOG.error("selectIPOData()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return ipoData;
    }

    /**
     * get record count
     *
     * @param sql query String
     * @return result count
     */
    private int selectRecordCount(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int count = -1;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql.replace("\\", ""));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException sqlE) {
            LOG.error("selectRecordCount()", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return count;
    }

    /**
     * getCompanyAggregatesData
     *
     * @param query
     * @return data map
     */
    public Map<String, Map<String, Map<String, String>>> getCompanyAggregatesData(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, Map<String, Map<String, String>>> companyAggregatesMap = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(query.replace("\\", ""));
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                if (companyAggregatesMap == null) {
                    companyAggregatesMap = new HashMap<String, Map<String, Map<String, String>>>(1);
                }
                DBUtils.setCompanyAggregatesData(rs, companyAggregatesMap);
            }

        } catch (SQLException sqlE) {
            LOG.error("getCompanyAggregatesData() " + sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return companyAggregatesMap;
    }

    /**
     * Search equity tickers
     *
     * @param requestDBDTO  requestDBDTO
     * @return Map - <Integer, EquityTickerDTO> ticker serial vs ticker dto
     */
    private Map<String, EquityTickerDTO> getEquityTickerDTOMap(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String, EquityTickerDTO> tickerMap = null;
        EquityTickerDTO tickerDTO;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            rs = preparedStatement.executeQuery();
            tickerMap = new HashMap<String, EquityTickerDTO>(INITIAL_CAPACITY_TEN);
            while (rs.next()) {
                tickerDTO = (EquityTickerDTO) EquityTickerDBHelper.getEquityTickerData(rs, requestDBDTO.getSupportedLang());
                tickerMap.put(String.valueOf(tickerDTO.getTickerSerial()), tickerDTO);
            }
        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return tickerMap;
    }
}
