package com.dfn.alerts.constants;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/30/13
 * Time: 9:21 AM
 */
@SuppressWarnings("unused")
public class DBConstants {

    public static final class MetaDataType {

        private MetaDataType() {
        }

        public static final int META_DATA_MARKET = 1;
        public static final int META_DATA_TICKER = 2;
        public static final int META_DATA_CURRENCY_RATES = 3;
        public static final int META_DATA_UPDATE_DATA_SYNC_STATUS = 4;
        public static final int META_DATA_COMPANY = 5;
        public static final int META_DATA_TICKER_BY_COMP_ID = 6;
        public static final int META_DATA_LOAD_IND_IMAGES = 7;
        public static final int META_DATA_LOAD_COM_IMAGES = 8;
        public static final int META_DATA_LOAD_ALL_EQUITY_TICKERS = 9;
        public static final int META_DATA_LOAD_ALL_FUND_TICKERS = 10;
        public static final int META_DATA_LOAD_ALL_FIXED_INCOME_TICKERS = 11;
        public static final int META_DATA_UPDATE_ALL_EQUITY_TICKERS = 12;
        public static final int META_DATA_UPDATE_ALL_FUND_TICKERS = 13;
        public static final int META_DATA_UPDATE_ALL_FIXED_INCOME_TICKERS = 14;
        public static final int META_DATA_LOAD_ALL_SOURCES = 15;
        public static final int META_DATA_UPDATE_ALL_SOURCES = 16;
        public static final int META_DATA_UPDATE_COUNTRY_INDICATORS = 17;
        public static final int META_DATA_LOAD_SRC_IMAGES = 18;
        public static final int META_DATA_EXECUTE_PROCEDURE = 19;
        public static final int META_DATA_LOAD_COUNTRY_IMAGES = 20;
        public static final int META_DATA_LOAD_INDEX_TICKERS = 21;
        public static final int META_DATA_UPDATE_ALL_INDEX_TICKERS = 22;
        public static final int META_DATA_LOAD_ALL_COUNTRY_TICKERS = 23;
        public static final int META_DATA_UPDATE_ALL_COUNTRY_TICKERS = 24;
        public static final int META_DATA_LOAD_ALL_IPO_TICKERS = 25;
        public static final int META_DATA_UPDATE_ALL_IPO_TICKERS = 26;
        public static final int META_DATA_LOAD_ALL_PROD_DB_SYNC_DATA = 27;
        public static final int META_DATA_UPDATE_WEB_REQUEST_DATA = 28;
        public static final int META_DATA_DELETE_WEB_REQUEST_DATA = 29;
        public static final int META_DATA_GET_ALL_FUND_BENCHMARKS = 30;

        public static final int META_DATA_UPDATE_ALL_FUND_BENCHMARKS = 31;
        public static final int META_DATA_LOAD_ALL_MA_DATA = 32;
        public static final int META_DATA_UPDATE_ALL_MA_TICKERS = 33;
        public static final int META_DATA_LOAD_MA_TICKERS = 34;

        public static final int META_DATA_LOAD_ALL_KPI = 35;
        public static final int META_DATA_UPDATE_ALL_KPI = 36;
        public static final int META_DATA_DELETE_TICKERS = 37;

        public static final int META_DATA_OWNERSHIP_SERIES_DEF = 38;
        public static final int META_DATA_MARKET_OWNERSHIP_DATA = 39;
        public static final int META_DATA_MARKET_OWNERSHIP_COMPARATIVE_DATA = 40;
        public static final int META_DATA_STOCK_OWNERSHIP_DEF = 41;
        public static final int META_DATA_STOCK_OWNERSHIP_HISTORY = 42;

        public static final int META_DATA_LOAD_ALL_STOCK_OWNERSHIP_LIMIT_DEFINITIONS = 43;
        public static final int META_DATA_UPDATE_ALL_STOCK_OWNERSHIP_LIMIT_DEFINITIONS = 44;

        public static final int META_DATA_SUPPORTED_CURRENCIES = 45;

        public static final int META_DATA_LOAD_ALL_FUND_INVESTMENT_ALLOCATIONS = 46;

        public static final int META_DATA_LOAD_ALL_DOC_FILE_DATA = 47;
        public static final int META_DATA_LOAD_ALL_DOC_COMPANY_DATA = 48;
        public static final int META_DATA_LOAD_ALL_DOC_COUNTRY_DATA = 49;
        public static final int META_DATA_LOAD_ALL_DOC_EXCHANGE_DATA = 50;
        public static final int META_DATA_LOAD_ALL_DOC_INDUSTRY_DATA = 51;
        public static final int META_DATA_LOAD_ALL_DOC_PERIOD_DATA = 52;
        public static final int META_DATA_LOAD_ALL_DOC_REGION_DATA = 53;
        public static final int META_DATA_LOAD_ALL_DOC_SECTOR_DATA = 54;
        public static final int META_DATA_LOAD_ALL_DOC_SYMBOL_DATA = 55;
        public static final int META_DATA_UPDATE_ALL_DOC_FILE_DATA = 56;
        public static final int META_DATA_UPDATE_ALL_DOC_COMPANY_DATA = 57;
        public static final int META_DATA_UPDATE_ALL_DOC_COUNTRY_DATA = 58;
        public static final int META_DATA_UPDATE_ALL_DOC_EXCHANGE_DATA = 59;
        public static final int META_DATA_UPDATE_ALL_DOC_INDUSTRY_DATA = 60;
        public static final int META_DATA_UPDATE_ALL_DOC_PERIOD_DATA = 61;
        public static final int META_DATA_UPDATE_ALL_DOC_REGION_DATA = 62;
        public static final int META_DATA_UPDATE_ALL_DOC_SECTOR_DATA = 63;
        public static final int META_DATA_UPDATE_ALL_DOC_SYMBOL_DATA = 64;
        public static final int META_DATA_SOURCE_TIME_ZONE_OFFSET = 65;
        public static final int META_DATA_DB_UPDATE_LOG = 66;
        public static final int META_DATA_GET_LAST_EOD_DATA = 67;
        public static final int META_DATA_LOAD_ALL_UNLISTED_COMPANIES = 68;
        public static final int META_DATA_UPDATE_ALL_UNLISTED_COMPANIES = 69;
        public static final int META_DATA_GET_TOP_NEWS_EDITION_DATA = 70;
        public static final int META_DATA_DELETE_COM_IMAGES = 71;
        public static final int META_DATA_DELETE_IND_IMAGES = 72;
        public static final int META_DATA_DELETE_SRC_IMAGES = 73;
        public static final int META_DATA_DELETE_COUNTRY_IMAGES = 74;

        public static final int META_DATA_LOAD_FUND_POSITIONS = 75;
        public static final int META_DATA_LOAD_MAX_FUND_POSITIONS = 76;
        public static final int META_DATA_LOAD_ALL_INDEX_TICKERS = 77;
        public static final int META_DATA_ALL_NEWS_SECTIONS_ORACLE = 78;
        public static final int META_DATA_LOAD_ALL_FUND_INVESTMENTS_BY_MARKET = 79;
        public static final int META_DATA_LOAD_ALL_ADJUSTED_PRICE_SNAPSHOT = 80;
        public static final int META_DATA_UPDATE_ADJUSTED_PRICE_SNAPSHOT = 81;
        public static final int META_DATA_GET_INVESTOR_TYPE_VALUES = 82;
        public static final int META_DATA_CURRENCY_MASTER_DATA = 83;
        public static final int META_DATA_COUNTRY_INDICATOR_TYPES = 84;
        public static final int META_DATA_COUNTRY_INDICATOR_GROUPS = 85;

        public static final int META_DATA_LOAD_CURRENT_FUND_INVESTMENT_ALLOCATIONS = 86;

        public static final int META_DATA_LOAD_KEY_VALUE = 87;
        public static final int META_DATA_LOAD_COUPON_DAY_COUNT = 88;

        public static final int META_DATA_LOAD_FUND_POSITIONS_TABLE = 89;
        public static final int META_DATA_GET_COUNTRY_DATA = 90;

        public static final int META_DATA_GET_SINGLE_COLUMN_DATA_SET = 91;
        public static final int META_DATA_UPDATE_INDEX_TICKERS = 92;

        public static final int META_DATA_LOAD_SNAP_UPDATED_INDEX_TICKERS = 93;
        public static final int META_DATA_UPDATE_SNAP_UPDATED_INDEX_TICKERS = 94;
        public static final int META_DATA_GET_TICKER_CLASSIFICATIONS = 95;

        public static final int META_DATA_GET_CLASSIFICATION_SERIALS = 96;

        public static final int META_DATA_GET_CAMPAIGN_REPORT_DATA = 97;
        public static final int META_DATA_GET_MERGE_AND_ACQUISITION_DETAILS = 98;

        public static final int META_DATA_GET_ALL_FUND_TICKER_COUNTRIES = 99;
        public static final int META_DATA_UPDATE_FUND_TICKER_COUNTRIES = 100;
        public static final int META_DATA_SENDING_FAILED_EXCEL_FILES = 101;
        public static final int META_DATA_GET_VWAP_FOR_TICKERS = 102;
        public static final int META_DATA_COMPANY_KPI = 103;
        public static final int META_DATA_RELATED_KPI = 104;
        public static final int META_DATA_GET_IPO_DATA = 105;
        public static final int META_DATA_GET_IPO_RESULT_COUNT = 106;
        public static final int META_DATA_COMPANY_AGGREGATES = 107;
        public static final int META_DATA_COMPANY_FINANCIAL_COMPANY_SIZE = 108;
        public static final int META_DATA_COMPANY_FINANCIAL_COMPANY_SIZE_BY_ID = 109;

        //IMDB to ORACLE
        public static final int GET_EQUITY_TICKERS = 131;
    }

    public static final class MasterDataType {

        private MasterDataType() {
        }

        public static final int MASTER_DATA_APP_SETTINGS = 1;
        public static final int MASTER_DATA_REPORT_CATEGORIES = 2;
        public static final int MASTER_DATA_REGION_COUNTRY = 3;
        public static final int MASTER_DATA_CORPORATE_ACTION_TYPES = 4;
        public static final int MASTER_DATA_REPORT_PROVIDERS = 5;
        public static final int MASTER_DATA_COMBO_BOX = 6;
        public static final int MASTER_DATA_SECTORS = 7;
        public static final int MASTER_DATA_COUNTRY = 8;
        public static final int MASTER_DATA_CAL_EVENTS_TITLE = 9;
        public static final int MASTER_DATA_FUND_CLASS = 10;
        public static final int MASTER_DATA_SUKUK_TYPES = 11;
        public static final int MASTER_DATA_BOND_TYPES = 12;
        public static final int MASTER_DATA_INSTRUMENT_TYPES = 13;
        public static final int MASTER_DATA_COUNTRY_INDICATORS = 14;
        public static final int MASTER_DATA_GLOBAL_EDITION_SETTINGS = 15;
        public static final int MASTER_DATA_ADD_USER = 17;
        public static final int MASTER_DATA_TIME_ZONES = 18;
        public static final int MASTER_DATA_SMART_PAGES = 19;
        public static final int MASTER_DATA_IPO_STATUS = 20;
        public static final int MASTER_DATA_IPO_SUBSCRIPTION_TYPE = 21;
        public static final int MASTER_DATA_SCREENER_TYPE = 22;
        public static final int MASTER_DATA_SCREENER_CATEGORIES = 23;
        public static final int MASTER_DATA_SECTOR_SNAPSHOT = 24;
        public static final int MASTER_DATA_UPDATE_CAL_EVENT_CATEGORY = 24;
        public static final int MASTER_DATA_GET_CAL_EVT_CATEGORIES = 25;
        public static final int MASTER_DATA_MA_STATUS = 26;
        public static final int MASTER_DATA_MA_DEAL_TYPES = 27;
        public static final int MASTER_DATA_PAYMENT_METHODS = 28;
        public static final int MASTER_DATA_SUPPORTED_CURRENCIES = 29;
        public static final int MASTER_DATA_REPORT_TYPES = 30;
        public static final int MASTER_DATA_FILE_PUBLISHERS = 31;
        public static final int MASTER_DATA_EXCHANGE_METADATA = 32;
        public static final int MASTER_DATA_STOCK_METADATA = 33;

        public static final int MASTER_DATA_ANNOUNCEMENT_COUNT = 34;
        public static final int MASTER_DATA_ANNOUNCEMENTS = 35;
        public static final int MASTER_DATA_NEWS_COUNT = 36;
        public static final int MASTER_DATA_ACCOUNT_TYPE = 37;
        public static final int MASTER_DATA_SALES_REP = 38;
        public static final int MASTER_DATA_CITY = 39;
        public static final int MASTER_DATA_NEWS_SOURCES = 40;
        public static final int MASTER_DATA_COMPANY_TYPES = 41;
        public static final int MASTER_DATA_EMBEDDED_OPTION_TYPES = 42;
        public static final int MASTER_DATA_PERIOD_DESCRIPTIONS = 43;
        public static final int MASTER_DATA_RATIO_GROUP_DESCRIPTIONS = 44;
        public static final int MASTER_DATA_CORPORATE_ACTION_ATTRIBUTES = 45;
        public static final int MASTER_DATA_EDITORIAL_DESCRIPTIONS = 46;
        public static final int MASTER_DATA_PRODUCT_CLASSIFICATION = 47;

    }


    public static final class InsertStatus {

        private InsertStatus() {
        }

        public static final int INSERT_FAILED = 0;
        public static final int INSERT_SUCCESS = 1;
        public static final int INSERT_OBJECT_ALREADY_EXISTS = 2;
    }

    //Price data table names
    public static final String REAL_TIME_SNAPSHOT_TABLE = " TICKER_SNAPSHOT ";
    public static final String DELAYED_SNAPSHOT_TABLE = " TICKER_SNAPSHOT_DELAYED ";

    public static final String REAL_TIME_SOURCES_SNAPSHOT_TABLE = " SOURCE_SNAPSHOT ";
    public static final String DELAYED_SOURCES_SNAPSHOT_TABLE = " SOURCE_SNAPSHOT_DELAYED ";

    public static final String DELAYED_INDEX_SNAPSHOT_TABLE = " INDEX_SNAPSHOT_DELAYED ";
    public static final String REAL_TIME_INDEX_SNAPSHOT_TABLE = " INDEX_SNAPSHOT ";

    //insert master data to price table
    public static final String INSERT_TICKER_SNAPSHOT = " INSERT INTO {0} (SOURCE_ID,TICKER_ID,CURRENCY,TICKER_SERIAL) SELECT SOURCE_ID,TICKER_ID,CURRENCY_ID,TICKER_SERIAL FROM TICKERS";
    public static final String INSERT_SOURCE_SNAPSHOT = " INSERT INTO {0} (SOURCE_ID,SOURCE_SERIAL) SELECT SOURCE_ID,SOURCE_SERIAL FROM SOURCES";

