package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.CompanyDTO;
import com.dfn.alerts.beans.MergeAcquireDTO;
import com.dfn.alerts.beans.MergeAcquireLangDTO;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.LAST_UPDATED_TIME;
import static com.dfn.alerts.constants.DBConstants.MergeAndAcquireColumns.*;
import static com.dfn.alerts.constants.DBConstants.TablesORACLE.TABLE_MERGING_AND_ACQUISITION;

/**
 * User : Chathurag
 * Date : 5/29/2015.
 * Time : 9.32 AM
 */
public class MADBHelper {
    private static final String STATUS_COMPLETED_MA = "4";

    /**
     * Set Company DTO for MA
     *
     * @param results       ResultSet
     * @param companyDTOMap Map<Integer,CompanyDTO>
     * @throws SQLException
     */
    public static void setMACompanyDataTOMap(ResultSet results, Map<Integer, CompanyDTO> companyDTOMap) throws SQLException {
        CompanyDTO company = new TickerDTO();
        company.setCountryCode(results.getString(DBConstants.DatabaseColumns.COUNTRY_CODE));
        company.setGicsL4Code(results.getString(DBConstants.DatabaseColumns.GICSL4_CODE));
        companyDTOMap.put(results.getInt(DBConstants.DatabaseColumns.COMPANY_ID), company);
    }

