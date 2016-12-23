package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.*;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.dao.MasterDataDAO;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.dataaccess.utils.MacroEconomyDBHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * DB Data access class for Fundamental data
 * Created by IntelliJ IDEA.
 * User: Duminda Amarasinghe
 * Date: 3/5/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class JDBCFundamentalDataDAOImpl implements MasterDataDAO {

    private static final Logger log = LogManager.getLogger(JDBCFundamentalDataDAOImpl.class);
    private static final int INDIVIDUAL_RESULT_COUNT = 6;
    private DataSource driverManagerDataSource;


    public List<Object> getAll(int metadataType) {
        return null;
    }

    /**
     * get fundamental data - individuals & news
     *
     * @param controlType control type
     * @param request     query/RequestDBDTO
     * @return object
     */
    public Object get(int controlType, Object request) {
        Object returnObj = null;

        switch (controlType) {
            case IConstants.ControlPathTypes.INDIVIDUAL_SNAPSHOT:
                returnObj = getIndividualSnapshotFromDatabase((String) request);
                break;
            case IConstants.ControlPathTypes.INDIVIDUAL:
                returnObj = getIndividualFromDatabase((String) request);
                break;
            case IConstants.ControlPathTypes.INDIVIDUAL_LAST_UPDATED_DATA:
                returnObj = getLastUpdatedIndividualFromDatabase((String) request);
                break;
            case IConstants.ControlPathTypes.COMPANY_LIST_KPI:
                returnObj = getKPIDefinitionsFromDatabase((String) request);
                break;
            case IConstants.ControlPathTypes.NUMBER_OF_EMPLOYEES_BY_COMPANY_ID:
                returnObj = getKPINumberOfEmployeesFromDB((String) request);
                break;
            case IConstants.ControlPathTypes.INDIVIDUAL_PROFILES_MAP:
                returnObj = getIndividualDetailsMap((String) request);
                break;
            case IConstants.ControlPathTypes.NEWS_SEARCH_MAX_NEWS_DATE:
                RequestDBDTO dbRequest = (RequestDBDTO) request;
                returnObj = getMaxNewsDateInOracle(dbRequest);
                break;
            case IConstants.ControlPathTypes.SEARCH_MAX_ANNOUNCEMENT_DATE:
                RequestDBDTO annSearchRequest = (RequestDBDTO) request;
                returnObj = getMaxAnnouncementDateInOracle(annSearchRequest);
                break;
            case IConstants.ControlPathTypes.COUNTRY_INDICATOR_SEARCH:
                RequestDBDTO indicatorSearchRequest = (RequestDBDTO) request;
                returnObj = getCountryIndicators(indicatorSearchRequest);
                break;
            case IConstants.ControlPathTypes.LATEST_INDIVIDUAL_PROFILES:
                returnObj = getLatestIndividualProfiles((String) request);
                break;
            default:
                break;
        }

        return returnObj;
    }

    public int update(int controlPath, Object sqlQuery, Object data) {
        int returnVal = -1;
        switch (controlPath) {
            case IConstants.ControlPathTypes.NEWS_JDBC_DELETE_ON_UPDATE:
                returnVal = deleteNewsOnImdbUpdate(sqlQuery, (List<Object>) data);
                break;
        }

        return returnVal;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private int deleteNewsOnImdbUpdate(Object sqlQuery, List<Object> data) {
        int status = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Integer> newsIdList = (List<Integer>) data.get(0);
        String lang = (String) data.get(1);
        int index = 1;

        try {
            connection = driverManagerDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlQuery.toString().replace("\\", ""));
            for (int newsId : newsIdList) {
                preparedStatement.setInt(index++, newsId);
            }
            preparedStatement.setString(index, lang);
            status = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlE) {
            if (connection != null) {
                try {
                    connection.rollback();
                    log.error("<!--transaction Rollback Successfully Completed..");
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                    log.error("<!--transaction Rollback Failed..");
                }
            }
            status = 0;
            log.error("<!--SQL exception occurred in deleting news from RDBMS..");
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }

        return status;
    }


    /**
     * Get Indvidialsnapshot DTO
     *
     * @param sqlQuery SQL sqlQuery
     * @return IndividualSnapshotDTO Object *
     */
    private IndividualSnapshotDTO getIndividualSnapshotFromDatabase(String sqlQuery) {
        IndividualSnapshotDTO individualSnapshotDTO = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            individualSnapshotDTO = DBUtils.populateIndividualSnapShot(rs);
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return individualSnapshotDTO;
    }

    /**
     * Get individual data from DB
     *
     * @param sqlQuery SQL sqlQuery
     * @return individualDTO Object
     */
    private List<IndividualDTO> getIndividualFromDatabase(String sqlQuery) {
        List<IndividualDTO> individualDTO = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            individualDTO = new ArrayList<IndividualDTO>(INDIVIDUAL_RESULT_COUNT);
            while (rs.next()) {
                individualDTO.add(DBUtils.getIndividual(rs));
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return individualDTO;
    }

    /**
     * Get individual data from DB
     *
     * @param sqlQuery SQL sqlQuery
     * @return individualDTO Object
     */
    private Map<String, IndividualDTO> getIndividualDetailsMap(String sqlQuery) {
        Map<String, IndividualDTO> individualDTO = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            individualDTO = new HashMap<String, IndividualDTO>(INDIVIDUAL_RESULT_COUNT);
            while (rs.next()) {
                IndividualDTO individual = DBUtils.getIndividual(rs);
                individualDTO.put(individual.getIndividualId().toString(), individual);
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return individualDTO;
    }

    /**
     * get latest updated Individuals for give country
     *
     * @param sqlQuery db query
     * @return IndividualSnapshotDTO  this object contains only lastUpdatedProfiles list
     */
    private IndividualSnapshotDTO getLastUpdatedIndividualFromDatabase(String sqlQuery) {
        IndividualSnapshotDTO individualDTO = new IndividualSnapshotDTO();
        List<Map<String, String>> lastUpdatedProfiles = new ArrayList<Map<String, String>>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lastUpdatedProfiles.add(DBUtils.getLastUpdatedIndividuals((rs)));
            }
            individualDTO.setLastUpdatedProfiles(lastUpdatedProfiles);
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return individualDTO;
    }

    /**
     * get kpi definitions from db
     *
     * @param sqlQuery sqlQuery
     * @return definitions list
     */
    private Map<String, Map<String, String>> getKPIDefinitionsFromDatabase(String sqlQuery) {
        Map<String, Map<String, String>> definitionsMap = new HashMap<String, Map<String, String>>();
        Map<String, String> definition;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                definition = DBUtils.getKPIDefinition((rs));
                definitionsMap.put(definition.get(DBConstants.DatabaseColumns.KPI_DEFINITION_ID), definition);
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return definitionsMap;
    }

    /**
     * get kpi definitions from db
     *
     * @param sqlQuery sqlQuery
     * @return definitions list
     */
    private Map<String, String> getKPINumberOfEmployeesFromDB(String sqlQuery) {
        Map<String, String> numberOfEmployees = new HashMap<String, String>(2);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numberOfEmployees.put("YEAR",rs.getString(DBConstants.DatabaseColumns.KPI_YEAR));
                numberOfEmployees.put("VALUE", rs.getString(DBConstants.DatabaseColumns.KPI_VALUE));
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return numberOfEmployees;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        this.driverManagerDataSource = driverManagerDataSource;
    }


    //region NEWS

    /**
     * get max news date in oracle
     *
     * @param requestDBDTO request db dto
     * @return max news date in oracle
     */
    private java.sql.Date getMaxNewsDateInOracle(RequestDBDTO requestDBDTO) {
        java.sql.Date maxDate = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery());
            preparedStatement.setString(1, requestDBDTO.getParams());//language
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                maxDate = rs.getDate(1);//only date requested
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return maxDate;
    }

    //endregion

    //region ANNOUNCEMENTS

    /**
     * get max news date in oracle
     *
     * @param requestDBDTO request db dto
     * @return max news date in oracle
     */
    private java.sql.Date getMaxAnnouncementDateInOracle(RequestDBDTO requestDBDTO) {
        java.sql.Date maxDate = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                maxDate = rs.getDate(1);//only date requested
            }
        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return maxDate;
    }
    //endregion

    //region Country Indicators

    /**
     * Search Country Indicators
     *
     * @param requestDBDTO country indicator search request object
     * @return Grouped Country indicator history object
     */
    private CountryIndicatorsDTO getCountryIndicators(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        CountryIndicatorValuesDTO indicatorValueObj;
        List<Object[]> indicatorHistoryObj;
        Map<Integer, Set<Date>> indicatorDatesMap;
        Map<String, List<Object[]>> countryIndicatorData;
        Map<String, Map<String, List<Object[]>>> countryIndicatorGroupData = null;
        CountryIndicatorsDTO countryIndicatorsDTO = null;
        Set<Date> indicatorDates;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));
            preparedStatement.setString(1, requestDBDTO.getParams());
            rs = preparedStatement.executeQuery();

            countryIndicatorGroupData = new HashMap<String, Map<String, List<Object[]>>>();
            indicatorDatesMap = new HashMap<Integer, Set<Date>>();

            while (rs.next()) {
                indicatorValueObj = new CountryIndicatorValuesDTO();
                MacroEconomyDBHelper.setCountryIndicatorColumnValues(rs, indicatorValueObj);
                String indicatorGroupId = String.valueOf(indicatorValueObj.getIndicatorGroupId());
                String indicatorCode = indicatorValueObj.getIndicatorCode();

                if (!countryIndicatorGroupData.containsKey(indicatorGroupId)) {
                    countryIndicatorData = new HashMap<String, List<Object[]>>();
                } else {
                    countryIndicatorData = countryIndicatorGroupData.get(indicatorGroupId);
                }

                if (!countryIndicatorData.containsKey(indicatorCode)) {
                    indicatorHistoryObj = new ArrayList<Object[]>();
                } else {
                    indicatorHistoryObj = countryIndicatorData.get(indicatorCode);
                }

                indicatorHistoryObj.add(indicatorValueObj.getIndicatorValue());
                countryIndicatorData.put(indicatorCode, indicatorHistoryObj);
                countryIndicatorGroupData.put(indicatorGroupId, countryIndicatorData);

                if (indicatorDatesMap.containsKey(indicatorValueObj.getProviderId())) {
                    indicatorDates = indicatorDatesMap.get(indicatorValueObj.getProviderId());
                    indicatorDates.add(indicatorValueObj.getIndicatorValueDate());
                } else {
                    indicatorDates = new HashSet<Date>();
                    indicatorDates.add(indicatorValueObj.getIndicatorValueDate());
                    indicatorDatesMap.put(indicatorValueObj.getProviderId(), indicatorDates);
                }
            }

            if (countryIndicatorGroupData.size() > 0) {
                countryIndicatorsDTO = new CountryIndicatorsDTO();
                countryIndicatorsDTO.setIndicatorValues(countryIndicatorGroupData);
                countryIndicatorsDTO.setIndicatorDatesMap(indicatorDatesMap);
            }

        } catch (SQLException sqlE) {
            log.error(sqlE.getMessage(), sqlE.getCause());
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return countryIndicatorsDTO;
    }

    /**
     * getLatestIndividualProfiles
     *
     * @param sqlQuery individual profile sql reguest
     * @return individual data
     */
    private List<Map<String, String>> getLatestIndividualProfiles(String sqlQuery) {
        List<Map<String, String>> individualProfiles = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();
            individualProfiles = DBUtils.populateIndividualProfiles(rs);
        } catch (SQLException sqlE) {
            log.error("getLatestIndividualProfiles() ", sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return individualProfiles;
    }

    //endregion
}