    //update price data
    public static final String UPDATE_SNAPSHOT = " UPDATE {0} SET ";
    public static final String UPDATE_SOURCE_SNAPSHOT = " UPDATE {0} SET ";
    public static final String UPDATE_INDEX_SNAPSHOT = " UPDATE {0} SET ";

    public static final String WHERE = " WHERE ";
    public static final String AND = " AND ";
    public static final String UPDATE_TICKER = " UPDATE TICKERS SET ";
    public static final String INSERT_TICKER = " INSERT INTO TICKERS ";
    public static final String UPDATE_CURRENCY_TICKER_SNAPSHOT = " UPDATE CURRENCY_SNAPSHOT SET ";
    public static final String INSERT_CURRENCY_TICKER_SNAPSHOT = " INSERT INTO CURRENCY_SNAPSHOT (SOURCE_ID,CURRENCY_PAIR";
    public static final String SQL_VALUES = ") VALUES(";
    public static final String SQL_BRACKET_OPEN = "(";
    public static final String SQL_BRACKET_CLOSE = ")";
    public static final String SQL_CURL_BRACKET_OPEN = "{";
    public static final String SQL_CURL_BRACKET_CLOSE = "}";
    public static final String SQL_COMMA = ",";
    public static final String SQL_DOUBLE_QUOTE = "\"";
    public static final String UPDATE_COMMODITY_SNAPSHOT = " UPDATE COMMODITY_SNAPSHOT SET ";
    public static final String INSERT_COMMODITY_SNAPSHOT = " INSERT INTO COMMODITY_SNAPSHOT (SOURCE_ID,TICKER_ID,INSTRUMENT_TYPE";

    public static final String QUERY_BETWEEN = " BETWEEN ";
    public static final String QUERY_ORDER_BY = " ORDER BY ";

    public static final String SQL_LAST_UPDATED_TIME_FILTER = DatabaseColumns.LAST_UPDATED_TIME + CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN + CommonDatabaseParams.SQL_QUESTION_MARK;
    public static final String SQL_LAST_SYNC_TIME_FILTER = DatabaseColumns.LAST_SYNC_TIME + CommonDatabaseParams.QUERY_GREATER_OR_EQUAL_THAN + CommonDatabaseParams.SQL_QUESTION_MARK;

    public static final class DatabaseColumns {
        private DatabaseColumns() {
        }

        public static final String RESULT_COUNT = "RESULT_COUNT";
        public static final String ROW_NUM = "ROW_NUM";
        public static final String MARKET_ID = "MARKET_ID";
        public static final String SOURCE_TICKER_ID = "SOURCE_TICKER_ID";
        public static final String COMPANY_ID = "COMPANY_ID";
        @Deprecated
        public static final String INSTRUMENT_TYPE_ID = "INSTRUMENT_TYPE_ID";
        public static final String CURRENCY_ID = "CURRENCY_ID";
        public static final String COMPANY_CURRENCY_ID = "COMPANY_CURRENCY_ID";
        public static final String DECIMAL_PLACES = "DECIMAL_PLACES";
        public static final String ISIN_CODE = "ISIN_CODE";
        public static final String FY_START_MONTH = "FY_START_MONTH";
        public static final String FINANCIAL_STMNT_FREQ = "FINANCIAL_STMNT_FREQ";
        public static final String NO_OF_SHARES = "NO_OF_SHARES";
        public static final String USE_SUBUNIT = "USE_SUBUNIT";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String LOT_SIZE = "LOT_SIZE";
        public static final String SECTOR_NAME_LAN = "SECTOR_NAME_LN";    //
        public static final String COMPANY_NAME_LAN = "COMPANY_NAME_LAN";    //
        public static final String TICKER_LONG_DES_LN = "TICKER_LONG_DES_LN";
        public static final String TICKER_SHORT_DES_LN = "TICKER_SHORT_DES_LN";
        public static final String TICKER_SHORT_DESCRIPTION = "TICKER_SHORT_DESCRIPTION";
        public static final String TICKER_LONG_DESCRIPTION = "TICKER_LONG_DESCRIPTION";
        public static final String SECTOR_NAME = "SECTOR_NAME";
        public static final String FOCUSED_REGIONS = "FOCUSED_REGIONS";
        public static final String FOCUSED_COUNTRIES = "FOCUSED_COUNTRIES";
        public static final String COUNTRY_CODE = "COUNTRY_CODE";
        public static final String GICSL2_LAN = "GICS_L2_LAN";
        public static final String GICSL3_LAN = "GICS_L3_LAN";
        public static final String GICSL4_LAN = "GICS_L4_LAN";
        public static final String GICSL4_CODE = "GICS_L4_CODE";
        public static final String GICSL3_CODE = "GICS_L3_CODE";
        public static final String GICSL2_CODE = "GICS_L2_CODE";
        public static final String GICSL1_CODE = "GICS_L1_CODE";
        public static final String UNIT = "UNIT";
        public static final String DISPLAY_TICKER = "DISPLAY_TICKER";
        public static final String STATUS = "STATUS";
        public static final String TICKER_ID = "TICKER_ID";
        public static final String MODIFIED_DATE = "MODIFIED_DATE";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String TICKER_SOURCE = "TICKER_SOURCE";
        public static final String SHORT_DESC_LAN = "TICKER_SHORT_DES_LN";
        public static final String LONG_DESC_LAN = "TICKER_LONG_DES_LN";
        public static final String LISTING_DATE = "LISTING_DATE";
        public static final String LISTING_STATUS = "LISTING_STATUS";
        public static final String STOCK_PUBLISHED_STATUS = "STOCK_PUBLISHED_STATUS";
        public static final String MAX_STOCKS = "MAX_STOCKS";
        public static final String IS_SETTLEMENT_T0 = "IS_SETTLEMENT_T0";
        public static final String IS_MAIN_STOCK = "IS_MAIN_STOCK";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String LAST_UPDATED_ON = "LAST_UPDATED_ON";
        public static final String CURRENCY_CORRECTION_FACTOR = "CURRENCY_CORRECTION_FACTOR";
        public static final String GLOBAL_SECTOR_ID = "GLOBAL_SECTOR_ID";
        public static final String DFN_SECTOR = "DFN_SECTOR";
        public static final String CATEGORY = "CATEGORY";
        public static final String INDEX_TYPE = "INDEX_TYPE";
        public static final String SOURCE_SOURCE_ID = "SOURCE_SOURCE_ID";
        public static final String REUTER_SYMBOL = "REUTER_SYMBOL";
        public static final String BLOOMBERG_SYMBOL = "BLOOMBERG_SYMBOL";
        public static final String SYMBOL_STATUS = "SYMBOL_STATUS";
        public static final String ELIGIBILITY_ID = "ELIGIBILITY_ID";
        public static final String SHARIA_COMPLIANT = "SHARIA_COMPLIANT";
        public static final String SYMBOLCODE = "SYMBOLCODE";
        public static final String SYMBOL = "SYMBOL";
        public static final String EXCHANGE_STATUS = "EXCHANGE_STATUS";
        public static final String ASSCT_TICKER_SERIAL = "ASSCT_TICKER_SERIAL";
        public static final String TICKER_ASSCTN_DATE = "TICKER_ASSCTN_DATE";
        public static final String FIRST_TRADING = "FIRST_TRADING";
        public static final String IDX_MAIN_SOURCE_ID = "IDX_MAIN_SOURCE_ID";
        public static final String IDX_SOURCE_ID = "IDX_SOURCE_ID";
        public static final String TEMPLATE_ID = "TEMPLATE_ID";
        public static final String COMPANY_TYPE = "COMPANY_TYPE";
        public static final String SECTOR_ID = "SECTOR_ID";
        public static final String OTC = "OTC";
        public static final String CLEARANCE_DURATION = "CLEARANCE_DURATION";
        public static final String LASTTRADABLEDATE = "LASTTRADABLEDATE";
        public static final String MIN_ORDER_SIZE = "MIN_ORDER_SIZE";
        public static final String LISTED_IDX = "LISTED_INDEXS";
        public static final String PARENT_SOURCE_ID = "PARENT_SOURCE_ID";
        public static final String EXCHANGE = "EXCHANGE";
        public static final String PER = "PER";
        public static final String TRADING_NAME = "TRADING_NAME";
        public static final String COMPANY_NAME = "COMPANY_NAME";
        public static final String CITY_SHORT_DESC = "CITY_SHORT_DESC";
        public static final String COUNTRY_DESC = "COUNTRY_DESC";
        public static final String TICKER_COUNTRY_DESC = "TICKER_COUNTRY_DESC";
        public static final String TICKER_COUNTRY_CODE = "TICKER_COUNTRY_CODE";
        public static final String PAID_CAPITAL = "PAID_CAPITAL";
        public static final String PAID_CAPITAL_CURRENCY = "PAID_CAPITAL_CURRENCY";
        public static final String AUTHORIZED_CAPITAL = "AUTHORIZED_CAPITAL";
        public static final String AUTHORISED_CAPITAL_CURRENCY = "AUTHORISED_CAPITAL_CURRENCY";
        public static final String SLA_LEVEL = "SLA_LEVEL";
        public static final String COMPANY_MARKET_CAP = "COMPANY_MARKET_CAP";
        public static final String FULLY_OWN_SUBS = "FULLY_OWN_SUBS";
        public static final String PARTIALY_OWN_SUBS = "PARTIALY_OWN_SUBS";
        public static final String PREV_NO_OF_SHARES = "PREV_NO_OF_SHARES";
        public static final String STOCK_MARKET_CAP  = "STOCK_MARKET_CAP";
        public static final String STOCK_MARKET_CAP_USD  = "STOCK_MARKET_CAP_USD";

        // company product classification columns
        public static final String CPC_L1_CODE = "CPC_L1_CODE";
        public static final String CPC_L2_CODE = "CPC_L2_CODE";
        public static final String CPC_L3_CODE = "CPC_L3_CODE";
        public static final String CPC_L4_CODE = "CPC_L4_CODE";
        public static final String CPC_L5_CODE = "CPC_L5_CODE";
        public static final String CPC_L5_LAN = "CPC_L5_LAN_";
        public static final String CPC_DESC = "CPC_DESC";

        //ticker levels
        public static final String TICKER_CLASS_L1 = "TICKER_CLASS_L1";
        public static final String TICKER_CLASS_L2 = "TICKER_CLASS_L2";
        public static final String TICKER_CLASS_L3 = "TICKER_CLASS_L3";

        //Fund table columns
        public static final String FUND_CLASS = "FUND_CLASS";
        public static final String CHG_3M = "CHG_3M";
        public static final String CHG_1Y = "CHG_1Y";
        public static final String CHG_YTD = "CHG_YTD";
        public static final String CHG_PER_3M = "PER_CHG_3M";
        public static final String CHG_PER_1Y = "PER_CHG_1Y";
        public static final String CHG_PER_YTD = "PER_CHG_YTD";
        public static final String NAV = "NAV";
        public static final String NAV_DATE = "NAV_DATE";
        public static final String MANAGED_COMPANIES = "MANAGED_COMPANIES";
        public static final String ISSUED_COMPANIES = "ISSUED_COMPANIES";
        public static final String TNAV = "TNAV";
        public static final String TNAV_DATE = "TNAV_DATE";
        public static final String TNAV_USD = "TNAV_USD";
        public static final String PRV_TNAV = "PRV_TNAV";
        public static final String PRV_TNAV_DATE = "PRV_TNAV_DATE";
        public static final String PRV_TNAV_USD = "PRV_TNAV_USD";
        public static final String CHG_PER_1M = "PER_CHG_1M";
        public static final String CHG_PER_3Y = "PER_CHG_3Y";
        public static final String CHG_PER_5Y = "PER_CHG_5Y";
        public static final String LOW_52_WEEK = "LOW_52WK";
        public static final String HIGH_52_WEEK = "HIGH_52WK";
        public static final String TEN_YEARS_PCT_CHANGE = "TEN_YEARS_PCT_CHANGE";
        public static final String LIFETIME_PCT_CHANGE = "LIFETIME_PCT_CHANGE";
        public static final String THREE_YEAR_MONTHLY_RETURN = "THREE_YEAR_MONTHLY_RETURN";
        public static final String FIVE_YEAR_MONTHLY_RETURN = "FIVE_YEAR_MONTHLY_RETURN";
        public static final String THREE_YEAR_ANNUAL_RETURN = "THREE_YEAR_ANNUAL_RETURN";
        public static final String FIVE_YEAR_ANNUAL_RETURN = "FIVE_YEAR_ANNUAL_RETURN";
        public static final String THREE_YR_ANNUAL_RETURN_STD_DEV = "THREE_YR_ANNUAL_RETURN_STD_DEV";
        public static final String FIVE_YR_ANNUAL_RETURN_STD_DEV = "FIVE_YR_ANNUAL_RETURN_STD_DEV";


        public static final String ESTB_DATE = "ESTB_DATE";
        public static final String ADMIN_FEE = "ADMIN_FEE";
        public static final String MGT_FEE = "MGT_FEE";
        public static final String CUSTODIAN_FEE = "CUSTODIAN_FEE";
        public static final String PERFORMANCE_FEE = "PERFORMANCE_FEE";
        public static final String REDEMPTION_FEE = "REDEMPTION_FEE";
        public static final String SUBSCRIPTION_FEE = "SUBSCRIPTION_FEE";
        public static final String SUBSEQUENT_SUBS = "SUBSEQUENT_SUBS";
        public static final String MIN_SUBSCRIPTION = "MIN_SUBSCRIPTION";
        public static final String SERVICE_FEE = "SERVICE_FEE";
        public static final String OTHER_EXP = "OTHER_EXP";
        public static final String NAV_FREQ_ID = "NAV_FREQ_ID";
        public static final String FYS_DAY = "FYS_DAY";
        public static final String FYS_MONTH = "FYS_MONTH";
        public static final String FYE_DAY = "FYE_DAY";
        public static final String FYE_MONTH = "FYE_MONTH";
        public static final String FUND_DURATION = "FUND_DURATION";
        public static final String FUND_STATUS = "FUND_STATUS";
        public static final String MANAGED_COUNTRIES = "MANAGED_COUNTRIES";
        public static final String ISSUED_COUNTRIES = "ISSUED_COUNTRIES";

