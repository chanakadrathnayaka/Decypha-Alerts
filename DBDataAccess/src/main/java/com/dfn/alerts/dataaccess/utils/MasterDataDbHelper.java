package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.CouponDayCountTypeDTO;
import com.dfn.alerts.constants.DBConstants;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.tickers.TickerClassLevels;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aravindal on 08/07/14.
 */
public class MasterDataDbHelper {

    public static final String QUERY_MARKET = "SELECT * FROM SOURCES";

    public static final String SQL_GET_SUPPORTED_EXCHANGES = "SELECT * FROM SOURCES WHERE SOURCE_ID IN ({0})";

    public static final String SQL_GET_INVESTMENT_FUND_ASSETS = DBConstants.CommonDatabaseParams.QUERY_SELECT +
            DBConstants.DatabaseColumns.FUND_ASSET_ID + DBConstants.CommonDatabaseParams.SQL_AS + DBConstants.DatabaseColumns.KEY + IConstants.Delimiter.COMMA +
            DBConstants.LangSpecificDatabaseColumns.FUND_ASSET_SHORT_DESC + "#lang#" + DBConstants.CommonDatabaseParams.SQL_AS + DBConstants.DatabaseColumns.VALUE +
            DBConstants.CommonDatabaseParams.FROM + DBConstants.TablesORACLE.FUND_ASSET;

    public static final String SQL_GET_INVESTMENT_FUND_SECTORS = DBConstants.CommonDatabaseParams.QUERY_SELECT +
            DBConstants.DatabaseColumns.FUND_SECTOR + DBConstants.CommonDatabaseParams.SQL_AS + DBConstants.DatabaseColumns.KEY + IConstants.Delimiter.COMMA +
            DBConstants.LangSpecificDatabaseColumns.FUND_SECTOR_SHORT_DESC + "#lang#" + DBConstants.CommonDatabaseParams.SQL_AS + DBConstants.DatabaseColumns.VALUE +
            DBConstants.CommonDatabaseParams.FROM + DBConstants.TablesORACLE.FUND_SECTOR;

    public static final String SQL_GET_ALL_COUPON_DAY_COUNT_MASTER = DBConstants.CommonDatabaseParams.QUERY_SELECT_ALL_FROM +
            DBConstants.TablesORACLE.COUPON_DAY_COUNT_MAST;

    /**
     * SELECT CLASSIFICATION_CODE, CLASSIFICATION_SERIAL FROM CLASSIFICATION_CODES
     */
    public static final String SQL_GET_ALL_CLASSIFICATION_SERIALS = DBConstants.CommonDatabaseParams.QUERY_SELECT +
            DBConstants.DatabaseColumns.CLASSIFICATION_CODE + DBConstants.CommonDatabaseParams.QUERY_COMMA + DBConstants.DatabaseColumns.CLASSIFICATION_SERIAL +
            DBConstants.CommonDatabaseParams.FROM + DBConstants.TablesORACLE.TABLE_CLASSIFICATION_CODES;

    public static final String QUERY_TICKER_CLASSIFICATION = "SELECT * FROM TICKER_CLASSIFICATION_LEVELS";

    public static final String QUERY_TICKER_CURRENCY = DBConstants.CommonDatabaseParams.QUERY_SELECT + DBConstants.DatabaseColumns.SOURCE_ID
            + DBConstants.CommonDatabaseParams.QUERY_COMMA + DBConstants.DatabaseColumns.TICKER_ID + DBConstants.CommonDatabaseParams.QUERY_COMMA +
            DBConstants.DatabaseColumns.CURRENCY_ID + DBConstants.CommonDatabaseParams.FROM + DBConstants.TablesIMDB.TABLE_TICKERS;

    /**
     * Set Coupon day count master data column vaues to DTO
     *
     * @param resultSet       result set
     * @param dayCountTypeDTO coupon day count type object
     * @throws SQLException sql exception
     */
    public static void setCouponDayCountTypeColumnValues(ResultSet resultSet,
                                                         CouponDayCountTypeDTO dayCountTypeDTO) throws SQLException {

        dayCountTypeDTO.setDayCountId(resultSet.getInt(DBConstants.DatabaseColumns.COUPON_DAY_COUNT));
        dayCountTypeDTO.setDayCountDesc(resultSet.getString(DBConstants.DatabaseColumns.DAY_COUNT_DESC));
        dayCountTypeDTO.setDayCountValue(resultSet.getDouble(DBConstants.DatabaseColumns.DAY_COUNT_VALUE));
    }

    public static TickerClassLevels getTickerClassData(ResultSet resultSet) throws SQLException {
        int level1 = resultSet.getInt(DBConstants.DatabaseColumns.TICKER_CLASS_L1_ID);
        int level2 = resultSet.getInt(DBConstants.DatabaseColumns.TICKER_CLASS_L2_ID);
        int level3 = resultSet.getInt(DBConstants.DatabaseColumns.TICKER_CLASS_L3_ID);
        String level1Desc = resultSet.getString(DBConstants.DatabaseColumns.TICKER_CLASS_L1_DESC);
        String level2Desc = resultSet.getString(DBConstants.DatabaseColumns.TICKER_CLASS_L2_DESC);
        String level3Desc = resultSet.getString(DBConstants.DatabaseColumns.TICKER_CLASS_L3_DESC);

        return new TickerClassLevels.Builder(level1, level2, level3).level1Description(level1Desc).level2Description(level2Desc).level3Description(level3Desc).build();
    }
}