    /**
     * Get merge and acquisition DTO
     */
    public static MergeAcquireDTO getMADetails(ResultSet results, List<String> supportedLang) throws SQLException {

        MergeAcquireDTO mergeAcquireDTO = new MergeAcquireDTO();

        mergeAcquireDTO.setMaId(results.getInt(MA_ID));
        mergeAcquireDTO.setTargetCompanies(results.getString(TARGET_COMPANIES));
        mergeAcquireDTO.setTargetCompanyCountryCodes(results.getString(TARGET_COMPANY_COUNTRY_CODES));
        mergeAcquireDTO.setTargetCompanyGicsL4Codes(results.getString(TARGET_COMPANY_GICSL4_CODES));
        mergeAcquireDTO.setPaymentMethodId(results.getObject(PAYMENT_METHOD_ID) != null ? results.getInt(PAYMENT_METHOD_ID) : -1);
        mergeAcquireDTO.setPaidShares(results.getString(PAID_SHARES));
        mergeAcquireDTO.setPaidCash(results.getString(PAID_CASH));
        mergeAcquireDTO.setCurrency(results.getString(CURRENCY));
        mergeAcquireDTO.setDealSize(results.getObject(DEAL_SIZE) != null ? results.getDouble(DEAL_SIZE) : -1.0);
        mergeAcquireDTO.setPreOffAnnDate(results.getDate(PRE_OFF_ANN_DATE));
        mergeAcquireDTO.setOffAnnDate(results.getDate(OFF_ANN_DATE));
        mergeAcquireDTO.setMktAuthAppDate(results.getDate(MKT_AUTH_APP_DATE));
        mergeAcquireDTO.setCompletionDate(results.getDate(COMPLETION_DATE));
        mergeAcquireDTO.setDealDate(results.getDate(DEAL_DATE));
        mergeAcquireDTO.setOwnedBeforePer(results.getObject(OWNED_BEF_PERCENTAGE) != null ? results.getDouble(OWNED_BEF_PERCENTAGE) : -1.0);
        mergeAcquireDTO.setTargetPer(results.getObject(TARGET_PERCENTAGE) != null ? results.getDouble(TARGET_PERCENTAGE) : -1.0);
        mergeAcquireDTO.setOfferedPer(results.getObject(OFFERED_PERCENTAGE) != null ? results.getDouble(OFFERED_PERCENTAGE) : -1.0);
        mergeAcquireDTO.setAcceptedPer(results.getObject(ACCEPTED_PERCENTAGE) != null ? results.getDouble(ACCEPTED_PERCENTAGE) : -1.0);
        mergeAcquireDTO.setNewCompanyId(results.getInt(NEW_COMPANY_ID));
        mergeAcquireDTO.setRelatedNcCaId(results.getInt(RELATED_NC_CA_ID));
        mergeAcquireDTO.setRelatedLoanId(results.getInt(RELATED_LOAN_ID));
        mergeAcquireDTO.setMaStatus(results.getInt(MA_STATUS));
        mergeAcquireDTO.setLastUpdatedTime(results.getDate(LAST_UPDATED_TIME));
        mergeAcquireDTO.setAcquiredCompanies(results.getString(ACQUIRED_COMPANIES));
        mergeAcquireDTO.setAcquiredCompaniesCountryCodes(results.getString(ACQUIRED_COMPANIES_COUNTRY_CODES));
        mergeAcquireDTO.setAcquiredCompaniesGicsL4Codes(results.getString(ACQUIRED_COMPANIES_GICSL4_CODES));
        mergeAcquireDTO.setAcquirerFinancialAdvisers(results.getString(ACQUIRER_FINANCIAL_ADVISERS));
        mergeAcquireDTO.setAcquirerLegalAdvisers(results.getString(ACQUIRER_LEGAL_ADVISERS));
        mergeAcquireDTO.setTargetFinancialAdvisers(results.getString(TARGET_FINANCIAL_ADVISERS));
        mergeAcquireDTO.setTargetLegalAdvisers(results.getString(TARGET_LEGAL_ADVISERS));
        mergeAcquireDTO.setDealTypes(results.getString(DEAL_TYPES));
        mergeAcquireDTO.setAnnouncements(results.getString(ANNOUNCEMENTS));
        mergeAcquireDTO.setNews(results.getString(NEWS));
        mergeAcquireDTO.setLastSyncTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_SYNC_TIME));
        mergeAcquireDTO.setDocuments(results.getString(DOCUMENTS));
        mergeAcquireDTO.setMaTypeId(results.getInt(MA_TYPE_ID));
        mergeAcquireDTO.setRelatedDebtInstruments(results.getString(RELATED_DEBT_INSTRUMENTS));
        mergeAcquireDTO.setStockSymbol(results.getLong(STOCK_SYMBOL));
        mergeAcquireDTO.setStockExchangeRatios(results.getString(STOCK_EXCHANGE_RATIOS));
        mergeAcquireDTO.setTargetCompanies(results.getString(TARGET_COMPANIES));
        mergeAcquireDTO.setRatioPE(results.getObject(DBConstants.MergeAndAcquireColumns.PE_RATIO) != null ? results.getDouble(DBConstants.MergeAndAcquireColumns.PE_RATIO) : -1.0);
        mergeAcquireDTO.setRatioEVSALES(results.getObject(DBConstants.MergeAndAcquireColumns.EV_SALES) != null ? results.getDouble(DBConstants.MergeAndAcquireColumns.EV_SALES) : -1.0);
        mergeAcquireDTO.setRatioEVEBIT(results.getObject(DBConstants.MergeAndAcquireColumns.EV_EBIT) != null ? results.getDouble(DBConstants.MergeAndAcquireColumns.EV_EBIT): -1.0);
        mergeAcquireDTO.setRatioEVEBITDA(results.getObject(DBConstants.MergeAndAcquireColumns.EV_EBITDA) != null ? results.getDouble(DBConstants.MergeAndAcquireColumns.EV_EBITDA): -1.0);
        mergeAcquireDTO.setRatioPB(results.getObject(DBConstants.MergeAndAcquireColumns.PB_RATIO) != null ? results.getDouble(DBConstants.MergeAndAcquireColumns.PB_RATIO): -1.0);

        if (supportedLang != null && supportedLang.size() > 0) {
            Map<String, MergeAcquireLangDTO> mergeAcquireLangDTOMap = new HashMap<String, MergeAcquireLangDTO>(supportedLang.size());
            for (String lang : supportedLang) {
                lang = lang.toUpperCase();

                MergeAcquireLangDTO mergeAcquireLangDTO = new MergeAcquireLangDTO();
                mergeAcquireLangDTO.setStatusDescription(results.getString(STATUS_DESCRIPTION + IConstants.Delimiter.UNDERSCORE + lang));
                mergeAcquireLangDTO.setDealClassification(results.getString(DEAL_CLASSIFICATION + IConstants.Delimiter.UNDERSCORE + lang));
                mergeAcquireLangDTO.setDealTypeDescription(results.getString(DEAL_TYPE_DESCRIPTION + IConstants.Delimiter.UNDERSCORE + lang));
                mergeAcquireLangDTOMap.put(lang, mergeAcquireLangDTO);
            }
            mergeAcquireDTO.setMergeAcquireLangDTOMap(mergeAcquireLangDTOMap);
        }

        return mergeAcquireDTO;
    }

    /**
     *
     * @param results
     * @return
     * @throws SQLException
     */
    public static MergeAcquireDTO setMAData(ResultSet results, List<String> supportedLang) throws SQLException {

        MergeAcquireDTO mergeAcquireDTO = new MergeAcquireDTO();

        mergeAcquireDTO.setMaId(results.getInt(MA_ID));
        mergeAcquireDTO.setTargetCompanies(results.getString(TARGET_COMPANIES));
        mergeAcquireDTO.setTargetCompanyCountryCodes(results.getString(TARGET_COMPANY_COUNTRY_CODES));
        mergeAcquireDTO.setTargetCompanyGicsL4Codes(results.getString(TARGET_COMPANY_GICSL4_CODES));
        mergeAcquireDTO.setPaymentMethodId(results.getInt(PAYMENT_METHOD_ID));
        mergeAcquireDTO.setPaidShares(results.getString(PAID_SHARES));
        mergeAcquireDTO.setPaidCash(results.getString(PAID_CASH));
        mergeAcquireDTO.setCurrency(results.getString(CURRENCY));
        mergeAcquireDTO.setDealSize(results.getDouble(DEAL_SIZE));
        mergeAcquireDTO.setDealSizeUsd(results.getDouble(DEAL_SIZE_USD));
        mergeAcquireDTO.setPreOffAnnDate(results.getDate(PRE_OFF_ANN_DATE));
        mergeAcquireDTO.setOffAnnDate(results.getDate(OFF_ANN_DATE));
        mergeAcquireDTO.setMktAuthAppDate(results.getDate(MKT_AUTH_APP_DATE));
        mergeAcquireDTO.setCompletionDate(results.getDate(COMPLETION_DATE));
        mergeAcquireDTO.setDealDate(results.getDate(DEAL_DATE));
        mergeAcquireDTO.setOwnedBeforePer(results.getDouble(OWNED_BEF_PERCENTAGE));
        mergeAcquireDTO.setTargetPer(results.getDouble(TARGET_PERCENTAGE));
        mergeAcquireDTO.setOfferedPer(results.getDouble(OFFERED_PERCENTAGE));
        mergeAcquireDTO.setAcceptedPer(results.getDouble(ACCEPTED_PERCENTAGE));
        mergeAcquireDTO.setNewCompanyId(results.getInt(NEW_COMPANY_ID));
        mergeAcquireDTO.setRelatedNcCaId(results.getInt(RELATED_NC_CA_ID));
        mergeAcquireDTO.setRelatedLoanId(results.getInt(RELATED_LOAN_ID));
        mergeAcquireDTO.setMaStatus(results.getInt(MA_STATUS));
        mergeAcquireDTO.setStatus(results.getInt(STATUS));
        mergeAcquireDTO.setLastUpdatedTime(results.getDate(LAST_UPDATED_TIME));
        mergeAcquireDTO.setAcquiredCompanies(results.getString(ACQUIRED_COMPANIES));
        mergeAcquireDTO.setAcquiredCompaniesCountryCodes(results.getString(ACQUIRED_COMPANIES_COUNTRY_CODES));
        mergeAcquireDTO.setAcquiredCompaniesGicsL4Codes(results.getString(ACQUIRED_COMPANIES_GICSL4_CODES));
        mergeAcquireDTO.setAcquirerFinancialAdvisers(results.getString(ACQUIRER_FINANCIAL_ADVISERS));
        mergeAcquireDTO.setAcquirerLegalAdvisers(results.getString(ACQUIRER_LEGAL_ADVISERS));
        mergeAcquireDTO.setTargetFinancialAdvisers(results.getString(TARGET_FINANCIAL_ADVISERS));
        mergeAcquireDTO.setTargetLegalAdvisers(results.getString(TARGET_LEGAL_ADVISERS));
        mergeAcquireDTO.setDealTypes(results.getString(DEAL_TYPES));
        mergeAcquireDTO.setAnnouncements(results.getString(ANNOUNCEMENTS));
        mergeAcquireDTO.setNews(results.getString(NEWS));
        mergeAcquireDTO.setDocuments(results.getString(DOCUMENTS));
        mergeAcquireDTO.setLastSyncTime(results.getTimestamp(DBConstants.DatabaseColumns.LAST_SYNC_TIME));
        mergeAcquireDTO.setMaTypeId(results.getInt(MA_TYPE_ID));
        mergeAcquireDTO.setRelatedDebtInstruments(results.getString(RELATED_DEBT_INSTRUMENTS));
        mergeAcquireDTO.setStockSymbol(results.getLong(STOCK_SYMBOL));
        mergeAcquireDTO.setStockExchangeRatios(results.getString(STOCK_EXCHANGE_RATIOS));
        mergeAcquireDTO.setRatioPE(results.getObject(PE_RATIO) != null ? results.getDouble(PE_RATIO) : -1.0);
        mergeAcquireDTO.setRatioEVSALES(results.getObject(EV_SALES) != null ? results.getDouble(EV_SALES) : -1.0);
        mergeAcquireDTO.setRatioEVEBIT(results.getObject(EV_EBIT) != null ? results.getDouble(EV_EBIT): -1.0);
        mergeAcquireDTO.setRatioEVEBITDA(results.getObject(EV_EBITDA) != null ? results.getDouble(EV_EBITDA): -1.0);
        mergeAcquireDTO.setRatioPB(results.getObject(PB_RATIO) != null ? results.getDouble(PB_RATIO): -1.0);

        if (supportedLang != null && supportedLang.size() > 0) {
            Map<String, MergeAcquireLangDTO> mergeAcquireLangDTOMap = new HashMap<String, MergeAcquireLangDTO>(supportedLang.size());
            for (String lang : supportedLang) {
                lang = lang.toUpperCase();

                MergeAcquireLangDTO mergeAcquireLangDTO = new MergeAcquireLangDTO();
                mergeAcquireLangDTO.setStatusDescription(results.getString(STATUS_DESCRIPTION + IConstants.Delimiter.UNDERSCORE + lang));
                mergeAcquireLangDTO.setDealClassification(results.getString(DEAL_CLASSIFICATION + IConstants.Delimiter.UNDERSCORE + lang));
                mergeAcquireLangDTO.setDealTypeDescription(results.getString(DEAL_TYPE_DESCRIPTION + IConstants.Delimiter.UNDERSCORE + lang));
                mergeAcquireLangDTOMap.put(lang, mergeAcquireLangDTO);
            }
            mergeAcquireDTO.setMergeAcquireLangDTOMap(mergeAcquireLangDTOMap);
        }

        return mergeAcquireDTO;
    }

    /**
     * SELECT * FROM MERGING_AND_ACQUISITION
     */
    private static final String QUERY_MERGE_AND_ACQUISITION = QUERY_SELECT_ALL_FROM + TABLE_MERGING_AND_ACQUISITION;

    /**
     * SELECT * FROM MERGING_AND_ACQUISITION WHERE STATUS = 1
     */
    private static final String QUERY_MA_DATA = QUERY_MERGE_AND_ACQUISITION + QUERY_WHERE + QUERY_STATUS_FILTER
            + DBConstants.AND + MA_ID + DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN + IConstants.ZERO;

    public static RequestDBDTO getSearchQuery(String targetCountry, String targetGics, String acquirerCountry, String acquirerGics,
                                           String maStatus, String paymentMethod, String dealFrom, String dealTo,
                                           Integer beginIndex, Integer endIndex, boolean isPagination, String sortOrder,
                                           String sortColumn, List<String> supportedLang, int dbType) {
        String[] stringArr;

        StringBuilder query;
        if(isPagination){
            String rankSql = MessageFormat.format(QUERY_ROW_NUM_OVER_COMPLETE, sortColumn, sortOrder, "RNUM");
            query = new StringBuilder(QUERY_SELECT_ALL_FROM).append(QUERY_BRACKET_OPEN)
                    .append(QUERY_SELECT).append("ma.*").append(QUERY_COMMA).append(rankSql)
                    .append(FROM).append(TABLE_MERGING_AND_ACQUISITION).append(" ma ")
                    .append(QUERY_WHERE).append(QUERY_STATUS_FILTER).append(DBConstants.AND).append(MA_ID)
                    .append(DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN).append(IConstants.ZERO);
        } else {
            query = new StringBuilder(QUERY_MA_DATA);
        }

       /* if (!CommonUtils.isNullOrEmptyString(targetName)) {
            query.append(QUERY_AND).append(TARGET_COMPANIES).append(QUERY_LIKE)
                 .append(QUERY_QUOTE).append(QUERY_PREC)
                 .append(targetName).append(QUERY_PREC).append(QUERY_QUOTE);
        }

        if (!CommonUtils.isNullOrEmptyString(acquirerName)) {
            query.append(QUERY_AND).append(ACQUIRED_COMPANIES).append(QUERY_LIKE)
                    .append(QUERY_QUOTE).append(QUERY_PREC)
                    .append(acquirerName).append(QUERY_PREC).append(QUERY_QUOTE);
        }*/

        if (!CommonUtils.isNullOrEmptyString(dealFrom) && !CommonUtils.isNullOrEmptyString(dealTo)) {
            query.append(DBConstants.AND)
            .append(DEAL_DATE)
            .append(QUERY_BETWEEN)
            .append(DBUtils.getDateFunctionQuery(dealFrom, DBConstants.dateFormat, dbType))
            .append(QUERY_AND)
            .append(DBUtils.getDateFunctionQuery(dealTo, DBConstants.dateFormat, dbType));
        }

        //target company
        if (targetCountry != null && !StringUtils.isBlank(targetCountry)) {
            query.append(QUERY_AND);
            List<String> countries = Arrays.asList(targetCountry.split(CharUtils.toString(IConstants.Delimiter.COMMA)));
            DBUtils.addLikeQueryPhrase(query, TARGET_COMPANY_COUNTRY_CODES, countries,
                    true, false, false, QUERY_OR);
        }
        //MA_STATUS
        DBUtils.addInQueryPhrase(query, MA_STATUS, maStatus, true, true);
        //PAYMENT_METHOD_ID
        DBUtils.addInQueryPhrase(query, PAYMENT_METHOD_ID, paymentMethod, true, true);
        //target gics
        if (targetGics != null && !StringUtils.isBlank(targetGics)) {
            List<String> gics = Arrays.asList(targetGics.split(CharUtils.toString(IConstants.Delimiter.COMMA)));
            query.append(QUERY_AND);
            DBUtils.addLikeQueryPhrase(query, TARGET_COMPANY_GICSL4_CODES, gics,
                    true, false, false, false, QUERY_OR);
        }
        //acquirer company
        if (acquirerCountry != null && !StringUtils.isBlank(acquirerCountry)) {
            query.append(QUERY_AND);
            List<String> countries = Arrays.asList(acquirerCountry.split(CharUtils.toString(IConstants.Delimiter.COMMA)));
            DBUtils.addLikeQueryPhrase(query, ACQUIRED_COMPANIES_COUNTRY_CODES, countries, true, false, false, QUERY_OR);
        }
        //acquirer gics
        if (acquirerGics != null && !StringUtils.isBlank(acquirerGics)) {
            Boolean addOr = false;
            stringArr = acquirerGics.split(CharUtils.toString(IConstants.Delimiter.COMMA));
            query.append(QUERY_AND).append(QUERY_BRACKET_OPEN);
            for (String ag : stringArr) {
                if(addOr){
                    query.append(QUERY_OR);
                }
                DBUtils.addLikeQueryPhrase(query, ACQUIRED_COMPANIES_GICSL4_CODES, QUERY_COMMA + ag, false, false, false, false);
                addOr = true;
            }
            query.append(QUERY_BRACKET_CLOSE);
        }

        if(isPagination){
            query.append(QUERY_BRACKET_CLOSE).append(QUERY_WHERE).append(" RNUM ").append(QUERY_BETWEEN).append(beginIndex).append(QUERY_AND).append(endIndex);
            //add ordering
            query.append(QUERY_ORDER);
            query.append(" RNUM ");
            //add ordering type
            query.append(QUERY_SPACE).append(QUERY_ASC_ORDER);
        }else{
            //add ordering
            query.append(QUERY_ORDER);
            query.append(sortColumn);
            //add ordering type
            query.append(QUERY_SPACE).append(sortOrder);
        }
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(query.toString());
        requestDBDTO.setSupportedLang(supportedLang);
        return requestDBDTO;
    }

    public static final String GET_MA_DETAILS = QUERY_MERGE_AND_ACQUISITION + QUERY_WHERE +
            MA_ID + QUERY_EQUAL + SQL_QUESTION_MARK;

    /**
     * Get Merge and Acquisition Details
     *
     * @param maId Merge and Acquisition Id
     * @return MergeAcquireDTO
     */
    public static RequestDBDTO getMADetailsRequestDBTO(String maId, List<String> supportedLang) {
        RequestDBDTO  requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(GET_MA_DETAILS);
        requestDBDTO.setParams(maId);
        requestDBDTO.setSupportedLang(supportedLang);
        return requestDBDTO;
    }

    /**
     * getSearchQueryForCompletedMADeals
     * @param supportedLang supported languages
     * @return RequestDBDTO
     */
    public static RequestDBDTO getSearchQueryForCompletedMADeals(List<String> supportedLang){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM);
        queryBuilder.append(DBConstants.TablesORACLE.TABLE_MERGING_AND_ACQUISITION);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_WHERE);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.MA_ID);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_GREATER_THAN);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.MA_STATUS);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_EQUAL);
        queryBuilder.append(STATUS_COMPLETED_MA);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.DEAL_SIZE_USD);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.TARGET_FINANCIAL_ADVISERS);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_OR);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.ACQUIRER_FINANCIAL_ADVISERS);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.COMPLETION_DATE);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_AND);
        queryBuilder.append(DBConstants.MergeAndAcquireColumns.CURRENCY);
        queryBuilder.append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);

        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(queryBuilder.toString());
        requestDBDTO.setSupportedLang(supportedLang);
        return requestDBDTO;
    }

}