        //Fixed income columns
        public static final String COUPON_DATE_FIRST = "COUPON_DATE_FIRST";
        public static final String ANNOUNCE_DATE = "ANNOUNCE_DATE";
        public static final String AUCTION_DATE = "AUCTION_DATE";
        public static final String SETTLE_DATE_FIRST = "SETTLE_DATE_FIRST";
        public static final String INTEREST_ACR_DATE = "INTEREST_ACR_DATE";
        public static final String MATURITY_DATE = "MATURITY_DATE";
        public static final String DATE_OF_ISSUE = "DATE_OF_ISSUE";
        public static final String WKN = "WKN";
        public static final String ISSUED_PERIOD = "ISSUED_PERIOD";
        public static final String COUNTRY_OF_ISSUE = "COUNTRY_OF_ISSUE";
        public static final String MATURITY_PERIOD = "MATURITY_PERIOD";
        public static final String AMOUNT_ISSUED = "AMOUNT_ISSUED";
        public static final String AMOUNT_ISSUED_USD = "AMOUNT_ISSUED_USD";
        public static final String REQUIRED_AMOUNT = "REQUIRED_AMOUNT";
        public static final String AMOUNT_ISSUED_ISSUER_CUR = "AMOUNT_ISSUED_ISSUER_CUR";
        public static final String AMOUNT_OUTSTANDING = "AMOUNT_OUTSTANDING";
        public static final String MINIMUM_PIECE = "MINIMUM_PIECE";
        public static final String MINIMUM_INCREMENT = "MINIMUM_INCREMENT";
        public static final String ISSUE_PRICE = "ISSUE_PRICE";
        public static final String COUPON_RATE = "COUPON_RATE";
        public static final String MIN_PRICE = "MIN_PRICE";
        public static final String YIELD = "YIELD";//not sure
        public static final String AUCTION_AMOUNT = "AUCTION_AMOUNT";
        public static final String ISSUE_MIN_RATE = "ISSUE_MIN_RATE";
        public static final String ISSUE_MAX_RATE = "ISSUE_MAX_RATE";
        public static final String ISSUE_WGT_AVG_RATE = "ISSUE_WGT_AVG_RATE";
        public static final String FLOATING_COUPON_A = "FLOATING_COUPON_A";
        public static final String FLOATING_COUPON_B = "FLOATING_COUPON_B";
        public static final String FLOATING_COUPON_C = "FLOATING_COUPON_C";
        public static final String FLOATING_COUPON_D = "FLOATING_COUPON_D";
        public static final String FLOATING_COUPON_E = "FLOATING_COUPON_E";
        public static final String FLOATING_COUPON_IBOR1 = "FLOATING_COUPON_IBOR1";
        public static final String FLOATING_COUPON_IBOR2 = "FLOATING_COUPON_IBOR2";
        public static final String FLOATING_COUPON_TBILL1 = "FLOATING_COUPON_TBILL1";
        public static final String FLOATING_COUPON_TBILL2 = "FLOATING_COUPON_TBILL2";
        public static final String AUCTION_MIN_RATE = "AUCTION_MIN_RATE";
        public static final String AUCTION_MAX_RATE = "AUCTION_MAX_RATE";
        public static final String AUCTION_WGT_AVG_RATE = "AUCTION_WGT_AVG_RATE";
        public static final String SUKUK_TYPE = "SUKUK_TYPE";
        public static final String LEAD_MANAGER = "LEAD_MANAGER";
        public static final String MARKET_TYPE = "MARKET_TYPE";
        public static final String BOND_TYPE = "BOND_TYPE";
        public static final String COUPON_FREQUENCY = "COUPON_FREQUENCY";
        public static final String COUPON_TYPE = "COUPON_TYPE";
        public static final String COUPON_DAY_COUNT = "COUPON_DAY_COUNT";
        public static final String AUCTION_BIDS = "AUCTION_BIDS";
        public static final String ISSUE_BIDS = "ISSUE_BIDS";
        public static final String NEXT_COUPON_DATE = "NEXT_COUPON_DATE";
        public static final String REMAINING_COUPON_COUNT = "REMAINING_COUPON_COUNT";
        public static final String LAST_COUPON_DATE = "LAST_COUPON_DATE";
        public static final String LAST_TRADED_DATE = "LAST_TRADED_DATE";
        public static final String PRICE_ON_TRADING_DATE = "PRICE_ON_TRADING_DATE";
        public static final String TRADING_DATE = "TRADING_DATE";
        public static final String CANCELLATION_DATE = "CANCELLATION_DATE";
        public static final String DETAILS_DISCLOSING_DATE = "DETAILS_DISCLOSING_DATE";
        public static final String LATEST_DATE = "LATEST_DATE";
        public static final String PAR_VALUE = "PAR_VALUE";
        public static final String FACE_VALUE = "FACE_VALUE";
        public static final String COUPON_DAY_COUNT_DESC = "COUPON_DAY_COUNT_DESC";
        public static final String EMBEDED_OPTIONS = "EMBEDED_OPTIONS";
        public static final String TRADING_CLEARANCE_DAYS = "TRADING_CLEARANCE_DAYS";
        public static final String YTM = "YTM";
        public static final String AMOUNT_ISSUED_USD_LATEST = "AMOUNT_ISSUED_USD_LATEST";
        public static final String IS_PRICE_PERCENTAGE = "IS_PRICE_PERCENTAGE";

        //individual snapshot columns This name is bind to DB and view. If change is done need to change the mapping
        public static final String IND_INDIVIDUAL_ID = "INDIVIDUAL_ID";
        public static final String IND_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String IND_INDIVIDUAL_NAME = "INDIVIDUAL_NAME";
        public static final String IND_LAN_CODE = "LAN_CODE";
        public static final String IND_HED = "HED";
        public static final String IND_DAT = "DAT";
        public static final String IND_SNAP_TYPE = "TYPE";
        public static final String IND_INSIDER_ID = "INS_TRD_ID";
        public static final String IND_INSIDER_TYPE = "INS_TRD_TYPE";
        public static final String IND_COMPANY_ID = "COMPANY_ID";
        public static final String IND_COMPANY_NAME = "COMPANY_NAME";
        public static final String IND_FULL_NAME = "FULL_NAME";
        public static final String IND_OWNERSHIP_PER = "OWN_PCT";
        public static final String PRV_IND_OWNERSHIP_PER = "PRV_OWN_PCT";
        public static final String IND_DESIGNATION = "DESIGNATION";
        public static final String IND_RESIDENCE = "RESIDENCE";
        public static final String IND_WEBSITE = "WEBSITE";
        public static final String IND_NEWS_ID = "NEWS_ID";
        public static final String IND_NEWS_HEAD = "NEWS_HEAD";
        public static final String SEQ_ID_MAX = "SEQ_ID_MAX";
        public static final String IND_COUNTRY = "COUNTRY";
        public static final String IND_SOURCE_ID = "SOURCE_ID";
        public static final String IND_TICKER_ID = "TICKER_ID";
        public static final String QUANTITY = "QUANTITY";
        public static final String SEQ_ID_MIN = "SEQ_ID_MIN";
        public static final String LOGO = "LOGO";

        //individual detail columns
        public static final String IND_DETAIL_ID = "INDIVIDUAL_ID";
        public static final String IND_DETAIL_NAME = "NAME";
        public static final String IND_DETAIL_RELATED_IND = "RELATED_INDIVIDUALS";
        public static final String IND_DETAIL_RELATED_COM = "RELATED_COMPANIES";
        public static final String RELATED_OWNERS = "RELATED_OWNERS";
        public static final String RELATED_MEMBERS = "RELATED_MEMBERS";
        public static final String RELATED_INSIDER_TRANS = "RELATED_INSIDER_TRANS";

        //sources table columns
        public static final String S_SOURCE_ID = "SOURCE_ID";
        public static final String S_STATUS = "STATUS";
        public static final String S_SOURCE_SERIAL = "SOURCE_SERIAL";
        public static final String S_OPEN_TIME = "OPEN_TIME";
        public static final String S_CLOSE_TIME = "CLOSE_TIME";
        public static final String S_SHORT_DESCRIPTION_LAN = "SHORT_DESCRIPTION_LAN";
        public static final String S_LONG_DESCRIPTION_LAN = "LONG_DESCRIPTION_LAN";
        public static final String S_LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String S_DEFAULT_CURRENCY = "DEFAULT_CURRENCY";
        public static final String S_TIMEZONE_ID = "TIMEZONE_ID";
        public static final String S_DISPLAY_CODE = "DISPLAY_CODE";
        public static final String S_DEFAULT_DECIMAL_PLACES = "DEFAULT_DECIMAL_PLACES";
        public static final String S_IS_VIRTUAL_EXCHANGE = "IS_VIRTUAL_EXCHANGE";
        public static final String S_IS_EXPAND_SUBMKTS = "IS_EXPAND_SUBMKTS";
        public static final String S_WEEK_START = "WEEK_START";
        public static final String S_MAIN_INDEX_SOURCE = "MAIN_INDEX_SOURCE";
        public static final String S_MAIN_INDEX_TICKER = "MAIN_INDEX_TICKER";
        public static final String S_MAIN_INDEX_TICKER_SERIAL = "MAIN_INDEX_TICKER_SERIAL";
        public static final String S_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String S_LISTED_STOCKS_COUNT = "LISTED_STOCKS_COUNT";
        public static final String S_SECTOR_COUNT = "SECTOR_COUNT";
        public static final String S_IS_DEFAULT = "IS_DEFAULT";
        public static final String S_DEFAULT_WINDOW_TYPE = "DEFAULT_WINDOW_TYPES";
        public static final String S_SCREENER_CODE = "SCREENER_CODE";

        /*Country ticker columns*/
        public static final String COUNTRY_TICKER_SERIAL = "TICKER_SERIAL";
        public static final String COUNTRY_TICKER_ID = "TICKER_ID";
        public static final String COUNTRY_TICKER_COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COUNTRY_TICKER_CURRENCY_ID = "CURRENCY_ID";
        public static final String COUNTRY_TICKER_DECIMAL_PLACES = "DECIMAL_PLACES";
        public static final String COUNTRY_TICKER_DISPLAY_TICKER = "DISPLAY_TICKER";
        public static final String COUNTRY_TICKER_LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String COUNTRY_TICKER_SOURCE_ID = "SOURCE_ID";
        public static final String COUNTRY_TICKER_STATUS = "STATUS";
        public static final String COUNTRY_TICKER_SHORT_DES_LN = "TICKER_SHORT_DES_LN";
        public static final String COUNTRY_TICKER_LONG_DES_LN = "TICKER_LONG_DES_LN";
        public static final String COUNTRY_TICKER_UNIT = "UNIT";
        public static final String COUNTRY_TICKER_HISTORY_DATA = "HISTORY_DATA";
        public static final String COUNTRY_TICKER_INSTRUMENT_TYPE_ID = "INSTRUMENT_TYPE_ID";

        /* IPO Columns*/
        public static final String SUBSCRIPTION_TYPE = "SUBSCRIPTION_TYPE";
        public static final String IPO_STATUS = "IPO_STATUS";
        public static final String GICS_L4_CODE = "GICS_L4_CODE";
        public static final String ANNOUNCEMENT_DATE = "ANNOUNCEMENT_DATE";
        public static final String IPO_ID = "IPO_ID";
        public static final String IPO_START_DATE = "IPO_START_DATE";
        public static final String IPO_END_DATE = "IPO_END_DATE";
        public static final String VOLUME = "VOLUME";
        public static final String ANNOUNCEMENT_ID = "ANNOUNCEMENT_ID";
        public static final String COVERAGE_PERCENTAGE = "COVERAGE_PERCENTAGE";
        public static final String COVERAGE_VOLUME = "COVERAGE_VOLUME";
        public static final String CURRENCY = "CURRENCY";
        public static final String CUSTODY_SETTLEMENT_DATE = "CUSTODY_SETTLEMENT_DATE";
        public static final String REFUND_DATE = "REFUND_DATE";
        public static final String UPCOMING_NEWS_ID = "UPCOMING_NEWS_ID";
        public static final String DETAILS_RELEASED_NEWS_ID = "DETAILS_RELEASED_NEWS_ID";
        public static final String ACTIVE_NEWS_ID = "ACTIVE_NEWS_ID";
        public static final String TO_BE_TRADED_NEWS_ID = "TO_BE_TRADED_NEWS_ID";
        public static final String TRADED_NEWS_ID = "TRADED_NEWS_ID";
        public static final String WITHDRAWN_NEWS_ID = "WITHDRAWN_NEWS_ID";
        public static final String NEWS_ID = "NEWS_ID";
        public static final String DOCUMENT_URL = "DOCUMENT_URL";
        public static final String DOCUMENT_ID = "DOCUMENT_ID";
        public static final String MANAGER_ID_LIST = "MANAGER_ID_LIST";

        //LOG table constants
        public static final String LAST_RUN_TIME = "LAST_RUN_TIME";
        public static final String PROCESS = "PROCESS";
        public static final String ROW_COUNT = "ROW_COUNT";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String PARAMS = "PARAMS";
        public static final String PROCESS_ID = "PROCESS_ID";
        public static final String START_TIME = "START_TIME";
        public static final String END_TIME = "END_TIME";
        public static final String TYPE = "TYPE";
        public static final String ID = "ID";
        public static final String LAST_ID = "LAST_ID";

        //fund bechmark table constants
        public static final String BENCHMARK_TICKER = "BENCHMARK_TICKER";
        public static final String BENCHMARK_RATE = "BENCHMARK_RATE";

        //announcements - IMDB/RDBMS
        public static final String ANN_ID_MAX = "ANN_ID_MAX";
        public static final String ANN_ID_MIN = "ANN_ID_MIN";

        public static final String ESTABLISHED_DATE = "ESTABLISHED_DATE";
        public static final String WEB = "WEB";
        public static final String EMAIL = "EMAIL";
        public static final String PHONE = "PHONE_1";
        public static final String FAX = "FAX_1";
        public static final String CONTACT_PERSON = "CONTACT_PERSON";
        public static final String ADDRESS = "ADDRESS_1";

        /* KPI Columns*/
        public static final String KPI_ID = "KPI_ID";
        public static final String KPI_DEFINITION = "KPI_DEFINITION";
        public static final String KPI_DATE = "KPI_DATE";
        public static final String KPI_VALUE = "KPI_VALUE";
        public static final String KPI_YEAR = "KPI_YEAR";
        public static final String KPI_PERIOD = "KPI_PERIOD";
        public static final String KPI_TITLE = "KPI_TITLE";
        public static final String KPI_MEASURING_UNIT = "KPI_MEASURING_UNIT";
        public static final String KPI_DEFINITION_ID = "KPI_DEFINITION_ID";

