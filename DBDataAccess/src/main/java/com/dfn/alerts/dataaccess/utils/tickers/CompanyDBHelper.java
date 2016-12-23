package com.dfn.alerts.dataaccess.utils.tickers;

import com.dfn.alerts.beans.CompanyDTO;
import com.dfn.alerts.beans.CompanyLangDTO;
import com.dfn.alerts.beans.CompanyProfileDTO;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.dfn.alerts.constants.DBConstants.CommonDatabaseParams.*;
import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by udaras on 4/3/2014.
 */
@SuppressWarnings("unused")
public class CompanyDBHelper {

    public static final String UPDATE_PLACEHOLDER = QUERY_EQUAL + SQL_QUESTION_MARK + QUERY_COMMA;

    private static final String[] COMPANY_COLUMNS = new String[]{COMPANY_NAME_LAN, COUNTRY_CODE, GICSL2_LAN, GICSL3_LAN, GICSL4_LAN,
            LISTING_STATUS, GICS_L4_CODE, FY_START_MONTH, FINANCIAL_STMNT_FREQ, TEMPLATE_ID, COMPANY_TYPE, GICSL3_CODE, GICSL2_CODE, COMPANY_CURRENCY_ID,
            PAID_CAPITAL, PAID_CAPITAL_CURRENCY, AUTHORIZED_CAPITAL, AUTHORISED_CAPITAL_CURRENCY, SLA_LEVEL, COMPANY_MARKET_CAP, FULLY_OWN_SUBS, PARTIALY_OWN_SUBS};

    private static final String[] COMPANY_PROFILE_COLUMNS = new String[]{ESTABLISHED_DATE, WEB, EMAIL, PHONE, FAX, CONTACT_PERSON, ADDRESS, CITY_ID};

    private static final String[] LANGUAGE_SPECIFIC_COMPANY_COLUMNS = new String[]{COMPANY_NAME_LAN, COUNTRY_DESC, CITY_SHORT_DESC, TRADING_NAME, GICSL2_LAN, GICSL3_LAN, GICSL4_LAN};
    private static final String LISTED_SYMBOL_FILTER = TICKER_SERIAL + QUERY_GREATER_THAN + 0 + QUERY_AND + QUERY_STATUS_FILTER;
    private static final String UNLISTED_SYMBOL_FILTER =  TICKER_SERIAL + QUERY_EQUAL + 0 +
            QUERY_AND + LISTING_STATUS + QUERY_NOT_IN + QUERY_BRACKET_OPEN +
            IConstants.CompanyListingType.LISTED + IConstants.COMMA + IConstants.CompanyListingType.MULTY_LISTED +
            QUERY_BRACKET_CLOSE;
    private static final String MULTI_LISTED_SYMBOL_FILTER = LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.MULTY_LISTED + QUERY_AND + IS_MAIN_STOCK + QUERY_EQUAL + 1;
    private static final String SINGLE_LISTED_SYMBOL_FILTER = LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.LISTED ;
    private static final String TICKERS_TABLE_FILTER = QUERY_BRACKET_OPEN +
            QUERY_BRACKET_OPEN + UNLISTED_SYMBOL_FILTER + QUERY_BRACKET_CLOSE +
            QUERY_OR + QUERY_BRACKET_OPEN + LISTED_SYMBOL_FILTER + QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE;
    private static final int GICS_CODE_TYPE_3 = 3;
    private static final int GICS_CODE_TYPE_2 = 2;
    private static final String[] COMPANY_SCREENER_COLUMNS = new String[]{COMPANY_ID, COUNTRY_CODE, GICSL3_CODE, CITY_ID, COMPANY_TYPE, WEB, TICKER_ID, SOURCE_ID, LISTING_STATUS, TICKER_SERIAL};
    private static final String[] LANGUAGE_SPECIFIC_COMPANY_SCREENER_COLUMNS = new String[]{COMPANY_NAME_LAN, COUNTRY_DESC, GICSL3_LAN, CITY_SHORT_DESC};
    private static String updateCompanyColumnString;
    private static String insertCompanyColumnString;
    private static String insertCompanyColumnParams;

