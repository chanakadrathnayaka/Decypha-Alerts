package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.CountryDTO;
import com.dfn.alerts.beans.CountryLangDTO;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.constants.DBConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dfn.alerts.constants.DBConstants.DatabaseColumns.*;

/**
 * Created by dushani on 8/26/14.
 */
public class CountryDBHelper {

    private static final String COUNTRY_SELECT = "SELECT * FROM COUNTRY WHERE STATUS=1 ";

    private static final String LOAD_FUND_TICKER_COUNTRY_LIST = "SELECT DISTINCT TICKER_COUNTRY_CODE FROM FUND_TICKERS WHERE STATUS=1 AND TICKER_COUNTRY_CODE IS NOT NULL ";

    public static  String getSelectQuery (){
        return COUNTRY_SELECT;
    }
    public static void setCountryData(CountryDTO countryDTO, ResultSet results, List<String> supportedLanguages) throws SQLException {

        countryDTO.setCountryCode(results.getString(COUNTRY_CODE));
        countryDTO.setRegionIds(results.getString(REGION_IDS));
        countryDTO.setIsMacroData(results.getBoolean(IS_MACRO_DATA));
        countryDTO.setIsPriceData(results.getBoolean(IS_PRICE_DATA));
        countryDTO.setIsIPOData(results.getBoolean(IS_IPO_DATA));
        countryDTO.setCompanyData(results.getBoolean(IS_COMPANY_DATA));
        countryDTO.setIsOtherData(results.getBoolean(IS_OTHER_DATA));
        countryDTO.setEditionControlCountry(results.getBoolean(IS_EC_COUNTRY));
        countryDTO.setIsFIData(results.getBoolean(IS_FI_DATA));
        countryDTO.setIsMFData(results.getBoolean(IS_MF_DATA));
        countryDTO.setStatus(results.getInt(STATUS));
        countryDTO.setIsoCode(results.getString(ISO_CODE));
        if (supportedLanguages != null) {
            setCountryLangDTOMap(countryDTO, results, supportedLanguages);
        }
    }

    private static void setCountryLangDTOMap(CountryDTO countryDTO, ResultSet results, List<String> supportedLang) throws SQLException {
        Map<String, CountryLangDTO> countryLangDTOMap = new HashMap<String, CountryLangDTO>();
        Map<String, String> description = new HashMap<String, String>();

        for (String lang : supportedLang) {
            lang = lang.toUpperCase();
            CountryLangDTO countryLangDTO = new CountryLangDTO();
            countryLangDTO.setShortName(results.getString(DBConstants.LangSpecificDatabaseColumns.COUNTRY_SHORT_NAME + lang));
            countryLangDTO.setFullName(results.getString(DBConstants.LangSpecificDatabaseColumns.COUNTRY_FULL_NAME + lang));

            description.put(lang,countryLangDTO.getShortName());
            countryLangDTOMap.put(lang, countryLangDTO);
        }

        countryDTO.setCountryLangDTOMap(countryLangDTOMap);
        countryDTO.setDescription(description);
    }

    public static RequestDBDTO getFundTickerCountriesQuery() {
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(LOAD_FUND_TICKER_COUNTRY_LIST);
        return requestDBDTO;
    }



}