        //stock ownership limits
        public static final String SERIES_ID = "SERIES_ID";
        public static final String SERIES_DESC = "SERIES_DESC";
        public static final String UPDATE_FREQUENCY = "UPDATE_FREQUENCY";
        public static final String UPDATE_FREQUENCY_DESC = "UPDATE_FREQUENCY_DESC";
        public static final String DIRECTION = "DIRECTION";
        public static final String OWNERSHIP_ELIGIBILITY = "OWNERSHIP_ELIGIBILITY";
        public static final String OWNERSHIP_LIMIT = "OWNERSHIP_LIMIT";
        public static final String OWNERSHIP_VALUE = "OWNERSHIP_VALUE";
        public static final String OWNERSHIP_DATE = "OWNERSHIP_VALUE_DATE";
        public static final String STOCK_OWNERSHIP_LIMIT_ID = "STOCK_OWNERSHIP_LIMIT_ID";
        public static final String LIMIT_STOCK_DEF_ID = "LIMIT_STOCK_DEF_ID";

        //fund investment allocations
        public static final String TXN_SEQ = "TXN_SEQ";
        public static final String ALLOCATION_ID = "ALLOCATION_ID";
        public static final String FUND_TICKER_SERIAL = "FUND_TICKER_SERIAL";
        public static final String INV_TYPE = "INV_TYPE";
        public static final String INV_TYPE_DESCRIPTION = "INV_TYPE_DESCRIPTION";
        public static final String TXN_DATE = "TXN_DATE";
        public static final String INVESTED_ASSET_ID = "INVESTED_ASSET_ID";
        public static final String INVESTED_ASSET_DESCRIPTION = "INVESTED_ASSET_DESCRIPTION";
        public static final String INVESTED_TICKER_SERIAL = "INVESTED_TICKER_SERIAL";
        public static final String INVESTED_TICKER_DESCRIPTION = "INVESTED_TICKER_DESCRIPTION";
        public static final String INVESTED_COUNTRY_CODE = "INVESTED_COUNTRY_CODE";
        public static final String INVESTED_COUNTRY_DESCRIPTION = "INVESTED_COUNTRY_DESCRIPTION";
        public static final String INVESTED_SECTOR = "INVESTED_SECTOR";
        public static final String INVESTED_SECTOR_DESCRIPTION = "INVESTED_SECTOR_DESCRIPTION";
        public static final String INVESTED_REGION = "INVESTED_REGION";
        public static final String INVESTED_REGION_DESCRIPTION = "INVESTED_REGION_DESCRIPTION";
        public static final String RATIO = "RATIO";
        public static final String FILE_ID = "FILE_ID";
        public static final String FILE_IDS = "FILE_IDS";
        public static final String ESTIMATED_SHARES = "ESTIMATED_SHARES";
        public static final String NUMBER_OF_SHARES = "NUMBER_OF_SHARES";
        public static final String IS_SINGLE_RECORD = "IS_SINGLE_RECORD";

        public static final String MAX_DATE = "MAX_DATE";
        public static final String LAST_SYNC_TIME = "LAST_SYNC_TIME";
        public static final String TIMEZONE_OFFSET = "TIMEZONE_OFFSET";
        public static final String ADJUSTED_OFFSET = "ADJUSTED_OFFSET";
        public static final String MARKET_CAP = "MARKET_CAP";
        public static final String CITY_ID = "CITY_ID";

        public static final String EMAIL_TYPE = "EMAIL_TYPE";
        public static final String EMAIL_COUNT = "EMAIL_COUNT";

        //adjusted price snapshot
        public static final String TRADE_PRICE = "TRADE_PRICE";
        public static final String TRADE_PRICE_UPDATED_TIME = "TRADE_PRICE_UPDATED_TIME";
        public static final String BEST_ASK_PRICE = "BEST_ASK_PRICE";
        public static final String BEST_ASK_PRICE_UPDATED_TIME = "BEST_ASK_PRICE_UPDATED_TIME";
        public static final String BEST_BID_PRICE = "BEST_BID_PRICE";
        public static final String BEST_BID_PRICE_UPDATED_TIME = "BEST_BID_PRICE_UPDATED_TIME";

        //market fund investments
        public static final String FUND_TICKER = "FUND_TICKER";
        public static final String FUND_SOURCE = "FUND_SOURCE";
        public static final String VWAP = "VWAP";
        public static final String CURRENCY_RATE = "CURRENCY_RATE";

        //Currency Data columns
        public static final String CURRENCY_CODE = "CURRENCY_CODE";
        public static final String CURRENCY_DESCRIPTION = "DESCRIPTION";

        //
        public static final String INVESTOR_TYPE_VALUE_ID = "INVESTOR_TYPE_VALUE_ID";
        public static final String NATIONALITY_ID = "NATIONALITY_ID";
        public static final String INVESTOR_TYPE_ID = "INVESTOR_TYPE_ID";
        public static final String INVESTMENT_DATE = "INVESTMENT_DATE";
        public static final String REF_DOC_ID = "REF_DOC_ID";
        public static final String BUY_VALUE = "BUY_VALUE";
        public static final String SELL_VALUE = "SELL_VALUE";
        public static final String BUY_PERCENTAGE = "BUY_PERCENTAGE";
        public static final String SELL_PERCENTAGE = "SELL_PERCENTAGE";

        //user session details
        public static final String USERNAME = "USERNAME";
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String COMPANY = "COMPANY";
        public static final String COUNTRY = "COUNTRY";
        public static final String PHONE_NO = "PHONE";
        public static final String WORK_TEL = "WORK_TEL";
        public static final String SALES_REP_NAME = "SALES_REP_NAME";
        public static final String START_DATE = "START_DATE";
        public static final String EXPIRY_DATE = "EXPIRY_DATE";
        public static final String LOGIN_DATE = "LOGIN_DATE";
        public static final String LOGOUT_DATE = "LOGOUT_DATE";

        //user details
        public static final String ACCOUNT_EXPIRE_DATE = "ACCOUNT_EXPIRE_DATE";
        public static final String ACCOUNT_STATUS = "ACCOUNT_STATUS";
        public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
        public static final String TITLE = "TITLE";
        public static final String EMAIL_RELEASE_NOTES = "EMAIL_RELEASE_NOTES";

        //Notification email table
        public static final String EOD = "EOD";
        public static final String DEMO_USAGE = "DEMO_USAGE";

        // campaign active count
        public static final String CAMPAIGN_ID = "CAMPAIGN_ID";
        public static final String ACTIVE_COUNT = "ACTIVE_COUNT";

        // release note columns
        public static final String RELEASE_NOTE_ID = "RELEASE_NOTE_ID";
        public static final String NOTE_TITLE = "NOTE_TITLE";
        public static final String RELEASE_DATE = "RELEASE_DATE";
        public static final String NOTE_BODY = "NOTE_BODY";
        public static final String NOTE_STATUS = "NOTE_STATUS";
        public static final String CREATED_USER = "CREATED_USER";
        public static final String CREATED_TIME = "CREATED_TIME";
        public static final String LAST_UPDATED_TIME_RN = "LAST_UPDATED_TIME";
        public static final String LAST_UPDATED_USER = "LAST_UPDATED_USER";
        public static final String IS_USERS_UPDATED = "IS_USERS_UPDATED";
        public static final String IS_EMAIL_SENT = "IS_EMAIL_SENT";
        public static final String IS_NOTIFY_USER = "IS_NOTIFY_USER";
        public static final String LAST_UPDATED_USERNAME = "LAST_UPDATED_USERNAME";

        //Country Indicators
        public static final String INDICATOR_CODE = "INDICATOR_CODE";
        public static final String INDICATOR_GROUP_ID = "INDICATOR_GROUP_ID";
        public static final String INDICATOR_DESC = "INDICATOR_DESC";
        public static final String INDICATOR_VALUE_DATE = "INDICATOR_VALUE_DATE";
        public static final String INDICATOR_VALUE = "INDICATOR_VALUE";
        public static final String PROVIDER = "PROVIDER";
        public static final String SCALE = "SCALE";
        public static final String INDICATOR_LAST_REVIEW_DATE = "INDICATOR_LAST_REVIEW_DATE";
        public static final String IS_FORECAST = "IS_FORECAST";
        public static final String INDICATOR_GROUP_CODE = "INDICATOR_GROUP_CODE";
        public static final String INDICATOR_GROUP_DESC = "INDICATOR_GROUP_DESC";

        public static final String FUND_SECTOR = "FUND_SECTOR";
        public static final String FUND_ASSET_ID = "FUND_ASSET_ID";

        public static final String KEY = "KEY";
        public static final String VALUE = "VALUE";

        //Common for news and announcements
        public static final String SEQ_ID = "SEQ_ID";

        //COUPON DAY COUNT TYPES
        public static final String DAY_COUNT_DESC = "DAY_COUNT_DESC";
        public static final String DAY_COUNT_VALUE = "DAY_COUNT_VALUE";

        public static final String LANGUAGE_ID = "LANGUAGE_ID";

        public static final String RANK = "RANK";

        //MA Excel Columns
        public static final String COMPLETION_DATE = "date";

        // country Columns
        public static final String REGION_IDS = "REGION_IDS";
        public static final String IS_MACRO_DATA = "IS_MACRO_DATA";
        public static final String IS_PRICE_DATA = "IS_PRICE_DATA";
        public static final String IS_IPO_DATA = "IS_IPO_DATA";
        public static final String IS_COMPANY_DATA = "IS_COMPANY_DATA";
        public static final String IS_OTHER_DATA = "IS_OTHER_DATA";
        public static final String IS_EC_COUNTRY = "IS_EC_COUNTRY";
        public static final String IS_FI_DATA = "IS_FI_DATA";
        public static final String IS_MF_DATA = "IS_MF_DATA";
        public static final String SHORT_NAME = "SHORT_NAME";
        public static final String FULL_NAME = "FULL_NAME";
        public static final String ISO_CODE = "ISO_CODE";

        //top news history database columns
        public static final String EDITION_ID = "EDITION_ID";
        public static final String SECTION_ID = "SECTION_ID";
        public static final String IS_NEWS_TAB_UPDATED = "IS_NEWS_TAB_UPDATED";
        public static final String IS_TOP_STORY = "IS_TOP_STORY";
        public static final String NEWS_EDITION_SECTION = "NEWS_EDITION_SECTION";
        public static final String TOP_NEWS_EDITION_SECTION = "TOP_NEWS_EDITION_SECTION";

        //index
        public static final String EXCHANGES = "EXCHANGES";
        public static final String COUNTRIES = "COUNTRIES";
        public static final String REGIONS = "REGIONS";

        public static final String CONSTITUENTS = "CONSTITUENTS";

        //        public static final String PROVIDER = "PROVIDER";
        public static final String LAUNCH_DATE = "LAUNCH_DATE";
        public static final String LAST_TRADE_PRICE = "LAST_TRADE_PRICE";
        public static final String LAST_TRADE_DATE = "LAST_TRADE_DATE";

        public static final String ANNUALIZED_RETURN_QTD = "ANNUALIZED_RETURN_QTD";
        public static final String ANNUALIZED_RETURN_YTD = "ANNUALIZED_RETURN_YTD";
        public static final String ANNUALIZED_RETURN_1Y = "ANNUALIZED_RETURN_1Y";
        public static final String ANNUALIZED_RETURN_2Y = "ANNUALIZED_RETURN_2Y";
        public static final String ANNUALIZED_RETURN_3Y = "ANNUALIZED_RETURN_3Y";
        public static final String ANNUALIZED_RETURN_5Y = "ANNUALIZED_RETURN_5Y";
        public static final String ANNUALIZED_RETURN_LIFETIME = "ANNUALIZED_RETURN_LIFETIME";
        public static final String TYPE_ID = "TYPE_ID";

        public static final String MONTHLY_HIGH = "HIGH_30DAY";
        public static final String MONTHLY_HIGH_DATE = "HIGH_30DAY_DATE";
        public static final String MONTHLY_LOW = "LOW_30DAY";
        public static final String MONTHLY_LOW_DATE = "LOW_30DAY_DATE";
        public static final String QUARTERLY_HIGH = "HIGH_QTD";
        public static final String QUARTERLY_HIGH_DATE = "HIGH_QTD_DATE";
        public static final String QUARTERLY_LOW = "LOW_QTD";
        public static final String QUARTERLY_LOW_DATE = "LOW_QTD_DATE";
        public static final String YTD_HIGH = "HIGH_YTD";
        public static final String YTD_HIGH_DATE = "HIGH_YTD_DATE";
        public static final String YTD_LOW = "LOW_YTD";
        public static final String YTD_LOW_DATE = "LOW_YTD_DATE";
        public static final String ONE_YEAR_HIGH = "HIGH_PRICE_OF_52_WEEKS";
        public static final String ONE_YEAR_HIGH_DATE = "HIGH_PRICE_DATE_OF_52_WEEKS";
        public static final String ONE_YEAR_LOW = "LOW_PRICE_OF_52_WEEKS";
        public static final String ONE_YEAR_LOW_DATE = "LOW_PRICE_DATE_OF_52_WEEKS";
        public static final String TWO_YEAR_HIGH = "HIGH_2YEAR";
        public static final String TWO_YEAR_HIGH_DATE = "HIGH_2YEAR_DATE";
        public static final String TWO_YEAR_LOW = "LOW_2YEAR";
        public static final String TWO_YEAR_LOW_DATE = "LOW_2YEAR_DATE";
        public static final String THREE_YEAR_HIGH = "HIGH_3YEAR";
        public static final String THREE_YEAR_HIGH_DATE = "HIGH_3YEAR_DATE";
        public static final String THREE_YEAR_LOW = "LOW_3YEAR";
        public static final String THREE_YEAR_LOW_DATE = "LOW_3YEAR_DATE";
        public static final String LIFETIME_HIGH = "HIGH_LIFETIME";
        public static final String LIFETIME_HIGH_DATE = "HIGH_LIFETIME_DATE";
        public static final String LIFETIME_LOW = "LOW_LIFETIME";
        public static final String LIFETIME_LOW_DATE = "LOW_LIFETIME_DATE";

        public static final String CLOSE_QTD = "CLOSE_QTD";
        public static final String CLOSE_QTD_DATE = "CLOSE_QTD_DATE";
        public static final String CLOSE_YTD = "CLOSE_YTD";
        public static final String CLOSE_YTD_DATE = "CLOSE_YTD_DATE";
        public static final String CLOSE_1YEAR = "CLOSE_1YEAR";
        public static final String CLOSE_1YEAR_DATE = "CLOSE_1YEAR_DATE";
        public static final String CLOSE_2YEAR = "CLOSE_2YEAR";
        public static final String CLOSE_2YEAR_DATE = "CLOSE_2YEAR_DATE";
        public static final String CLOSE_3YEAR = "CLOSE_3YEAR";
        public static final String CLOSE_3YEAR_DATE = "CLOSE_3YEAR_DATE";
        public static final String CLOSE_5YEAR = "CLOSE_5YEAR";
        public static final String CLOSE_5YEAR_DATE = "CLOSE_5YEAR_DATE";
        public static final String CLOSE_START = "CLOSE_START";
        public static final String CLOSE_START_DATE = "CLOSE_START_DATE";