    public static String getUpdateCompanyColumnString(List<String> supportedLang) {
        if (updateCompanyColumnString == null) {
            StringBuilder updateCompanyStringBuilder = new StringBuilder();
            //join TICKER_COLUMNS with =?,"
            updateCompanyStringBuilder.append(StringUtils.join(COMPANY_PROFILE_COLUMNS, UPDATE_PLACEHOLDER)).append(UPDATE_PLACEHOLDER);
            //join TICKER_COLUMNS with =?,"
            updateCompanyStringBuilder.append(COMPANY_ID).append(UPDATE_PLACEHOLDER);
            updateCompanyStringBuilder.append(StringUtils.join(COMPANY_COLUMNS, UPDATE_PLACEHOLDER)).append(UPDATE_PLACEHOLDER);
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_COMPANY_COLUMNS) {
                    lang = lang.toUpperCase();
                    updateCompanyStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(UPDATE_PLACEHOLDER);
                }
            }
            updateCompanyColumnString = CommonUtils.removeLast(updateCompanyStringBuilder.toString());
        }
        return updateCompanyColumnString;
    }

    public static String getInsertCompanyColumnString(List<String> supportedLang) {
        if (insertCompanyColumnString == null) {
            StringBuilder insertCompanyStringBuilder = new StringBuilder();
            //join TICKER_COLUMNS with ","
            insertCompanyStringBuilder.append(StringUtils.join(COMPANY_PROFILE_COLUMNS, IConstants.Delimiter.COMMA)).append(IConstants.Delimiter.COMMA);
            //join TICKER_COLUMNS with ","
            insertCompanyStringBuilder.append(COMPANY_ID).append(IConstants.Delimiter.COMMA);
            insertCompanyStringBuilder.append(StringUtils.join(COMPANY_COLUMNS, IConstants.Delimiter.COMMA)).append(IConstants.Delimiter.COMMA);
            for (String lang : supportedLang) {
                for (String column : LANGUAGE_SPECIFIC_COMPANY_COLUMNS) {
                    lang = lang.toUpperCase();
                    insertCompanyStringBuilder.append(column).append(IConstants.Delimiter.UNDERSCORE).append(lang).append(IConstants.Delimiter.COMMA);
                }
            }
            insertCompanyColumnString = CommonUtils.removeLast(insertCompanyStringBuilder.toString());
        }
        return insertCompanyColumnString;
    }

    /**
     * plus 1 is for company id
     * @param supportedLang languages
     * @return insert columns
     */
    public static String getCompanyInsertColumnParams(List<String> supportedLang) {
        if (insertCompanyColumnParams == null) {
            int columnCount = (COMPANY_PROFILE_COLUMNS.length + COMPANY_COLUMNS.length + 1) + LANGUAGE_SPECIFIC_COMPANY_COLUMNS.length * supportedLang.size();
            insertCompanyColumnParams = StringUtils.repeat(IConstants.Delimiter.QUESTION_MARK, CharUtils.toString(IConstants.Delimiter.COMMA), columnCount);
        }
        return insertCompanyColumnParams;

    }

    /**
     * company_id > 0 and (
     *   ticker_serial = 0 or (
     *       ticker_serial > 0 and status = 1 and instrument_type_id in (0,60,61,62,64,65,66,67,86,122) and (
     *          listing_status =1 or (listing_status = 2 and is_main_stock = 1)
     *       )
     *   )
     *)
     * @param tickerClasses 61, 62, 63, 64, 65, 66, 67, 68
     * @return filtered query
     */
    public static String getCompanyFilterQuery(String tickerClasses) {
        return getCompanyFilterQuery(tickerClasses, null);
    }

    /**
     * company_id > 0 and (
     *   ticker_serial = 0 or (
     *       ticker_serial > 0 and status = 1 and TICKER_CLASS_L3 in (61, 62, 63, 64, 65, 66, 67, 68) and (
     *          listing_status =1 or (listing_status = 2 and is_main_stock = 1)
     *       )
     *   )
     *)
     * @param tickerClassL3 61, 62, 63, 64, 65, 66, 67, 68
     * @return filtered query
     */
    private static String getCompanyFilterQuery(String tickerClassL3, String[] listingTypes) {
        String filterQuery = COMPANY_ID + QUERY_GREATER_THAN + 0 + QUERY_AND + QUERY_BRACKET_OPEN +
                TICKER_SERIAL + QUERY_EQUAL + 0;

        boolean isPrivateEquity = true;
        if(listingTypes != null){
            for(String listingType : listingTypes){
                if(isPrivateEquity) {
                    Integer l = Integer.parseInt(listingType);
                    switch (l) {
                        case IConstants.CompanyListingType.LISTED:
                        case IConstants.CompanyListingType.MULTY_LISTED:
                            isPrivateEquity = false;
                            break;
                    }
                }
            }
        }else{
            isPrivateEquity = false;
        }
        if(isPrivateEquity){
            filterQuery += QUERY_BRACKET_CLOSE;
        }else{
            filterQuery += QUERY_OR + QUERY_BRACKET_OPEN +
                    TICKER_SERIAL + QUERY_GREATER_THAN + 0 + QUERY_AND + STATUS + QUERY_EQUAL + 1 + QUERY_AND +
                    ((tickerClassL3.contains(CharUtils.toString(IConstants.Delimiter.COMMA))) ? (TICKER_CLASS_L3 + QUERY_IN + QUERY_BRACKET_OPEN + tickerClassL3 + QUERY_BRACKET_CLOSE + QUERY_AND + QUERY_BRACKET_OPEN )
                    :(TICKER_CLASS_L3 + QUERY_EQUAL  + tickerClassL3 + QUERY_AND + QUERY_BRACKET_OPEN ))+
                    LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.LISTED + QUERY_OR + QUERY_BRACKET_OPEN +
                    LISTING_STATUS + QUERY_EQUAL + IConstants.CompanyListingType.MULTY_LISTED + QUERY_AND + IS_MAIN_STOCK + QUERY_EQUAL + 1 + QUERY_BRACKET_CLOSE +
                    QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE + QUERY_BRACKET_CLOSE;
        }
        return filterQuery;
    }

    public static void setCompanyData(CompanyDTO company, ResultSet results, List<String> supportedLanguages) throws SQLException {
        setCompanyProfileData(company, results);
        company.setCompanyId(results.getInt(COMPANY_ID));
        company.setCountryCode(results.getString(COUNTRY_CODE));
        company.setFinancialYearStartMonth(results.getInt(FY_START_MONTH));
        company.setFinancialStFreq(results.getInt(FINANCIAL_STMNT_FREQ));
        company.setCompanyNameLan(results.getString(COMPANY_NAME_LAN));
        company.setGicsL2Lan(results.getString(GICSL2_LAN));
        company.setGicsL3Lan(results.getString(GICSL3_LAN));
        company.setGicsL4Lan(results.getString(GICSL4_LAN));
        company.setGicsL4Code(results.getString(GICSL4_CODE));
        company.setGicsL3Code(results.getString(GICSL3_CODE));
        company.setGicsL2Code(results.getString(GICSL2_CODE));
        company.setListingType(results.getInt(LISTING_STATUS));
        company.setCompanyType(results.getInt(COMPANY_TYPE));
        company.setTemplateId(results.getInt(TEMPLATE_ID));
        company.setDefaultCurrencyId(results.getString(COMPANY_CURRENCY_ID));
        company.setPaidCapital(results.getDouble(PAID_CAPITAL));
        company.setPaidCapitalCurrency(results.getString(PAID_CAPITAL_CURRENCY));
        company.setAuthCapital(results.getDouble(AUTHORIZED_CAPITAL));
        company.setAuthCapitalCurrency(results.getString(AUTHORISED_CAPITAL_CURRENCY));
        company.setSlaLevel(results.getInt(SLA_LEVEL));
        company.setCompanyMarketCap(results.getDouble(COMPANY_MARKET_CAP));
        company.setFullyOwnedSubsidiary(results.getString(FULLY_OWN_SUBS));
        company.setPartiallyOwnedSubsidiary(results.getString(PARTIALY_OWN_SUBS));

        List<String> supportedLang = supportedLanguages;
        if (supportedLang == null) {
            supportedLang = new ArrayList<String>(1);
            supportedLang.add("EN");
        }
        setCompanyLangDTOMap(company, results, supportedLang);
    }

    private static void setCompanyLangDTOMap(CompanyDTO companyDTO, ResultSet results, List<String> supportedLang) throws SQLException {
        Map<String, CompanyLangDTO> companyLangDTOMap = new HashMap<String, CompanyLangDTO>();

        for (String lang : supportedLang) {
            lang = lang.toUpperCase();
            CompanyLangDTO companyLangDTO = new CompanyLangDTO();
            companyLangDTO.setCompanyName(results.getString(COMPANY_NAME_LAN + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTO.setCountryDesc(results.getString(COUNTRY_DESC + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTO.setCityDesc(results.getString(CITY_SHORT_DESC + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTO.setTradingName(results.getString(TRADING_NAME + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTO.setGicsL2Lan(results.getString(GICSL2_LAN + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTO.setGicsL3Lan(results.getString(GICSL3_LAN + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTO.setGicsL4Lan(results.getString(GICSL4_LAN + IConstants.Delimiter.UNDERSCORE + lang));
            companyLangDTOMap.put(lang, companyLangDTO);
        }

        companyDTO.setCompanyLangDTOMap(companyLangDTOMap);
    }

    private static void setCompanyProfileData(CompanyProfileDTO company, ResultSet results) throws SQLException {
        company.setContactPerson(results.getString(CONTACT_PERSON));
        company.setEmail(results.getString(EMAIL));
        company.setEstDate(results.getDate(ESTABLISHED_DATE));
        company.setFax(results.getString(FAX));
        company.setPhone(results.getString(PHONE));
        company.setWeb(results.getString(WEB));
        company.setAddress(results.getString(ADDRESS));
    }

    public static int setUpdateCompanySetValues(PreparedStatement preparedStatement,
                                                TickerDTO ticker, int index, List<String> supportedLanguages) throws SQLException {

        int indexTemp = setUpdateCompanyProfileSetValues(preparedStatement, ticker, index, supportedLanguages);

        preparedStatement.setInt(indexTemp++, ticker.getCompanyId());
        preparedStatement.setString(indexTemp++, ticker.getCompanyNameLan());
        preparedStatement.setString(indexTemp++, ticker.getCountryCode());
        preparedStatement.setString(indexTemp++, ticker.getGicsL2Lan());
        preparedStatement.setString(indexTemp++, ticker.getGicsL3Lan());
        preparedStatement.setString(indexTemp++, ticker.getGicsL4Lan());
        preparedStatement.setInt(indexTemp++, ticker.getListingType());
        preparedStatement.setString(indexTemp++, ticker.getGicsL4Code());
        preparedStatement.setInt(indexTemp++, ticker.getFinancialYearStartMonth());
        preparedStatement.setInt(indexTemp++, ticker.getFinancialStFreq());
        preparedStatement.setInt(indexTemp++, ticker.getTemplateId());
        preparedStatement.setInt(indexTemp++, ticker.getCompanyType());

        if (ticker.getGicsL3Code() != null && !ticker.getGicsL3Code().trim().isEmpty()) {
            preparedStatement.setString(indexTemp++, ticker.getGicsL3Code());
        } else {
            String gicsL3Code = CommonUtils.getGicsCodeFromGicsL4(ticker.getGicsL4Code(), GICS_CODE_TYPE_3);
            preparedStatement.setString(indexTemp++, gicsL3Code);
        }

        if (ticker.getGicsL2Code() != null && !ticker.getGicsL2Code().trim().isEmpty()) {
            preparedStatement.setString(indexTemp++, ticker.getGicsL2Code());
        } else {
            String gicsL2Code = CommonUtils.getGicsCodeFromGicsL4(ticker.getGicsL4Code(), GICS_CODE_TYPE_2);
            preparedStatement.setString(indexTemp++, gicsL2Code);
        }

        preparedStatement.setString(indexTemp++, ticker.getDefaultCurrencyId());
        preparedStatement.setDouble(indexTemp++, ticker.getPaidCapital());
        preparedStatement.setString(indexTemp++, ticker.getPaidCapitalCurrency());
        preparedStatement.setDouble(indexTemp++, ticker.getAuthCapital());
        preparedStatement.setString(indexTemp++, ticker.getAuthCapitalCurrency());
        if (ticker.getSlaLevel() != 0) {
            preparedStatement.setInt(indexTemp++, ticker.getSlaLevel());
        } else {
            preparedStatement.setObject(indexTemp++, null);
        }

        if (ticker.getCompanyMarketCap() != 0) {
            preparedStatement.setDouble(indexTemp++, ticker.getCompanyMarketCap());
        } else {
            preparedStatement.setObject(indexTemp++, null);
        }
        preparedStatement.setString(indexTemp++, ticker.getFullyOwnedSubsidiary());
        preparedStatement.setString(indexTemp++, ticker.getPartiallyOwnedSubsidiary());

        if (supportedLanguages != null) {
            for (String lang : supportedLanguages) {
                if (ticker.getTickerLangDTOMap() != null && ticker.getTickerLangDTOMap().get(lang) != null) {
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getCompanyName());
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getCountryDesc());
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getCityDesc());
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getTradingName());
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getGicsL2Lan());
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getGicsL3Lan());
                    preparedStatement.setString(indexTemp++, ticker.getCompanyLangDTOMap().get(lang).getGicsL4Lan());
                }
            }
        }
        return indexTemp;
    }

    public static int setUpdateCompanyProfileSetValues(PreparedStatement preparedStatement,
                                                       TickerDTO ticker, int index, List<String> supportedLanguages) throws SQLException {
        int indexTemp = index;

        preparedStatement.setDate(indexTemp++, ticker.getEstDate());
        preparedStatement.setString(indexTemp++, ticker.getWeb());
        preparedStatement.setString(indexTemp++, ticker.getEmail());
        preparedStatement.setString(indexTemp++, ticker.getPhone());
        preparedStatement.setString(indexTemp++, ticker.getFax());
        preparedStatement.setString(indexTemp++, ticker.getContactPerson());
        preparedStatement.setString(indexTemp++, ticker.getAddress());
        preparedStatement.setInt(indexTemp++, ticker.getCityId());
        return indexTemp;
    }

    public static String[] getCompanyProfileColumns() {
        return COMPANY_PROFILE_COLUMNS;
    }

    public static String[] getLanguageSpecificCompanyColumns() {
        return LANGUAGE_SPECIFIC_COMPANY_COLUMNS;
    }

    public static String[] getCompanyUpdateColumns() {
        return COMPANY_COLUMNS;
    }

    //region company screener

    /**
     * get company columns
     *
     * @param supportedLanguages supported languages
     * @return ESTABLISHED_DATE, WEB, EMAIL, PHONE_1, FAX_1, CONTACT_PERSON, ADDRESS_1, CITY_ID, COMPANY_ID, COMPANY_NAME_LAN,
     * COUNTRY_CODE,GICS_L2_LAN,GICS_L3_LAN,GICS_L4_LAN,LISTING_STATUS,GICS_L4_CODE,FY_START_MONTH,FINANCIAL_STMNT_FREQ,TEMPLATE_ID,
     * COMPANY_TYPE,GICS_L3_CODE,GICS_L2_CODE,COMPANY_CURRENCY_ID,COMPANY_NAME_LAN_EN,COUNTRY_DESC_EN,CITY_SHORT_DESC_EN,
     * TRADING_NAME_EN,GICS_L2_LAN_EN,GICS_L3_LAN_EN,GICS_L4_LAN_EN
     */
    public static List<String> getAllCompanyColumns(List<String> supportedLanguages) {
        List<String> columns = new ArrayList<String>(Arrays.asList(COMPANY_PROFILE_COLUMNS));
        columns.add(COMPANY_ID);
        columns.addAll(Arrays.asList(COMPANY_COLUMNS));
        for (String lang : supportedLanguages) {
            lang = lang.toUpperCase();
            for (String column : LANGUAGE_SPECIFIC_COMPANY_COLUMNS) {
                columns.add(column + IConstants.Delimiter.UNDERSCORE + lang);
            }
        }
        return columns;
    }

    /**
     * get company search query for tickers table
     * @param companyIdList company id list
     * @param tickerClasses tickerClass l3
     * @param supportedLanguages supported languages
     * @return SELECT ESTABLISHED_DATE,WEB,EMAIL,PHONE_1,FAX_1,CONTACT_PERSON,ADDRESS_1,CITY_ID,COMPANY_ID,COMPANY_NAME_LAN,COUNTRY_CODE,
    GICS_L2_LAN,GICS_L3_LAN,GICS_L4_LAN,LISTING_STATUS,GICS_L4_CODE,FY_START_MONTH,FINANCIAL_STMNT_FREQ,TEMPLATE_ID,COMPANY_TYPE,
    GICS_L3_CODE,GICS_L2_CODE,COMPANY_CURRENCY_ID,PAID_CAPITAL,PAID_CAPITAL_CURRENCY,AUTHORIZED_CAPITAL,AUTHORISED_CAPITAL_CURRENCY,
    COMPANY_NAME_LAN_EN,COUNTRY_DESC_EN,CITY_SHORT_DESC_EN,TRADING_NAME_EN,GICS_L2_LAN_EN,GICS_L3_LAN_EN,GICS_L4_LAN_EN
    FROM TICKERS
    WHERE COMPANY_ID IN  (13097,13163,13153,13048,13048)
    AND COMPANY_ID > 0
    AND  (
    TICKER_SERIAL = 0
    OR  (
    TICKER_SERIAL > 0
    AND STATUS = 1
    AND TICKER_CLASS_L3 IN  (62,64,65,61,63,66,68,67)
    AND  (
    LISTING_STATUS = 1
    OR  (LISTING_STATUS = 2 AND IS_MAIN_STOCK = 1)
    )
    )
    )
     */
    public static String getCompaniesByCompanyIds(Collection<String> companyIdList, String tickerClasses, List<String> supportedLanguages) {
        String query = null;
        String companies = CommonUtils.getCommaSeperatedStringFromList(companyIdList);
        if (!companies.isEmpty()) {
            List<String> companyColumns = getAllCompanyColumns(supportedLanguages);

            String filter = QUERY_WHERE + COMPANY_ID;
            if (companies.indexOf(IConstants.Delimiter.COMMA) > 0) {
                filter += QUERY_IN + QUERY_BRACKET_OPEN + companies + QUERY_BRACKET_CLOSE;
            } else {
                filter += QUERY_EQUAL + companies;
            }
            String selectFrom = QUERY_SELECT + StringUtils.join(companyColumns, IConstants.Delimiter.COMMA) + FROM;
            query = selectFrom + DBConstants.TablesIMDB.TABLE_TICKERS + filter + QUERY_AND + getCompanyFilterQuery(tickerClasses);
        }
        return query;
    }

    /**
     * getLatestUpdatedCompanies
     * @param countries selected countries
     * @param tickerClasses ticker classes l3
     * @return query
     */
    public static String getLatestUpdatedCompanies(String countries, String tickerClasses) {

        String countryList = DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                StringUtils.join(countries.split(CharUtils.toString(IConstants.Delimiter.COMMA)), DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                        DBConstants.SQL_COMMA + DBConstants.CommonDatabaseParams.QUERY_QUOTE) + DBConstants.CommonDatabaseParams.QUERY_QUOTE;

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(QUERY_SELECT_ALL_FROM);
        queryBuilder.append(DBConstants.TablesIMDB.TABLE_TICKERS);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.COUNTRY_CODE);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(countryList);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(getCompanyFilterQuery(tickerClasses));
        queryBuilder.append(QUERY_ORDER);
        queryBuilder.append(LAST_UPDATED_TIME);
        queryBuilder.append(QUERY_DESC_ORDER);
        queryBuilder.append(FETCH_FIRST_SEVEN_ROWS);

        return queryBuilder.toString();
    }

    /**
     * getCompanyCoverage
     *
     * @param countries     countries
     * @param tickerClasses ticker classes l3
     * @param type coverage type
     * @return query
     */
    public static String getCompanyCoverage(String countries, String tickerClasses, IConstants.Coverage type) {
        String selectedColumn;

        switch (type) {
            case COUNTRY:
                selectedColumn = DBConstants.DatabaseColumns.COUNTRY_CODE;
                break;
            case INDUSTRY:
                selectedColumn = QUERY_SUBSTR_FUNC + QUERY_BRACKET_OPEN + GICSL4_CODE + IConstants.COMMA + IConstants.NUM_1
                        + IConstants.COMMA + IConstants.NUM_2 + QUERY_BRACKET_CLOSE;
                break;
            default:
                selectedColumn = IConstants.EMPTY;
                break;
        }
        String countryList = DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                StringUtils.join(countries.split(IConstants.COMMA), DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                        DBConstants.SQL_COMMA + DBConstants.CommonDatabaseParams.QUERY_QUOTE) + DBConstants.CommonDatabaseParams.QUERY_QUOTE;
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_SELECT);
        queryBuilder.append(selectedColumn);
        queryBuilder.append(IConstants.SPACE);
        queryBuilder.append(CODE);
        queryBuilder.append(IConstants.COMMA);
        queryBuilder.append(QUERY_COUNT);
        queryBuilder.append(TOTAL);
        queryBuilder.append(IConstants.COMMA);
        queryBuilder.append(SUM_PRIVATE_COUNT);
        queryBuilder.append(IConstants.COMMA);
        queryBuilder.append(SUM_PUBLIC_COUNT);
        queryBuilder.append(FROM);
        queryBuilder.append(DBConstants.TablesIMDB.TABLE_TICKERS);
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.COUNTRY_CODE);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(countryList);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        if (type.equals(IConstants.Coverage.INDUSTRY)) {
            queryBuilder.append(GICS_L4_CODE);
            queryBuilder.append(QUERY_NOT_NULL);
            queryBuilder.append(QUERY_AND);
        }
        queryBuilder.append(getCompanyFilterQuery(tickerClasses));
        queryBuilder.append(QUERY_GROUP_BY);
        queryBuilder.append(selectedColumn);

        return queryBuilder.toString();
    }

    /**
     * getCompanyAggregates
     *
     * @param tickerClasses ticker class l3
     * @return query
     */
    public static String getCompanyAggregates(String tickerClasses, String column, String countries) {
        String countryList = DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                StringUtils.join(countries.split(IConstants.COMMA), DBConstants.CommonDatabaseParams.QUERY_QUOTE +
                        DBConstants.SQL_COMMA + DBConstants.CommonDatabaseParams.QUERY_QUOTE) + DBConstants.CommonDatabaseParams.QUERY_QUOTE;

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_SELECT);
        queryBuilder.append(SUB_STRING_SECTOR_COUNTRY);
        queryBuilder.append(IConstants.COMMA);
        queryBuilder.append(getCompanyAggregatesSummation(column));
        queryBuilder.append(FROM);
        if (column.equalsIgnoreCase(IConstants.CompanyAggregatesTypes.MCAP.getDefaultValue())) {
            queryBuilder.append(DBConstants.TablesIMDB.TABLE_TICKERS);
        } else {
            queryBuilder.append(DBConstants.TablesORACLE.TABLE_COMPANY_SIZE);
            queryBuilder.append(DBConstants.DatabaseAlias.COMPANY_SIZE_ALIAS);
            queryBuilder.append(QUERY_INNER_JOIN);
            queryBuilder.append(DBConstants.TablesIMDB.TABLE_TICKERS);
            queryBuilder.append(DBConstants.DatabaseAlias.TICKERS_ALIAS);
            queryBuilder.append(QUERY_ON);
            queryBuilder.append(DBConstants.DatabaseAlias.COMPANY_SIZE_ALIAS).append(QUERY_DOT);
            queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
            queryBuilder.append(QUERY_EQUAL);
            queryBuilder.append(DBConstants.DatabaseAlias.TICKERS_ALIAS).append(QUERY_DOT);
            queryBuilder.append(DBConstants.DatabaseColumns.COMPANY_ID);
        }
        queryBuilder.append(QUERY_WHERE);
        queryBuilder.append(DBConstants.DatabaseColumns.COUNTRY_CODE);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(countryList);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        if (column.equalsIgnoreCase(IConstants.CompanyAggregatesTypes.MCAP.getDefaultValue())) {
            queryBuilder.append(getCompanyFilterQuery(tickerClasses));
        } else {
            queryBuilder.append(getCompanyFilterQuery(tickerClasses).replace(DBConstants.DatabaseColumns.COMPANY_ID,
                    DBConstants.DatabaseAlias.COMPANY_SIZE_ALIAS + QUERY_DOT + DBConstants.DatabaseColumns.COMPANY_ID));
        }
        queryBuilder.append(QUERY_GROUP_BY);
        queryBuilder.append(SUB_STRING_COUNTRY);

        return queryBuilder.toString();
    }

    /**
     * getCompanyAggregatesSummation
     *
     * @param column
     * @return
     */
    public static String getCompanyAggregatesSummation(String column) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(QUERY_SUM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_CASE);
        queryBuilder.append(QUERY_WHEN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.LISTING_STATUS);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(IConstants.CompanyListingType.LISTED);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(IConstants.CompanyListingType.MULTY_LISTED);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(IConstants.CompanyListingType.UNLISTED);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_THEN);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_ELSE);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_END);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.DatabaseColumns.TOTAL);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(QUERY_SUM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_CASE);
        queryBuilder.append(QUERY_WHEN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.LISTING_STATUS);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(IConstants.CompanyListingType.UNLISTED);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_THEN);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_ELSE);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_END);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.DatabaseColumns.PRIVATE_COUNT);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(QUERY_SUM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_CASE);
        queryBuilder.append(QUERY_WHEN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.LISTING_STATUS);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(IConstants.CompanyListingType.LISTED);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(IConstants.CompanyListingType.MULTY_LISTED);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_THEN);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_ELSE);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_END);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.DatabaseColumns.PUBLIC_COUNT);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(QUERY_SUM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_CASE);
        queryBuilder.append(QUERY_WHEN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.LISTING_STATUS);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(IConstants.CompanyListingType.LISTED);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(IConstants.CompanyListingType.MULTY_LISTED);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(IConstants.CompanyListingType.UNLISTED);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_EQUAL);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_THEN);
        queryBuilder.append(IConstants.ONE);
        queryBuilder.append(QUERY_ELSE);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_END);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.DatabaseColumns.TOTAL_COMPANY_COUNT);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(QUERY_SUM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_CASE);
        queryBuilder.append(QUERY_WHEN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.LISTING_STATUS);
        queryBuilder.append(QUERY_EQUAL);
        queryBuilder.append(IConstants.CompanyListingType.UNLISTED);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_EQUAL);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_THEN);
        queryBuilder.append(IConstants.ONE);
        queryBuilder.append(QUERY_ELSE);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_END);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.DatabaseColumns.PRIVATE_COMPANY_COUNT);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(QUERY_SUM);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(QUERY_CASE);
        queryBuilder.append(QUERY_WHEN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(DBConstants.DatabaseColumns.LISTING_STATUS);
        queryBuilder.append(QUERY_IN);
        queryBuilder.append(QUERY_BRACKET_OPEN);
        queryBuilder.append(IConstants.CompanyListingType.LISTED);
        queryBuilder.append(DBConstants.SQL_COMMA);
        queryBuilder.append(IConstants.CompanyListingType.MULTY_LISTED);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_NULL);
        queryBuilder.append(QUERY_AND);
        queryBuilder.append(column);
        queryBuilder.append(QUERY_NOT_EQUAL);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(QUERY_THEN);
        queryBuilder.append(IConstants.ONE);
        queryBuilder.append(QUERY_ELSE);
        queryBuilder.append(IConstants.ZERO);
        queryBuilder.append(QUERY_END);
        queryBuilder.append(QUERY_BRACKET_CLOSE);
        queryBuilder.append(DBConstants.DatabaseColumns.PUBLIC_COMPANY_COUNT);

        return queryBuilder.toString();
    }

    /**
     * columns since it is done after grouping
     * @param supportedLang languages
     * @return query
     */
    public static String getCompanyScreenSelectColumns(List<String> supportedLang){
        StringBuilder select = new StringBuilder();
        for(String column : getCompanyScreenColumns(supportedLang)){
            if(column.equalsIgnoreCase(DBConstants.DatabaseColumns.COMPANY_ID)){
                select.append(QUERY_COMMA).append(column);
            }else{
                select.append(QUERY_COMMA).append(QUERY_MAX).append(QUERY_BRACKET_OPEN).append(column).append(QUERY_BRACKET_CLOSE).append(SQL_AS).append(column);
            }
        }
        return select.substring(1);
    }

    /**
     * company screener columns
     * @param supportedLanguages languages
     * @return columns
     */
    private static List<String> getCompanyScreenColumns(List<String> supportedLanguages) {
        List<String> columns = new ArrayList<String>(Arrays.asList(COMPANY_SCREENER_COLUMNS));
        for (String language : supportedLanguages) {
            for (String col : LANGUAGE_SPECIFIC_COMPANY_SCREENER_COLUMNS) {
                columns.add(col + IConstants.Delimiter.UNDERSCORE + language);
            }
        }
        return columns;
    }

    /**
     * get company dto map of company screening results
     *
     * @param countries         countries
     * @param listingStatus     listingStatus
     * @param industries        industries
     * @param city              city
     * @param companyName       companyName
     * @param language          language
     * @param searchCompanyType isOrdinary
     * @return query
     */
    public static String getCompanyScreenQuery(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                              String city, String companyName, String searchCompanyType, String language, String instrumentTypes) {
        return getCompanyScreenQuery(countries, listingStatus, companyTypes, industries, city, companyName, searchCompanyType, language, null, instrumentTypes);
    }

    /**
     * get company dto map of company screening results
     * will get both EN & AR
     * @param countries                 countries
     * @param listingStatus             listingStatus
     * @param industries                industries
     * @param city                      city
     * @param companyName               companyName
     * @param language                  language
     * @param secondaryLanguage         secondaryLanguage
     * @param searchCompanyType         isOrdinary
     * @return query
     */
    public static String getCompanyScreenQuery(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                               String city, String companyName, String searchCompanyType, String language,
                                               String secondaryLanguage, String instrumentTypes) {
        String lang = language.toUpperCase();
        List<String> supportedLang = new ArrayList<String>(2);
        supportedLang.add(lang);
        if(secondaryLanguage != null) {
            supportedLang.add(secondaryLanguage);
        }
        String filterQuery = getCompanyFilterQuery(countries, listingStatus, companyTypes, industries, city, companyName, language);
        String columns = StringUtils.join(CompanyDBHelper.getCompanyScreenColumns(supportedLang), QUERY_COMMA);
        String selectColumnsFrom = QUERY_SELECT + columns + FROM;

        return selectColumnsFrom + DBConstants.TablesORACLE.TABLE_TICKERS + QUERY_WHERE + getCompanyFilterQuery(instrumentTypes, listingStatus)  + QUERY_AND +  filterQuery;
    }

    /**
     * get query for company count in company screening results
     *
     * @param countries         countries
     * @param listingStatus     listingStatus
     * @param industries        industries
     * @param city              city
     * @param companyName       companyName
     * @param language          language
     * @param searchCompanyType isOrdinary
     * @return query
     */
    public static String getCompanyScreenCountQuery(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                               String city, String companyName, String searchCompanyType, String language, String instrumentTypes) {
        String lang = language.toUpperCase();

        String filterQuery = getCompanyFilterQuery(countries, listingStatus, companyTypes, industries, city, companyName, lang );
        String selectColumnsFrom = QUERY_SELECT + QUERY_COUNT_ALL + FROM;

        return selectColumnsFrom + DBConstants.TablesORACLE.TABLE_TICKERS + QUERY_WHERE + filterQuery + QUERY_AND + getCompanyFilterQuery(instrumentTypes, listingStatus);
    }

    /**
     * Get Counts(*) for all company types
     *
     * @param countries country list
     * @param listingStatus company listing status
     * @param companyTypes company types, null for all
     * @param industries industry list
     * @param city city list
     * @param companyName company name
     * @param language language
     * @param instrumentTypes instrument types
     * @return Map<COMPANY_TYPE,COUNT>
     */
    public static String getCompanyTypeCountQuery(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                                    String city, String companyName, String language, String instrumentTypes) {
        String lang = language.toUpperCase();

        String selectColumnsFrom = QUERY_SELECT + COMPANY_TYPE + DBConstants.SQL_COMMA + QUERY_COUNT_ALL + FROM;
        String filterQuery = getCompanyFilterQuery(countries, listingStatus, companyTypes, industries, city, companyName, lang);

        return selectColumnsFrom + DBConstants.TablesORACLE.TABLE_TICKERS + QUERY_WHERE + getCompanyFilterQuery(instrumentTypes, listingStatus) + QUERY_AND + filterQuery + QUERY_GROUP_BY + COMPANY_TYPE;
    }

    /**
     * get the filters for company screen
     *
     * @param countries     company country
     * @param listingStatus @see com.dfn.alerts.constants.IConstants.CompanyListingType
     * @param companyTypes  @see com.dfn.alerts.constants.IConstants.CompanyTypes
     * @param industries    gics l3
     * @param city          city id
     * @param companyName   company name or trading name
     * @param language      lang
     * @return filter query
     */
    private static String getCompanyFilterQuery(String[] countries, String[] listingStatus, String[] companyTypes, String[] industries,
                                         String city, String companyName, String language ) {
        StringBuilder filter = new StringBuilder();

        if (listingStatus != null && listingStatus.length > 0) {
            DBUtils.addInQueryPhrase(filter, LISTING_STATUS, StringUtils.join(listingStatus, QUERY_COMMA), true, false);
        }

        if (companyTypes != null && companyTypes.length > 0) {
            DBUtils.addInQueryPhrase(filter, COMPANY_TYPE, StringUtils.join(companyTypes, QUERY_COMMA), true, true);
        }

        if (countries != null && countries.length > 0) {
            DBUtils.addInQueryPhrase(filter, COUNTRY_CODE, StringUtils.join(countries, QUERY_COMMA), false, true);
        }

        if (industries != null && industries.length > 0) {
            DBUtils.addInQueryPhrase(filter, GICSL3_CODE, StringUtils.join(industries, QUERY_COMMA), false, true);
        }

        if (city != null && !city.trim().isEmpty()) {
            DBUtils.addEqualQueryPhrase(filter, CITY_ID, city.trim(), true, true);
        }

        //company name not null
        filter.append(QUERY_AND).append(DBConstants.LangSpecificDatabaseColumns.COMPANY_NAME).append(language).append(DBConstants.CommonDatabaseParams.QUERY_NOT_NULL);

        if (companyName != null && !companyName.trim().isEmpty()) {
            filter.append(QUERY_AND).append(QUERY_BRACKET_OPEN).append(QUERY_UPPER).append(QUERY_BRACKET_OPEN)
                    .append(DBConstants.LangSpecificDatabaseColumns.COMPANY_NAME).append(language).append(QUERY_BRACKET_CLOSE)
                    .append(QUERY_LIKE).append(QUERY_QUOTE).append(QUERY_PREC).append(companyName.trim().toUpperCase()).append(QUERY_PREC).append(QUERY_QUOTE)
                    .append(QUERY_OR)
                    .append(QUERY_UPPER).append(QUERY_BRACKET_OPEN).append(DBConstants.LangSpecificDatabaseColumns.TRADING_NAME).append(language).append(QUERY_BRACKET_CLOSE)
                    .append(QUERY_LIKE).append(QUERY_QUOTE).append(QUERY_PREC).append(companyName.trim().toUpperCase()).append(QUERY_PREC).append(QUERY_QUOTE)
                    .append(QUERY_BRACKET_CLOSE);
        }

        return filter.toString();
    }

    /**
     * fill company Screen DTO
     *
     * @param results            result set COMPANY_SCREENER_COLUMNS + LANGUAGE_SPECIFIC_COMPANY_SCREENER_COLUMNS
     * @param supportedLanguages languages
     * @return company dto
     * @throws SQLException
     */
    public static TickerDTO getCompanyScreenerDTO(ResultSet results, List<String> supportedLanguages) throws SQLException {
        TickerDTO company = new TickerDTO();
        company.setCompanyId(results.getInt(COMPANY_ID));
        company.setCountryCode(results.getString(COUNTRY_CODE));
        company.setGicsL3Code(results.getString(GICSL3_CODE));
        company.setCompanyType(results.getInt(COMPANY_TYPE));
        company.setWeb(results.getString(WEB));
        company.setTickerId(results.getString(TICKER_ID));
        company.setSourceId(results.getString(SOURCE_ID));
        company.setListingType(results.getInt(LISTING_STATUS));
        company.setTickerSerial(results.getLong(TICKER_SERIAL));

        if (supportedLanguages != null) {
            Map<String, CompanyLangDTO> companyLangDTOMap = new HashMap<String, CompanyLangDTO>(supportedLanguages.size());
            for (String lang : supportedLanguages) {
                lang = lang.toUpperCase();
                CompanyLangDTO companyLangDTO = new CompanyLangDTO();
                companyLangDTO.setCompanyName(results.getString(COMPANY_NAME_LAN + IConstants.Delimiter.UNDERSCORE + lang));
                companyLangDTO.setCountryDesc(results.getString(COUNTRY_DESC + IConstants.Delimiter.UNDERSCORE + lang));
                companyLangDTO.setCityDesc(results.getString(CITY_SHORT_DESC + IConstants.Delimiter.UNDERSCORE + lang));
                companyLangDTO.setGicsL3Lan(results.getString(GICSL3_LAN + IConstants.Delimiter.UNDERSCORE + lang));
                companyLangDTOMap.put(lang, companyLangDTO);
            }
            company.setCompanyLangDTOMap(companyLangDTOMap);
        }
        return company;
    }

    //endregion
}