        public static final String CLASSIFICATION_CODE = "CLASSIFICATION_CODE";
        public static final String CLASSIFICATION_SERIAL = "CLASSIFICATION_SERIAL";

        public static final String IS_REALTIME_FEED = "IS_REALTIME_FEED";
        public static final String OFF_DAYS = "OFF_DAYS";
        public static final String MARKET_OPEN_TIME = "MARKET_OPEN_TIME";
        public static final String MARKET_CLOSE_TIME = "MARKET_CLOSE_TIME";

        public static final String OWNERSHIP_VWAP = "OWNERSHIP_DATE_VWAP";
        public static final String OWNERSHIP_VWAP_USD = "OWNERSHIP_DATE_VWAP_USD";
        public static final String OWNERSHIP_NO_OF_SHARES = "NUMBER_OF_SHARES_ON_DATE";
        public static final String OWNERSHIP_CA = "HAS_CORPORATE_ACTIONS_ON_DATE";

        public static final String PROMO_CODE = "PROMO_CODE";
        public static final String ACTIVATED_TIME = "ACTIVATED_TIME";
        public static final String USER_ID = "USER_ID";
        public static final String ROWNUM = "ROWNUM";

        //ticker class levels master
        public static final String TICKER_CLASS_L1_ID = "LEVEL_1_ID";
        public static final String TICKER_CLASS_L2_ID = "LEVEL_2_ID";
        public static final String TICKER_CLASS_L3_ID = "LEVEL_3_ID";
        public static final String TICKER_CLASS_L1_DESC = "LEVEL_1_DESC";
        public static final String TICKER_CLASS_L2_DESC = "LEVEL_2_DESC";
        public static final String TICKER_CLASS_L3_DESC = "LEVEL_3_DESC";

        //email meta data, email content
        public static final String EMAIL_ID = "EMAIL_ID";
        public static final String ATTACHMENT_NAME = "ATTACHMENT_NAME";

        //COMPANAY_SIZE
        public static final String TOTAL_ASSETS_USD = "TOTAL_ASSETS_USD";
        public static final String TOTAL_ASSETS_MIN_USD = "TOTAL_ASSETS_MIN_USD";
        public static final String TOTAL_ASSETS_MAX_USD = "TOTAL_ASSETS_MAX_USD";
        public static final String TOTAL_ASSETS_ACT_YEAR = "TOTAL_ASSETS_ACT_YEAR";
        public static final String TOTAL_ASSETS_BKT_YEAR = "TOTAL_ASSETS_BKT_YEAR";
        public static final String TOTAL_ASSETS_TEMP = "TOTAL_ASSETS_TEMP";
        public static final String TOTAL_ASSETS_YEAR_TEMP = "TOTAL_ASSETS_YEAR_TEMP";
        public static final String TOTAL_LIABILITIES_USD = "TOTAL_LIABILITIES_USD";
        public static final String TOTAL_LIABILITIES_MIN_USD = "TOTAL_LIABILITIES_MIN_USD";
        public static final String TOTAL_LIABILITIES_MAX_USD = "TOTAL_LIABILITIES_MAX_USD";
        public static final String TOTAL_LIABILITIES_ACT_YEAR = "TOTAL_LIABILITIES_ACT_YEAR";
        public static final String TOTAL_LIABILITIES_BKT_YEAR = "TOTAL_LIABILITIES_BKT_YEAR";
        public static final String TOTAL_LIABILITIES_TEMP = "TOTAL_LIABILITIES_TEMP";
        public static final String TOTAL_LIABILITIES_YEAR_TEMP = "TOTAL_LIABILITIES_YEAR_TEMP";
        public static final String TOTAL_REVENUE_USD = "TOTAL_REVENUE_USD";
        public static final String TOTAL_REVENUE_MIN_USD = "TOTAL_REVENUE_MIN_USD";
        public static final String TOTAL_REVENUE_MIN = "TOTAL_REVENUE_MIN";
        public static final String TOTAL_REVENUE_MAX_USD = "TOTAL_REVENUE_MAX_USD";
        public static final String TOTAL_REVENUE_MAX = "TOTAL_REVENUE_MAX";
        public static final String TOTAL_REVENUE_ACT_YEAR = "TOTAL_REVENUE_ACT_YEAR";
        public static final String TOTAL_REVENUE_BKT_YEAR = "TOTAL_REVENUE_BKT_YEAR";
        public static final String TOTAL_REVENUE_TEMP = "TOTAL_REVENUE_TEMP";
        public static final String TOTAL_REVENUE_YEAR_TEMP = "TOTAL_REVENUE_YEAR_TEMP";
        public static final String NET_INCOME_USD = "NET_INCOME_USD";
        public static final String NET_INCOME_MIN_USD = "NET_INCOME_MIN_USD";
        public static final String NET_INCOME_MAX_USD = "NET_INCOME_MAX_USD";
        public static final String NET_INCOME_ACT_YEAR = "NET_INCOME_ACT_YEAR";
        public static final String NET_INCOME_BKT_YEAR = "NET_INCOME_BKT_YEAR";
        public static final String REVENUE_GROWTH_ACT = "REVENUE_GROWTH_ACT";
        public static final String REVENUE_GROWTH_MAX = "REVENUE_GROWTH_MAX";
        public static final String REVENUE_GROWTH_MIN = "REVENUE_GROWTH_MIN";

        public static final String CODE = "CODE";
        public static final String SECTOR_CODE = "SECTOR_CODE";
        public static final String PUBLIC_COUNT = "PUBLIC_COUNT";
        public static final String PRIVATE_COUNT = "PRIVATE_COUNT";
        public static final String TOTAL = "TOTAL";
        public static final String PUBLIC_COMPANY_COUNT = "PUBLIC_COMPANY_COUNT";
        public static final String PRIVATE_COMPANY_COUNT = "PRIVATE_COMPANY_COUNT";
        public static final String TOTAL_COMPANY_COUNT = "TOTAL_COMPANY_COUNT";
        public static final String SUM_PRIVATE_COUNT = "sum(case when listing_status = 3 then 1 else 0 end) PRIVATE_COUNT";
        public static final String SUM_PUBLIC_COUNT = "sum(case when listing_status in (1,2) then 1 else 0 end) PUBLIC_COUNT";
        public static final String COMPANY_AGGREGATES_GROUP_COLUMNS = "SUBSTR(GICS_L4_CODE,1,2), COUNTRY_CODE ";
        public static final String SUB_STRING_SECTOR_COUNTRY = "SUBSTR(GICS_L4_CODE,1,2)SECTOR_CODE, COUNTRY_CODE";
        public static final String SUB_STRING_COUNTRY = "SUBSTR(GICS_L4_CODE,1,2), COUNTRY_CODE";
        public static final String CHANGE = "CHANGE";
        public static final String PREVIOUS_CLOSED = "PREVIOUS_CLOSED";

        // SUBSIDIARY_COMPANIES columns
        public static final String IS_FULLY_OWNED = "IS_FULLY_OWNED";
        public static final String SUBS_COMPANY_ID = "SUBS_COMPANY_ID";

        // ANALYST_FINANCIAL_NOTIFICATION columns
        public static final String ANALYST_NAME = "ANALYST_NAME";
        public static final String INTERESTED_COUNTRIES = "INTERESTED_COUNTRIES";
        public static final String INTERESTED_LISTING_TYPES = "INTERESTED_LISTING_TYPES";
    }

    public static final class NewsDatabaseColumns {
        private NewsDatabaseColumns() {
        }

        //News columns for IMDB and DB
        public static final String NEWS_ID = "NEWS_ID";
        public static final String SEQ_ID = "SEQ_ID";
        public static final String NEWS_PROVIDER = "NEWS_PROVIDER";
        public static final String ASSET_CLASS = "ASSET_CLASS";
        public static final String NEWS_DATE = "NEWS_DATE";
        public static final String COUNTRY = "COUNTRY";
        public static final String HOT_NEWS_INDICATOR = "HOT_NEWS_INDICATOR";
//        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String STATUS = "STATUS";
//        public static final String NEWS_HEADLINE = "NEWS_HEADLINE";
        public static final String EXCHANGE = "EXCHANGE";
        public static final String SYMBOL = "SYMBOL";
        public static final String EDITORIAL_CODE = "EDITORIAL_CODE";
        public static final String INDIVIDUAL_CODE = "INDIVIDUAL_CODE";
        public static final String INDUSTRY_CODE = "INDUSTRY_CODE";
        public static final String MARKET_SECTOR_CODE = "MARKET_SECTOR_CODE";
        public static final String APPROVAL_STATUS = "APPROVAL_STATUS";
        public static final String URL = "URL";
        public static final String COMPANY_ID = "COMPANY_ID";
        public static final String NEWS_SOURCE_ID = "NEWS_SOURCE_ID";
        public static final String NEWS_SOURCE_DESC = "NEWS_SOURCE_DESC";
        public static final String IS_TOP_STORY = "IS_TOP_STORY";
        public static final String TOP_NEWS_EDITION_SECTION = "TOP_NEWS_EDITION_SECTION";
        public static final String LAST_SYNC_TIME = "LAST_SYNC_TIME";
        public static final String TOP_STORY_SYNC_TIME = "TOP_STORY_SYNC_TIME";

        public static final String TICKER_ID = "TICKER_ID";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String EXT_ID = "EXT_ID";
        public static final String LANGUAGE_ID = "LANGUAGE_ID";
        public static final String NEWS_HEADLINE_LN = "NEWS_HEADLINE_LN";
        public static final String GEO_REGION_CODE = "GEO_REGION_CODE";
        public static final String GOVERNMENT_CODE = "GOVERNMENT_CODE";
        public static final String PRODUCT_SERVICES_CODE = "PRODUCT_SERVICES_CODE";
        public static final String INTERNAL_CLASS_TYPE = "INTERNAL_CLASS_TYPE";
        public static final String UNIQUE_SEQUENCE_ID = "UNIQUE_SEQUENCE_ID";
        public static final String INDIVIDUAL_ID = "INDIVIDUAL_ID";
        public static final String COUNTRY_CODE = "COUNTRY_CODE";
        public static final String NAME = "NAME";
        public static final String NEWS_CONTRIBUTED_DATE = "CREATED_DATE";
        public static final String NEWS_LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String GICSL3_DESC = "GICSL3_DESC";
    }

    public static final class NewsDatabaseVirtualColumns {
        private NewsDatabaseVirtualColumns() {
        }

        public static final String SECTION_TYPE = "SECTION_TYPE";
    }

    public static final class AnnouncementDatabaseColumns {
        private AnnouncementDatabaseColumns() {

        }

        public static final String ANN_ID = "ANN_ID";
        public static final String ANN_DATE = "ANN_DATE";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String TICKER_ID = "TICKER_ID";
        public static final String SECTOR_CODE = "SECTOR_CODE";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String ANN_SOURCE_SERIAL = "ANN_SOURCE_SERIAL";
        public static final String PRIORITY_ID = "PRIORITY_ID";
        public static final String STATUS = "STATUS";
        public static final String SEQ_ID = "SEQ_ID";
        public static final String DCMS_IDS = "DCMS_IDS";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";

        public static final class AnnManualTaggingColumns {
            AnnManualTaggingColumns() {
            }

            public static final String GICS_L3_CODE = "GICS_L3_CODE";
            public static final String GICS_L2_CODE = "GICS_L2_CODE";
        }

        public static final class AnnLangSpecificColumns {
            AnnLangSpecificColumns() {
            }

            public static final String ANN_HEADLINE = "ANN_HEADLINE";
            public static final String URL = "URL";
        }
    }

    public static final class DBTickerSnapshotColumns {
        private DBTickerSnapshotColumns() {
        }

        public static final String AVERAGE_TURNOVER_30D = "AVERAGE_TURNOVER_30D";
        public static final String AVERAGE_VOLUME_30D = "AVERAGE_VOLUME_30D";
        public static final String AVERAGE_TRANSACTION_VALUE_30D = "AVERAGE_TRANSACTION_VALUE_30D";
        public static final String BETA = "BETA";
        public static final String CURRENCY = "CURRENCY";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String HIGH = "HIGH";
        public static final String HIGH_PRICE_OF_52_WEEKS = "HIGH_PRICE_OF_52_WEEKS";
        public static final String LOW = "LOW";
        public static final String LOW_PRICE_OF_52_WEEKS = "LOW_PRICE_OF_52_WEEKS";
        public static final String MARKETCAP = "MARKETCAP";
        public static final String MARKETCAP_USD = "MARKETCAP_USD";
        public static final String NETCHANGE = "NETCHANGE";
        public static final String NO_OF_TRADES = "NO_OF_TRADES";
        public static final String PERCENT_CHANGE = "PERCENT_CHANGE";
        public static final String PREVIOUS_CLOSED = "PREVIOUS_CLOSED";
        public static final String TODAYS_CLOSED = "TODAYS_CLOSED";
        public static final String TICKER_ID = "TICKER_ID";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String TRADE_DATE = "TRADE_DATE";
        public static final String TRADE_PRICE = "TRADE_PRICE";
        public static final String TRADE_TIME = "TRADE_TIME";
        public static final String TURNOVER = "TURNOVER";
        public static final String VOLUME = "VOLUME";
        public static final String VWAP = "VWAP";
        public static final String YEAR_CHANGE = "YEAR_CHANGE";
        public static final String YEAR_PERCENTAGE_CHANGE = "YEAR_PERCENTAGE_CHANGE";
        public static final String YEAR_TURNOVER_RATIO = "YEAR_TURNOVER_RATIO";
        public static final String YTD_PRICE_CHANG = "YTD_PRICE_CHANG";
        public static final String YTD_PRICE_PERCENTAGE_CHANGE = "YTD_PRICE_PERCENTAGE_CHANGE";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String SUBSCRIPTION_STATUS = "SUBSCRIPTION_STATUS";
        public static final String BEST_BID_PRICE = "BEST_BID_PRICE";
        public static final String BEST_ASK_PRICE = "BEST_ASK_PRICE";
        public static final String OPEN_PRICE = "OPEN_PRICE";
        public static final String PREV_TRADE_TIME = "PREV_TRADE_TIME";
        public static final String PREV_TRADE_DATE = "PREV_TRADE_DATE";
    }

    public static final class DBMarketSnapshotColumns {
        private DBMarketSnapshotColumns() {
        }

        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String STATUS = "STATUS";
        public static final String SOURCE_SERIAL = "SOURCE_SERIAL";
        public static final String OPEN_TIME = "OPEN_TIME";
        public static final String CLOSE_TIME = "CLOSE_TIME";
        public static final String SHORT_DESCRIPTION_LAN = "SHORT_DESCRIPTION_LAN";
        public static final String LONG_DESCRIPTION_LAN = "LONG_DESCRIPTION_LAN";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String DEFAULT_CURRENCY = "DEFAULT_CURRENCY";
        public static final String TIMEZONE_ID = "TIMEZONE_ID";
        public static final String NETCHANGE = "NETCHANGE";
        public static final String DISPLAY_CODE = "DISPLAY_CODE";
        public static final String DEFAULT_DECIMAL_PLACES = "DEFAULT_DECIMAL_PLACES";
        public static final String IS_VIRTUAL_EXCHANGE = "IS_VIRTUAL_EXCHANGE";
        public static final String IS_EXPAND_SUBMKTS = "IS_EXPAND_SUBMKTS";
        public static final String IS_DEFAULT = "IS_DEFAULT";
        public static final String DEFAULT_WINDOW_TYPES = "DEFAULT_WINDOW_TYPES";
        public static final String SCREENER_CODE = "SCREENER_CODE";
        public static final String WEEK_START = "WEEK_START";
        public static final String MAIN_INDEX_SOURCE = "MAIN_INDEX_SOURCE";
        public static final String MAIN_INDEX_TICKER = "MAIN_INDEX_TICKER";
        public static final String COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COMPANY_ID = "COMPANY_ID";
        public static final String LISTED_STOCKS_COUNT = "LISTED_STOCKS_COUNT";
        public static final String SECTOR_COUNT = "SECTOR_COUNT";

        public static final String MARKET_ID = "MARKET_ID";
        public static final String DECIMAL_CORR_FACTOR = "DECIMAL_CORR_FACTOR";
        public static final String MARKET_STATUS = "MARKET_STATUS";
        public static final String LAST_EOD_DATE = "LAST_EOD_DATE";
        public static final String MARKET_DATE = "MARKET_DATE";
        public static final String MARKET_TIME = "MARKET_TIME";
        public static final String VOLUME = "VOLUME";
        public static final String TURNOVER = "TURNOVER";
        public static final String NO_OF_TRADES = "NO_OF_TRADES";
        public static final String SYMBOLS_TRADED = "SYMBOLS_TRADED";
        public static final String NO_OF_UPS = "NO_OF_UPS";
        public static final String NO_OF_DOWN = "NO_OF_DOWN";
        public static final String NO_OF_NO_CHANGE = "NO_OF_NO_CHANGE";
        public static final String IS_SUBMARKET = "IS_SUBMARKET";
        public static final String SUBSCRIPTION_STATUS = "SUBSCRIPTION_STATUS";
        public static final String MARKET_CAP = "MARKET_CAP";
        public static final String S_MARKET_CAP = "S_MARKET_CAP";
        public static final String SS_MARKET_CAP = "SS_MARKET_CAP";
    }

    public static final class DBIndicesColumns {

        private DBIndicesColumns() {
        }

        public static final String TICKER_ID = "TICKER_ID";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String INDEX_VALUE = "INDEX_VALUE";
        public static final String INDEX_OPEN = "INDEX_OPEN";
        public static final String ADJ_PREV_CLOSED = "ADJ_PREV_CLOSED";
        public static final String NET_CHANGE = "NET_CHANGE";
        public static final String PCNT_CHANGE = "PCNT_CHANGE";
        public static final String INDEX_HIGH = "INDEX_HIGH";
        public static final String INDEX_LOW = "INDEX_LOW";
        public static final String VOLUME = "VOLUME";
        public static final String TURNOVER = "TURNOVER";
        public static final String INDEX_TIME = "INDEX_TIME";
        public static final String WEIGHTED_INDEX_VALUE = "WEIGHTED_INDEX_VALUE";
        public static final String INDEX_CLOSE = "INDEX_CLOSE";
        public static final String DECIMAL_CORR_FACTOR = "DECIMAL_CORR_FACTOR";
        public static final String INSTRUMENT_TYPE = "INSTRUMENT_TYPE";
        public static final String TRANSACTION = "TRANSACTION";
        public static final String FORCASTED_INDEX_VALUE = "FORCASTED_INDEX_VALUE";
        public static final String FORCASTED_NET_CHANGE = "FORCASTED_NET_CHANGE";
        public static final String FORCASTED_PCT_CHANGE = "FORCASTED_PCT_CHANGE";
        public static final String IS_FORECASTING = "IS_FORECASTING";
        public static final String SESSION_PECENTAGE_CHANGE = "SESSION_PECENTAGE_CHANGE";
        public static final String WEEK_PECENTAGE_CHANGE = "WEEK_PECENTAGE_CHANGE";
        public static final String MONTH_PECENTAGE_CHANGE = "MONTH_PECENTAGE_CHANGE";
        public static final String YEAR_PECENTAGE_CHANGE = "YEAR_PECENTAGE_CHANGE";
        public static final String SESSION_CHANGE = "SESSION_CHANGE";
        public static final String WEEK_CHANGE = "WEEK_CHANGE";
        public static final String MONTH_CHANGE = "MONTH_CHANGE";
        public static final String YEAR_CHANGE = "YEAR_CHANGE";
        public static final String HIGH_52WEEK = "HIGH_52WEEK";
        public static final String LOW_52WEEK = "LOW_52WEEK";
        public static final String LAST_UPDATED_DATE_TIME = "LAST_UPDATED_DATE_TIME";
        public static final String SUBSCRIPTION_STATUS = "SUBSCRIPTION_STATUS";
        public static final String MARKET_CAP = "MARKET_CAP";
        public static final String CHANGE_IN_MARKET_CAP = "CHANGE_IN_MARKET_CAP";
        public static final String AVERAGE_30D_TRANSACTION = "AVERAGE_30D_TRANSACTION";
        public static final String AVERAGE_30D_TURNOVER = "AVERAGE_30D_TURNOVER";
        public static final String NO_OF_UPS = "NO_OF_UPS";
        public static final String NO_OF_DOWN = "NO_OF_DOWN";
        public static final String NO_OF_NO_CHANGE = "NO_OF_NO_CHANGE";
        public static final String SYMBOLS_TRADED = "SYMBOLS_TRADED";
        public static final String NUMBER_OF_TRADES = "NUMBER_OF_TRADES";
        public static final String IS_DELAYED = "IS_DELAYED";
        public static final String YTD_PRICE_CHANGE = "YTD_PRICE_CHANGE";
        public static final String YTD_PRICE_PERCENTAGE_CHANGE = "YTD_PRICE_PERCENTAGE_CHANGE";

    }

    public static final class DBCurrencyTickersColumns {

        private DBCurrencyTickersColumns() {
        }

        public static final String CURRENCY_PAIR = "CURRENCY_PAIR";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String CHANGE = "CHANGE";
        public static final String PERCENT_CHANGE = "PERCENT_CHANGE";
        public static final String RATE = "RATE";
        public static final String TRADE_TIME = "TRADE_TIME";
        public static final String TRADE_DATE = "TRADE_DATE";
        public static final String SUBSCRIPTION_STATUS = "SUBSCRIPTION_STATUS";
        public static final String HIGH = "HIGH";
        public static final String LOW = "LOW";
        public static final String OPEN_PRICE = "OPEN_PRICE";
        public static final String PREV_TRADE_TIME = "PREV_TRADE_TIME";
        public static final String PREV_TRADE_DATE = "PREV_TRADE_DATE";
    }

    public static final class DBCommoditySnapshotColumns {

        private DBCommoditySnapshotColumns() {
        }

        public static final String TICKER_ID = "TICKER_ID";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String CHANGE = "CHANGE";
        public static final String PERCENT_CHANGE = "PERCENT_CHANGE";
        public static final String TRADE_PRICE = "TRADE_PRICE";
        public static final String TRADE_TIME = "TRADE_TIME";
        public static final String TRADE_DATE = "TRADE_DATE";
        public static final String SUBSCRIPTION_STATUS = "SUBSCRIPTION_STATUS";
        public static final String SHORT_DESCRIPTION = "TICKER_SHORT_DES_LN";
        public static final String LONG_DESCRIPTION = "TICKER_LONG_DES_LN";
        public static final String CURRENCY_ID = "CURRENCY_ID";

        public static final String HIGH = "HIGH";
        public static final String LOW = "LOW";
        public static final String OPEN_PRICE = "OPEN_PRICE";
        public static final String PREV_TRADE_TIME = "PREV_TRADE_TIME";
        public static final String PREV_TRADE_DATE = "PREV_TRADE_DATE";
        public static final String INSTRUMENT_TYPE = "INSTRUMENT_TYPE";
    }

    public static final class FixedIncomeCountColumns {

        private FixedIncomeCountColumns() {
        }

        public static final String TYPE_COUNT = "TYPE_COUNT";
        public static final String INSTRUMENT_TYPE_ID = "INSTRUMENT_TYPE_ID";
        public static final String BOND_TYPE = "BOND_TYPE";
        public static final String TOTAL_AMOUNT_OUTSTANDING = "TOTAL_AMOUNT_OUTSTANDING";

    }

    public static final class MergeAndAcquireColumns {

        private MergeAndAcquireColumns() {
        }

        public static final String MA_ID = "MA_ID";
        public static final String TARGET_COMPANY_ID = "TARGET_COMPANY_ID";
        public static final String TARGET_COMPANY_COUNTRY_CODES = "TARGET_COMPANIES_COUNTRIES";
        public static final String TARGET_COMPANY_GICSL4_CODES = "TARGET_COMPANIES_GICSL4";
        public static final String PAYMENT_METHOD_ID = "PAYMENT_METHOD_ID";
        public static final String PAID_SHARES = "PAID_SHARES";
        public static final String PAID_CASH = "PAID_CASH";
        public static final String CURRENCY = "CURRENCY";
        public static final String DEAL_SIZE = "DEAL_SIZE";
        public static final String DEAL_SIZE_USD = "DEAL_SIZE_USD";
        public static final String PRE_OFF_ANN_DATE = "PRE_OFF_ANN_DATE";
        public static final String OFF_ANN_DATE = "OFF_ANN_DATE";
        public static final String MKT_AUTH_APP_DATE = "MKT_AUTH_APP_DATE";
        public static final String COMPLETION_DATE = "COMPLETION_DATE";
        public static final String DEAL_DATE = "DEAL_DATE";
        public static final String OWNED_BEF_PERCENTAGE = "OWNED_BEF_PERCENTAGE";
        public static final String TARGET_PERCENTAGE = "TARGET_PERCENTAGE";
        public static final String OFFERED_PERCENTAGE = "OFFERED_PERCENTAGE";
        public static final String ACCEPTED_PERCENTAGE = "ACCEPTED_PERCENTAGE";
        public static final String NEW_COMPANY_ID = "NEW_COMPANY_ID";
        public static final String RELATED_NC_CA_ID = "RELATED_NC_CA_ID";
        public static final String RELATED_LOAN_ID = "RELATED_LOAN_ID";
        public static final String MA_STATUS = "MA_STATUS";
        public static final String STATUS = "STATUS";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String ACQUIRED_COMPANIES = "ACQUIRED_COMPANIES";
        public static final String ACQUIRED_COMPANIES_COUNTRY_CODES = "ACQUIRED_COMPANIES_COUNTRIES";
        public static final String ACQUIRED_COMPANIES_GICSL4_CODES = "ACQUIRED_COMPANIES_GICSL4";
        public static final String ACQUIRER_FINANCIAL_ADVISERS = "ACQUIRER_FINANCIAL_ADVISORS";
        public static final String ACQUIRER_LEGAL_ADVISERS = "ACQUIRER_LEGAL_ADVISORS";
        public static final String TARGET_FINANCIAL_ADVISERS = "TARGET_FINANCIAL_ADVISORS";
        public static final String TARGET_LEGAL_ADVISERS = "TARGET_LEGAL_ADVISORS";
        public static final String DEAL_TYPES = "DEAL_TYPES";
        public static final String ANNOUNCEMENTS = "ANNOUNCEMENTS";
        public static final String NEWS = "NEWS";
        public static final String DOCUMENTS = "DOCUMENTS";
        public static final String MA_TYPE_ID = "MA_TYPE_ID";
        public static final String RELATED_DEBT_INSTRUMENTS = "RELATED_DEBT_INSTRUMENTS";
        public static final String STOCK_SYMBOL = "STOCK_SYMBOL";
        public static final String STOCK_EXCHANGE_RATIOS = "STOCK_EXCHANGE_RATIOS";
        public static final String TARGET_COMPANIES = "TARGET_COMPANIES";
        public static final String STATUS_DESCRIPTION = "STATUS_DESCRIPTION";
        public static final String DEAL_CLASSIFICATION = "DEAL_CLASSIFICATION";
        public static final String DEAL_TYPE_DESCRIPTION = "DEAL_TYPE_DESCRIPTION";
        public static final String PE_RATIO = "PE_RATIO";
        public static final String EV_SALES = "EV_SALES";
        public static final String EV_EBIT = "EV_EBIT";
        public static final String EV_EBITDA = "EV_EBITDA";
        public static final String PB_RATIO = "PB_RATIO";
    }

    public static final class DocFileColumns {

        private DocFileColumns() {
        }

        public static final String FILE_ID = "FILE_ID";
        public static final String REPORT_DATE = "REPORT_DATE";
        public static final String CATEGORY_ID = "CATEGORY_ID";
        public static final String SUB_CATEGORY_ID = "SUB_CATEGORY_ID";
        public static final String PROVIDER = "PROVIDER";
        public static final String PUBLISHER = "PUBLISHER";
        public static final String STATUS = "STATUS";
        public static final String SYMBOLS = "SYMBOLS";
        public static final String EXCHANGES = "EXCHANGES";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String DISPLAY_NAME = "DISPLAY_NAME";
        public static final String SUMMARY = "SUMMARY";
        public static final String FILE_GUID = "FILE_GUID";
        public static final String DEFAULT_FILE_LANG = "DEFAULT_FILE_LANG";
        public static final String LAST_SYNC_TIME = "LAST_SYNC_TIME";
        public static final String COMPANY_ID = "COMPANY_ID";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String GROUP_SERIAL = "GROUP_SERIAL";
        public static final String REGION_ID = "REGION_ID";
        public static final String PERIOD_ID = "PERIOD_ID";
        public static final String YEAR = "YEAR";
        public static final String INDUSTRY_CODE = "INDUSTRY_CODE";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COUNTRY_CODES = "COUNTRY_CODES";
        public static final String INDUSTRY_CODES = "INDUSTRY_CODES";
        public static final String INDUSTRY_CODES_DESC = "INDUSTRY_CODES_DESC";
        public static final String CLASSIFICATION_CODE = "CLASSIFICATION_CODE";
        public static final String CLASSIFICATION_SERIAL = "CLASSIFICATION_SERIAL";
    }

    public static final class PurchaseRecordColumns{
        private PurchaseRecordColumns(){}

        public static final String PURCHASE_ID = "PURCHASE_ID";
        public static final String REFERENCE_CODE = "REFERENCE_CODE";
        public static final String ITEM_ID = "ITEM_ID";
        public static final String ITEM_TYPE = "ITEM_TYPE";
        public static final String USER_ID = "USER_ID";
        public static final String INITIATE_DATE = "INITIATE_DATE";
        public static final String COMPLETE_DATE = "COMPLETE_DATE";
        public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
        public static final String PAYMENT_TYPE = "PAYMENT_TYPE";
        public static final String AMOUNT_USD = "AMOUNT_USD";
        public static final String MODERATOR_ID = "MODERATOR_ID";
    }

    public static final class DBWebRequestColumns {
        private DBWebRequestColumns() {
        }

        public static final String URL = "URL";
        public static final String INFO_PARAMS = "INFO_PARAMS";
    }

    public static final class LgDbJobColumns {
        private LgDbJobColumns() {
        }

        public static final String lastRunTime = "LAST_RUN_TIME";
        public static final String process = "PROCESS";
        public static final String status = "STATUS";
        public static final String rowCount = "ROW_COUNT";
        public static final String desc = "DESCRIPTION";
        public static final String params = "PARAMS";
        public static final String startTime = "START_TIME";
        public static final String endTime = "END_TIME";
        public static final String processId = "PROCESS_ID";
    }

    public enum DBDataSyncTypes {
        ALL("0"), IND_SNAP("1"), TICKERS("2"), FI_TICKERS("3"), FUND_TICKERS("4"), IND("5"), TICKER_DES("6"), TICKER_GICS("7"),
        COMP_DESC("8"), CURRENCY_RATES("9"), SOURCE("10"), SECTORS("11"), LISTED_INDXS("12"), RELTD_INDV("13"),
        SOURCES_MAIN_INDX("14"), IND_DETAILS("15"), UNLSTD_COMPS("16"), IPO_DATA("17"), MA_DATA("18"), KPI_DATA("19"),
        OWNERSHIP_LIMIT_DEF("20"), FILE_PUBLISHERS("21"), FUND_INVESTMENT_ALLOCATIONS("22"), IPO_GICS("23"), IPO_COMPANY_DESC("24"), DOC_FILE("25"),
        CITY("26"), TRADING_NAME("27"), COUNTRY("28"), INV_VAL("29"), PRICE_SNAPSHOT_ADJUSTED("30"), COMPANY_TYPE_DESC("31"), COUNTRY_INDICATORS("32"),
        COUNTRY_INDICATOR_MASTER("33"), COUNTRY_INDICATOR_GROUP_MASTER("34"), FUND_SNAPSHOT("35"), NEWS_HISTORY("36"),
        ANNOUNCEMENT_HISTORY("37"), TAG_NEWS_CID("38"), TAG_NEWS_TSID("39"), COUNTRY_MASTER_DATA("40"), TIMEZONE_DATA("41"),
        TOP_NEWS_HISTORY("42"), INDICES("43"), INDEX_SNAPSHOT("44"), FINANCIAL_ANALYST_PREFERENCE("45"), FINANCIAL_SEGMENT_DATA("46"),
        COMPANY_OWNER_COMPANY("47"), COMPANY_CPC("48"), COMPANY_SIZE_ACT("49"),COMPANY_SIZE_BKT("50"),REVENUE_GROWTH_ACT("51"),
        REVENUE_GROWTH_BKT("52"),CAL_COMPANY_GROWTH("53");

        private String defaultValue;

        private DBDataSyncTypes(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }


    }

    public static final class ProcedureTypes {
        private ProcedureTypes() {
        }

        public static final String PKG_MASTER_IND_DETAILS = "pkg_dfn_plus_data.insert_master_ind_details(?,?,?,?)";
        public static final String PKG_MASTER_SECTORS = "pkg_dfn_plus_data.insert_master_sectors(?,?,?)";
        public static final String PKG_COMPANY_DESC = "pkg_dfn_plus_data.update_company_desc(?,?,?,?,?)";
        public static final String PKG_CURRENCY_RATES = "pkg_dfn_plus_data.update_currency_rates(?,?,?,?)";
        public static final String PKG_UPDATE_GICS = "pkg_dfn_plus_data.update_gics(?,?,?,?,?,?)";
        public static final String PKG_INDIVIDUAL_SNAPSHOT = "pkg_dfn_plus_data.update_individual_snapshot(?,?,?,?)";
        public static final String PKG_LISTED_INDEXES = "pkg_dfn_plus_data.update_listed_indexes(?,?,?,?)";
        public static final String PKG_RELATED_INDIVIDUALS = "pkg_dfn_plus_data.update_related_individuals(?,?,?,?)";
        public static final String PKG_SOURCES = "pkg_dfn_plus_data.update_sources(?,?,?,?)";
        public static final String PKG_SOURCES_MAIN_INDEX = "pkg_dfn_plus_data.update_sources_main_index(?,?,?,?)";

        public static final String PKG_TICKERS = "pkg_dfn_plus_data.update_tickers(?,?,?,?,?,?)";

        public static final String PKG_EQUITY_TICKERS = "pkg_dfn_plus_data.update_equity_tickers(?,?,?,?,?)";
        public static final String PKG_FUND_TICKERS = "pkg_dfn_plus_data.update_fund_tickers(?,?,?,?,?)";
        public static final String PKG_FIXED_INCOME_TICKERS = "pkg_dfn_plus_data.update_fixed_income_tickers(?,?,?,?,?)";
        public static final String PKG_INDICES = "pkg_dfn_plus_data.update_indices(?,?,?,?,?)";

        public static final String PKG_PRICE_TICKERS = "pkg_dfn_plus_data.update_price_data(?,?,?,?,?)";
        public static final String PKG_TICKER_DESC = "pkg_dfn_plus_data.update_ticker_desc(?,?,?,?,?,?)";
        public static final String PKG_TRADING_NAME = "pkg_dfn_plus_data.update_trading_name(?,?,?,?,?)";
        public static final String PKG_UNLISTED_COMPANIES = "pkg_dfn_plus_data.update_unlisted_companies(?,?,?,?)";
        public static final String PKG_IPO_DATA = "pkg_dfn_plus_data.update_ipo_data(?,?,?,?)";
        public static final String PKG_MA_DATA = "pkg_dfn_plus_data.update_ma_data(?,?,?,?)";
        public static final String PKG_KPI_DATA = "pkg_dfn_plus_data.update_kpi_data(?,?,?,?)";
        public static final String PKG_OWNERSHIP_LIMIT_DEF = "pkg_dfn_plus_data.update_ownership_limit_def(?,?,?,?)";
        public static final String PKG_FILE_PUBLISHERS = "pkg_dfn_plus_data.update_file_publisher(?,?,?,?)";
        public static final String PKG_FUND_INVESTMENTS = "pkg_dfn_plus_data.update_fund_investments(?,?,?,?)";
        public static final String PKG_DCMS = "pkg_dfn_plus_doc_data.update_doc_files(?,?,?,?)";
        public static final String PKG_CITY = "pkg_dfn_plus_data.update_cities(?,?,?)";
        public static final String PKG_COUNTRY_TICKERS = "pkg_dfn_plus_data.update_country_tickers(?,?,?)";
        public static final String PKG_INV_VAL = "pkg_dfn_plus_data.update_investor_type_val_data(?,?,?,?)";
        public static final String PKG_PRICE_SNAPSHOT_ADJUSTED = "pkg_dfn_plus_data.update_ticker_snap_adjusted(?,?,?)";
        public static final String PKG_COMPANY_TYPE_DESC = "pkg_dfn_plus_data.update_company_types(?,?,?)";
        public static final String PKG_COUNTRY_INDICATORS = "pkg_dfn_plus_data.update_macro_indicator_values(?,?,?,?)";
        public static final String PKG_COUNTRY_INDICATOR_MASTER = "pkg_dfn_plus_data.update_macro_indicator_master(?,?,?)";
        public static final String PKG_COUNTRY_INDICATOR_GROUP_MASTER = "pkg_dfn_plus_data.update_macro_group_master(?,?,?)";
        public static final String PKG_NEWS_HISTORY = "pkg_dfn_plus_data.update_news_history(?,?,?,?)";
        public static final String PKG_ANNOUNCEMENT_HISTORY = "pkg_dfn_plus_data.update_announcement_history(?,?,?,?)";
        public static final String PKG_FUND_SNAPSHOT = "pkg_dfn_plus_data.update_fund_snapshot(?,?,?)";
        public static final String PKG_INDEX_SNAPSHOT = "pkg_dfn_plus_data.update_index_snapshot(?,?,?)";
        public static final String PKG_TAG_NEWS_CID = "pkg_dfn_plus_data.NEWS_TAG_BY_COMPANY_ID(?,?,?,?)";
        public static final String PKG_TAG_NEWS_TSID = "pkg_dfn_plus_data.NEWS_TAG_BY_TICKER_SERIAL(?,?,?,?)";
        public static final String PKG_COUNTRY_MASTER_DATA = "pkg_dfn_plus_data.update_country_master_data(?,?,?,?)";
        public static final String PKG_TIME_ZONE_DATA = "pkg_dfn_plus_data.update_time_zone_data(?,?,?,?)";
        public static final String PKG_TOP_NEWS_HISTORY_UPDATE = "pkg_dfn_plus_data.update_top_news_history(?,?,?,?)";

        public static final String PKG_FINANCIAL_ANALYST_PREFERENCE_UPDATE = "pkg_dfn_plus_data.update_financial_analyst_pref(?,?,?)";
        public static final String PKG_FINANCIAL_SEGMENT_DATA = "pkg_dfn_plus_data.update_financial_segment_data(?,?,?)";
        public static final String PKG_COMPANY_OWNER_COMPANY = "pkg_dfn_plus_data.update_company_owner_company(?,?,?,?)";
        public static final String PKG_COMPANY_CPC = "pkg_dfn_plus_data.update_cpc_lan(?,?,?,?)";
        public static final String PKG_COMPANY_SIZE_ACT = "pkg_dfn_plus_data.update_company_size(?,?,?,?)";
        public static final String PKG_COMPANY_SIZE_BKT = "pkg_dfn_plus_data.update_company_bkt(?,?,?,?)";
        public static final String PKG_REVENUE_GROWTH_ACT = "pkg_dfn_plus_data.update_revenue_growth(?,?,?,?)";
        public static final String PKG_REVENUE_GROWTH_BKT = "pkg_dfn_plus_data.update_revenue_growth_bkt(?,?,?,?)";
        public static final String PKG_CAL_COMPANY_GROWTH_RATE = "pkg_dfn_plus_data.calculate_revenue_growth_rate(?,?,?,?)";
    }

    public static final class TablesSP {
        private TablesSP() {
        }

        public static final String TABLE_TICKERS = "TICKERS";
        public static final String TABLE_FUND_TICKERS = "FUND_TICKERS";
        public static final String TABLE_FIXED_INCOME_TICKERS = "FIXED_INCOME_TICKERS";
        public static final String TABLE_IPO_MASTER = "IPO_MASTER";
        public static final String TABLE_INDICES = "INDEX_TICKERS";

    }

    public static final class TablesIMDB {
        private TablesIMDB() {
        }

        public static final String TABLE_TICKERS = "TICKERS";
        public static final String TABLE_FUND_TICKERS = "FUND_TICKERS";
        public static final String TABLE_FIXED_INCOME_TICKERS = "FIXED_INCOME_TICKERS";
        public static final String TABLE_SOURCES = "SOURCES";
        public static final String TABLE_IPO_MASTER = "IPO_MASTER";
        public static final String TABLE_MERGING_AND_ACQUISITION = "MERGING_AND_ACQUISITION";
        public static final String TABLE_KPI_MASTER = "KPI_MASTER";
        public static final String TABLE_DOC_FILES = "DOC_FILES";
        public static final String TABLE_DOC_COMPANIES = "DOC_COMPANIES";
        public static final String TABLE_DOC_SYMBOLS = "DOC_SYMBOLS";
        public static final String TABLE_DOC_EXCHANGES = "DOC_EXCHANGES";
        public static final String TABLE_DOC_COUNTRIES = "DOC_COUNTRIES";
        public static final String TABLE_DOC_REGIONS = "DOC_REGIONS";
        public static final String TABLE_DOC_PERIODS = "DOC_PERIODS";
        public static final String TABLE_DOC_INDUSTRIES = "DOC_INDUSTRIES";
        public static final String TABLE_DOC_SECTORS = "DOC_SECTORS";
        public static final String TABLE_NEWS = "NEWS";
        public static final String TABLE_ANNOUNCEMENTS = "ANNOUNCEMENTS";
        public static final String TABLE_FUND_BENCHMARKS = "FUND_BENCHMARKS";
        public static final String TABLE_TICKER_SNAPSHOT_ADJUSTED = "TICKER_SNAPSHOT_ADJUSTED";
        public static final String TABLE_TICKER_SNAPSHOT = "TICKER_SNAPSHOT";
        public static final String TABLE_INDEX_SNAPSHOT = "INDEX_SNAPSHOT";
        public static final String TABLE_INDEX_SNAPSHOT_DELAYED = "INDEX_SNAPSHOT_DELAYED";

        public static final String TABLE_INDICES = TablesSP.TABLE_INDICES;
    }

    public static final class TablesORACLE {
        private TablesORACLE() {
        }

        public static final String TABLE_TICKERS = "TICKERS";
        public static final String TABLE_FUND_TICKERS = "FUND_TICKERS";
        public static final String TABLE_FIXED_INCOME_TICKERS = "FIXED_INCOME_TICKERS";
        public static final String TABLE_SOURCES = "SOURCES";
        public static final String TABLE_IPO_MASTER = "IPO_MASTER";
        public static final String TABLE_MERGING_AND_ACQUISITION = "MERGING_AND_ACQUISITION";
        public static final String TABLE_KPI_MASTER = "KPI_MASTER";
        public static final String TABLE_KPI_DEFINITION_DESC = "KPI_DEFINITION_DESC";
        public static final String TABLE_DOC_FILES = "DOC_FILES";
        public static final String TABLE_DOC_COMPANIES = "DOC_COMPANIES";
        public static final String TABLE_DOC_SYMBOLS = "DOC_SYMBOLS";
        public static final String TABLE_DOC_EXCHANGES = "DOC_EXCHANGES";
        public static final String TABLE_DOC_COUNTRIES = "DOC_COUNTRIES";
        public static final String TABLE_DOC_REGIONS = "DOC_REGIONS";
        public static final String TABLE_DOC_PERIODS = "DOC_PERIODS";
        public static final String TABLE_DOC_INDUSTRIES = "DOC_INDUSTRIES";
        public static final String TABLE_DOC_SECTORS = "DOC_SECTORS";
        public static final String TABLE_NEWS = "NEWS";
        public static final String TABLE_NEWS_INDIVIDUALS = "NEWS_INDIVIDUALS";
        public static final String TABLE_ANNOUNCEMENTS = "ANNOUNCEMENTS";
        public static final String MACRO_ECONOMY_INDICATOR_VALUES = "MACRO_ECONOMY_INDICATOR_VALUES";
        public static final String MACRO_ECONOMY_INDICATOR_MASTER = "MACRO_ECONOMY_INDICATOR_MASTER";

        public static final String FUND_ASSET = "FUND_ASSET";
        public static final String FUND_SECTOR = "FUND_SECTOR";
        public static final String COUPON_DAY_COUNT_MAST = "COUPON_DAY_COUNT_MAST";
        public static final String TOP_NEWS_HISTORY = "TOP_NEWS_HISTORY";

        public static final String TABLE_STOCK_OWNERSHIP_VALUES = "STOCK_OWNERSHIP_VALUES";
        public static final String TABLE_OWNERSHIP_LIMIT_SERIES = "OWNERSHIP_LIMIT_SERIES";
        public static final String TABLE_STOCK_OWNERSHIP_LIMITS = "STOCK_OWNERSHIP_LIMIT";

        public static final String TABLE_FUND_BENCHMARKS = "FUND_BENCHMARK";
        public static final String TABLE_INDICES = TablesSP.TABLE_INDICES;

        public static final String TABLE_CLASSIFICATION_CODES = "CLASSIFICATION_CODES";
        public static final String TABLE_INDIVIDUAL_DETAILS = "INDIVIDUAL_DETAILS";
        public static final String COMPANY_PRODUCT_CLASSIFICATION = "COMPANY_PRODUCT_CLASSIFICATION";
        public static final String TABLE_COMPANY_SIZE = "COMPANY_SIZE";
        public static final String TABLE_SUBSIDIARY_COMPANIES = "SUBSIDIARY_COMPANIES";
        public static final String TABLE_USR_PURCHASE_RECORDS = "USR_PURCHASE_RECORDS";
        public static final String TABLE_USR_PURCHASE_RECORD_HISTORY = "USR_PURCHASE_RECORD_HISTORY";
    }

    public enum SolrCores{
        NEWS_CORE("news"),ANNOUNCEMENTS_CORE("announcements");

        private String defaultValue;

        SolrCores(String core){
            this.defaultValue = core;
        }

        public String getDefaultValue(){
            return defaultValue;
        }
    }

    public enum SolrResponseStatus{
        SOLR_SERVER_EXCEPTION(-1),REMOTE_SOLR_EXCEPTION(-2),SUCCEED(0), DEFAULT (1);

        private int defaultValue;

        SolrResponseStatus(int status){
            this.defaultValue = status;
        }

        public int getDefaultValue(){
            return defaultValue;
        }

        public static SolrResponseStatus valueOf(Integer i){
            SolrResponseStatus status;
            switch (i){
                case -1:
                    status = SOLR_SERVER_EXCEPTION;
                    break;
                case -2:
                    status = REMOTE_SOLR_EXCEPTION;
                    break;
                case 0:
                    status = SUCCEED;
                    break;
                default:
                    status = DEFAULT;
            }
            return status;
        }
    }

    public static final class CommonDatabaseParams {
        private CommonDatabaseParams() {
        }

        public static final String QUERY_SELECT = "SELECT ";
        public static final String QUERY_WHERE = " WHERE ";
        public static final String QUERY_UNION = " UNION ";
        public static final String QUERY_AND = " AND ";
        public static final String QUERY_ORDER = " ORDER BY ";
        public static final String NULLS_LAST = " NULLS LAST ";
        public static final String QUERY_BRACKET_OPEN = " (";
        public static final String QUERY_BRACKET_CLOSE = ") ";
        public static final String QUERY_COMMA = ",";
        public static final String QUERY_QUOTE = "'";
        public static final String QUERY_EQUAL = " = ";
        public static final String QUERY_TOP = " FETCH FIRST ";
        public static final String QUERY_ROWS_ONLY = " ROWS ONLY ";
        public static final String QUERY_OR = " OR ";
        public static final String QUERY_ON = " ON ";
        public static final String QUERY_NOT_NULL = " IS NOT NULL ";
        public static final String QUERY_NULL = " IS NULL ";
        public static final String QUERY_NOT_EQUALS = " <> ";
        public static final String UNION_ALL = " UNION ALL ";
        public static final String QUERY_LIKE = " LIKE ";
        public static final String QUERY_PREC = "%";
        public static final String QUERY_COUNT = " COUNT(*) ";
        public static final String QUERY_IN = " IN ";
        public static final String QUERY_NOT_IN = " NOT IN ";
        public static final String COUNTRY = "COUNTRY";
        public static final String SQL_VALUES = ") VALUES(";
        public static final String SQL_BRACKET_OPEN = "(";
        public static final String SQL_BRACKET_CLOSE = ")";
        public static final String SQL_COMMA = ",";
        public static final String SQL_QUESTION_MARK = "?";
        public static final String SQL_SELECT_MAX = "SELECT MAX ";
        public static final String SQL_AS = " AS ";
        public static final String SQL_OFFSET = " OFFSET ";
        public static final String SQL_ROWS = " ROWS ";
        public static final String SQL_DESC = " DESC ";
        public static final String QUERY_NOT_EQUAL = "<>";
        public static final String FROM = " FROM ";
        public static final String DELETE_FROM = "DELETE FROM ";
        public static final String EQUAL_QUETION_MARK = " = ? ";
        public static final String MINUS = " - ";
        public static final String MULTIPLY = "*";
        public static final String SYSDATE = "SYSDATE";
        public static final String HOURS = "24";
        public static final String MINUTES = "60";
        public static final String ZERO = "0";
        public static final String ONE = "1";

        public static final String QUERY_ASC_ORDER = " ASC ";
        public static final String QUERY_DESC_ORDER = " DESC ";
        public static final String QUERY_BETWEEN = " BETWEEN ";

        public static final String QUERY_SPACE = " ";

        public static final String QUERY_GREATER_OR_EQUAL_THAN = " >= ";
        public static final String QUERY_GREATER_THAN = " > ";
        public static final String QUERY_LESS_OR_EQUAL_THAN = " <= ";
        public static final String QUERY_LESS_THAN = " < ";

        public static final String QUERY_SUBSTR_FUNC = " SUBSTR";
        public static final String QUERY_CAST_FUNC = " CAST";
        public static final String QUERY_AS_NUMERIC = " AS NUMERIC";

        //In-built function apply for DERBY,ORACLE
        public static final String COALESCE = "COALESCE";
        public static final String QUERY_UPDATE = " UPDATE ";
        public static final String QUERY_SET = " SET ";
        public static final String QUERY_INSERT_INTO = " INSERT INTO ";

        public static final String QUERY_SELECT_ALL_FROM = "SELECT * FROM ";
        public static final String QUERY_SELECT_COUNT_ALL_FROM = "SELECT COUNT(*) FROM ";
        public static final String QUERY_SELECT_ONE_FROM = "SELECT 1 FROM ";

        public static final String QUERY_JOIN = " JOIN ";
        public static final String QUERY_LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";
        public static final String QUERY_INNER_JOIN = " INNER JOIN ";

        public static final String QUERY_STATUS_FILTER = DatabaseColumns.STATUS + " = 1 ";
        public static final String QUERY_IS_MAIN_STOCK = DatabaseColumns.IS_MAIN_STOCK + " = 1 ";
        public static final String QUERY_TOP_STORY_TO_BE_UPDATED = DatabaseColumns.IS_NEWS_TAB_UPDATED + " = 0 ";
        public static final String QUERY_TOP_STORY_UPDATED = DatabaseColumns.IS_NEWS_TAB_UPDATED + " = 1 ";
        public static final String QUERY_TOP_STORY = DatabaseColumns.IS_TOP_STORY + " = 1 ";

        public static final String QUERY_UPPER = " UPPER";
        public static final String QUERY_LOWER = " LOWER";

        public static final String QUERY_GROUP_BY = " GROUP BY ";
        public static final String QUERY_MAX = " MAX";
        public static final String QUERY_COUNT_FUNC = " COUNT";
        public static final String QUERY_DISTINCT = " DISTINCT ";
        public static final String QUERY_ROW_NUM_OVER = " ROW_NUMBER () OVER  ";
        public static final String QUERY_ROW_NUM_OVER_COMPLETE = " ROW_NUMBER () OVER (ORDER BY {0} {1}) AS {2} ";
        public static final String QUERY_COUNT_ALL = " COUNT(*)  ";
        public static final String CURRENT_DATE = " CURRENT_DATE ";

        public static final String QUERY_CASE = " CASE ";
        public static final String QUERY_WHEN = " WHEN ";
        public static final String QUERY_THEN = " THEN ";
        public static final String QUERY_ELSE = " ELSE ";
        public static final String QUERY_END = " END ";
        public static final String QUERY_AS = " AS ";
        public static final String QUERY_SUM = " SUM ";
        public static final String QUERY_NVL = " NVL";

        public static final String FUNC_ORACLE_TO_TIMESTAMP = "TO_TIMESTAMP";

        public static final String EXISTS = "EXISTS";
        public static final String FETCH_FIRST_SEVEN_ROWS = "FETCH FIRST 7 ROWS ONLY";
        public static final String FETCH_NEXT = " FETCH NEXT ";
        public static final String QUERY_DOT = ".";
    }

    public static final class DatabaseTypes {

        private DatabaseTypes() {
        }

        public static final int DB_TYPE_ORACLE = 2;
        public static final int DB_TYPE_H2 = 3;
        public static final int DB_TYPE_DERBY = 4;

    }

    public static final class DateTypes {

        private DateTypes() {
        }

        public static final int DAY = 1;
        public static final int MONTH = 2;

    }

    public static final class LangSpecificDatabaseColumns {
        private LangSpecificDatabaseColumns() {
        }

        public static final String COMPANY_NAME = "COMPANY_NAME_LAN_";
        public static final String TRADING_NAME = "TRADING_NAME_";

        public static final String IPO_STATUS_DESC = "IPO_STATUS_DESC_";
        public static final String IPO_TYPE_DESC = "IPO_TYPE_DESC_";

        public static final String IPO_COUNTRY_DESC = "COUNTRY_DESC_";
        public static final String IPO_COMPANY_NAME = "COMPANY_NAME_";

        public static final String GICS_L4_DESC = "GICS_L4_LAN_";
        public static final String GICS_L3_DESC = "GICS_L3_LAN_";
        public static final String GICS_L2_DESC = "GICS_L2_LAN_";

        public static final String IPO_GICS_L4_DESC = GICS_L4_DESC;
        public static final String IPO_GICS_L3_DESC = GICS_L3_DESC;
        public static final String IPO_GICS_L2_DESC = GICS_L2_DESC;

        public static final String ANN_HEADLINE = "ANN_HEADLINE_";
        public static final String ANN_URL = "URL_";

        //market fund investments
        public static final String TICKER_SHORT_DESCRIPTION = "TICKER_SHORT_DESCRIPTION_";
        public static final String TICKER_LONG_DESCRIPTION = "TICKER_LONG_DESCRIPTION_";
        public static final String SECTOR_NAME = "SECTOR_NAME_";
        public static final String COUNTRY_DESC = "COUNTRY_DESC_";
        public static final String TICKER_COUNTRY_DESC = "TICKER_COUNTRY_DESC_";
        public static final String FUND_NAME = "FUND_NAME_";

        public static final String FUND_ASSET_SHORT_DESC = "SHORT_DESCRIPTION_";
        public static final String FUND_ASSET_LONG_DESC = "LONG_DESCRIPTION_";
        public static final String FUND_SECTOR_SHORT_DESC = "SHORT_DESCRIPTION_";
        public static final String FUND_SECTOR_LONG_DESC = "LONG_DESCRIPTION_";

        // Country Column
        public static final String COUNTRY_SHORT_NAME = "SHORT_NAME_";
        public static final String COUNTRY_FULL_NAME = "FULL_NAME_";

        public static final String DOC_DISPLAY_NAME = "DISPLAY_NAME_";
        public static final String DOC_FILE_GUID = "FILE_GUID_";
        public static final String INDUSTRY_CODES_DESC = "INDUSTRY_CODES_DESC_";

        //individuals
        public static final String NAME_LANG = "NAME_";
        public static final String PREFIX_LANG = "PREFIX_";
        public static final String INDIVIDUAL_NAME_LANG = "INDIVIDUAL_NAME_";

    }


    public static final class SpCustomParams {
        private SpCustomParams() {
        }

        public static final String SCHEDULER_TYPE = "SCHEDULER_TYPE";
    }

    public static final String dateFormat = IConstants.DateFormats.FORMAT4;
    public static final String timeFormat = IConstants.DateFormats.FORMAT9;

    public static final class CustomParameters {
        private CustomParameters() {
        }

        public static final String INSERT_QUERY = "INSERT_QUERY";
        public static final String UPDATE_QUERY = "UPDATE_QUERY";
        public static final String DELETE_QUERY = "DELETE_QUERY";
        public static final String AVAILABLE_SEQ_ID_LIST = "AVAILABLE_SEQ_ID_LIST";
    }

    public static final String SQL_SELECT_ONLY = " SELECT {0} FROM {1}";

    public static final String SQL_SELECT_WHERE_ORDER = " SELECT {0} FROM {1} WHERE {2} ORDER BY {3} {4}";

    public static final String SQL_SELECT_WHERE = " SELECT {0} FROM {1} WHERE {2}";

    public static final String SQL_UPDATE = " UPDATE {0} SET {1} WHERE {2}";

    public static final String SQL_INSERT = " INSERT INTO {0}({1}) VALUES({2})";

    private static final String SQL_SET = " SET ";

    public static final class DatabaseAlias {
        private DatabaseAlias() {
        }

        public static String TICKERS_ALIAS = " t";
        public static String COMPANY_SIZE_ALIAS = " c";
    }

    public static final class SolrConstants {
        private SolrConstants(){
        }
        public static final String TO = " TO ";
        public static final String SYSDATE = "NOW";
        public static final String DEFAULT_TIME_UNIT = "MINUTE";
        public static final String DESCENDING_ORDER= "desc";
        public static final String ANN_DATE = "ann_date";
        public static final String SEQ_ID = "seq_id";
        public static final String COUNTRY_EXACT_MATCH = "country_exact_match";
    }

    public enum SolrRequestHandlers {
        DATA_IMPORT("/dataimport");

        private String defaultValue;

        SolrRequestHandlers(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}


