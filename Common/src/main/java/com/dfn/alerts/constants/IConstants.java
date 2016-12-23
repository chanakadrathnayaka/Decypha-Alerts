package com.dfn.alerts.constants;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/26/12
 * Time: 5:04 PM
 */

public interface IConstants {

    String HED = "HED";

    static final String NEWS_SOURCE_FIELDS = "SOU,SCODE";
    public static final String BOND_TYPE = "bt";
    public static final String SUKUK_TYPE = "st";
    public static final String ISSUER_NAME = "in";
    public static final String LANGUAGE = "lan";
    public static final String COUPON_FREQUENCY = "cf";
    public static final String INDUSTRY = "ind";
    public static final String MATURITY_DATE = "md";
    public static final String DATE_OF_ISSUE = "id";
    public static final String SHORT_DESC_LAN = "sym";
    public static final String COUNTRY_CODE = "cnt";
    public static final String COUNTRY_DESC = "cntd";
    public static final String FOCUSED_COUNTRIES = "ffcnt";
    public static final String MATCH_EACH_COUNTRY_OR_REGION = "mec";
    public static final String FOCUSED_REGIONS = "ffreg";
    public static final String TICKER_COUNTRY = "cnti";
    public static final String MATURITY_PERIOD = "mp";
    public static final String ISSUE_WGT_AVG_RATE = "iwar";
    public static final String AMOUNT_ISSUED = "ai";
    public static final String AMOUNT_ISSUED_USD = "aiu";
    public static final String AMOUNT_ISSUED_USD_LATEST = "aiul";
    public static final String REQUIRED_AMOUNT = "ra";
    public static final String COUPON_RATE = "cr";
    public static final String ISSUE_MIN_RATE = "iminr";
    public static final String ISSUE_MAX_RATE = "imaxr";
    public static final String CURRENCY = "cur";
    public static final String SYMBOL = "s";
    public static final String DISPLAY_TICKER = "dtic";
    public static final String EXCHANGE = "e";
    public static final String DECIMAL_PLACES = "dp";
    public static final String COMPANY = "comp";
    public static final String COMPANY_ID = "cid";
    public static final String TICKER_LONG = "tikL";
    public static final String TICKER_SHOT = "tikS";
    public static final String TICKER_ID = "tikId";
    public static final String TICKER_SERIAL = "tikSer";
    public static final String INSTRUMENT_TYPE = "ins";
    public static final String FIXED_INCOME_TYPE = "t";
    public static final String FUND_CLASS = "fc";
    public static final String TNA_USD = "tna";
    public static final String TNA_USD_VALID = "tnaValid";
    public static final String CHANGE_PER_YTD = "ytd";
    public static final String CHANGE_PER_3M = "3mpc";
    public static final String MANAGED_COMPANIES = "mc";
    public static final String ISSUED_COMPANIES = "ic";
    public static final String BENCHMARK = "b";
    public static final String CHANGE_PER_YEAR = "ypc";
    public static final String ESTB_DATE = "ed";
    public static final String TICKER_DATA = "tikd";
    public static final String NEXT_COUPON_DATE = "ncd";
    public static final String AUCTION_DATE = "ad";
    public static final String GICSL3_DESC = "gicsd";
    public static final String GICSL3_CODE = "gicsc";
    public static final String IPO_TYPE = "ipod";
    public static final String IPO_TYPE_DESC = "ipotd";
    public static final String IPO_STATUS = "ipos";
    public static final String IPO_STATUS_DESC = "iposd";
    public static final String ACTIVE_NEWS_ID = "ani";
    public static final String DOCUMENT_ID = "docu";
    public static final String START_DATE = "sdate";
    public static final String END_DATE = "edate";
    public static final String YTM = "ytm";
    public static final String UNIFY_CURRENCY = "ucurr";
    public static final String DURATION = "dur";
    public static final String END = "E";
    public static final String LEVEL = "L";

    //MA/ipo List
    public static final String MA_ID = "id";
    public static final String IPO_ID = "id";
    public static final String LATEST_DATE = "date";
    public static final String DEAL_DATE = "dealDate";
    public static final String TARGET_COMPANIES = "tc";
    public static final String TARGET_COMPANIES_COUNTRY_CODES = "tccc";
    public static final String TARGET_COMPANIES_GICS_CODES = "tcgc";
    public static final String ACQUIRED_COMPANIES = "ac";
    public static final String ACQUIRED_COMPANIES_COUNTRY_CODES = "accc";
    public static final String ACQUIRED_COMPANIES_GICS_CODES = "acgc";
    public static final String PAYMENT_ID = "pid";
    public static final String MA_STATUS = "status";
    public static final String MA_RATIO_PE = "pe";
    public static final String FROM_DATE = "from_date";
    public static final String TO_DATE = "to_date";
    // fund screener
    public static final String MANAGED_COUNTRY = "mcnt";
    public static final String ISSUED_COUNTRY = "icnt";
    public static final String MONTHLY_RETURN_3Y = "3ymr";
    public static final String MONTHLY_RETURN_5Y = "5ymr";
    public static final String ANNUAL_RETURN_3Y = "3yar";
    public static final String ANNUAL_RETURN_5Y = "5yar";
    public static final String STD_DEVIATION_ANNUAL_RETURN_3Y = "3yarsd";
    public static final String STD_DEVIATION_ANNUAL_RETURN_5Y = "5yarsd";
    public static final String FUND_DURATION = "fdur";
    //fund fees
    public static final String SUBSCRIPTION_FEE = "subFee";
    public static final String SERVICE_FEE = "serFee";
    public static final String ADMIN_FEE = "admFee";
    public static final String MGT_FEE = "mgtFee";
    //jasper reports
    public static final String LISTED_COMP_JASPER = "CompanyProfile.jasper";
    public static final String UNLISTED_COMP_JASPER = "miniCompanyProfile.jasper";
    public static final String REPRESENTATIVE = "Representative";
    public static final String OF = "of";
    public static final String FOR = "for";
    public static final String AS_A = "As a";
    public static final String INDIVIDUAL_PROFILE = "/individual-profile/";
    public static final String COMPANY_PROFILE = "/company-profile/";
    public static final String INDIVIDUAL_REPRESENTATIVE = "INDIVIDUAL_REPRESENTATIVE";
    public static final String COMP_PROF_MGT_DATA_SOURCE = "ComProfManagersDataSource";
    public static final String MANAGEMENT_DATA_SOURCE = "ManagementDataSource";
    public static final String COMP_CURRENCY = "COMP_CURRENCY";
    public static final String FYS_MONTH = "FYS_MONTH";
    public static final String INDIVIDUAL_URL = "individualUrl";
    public static final String DATE = "date";
    public static final String COMPANY_NAME = "companyName";

    //************************REQUEST TYPE DEFINITIONS END******************************************************
    public static final String CUSTODIAN_FEE = "cusFee";
    public static final String PERFORMANCE_FEE = "perFee";
    public static final String REDEMPTION_FEE = "rdmFee";
    public static final String MIN_SUBSCRIPTION = "minSub";
    public static final String SUBSEQUENT_SUBS = "subSub";
    public static final String OTHER_EXP = "otrExp";
    public static final String FUND_ISSUERS = "fi";
    public static final String UPDATE_FREQUENCY = "uf";
    public static final String FUND_FEE1 = "f1";
    public static final String FUND_FEE2 = "f2";
    public static final String SORT_ORDER = "sord";
    public static final String SORT_FIELD = "sidx";
    public static final String PAGE_INDEX = "page";
    public static final String PAGE_SIZE = "rows";
    public static final String PAGINATION = "pg";
    public static final String REGION = "region";
    public static final String COUNTRY_LIST = "country";
    public static final int DEFAULT_PAGE_SIZE_5 = 5;
    public static final int DEFAULT_PAGE_SIZE_20 = 20;
    public static final int DEFAULT_PAGE_SIZE_100 = 100;
    public static final int DEFAULT_EXCEL_EXPORT_SIZE = 1000;
    public static final int DEFAULT_PAGE_SIZE_15 = 15;
    public static final int DEFAULT_PAGE_INDEX_0 = 0;
    public static final int DEFAULT_PAGE_INDEX_1 = 0;
    public static final int MILLION = 1000000;
    public static final int THOUSAND = 1000;
    public static final String MENA_REGION_ID = "20";
    public static final String DEFAULT_REGION_MENA = "17";
    public static final String DEFAULT_COUNTRY_EG = "EG";
    public static final String DEFAULT_SORT_ORDER_DESC = "DESC";
    public static final String DEFAULT_SORT_ORDER_ASC = "ASC";
    public static final String ASTERISK = "*";
    public static final String NEGATIVE_ONE = "-1";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String COMMA_WITH_SPACE = ", ";
    public static final String SPACE = " ";
    public static final int MANAGERS = 1;
    public static final int ISSUERS = 2;
    public static final int BENCHMARKS = 3;
    public static final double D_NEGATIVE_ONE = -1d;
    public static final String FILE_DETAILS = "fileDetails";
    public static final int INDEX_0 = 0;
    public static final String TILDE = "~";
    //Market Overview- index table
    public static final String M_SYMBOL = "SYMBOL";
    public static final String M_VALUE = "VALUE";
    public static final String VALUE = "value";
    public static final String M_PER_CHANGE = "PER_CHANGE";
    public static final String M_CHANGE = "CHANGE";
    public static final String M_TICKER_ID = "TICKER_ID";
    public static final String M_TURNOVER = "TURNOVER";
    public static final String M_SYMBOL_DES = "SYMBOL_DES";
    public static final String M_DP = "DP";
    public static final String M_RID = "RID";
    public static final String COMP_NAME = "COMP_NAME";
    public static final String COUNTRY = "COUNTRY";
    public static final String CTRY_CODE = "CTRY_CODE";
    public static final String COM_ID = "COMPANY_ID";
    public static final String GICS_L3_DESC = "GICS_L3_DESC";
    public static final String CITY = "CITY";
    public static final String ORDINARY = "ORDINARY";
    public static final String COMPANY_TYPE = "COMPANY_TYPE";
    public static final String WEB = "WEB";
    public static final String DISPLAY_EXCHANGE = "DISPLAY_EXCHANGE";
    //MA screener
    public static final String MA_SCREENER_DATE = "date";
    public static final String MA_SCREENER_TARGET_COUNTRY = "maTargetCountry";
    public static final String MA_SCREENER_TARGET_GIGS = "maTargetGIGS";
    public static final String MA_SCREENER_AQUARE_COUNTRY = "maAquareCountry";
    public static final String MA_SCREENER_AQUARE_GIGS = "maAquareGIGS";
    public static final String MA_SCREENER_STATUS = "maStatus";
    public static final String MA_SCREENER_PAYMENT = "maPayment";
    public static final String MA_SCREENER_TARGETNAME = "maTargetName";
    public static final String MA_SCREENER_AQUARERNAME = "maAquarerName";
    public static final String MA_SCREENER_DEALFROM = "maDealFrom";
    public static final String MA_SCREENER_DEALTO = "maDealTo";

    //Index Screener
    public static final String LAST_TRADE_DATE = "ltd";
    public static final String SYMBOL_DESC = "symDesc";
    public static final String TYPE = "type";
    public static final String EXCHANGES = "exgs";
    public static final String COUNTRIES = "countries";
    public static final String REGIONS = "regions";
    public static final String PROVIDERS = "prv";
    public static final String TICKER_CLASS_L2 = "typeTCL2";
    public static final String RQ = "rq";
    public static final String RTYPE = "rType";
    public static final String PKEY = "pKey";
    public static final String CKEY = "cKey";
    public static final String PGS = "pgs";
    public static final String CRITERIA_SET = "criteriaSet";
    public static final String NA_VAL = "--";
    public static final String PERCENTAGE_MARK = "%";
    public static final String STOCK_OWNERSHIP_CURRENT = "socu";
    public static final String STOCK_OWNERSHIP_COMPARATIVE = "soco";
    public static final String NO_OF_SHARES = "nos";
    public static final String SERIES_ID = "si";
    public static final String OWNERSHIP_DATE = "od";
    public static final String FILTER_BY_DATE = "fd";
    public static final String categories = "categories";
    public static final String series = "series";
    public static final String name = "name";
    public static final String data = "data";
    public static final int STOCK_OWNERSHIP_MTD = 100;
    public static final int STOCK_OWNERSHIP_QTD = 200;
    public static final int STOCK_OWNERSHIP_YTD = 300;
    public static final int STOCK_OWNERSHIP_CUSTOM = 400;
    public static final String DEFAULT_CURRENCY_EXCHANGE = "PFX";
    public static final String RELATED_TICKERS = "RELATED_TICKERS";
    public static final String PUBLISHERS = "PUBLISHERS";
    public static final String MARKETS = "MARKETS";
    public static final String DEFAULT_COUNTRY = "*"; //Default country
    public static final String DEFAULT_REGION = "GCC-17";   //Default region
    public static final String FUND_POSITION_SYMBOL = "fsts";
    public static final String FUND_POSITION_START_DATE = "fssd";
    public static final String FUND_POSITION_END_DATE = "fsed";
    public static final String MIX_DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String NEWS_TAG_FIELDS = "nTagF";
    public static final String ANNOUNCEMENT_TAG_FIELDS = "aTagF";
    public static final String DEFAULT_PARAM_VALUE = "-1";
    public static final String DEFAULT_REPORTS_PAGE_INDEX = "0";
    public static final String DEFAULT_REPORTS_PAGE_SIZE = "20";
    public static final String DEFAULT_REPORTS_SORT_ORDER = "DESC";
    public static final String DEFAULT_REPORTS_SORT_FIELD = "REPORT_DATE";
    public static final int DB_TYPE_ORACLE = 1;
    public static final int DB_TYPE_IMDB = 2;
    public static final int NEWS_NODE_STATUS_DEFAULT = -1;
    public static final int NEWS_NODE_STATUS_AVAILABLE_IN_IMDB = 1;
    public static final int NEWS_NODE_STATUS_NOT_AVAILABLE_IN_IMDB = 2;
    public static final int MAX_ANN_NO_OF_CHAR = 200;
    public static final int MAX_ANN_HEADLINE_LENGTH = 2000;
    public static final String IS_VIRTUAL = "1";
    public static final String SEO_LABEL_PREFIX = "seo.";
    public static final String SEO_PAGE_DESCRIPTION_SUFFIX = "description";
    public static final String SEO_PAGE_KEYWORDS_SUFFIX = "keywords";
    public static final String CURRENCY_SOURCE_ID_PLUS_TILDA = "PFX~";
    public static final String NEWS_SEARCH_TYPE = "NEWS_SEARCH_TYPE";
    //corporate Actions
    public static final String CA_CAPITAL_DATA = "caCapitalData";
    public static final String CA_NON_CAPITAL_DATA = "caNonCapitalData";
    public static final String CHART_PERIOD = "chartPeriod";
    public static final int NEGATIVE_NUM_1 = -1;
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    //lates company earnings
    public static final String ANNOUNCE_DATE = "ANNOUNCE_DATE";
    public static final String EXCHANGE_DES = "EXCHANGE_DES";
    public static final String SYMBOL_DES = "SYMBOL_DES";
    public static final String PERIOD_ID = "PERIOD_ID";
    public static final String ANNOUNCED_VALUE = "ANNOUNCED_VALUE";
    public static final String COMPARED_VALUE = "COMPARED_VALUE";
    public static final String CURENCY = "CURRENCY";
    public static final String CHANGE = "CHANGE";
    public static final String PER_CHANGE = "PER_CHANGE";
    public static final String PER = "PER";

    public static final String FIRST_PAGE_INDEX = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final int MONTHS_FOR_QUARTER = 3;
    public static final String QUARTER = "Q";

    public static final int NUM_1 = 1;
    public static final int NUM_2 = 2;
    public static final int NUM_6 = 6;
    public static final String MUBAHER_NOT_NORMAL_NEWS_INDICATOR = "3";
    public static final String DEFAULT_LANGUAGE = "EN";
    public static final String PAGE_SIZE_5 = "5";

    //response codes
    public static final int RESPONSE_OK = 200;
    public static final String RESPONSE_NEWS_SELECTED_LANGUAGES = "NSL";

    public static final String PUBLIC = "PUBLIC";
    public static final String PRIVATE = "PRIVATE";
    public static final String TOTAL = "TOTAL";
    public static final String PUBLIC_COMPANY_COUNT = "PUBLIC_COMPANY_COUNT";
    public static final String PRIVATE_COMPANY_COUNT = "PRIVATE_COMPANY_COUNT";
    public static final String TOTAL_COMPANY_COUNT = "TOTAL_COMPANY_COUNT";
    public static final String USD = "USD";
    public static final String DEFAULT_DELAYED_EXCHANGE ="MSM";

    //agreggated news
    public static final int MUBASHER_EXCLUSIVE = 301;
    public static final int MUBASHER = 302;
    public static final int DOW_JONES = 306;

    public static final String IS_FREE_USER = "IS_FREE_USER";
    public static final String DELAYED_SOURCES = "DELAYED_SOURCES";

    // report
    public static final String REPORT_CATEGORY_FUND = "8";

    public static final int MINUTE_IN_SECONDS = 60;
    public static final int SECOND_IN_MILLI = 1000;

    // analyst region preference
    public static final String EMAIL = "EMAIL";
    public static final String ANALYST_NAME = "ANALYST_NAME";
    public static final String INTERESTED_COUNTRIES = "INTERESTED_COUNTRIES";
    public static final String LISTING_TYPES = "LISTING_TYPES";

    //feedback
    public static final String CASE_NUM_PREFIX = "DECYPHACASE-";
    public static final String FEEDBACK_CASE = "Case:";
    public static final String FEEDBACK_YOUR_CASE = "Your Case";
    public static final String FEEDBACK_PRODUCT_DATA = "Product/Data";
    public static final String FEEDBACK_FOR_USER = "for user";
    public static final String FEEDBACK_CURRENT_URL = "Current URL";
    public static final String FEEDBACK_PREFERRED_CONTACT= "Preferred Contact Method";

    //decypha mobile
    public static final String CLIENT_TYPE= "client-type";

    //top news editions * maximum top news result for each edition
    public static final int TOP_NEWS_EDITION_TOTAL_COUNT = 2000;


    /* Keep default values for general request parameters */
    public enum RequestParameters {
        SID("DECYPHA"), UID("DECYPHA"), L("EN"), PGS("1000");

        private String defaultValue;

        private RequestParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for top stocks parameters */
    public enum TopStockParameters {

        RT("57"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), TYP("TOPSTK"), SO(""), SF(""), UE(""), OPTNL("");

        private String defaultValue;

        private TopStockParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for news parameters */
    public enum NewsParameters {
        RT("27"), SID("DECYPHA"), UID("DECYPHA"), E(""), UE(""), IFLD(""), NI(""), AE("0"), SD(""), ED(""), H("1"), M("1"), ST(HED), SK(""), S(""),
        XFLD(""), PRV(""), CAT(""), L("AR"), UNC("0"), PGI(""), PGS("1000"), CC(""), PRIO(""), AST(""), GEO(""), INDV(""), INDST(""), MKT(""),
        PRODSRV(""), SUBCAT(""), APPRVSTAT(""), SI(""), TL(""), NID(""), CREUSR(""), LUPDUSR(""), SOU(""), STATUS(""), DL(""), TSID(""), AN("2"), CID("");

        private String defaultValue;

        private NewsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for news parameters */
    public enum TopNewsParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT("NEWS_TOP"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), PDM("");

        private String defaultValue;

        private TopNewsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for news  details parameters */
    public enum NewsDetailsParameters {
        RT("28"), SID("DECYPHA"), UID("DECYPHA"), UE("*"), NI(""), H("1"), M("1"), L("EN"), DL(""), UNC("0"), AN("0");

        private String defaultValue;

        private NewsDetailsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum AnnouncementDetailsParameters {

        RT("26"), SID("DECYPHA"), UID("DECYPHA"), UE(""), AI(""), H("1"), M("1"), L("AR"), UNC("0"), DL("");

        private String defaultValue;

        private AnnouncementDetailsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    /* Keep default values for Announcement parameters */
    public enum AnnouncementParameters {
        RT("25"), SID("DECYPHA"), UID("DECYPHA"), E(""), UE(""), AI(""), AE("0"), SD(""), ED(""), H("1"), M("1"), ST(HED), SK(""), S(""), XFLD(""), PRIO(""),
        STATUS(""), SI(""), DL(""), L("EN"), UNC("1"), PGI(""), PGS("1000"), AA("1"), TSID(""), AID("");
        private String defaultValue;

        private AnnouncementParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for price snapshot parameters */
    public enum PriceSnapshotParameters {
        RT("2"), SID("DECYPHA"), UID("DECYPHA"), LI("0"), E(""), UE(""), AS("1"), SS(""), IR("0"), IFLD(""), XFLD(""), EC("1"), H("0"), M("0");

        private String defaultValue;

        private PriceSnapshotParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for price history parameters */
    public enum PriceHistoryParameters {
        RT("37"), SID("DECYPHA"), UID("DECYPHA"), LI("0"), E(""), S(""), INS("1"), CT("7"), CM("3"), PGS("1000"), UE("0"), SO(""), M("0"), H("1"), SD(""),
        ED(""), NOD("0 "), PGI("0"), AS("0");

        private String defaultValue;

        private PriceHistoryParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    /**
     * Keep default values for Insider Transaction parameters
     */
    public enum InsiderTransactionParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT("INTR"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private InsiderTransactionParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for Insider Transaction parameters
     */
    public enum FairValuesParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT("FRVL"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private FairValuesParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep additional response tags for previous fair value data
     */
    public enum InfoFairValuesTags {
        FV_UP("FV_UP"), PRV_FV_DATE("FV_DATE"), PRV_FV_RATING_SCORE("FV_RATING_SCORE"), PRV_FAIR_VALUE("FAIR_VALUE"), PRV_ADJ_FAIR_VALUE("ADJUSTED_FAIR_VALUE"), PRV_FV_RATING_NAME("FV_RATING_NAME"), PRV_DOC_ID("DOC_ID");

        private String mappingTag;

        private InfoFairValuesTags(String mp) {
            this.mappingTag = mp;
        }

        public String getMappingTag() {
            return mappingTag;
        }
    }

    /**
     * Keep default values for corporate actions parameters
     */
    public enum CorporateActionsParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E("");

        private String defaultValue;

        private CorporateActionsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for Announcement parameters */
    public enum SymbolDataParameters {
        RT("52"), SID("DECYPHA"), UID("DECYPHA"), E(""), UE(""), L("EN"), AE("0"), AS("0"), S(""), SC(""), PGI("0"),
        PGS("10000"), ISYM(""), INS("-1"), UNC("0"), XFLD(""), IFLD("GICSL1,GICSL2,GICSL3,GICSL4"), SPCODE(""), DFNS(""), M("0"), H("0");

        private String defaultValue;

        private SymbolDataParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for report search */
    /*production-SID("BD584E3E-DB9B-9ECF-E040-007F01005D49"), UID("112859")*/
    /*changed for UAT*/
    public enum ReportSearchParameters {
        RT("97"), SID("BD584E3E-DB9B-9ECF-E040-007F01005D49"), UID("111009"), DES(""), E(""), /*I(""),*/ CAT(""), SCAT(""),
        L(""), CTI(""), SO("DESC"), SF("REPORT_DATE"), PGI("0"), PGS("1000"), UNC("0"), STK(""), FDK(""), PL(""), ITK(""), FC("1"), IFC("1");

        private String defaultValue;

        private ReportSearchParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    /* Keep default values for company event parameters */
    public enum CalendarEventParameters {

        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), UNC("0"), SCDT("CALEVT"), SF(""), H("1"), SO("DESC"), FC("1"), IFC("1"), PGI(""), PGS("1000"), ITK(""), SD(""), ED(""), FDK(""), E(""),
        STK("");

        private String defaultValue;

        private CalendarEventParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for company event parameters */
    public enum CalendarEventMetaDataParams {

        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), UNC("0"), SCDT("CALMG"), SF(""), H("1"), SO(""), FC("1");

        private String defaultValue;

        private CalendarEventMetaDataParams(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum InvestmentsParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT("SUBS"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private InvestmentsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for company profile parameters */
    public enum CompanyProfileParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), CIT("CP,INMGT,FFHIST,STK,GRPHIST,COMGTREP,COMGT"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), CID("");

        private String defaultValue;

        private CompanyProfileParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    public enum CompanyManagementParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), CIT("INMGT,COMGT,COMGTREP"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), CID("");

        private String defaultValue;

        private CompanyManagementParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    public enum CompanyOwnersParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), CIT("OWN_CMP,OWN_IND,FNINAL"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private CompanyOwnersParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    public enum MacroEconomyParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), CIT("COUN,EXCHG,CP,CITY"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private MacroEconomyParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    /* Keep default values for fund profile parameters */
    public enum FundProfileParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), CIT("MFUND,FNFCRG,FNBMK,FNFCCN"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private FundProfileParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    /* Keep default values for bond profile parameters */
    public enum BondProfileParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), CIT("FINC"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private BondProfileParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    public enum FinancialParameters {
        RT("131"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), UNC("0"), L("EN"), CFT(""), S("DFM~EMAAR"), CID(""),
        Q(""), Y(""), FY(""), TY(""), FQ(""), TQ(""), ROW(""), PRTCL(""), CFXI(""), FC("1"),
        DES("1"), VT(""), SD(""), ED(""), IFC("1"), LVL(""), FST(""), ATTR(""), GRP(""), ACCR(""), CAL(""), LONGDES(""), FRM(""), AVG(""), DOM("");

        private String defaultValue;

        private FinancialParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    public enum FinancialAverageRatioParameters {
        RT("133"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), DES("1"), FC("1"), Q(""), Y(""), CAL(""), DOM(""), RGID(""), TMPL("");

        private String defaultValue;

        private FinancialAverageRatioParameters(String defValue) {
            this.defaultValue = defValue;

        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }

    /* Keep default values for Earnings parameters */
    public enum EarningsParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E("");

        private String defaultValues;

        private EarningsParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    /* Keep default values for individuals search parameters */
    public enum IndividualsSearchParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SCT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E(""), XFLD("");

        private String defaultValues;

        private IndividualsSearchParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    /* Keep default values for individuals parameters */
    public enum IndividualParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), CIT(""), S(""), E("");

        private String defaultValues;

        private IndividualParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    /* Keep default values for individuals parameters */
    public enum IndividualDataByCountryParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("EN"), UNC("0"), PGI("0"), PGS("20"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), STK(""), ITK("");

        private String defaultValues;

        private IndividualDataByCountryParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }
    /**
     * Keep default values for related instrument parameters
     */
    public enum RelatedInstrumentParameters {
        RT("32"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("EN"), UNC("0"), PGI(""), PGS("1000"), CIT("OWN_CMP,SUBS"),
        FC("1"), IFC("1"), ITK(""), XFLD(""), SCDT(""), FDK(""), STK(""), S(""), E(""), SO("DESC"), SF("OWN_PCT");

        private String defaultValues;

        private RelatedInstrumentParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValue() {
            return defaultValues;
        }
    }


    /* Keep default values for peer companies parameters */
    public enum PeerCompaniesParameters {
        RT("57"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), TYP("PEER"), SO(""), SF(""), UE(""), OPTNL("");

        private String defaultValues;

        private PeerCompaniesParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    /* Keep default values for Profile get parameters */
    public enum ProfileGetParameters {
        RT("175"), SID("DECYPHA"), UID("DECYPHA"), PDM("42"), VER("0"), BCD("ISI");

        private String defaultValue;

        private ProfileGetParameters(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Keep default values for Profile get parameters */
    public enum ProfileSetParameters {
        RT("275"), SID("DECYPHA"), UID("DECYPHA"), PDM("42"), PROFILE(""), ISP("0");

        private String defaultValue;

        private ProfileSetParameters(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for companies listing parameters
     */
    public enum CompaniesListingParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E("");

        private String defaultValue;

        private CompaniesListingParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for city list parameters
     */
    public enum CityListParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E("");

        private String defaultValue;

        private CityListParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for multimedia list parameters
     */
    public enum MultimediaParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E(""), XFLD("");

        private String defaultValue;

        private MultimediaParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    public enum KeyOfficersParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT("KEY_OF"), SO(""), SF(""), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private KeyOfficersParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for symbol search parameters
     */
    public enum SymbolSearchParameters {
        RT("53"), SID("DECYPHA"), UID("DECYPHA"), E(""), UE(""), L("EN"), AE("1"), ASYT(""), AAE(""), SK(""), ST(""),
        SC(""), SYMT(""), PGI(""), PGS("1000"), XFLD(""), IFLD(""), INS(""), UNC(""), EMT(""), TI(""), M("1"), H("1"), CTRY(""), SO(""), SF("");

        private String defaultValue;

        private SymbolSearchParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for symbol search parameters
     */
    public enum TechnicalScannerParameters {
        RT("93"), Body("");

        private String defaultValue;

        private TechnicalScannerParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for symbol search parameters
     */
    public enum ScannerChartParameters {
        RT("94"), Body("");

        private String defaultValue;

        private ScannerChartParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum AuthenticationParameters {
        RT("1000"), UNM(""), PWD(""), SSO(""), PRD("");
        private String defaultValue;

        private AuthenticationParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum PriceUserLogOutParameters {
        RT("1003"), SID("DECYPHA"), PRODUCT(""), NGP("1"), UT(""), LGR("");

        private String defaultValue;

        private PriceUserLogOutParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum UserDataParameters {
        RT("1001"), ATH("");
        private String defaultValue;

        private UserDataParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keep default values for multimedia list parameters
     */
    public enum TreasuryBillsParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT(""), SO(""), SF(""), FDK(""), STK(""), ITK(""), S(""), E(""), XFLD("");

        private String defaultValue;

        private TreasuryBillsParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /* Keep default values for fundamental screener parameters */
    public enum FundamentalScreenerParameters {
        RT("57"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("AR"), UNC("0"), PGI(""), PGS("1000"), IFLD(""),
        IFC("1"), FC("1"), TYP("FUNDGRPH"), SO(""), SF(""), UE(""), OPTNL(""), REQID(""), PREVREQID(""), FYEAR(""), FPERIODID(""), FCALTYPE("");

        private String defaultValues;

        private FundamentalScreenerParameters(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    public enum FundPositionParameters {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("EN"), UNC("0"), PGI(""), PGS("1000"),
        IFC("1"), FC("1"), SCDT("FNINAL"), SO("DESC"), SF("FUND_INVEST_DATE"), FDK(""), STK(""), ITK(""), S("");

        private String defaultValue;

        private FundPositionParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Keeps default values for Notification request
     */
    public enum NotificationParameters {
        RT("128"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), TSID("0"), INFT("");

        private String defaultValue;

        private NotificationParameters(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum MultimediaProviders {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), PL(""), L("AR"), UNC("0"),
        IFC("1"), FC("1"), SCDT("");

        private String defaultValue;

        private MultimediaProviders(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    public enum MarketHighlights {
        RT("64"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("AR"), UNC("0"),
        IFC("1"), E("1"), S(""), PI("");

        private String defaultValue;

        private MarketHighlights(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    public enum CouponFrequency {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), H("1"), M("1"), L("EN"), UNC("0"),
        PGS("100"), FC("1"), PGI("0"), SCDT("CFREQ");

        private String defaultValue;

        private CouponFrequency(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    public enum MarketHighLowChart {
        RT("48"), SID("DECYPHA"), UID("DECYPHA"), SD(""), ED(""), L("AR"), UNC("0"),
        IFC("1"), E("1"), NOD(""), CC(""), PGI(""), PGS("1000"), SO("");

        private String defaultValue;

        private MarketHighLowChart(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /*
    * Enum of fund Action request
    * */
    public enum FundAction {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), SCDT("FNCA"), FC("1"), IFC("1"), PGS("1000"), PGI(""), ITK(""), FDK("");

        private String defaultValue;

        private FundAction(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /*
    * Enum of Investor Types Values request
    * */
    public enum InvestorUpdateFreq {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), SCDT("UPDT_FREQ"), FC("1"), IFC("1"), PGS("1000"), PGI(""), ITK(""), FDK("");

        private String defaultValue;

        private InvestorUpdateFreq(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /*
    * Enum of Investor Types Values request
    * */
    public enum InvestorTypes {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), SCDT("INV_TYPE"), FC("1"), IFC("1"), PGS("1000"), PGI(""), ITK(""), FDK("");

        private String defaultValue;

        private InvestorTypes(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /*
* Enum of Investor Types Values request
* */
    public enum InvestorNationalities {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), SCDT("INV_NAT"), FC("1"), IFC("1"), PGS("1000"), PGI(""), ITK(""), FDK("");

        private String defaultValue;

        private InvestorNationalities(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /*
* Enum of Fund Nav Frequency request
* */
    public enum FundNavFreq {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), SCDT("FNNVFQ"), FC("1"), IFC("1"), PGS("1000"), PGI(""), ITK(""), FDK("");

        private String defaultValue;

        private FundNavFreq(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /*
* Enum of Fund Nav Frequency request
* */
    public enum FundEligibility {
        RT("30"), SID("DECYPHA"), UID("DECYPHA"), L("EN"), SCDT("FNELG"), FC("1"), IFC("1"), PGS("1000"), PGI(""), ITK(""), FDK("");

        private String defaultValue;

        private FundEligibility(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValues() {
            return defaultValue;
        }
    }

    /* Keep default values for Profile get parameters */
    public enum MultiSymbolHistoryRequestParameters {
        RT("49"), SID("DECYPHA"), UID("DECYPHA"), L(""), PDM("42"), SL(""), SD(""), ED(""), UNC("0"), H("1"), M("1"), PGS("2000"), SO("DESC");

        private String defaultValue;

        private MultiSymbolHistoryRequestParameters(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /* Types of Caches Using to store data */
    public enum CacheType {
        EH, MEM
    }

    /* Types of data sources */
    public enum SocketType {
        MIX, MIX_HTTP, NEWS_CACHE, SYMBOL_SEARCH_CACHE, FUNDAMENTAL_CACHE, CHART_CACHE, ANNOUNCEMENTS_CACHE
    }

    /* Top stocks data types */
    public enum TopStockType {
        TGC, TLC, MAV, MAP, MAT, MAVA, TGVC, TLVC, TMI, TMO, MAMC
    }

    /* Fundamental data types */
    public enum FundamentalDataTypes {
        CP, BRI, MGT, STK, OWN, SUBS, SECT, GRP, AUD, FRVL, FRSC, FRRT, INTR, INTY, EANN, CALEVT, CPAC, NCCPAC, CALMG, SUBSH, INMGT, FFHIST,
        GRPHIST, MFUND, FNFCRG, FNBMK, IPO, COMGTREP, COMGT, INDV, OWN_IND, INOWH, OWN_CMP, CMOWH, FNINAL, FINC, COUN, EXCHG, CITY, MEDIA,
        CPUNLS, IS, BS, CF, FR, MEDPROV, VW, CFREQ, FNCA, INV_VAL, INV_TYPE, INV_NAT, MA, KPI, OSLD, CALET, FILE, CPCLS, NEWS_TOP, OWLM_SER,
        OWLM_STK, OWLM_VAL, OWLM_BLKV, FNFCCN, UPDT_FREQ, FNELG, FNNVFQ, EANNML, KEY_OF, CPPRODCLS, BRACKETS, NEWS_TOP_A
    }

    public enum RequestDataType {
        PRICE_HISTORY, PRICE_SNAPSHOT, INSIDER_TRANSACTION, NEWS_DETAILS, NEWS, TOP_STOCK, ANNOUNCEMENT, SYMBOL_DATA, REPORT_DATA,
        CALENDAR_EVENTS, PEER_COMPANY, CALENDAR_EVENTS_META_DATA, CORPORATE_ACTIONS, INVESTMENTS, COMPANY_PROFILE, FINANCIAL_DATA,
        FAIR_VALUES, FUND_PROFILE, EARNINGS, RELATED_INSTRUMENTS, BOND_PROFILE, COMPANY_MANAGEMENT, INDIVIDUALS_SEARCH,
        INDIVIDUAL_PROFILE, INDIVIDUAL_OWNSHP_HISTRY, COMPANY_OWNERS, MACRO_ECONOMY, COMPANY_LIST, CITY_LIST, USER_PROFILE_GET,
        USER_PROFILE_SET, MULTIMEDIA, SYMBOL_SEARCH, TECHNICAL_SCANNER, AUTHENTICATION, USER_DATA, PRICE_USER_LOGOUT, TREASURY_BILLS, ANNOUNCEMENT_DETAILS,
        FUND_SCREENER, UPDATE_NOTIFICATION, MULTIMEDIA_DETAILS, MULTIMEDIA_PROVIDERS, FUND_POSITION, MARKET_HIGHLIGHTS, COUPON_FREQUENCY,
        MARKET_HIGHLOW_CHART, FUND_ACTION, INVESTOR_TYPES_VALUES, INVESTOR_TYPES, INVESTOR_NATIONALITIES,
        MARKET_OWNERSHIP, MARKET_OWNERSHIP_COMPARATIVE, STOCK_OWNERSHIP, OWNERSHIP_SERIES_DEF, STOCK_OWNERSHIP_CHART, CAL_EVENTS_TITLE, NEWS_TOP, UPDATE_FREQ,
        FUND_NAV_FREQ, FUND_ELIGIBILITY, MULTI_SYMBOL_HISTORY, ZOHO_DATA, FINANCIAL_AVG_RATIO, INDIVIDUAL_DATA_BY_COUNTRY, KEY_OFFICERS

    }

    public enum FinancialTypes {
        IS, BS, CF, FR, MR, DR
    }

    public enum FinancialPageTypes{
        IS, BS, CF, SNP, FR, RB, SEG
    }

    public enum TemplateIds{
        INDUSTRY(1),
        BANK(2),
        INSURANCE(3),
        UTILITY(4);

        private int code;

        private TemplateIds(int code) {
            this.code = code;
        }

        public static TemplateIds valueOf(int code) {
            switch (code) {
                case 1:
                    return TemplateIds.INDUSTRY;
                case 2:
                    return TemplateIds.BANK;
                case 3:
                    return TemplateIds.INSURANCE;
                case 4:
                    return TemplateIds.UTILITY;
                default:
                    return TemplateIds.INDUSTRY;
            }
        }
    }

    public enum FinancialAvgRatioTypes {
        FR, MR
    }

    public enum GlobalSelectionTypes {
        con, reg
    }

    public enum FinancialViewTypes {
        Q, A, C, T
    }

    public enum SectorSnapshotTypes {
        Last(1), Av5D(2), Av10D(3), MTD(4), QTD(5), YTD(6), PrevMonth(7), PrevQuarter(8);

        private static Map<Integer, SectorSnapshotTypes> map = new HashMap<Integer, SectorSnapshotTypes>();

        static {
            for (SectorSnapshotTypes periodsEnum : SectorSnapshotTypes.values()) {
                map.put(periodsEnum.period, periodsEnum);
            }
        }

        private int period;

        private SectorSnapshotTypes(final int periodItem) {
            period = periodItem;
        }

        public static SectorSnapshotTypes valueOf(int periodItem) {
            return map.get(periodItem);
        }

        public int getPeriod() {
            return period;
        }
    }

    /**
     * Menu types available in dfnplus
     */
    public enum MenuTypes {
        Menu_Section("a"),
        Content_Section("b"),
        Content_News_Section("ba"),
        Content_Market_Section("bb"),
        Content_Company_Section("bl"),
        Content_Research_Section("bm"),
        Content_Funds_Section("bc"),
        Content_Fixed_Income_Section("bd"),
        Content_Money_Market_Section("be"),
        Content_Commodity_Section("bf"),
        Content_FX_Section("bg"),
        Content_Macro_Economy_Section("bh"),
        Content_Industry_section("bi"),
        Content_Analysis_Section("bj"),
        Content_More_Section("bk"),
        Menu_And_Content("c");

        private static Map<String, MenuTypes> map = new HashMap<String, MenuTypes>();

        static {
            for (MenuTypes menuTypeEnum : MenuTypes.values()) {
                map.put(menuTypeEnum.menuType, menuTypeEnum);
            }
        }

        private String menuType;

        private MenuTypes(final String periodItem) {
            menuType = periodItem;
        }

        public static MenuTypes getMenuTypeDesc(String menuType) {
            return map.get(menuType);
        }

        public String getMenuType() {
            return menuType;
        }
    }

    /**
     * User Types available in the dfnplus(Used in menu and authorization)
     */
    public enum UserTypes {
        Anonymous_User("a"), Registered_User("b"), Premium_User("c");

        private static Map<String, UserTypes> map = new HashMap<String, UserTypes>();

        static {
            for (UserTypes userTypeEnum : UserTypes.values()) {
                map.put(userTypeEnum.userType, userTypeEnum);
            }
        }

        private String userType;

        private UserTypes(final String periodItem) {
            userType = periodItem;
        }

        public static UserTypes getUserTypeDesc(String userType) {
            return map.get(userType);
        }

        public String getUserType() {
            return userType;
        }
    }

    public enum TreasuryBillScreenerProperties {
        MATURITY_DATE("md", DBConstants.DatabaseColumns.MATURITY_DATE),
        DATE_OF_ISSUE("id", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        SHORT_DESC_LAN("sym", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        COUNTRY_CODE("cnt", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        MATURITY_PERIOD("mp", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        ISSUE_WGT_AVG_RATE("iwar", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        AMOUNT_ISSUED("ai", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        COUPON_RATE("cr", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        CURRENCY("cur", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        SYMBOL("s", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        EXCHANGE("e", DBConstants.DatabaseColumns.DATE_OF_ISSUE),
        DECIMAL_PLACES("dp", DBConstants.DatabaseColumns.DATE_OF_ISSUE);

        private static Map<String, TreasuryBillScreenerProperties> map = new HashMap<String, TreasuryBillScreenerProperties>();
        private String name;
        private String dbColumnName;

        private TreasuryBillScreenerProperties(String name, String dbColumnName) {
            this.name = name;
            this.dbColumnName = dbColumnName;
        }

        public String getName() {
            return name;
        }

        public String getDbColumnName() {
            return dbColumnName;
        }

        static {

            for (TreasuryBillScreenerProperties param : TreasuryBillScreenerProperties.values()) {
                map.put(param.name, param);
            }
        }

        public TreasuryBillScreenerProperties get(String name) {
            return map.get(name);
        }
    }

    /* Instrument types for asset classes */
    public enum InstrumentTypes {
        TREASURY_BILLS("108"), QUOTE("0"), EQUITY("60"), COMMON_STOCK("61"), PREFERRED_STOCK("62"), PREMIUM("64"), TRUST("65"),
        RIGHT("66"), WARRANT_RIGHT("67"), EXCHANGE_TRADED_FUNDS("86"), E_122("122"), BOND("75"), SUKUK("121"), MUTUAL_FUND("2"),
        DEBENTURE("112"), LENDING_RATE("109"), BORROWING_RATE("110"), INTER_BANK_RATE("111"), CONVERTIBLE_BOND("76"), MBS("77"),
        GOVERNMENT_BOND("78"),
        CORPORATE_BOND("79"), US_AGENCY_BOND("80"), US_TREASURY_BILLS("81"), US_TREASURY_COUPON("82"), CD("84"), INDICES("7");

        private static List<String> SUPPORTED_INSTRUMENT_TYPES;
        private static Map<String, InstrumentTypes> instrumentTypesMap;

        static {
            String validInstrumentTypes = IConstants.InstrumentTypes.getEquityAssetClassInstrumentTypes() + Delimiter.COMMA
                    + IConstants.InstrumentTypes.getFixedIncomeInstrumentTypes() + Delimiter.COMMA
                    + IConstants.InstrumentTypes.MUTUAL_FUND.getDefaultValues();
            SUPPORTED_INSTRUMENT_TYPES = new ArrayList<String>(Arrays.asList(validInstrumentTypes.split(String.valueOf(Delimiter.COMMA))));

            instrumentTypesMap = new HashMap<String, InstrumentTypes>(27);
            for (InstrumentTypes instrumentType : InstrumentTypes.values()) {
                instrumentTypesMap.put(instrumentType.defaultValues, instrumentType);
            }
        }

        private String defaultValues;

        private InstrumentTypes(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public static String getEquityInstrumentTypes() {
            return QUOTE.defaultValues + Delimiter.COMMA + EQUITY.defaultValues + Delimiter.COMMA + COMMON_STOCK.defaultValues;
        }

        public static String getFixedIncomeInstrumentTypes() {
            return BOND.defaultValues + Delimiter.COMMA + GOVERNMENT_BOND.defaultValues + Delimiter.COMMA + CORPORATE_BOND.defaultValues +
                    Delimiter.COMMA + SUKUK.defaultValues + Delimiter.COMMA + CONVERTIBLE_BOND.defaultValues;
        }

        public static String getEquityAssetClassInstrumentTypes() {
            return QUOTE.defaultValues + Delimiter.COMMA + EQUITY.defaultValues + Delimiter.COMMA + COMMON_STOCK.defaultValues +
                    Delimiter.COMMA + PREFERRED_STOCK.defaultValues + Delimiter.COMMA + PREMIUM.defaultValues + Delimiter.COMMA +
                    TRUST.defaultValues + Delimiter.COMMA + RIGHT.defaultValues + Delimiter.COMMA + WARRANT_RIGHT.defaultValues +
                    Delimiter.COMMA + EXCHANGE_TRADED_FUNDS.defaultValues + Delimiter.COMMA + E_122.defaultValues;
        }

        public static List<String> getSupportedInstrumentTypes() {
            return SUPPORTED_INSTRUMENT_TYPES;
        }

        public static InstrumentTypes getInstrumentType(String id) {
            return instrumentTypesMap.get(id);
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    public enum AnnouncementMixDataField {
        ID(MIXDataField.ID),
        DT(MIXDataField.DT),
        E(MIXDataField.E),
        S(MIXDataField.S),
        HED(MIXDataField.HED),
        SC(MIXDataField.SC),
        URL(MIXDataField.URL),
        TSID(MIXDataField.TSID),
        PRIO(MIXDataField.PRIO),
        STATUS(MIXDataField.STATUS),
        SI(MIXDataField.SI),
        SRCSEQ(MIXDataField.SRCSEQ),
        DCMSID(MIXDataField.DCMSID),
        ATCOD(MIXDataField.ATCOD),
        L(MIXDataField.L),
        SRC(MIXDataField.SRC),
        ATURL(MIXDataField.ATURL);

        static Map<String, AnnouncementMixDataField> annFieldMap;

        static {
            annFieldMap = new HashMap<String, AnnouncementMixDataField>();
            for (AnnouncementMixDataField field : AnnouncementMixDataField.values()) {
                annFieldMap.put(field.field, field);
            }
        }

        private String field;

        AnnouncementMixDataField(String field) {
            this.field = field;
        }

        public static AnnouncementMixDataField getField(String field) {
            return annFieldMap.containsKey(field) ? annFieldMap.get(field) : null;
        }

    }

    public enum NewsMixDataField {
        NEWS_ID(MIXDataField.ID),
        SEQ_ID(MIXDataField.SI),
        NEWS_DATE(MIXDataField.DT),
        STATUS(MIXDataField.STATUS),
        NEWS_PROVIDER(MIXDataField.PRV),
        NEWS_SOURCE_ID(MIXDataField.SCODE),
        HOT_NEWS_INDICATOR(MIXDataField.HTIND),
        ASSET_CLASS(MIXDataField.AST),
        COMPANY_ID(MIXDataField.CID),
        TICKER_SERIAL(MIXDataField.TSID),
        SYMBOL(MIXDataField.S),
        MARKET_SECTOR_CODE(MIXDataField.MKT),
        INDUSTRY_CODE(MIXDataField.INDST),
        EXCHANGE(MIXDataField.E),
        COUNTRY(MIXDataField.CC),
        INDIVIDUAL_CODE(MIXDataField.INDV),
        EDITORIAL_CODE(MIXDataField.EDT),
        NEWS_SOURCE_DESC(MIXDataField.SOU),
        NEWS_HEADLINE(MIXDataField.HED),
        URL(MIXDataField.URL),
        APPROVAL_STATUS(MIXDataField.APPRVSTAT),
        LANGUAGE(MIXDataField.L),
        CONTRIBUTED_DATE(MIXDataField.CD),
        LAST_UPDATED_TIME(MIXDataField.LUT);

        static Map<String, NewsMixDataField> newsFieldMap;

        static {
            newsFieldMap = new HashMap<String, NewsMixDataField>();
            for (NewsMixDataField field : NewsMixDataField.values()) {
                newsFieldMap.put(field.field, field);
            }
        }

        private String field;

        NewsMixDataField(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }

        public static NewsMixDataField getField(String field) {
            return newsFieldMap.containsKey(field) ? newsFieldMap.get(field) : null;
        }

    }

    public enum GicsLevel {
        GICS1(1),
        GICS2(2),
        GICS3(3),
        GICS4(4);

        private int code;

        private GicsLevel(int code) {
            this.code = code;
        }

        public static GicsLevel valueOf(int code) {
            switch (code) {
                case 1:
                    return GicsLevel.GICS1;
                case 2:
                    return GicsLevel.GICS2;
                case 3:
                    return GicsLevel.GICS3;
                case 4:
                    return GicsLevel.GICS4;
                default:
                    return null;
            }
        }

        public int getCode() {
            return code;
        }
    }

    public enum CpcLevel {
        CPC_L1(1),
        CPC_L2(2),
        CPC_L3(3),
        CPC_L4(4),
        CPC_L5(5);

        private int code;

        public int getCode() {
            return code;
        }

        private CpcLevel(int code) {
            this.code = code;
        }

        public static CpcLevel valueOf(int code) {
            switch (code){
                case 1:
                    return CPC_L1;
                case 2:
                    return CPC_L2;
                case 3:
                    return CPC_L3;
                case 4:
                    return CPC_L4;
                case 5:
                    return CPC_L5;
                default:
                    return null;
            }
        }
    }

    public static enum EmailType {
        WELCOME(1),
        RESET_PASSWORD(2),
        DEMO_USAGE_REPORT_TO_SALES_REP(3),
        ACCOUNT_EXPIRY_NOTIFICATION(4),
        REGISTER(5),
        DEMO_USAGE_REPORT(6),
        EOD_JOB(7),
        PREMIUM_USAGE_REPORT(8),
        DFN_USAGE_REPORT(9),
        CDC_USAGE_REPORT(10),
        ECC_USAGE_REPORT(11),
        SALES_USAGE_REPORT(12),
        PROMO_EXISTING_TRIAL_USER(13),
        PROMO_EXISTING_PREMIUM_USER(14),
        PROMO_WELCOME(15),
        FINANCIAL_ANALYST_NOTIFICATION(16),
        RELEASE_NOTE_SELF(17),
        RELEASE_NOTE_SYSTEM(18),
        RELEASE_NOTE_TO_THIRD_PARTY(19),
        ANALYST_NOTOFICATION_FEEDBACK(20),
        RELEASE_NOTE_FEEDBACK(21),
        EMAIL_EXCEL_TO_THIRD_PARTY(22),
        EMAIL_EXCEL_TO_ME(23),
        ACCOUNT_ACTIVATION(24),
        REQUEST_TRIAL(25),
        EMAIL_FEEDBACK(26),
        EMAIL_FEEDBACK_EXTERNAL(27),
        SOLR_HEALTH_CHECK(28);

        private int code;

        private EmailType(int code) {
            this.code = code;
        }

        public static EmailType valueOf(int code) {
            switch (code) {
                case 1:
                    return EmailType.WELCOME;
                case 2:
                    return EmailType.RESET_PASSWORD;
                case 3:
                    return EmailType.DEMO_USAGE_REPORT_TO_SALES_REP;
                case 4:
                    return EmailType.ACCOUNT_EXPIRY_NOTIFICATION;
                case 5:
                    return EmailType.REGISTER;
                case 6:
                    return EmailType.DEMO_USAGE_REPORT;
                case 7:
                    return EmailType.EOD_JOB;
                case 8:
                    return EmailType.PREMIUM_USAGE_REPORT;
                case 9:
                    return EmailType.DFN_USAGE_REPORT;
                case 10:
                    return EmailType.CDC_USAGE_REPORT;
                case 11:
                    return EmailType.ECC_USAGE_REPORT;
                case 12:
                    return EmailType.SALES_USAGE_REPORT;
                case 13:
                    return EmailType.PROMO_EXISTING_TRIAL_USER;
                case 14:
                    return EmailType.PROMO_EXISTING_PREMIUM_USER;
                case 15:
                    return EmailType.PROMO_WELCOME;
                case 16:
                    return EmailType.FINANCIAL_ANALYST_NOTIFICATION;
                case 17:
                    return EmailType.RELEASE_NOTE_SELF;
                case 18:
                    return EmailType.RELEASE_NOTE_SYSTEM;
                case 19:
                    return EmailType.RELEASE_NOTE_TO_THIRD_PARTY;
                case 20:
                    return EmailType.ANALYST_NOTOFICATION_FEEDBACK;
                case 21:
                    return EmailType.RELEASE_NOTE_FEEDBACK;
                case 22:
                    return EmailType.EMAIL_EXCEL_TO_THIRD_PARTY;
                case 23:
                    return EmailType.EMAIL_EXCEL_TO_ME;
                case 24:
                    return EmailType.ACCOUNT_ACTIVATION;
                case 25:
                    return EmailType.REQUEST_TRIAL;
                case 26:
                    return EmailType.EMAIL_FEEDBACK;
                case 27:
                    return EmailType.EMAIL_FEEDBACK_EXTERNAL;
                default:
                    return null;
            }
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * Period Types
     */
    public enum PeriodTypes {
        ONE_WEEK, ONE_MONTH, SIX_MONTH, CURRENT_QTR, CUSTOM, ALL, QTD, THREE_MONTH, YTD, ONE_YEAR, TWO_YEAR, LATEST
    }

    /**
     * Response Status
     */
    public enum ResponseStatus {
        SUCCESS, NO_DATA
    }

    /**
     * Individual Snapshot InformationTypes
     */
    public enum IndividualSnapshotTypes {
        OWN, INDV, NEWS, INTR, DESG
    }

    /**
     * Period Types
     */
    public enum BondStatus {
        NA, MATURED, OUT_STANDING
    }

    /**
     * Fund Page Period Types map with db_column name.
     */
    public enum FundOrderType {
        THREE_MONTH(DBConstants.DatabaseColumns.CHG_3M),
        YTD(DBConstants.DatabaseColumns.CHG_YTD),
        ONE_YEAR(DBConstants.DatabaseColumns.CHG_1Y),
        THREE_MONTH_PER(DBConstants.DatabaseColumns.CHG_PER_3M),
        YTD_PER(DBConstants.DatabaseColumns.CHG_PER_YTD),
        ONE_YEAR_PER(DBConstants.DatabaseColumns.CHG_PER_1Y);


        private String defaultValue;

        private FundOrderType(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Asset class types
     */
    public enum AssetType {
        EQUITY(1),
        COMMODITY(10),
        STOCK_BORROWING(11),
        COUNTRY(12),
        WARRENT(2),
        OPTIONS(3),
        FUTURES(4),
        MUTUAL_FUNDS(5),
        FIXED_INCOME(6),
        FOREX(7),
        INDEX(8),
        MONEY_MARKET(9),
        MA(10);

        private int defaultValue;

        private AssetType(int defValue) {
            this.defaultValue = defValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }

        private static Map<String, AssetType> map = new HashMap<String, AssetType>();

        static {
            for (AssetType value : AssetType.values()) {
                map.put(String.valueOf(value.defaultValue), value);
            }
        }

        public static AssetType getAsset(String defValue) {
            return map.get(defValue);
        }
    }

    /**
     * Asset class types
     */
    public enum RelationClass {

        SUBSIDIARY(1),
        ASSOCIATED(2),
        INVESTMENTS(3);

        private static Map<Integer, RelationClass> typeMap = new HashMap<Integer, RelationClass>();

        static {
            for (RelationClass type : values()) {
                typeMap.put(type.defaultValue, type);
            }
        }

        private int defaultValue;

        public static RelationClass getRelationClass(int type) {
            return typeMap.get(type);
        }

        private RelationClass(int defValue) {
            this.defaultValue = defValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }
    }

    public enum NewsSections {
        EQUITY_NEWS("1"),
        MACRO_ECONOMY_NEWS("2"),
        FIXED_INCOME_NEWS("6"),
        MONEY_MARKET_NEWS("9"),
        FUNDS_NEWS("13"),
        COMMODITY_NEWS("10"),
        COUNTRY_DATA_NEWS("12"),
        CURRENCY_NEWS("14");

        private static Map<String, NewsSections> map = new HashMap<String, NewsSections>();

        static {
            for (NewsSections value : NewsSections.values()) {
                map.put(value.defaultValue, value);
            }
        }

        private String defaultValue;

        private NewsSections(String defValue) {
            this.defaultValue = defValue;
        }

        public static NewsSections getSection(String defValue) {
            return map.get(defValue);
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum RatioGroup {
        PER_SHARE_DATA("1"),
        VALUATION_RATIOS("2"),
        PROFITABILITY_RATIOS("3"),
        GROWTH_RATIOS("4"),
        LIQUIDITY_RATIOS("5"),
        LEVERAGE_RATIOS("6"),
        EFFICIENCY_RATIOS("7"),
        OTHER_METRICS("8"),
        SHARIAH_COMPLIANCE_RATIOS("9");

        private static Map<String, RatioGroup> map = new HashMap<String, RatioGroup>();

        static {
            for (RatioGroup value : RatioGroup.values()) {
                map.put(value.defaultValue, value);
            }
        }

        private String defaultValue;

        private RatioGroup(String defValue) {
            this.defaultValue = defValue;
        }

        public static RatioGroup getGroup(String defValue) {
            return map.get(defValue);
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Editorial descriptions for macro economy news
     */
    public enum MacroEconomyNewsEdtCodes {

        GLOBAL_ECON_NEWS_INDICATORS("26"), LOCAL_ECONOMY("55");

        private String defaultValues;

        private MacroEconomyNewsEdtCodes(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }


    public enum IpoNewsEdtCodes {

        IPO_NEWS_EDT_CODES("41");

        private String defaultValues;

        private IpoNewsEdtCodes(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    public enum maNewsEdtCodes {

        IMA_NEWS_EDT_CODES("1");

        private String defaultValues;

        private maNewsEdtCodes(String defaultValues) {
            this.defaultValues = defaultValues;
        }

        public String getDefaultValues() {
            return defaultValues;
        }
    }

    /*Company listing status to get listed and unlisted companies for unlisted companies listing*/
    public enum CompanyListingStatus {
        L("\"1\""),
        M("\"2\""),
        U("\"3\""),
        A("\"1\",\"2\",\"3\"");

        private String defaultValue;

        private CompanyListingStatus(String defValue) {
            this.defaultValue = defValue;
        }

        public static boolean getEnums(String name) {

            for (CompanyListingStatus c : CompanyListingStatus.values()) {
                if (c.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    /**
     * Custom company types to search in IMDB
     */
    public enum CustomCompanyTypes {
        ORDINARY("1"),
        OTHER("2"),
        KEY_ORGANIZATIONS("3");

        private static Map<String, CustomCompanyTypes> map = new HashMap<String, CustomCompanyTypes>();

        static {
            for (CustomCompanyTypes value : CustomCompanyTypes.values()) {
                map.put(value.defaultValue, value);
            }
        }

        private String defaultValue;

        private CustomCompanyTypes(String defValue) {
            this.defaultValue = defValue;
        }

        public static CustomCompanyTypes getCompanyType(String value) {
            return map.get(value);
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Company types enum
     */
    public enum CompanyTypes {
        ORDINARY("1"),
        CENTRAL_BANK("2"),
        GOV_INST("3"),
        GOV("4"),
        INT_INST("5"),
        MINISTRY("6"),
        FAMILY("7"),
        UNION("8"),
        SYNDICATES("9"),
        PENSION_FUNDS("10"),
        SOVER_FUNDS("11"),
        PRV_EQU_FUNDS("12"),
        VEN_CAP_FUNDS("13"),
        POLITICAL_ENTITY("14"),
        UND_ACCOUNT("15"),
        COMMUNITY("16"),
        SPV("17");

        private String defaultValue;

        private CompanyTypes(String defValue) {
            this.defaultValue = defValue;
        }

        public static String getSymbolSearchCompanyTypes() {
            return ORDINARY.defaultValue + Delimiter.COMMA + CENTRAL_BANK.defaultValue + Delimiter.COMMA + GOV_INST.defaultValue + Delimiter.COMMA + MINISTRY.defaultValue +
                    Delimiter.COMMA + PENSION_FUNDS.defaultValue + Delimiter.COMMA + SOVER_FUNDS.defaultValue + Delimiter.COMMA +
                    PRV_EQU_FUNDS.defaultValue + Delimiter.COMMA + VEN_CAP_FUNDS.defaultValue + Delimiter.COMMA + SPV.defaultValue;
        }

        public static boolean getEnums(String name) {

            for (CompanyTypes c : CompanyTypes.values()) {
                if (c.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    /**
     * Enum represents image download type used for downloding images to disk from
     */
    public static enum ImageDownloadType {
        IMAGE_DOWNLOAD_INDIVIDUAL(1),
        IMAGE_DOWNLOAD_COUNTRY_FLAG(2),
        IMAGE_DOWNLOAD_SOURCE_FLAG(3),
        IMAGE_DOWNLOAD_COMPANY_LOGO(4),
        IMAGE_DELETE_COMPANY_LOGO(5),
        IMAGE_DELETE_INDIVIDUAL_LOGO(6),
        IMAGE_DELETE_COUNTRY_LOGO(7),
        IMAGE_DELETE_SOURCE_LOGO(8);

        private int defaultValue;

        private Map<Integer, String> downloadTypes;

        ImageDownloadType(int defValue) {
            this.defaultValue = defValue;
        }

        public static ImageDownloadType getImgDownloadType(int value) {
            ImageDownloadType downloadType = null;
            for (ImageDownloadType type : ImageDownloadType.values()) {
                if (type.defaultValue == value) {
                    downloadType = type;
                }
            }
            return downloadType;
        }

    }

    /**
     * Asset class types
     */
    public enum NotificationType {
        FUNDAMENTAL_DATA_NOTIFICATION("FDN");

        private String defaultValue;

        private NotificationType(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum JavaDataType {
        INT,
        DOUBLE,
        STRING,
        LONG
    }

    public enum Page {
        HOME_PAGE,
        MACRO_ECONOMY,
        COUNTRY_PROFILE,
        CORPORATE_ACTION,
        CALENDAR_EVENTS,
        EARNINGS,
        COMPANY_MANAGEMENT,
        COMPANY_OWNERS,
        COMPANY_PROFILE,
        ESTIMATES,
        FAIR_VALUE,
        FINANCIAL_COMPARISON,
        INDEX_DETAIL,
        INSIDER_TRANSACTION,
        INVESTMENTS,
        REPORTS,
        INVESTOR_TYPES,
        IPO,
        KPI,
        FIXED_INCOME,
        BOND_PROFILE,
        FUNDS,
        FUND_PROFILE,
        FUND_ACTION,
        FUND_INVESTMENT,
        FUND_PERFORMANCE,
        OWNERSHIP_LIMITS_EXCHANGE,
        OWNERSHIP_LIMITS_STOCK,
        MARKET_FUND_INVESTMENT,
        FUND_RATIOS,
        INDIVIDUAL_PROFILE,
        FREE_LISTED_COMPANY,
        FREE_UNLISTED_COMPANY,
        FREE_GOV_COMPANY
    }

    public enum AnnouncementType {
        EXCHANGE_SYMBOL(1), EXCHANGE(2), COUNTRY(3), REGION(4), GICSL2(5), GICSL3(6), GICSL4(7), RELATED_ANN(8), ANN_BY_ID(9), RELATED_EXCHNAGE_ANN(10);

        private int defaultValue;

        AnnouncementType(int defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public enum SegmentPeriodType {
        ALL(1),
        ANNUAL(2),
        PERIOD(3);

        private int type;

        private SegmentPeriodType(int type) {
            this.type = type;
        }

        public int getPeriodType() {
            return this.type;
        }
    }

    public enum FinancialSegmentType {
        COUNTRY(1, 6, "Country"),//todo : add labels
        REGION(2, 5, "Region"),
        ZONE(3, 7, "Zone"),
        CITY(4, 8, "City"),
        PRODUCT(5, 2, "Product"),
        BUSINESS_ACTIVITY(6, 1, "Business Activity"),
        SUBS(7, 4, "Subsidiary"),
        CUSTOMERS(8, 3, "Customer");

        private static Map<Integer, FinancialSegmentType> map = new HashMap<Integer, FinancialSegmentType>(8);
        private static Map<String, String> displayNameMap = new HashMap<String, String>(8);

        static {
            for (FinancialSegmentType segmentType : FinancialSegmentType.values()) {
                map.put(segmentType.type, segmentType);
                displayNameMap.put(String.valueOf(segmentType.type), segmentType.label);
            }
        }

        private int type;
        private int order;
        private String label;

        private FinancialSegmentType(int type, int order, String label) {
            this.type = type;
            this.order = order;
            this.label = label;
        }

        public static FinancialSegmentType getSegmentType(int value) {
            return map.containsKey(value) ? map.get(value) : null;
        }

        public static Map<String, String> getDisplayNameMap() {
            return displayNameMap;
        }

        public int getSegmentType() {
            return this.type;
        }

        /**
         * if order of type is higher than this order return 1
         * this is a custom comparable implementation
         *
         * @param type comparing type
         * @return 0, 1 or -1
         */
        public int isHigherPriority(FinancialSegmentType type) {
            int priorityCompare;
            if (this.order == type.order) {
                priorityCompare = 0;
            } else {
                priorityCompare = this.order < type.order ? -1 : 1;
            }
            return priorityCompare;
        }
    }

    public enum SegmentStandardType {
        STANDARD(1),
        AS_REPORTED(2);

        private int type;

        private SegmentStandardType(int type) {
            this.type = type;
        }

        public int getSegmentType() {
            return this.type;
        }
    }

    //-------------------------------------------- Stock Ownership -----------------------------------------------------

    /**
     * fields for jasper reports
     */
    public enum JasperReportFields {
        MAIN_ACTIVITY,
        EST_DATE,
        ADDR,
        PHN,
        FAX,
        WEB,
        EMAIL,
        COMPANY_ID,
        COMPANY_NAME,
        SYMBOL_DESCRIPTION,
        LISTING_DATE,
        TOTAL_STOCKS,
        SECT_DSC,
        CURRENCY,
        CURRENCY_CODE,
        FREE_FLOAT,
        CHART_ID,
        ONE_YEAR_CHANGE,
        ONE_YEAR_PCT_CHANGE,
        LTP,
        MARKET_CAP,
        DT,
        TT,
        I,
        SI,
        ISINC,
        OWN_NAME,
        VALUE,
        KEY,
        TXT,
        P1,
        P2,
        P3,
        P4
    }

    /**
     * parameters for jasper reports
     */
    public enum JasperReportParameters {
        Resources_DIR,
        Logos_DIR,
        Flags_DIR,
        SYMBOL,
        EXCHANGE,
        MANAGERS,
        INDIVIDUAL,
        CORP_OWNER,
        NON_CORP_OWNER,
        OWN_TYPE_CHART,
        OWN_COUNTRY_CHART,
        USER_STAMP,
        KEY_OFFICERS,
        BOD,
        PAGE_CONTEXT_URL,
        LAST_REVIEW_DATE
    }

    /**
     * data sources for jasper reports
     */
    public enum JasperReportDataSourceParameters {
        CP,
        FIN,
        INMGT,
        INDIVIDUAL,
        CORP_OWNER,
        NON_CORP_OWNER,
        OWN_TYPE_CHART,
        OWN_COUNTRY_CHART,
        INVESTMENTS,
        KEY_OFFICERS,
        LAST_REVIEW_DATE
    }

    /**
     * Ownership types using in the jasper report
     */
    public enum JasperReportOwnershipTypes {
        CORP_OWNER,
        NON_CORP_OWNER,
        INDIVIDUAL,
        FUNDS,
        OTHERS
    }

    public enum OwnershipUpdateFrequencies {
        DAILY(1),
        WEEKLY(2),
        MONTHLY(3),
        QUATERLY(4),
        YEARLY(5);

        private static Map<Integer, OwnershipUpdateFrequencies> map = new HashMap<Integer, OwnershipUpdateFrequencies>();

        private int frequencyId;

        static {
            for (OwnershipUpdateFrequencies param : OwnershipUpdateFrequencies.values()) {
                map.put(param.frequencyId, param);
            }
        }

        OwnershipUpdateFrequencies(int frequencyId) {
            this.frequencyId = frequencyId;
        }

        public static OwnershipUpdateFrequencies get(int frequencyId) {
            return map.get(frequencyId);
        }

        public int getFrequencyId() {
            return frequencyId;
        }

    }

    public enum ExchangeDataType {
        PRICE_DATA, MACRO_DATA, IPO_DATA, OTHER_DATA, COMPANY_DATA, EDITION_CONTROL_DATA, DEFAULT_DATA, FI_DATA, MF_DATA
    }

    public enum NavFrequency {
        ANNUALLY(2),
        BI_MONTHLY(3),
        BI_WEEKLY(4),
        DAILY(5),
        MONTHLY(6),
        QUARTERLY(7),
        SEMI_ANNUAL(8),
        THREE_TIMES_A_WEEK(9),
        WEEKLY(10);

        private static Map<Integer, String> map = new HashMap<Integer, String>();

        private int frequencyId;

        static {
            for (NavFrequency param : NavFrequency.values()) {
                map.put(param.frequencyId, param.toString());
            }
        }

        NavFrequency(int frequencyId) {
            this.frequencyId = frequencyId;
        }

        public static Map<Integer, String> getNavFrequencyClassification() {
            return map;
        }

        public static NavFrequency getFrequency(Integer navFrequency) {
            return map.containsKey(navFrequency) ? NavFrequency.valueOf(map.get(navFrequency)) : MONTHLY;
        }

        public int getFrequencyId() {
            return frequencyId;
        }
    }

    public enum IndexUpdateFrequency {
        INTRADAY("I"),
        DAILY("D"),
        WEEKLY("W"),
        BI_WEEKLY("B"),
        MONTHLY("M");

        private static Map<String, String> map = new HashMap<String, String>();

        private String id;

        static {
            for (IndexUpdateFrequency param : IndexUpdateFrequency.values()) {
                map.put(param.id, param.toString());
            }
        }

        IndexUpdateFrequency(String id) {
            this.id = id;
        }

        public static IndexUpdateFrequency getFrequency(String id) {
            return map.containsKey(id) ? IndexUpdateFrequency.valueOf(map.get(id)) : MONTHLY;
        }

        public String getFrequencyId() {
            return id;
        }
    }

    /**
     * Defines Top News section ids
     */
    public enum TopNewsSection {
        EQUITY("1"),
        F_INCOME("2"),
        M_FUNDS("3"),
        M_MARKET("4"),
        M_ECONOMY("5"),
        COUNTRY("6"),
        COMMODITY("7"),
        FX("8"),
        GENERAL("9");

        private String defaultValue;

        private TopNewsSection(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    public enum DCPStatus {
        MODIFIED("M"),
        NEW("N"),
        DELETED("D");

        private static Map<String, DCPStatus> statusMap = null;

        static {
            statusMap = new HashMap<String, DCPStatus>(3);
            for (DCPStatus dcpStatus : DCPStatus.values()) {
                statusMap.put(dcpStatus.getStatus(), dcpStatus);
            }
        }

        private String status;

        private DCPStatus(String status) {
            this.status = status;
        }

        public static DCPStatus getDCPStatus(String status) {
            return statusMap.get(status);
        }

        public String getStatus() {
            return status;
        }
    }

    public enum NewsApprovalStatus {
        PUBLISHED("1"),
        APPROVED_BY_QA("2"),
        REJECTED_BY_QA("3"),
        WAITING_QA_APPROVAL("4"),
        HIDDEN("5");

        private String approvalStatus;

        private NewsApprovalStatus(String status) {
            this.approvalStatus = status;
        }

        public String getStatus() {
            return approvalStatus;
        }

    }

    public enum FundAllocationTypes {
        BY_FUND,
        BY_MARKET,
        BY_STOCK
    }

    public enum NotificationKeyFields {
        TICKER_SERIAL("633"),
        EXCHANGE("4"),
        INV_TYPE("12577"),
        STOCK_OWNERSHIP_LIMIT_ID("24714"),
        OWNERSHIP_SERIES_ID("24710"),
        LIMIT_STOCK_DEF_ID("24712"),
        COMPANY_ID("2015"),
        FINANCIAL_YEAR("2000"),
        FINANCIAL_QUARTER("2001");

        private String fieldId;

        private NotificationKeyFields(String fieldId) {
            this.fieldId = fieldId;
        }

        public String getFieldId() {
            return fieldId;
        }
    }

    /**
     * investment type definitions
     */
    enum InvestmentType {
        SECTOR(1),
        STOCK(2),
        ASSET(3),
        COUNTRY(4),
        COMMODITIES(5),
        FUNDS(6);

        private static Map<Integer, InvestmentType> map = new HashMap<Integer, InvestmentType>(6);

        static {
            for (InvestmentType type : InvestmentType.values()) {
                map.put(type.typeId, type);
            }
        }

        private int typeId;

        InvestmentType(int typeId) {
            this.typeId = typeId;
        }

        public static InvestmentType getType(int typeId) {
            return map.get(typeId);
        }

        public int getTypeId() {
            return typeId;
        }
    }

    public enum NewsSourceType {
        ALL_NEWS_SOURCES("0"),
        MUBASHER("301-302"),
        //        BLOOMBERG("308"),
        DOWJONES("306"),
        LIVE_MUBASHER_DOW_JONES("301-302-306"),
        LIVE_MUBASHER_DOW_JONES_EXLUDED_HOT_NEWS_INDICATER("3"),
        LIVE_MUBASHER_DOW_JONES_SOURCE_EXLUDED_HOT_NEWS_INDICATER("302"),
        AGGREGATED_NEWS("Aggregated"),
        NON_AGGREGATED_NEWS_SOURCES("301-302-306");

        private static Map<String, NewsSourceType> topNewsTypes = new HashMap<String, NewsSourceType>(NewsSourceType.class.getFields().length);

        static {
            for (NewsSourceType newsType : NewsSourceType.values()) {
                topNewsTypes.put(newsType.typeId, newsType);
            }
        }

        private String typeId;

        NewsSourceType(String type) {
            this.typeId = type;
        }

        public static Map<String, NewsSourceType> getAllNewsSourceType() {
            return topNewsTypes;
        }

        public NewsSourceType getTopNewsType(String typeId) {
            return topNewsTypes.get(typeId);
        }

        public String getDefaultValue() {
            return typeId;
        }
    }

    public enum EmailReceiptType {
        IGNORE(0),
        TO(1),
        CC(2),
        BCC(3);

        private static Map<Integer, EmailReceiptType> map = new HashMap<Integer, EmailReceiptType>(6);

        static {
            for (EmailReceiptType type : EmailReceiptType.values()) {
                map.put(type.typeId, type);
            }
        }

        private int typeId;

        EmailReceiptType(int typeId) {
            this.typeId = typeId;
        }

        public EmailReceiptType getType(int typeId) {
            return map.get(typeId);
        }

        public int getTypeId() {
            return typeId;
        }
    }

    public enum SchedulerTypes {
        EOD_CENTRAL_TO_LOCAL_DB(true),
        EOD_IMAGE_DOWNLOAD(false),
        EOD_CAMPAIGN_EXPIRY_DATE_UPDATE(false),
        EOD_RELEASE_NOTE_PUBLISH(false),
        EOD_FINANCIAL_ANALYST_PREF(false),
        EOD_LOCALDB_TO_IMDB(false),

        QRTS_NOTIFICATION_CENTRAL_TO_LOCAL_DB(true),
        QRTS_NOTIFICATION_IMDB(false),

        QRTS_FUND_SNAPSHOT_CENTRAL_TO_LOCAL_DB(true),
        QRTS_FUND_SNAPSHOT_IMDB(false),

        QRTS_INDEX_SNAPSHOT_CENTRAL_TO_LOCAL_DB(true),
        QRTS_INDEX_SNAPSHOT_IMDB(false),

        QRTS_NOTIFICATION(false),

        QRTS_CURRENCY_RATES(true),
        QRTS_CURRENCY_RATES_CACHE(false),

        QRTS_MIX_TO_ORACLE_NEWS_INSERT_UPDATE_JOB(false),
        QRTS_NEWS_INSERT_UPDATE_JOB(false),

        QRTS_MIX_TO_ORACLE_ANNOUNCEMENT_INSERT_UPDATE_JOB(false),
        QRTS_ANNOUNCEMENT_INSERT_UPDATE_JOB(false),
        QRTS_ANNOUNCEMENT_EOD_JOB(false),

        EOD(false),
        QRTS_NEWS_EOD_BACKUP(false),
        QRTS_ANNOUNCEMENT_EOD_BACKUP(false),
        NEWS_INSERT_UPDATE_MANUAL(false),
        ANNOUNCEMNT_INSERT_UPDATE_MANUAL(false),
        NEWS_TAG_MANUAL(false),
        QRTS_TOP_NEWS_NOTIFICATION(false),
        QRTS_EOD_NEWS_JOB(false),

        MANUAL(false),
        TEST(false),
        QRTS_EMAIL_RETRY(false),
        QRTS_EMAIL_CLEANUP(false),
        QRTS_USER_NOTIFICATION_EMAILS(false),
        QRTS_SALES_NOTIFICATION_EMAILS(false),
        QRTS_CLUSTER_TEST(false),
        QRTS_CLUSTER_TEST_LISTENER(false),
        QRTS_SMS_MESSAGES(false),
        QRTS_EMAIL_NOTIFICATION(false),
        QRTS_EMAIL_ARCHIVE_JOB(false),
        QRTS_FINANCIAL_ANALYST_PREF_JOB(false),
        QRTS_FINANCIAL_SEGMENT_JOB(false),
        QRTS_FUND_TICKERS_JOB(false),
        QRTS_NEWS_HISTORY_UPDATE_JOB(false),
        QRTS_SOLR_HEALTH_CHECK_JOB(false),
        QRTS_SOLR_OPTIMIZED_JOB(false),

        DELETE_EXCEL_FILES(false),

        QRTS_UPDATE_CACHE_DATA(false);

        private boolean dependantJobAvailable;

        SchedulerTypes(boolean dependantJobAvailable) {
            this.dependantJobAvailable = dependantJobAvailable;
        }

        public boolean isDependantJobAvailable() {
            return dependantJobAvailable;
        }


    }

    public enum ShowNewsDetailsStatus {
        SHOW_FULL_NEWS_DETAILS(2),
        SHOW_NEWS_HEADLINE_ONLY(1),
        HIDE_NEWS_DETAILS(0);

        private int status;

        ShowNewsDetailsStatus(int status) {
            this.status = status;
        }

        public int getDefaultValue() {
            return status;
        }
    }

    public enum CampaignOperationStatus {
        CAMPAIGN_INSERT_SUCCESS(1),
        CAMPAIGN_INSERT_FAIL(-1),
        CAMPAIGN_UPDATE_SUCCESS(2),
        CAMPAIGN_UPDATE_FAIL(-2),
        INVALID_CAMPAIGN(-3),
        CAMPAIGN_NOT_FOUND(-4),
        CAMPAIGN_INSERT_SUCCESS_IMAGE_FAIL(-5),
        CAMPAIGN_UPDATE_SUCCESS_IMAGE_FAIL(-6);

        private int status;

        CampaignOperationStatus(int status) {
            this.status = status;
        }

        public int getDefaultValue() {
            return status;
        }
    }

    public enum ReleaseNoteStatus {
        PUBLISHED("P"),
        PUBLISH_PENDING("H"),
        UNPUBLISHED("U"),
        DELETED("D"),
        PENDING("W"),
        EXPIRED("E");

        private String status;

        ReleaseNoteStatus(String status) {
            this.status = status;
        }

        public String getDefaultValue() {
            return status;
        }
    }

    public enum ReleaseNoteOperationStatus {
        RELEASE_NOTE_OP_PENDING(0),
        RELEASE_NOTE_INSERT_SUCCESS(1),
        RELEASE_NOTE_INSERT_FAIL(-1),
        RELEASE_NOTE_UPDATE_SUCCESS(2),
        RELEASE_NOTE_UPDATE_FAIL(-2),
        RELEASE_NOTE_DELETE_SUCCESS(3),
        RELEASE_NOTE_DELETE_FAIL(-3),
        RELEASE_NOTE_PUBLISH_SUCCESS(4),
        RELEASE_NOTE_PUBLISH_FAIL(-4),
        RELEASE_NOTE_NOTIFY_SUCCESS(5),
        RELEASE_NOTE_NOTIFY_FAIL(-5),
        INVALID_RELEASE_NOTE(-6),
        RELEASE_NOTE_NOT_FOUND(-7);

        private int status;

        ReleaseNoteOperationStatus(int status) {
            this.status = status;
        }

        public int getDefaultValue() {
            return status;
        }
    }

    public enum ExcelFormatterType {

        DEFAULT(ExcelKeys.DEFAULT),
        TYP_DATE(ExcelKeys.TYP_DATE),
        NUMBER(ExcelKeys.NUMBER),
        TYP_FLOAT(ExcelKeys.TYP_FLOAT),
        TYP_TIME(ExcelKeys.TYP_TIME),
        TYP_HIDE(ExcelKeys.TYP_HIDE),
        FUND_CLASS(ExcelKeys.FUND_CLASS),
        FUND_META(ExcelKeys.FUND_META),
        COUNTRY_DESC(ExcelKeys.COUNTRY_DESC),
        FUND_META_M(ExcelKeys.FUND_META_M),
        FUND_META_I(ExcelKeys.FUND_META_I),
        PERCENTAGE(ExcelKeys.PERCENTAGE),
        SUKUK_TYPE(ExcelKeys.SUKUK_TYPE),
        BOND_TYPE(ExcelKeys.BOND_TYPE),
        FIXED_INCOME_TYPE(ExcelKeys.FIXED_INCOME_TYPE),
        COUPON_FREQUENCY(ExcelKeys.COUPON_FREQUENCY),
        TENOR(ExcelKeys.TENOR),
        REPORT_CATEGORY(ExcelKeys.REPORT_CATEGORY),
        REPORT_SUB_CATEGORY(ExcelKeys.REPORT_SUB_CATEGORY),
        REPORT_PUBLISHER(ExcelKeys.REPORT_PUBLISHER),
        REPORT_TITLE(ExcelKeys.REPORT_TITLE),
        INSTRUMENT(ExcelKeys.INSTRUMENT),
        COMPANY(ExcelKeys.COMPANY),
        REPORT_URL(ExcelKeys.REPORT_URL);

        private static Map<String, ExcelFormatterType> formatterTypeMap = new HashMap<String, ExcelFormatterType>();
        private String field;

        ExcelFormatterType(String field) {
            this.field = field;
        }

        static {
            for (ExcelFormatterType formatterType : values()) {
                formatterTypeMap.put(formatterType.field, formatterType);
            }
        }

        public static Map<String, ExcelFormatterType> getAllFormatterTypes() {
            return formatterTypeMap;
        }

    }

    public enum MonthDescription {

        JANUARY_0("January"),
        FEBRUARY_1("February"),
        MARCH_2("March"),
        APRIL_3("April"),
        MAY_4("May"),
        JUNE_5("June"),
        JUNE_6("July"),
        AUGUST_7("August"),
        SEPTEMBER_8("September"),
        OCTOBER_9("October"),
        NOVEMBER_10("November"),
        DECEMBER_11("December");

        private static Map<Integer, String> monthDescriptionMap = new HashMap<Integer, String>();
        private String month;

        MonthDescription(String field) {
            this.month = field;
        }

        static {
            for (MonthDescription monthDescription : values()) {
                monthDescriptionMap.put(Integer.valueOf(monthDescription.toString().split(Delimiter.UNDERSCORE)[1]), monthDescription.month);
            }
        }

        public static Map<Integer, String> getAllMonthDescriptionMap() {
            return monthDescriptionMap;
        }

    }

    public enum RequestTypes {
        COMPANY_REPORT,
        FEED_BACK,
        TRIAL_REQUEST,
        EMAIL_EXCEL_TO_FRIEND,
        EMAIL_EXCEL_TO_ME
    }

    public enum RegressionType {
        FUND,
        INDEX
    }

    public enum OwnershipLimitRequestType {
        SERIES,
        MARKET,
        STOCK
    }

    public enum MixNotificationOperationType {
        INSERT(1),
        UPDATE(2),
        DELETE(3);

        private int operationTypeId;

        MixNotificationOperationType(int operationTypeId) {
            this.operationTypeId = operationTypeId;
        }

        public int getOperationTypeId() {
            return operationTypeId;
        }
    }

    public enum DBStatus {
        COMPLETE(1),
        PENDING(0),
        FAILED(-1);

        private int status;

        DBStatus(int status) {
            this.status = status;
        }

        public int getDefaultValue() {
            return status;
        }
    }

    public enum DBOperationStatus {
        INSERT_SUCCESS(1),
        INSERT_FAIL(-1),
        UPDATE_SUCCESS(2),
        UPDATE_FAIL(-2),
        NOT_FOUND(-3);

        private int status;

        DBOperationStatus(int status) {
            this.status = status;
        }

        public int getDefaultValue() {
            return status;
        }
    }

    /**
     * Types of user interactions
     */
    public enum UserInteractionTypes {
        ASK_FOR_FREE_TRIAL(1),
        UNSUBSCRIBE_ALL_ALERTS(2),
        ASK_FOR_A_CALL(3),
        EDIT_MY_FAVOURITE_LIST(4),
        EXTEND_TRIAL(5),
        UNSUBSCRIBE(6);

        private static Map<Integer, UserInteractionTypes> map = new HashMap<Integer, UserInteractionTypes>(6);

        static {
            for (UserInteractionTypes type : UserInteractionTypes.values()) {
                map.put(type.typeId, type);
            }
        }

        private int typeId;

        UserInteractionTypes(int typeId) {
            this.typeId = typeId;
        }

        public static UserInteractionTypes getType(int typeId) {
            return map.get(typeId);
        }

        public int getUserInteractionTypeValue() {
            return typeId;
        }

    }

    public enum FinancialTypeDescription {

        IS("Income Statement"),
        BS("Balance Sheet"),
        CF("Cash Flow"),
        FR("Financial Ratios"),
        MR("Market Ratios");

        private static Map<String, String> financialDescriptionMap = new HashMap<String, String>();
        private String financialDescription;

        FinancialTypeDescription(String field) {
            this.financialDescription = field;
        }

        static {
            for (FinancialTypeDescription financialTypeDescription : values()) {
                financialDescriptionMap.put(financialTypeDescription.toString(), financialTypeDescription.financialDescription);
            }
        }

        public static Map<String, String> getFinancialTypeDescriptionMap() {
            return financialDescriptionMap;
        }

    }

    public enum CustomerPublicType {
        DECYPHA_USER(1),
        FINANCIAL_ANALYST(2);

        private static Map<Integer, CustomerPublicType> customerPublicTypeMap = new HashMap<Integer, CustomerPublicType>();

        static {
            for (CustomerPublicType customerPublicType : values()) {
                customerPublicTypeMap.put(customerPublicType.type, customerPublicType);
            }
        }

        private int type;

        CustomerPublicType(int type) {
            this.type = type;
        }

        public static CustomerPublicType getCustomerType(int type) {
            return customerPublicTypeMap.get(type);
        }

        public int getDefaultValue() {
            return type;
        }
    }


    public enum CorporateActionRequestTypes {
        SYMBOL(1),
        EXCHANGE(2),
        COUNTRY(3);

        private static Map<Integer, CorporateActionRequestTypes> typeMap = new HashMap<Integer, CorporateActionRequestTypes>();

        static {
            for (CorporateActionRequestTypes type : values()) {
                typeMap.put(type.type, type);
            }
        }

        private int type;

        CorporateActionRequestTypes(int type) {
            this.type = type;
        }

        public static CorporateActionRequestTypes getRequestType(int type) {
            return typeMap.get(type);
        }

        public int getDefaultValue() {
            return type;
        }

    }

    public enum CorporateActionSourceTypes {
        ANNOUNCEMENT(1),
        ANNUAL_REPORT(2),
        PRESS_RELEASE(3),
        NEWS(4);

        private static Map<Integer, CorporateActionSourceTypes> typeMap = new HashMap<Integer, CorporateActionSourceTypes>();

        static {
            for (CorporateActionSourceTypes type : values()) {
                typeMap.put(type.type, type);
            }
        }

        private int type;

        CorporateActionSourceTypes(int type) {
            this.type = type;
        }

        public static CorporateActionSourceTypes getSourceType(int type) {
            return typeMap.get(type);
        }

        public int getDefaultValue() {
            return type;
        }

    }

    public enum CorporateActionAttributeFormatTypes {
        STRING,
        NUMBER,
        STATUS,
        APPROVAL,
        DATE,
        CURRENCY,
        PERIOD
    }

    public enum CorporateActionApprovalStatus {
        FINAL,
        PROPOSED
    }

    public enum CorporateActionStatus {
        YES,
        NO
    }

    public enum ExcelFileDeletionStatus {
        FILES_DELETED_FOLDER_DELETED(2),
        FILES_DELETED_FOLDER_NOT_DELETED(1),
        NOTHING_DELETED(-1),
        NO_DIRECTORY_TO_DELETE(0);

        private int status;

        ExcelFileDeletionStatus(int type) {
            this.status = type;
        }

        public int getDefaultValue() {
            return status;
        }


    }

    public enum KPIFilteringFields {
        COMPANY_ID,
        COUNTRY_CODE,
        GICS_L3_CODE,
        SLA_LEVEL
    }

    final class FinancialModes {
        public static final String FINANCIAL_DATA = "1";
        public static final String FINANCIAL_COMPARISON_DATA = "2";
        public static final String FINANCIAL_FIELD_DATA = "3";
        public static final String FINANCIAL_COMPARISON_LATEST_DATA = "4";
        public static final String FINANCIAL_PAGE_DATA = "5";
        public static final String DAILY_RATIO_DATA = "6";
        public static final String FINANCIAL_SNAPSHOT_DATA = "7";

        private FinancialModes() {

        }
    }

    final class RequestAttributes {
        public static final String GLOBAL_SELECTION = "1";
        public static final String GLOBAL_SELECTION_TYPE = "2";
        public static final String GLOBAL_SELECTION_CODE = "3";

        private RequestAttributes() {

        }
    }

    final class CorporateActionCategoryTypes {
        public static final int NON_CAPITAL_DESC = 2;
        public static final int CAPITAL_EQUITY_DESC = 1;

        private CorporateActionCategoryTypes() {

        }
    }

    /* Response json types from mix or cache servers */
    final class ResponseTypes {
        public static final String TOP_STOCKS = "TOP";
        public static final String NEWS = "NWSL";
        public static final String NEWS_DETAILS = "NWS";
        public static final String ANNOUNCEMENTS = "ANNL";
        public static final String FUNDAMENTAL_DATA = "CDS";
        public static final String FINANCIAL_DATA = "COMPFIN";
        public static final String AVG_FINANCIAL_DATA = "AV";
        public static final String PRICE_SNAPSHOT = "SS";
        public static final String SYMBOL_INFO = "S";
        public static final String PRICE_HISTORY = "HIS";
        public static final String SYMBOL_DATA = "SYM";
        public static final String REPORT_DATA = "DS";
        public static final String HED = "HED";
        public static final String DAT = "DAT";
        public static final String RES = "RES";
        public static final String STAT = "STAT";
        public static final String ROW = "ROW";
        public static final String STYLE = "STYLE";
        public static final String LONGDES = "LONGDES";
        public static final String LONG_DES = "LONG_DES";
        public static final String QTR = "QTR";
        public static final String AVG = "AVG";
        public static final String COMPANY_INFO = "COMPINF";
        public static final String MKTS = "MKTS";
        public static final String SYMBOL_SEARCH = "SYMS";
        public static final String AUTH_USER_KEY = "UserKey";
        public static final String AUTH_USER_ID = "Id";
        public static final String AUTH_USER_VALUE = "UserValue";
        public static final String ANNOUNCEMENT_DETAILS = "ANN";
        public static final String RANGE = "RANGE";
        public static final String HEIGHT = "HEIGHT";
        public static final String CRITERIA = "CRITERIA";
        public static final String FUNDAMENTAL_DATA_NOTIFICATION = "FDN";
        public static final String LTSID = "LTSID";
        public static final String FUND_ACTION = "FNCA";
        public static final String FILE = "FILE";
        public static final String PGS = "PGS";
        public static final String PGI = "PGI";
        public static final String MINMAX = "MINMAX";

        private ResponseTypes() {
        }

    }

    /**
     * Additional data types(internal data types) pass from client to data access layer other than
     * Mix Web Tags
     */
    public static final class DataType {

        public static final String TYPE = "TYPE";
        public static final String HEAD = "HEAD";
        public static final String DETAILS = "DETAILS";
        public static final String NEWS_COUNT = "NEWS_COUNT";
        public static final String NEWS_TYPE = "NEWS_TYPE";
        public static final String ANNOUNCEMENTS_TYPE = "ANNOUNCEMENTS_TYPE";
        public static final String REQUIRED_AMOUNT = "REQUIRED_AMOUNT";
        public static final String HEAD_IMDB = "HEAD_IMDB";
        public static final String NEWS_TOP_STORIES = "NEWS_TOP_STORIES";
        public static final String COP_ACT_SEARCH_DATA = "COP_ACT_SEARCH_DATA";
        public static final String RELATED_INSTRUMENTS = "RELATED_INSTRUMENTS";
        public static final String SYMBOL_NEWS = "SYMBOL_NEWS";
        public static final String ENABLE_SYMBOL_SEARCH_REQUEST = "SYMBOLS";
        public static final String ENABLE_INDIVIDUALS_SEARCH_REQUEST = "INDIVIDUALS";
        public static final String ANNOUNCEMENT_DETAILS = "ANNOUNCEMENT_DETAILS";

        private DataType() {
        }
    }

    /**
     * Types of news available in the dfn system
     */
    public static final class NewsTypes {

        public static final int EQUITY_NEWS = 1;
        public static final int MACRO_ECONOMY_NEWS = 2;
        public static final int OPTIONS_NEWS = 3;
        public static final int FUTURES_NEWS = 4;
        public static final int MUTUAL_FUNDS_NEWS = 5;
        public static final int FIXED_INCOME_NEWS = 6;
        public static final int FOREX_NEWS = 7;
        public static final int INDEX_NEWS = 8;
        public static final int MONEY_MARKET_NEWS = 9;
        public static final int COMMODITY_NEWS = 10;
        public static final int STOCK_BORROWING_NEWS = 11;
        public static final int COUNTRY_DATA_NEWS = 12;
        public static final int FUNDS_NEWS = 13;
        public static final int CURRENCY_NEWS = 14;
        public static final int INDUSTRY_NEWS = 15;
        public static final int SECTOR_NEWS = 16;
        public static final int INDIVIDUAL_NEWS = 17;
        public static final int ALL_NEWS = 18;
        public static final int INDIVIDUALS_SANP_NEWS = 19;
        public static final int ALL_NEWS_SECTIONS = 20;
        public static final int IPO_NEWS = 21;
        public static final int GET_NEWS_BY_ID = 22;
        public static final int MA_NEWS = 23;
        public static final int ALL_NEWS_SECTIONS_ORACLE = 24;
        public static final int TOP_NEWS_HISTORY = 25;
        public static final int TOTAL_NEWS_COUNT = 26;
        public static final int NEWS_INDIVIDUALS = 27;
        public static final int EDITORIAL_NEWS = 28;

        private NewsTypes() {
        }
    }

    /*NewsHeadLines data related request types should be defined here*/
    public static final class News {
        public static final int NEWS_SEARCH = 84;
        public static final int NEWS_KEYWORDS_REQUEST = 27;
        public static final int NEWS_BODY = 28;
        public static final int NEWS_PROVIDER = 29;

        private News() {
        }
    }

    public static final class Alerts {
        public static final int SET_PRICE_AND_NEWS_ALERTS_REQUEST = 83;

        private Alerts() {
        }
    }

    /*Symbol data related request types should be defined here*/
    public static final class Symbol {
        public static final int PARTIAL_SYMBOL_SEARCH = 53;
        public static final int SYMBOL_DATA = 52;

        private Symbol() {
        }
    }

    /*Meta data related request types should be defined here*/
    public static final class FileDownloadRequests {
        public static final int DICTIONARY = 300;

        private FileDownloadRequests() {
        }
    }

    /**
     * USed as decimeters.
     */
    public static final class Delimiter {
        public static final char FD = '\u0019'; /* ASCII name 'EM' */
        public static final char DS = '\u0002'; /* ASCII name 'STX' */
        public static final char VL = '\u007c'; /* ASCII name '|' */
        public static final char ID = '\u0018'; /* ASCII name 'CAN'*/
        public static final char COMMA = '\u002c'; /* ASCII name 'COMMA'*/
        public static final char ETX = '\u0003'; /* ASCII name 'ETX'*/
        public static final char RS = '\u001E'; /*ASCII name RS (Record Seperator)*/
        public static final char EQUAL = '\u003D'; /* ASCII name '='*/
        public static final char AMPERSAND = '\u0026'; /* ASCII name '&'*/
        public static final char TILDE = '\u007E'; /* ASCII name '~'*/
        public static final char DOT = '\u002E'; /*ASCII name 'PERIOD'*/
        public static final char HASH = '\u0023'; /* '#' */
        public static final char NEW_LINE = '\n'; /*ASCII name 'NL'*/
        public static final char FS = '\u001c';
        public static final char COLON = '\u003a'; /*Delimiter  name ':'*/
        public static final char SEMICOLON = ';'; /*Delimiter  name ';'*/
        public static final char LCB = '\u007b'; /*ASCII name '{'*/
        public static final char LSB = '\u005b'; /*ASCII name '['*/
        public static final String EOL = "\n";
        public static final char RCB = '\u007d'; /*ASCII name '}'*/
        public static final char RSB = '\u005d'; /*ASCII name ']'*/
        public static final char ASTERISK = '\u002A'; /*ASCII name '*' */
        public static final String AT_SIGN = "@";
        public static final String QUESTION_MARK = "?";
        public static final String HYPHEN = "-";
        public static final String UNDERSCORE = "_";
        public static final String OPN_CL_BRACKET = "{";
        public static final String CLS_CL_BRACKET = "}";
        public static final String OPN_SQ_BRACKET = "[";
        public static final String CLS_SQ_BRACKET = "]";
        public static final String D_QUOTE = "\"";
        public static final String S_ASTERISK = "*";
        public static final String QUOTE = "'";
        public static final String DQUOTE = "\"";
        public static final String QUOTE_COMMA_QUOTE = "','";
        public static final String DQUOTE_COMMA_DQUOTE = "\",\"";
        public static final String VERTICAL_BAR_SPLIT = "\\|";
        public static final char CHAR28 = (char) 28;/*Files sep*/
        public static final char CHAR29 = (char) 29;/*Group sep*/
        public static final char CHAR30 = (char) 30;/*Record sep*/
        public static final String FORWARD_SLASH = "/";

        private Delimiter() {
        }
    }

    /**
     * Response content types
     */
    public static final class ContentTypes {
        public static final String JSON = "application/json;charset=UTF-8";
        public static final String PDF = "application/pdf";
        //Excel related fields
        public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static final String XLSX_HEADER = "Content-Disposition";
        public static final String XLSX_ATTACHMENT = "attachment; filename=";
        public static final String XLSX_EXTENSION = ".xlsx";

        private ContentTypes() {
        }
    }

    /**
     * Keep default values in the system
     */
    public static final class DefaultValues {
        public static final int DECIMAL_PLACES = 2;

        private DefaultValues() {

        }
    }

    /**
     * MIX related constants.
     */
    public static final class MIXDataField {
        public static final String RT = "RT";
        public static final String UID = "UID";
        public static final String LN = "LN";
        public static final String SID = "SID";
        public static final String CID = "CID";
        public static final String DM = "DM";
        public static final String IC = "IC";
        public static final String L = "L";
        public static final String DL = "DL";
        public static final String S = "S";
        public static final String SDES = "SDES";
        public static final String DES = "DES";
        public static final String RGID = "RGID";
        public static final String TMPL = "TMPL";
        public static final String E = "E";
        public static final String UE = "UE";
        public static final String AE = "AE";
        public static final String IFLD = "IFLD";
        public static final String XFLD = "XFLD";
        public static final String LI = "LI";
        public static final String IR = "IR";
        public static final String MD = "MD";
        public static final String OS = "OS";
        public static final String PS = "PS";
        public static final String PSS = "PSS";
        public static final String IMDO = "IMDO";
        public static final String IMDP = "IMDP";
        public static final String TE = "TE";
        public static final String TT = "TT";
        public static final String DS = "DS";
        public static final String NC = "NC";
        public static final String SK = "SK";
        public static final String ST = "ST";
        public static final String SC = "SC";
        public static final String AD = "AD";
        public static final String DR = "DR";
        public static final String SM = "SM";
        public static final String IS = "IS";
        public static final String ISM = "ISM";
        public static final String CT = "CT";
        public static final String CM = "CM";
        public static final String H = "H";
        public static final String M = "M";
        public static final String PGI = "PGI";
        public static final String PGS = "PGS";
        public static final String ROW = "ROW";
        public static final String PRV = "PRV";
        public static final String INS = "INS";
        public static final String IEXG = "IEXG";
        public static final String INC = "INC";
        public static final String IIND = "IIND";
        public static final String MT = "MT";
        public static final String MST = "MST";
        public static final String SF = "SF";
        public static final String SO = "SO";
        public static final String AS = "AS";
        public static final String SS = "SS";
        public static final String AIS = "AIS";
        public static final String TOPC = "TOPC";
        public static final String SD = "SD";
        public static final String ED = "ED";
        public static final String AI = "AI";
        public static final String AUTH = "AUTH";
        public static final String APPID = "APPID";
        public static final String PVER = "PVER";
        public static final String ENT = "ENT";
        public static final String BOD = "BOD";
        public static final String ME = "ME";
        public static final String CAT = "CAT";
        public static final String NI = "NI";
        public static final String MC = "MC";
        public static final String CUR = "CUR";
        public static final String DEF = "DEF";
        public static final String DEP = "DEP";
        public static final String VID = "VID";
        public static final String ISINC = "ISINC";
        public static final String ISYM = "ISYM";
        public static final String UNC = "UNC";
        public static final String CLS = "CLS";
        public static final String DT = "DT";
        public static final String HIG = "HIG";
        public static final String LOW = "LOW";
        public static final String OP = "OP";
        public static final String VOL = "VOL";
        public static final String OPWD = "OPWD";
        public static final String NPWD = "NPWD";
        public static final String EC = "EC";
        public static final String PD = "PD";
        public static final String VER = "VER";
        public static final String MDL = "MDL";
        public static final String SME = "SME";
        public static final String ASM = "ASM";
        public static final String WN = "WN";
        public static final String WT = "WT";
        public static final String TLI = "TLI";
        public static final String TIR = "TIR";
        public static final String IN = "IN";
        public static final String OUT = "OUT";
        public static final String VT = "VT";
        public static final String ACCR = "ACCR";
        public static final String GRP = "GRP";
        public static final String CAL = "CAL";
        public static final String AVG = "AVG";
        public static final String DOM = "DOM";
        public static final String DOM_EXCHANGE = "E:";
        public static final String DOM_SECTOR = "SECT:";
        public static final String LONGDES = "LONGDES";
        public static final String FRM = "FRM";
        public static final String TPGC = "TPGC";
        public static final String TPLC = "TPLC";
        public static final String MVC = "MVC";
        public static final String MPC = "MPC";
        public static final String MTC = "MTC";
        public static final String TSI = "TSI";
        public static final String ASYT = "ASYT";
        public static final String AAE = "AAE";
        public static final String PT = "PT";
        public static final String SOC = "SOC";
        public static final String CRT = "CRT";
        public static final String SRC = "SRC";//TODO Need to verify
        public static final String ATURL = "ATURL";
        public static final String SYS = "SYS";//TODO Need to verify
        public static final String REL = "REL";
        public static final String VW = "VW";
        public static final String OPTNL = "OPTNL";
        public static final String MODE = "MODE"; // TODO Need to verify
        public static final String RWLM = "RWLM";
        public static final String NOD = "NOD";
        public static final String SYMC = "SYMC";
        public static final String SYMT = "SYMT";
        public static final String SSYMT = "SSYMT";
        public static final String NOS = "NOS";
        public static final String CAE = "CAE";
        public static final String CAS = "CAS";
        public static final String VS = "VS";
        public static final String CRP = "CRP";
        public static final String TR = "TR";
        public static final String PI = "PI";
        public static final String TGVCC = "TGVCC";
        public static final String TLVCC = "TLVCC";
        public static final String TMIC = "TMIC";
        public static final String TMOC = "TMOC";
        public static final String R = "R";
        public static final String SEC = "SEC";
        public static final String ACR = "ACR";
        public static final String DFNS = "DFNS";
        public static final String MOD = "MOD";
        public static final String ES = "ES";
        public static final String BT = "BT";
        public static final String SPCODE = "SPCODE";
        public static final String TIMESTAMP = "TS";
        public static final String TYPE_OF_THE_INFORMATION = "TYP";
        public static final String TRANSACTION_SEQ_ID = "TSID";
        public static final String CLASSIFICATION_ID = "CID";
        public static final String NPRT = "NPRT";
        public static final String CLASSIFICATION_CODE = "CCODE";
        public static final String CONTRIBUTOR_CODE = "CTC";
        public static final String MOST_ACTIVE_BY_MARKET_CAP_COUNT = "MAMCC";
        public static final String INFORMATION_TYPE = "INFT";
        public static final String ATTR = "ATTR";
        public static final String MIME = "MIME";
        public static final String BC = "BC";
        public static final String FILEID = "FILEID";
        public static final String MIMG = "MIMG";
        public static final String L2BOOK = "L2BOOK";
        public static final String FS = "FS";
        public static final String MAVC = "MAVC";
        public static final String MAMCC = "MAMCC";
        public static final String CC = "CC";
        public static final String AN = "AN";
        public static final String SCDT = "SCDT";
        public static final String ID = "ID";
        public static final String SI = "SI";
        public static final String GEO = "GEO";
        public static final String HTIND = "HTIND";
        public static final String ITK = "ITK";
        public static final String DTL = "DTL";
        public static final String OWN_COMP_ID = "OWN_COMP_ID";
        public static final String HEAD = "HEAD";
        public static final String SYM = "SYM";
        public static final String INDV_NAME = "INDV_NAME";
        public static final String KSE = "KSE";
        //advanced search new country filter tag
        public static final String CTRY = "CTRY";
        //news sources
        public static final String SCODE = "SCODE";
        public static final String SOU = "SOU";
        public static final String ATCOD = "ATCOD";
        public static final String CFT = "CFT";
        public static final String FST = "FST";
        public static final String FY = "FY";
        public static final String TY = "TY";
        public static final String FQ = "FQ";
        public static final String TQ = "TQ";
        public static final String LVL = "LVL";
        public static final String FV = "FV";
        public static final String FR = "FR";
        public static final String MR = "MR";
        public static final String CF = "CF";
        //
        public static final String IFC = "IFC";
        public static final String FC = "FC";
        public static final String FDK = "FDK";
        //Lot size
        public static final String LS = "LS";
        // base Exchange
        public static final String BE = "BE";
        //base Symbol
        public static final String BS = "BS";
        //base Instrument Type
        public static final String BI = "BI";
        //index Type
        public static final String IDXTYPE = "IDXTYPE";
        //first Transaction Date
        public static final String FT = "FT";
        //
        public static final String GICSL1 = "GICSL1";
        public static final String GICSL2 = "GICSL2";
        public static final String GICSL3 = "GICSL3";
        public static final String GICSL4 = "GICSL4";
        public static final String PRTCL = "PRTCL";
        public static final String PDM = "PDM";
        public static final String RND = "RND";
        public static final String CTI = "CTI";   //Contributor ID | integer
        public static final String I = "I";  //Instrument | String
        public static final String ROLE = "ROLE";  //User role as defined in DCMS engine | String | A-admin
        public static final String SCAT = "SCAT";  //Sub Category | String
        public static final String RD = "RD"; // report Date
        public static final String CIT = "CIT"; //Company Information Type
        public static final String DESIG_ID = "DESIG_ID"; // designation Id used in company profile
        public static final String DESC = "DESC"; // index description used in company profile
        public static final String CHANGE_TIME = "CHANGE_TIME"; //
        public static final String RSD = "RSD";//report start date
        public static final String RED = "RED";// report end date
        public static final String CON = "CON"; //country
        public static final String CTRY_CODE = "CTRY_CODE"; //country
        public static final String RESIDENCY = "RESIDENCY"; //country
        public static final String AST = "AST"; //Asset class code
        public static final String PUBID = "PUBID"; //Asset class code
        public static final String ASC = "ASC";  // sort order used in calendarEvent
        // corporate action related params
        public static final String INITIAL_PAR_VALUE = "OLD_PAR_VALUE";
        public static final String INITIAL_NUM_SHARES = "NUMBER_OF_SHARES";
        public static final String FIRST_CA_DATE = "EFFECTIVE_DATE";
        //News related params
        public static final String STATUS = "STATUS";   //news status
        public static final String HED = "HED";  //news headline
        public static final String TSID = "TSID";    //ticker id
        public static final String SSID = "SSID";    //source id
        public static final String EDT = "EDT";
        public static final String GOV = "GOV";
        public static final String INDV = "INDV";
        public static final String INDST = "INDST";
        public static final String MKT = "MKT";
        public static final String PRODSRV = "PRODSRV";
        public static final String INTCLS = "INTCLS";
        public static final String APPRVSTAT = "APPRVSTAT";
        public static final String URL = "URL";
        public static final String CREUSR = "CREUSR";
        public static final String LUPDUSR = "LUPDUSR";
        public static final String SRCSEQ = "SRCSEQ";
        public static final String QACMNTS = "QACMNTS";
        public static final String NEWS_DATE = "NEWS_DATE";
        // notification
        public static final String ITID = "ITID";    //infomation type id
        public static final String OT = "OT";    //operation type id
        public static final String CL = "CL";    // contribution list
        public static final String K = "K";    // key
        public static final String FID = "FID";    // field id
        public static final String VAL = "VAL";    // value
        public static final String FDN = "FDN";
        //Announcement related params
        public static final String PRIO = "PRIO";    //Announcement priority
        public static final String ATCODE = "ATCODE";  //Announcement attachement codes
        public static final String DCMSID = "DCMSID";  //attachment id
        public static final String FUND_MANAGER_LIST = "FUND_MANAGER_LIST";
        public static final String IPO_RECEIVER_LIST = "IPO_RECEIVER_LIST";
        public static final String FUND_OWNER_SEC_LIST = "FUND_OWNER_SEC_LIST";
        public static final String MGT_COMPANY_ID = "MGT_COMPANY_ID";
        public static final String DESIGNATION = "DESIGNATION";
        public static final String COMPANY_ID = "COMPANY_ID";
        public static final String COMPANY_NAME = "COMPANY_NAME";
        public static final String MATURITY_DATE = "MATURITY_DATE";
        public static final String DATE_OF_ISSUE = "DATE_OF_ISSUE";
        public static final String MATURITY_PERIOD = "MATURITY_PERIOD";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String COMPANY_TYPE = "COMPANY_TYPE";
        public static final String OWN_PCT = "OWN_PCT";
        public static final String OWN_PCT_IND = "OWN_PCT_IND";
        public static final String COUNTRY_CODE = "COUNTRY_CODE";
        public static final String COUNTRY_NAME = "COUNTRY_NAME";
        public static final String LOGO = "LOGO";
        public static final String INDIVIDUAL_NAME = "INDIVIDUAL_NAME";
        public static final String INDIVIDUAL_ID = "INDIVIDUAL_ID";
        public static final String INDIVIDUAL_LAST_NAME = "INDIVIDUAL_LAST_NAME";
        public static final String OWN_COMP_NAME = "OWN_COMP_NAME";
        public static final String OWNERSHIP_DATE = "OWNERSHIP_DATE";
        public static final String OWNERSHIP_DATE_VWAP = "OWNERSHIP_DATE_VWAP";
        public static final String NUMBER_OF_SHARES_ON_DATE = "NUMBER_OF_SHARES_ON_DATE";
        public static final String LISTING_STATUS = "LISTING_STATUS";
        public static final String CITY = "CITY";
        public static final String INDUSTRY = "INDUSTRY";
        public static final String PERCENTAGE_NA = "PERCENTAGE_NA";
        public static final String PREFIX = "PREFIX";
        public static final String MGT_COMP_NAME = "MGT_COMP_NAME";
        /**
         * Financial Mix Headers
         */
        public static final String IS_RotBasEPS = "IS_RotBasEPS";
        public static final String STK = "STK"; //Search like
        public static final String SCT = "SCT"; //Search like
        public static final String COMPID = "COMPID"; //Search like
        public static final String Y = "Y"; //Search like
        public static final String Q = "Q"; //Search like
        public static final String LTP = "LTP"; //Search like
        public static final String BCD = "BCD"; // billing code
        public static final String PROFILE = "PROFILE"; // billing code
        //Related Instruments - asset type params
        public static final String RELATION_CLASS_ID = "RELATION_CLASS_ID";
        //Authentication
        public static final String UNM = "UNM";
        public static final String PWD = "PWD";
        public static final String PRD = "PRD";
        public static final String SSO = "SSO";
        public static final String ATH = "ATH";
        public static final String USER_VALUE = "UserValue";
        public static final String Id = "Id";
        public static final String INC_EXG = "INC_EXG";
        public static final String EXG = "EXG";
        public static final String WTP = "WTP";
        public static final String USER_KEY = "UserKey";
        public static final String PRODUCT = "PRODUCT";
        public static final String NGP = "NGP";
        public static final String UT = "UT";
        public static final String LGR = "LGR";
        //screener related
        public static final String REQID = "REQID";
        public static final String PREVREQID = "PREVREQID";
        public static final String FPERIOD = "FPERIODID";
        public static final String FYEAR = "FYEAR";
        public static final String FCALTYPE = "FCALTYPE";
        // Notification related params
        public static final String INFT = "INFT";
        // investments
        public static final String SUBS_COMP_ID = "SUBS_COMP_ID";
        //coupon frequency
        public static final String COUPON_FREQUENCY = "COUPON_FREQUENCY";
        public static final String FIRST_LAST_NAME = "FIRST_LAST_NAME";
        //Jasper reports
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String MIDDLE_NAME = "MIDDLE_NAME";
        public static final String MAIN_ACTIVITY = "MAIN_ACTIVITY";
        public static final String EST_DATE = "EST_DATE";
        public static final String ADDR = "ADDR_1";
        public static final String PHN = "PHN";
        public static final String FAX = "FAX";
        public static final String WEB = "WEB";
        public static final String EMAIL = "EMAIL";
        public static final String SYMBOL_DESCRIPTION = "SYMBOL_DESCRIPTION";
        public static final String LISTING_DATE = "LISTING_DATE";
        public static final String TOTAL_STOCKS = "TOTAL_STOCKS";
        public static final String SECT_DSC = "SECT_DSC";
        public static final String CURRENCY = "CURRENCY";
        public static final String FREE_FLOAT = "FREE_FLOAT";
        public static final String INSTRUMENT_TYPE = "INSTRUMENT_TYPE";
        public static final String DUR_YEAR = "DUR_YEAR";
        public static final String QUARTER_REQUESTED = "QUARTER_REQUESTED";
        public static final String MGT_RESIGN_DATE = "MGT_RESIGN_DATE";
        public static final String MGT_START_DATE = "MGT_START_DATE";
        public static final String COMP_MGT_RESIGN_DATE = "RESIGNATION_DATE";
        public static final String COMP_MGT_START_DATE = "DESIGNATION_DATE";
        // investor types
        public static final String SERIES_NAME = "SERIES_NAME";
        public static final String NATIONALITY_ID = "NATIONALITY_ID";
        public static final String INVESTOR_TYPE_ID = "INVESTOR_TYPE_ID";
        public static final String INVESTMENT_DATE = "INVESTMENT_DATE";
        public static final String EXCHANGE = "EXCHANGE";
        public static final String INV_TYP_STATUS = "INV_TYP_STATUS";
        // Document Search
        public static final String REPORT_DATE = "REPORT_DATE";
        public static final String EXCHANGES_LIST = "EXCHANGES_LIST";
        public static final String TICKER_SERIAL_LIST = "TICKER_SERIAL_LIST";
        public static final String DOC_TITLE = "DOC_TITLE";
        public static final String PROVIDER = "PROVIDER";
        public static final String PUBLISHER = "PUBLISHER";
        public static final String CTRY_CODE_LIST = "CTRY_CODE_LIST";
        public static final String LANGUAGE_CODE = "LANGUAGE_CODE";
        public static final String COMPANY_ID_LIST = "COMPANY_ID_LIST";
        public static final String SYMBOL = "SYMBOL";
        public static final String PL = "PL";
        public static final String DOC_ID = "DOC_ID";
        public static final String FILE = "FILE";
        public static final String FILE_GUID = "FILE_GUID";
        public static final String SCT_ID = "SCT_ID";
        public static final String CT_ID = "CT_ID";
        public static final String FILE_LANGUAGE = "FILE_LANGUAGE";
        public static final String REGION_LIST = "REGION_LIST";
        public static final String YEAR_LIST = "YEAR_LIST";
        public static final String PERIOD_LIST = "PERIOD_LIST";
        public static final String YEAR = "YEAR";
        public static final String PERIOD_ID = "PERIOD_ID";
        public static final String CATEGORY_ID = "CATEGORY_ID";
        //TOP News
        public static final String NEWS_STORY = "NEWS_STORY";
        public static final String EDITION_ID = "EDITION_ID";
        public static final String SECTION_ID = "SECTION_ID";
        public static final String COMPANY_TYPE_ID = "COMPANY_TYPE_ID";
        public static final String UPDATE_FREQUENCY = "UPDATE_FREQUENCY";
        public static final String NAV_FREQ_ID = "NAV_FREQ_ID";
        public static final String ELIGIBILITY_ID = "ELIGIBILITY_ID";
        public static final String LST = "LST";  //LAST_SYNC_TIME (NEWS)
        public static final String TNES = "TNES";  //TOP NEWS EDITION SECTION
        public static final String ITS = "ITS";  //IS TOP STORY (NEWS)
        public static final String SL = "SL";  //symbol list
        public static final String INS_TRD_TYPE = "INS_TRD_TYPE";
        public static final String COM = "COM";
        public static final String ACTION_ID = "ACTION_ID"; //corporate action id
        public static final String NEWS_SOURCE = "NEWS_SOURCE";
        public static final String LUT = "LUT";
        public static final String CD = "CD";

        public static final String REG_ID = "REG_ID";

        private MIXDataField() {
        }
    }

    final class MixOperationType {
        public static final String EQUALS = "0";
        public static final String LESS_THAN = "1";
        public static final String LESS_THAN_EQUAL = "2";
        public static final String GREATER_THAN = "3";
        public static final String GREATER_THAN_EQUAL = "4";

        private MixOperationType() {
        }
    }

    public static final class CustomDataField {
        public static final String PAGE_COUNTS = "C_PAGE_COUNTS";
        public static final String MAX_PAGE_COUNT = "C_MAX_PAGE_COUNT";
        public static final String PAGE_ID = "C_PAGE_ID";
        public static final String ACTUAL_PGS = "C_ACTUAL_PGS";
        public static final String DIRECT_DATA = "C_DIRECT_DATA";
        public static final String INS_SHORT_DESC = "C_INS_SHORT_DESC"; // short description of related instruments
        public static final String INS_DESC = "C_INS_DESC";
        public static final String EXG_DESC = "C_EXG_DESC"; // exchange description
        public static final String SD = "C_SD";
        public static final String ED = "C_ED";
        public static final String PRD = "C_PRD";
        public static final String IS_ALL_CON = "C_IS_ALL_CON";
        public static final String IS_CONTRY_ONLY = "C_IS_CONTRY_ONLY";
        public static final String PERIOD = "PERIOD";
        public static final String PERIOD_TYPE = "PERIOD_TYPE";
        public static final String CONTROL_PATH = "CONTROL_PATH";
        public static final String CORPORATE_ACTION_PATH = "CORPORATE_ACTION_PATH";
        public static final String COUNTRIES = "COUNTRIES";
        public static final String L = "L";
        public static final String DATA_UPDATE_SEQ = "DATA_UPDATE_SEQ";
        public static final String NEWS_FILTER_TYPE = "NEWS_FILTER_TYPE";
        public static final String NEWS_ACCESS_DB = "NEWS_ACCESS_DB";
        public static final String NEWS_SECTION_METADATA = "NEWS_SECTION_METADATA";
        public static final String NEWS_CONTENT_TYPE = "NEWS_CONTENT_TYPE";
        public static final String COUNTRY_CODE_LIST = "COUNTRY_CODE_LIST";
        public static final String NEWS_FROM_DATE_TIME = "NEWS_FROM_DATE_TIME";
        public static final String NEWS_TO_DATE_TIME = "NEWS_TO_DATE_TIME";
        public static final String DE = "C_DE";
        public static final String PAGE_SECTION_ID = "PAGE_SECTION_ID";
        public static final String IID = "IID";
        public static final String CID = "CID";
        public static final String PAGE_INDEX = "PAGE_INDEX";
        public static final String PAGE_SIZE = "PAGE_SIZE";
        public static final String FINANCIAL_TYPE = "FINANCIAL_TYPE";
        public static final String FINANCIAL_DATA_TYPE = "FINANCIAL_DATA_TYPE";
        public static final String NEWS_PROFILE_TYPES = "NEWS_PROFILE_TYPES";
        public static final String COMPANY_NAME = "COMPANY_NAME";
        public static final String INDIVIDUAL_NAME = "INDIVIDUAL_NAME";
        public static final String COUNTRY_NAME = "COUNTRY_NAME";
        public static final String MARKET_NAME = "MARKET_NAME";
        public static final String INDUSTRY_NAME = "MARKET_NAME";
        public static final String NSID = "NSID";     //news section id
        public static final String CUSTOM_QUERY = "CUSTOM_QUERY";
        public static final String SECTION_TYPE = "SECTION_TYPE";
        public static final String SYM = "SYM";
        public static final String EXG = "EXG";
        public static final String COUNTRY_CODE = "COUNTRY_CODE";
        public static final String REGION_CODE = "REGION_CODE";
        public static final String MEDIA_ID = "MEDIA_ID";
        public static final String MEDIA_PGI = "MEDIA_PGI";
        public static final String LIST_ITK = "LIST_ITK";
        public static final String USER_NAME = "USER_NAME";
        public static final String PASSWORD_HASH = "PASSWORD_HASH";
        public static final String PASSWORD_NEW_HASH = "PASSWORD_NEW_HASH";
        public static final String USER_PREFERENCE = "USER_PREFERENCE";
        public static final String USER_PROFILE = "USER_PROFILE";
        public static final String RESIDENCY = "RESIDENCY";
        public static final String COUNT = "COUNT";
        public static final String ORDER = "ORDER";
        public static final String FIELD = "FIELD";
        public static final String ALL = "*";
        public static final String SYMBOL = "SYMBOL";
        public static final String EXCHANGE = "EXCHANGE";
        public static final String MAIN_GROUP_ID = "MAIN_GROUP_ID";
        public static final String EVENT_TITLE_ID = "EVENT_TITLE_ID";
        public static final String EFFECTIVE_DATE = "EFFECTIVE_DATE";
        public static final String EVENT_START_DATE = "EVENT_START_DATE";
        public static final String ANNOUNCEMENT_COUNT = "ANNOUNCEMENT_COUNT";
        public static final String SET_PUB_DATA = "SET_PUB_DATA";
        public static final String SET_TICKER_DATA = "SET_TICKER_DATA";
        public static final String SET_MARKET_DATA = "SET_MARKET_DATA";
        public static final String LAST_LOGIN_IP = "LAST_LOGIN_IP";
        public static final String CACHE_KEY_TYPE = "CACHE_KEY_TYPE";
        public static final String MEDIA_PROVIDER = "MEDIA_PROVIDER";
        public static final String GICS_L2_LAN = "GICS_L2_LAN";
        public static final String GICS_L3_LAN = "GICS_L3_LAN";
        public static final String OWNERSHIP_VAL = "OWNERSHIP_VAL";
        public static final String CURRENCY = "CURRENCY";
        public static final String NEWS_ID_LIST = "NEWS_ID_LIST";
        public static final String ENABLE_FILTERED_QUERY = "ENABLE_FILTERED_QUERY";
        public static final String MANAGER_ID = "MANAGER_ID";
        public static final String GICS_L1 = "GICS_L1";
        public static final String GICS_L2 = "GICS_L2";
        public static final String COUNTRY_DESC = "COUNTRY_DESC";
        public static final String MAID = "mid";
        public static final String IS_DIRECT = "C_IS_DIRECT";
        public static final String INVESTMENT_DATE = "C_INVESTMENT_DATE";
        public static final String REQ_PRD = "C_REQ_PRD";
        public static final String IM_PLR = "IM_PLR"; //is IMDB news/announcements limit reached
        public static final String IM_M_PGI = "IM_M_PGI"; //IMDB Max page index
        public static final String ODB_OFFSET = "IM_OFFSET"; //Oracle DB NEWS offset value
        public static final String TOTAL_COUNT = "TOTAL_COUNT"; //toal new count
        public static final String T_N_SEC = "T_N_SEC"; //Top news SECTION
        public static final String T_N_EDT = "T_N_EDT"; //Top news EDITION
        public static final String IMDB_OFFSET = "IMDB_OFFSET"; //IMDB NEWS offset value
        public static final String HAS_NEXT = "HAS_NEXT";
        public static final String R_TIMEOUT = "R_TIMEOUT";
        public static final String NI_EXD = "NI_EXD";  //News id exclude request param
        public static final String NEWS_ID_LIST_EXCLUDE = "NEWS_ID_LIST_EXCLUDE";  //'Not in id list'
        public static final String TOP_NEWS_ID_LIST_INCLUDE = "TOP_NEWS_ID_LIST_INCLUDE";  //'Not in id list'
        public static final String ANN_ID_LIST = "ANN_ID_LIST";
        public static final String DOC_ID_LIST = "DOC_ID_LIST";
        public static final String SFY = "SFY";
        public static final String SFQ = "SFQ";
        public static final String STY = "STY";
        public static final String STQ = "STQ";
        public static final String IS_ERROR_PERIOD = "IS_ERROR_PERIOD";
        public static final String IS_LATEST_FINANCIAL = "IS_LATEST_FINANCIAL";
        public static final String IS_PERIOD_REQUIRED = "IS_PERIOD_REQUIRED";
        public static final String IS_ALL_AVG_RATIO_REQUIRED = "IS_ALL_AVG_RATIO_REQUIRED";
        public static final String CALENDER = "CALENDER";
        public static final String NEWS_UPDATE_TYPE = "NEWS_UPDATE_TYPE";
        public static final String ANNOUNCEMENT_UPDATE_TYPE = "ANNOUNCEMENT_UPDATE_TYPE";
        public static final String NODE_ID = "NODE";
        public static final String MIN_NEWS_DATE = "MIN_NEWS_DATE";
        public static final String MIN_ANN_DATE = "MIN_ANN_DATE";
        public static final String HEAD_TEXT = "HEAD_TEXT";
        public static final String PRICE_USERNAME = "PRICE_USERNAME";
        public static final String CUSTOM_TAG_SELECTED_SYMBOL_TYPE = "SSYMT";
        public static final String ENABLE_INDIVIDUALS = "IE";
        public static final String ENABLE_QUOTE = "QE";
        public static final String ENABLE_COUNTS = "CE";
        public static final String RESULT_COUNT = "R_COUNT";
        public static final String IS_FINANCIAL_REQUIRED = "IS_FINANCIAL_REQUIRED";
        public static final String IS_FINANCIAL_EXCEL = "IS_FINANCIAL_EXCEL";
        public static final String INDEX_SCREENER_PATH = "INDEX_SCREENER_PATH";
        public static final String SUBS_COMP_NAME = "SUBS_COMP_NAME";
        public static final String SELCTION = "SELECTION";
        public static final String SHOW_ALL = "SHOW_ALL";
        public static final String INFO_TYPE = "INFO_TYPE";
        public static final String DECIMAL_FORMATTER_3 = "%.3f";
        public static final String DECIMAL_FORMATTER_2 = "%.2f";
        public static final String TOP_STORY_ENABLED = "TOP_STORY_ENABLED";
        public static final String IS_TOP_STORY = "IS_TOP_STORY";
        public static final String IS_FREE_USER = "IS_FREE_USER";
        public static final String DELAYED_NEWS_SOURCES = "DELAYED_NEWS_SOURCES";
        /**
         * field for screener
         */
        public static final String SCREENER_VOLUME = "7";
        public static final String SCREENER_TURNOVER = "8";
        public static final String SCREENER_CHANGE = "9";
        public static final String SCREENER_PER_CHANGE = "10";
        public static final String FS_SCREENER_CODE = "scrCode";
        public static final String FS_CODE = "code";
        public static final String FS_MIN = "min";
        public static final String FS_MAX = "max";
        public static final String FS_CATEGORY = "cat";
        public static final String FS_NAM_EN = "namEN";
        public static final String FS_NAM_AR = "namAR";
        public static final String FS_DES_EN = "desEN";
        public static final String FS_DES_AR = "desAR";
        public static final String FS_CUR_COV_AR = "curCov";
        public static final String FS_FAC = "fac";
        public static final String DESC_ORDER_TS = "DEC";
        public static final String FS_DESC = "desc";
        public static final String QUERY_EXCEL_DATA = "html";
        public static final String QUERY_REQUEST_TYPE = "rt";
        public static final String QUERY_FILE_NAME = "fn";
        public static final String QUERY_HEADINGS = "hd";
        public static final String QUERY_HEAD_KEYS = "hk";
        public static final String QUERY_RESPONSE_EXCEL = "EXCEL";
        //fund positions
        public static final String INVEST_SYMBOL = "INVEST_SYMBOL";
        public static final String FUND_INVEST_DATE = "FUND_INVEST_DATE";
        public static final String FUND_STATUS = "FUND_STATUS";
        public static final String FUND_TICKER_STATUS = "FUND_TICKER_STATUS";
        public static final String OWNERSHIP_DATE = "OWNERSHIP_DATE";
        public static final String DESC = "DESC";
        //email notification data fields
        public static final String EMAIL_TITLE = "EMAIL_TITLE";
        public static final String EMAIL_CONTENT = "EMAIL_CONTENT";
        public static final String EMAIL_RECEPIENT_LIST = "EMAIL_RECEPIENT_LIST";
        public static final String EMAIL_CC_LIST = "EMAIL_CC_LIST";
        public static final String EMAIL_BCC_LIST = "EMAIL_BCC_LIST";
        public static final String EMAIL_FROM = "EMAIL_FROM";
        public static final String EMAIL_FROM_NAME = "EMAIL_FROM_NAME";
        public static final String EMAIL_ATTACHMENT = "EMAIL_ATTACHMENT";
        public static final String EMAIL_ATTACHMENT_PATH = "EMAIL_ATTACHMENT_PATH";
        public static final String EMAIL_ATTACHMENT_NAME = "EMAIL_ATTACHMENT_NAME";
        public static final String EMAIL_ATTACHMENT_CREATED_ALREADY = "EMAIL_ATTACHMENT_CREATED_ALREADY";
        public static final String EMAIL_TYPE = "EMAIL_TYPE";
        public static final String EMAIL_SOURCE = "EMAIL_SOURCE";
        public static final String EMAIL_LANGUAGE = "EMAIL_LANGUAGE";
        public static final String AST = "C_AST";
        /* fund issuer details*/
        public static final String COMPANY_ID = "COMPANY_ID";
        //fund action
        public static final String CORP_ACT_TYPE = "CORP_ACT_TYPE";
        public static final String ACTION_TYPE_NAME = "ACTION_TYPE_NAME";
        public static final String EFF_DATE = "EFF_DATE";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        //KPIs
        public static final String KPI_DEF_IDS = "KPI_DEF_IDS";
        public static final String KPI_BASE_COMPANY = "BASE_COMPANY";
        public static final String KPI_COMPANY_LIST = "COMPANY_LIST";
        public static final String KPI_COMPANY_NAME_LIST = "KPI_COMPANY_NAME_LIST";
        public static final String KPI_PERIOD_BASIS = "KPI_PERIOD_BASIS";
        public static final String KPI_FROM_PERIOD = "KPI_FROM_PERIOD";
        public static final String KPI_TO_PERIOD = "KPI_TO_PERIOD";
        public static final String KPI_COMPANY_DTO_LIST = "KPI_COMPANY_DTO_LIST";

        //Advanced Search
        public static final String CTRY_CODE = "CTRY_CODE";
        public static final String COMP_NAME = "COMP_NAME";
        public static final String COMPANY_LIST_STATUS = "COMPANY_LISTING_STATUS";
        public static final String COMPANY_TYPE_ID = "COMPANY_TYPE_ID";
        //AJAX
        public static final String URL = "url";
        public static final String DATA_REQUIRED = "DATA_REQUIRED";
        public static final String COUNT_REQUIRED = "COUNT_REQUIRED";
        public static final String UNIVERSE_TYPE = "uType";
        public static final String UNIVERSE_CODE = "uCode";
        public static final String FILTER_BY_DATE = "filterByDate";
        public static final String IS_STATUS_PAGE = "statusPage";
        public static final String STATUS_DATE = "statusDate";
        public static final String CAMPAIGN_ID = "CAMPAIGN_ID";
        //MarketAvgRatios
        public static final String RATIO_GROUP = "RATIO_GROUP";
        public static final String HISTORY_FROM_PERIOD = "HISTORY_FROM_PERIOD";
        public static final String DOMAIN = "DOMAIN";
        public static final String HISTORY_TO_PERIOD = "HISTORY_TO_PERIOD";
        public static final String TYPE = "TYPE";
        public static final String VIEW_TYPE = "VIEW_TYPE";
        public static final String PERIODB = "PERIODB";
        public static final String RATIO_GROUP_DESC = "RATIO_GROUP_DESC";
        public static final String EXCHANGE_DESC = "EXCHNAGE_DESC";
        public static final String UNIVERSE = "UNIVERSE";
        public static final String SELECTION = "SELECTION";
        public static final String RATIO_TABLE = "RatioTable";
        public static final String HEADERS = "Headers";
        public static final String COMPANY_RATIOS = "companyRatios";
        public static final String FULL_MARKET = "Full Market";
        //financialUtils
        public static final String GROUP_HEAD = "groupHead";
        public static final String HEAD_STYLE = "headStyle";
        public static final String HEAD_DATA = "headData";
        public static final String HEAD_AVG_DATA = "headAvgData";
        public static final String DATA = "data";
        public static final String HEAD = "head";
        public static final String STYLE = "style";
        public static final String AVG_DATA = "avgData";
        public static final String CALANDER_YEAR = "CALENDER_YEAR";
        public static final String CAL_QUARTER_REQUESTED = "CAL_QUARTER_REQUESTED";
        public static final String COMPANY = "COMPANY";
        public static final String TSID = "TSID";
        public static final String FS_MEAN = "mean";
        public static final String FS_MEDIAN = "median";
        public static final String FS_AGG_AVG = "agg.Avg";
        public static final String FS_GEO_AVG = "geo.Avg";
        public static final String FS_SD = "sd";
        public static final String FS_SKEW = "skew";
        public static final String OVERVIEW_TAB = "ratioOvrTab";
        public static String AVG_RATIO_NOT_AVAIL_ATTR[] = {"FR_DivYld", "FR_DPS"};
        public static final String RATIO_COLUMNS[] = {"Applicable Comp. Count", "Available Comp. Count", "Min", "Max", "Mean", "Median", "Agg.Avg.", "Geo.Avg.", "SD", "Skewness"};
        public static final String HEAD_LIST = "headers";
        public static final String GROUP = "group";
        public static final String TABLE_DATA = "tableData";
        public static final String RATIO_DATA_OBJECT = "ratioDataObject";
        public static final String HISTORY_DATA_OBJECT = "historyRatioData";
        public static final String INDVIDUAL_DATA = "indvCompRatioData";
        public static final String COMPANY_HEAD_DESC = "Company";
        public static final String CURRENCY_CONV_RATE = "CURRENCY_CONV_RATE";
        public static final String BREAK_DOWN = "brk";
        public static final String ESTIMATES = "est";
        public static final String REQUEST_PARAM_PERIODS = "periods";
        public static final String REQUEST_PARAM_CURRENCY = "currency";
        public static final String COLLAPSED  = "C";
        public static final String EXPANDED  = "E";
        public static final String FROM_PERIODS = "fp";
        public static final String TO_PERIODS = "tp";
        public static final String AS_DATE = "AS_DATE";
        public static final String FROM_DATE = "FROM_DATE";
        public static final String TO_DATE = "TO_DATE";
        public static final String PERIOD_LENGTH = "PERIOD_LENGTH";
        public static final String MONTHS = "Months";
        public static final String DEFAULT_BREAKDOWN = "E";
        public static final String BREAK_DOWN_EXPANDED = "Expanded";
        public static final String BREAK_DOWN_COLLAPSED = "Collapsed";
        public static final String EST_INCLUDED = "Included";
        public static final String EST_EXCLUDED = "Excluded";
        public static final String FY_START_MONTH = "fy";
        public static final String REQUEST_PARAM_SHOW_AS = "showAs";
        public static final String SHOW_VALUES = "values";
        public static final String SHOW_COMMON = "commonSize";
        public static final String REQUEST_PARAM_NUM_DEC = "numDec";
        public static final String REQUEST_PARAM_NUM_INC = "numInc";
        public static final String THOUSANDS = "Thousands";
        public static final String MILLIONS = "Millions";
        public static final String[] FINANCIAL_NOR_NT_REQ_ATTR = {"IS_RotBasEPS", "IS_RptDilEPS", "IS_ReptDilEPS", "IS_RprtdDilEPS", "MS_DilutedEPS", "DILUTEDEPS"};
        public static final String NULL = "null";
        public static final String GROUP_ORDER = "groupOrder";
        public static final String HEADER_LIST = "headList";
        public static final String AVG_HEAD = "avgHead";
        public static final String TTM_DATA = "ttmData";
        public static final String FISCAL = "F";
        public static final String DEFAULT_INDEX = "0";
        public static final String GROUP_STYLE = "groupStyle";
        public static final String RATIO = "RATIO";
        public static final String QUARTER_VAL = " Q:";
        public static final String MIN = "MIN";
        public static final String MAX = "MAX";
        public static final String AVERAGE = "AVERAGE";
        public static final String COMPANY_TTM = "COMPANY_TTM";
        public static final String VERTICAL_BAR = "|";
        public static final String AVG_STYLE = "avgStyle";
        public static final String[] AVG_COLUMNS = {"Market Min", "Market Average", "Market Max", "Company TTM Ratio", "Ratios TTM Benchmark"};
        public static final String[] DATE_COLUMNS = {"From Date", "To Date", "PeriodLength"};
        public static final String INTERIM_VIEW_TYPE = "C";
        public static final String TTM_VIEW = "T";
        public static final String[] MONTHS_ARRAY = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        public static final String HISTORY = "H";

        // Benchmark ratio page
        public static final String DOMAIN_DESC = "DOMAIN_DESC";
        public static final double DEFAULT_VALUE = 0.0;
        public static final String DEFAULT_HISTORY_FROM = "2005-4";
        public static final String DEFAULT_LEVELS = "5";
        public static final String DEFAULT_PERIOD_BASIS = "CAL_PERIOD_BASIS";
        public static final String FINANCIAL_SNAPSHOT = "snp";
        public static final String FINANCIAL_SNAPSHOT_FIELDS = "IS,BS,CF,FR,MR";
        public static final String FINANCIAL_RATIO_GROUP = "FR,MR";
        public static final String YEARLY_VIEW_TYPE = "A";
        public static final String TTM_PERIOD_PREFIX = "2";
        public static final String MARKET_RATIOS = "MR";
        public static final String FINANCIAL_RATIOS = "FR";
        public static final String TTM_VIEW_TYPE = "vt";
        public static final String CAL_PERIOD_BASIS = "c";
        public static final String CAL_FISCAL = "F";
        public static final String TEMPLATE_ID = "tid";
        public static final String REQUEST_PARM_CAL = "cal";
        public static final String REQUEST_PARM_VIEW_TYPE = "vt";
        public static final String REQUEST_PARM_CURRENCY = "currency";
        public static final String REQUEST_PARM_FINANCIAL_TYPE = "ft";
        public static final String IS_NET_INC = "IS_NetInc";
        public static final String IS_TOT_RVN = "IS_TotRvn";
        public static final String IS_OPR_INC_BNK_RV = "IS_OprIncBnkrv";
        public static final String IS_TOT_UWGRV = "IS_TotUWGRv";
        public static final String IS_NOI = "IS_NOI";
        public static final String BS_TOT_AST = "BS_TotAst";
        public static final String BS_TOEAMINT = "BS_TOEAMINT";
        public static final String CF_CSH_OPR_ACV = "CF_CshOprAcv";
        public static final String CF_CSH_INV_ACV = "CF_CshInvAcv";
        public static final String DAILY_RATIO_ITEMS[] =  {"FR_PER", "FR_PBR", "FR_PSR", "FR_PCFR", "FR_DivYld", "FR_EV_EBITDA", "FR_EV_EBIT", "FR_EV_REV"};
        public static final String DAILY_RATIO_ITEMS_FOR_BANKS_INSURANCE[] = {"FR_PER", "FR_PBR", "FR_DivYld"};
        public static final String VIEW_TYPE_YEARLY = "Yearly";
        public static final String VIEW_TYPE_TTM = "TTM";
        public static final String VIEW_TYPE_INTERIM = "Interim";
        public static final String VIEW_TYPE_QUARTERLY = "Quarterly";
        public static final String PERIOD_BASIS_CALENDER = "Calender";
        public static final String PERIOD_BASIS_FISCAL = "Fiscal";
        public static final String FINANCIAL_REQUEST = "fr";
        public static final String FINANCIAL_LATEST_REQUEST = "flr";
        public static final String FINANCIAL_STATEMENTS = "fs";
        public static final String REQUEST_PARM_DEF_CURRENCY = "defCurrency";
        public static final String REQUEST_PARAM_FY_START = "fy";
        public static final String REQUEST_PARAM_RATIO_BASIS = "rb";
        public static final String REQUEST_PARAM_COMPANY_ID = "cid";
        public static final String REQUEST_PARAM_ESTIMATES = "est";
        public static final String REQUEST_PARAM_DOM = "dom";
        public static final String ESTIMATE_REQUEST = "fsd";

        //estimateUtils
        public static final String CAT_PERIODS = "categorizedPeriods";
        public static final String FIN_HEADERS = "financialHeaders";
        public static final String FIN_STYLES = "financialStyles";
        public static final String FIN_ESTIMATES = "financialEstimates";
        public static final String FIN_DETAIL_ESTIMATES = "financialDetailEstimates";
        public static final String PROV_DATE = "PROVIDED_DATE";
        public static final String COMP = "COMP";
        public static final String EST = "E";
        public static final String EXCHANGE_RATES = "exchangeRates";
        public static final String LAST_UPDATED_PERIODS = "period";

        //menu
        public static final String GROUPING_FIELD = "GROUPING_FIELD";
        public static final String GROUP_ORDER_FIELD = "GROUP_ORDER_FIELD";
        public static final String FILTER_CATEGORIES = "FILTER_CATEGORIES";
        public static final String GROUP_ENTITIES = "GROUP_ENTITIES";

        private CustomDataField() {
        }
    }

    final class SocketServiceTags {
        public static final String SEQ = "SEQ";
        public static final String RES = "RES";
        public static final String REQ = "REQ";
        public static final String VER = "VER";

        private SocketServiceTags() {
        }
    }

    /**
     * Keep Specifically used fair values tags
     */
    final class FairValuesTags {
        public static final String COMPANY_ID = "COMPANY_ID";

        private FairValuesTags() {
        }
    }

    /* filter types to be sent to Data Managers from Controllers*/
    final class FilterTypes {
        public static final String REGION = "REGION";
        public static final String COUNTRY = "COUNTRY";

        private FilterTypes() {
        }
    }

    public static final class CalendarEventCategoryFields {
        public static final String CATEGORY_ID = "MAIN_GROUP_ID";
        public static final String CATEGORY_DESC = "SHRT_DSC";

        private CalendarEventCategoryFields() {
        }
    }

    /*  DFN Sector types Maste. Values directly related to column Sector.Sector_Type*/
    public static final class SectorTypes {
        public static final String GICS1 = "GICSL1";
        public static final String GICS2 = "GICSL2";
        public static final String GICS3 = "GICSL3";
        public static final String GICS4 = "GICSL4";

        private SectorTypes() {
        }

        public static String valueOf(int code) {
            switch (code) {
                case 1:
                    return GICS1;
                case 2:
                    return GICS2;
                case 3:
                    return GICS3;
                case 4:
                    return GICS4;
                default:
                    return "";
            }
        }
    }

    /**
     * Defines the control path types for news requests
     */
    public static final class ControlPathTypes {
        public static final int INDIVIDUAL_SNAPSHOT = 1;
        public static final int NEWS_SEARCH = 2;
        public static final int EQUITY_TICKER_SEARCH = 3;
        public static final int EQUITY_SNAPSHOT_SEARCH = 4;
        public static final int FUND_TICKER_SEARCH = 5;
        public static final int COMPANY_SEARCH = 6;
        public static final int MARKET_DATA = 7;
        public static final int INDIVIDUAL = 8;
        public static final int INDIVIDUAL_LIST = 9;
        public static final int NEWS_SEARCH_MAX_SEQ_ID = 10;
        public static final int NEWS_UPDATE = 11;
        public static final int INDIVIDUAL_SEARCH = 12;
        public static final int INDIVIDUAL_PROFILE = 13;
        public static final int INDIVIDUAL_OWNSHP_HISTRY = 14;
        public static final int BOND_PROFILE = 15;
        public static final int COMPANY_OWNSHP_HISTRY = 15;
        public static final int OWNSHP = 16;
        public static final int FIXED_INCOME_TICKER_SEARCH = 17;
        public static final int ALL_NEWS_SECTIONS = 18;
        public static final int TICKER_SEARCH = 19;
        public static final int MACRO_ECONOMY = 20;
        public static final int COUNTRY_PROFILE = 21;
        public static final int FIXED_INCOME_TICKER_TYPES_COUNT = 22;
        public static final int EQUITY_TICKER_SEARCH_BY_LITS = 23;
        public static final int USER_PROFILE_GET = 24;
        public static final int USER_PROFILE_SET = 25;
        public static final int RELATED_INSTRUMENT_SEARCH = 26;
        public static final int COUNTRY_TICKER = 27;
        public static final int COUNTRY_TICKER_SNAPSHOT = 28;
        public static final int COMPANIES_LISTING = 29;
        public static final int CITY_LIST = 30;
        public static final int MEDIA_LIST = 31;
        public static final int MEDIA_VIEW = 32;
        public static final int MEDIA_RELATED = 33;
        public static final int INDEX_SEARCH = 34;
        public static final int INDIVIDUAL_LAST_UPDATED_DATA = 35;
        public static final int USER_PREFERENCES_GET = 36;
        public static final int TICKER_MAP = 38;
        public static final int COMPANY_DATA_MAP = 39;
        public static final int APP_SUPPORTED_MARKET_DATA = 40;
        public static final int INDEX_SNAPSHOT = 41;
        public static final int USER_PROFILE_DATA_GET = 42;
        public static final int USER_DATA_GET_ALL = 43;
        public static final int USER_PREFERENCES_UPDATE = 44;
        public static final int USER_CREATE_USER = 45;
        public static final int USER_PROFILE_UPDATE = 46;
        public static final int USER_AUTHENTICATION = 47;
        public static final int USER_UPDATE = 48;
        public static final int USER_MIX_AUTHENTICATION = 49;
        public static final int USER_MIX_GET_USER_DATA = 50;
        public static final int USER_GET_USER_DETAILS = 51;
        public static final int TICKER_SEARCH_BY_LIST = 52;
        public static final int USER_CHANGE_PASSWORD = 53;
        public static final int TICKER_SERIALS_MAP = 54;
        public static final int PRICE_CART_HISTORY = 55;
        public static final int PRICE_CHART_INTRADAY = 56;
        public static final int USER_UPDATE_LAST_LOGIN = 57;
        public static final int IPO = 58;
        public static final int FUND_PROFILE = 59;
        public static final int FUND_POSITION = 60;
        public static final int FUND_PROFILE_GET = 61;
        public static final int FUND_MANAGERS_GET = 62;
        public static final int FUND_COMPANY_DATA = 63;
        public static final int FUND_TICKER_LIST_SEARCH = 64;
        public static final int UPDATE_CALENDAR_EVENT_CATEGORY = 65;
        public static final int MEDIA_PROVIDER = 66;
        public static final int DELETE_CALENDAR_EVENT_CATEGORY = 67;
        public static final int IPO_DATA = 68;
        public static final int FIXED_INCOME_TICKER_OUTSTANDING_COUNT = 69;
        public static final int FUND_ISSUERS_GET = 70;
        public static final int MARKET_HIGHLIGHTS = 71;
        public static final int MARKET_52H_52L_CHART = 72;
        public static final int TICKER_DTO_MAP = 73;
        public static final int FUND_ISSUER_DETAILS = 74;
        public static final int WEB_CACHE_GET = 75;
        public static final int FUND_SCREENER_META = 76; //get managers, issuers
        public static final int FUND_BENCHMARKS = 77;
        public static final int FUND_MANAGER_DETAILS = 78;
        public static final int IPO_LIST = 79;
        public static final int MA_DATA = 80;
        public static final int INVESTOR_TYPES_VALUES = 81;
        public static final int INVESTOR_TYPES = 82;
        public static final int INVESTOR_NATIONALITIES = 83;
        //announcement control paths
        public static final int ANNOUNCEMENT_UPDATE = 84;
        public static final int ANNOUNCE_SEARCH_MAX_SEQ_ID = 85;
        public static final int ANNOUNCEMENT_SEARCH = 86;
        public static final int COMPANY_KPI = 87;
        public static final int COMPANY_LIST_KPI = 88;
        public static final int RELATED_KPI = 89;
        public static final int COMPANY_PROFILE_DATA = 90;
        public static final int EQUITY_TICKER_DTO = 91;
        public static final int MA_LIST = 92;
        public static final int MERGE_AND_ACQUISITION_DETAILS = 93;
        public static final int INDIVIDUAL_PROFILES_MAP = 94;
        public static final int GET_FUND_INVESTMENT_ALLOCATIONS = 95;
        public static final int GET_RESULT_COUNT = 96;
        public static final int NEWS_EOD_IMDB_DELETE = 98;
        public static final int NEWS_SEARCH_HIBERNATE = 99;
        public static final int GET_MAX_NEWS_SEQ_ID_RDBMS = 100;
        public static final int GET_MAX_ANN_SEQ_ID_RDBMS = 101;
        public static final int ANNOUNCEMENT_SEARCH_EOB_UPDATE = 102;
        public static final int ANNOUNCEMENT_EOD_IMDB_DELETE = 103;
        public static final int NEWS_IMDB_DELETE_ON_UPDATE = 104;
        public static final int NEWS_JDBC_DELETE_ON_UPDATE = 105;
        public static final int GET_LAST_SYNC_DATE = 106;
        public static final int REPORT_DATA = 107;
        public static final int DOC_FILES_DATA = 108;
        public static final int FILE_LIST = 109;
        public static final int COMPANY_SCREENER = 110;
        public static final int RELATED_INSTRUMENT_SUBSIDIARY_DATA = 111;
        public static final int TICKER_SEARCH_BY_TICKER_ID = 112;
        public static final int ANNOUNCEMENT_SEARCH_RDBMS = 113;
        public static final int ALL_NEWS_SECTIONS_ORACLE = 114;
        public static final int GET_FUND_INVESTMENT_ALLOCATIONS_FOR_STOCK_BY_MARKET = 115;
        public static final int AJUSTED_PRICE_SNAPSHOT_SEARCH = 116;
        public static final int NEWS_SEARCH_MAX_NEWS_DATE = 117;
        public static final int NEWS_UPDATE_ALL = 118;
        public static final int INVESTOR_UPDATE_FREQ = 119;
        public static final int FUND_BENCHMARK_MAP = 120;
        public static final int FUND_ELIGIBILITY = 121;
        public static final int FUND_NAV_FREQ = 122;
        public static final int SEARCH_MAX_ANNOUNCEMENT_DATE = 120;
        public static final int ANNOUNCEMENT_UPDATE_ALL = 121;
        public static final int COUNTRY_INDICATOR_SEARCH = 122;
        public static final int MACRO_ECONOMY_MIX_DATA = 123;
        public static final int FUND_RATIOS = 123;
        public static final int BENCHMARKS_FOR_FUNDS = 124;
        public static final int RISK_FREE_RATE = 125;
        public static final int SOURCE_FOR_FUND_RATIO = 126;
        public static final int TICKERS_FOR_FUND_RATIO = 127;
        public static final int GET_CURRENT_FUND_INVESTMENT_ALLOCATIONS = 128;
        public static final int GET_TICKERS = 129;
        public static final int GET_COMPANIES = 130;
        public static final int GET_EQUITY_TICKERS = 131;
        public static final int GET_FUND_TICKERS = 132;
        public static final int GET_FIXED_INCOME_TICKERS = 133;
        public static final int DOC_FILES_DATA_FUND_COMP = 134;
        public static final int ANNOUNCEMENT_SEARCH_BY_ANN_ID = 135;
        public static final int ANNOUNCEMENT_INSERT_UPDATE_ALL = 136;
        public static final int NEWS_SEARCH_BY_NEWS_ID = 137;
        public static final int NEWS_INSERT_UPDATE_ALL = 138;
        public static final int FUND_RATIOS_COMPARISON = 139;
        public static final int GET_LISTED_UNLISTED_TICKERS_BY_COMPANY_ID = 140;
        public static final int GET_TICKERS_BY_COMPANY_ID_FOR_NEWS = 141;
        public static final int GET_TICKERS_BY_TICKER_SERIAL_FOR_NEWS = 142;
        public static final int FUND_POSITION_TABLE = 143;
        public static final int GET_EQUITY_TICKERS_BY_SOURCE_TICKER = 144;
        public static final int GET_TICKER_SERIAL_SET = 145;
        public static final int INDEX_DATA_SEARCH = 146;
        public static final int INDEX_PROVIDERS = 147;
        public static final int INDEX_MAP = 148;
        public static final int INDEX_DTO_SEARCH = 149;
        public static final int MULTIPLE_PRICE_HISTORY = 150;
        public static final int INDIVIDUAL_PROFILE_SERACH = 151;
        public static final int COMPANY_DATA_COUNT = 152;
        public static final int INDIVIDUAL_DATA_BY_COUNTRY = 153;
        public static final int CAMPAIGN_REPORT_DATA = 154;
        public static final int GET_EQUITY_SNAPSHOT_BY_COMPANY = 155;
        public static final int GET_FILTERED_COMPANIES_FOR_KPI = 156;
        public static final int LATEST_INDIVIDUAL_PROFILES = 157;
        public static final int GET_REGION_COVERAGE = 158;
        public static final int GET_COMPANY_AGGREGATES = 159;
        public static final int GET_COMPANY_SIZE_MCAP = 160;
        public static final int GET_ALL_TICKER_CURRENCY = 161;
        public static final int GET_ALL_UNSUBSCRIBED_TICKERS = 162;
        public static final int GET_TICKERS_LIST_BY_COMPANY_ID = 163;
        public static final int NUMBER_OF_EMPLOYEES_BY_COMPANY_ID = 164;
        public static final int DOC_FILES_DATA_MAP = 165;

        private ControlPathTypes() {
        }
    }

    public static final class RequestTrial {

        public static final String PROMOCODE = "promoCode";
        public static final String EMAIL = "Email";
        public static final String COMMENT = "Description";
        public static final String FIRST_NAME = "First Name";
        public static final String LAST_NAME = "Last Name";
        public static final String PHONE = "Phone";
        public static final String COUNTRY = "Country";
        public static final String COMPANY = "Company";
        public static final String TITLE = "Designation";
        public static final String DESIGNATION = "Industry";

        private RequestTrial() {
        }

    }

    public static final class UserPreferences {

        //Create user
        public static final String USER_NAME = "USER_NAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String PLAIN_PASSWORD = "PLAIN_PASSWORD";
        public static final String TITLE = "TITLE";
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String EMAIL = "EMAIL";
        public static final String PHONE = "PHONE";
        public static final String WORK_TEL = "WORK_TEL";
        public static final String COUNTRY = "COUNTRY";
        public static final String COUNTRY_ID = "COUNTRY_ID";
        public static final String COMPANY = "COMPANY";
        public static final String CONTEXT = "CONTEXT";
        public static final String USER_ROLES = "USER_ROLES";
        public static final String STATUS = "USER_STATUS";
        public static final String SALES_REP = "SALES_REP";
        public static final String EXP_DATE = "EXP_DATE";
        public static final String CREATE_DATE = "CREATE_DATE";
        public static final String RELEASE_NOTE_EMAIL_NOTIFICATION = "RELEASE_NOTE_EMAIL_NOTIFICATION";
        public static final String RELEASE_NOTE_NOTIFY_AFTER_LOGIN = "RELEASE_NOTE_NOTIFY_AFTER_LOGIN";
        public static final String RELEASE_NOTE_SHOW_LATEST = "RELEASE_NOTE_SHOW_LATEST";
        public static final String FINANCIAL_PERIOD_BASIS = "FINANCIAL_PERIOD_BASIS";
        public static final String FINANCIAL_DEFAULT_TAB = "FINANCIAL_DEFAULT_TAB";
        public static final String FINANCIAL_DEFAULT_VIEW = "FINANCIAL_DEFAULT_VIEW";
        public static final String FINANCIAL_BREAKDOWN = "FINANCIAL_BREAKDOWN";
        public static final String FUTURE_ESTIMATES = "FUTURE_ESTIMATES";
        public static final String SERVICES = "SERVICES";
        public static final String PREMIUM_ACCT_ID = "PREMIUM_ACCOUNT_ID";
        public static final String USER_ID = "USER_ID";
        public static final String USER_KEY = "USER_KEY";
        public static final String SESSION_ID = "SESSION_ID";
        public static final String PRICE_LOGIN_ID = "PRICE_LOGIN_ID";
        public static final String PRICE_PROFILE_ID = "PRICE_PROFILE_ID";
        public static final String IS_DELAYED = "IS_DELAYED";
        public static final String UPDATED_BY = "UPDATED_BY";
        public static final String DESIGNATION = "DESIGNATION";
        public static final String CITY = "CITY";
        public static final String ADDRESS_1 = "ADDRESS_1";
        public static final String ADDRESS_2 = "ADDRESS_2";
        public static final String SUBSCRIPTION_STATUS = "SUBSCRIPTION_STATUS";
        public static final String PRICE_USER = "PRICE_USER";

        //preferences
        public static final String ACTIVE_EDITION = "ACTIVE_EDITION";
        public static final String EXPIRY_DATE = "EXPIRY_DATE";
        public static final String PRICE_PACKAGE = "PRICE_PACKAGE";
        public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
        public static final String BASE_COUNTRY = "BASE_COUNTRY";
        public static final String LAST_LOGIN = "LAST_LOGIN";
        public static final String LAST_LOGIN_IP = "LAST_LOGIN_IP";
        public static final String DEFAULT_TIME_ZONE = "TIME_ZONE";
        public static final String DEFAULT_CURRENCY = "DEFAULT_CURRENCY";
        public static final String DEFAULT_MARKET = "MARKET";
        public static final String DEFAULT_COMPANY = "COMPANY";
        public static final String DEFAULT_COUNTRY = "COUNTRY";
        public static final String DEFAULT_REGION = "REGION";
        public static final String EDITION_CONTROL_TYPE = "EDITION_CONTROL_TYPE";
        public static final String NEWS_READ_LANGUAGES = "NEWS_READ_LANGUAGES";
        public static final String NEWS_READ_LAN_PREFERENCE = "NEWS_READ_LANG_PREFERENCE";
        public static final String NEWS_HEADLINE_LANGUAGES = "NEWS_HEADLINE_LANGUAGES";
        public static final String SAVE_NEWS_STREAM_SETTINGS = "SAVE_NEWS_STREAM_SETTINGS";
        public static final String PREFER_FILES_LANGUAGE = "PREFER_FILES_LANGUAGE";
        public static final String PROFILE_NAME = "PROFILE_NAME";
        public static final String PROFILE_COMPANY = "PROFILE_COMPANY";
        public static final String PROFILE_EMAIL = "PROFILE_EMAIL";
        public static final String PROFILE_MOBILE_NO = "PROFILE_MOBILE_NO";
        public static final String MARKETS_IN_TIMELINE = "MARKETS_IN_TIMELINE";
        public static final String MARKETS_IN_ACTIVE_PANEL = "MARKETS_IN_ACTIVE_PANEL";
        public static final String COMMODITIES = "COMMODITIES";
        public static final String CURRENCIES = "CURRENCIES";
        public static final String ENABLE_EMAIL_NOTIFICATION = "ENABLE_EMAIL_NOTIFICATION";
        public static final String EMAIL_NOTIFICATION_ASSETS = "EMAIL_NOTIFICATION_ASSETS";
        public static final String NEWS_STREAM_CRITERIA = "NEWS_STREAM_CRITERIA";
        public static final String OLD_PASSWORD = "OLD_PASSWORD";
        public static final String NEW_PASSWORD = "NEW_PASSWORD";
        public static final int INDIVIDUAL_LAST_UPDATED_DATA = 35;
        public static final String USER_ACTIVATION_CODE = "USER_ACTIVATION_CODE";
        public static final String USER_ID_BO = "userID";
        public static final String ACT_CODE_BO = "actCode";
        public static final String STATUS_BO = "status";
        public static final String PRE_USERNAME_BO = "username";
        public static final String PRE_EMAIL_BO = "email";
        public static final String PRE_ACCOUNT_TYPE_BO = "accountType";
        public static final String SCREENER_SETTINGS = "SCREENER_SETTINGS";
        public static final String WATCH_LIST_SETTINGS = "WATCH_LIST_SETTINGS";
        public static final String NEWS_STREAM_SETTINGS = "NEWS_STREAM_SETTINGS";
        public static final String STOCK_CHART_TYPE = "STOCK_CHART_TYPE";

        private UserPreferences() {
        }


    }

    public static final class ScreenerOperators {
        public static final String OPTN = "OPTN";
        public static final String GRP = "GRP";
        public static final String MIN_KEY = "MIN";
        public static final String MAX_KEY = "MAX";
        public static final String MIN_OPERATOR = "MNO";
        public static final String MAX_OPERATOR = "MXO";
        public static final String AND_CONNECT_OPERATOR = "CO:0";
        public static final String OR_CONNECT_OPERATOR = "CO:1";
        public static final String NO_CONNECT_OPERATOR = "CO:";
        public static final String AND_VALUE = "0";
        public static final String OR_VALUE = "1";
        public static final String CONNECT_OPERATOR = "CO";
        public static final String EQUALS = MIXConstants.EQUAL;
        public static final String LESS_THAN = MIXConstants.LESS_THAN;
        public static final String LESS_EQUAL = MIXConstants.LESS_THAN_OR_EQUAL;
        public static final String GREATER = MIXConstants.GREATER_THAN;
        public static final String GREATER_EQUAL = MIXConstants.GREATER_THAN_OR_EQUAL;

        private ScreenerOperators() {
        }

    }

    public static final class ScreenerFields {
        public static final String SCREENER_REGION_ID = "1372";
        public static final String SCREENER_COUNTRY_ID = "1254";
        public static final String SCREENER_GICS_L4 = "1371";
        public static final String SCREENER_GICS_L3 = "1370";
        public static final String SCREENER_GICS_L2 = "1369";
        public static final String SCREENER_GICS_L1 = "1368";
        public static final String SCREENER_MARKET_CAP = "1198";
        public static final String SCREENER_TURN_RATIO = "1378";
        public static final String SCREENER_D30_AVG_TURN = "1379";
        public static final String SCREENER_SYM = "1101";
        public static final String SCREENER_EXG = "1505";
        public static final String SCREENER_EXG_CODE = "1100";
        public static final String SCREENER_TRADING_CUR = "1115";
        public static final String SCREENER_INDEX = "1328";
        public static final String SCREENER_FIN_YEAR = "1380";
        public static final String SCREENER_FIN_PERIOD_ID = "1381";
        public static final String SCREENER_FIN_CAL_TYPE = "1382";
        public static final String SCREENER_FIN_STAT_TYPE = "1383";
        public static final String SCREENER_FIN_INFO_TYPE = "1384";
        public static final String SCREENER_FIN_BASE_CUR = "1386";
        public static final String SCREENER_TICKER_SERIAL = "1513";
        public static final String SCREENER_BS_CUR_RATE = "1385";
        public static final String SCREENER_LISTING_STATUS = "1397";
        public static final String SCREENER_USD_TODAY_CURRENCY_RATE = "1404";
        public static final String SCREENER_CF_CUR_RATE = "1405";

        private ScreenerFields() {
        }

    }

    public static final class ScreenerPeriodBasis {
        public static final String LATEST = "3";
        public static final String FISCAL = "1";
        public static final String CALENDAR = "2";
        public static final String IGNORE = "0";

        private ScreenerPeriodBasis() {
        }
    }

    /**
     * Defines the constants, represents the sort order of news
     */
    public static final class NewsSortType {
        /**
         * News Priority
         */
        public static final int NEWS_SORT_DATE_DESC_ORDER = 1; //ORDER BY SEQ_ID DESC
        public static final int NEWS_SORT_PRIORITY_DATE_DESC_ORDER = 2; //HOT_NEWS_INDICATOR IN (1,2) ORDER BY SEQ_ID DESC
        public static final int NEWS_SORT_FIRST_PRIORITY_DATE_DESC_ORDER = 3; //HOT_NEWS_INDICATOR=1 ORDER BY SEQ_ID DESC

        private NewsSortType() {
        }

    }

    public static final class DataUpdateConstant {
        public static final String UPDATE_CACHE_SEQUENCE = "UPDATE_CACHE_SEQUENCE";
        public static final String UPDATE_DB_SEQUENCE = "UPDATE_DB_SEQUENCE";
        public static final String UPDATE_IMDB_SQEURNCE = "UPDATE_IMDB_SQEURNCE";
        public static final String DB_UPDATE_TYPE_NEWS = "DB_UPDATE_TYPE_NEWS";
        public static final String DB_UPDATE_TYPE_NEWS_PREVIOUS_DATA = "DB_UPDATE_TYPE_NEWS_PREVIOUS_DATA";
        public static final String DB_UPDATE_TYPE_NEWS_DELETE = "DB_UPDATE_TYPE_NEWS_DELETE";
        public static final String DB_UPDATE_TYPE_ANNOUNCEMENT = "DB_UPDATE_TYPE_ANNOUNCEMENT";
        public static final String DB_INSERT_UPDATE_NEWS_HISTORY = "DB_INSERT_UPDATE_NEWS_HISTORY";
        /**
         * You can manually update history news using admin console. This will insert missing news fro give time period and update rest
         */
        public static final int INSERT_UPDATE_ORACLE_NEWS_HISTORY_MANUAL = 1;

        //-----------------------NEWS----------------------------------------//
        /**
         * Quartz scheduler is triggering this type
         */
        public static final int INSERT_UPDATE_IMDB_NEWS_JOB = 3;
        /**
         * Keep limited number of news in IMDB and move rest to Oracle.
         * This is done once a day
         */
        public static final int DELETE_IMDB_NEWS_EOD = 4;
        /**
         * You can manually update history ANNOUNCEMENT using admin console. This will insert missing ANNOUNCEMENT from
         * given time period and update rest
         */
        public static final int INSERT_UPDATE_IMDB_ANNOUNCEMENT_HISTORY_MANUAL = 5;


        //-----------------------ANNOUNCEMENT------------------------------------//
        /**
         * You can manually update Oracle Announcement table. data is taken from MIX server. If you use this duplicate Announcement can be there in both IMDB and Oracle
         * warning : Do not use this
         */
        public static final int INSERT_UPDATE_ORACLE_ANNOUNCEMENT_HISTORY_MANUAL = 6;
        /**
         * Quartz scheduler is triggering this type
         */
        public static final int INSERT_UPDATE_IMDB_ANNOUNCEMENT_JOB = 7;
        /**
         * Keep limited number of Announcement in IMDB and move rest to Oracle.
         * This is done once a day
         */
        public static final int INSERT_UPDATE_ANNOUNCEMENT_EOD_IMDB_TO_ORACLE = 8;
        /**
         * Get latest archived top news from NEWS_TOP_STORIES History table, and update  News table (IMDB or Oracle DB).
         */
        public static final int TOP_NEWS_HISTORY_UPDATE = 9;
        /**
         * Get latest archived top news from Production table and Update NEWS_TOP_STORIES
         */
        public static final int TOP_NEWS_HISTORY_DB_UPDATE = 10;
        /**
         * Quartz scheduler is triggering this type to update ORACLE
         * fetch data from mix & update
         */
        public static final int JOB_UPDATE_ANNOUNCEMENTS_FROM_MIX = 10;
        /**
         * Quartz scheduler is triggering this type to update IMDB
         * fetch from oracle & update
         */
        public static final int JOB_UPDATE_ANNOUNCEMENTS_FROM_ORACLE = 11;
        /**
         * Keep limited number of Announcement in IMDB and move rest to Oracle.
         * This is done once a day
         */
        public static final int JOB_ANNOUNCEMENT_EOD = 12;
        /**
         * Keep limited number of Announcement in IMDB and move rest to Oracle.
         * This is done once a day
         */
        public static final int MANUAL_UPDATE_IMDB_ANNOUNCEMENTS_FROM_ORACLE = 13;
        /**
         * You can manually update history ANNOUNCEMENT using admin console. This will insert missing ANNOUNCEMENT from
         * given time period and update rest
         */
        public static final int MANUAL_UPDATE_ORACLE_ANNOUNCEMENTS_FROM_CENTRAL_DB = 14;
        public static final int INSERT_DELETE_IMDB_NEWS_FROM_ORACLE = 15;
        public static final int INSERT_UPDATE_ORACLE_NEWS = 16;

        private DataUpdateConstant() {

        }

    }

    /*Meta data related request types should be defined here*/
    public static final class NewsConstants {
        public static final int DEFAULT_NEWS_COUNT = 12;
        public static final int FOUR_HOURS = 4 * 60 * 60 * 1000;
        public static final int THREE_HOURS = 3 * 60 * 60 * 1000;
        public static final int TWLEVE_HOURS = 12 * 60 * 60 * 1000;
        public static final int NEWS_PROFILE_COMPANY = 10;
        public static final int NEWS_PROFILE_SYMBOL_EXCHANGE = 11;
        public static final int NEWS_PROFILE_INDIVIDUAL = 13;
        public static final int NEWS_PROFILE_MARKET = 14;
        public static final int NEWS_PROFILE_INDUSTRY = 15;
        public static final int NEWS_PROFILE_COUNTRY = 16;
        public static final int TIME_TO_LIVE = 60;
        public static final int NEWS_HOME_EQUITY_LIST_COUNT = 15;
        public static final int NEWS_HOME_SECTION_DETAIL_COUNT = 1;

        private NewsConstants() {
        }
    }

    /*financial period constants*/
    public static final class FinancialPeriodConstants {
        public static final String ANNUAL = "A";
        public static final String QUARTERLY = "Q";
        public static final String INTERIM = "C";
        public static final String TTM = "T";
        public static final String ANNUALLY = "5";
        public static final String Q_QUARTER_1 = "1";
        public static final String Q_QUARTER_2 = "12";
        public static final String Q_QUARTER_3 = "13";
        public static final String Q_QUARTER_4 = "14";
        public static final String I_QUARTER_1 = "1";
        public static final String I_QUARTER_2 = "2";
        public static final String I_QUARTER_3 = "3";
        public static final String I_QUARTER_4 = "5";
        public static final String T_QUARTER_1 = "21";
        public static final String T_QUARTER_2 = "22";
        public static final String T_QUARTER_3 = "23";
        public static final String T_QUARTER_4 = "24";

        private FinancialPeriodConstants() {
        }
    }

    /*Market Screener Data related request types should be defined here*/
    public static final class MarketScreener {
        public static final int MARKET_SECREENER_SYMBOL_DATA_REQUEST = 57;

        private MarketScreener() {
        }
    }

    public static final class CompanyListingType {
        public static final int LISTED = 1;
        public static final int MULTY_LISTED = 2;
        public static final int UNLISTED = 3;
        public static final int DELISTED = 4;

        private CompanyListingType() {
        }

    }

    public static final class HomePageDataTypes {
        public static final int HOME_TOPSTOCK = 1;
        public static final int HOME_ACTIVE_MARKET = 2;
        public static final int HOME_CMMODITIES = 3;
        public static final int HOME_CURRENCIES = 4;

        private HomePageDataTypes() {
        }

    }

    /*Fixed Income types should be defined here*/
    public static final class FixedIncomeCommonTypes {
        private FixedIncomeCommonTypes() {
        }

        /**
         * Get coupon period in Months
         *
         * @param freq coupon frequency ID
         * @return int coupon period in Months
         */
        public static Integer getFixedIncomeCouponPeriod(int freq) {
            switch (freq) {
                case 1:
                    return 12;
                case 2:
                    return 6;
                case 3:
                    return 3;
                case 4:
                    return 1;
                case 5:
                    return -1;
                default:
                    return 0;
            }
        }
    }

    /**
     * Data synchronization types
     */
    public static final class DataSyncType {
        public static final int DB_UPDATE = 1;
        public static final int IMDB_UPDATE = 2;
        public static final int NOTIFICATION = 3;
        public static final int IMAGE_DOWNLOAD = 4;

        private DataSyncType() {
        }

    }

    //Company screener
    public static final class CompanyScreener {
        public static final String COMP_NAME = "COMP_NAME";
        public static final String COUNTRY = "COUNTRY";
        public static final String CTRY_CODE = "CTRY_CODE";
        public static final String COM_ID = "COMPANY_ID";
        public static final String GICS_L3_DESC = "GICS_L3_DESC";
        public static final String CITY = "CITY";
        public static final String ORDINARY = "ORDINARY";
        public static final String COMPANY_TYPE = "COMPANY_TYPE";
        public static final String WEB = "WEB";
        public static final String SOURCE_ID = "SOURCE_ID";
        public static final String LISTING_STATUS = "LISTING_STATUS";
        public static final String TICKER_SERIAL = "TICKER_SERIAL";
        public static final String TICKER_ID = "TICKER_ID";

        private CompanyScreener() {
        }
    }

    // financial segmentation screener
    public static final class FinancialSegmentScreener {
        public static final String FIELD_LIST = "FIELD_LIST";
        public static final String BUSINESS_ACTIVITY_TYPE = "BUSINESS_ACTIVITY_TYPE";
        public static final String BUSINESS_ACTIVITY_LIST = "BUSINESS_ACTIVITY_LIST";
        public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
        public static final String PRODUCT_LIST = "PRODUCT_LIST";
        public static final String COUNTRY_LIST = "COUNTRY_LIST";
        public static final String PERIOD_TYPE = "PERIOD_TYPE";
        public static final String YEAR = "YEAR";
        public static final String PERIOD = "PERIOD";
        public static final String SORT_FIELD = "SORT_FIELD";
        public static final String SORT_ORDER = "SORT_ORDER";
        public static final String PAGE_SIZE = "PAGE_SIZE";
        public static final String PAGE_INDEX = "PAGE_INDEX";
        public static final String SEGMENT_ID = "SEGMENT_ID";

        private FinancialSegmentScreener() {

        }
    }

    public static final class NumberFormats {
        public static final String THOUSAND_SEP_AND_TWO_DECIMAL_POINTS = "###,###,###.##";
        public static final String THOUSAND_SEP_AND_THREE_DECIMAL_POINTS = "###,###,###.###";
        public static final String THOUSAND_SEP_AND_FOUR_DECIMAL_POINTS = "###,###,###.####";
        public static final String THOUSAND_SEP_AND_TWO_NON_ABSENT_DECIMAL_POINTS = "###,###,###.00";

        private NumberFormats() {

        }
    }

    public static final class DateFormats {
        public static final String FORMAT1 = "dd-MMM-yyyy";
        public static final String FORMAT2 = "yyyyMMdd";
        public static final String FORMAT3 = "yyyy-MM-dd";
        public static final String FORMAT4 = "MM/dd/yyyy";
        public static final String FORMAT5 = "yyyy-MM-dd hh:mm:ss.sss";
        public static final String FORMAT6 = "yyyy/MM/dd";
        public static final String FORMAT7 = "yyyyMMddhhmm";
        public static final String FORMAT8 = "dd/MM - hh:mm";
        public static final String FORMAT9 = "yyyy-MM-dd hh24:mi:ss.ff";
        public static final String FORMAT10 = "dd/MM/yyyy";
        public static final String FORMAT11 = "dd-MMM-yy";
        public static final String FORMAT12 = "yyyyMMddHHmmSS";
        public static final String FORMAT13 = "yyyyMMdd HHmmss";
        public static final String FORMAT14 = "yyyy-MM-dd HH:mm:ss.SS";
        public static final String FORMAT15 = "HH:mm:ss.SS";
        public static final String FORMAT16 = "HH:mm";
        private DateFormats() {

        }
    }

    public static final class NewsBoColumns {
        public static final String SEQ_ID = "seqId";
        public static final String NEWS_ID = "newsId";
        public static final String EXT_ID = "extId";
        public static final String NEWS_PROVIDER = "newsProvider";
        public static final String ASSET_CLASS = "assetClass";
        public static final String NEWS_DATE = "newsDate";
        public static final String COUNTRY = "country";
        public static final String HOT_NEWS_INDICATOR = "hotNewsIndicator";
        public static final String TICKER_ID = "tickerId";
        public static final String SOURCE_ID = "sourceId";
        public static final String LAST_UPDATED_TIME = "lastUpdatedTime";
        public static final String STATUS = "status";
        public static final String LANGUAGE_ID = "languageId";
        public static final String NEWS_HEADLINE_LN = "newsHeadlineLn";
        public static final String EXCHANGE = "exchange";
        public static final String SYMBOL = "symbol";
        public static final String EDITORIAL_CODE = "editorialCode";
        public static final String GEO_REGION_CODE = "geoRegionCode";
        public static final String GOVERNMENT_CODE = "governmentCode";
        public static final String INDIVIDUAL_CODE = "individualCode";
        public static final String INDUSTRY_CODE = "industryCode";
        public static final String MARKET_SECTOR_CODE = "marketSectorCode";
        public static final String PRODUCT_SERVICES_CODE = "productServicesCode";
        public static final String INTERNAL_CLASS_TYPE = "internalClassType";
        public static final String APPROVAL_STATUS = "approvalStatus";
        public static final String URL = "url";
        public static final String UNIQUE_SEQUENCE_ID = "uniqueSequenceId";
        public static final String COMPANY_ID = "companyId";
        //news source
        public static final String NEWS_SOURCE_ID = "newsSourceId";
        public static final String NEWS_SOURCE_DESC = "newsSourceDesc";
        //Top News
        public static final String TOP_NEWS_EDITION_SECTION = "topNewsEditionSection";
        public static final String IS_TOP_STORY = "isTopStory";
        public static final String NODE_1_STATUS = "node1Status";
        public static final String NODE_2_STATUS = "node2Status";
        public static final String LANGUAGE = "language";

        private NewsBoColumns() {
        }

    }

    public static final class AnnouncementBoColumns {
        public static final String ANNOUNCEMENT_ID = "announcementId";
        public static final String DATE = "date";
        public static final String SOURCE_ID = "sourceId";
        public static final String TICKER_ID = "tickerId";
        public static final String ANN_HEADLINE = "annHeadLine";
        public static final String LANGUAGE_ID = "languageId";
        public static final String SECTOR_CODE = "sectorCode";
        public static final String TICKER_SERIAL = "tickerSerial";
        public static final String ANN_SOURCE_SERIAL = "annSourceSerial";
        public static final String PRIORITY_ID = "priorityId";
        public static final String STATUS = "status";
        public static final String SEQ_ID = "seqId";
        public static final String URL = "url";
        public static final String GICS_L2_CODE = "gicsL2Code";
        public static final String GICS_L3_CODE = " gicsL3Code";

        private AnnouncementBoColumns() {
        }
    }

    public static final class ZohoParams {

        public static final String USER_ID = "userId";
        public static final String USERNAME= "username";
        public static final String TITLE = "Title";
        public static final String FIRST_NAME = "First Name";
        public static final String LAST_NAME = "Last Name";
        public static final String EMAIL = "Email";
        public static final String PHONE = "Phone";
        public static final String COMPANY = "CONTACTCF6";
        public static final String COUNTRY = "CONTACTCF5";
        public static final String CASE_NUM = "CASECF1";
        public static final String TYPE = "CONTACTCF4";
        public static final String CURRENT_URL = "CONTACTCF9";
        public static final String SUBJECT = "Subject";
        public static final String PREFERRED_CONTACT_METHOD = "CONTACTCF8";
        public static final String DESCRIPTION = "Description";

        private ZohoParams() {
        }
    }

    public static final class MarketFundInvestments {
        public static final String ALLOCATION_ID = "ai";
        public static final String FUND_TICKER_SERIAL = "fts";
        public static final String FUND_NAME = "fn";
        public static final String FUND_TICKER = "ft";
        public static final String FUND_SOURCE = "fs";
        public static final String INVESTED_TICKER_SERIAL = "its";
        public static final String NUMBER_OF_SHARES = "ns";
        public static final String TXN_DATE = "td";
        public static final String FILE_ID = "fi";
        public static final String TICKER_ID = "t";
        public static final String TICKER_DESCRIPTION = "tDesc";
        public static final String SOURCE_ID = "s";
        public static final String GICS_L3_CODE = "gc";
        public static final String GICS_L3 = "gcDesc";
        public static final String SECTOR_ID = "si";
        public static final String SECTOR_NAME = "sDesc";
        public static final String COUNTRY_CODE = "cc";
        public static final String COUNTRY_DESC = "ccDesc";
        public static final String VWAP = "vwap";
        public static final String CURRENCY_RATE = "cur";

        private MarketFundInvestments() {
        }
    }

    public static final class UserSessionDetails {
        public static final String USERNAME = "un";
        public static final String FIRST_NAME = "fn";
        public static final String LAST_NAME = "ln";
        public static final String COMPANY = "cp";
        public static final String COUNTRY = "cnt";
        public static final String MOB = "mob";
        public static final String TEL = "tel";
        public static final String EMAIL = "email";
        public static final String ACCOUNT_MANAGER = "acm";
        public static final String START_DATE = "sDate";
        public static final String EXPIRY_DATE = "eDate";
        public static final String LOGIN_DATE = "loginDate";
        public static final String LOGOUT_DATE = "logoutDate";

        private UserSessionDetails() {
        }
    }

    public static class UpdateStatus {
        public static final int UPDATE_FAILED = -3;
        public static final int UPDATE_SKIPPED = -2;
        public static final int UPDATE_PENDING = -1;
        public static final int UPDATE_IN_PROGRESS = 0;
        public static final int UPDATE_COMPLETED = 1;

        private UpdateStatus() {
        }
    }

    public static class Status {
        public static final int SUCCESS = 1;
        public static final int FAILED = -1;

        private Status() {
        }
    }

    public static class UserCreationStatus {
        public static final int SUCCESS = 1;
        public static final int ZOHO_SUCCESS = 4;
        public static final int VALIDATION_ERROR = 2;
        public static final int VALIDATION_SUCCESS = 3;
        public static final int FAILED = -1;
        public static final int ZOHO_FAILED = -2;

        private UserCreationStatus() {
        }
    }

    public static class CampaignStatus {
        public static final String ACTIVE = "A";
        public static final String SUSPENDED = "S";
        public static final String PENDING = "P";
        public static final String ENDED = "E";

        private CampaignStatus() {
        }
    }

    public static class CampaignInventoryStatus {
        public static final String PENDING = "P";
        public static final String ACTIVE = "A";
        public static final String USED = "U";
        public static final String SUSPENDED = "S";
        public static final String EXPIRED = "E";

        private CampaignInventoryStatus() {
        }
    }

    public static class CampaignActivationType {
        public static final int CLOSE_ENDED = 1;
        public static final int FIXED_DURATION = 2;

        private CampaignActivationType() {
        }
    }

    public class ExcelKeys {
        public static final String DEFAULT = "default";
        public static final String TYP_DATE = "date";
        public static final String TYP_FLOAT = "float";
        public static final String TYP_TIME = "time";
        public static final String TYP_HIDE = "hide";
        public static final String FUND_CLASS = "fundClass";
        public static final String FUND_META = "fundMeta";
        public static final String COUNTRY_DESC = "conDesc";
        public static final String FUND_META_M = "fundMetaM";
        public static final String FUND_META_I = "fundMetaI";
        public static final String PERCENTAGE = "percentage";
        public static final String SUKUK_TYPE = "sukukType";
        public static final String BOND_TYPE = "bondType";
        public static final String FIXED_INCOME_TYPE = "fiType";
        public static final String COUPON_FREQUENCY = "coupFr";
        public static final String TENOR = "tenor";
        //Document Screener
        public static final String REPORT_CATEGORY = "reportCat";
        public static final String REPORT_SUB_CATEGORY = "reportSubCat";
        public static final String REPORT_PUBLISHER = "reportPub";
        public static final String REPORT_TITLE = "reportTitle";
        public static final String INSTRUMENT = "reportIns";
        public static final String COMPANY = "comp";
        public static final String REPORT_URL = "reportUrl";
        public static final String CONTEXT_PATH = "contextPath";
        public static final String PROTOCOL = "protocol";
        public static final String SERVER_PATH = "serverPath";
        public static final String CONTEXT_DATA = "contextData";
        //IPO
        public static final String INDUSTRY_DATA = "industryData";
        public static final String IPO_STATUS = "ipoStatus";
        public static final String NUMBER = "num";

        private ExcelKeys() {
        }
    }

    public class AnnouncementUpdateStatus {
        public static final String SUCCESS = "S";
        public static final String FAILURE = "F";
        public static final String SKIPPED = "SK";
        public static final String DELETE_SUCCESS = "DS";
        public static final String DELETE_FAILED = "DF";

        private AnnouncementUpdateStatus() {
        }
    }

    public class DataConditions {

        public static final String CONDITION_FALSE = "0";
        public static final String CONDITION_TRUE = "1";

        private DataConditions() {
        }
    }

    public static final class TimePeriodConstants {
        public static final int DEFAULT = 0;
        public static final int ONE_MONTH = 1;
        public static final int THREE_MONTHS = 3;
        public static final int SIX_MONTHS = 6;
        public static final int ONE_YEAR = 12;
        public static final int TWO_YEARS = 24;

        private TimePeriodConstants() {
        }
    }

    public class WelcomeEmailStatus {
        public static final int SUCCESS = 0;
        public static final int MAX_LOGIN_ERROR = 1;
        public static final int SUSPICIOUS_ERROR = 2;
        public static final int ACCOUNT_EXPIRED_ERROR = 3;
        public static final int LINK_EXPIRED_ERROR = 4;

        private WelcomeEmailStatus() {
        }

    }

    public class WelcomeEmailDirectPage {
        public static final int HOME_PAGE = 0;
        public static final int PREFERENCES_PAGE = 1;
    }

    public class InteractionCustomerParam {
        public static final String NAME = "NAME";
        public static final String PREFIX = "PREFIX";
        public static final String EMAIL = "EMAIL";
        public static final String ID = "ID";
    }

    public static final class AuthenticationAPIConstants {

        //Create response
        public static final String FAILED = "FAILED";
        public static final String USER_NOT_EXIST = "USER DOES NOT EXIST";
        public static final String SUCCESS = "SUCCESS";
        public static final String STATUS_VALID = "0";
        public static final String STATUS_EXPIRED = "1";
        public static final String STATUS_REQUEST_NOT_VALID = "2";
        public static final String CREDENTIAL_MISMATCH = "3";
        public static final String MESSAGE_VALID = "VALID LOGIN";
        public static final String MESSAGE_CREDENTIAL_MISMATCH = "USER CREDENTIALS MISMATCH";
        public static final String MESSAGE_EXPIRED = "EXPIRED";
        public static final String MESSAGE_REQUEST_NOT_VALID = "REQUEST PARAMETERS NOT CORRECT";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String TYPE = "type";

        private AuthenticationAPIConstants() {
        }


    }

    public static final class NewsRequestTypes {

        //Create response
        public static final int STOCK = 1;
        public static final int EXCHANGE = 2;
        public static final int COMMODITY_NEWS_BY_COUNTRY = 3;
        public static final int CURRENCY_NEWS_BY_COUNTRY = 4;
        public static final int COUNTRY = 5;
        public static final int FIXED_INCOME_NEWS_BY_COUNTRY = 6;
        public static final int FUNDS_NEWS_BY_COUNTRY = 7;
        public static final int IPO_NEWS_BY_COUNTRY = 8;
        public static final int MA_NEWS_BY_COUNTRY = 9;
        public static final int TREASURY_BILLS_NEWS_BY_COUNTRY = 10;
        public static final int BY_ID = 11;
        public static final int RELATED_NEWS_BY_SYMBOL = 12;
        public static final int RELATED_NEWS_BY_COMPANY = 13;
        public static final int RELATED_NEWS_BY_INDIVIDUAL = 14;
        public static final int RELATED_NEWS_BY_COUNTRY = 15;
        public static final int SEARCH = 16;
        public static final int SEARCH_COUNT = 17;
        public static final int BY_EDITORIAL_CODE = 18;
        public static final int INDIVIDUAL = 19;
        public static final int INDIVIDUAL_NEWS_BY_COUNTRY = 20;
        public static final int EQUITY_NEWS_BY_COUNTRY = 21;

        private NewsRequestTypes() {
        }

    }


    public enum DBTypes {
        DERBY, ORACLE
    }

    /**
     * company Financials Constants
     */
    public class CompanyFinancialsConstants{
        private CompanyFinancialsConstants(){
        }

        public enum CompanySizeColumnMap {
            comp(DBConstants.LangSpecificDatabaseColumns.COMPANY_NAME),
            cid(""),
            inds(DBConstants.LangSpecificDatabaseColumns.GICS_L4_DESC),
            gicsL2(""),
            revenue("TOTAL_REVENUE_TEMP"),
            revenueMin("TOTAL_REVENUE_TEMP"),
            revenueMax("TOTAL_REVENUE_TEMP"),
            revenueActYear(""),
            revenueBktYear(""),
            revenue_year("TOTAL_REVENUE_YEAR_TEMP"),
            assets("TOTAL_ASSETS_TEMP"),
            assetsMin("TOTAL_ASSETS_TEMP"),
            assetsMax("TOTAL_ASSETS_TEMP"),
            assetsBktYear(""),
            assetsActYear(""),
            assets_year("TOTAL_ASSETS_YEAR_TEMP");

            private String defaultValue;

            private CompanySizeColumnMap(String defValue) {
                this.defaultValue = defValue;
            }

            public String getDefaultValue() {
                return defaultValue;
            }
        }
    }

    /**
     * Company screener request data constants
     */
    public class CompanyScreenerConstants {
        private CompanyScreenerConstants() {
        }

        public final static String DEFAULT_LANGUAGE = "EN";
        public final static String DEFAULT_COMPANY_LISTING_STATUS = "1,2,3";
        public final static String DEFAULT_COMPANY_TYPES = "1,18,7";
        public final static short MAXIMUM_KPI_RESULTS = 2;
        public final static String SELECTED_COLUMNS = "SELECTED_COLUMNS";
        public final static String IS_SERVICE = "IS_SERVICE";
        public final static String SCREENER_REQUEST = "SCREENER_REQUEST";
        public final static String KPI_ID = "KPI_ID";
        public final static double DEFAULT_VALUE = 0.0;

        public enum Mandatory {
            DB_TYPE,
            LANGUAGE,
            CRITERION_TYPE,
            PAGE_INDEX,
            PAGE_SIZE,
            SORT_FIELD,
            SORT_ORDER
        }

        public enum CriteriaTypes {
            KPI_DATA, PRODUCT, DEBT, COMPANY_SIZE, GROWTH_RATE, OTHER
        }

        public enum Criteria {
            COUNTRIES,
            LISTING_STATUS,
            EST_DATE_FROM,
            EST_DATE_TO,
            COMPANY_TYPE,
            INDUSTRY,
            PRODUCT,
            KPI,
            DEBT,
            COMPANY_SIZE,
            GROWTH_RATE,
            MARKET_CAP_FROM,
            MARKET_CAP_TO,
            COMPANY_NAME
        }

        /**
         * TOTAL_ASSETS, TOTAL_LIABILITIES, TOTAL_REVENUE, NET_INCOME, MARKET_CAP
         */
        public enum CompanySizeTypes{
            A("TOTAL_ASSETS"), L("TOTAL_LIABILITIES"), R("TOTAL_REVENUE"), I("NET_INCOME"), M("MARKET_CAP");
            private String defaultValue;

            private CompanySizeTypes(String defValue) {
                this.defaultValue = defValue;
            }

            public String getDefaultValue() {
                return defaultValue;
            }
        }
        /**
         * Enum for params with column mapping
         */
        public enum CompanyScreenerParamsColumnMap {
            comp(DBConstants.LangSpecificDatabaseColumns.COMPANY_NAME),
            inds(DBConstants.LangSpecificDatabaseColumns.GICS_L4_DESC),
            sec(DBConstants.LangSpecificDatabaseColumns.SECTOR_NAME),
            type(DBConstants.DatabaseColumns.COMPANY_TYPE),
            con(DBConstants.LangSpecificDatabaseColumns.COUNTRY_DESC),
            est(DBConstants.DatabaseColumns.ESTABLISHED_DATE),
            mcap("MCAP"), //this is a virtual column @ref CompanyScreenerOracleQueryGenerator line 60/CompanyScreenerIMDBQueryGenerator line 29
            cid(""),
            cc(""),
            rev(""),
            asst(""),
            liab(""),
            pe(""),
            pb(""),
            dy(""),
            prod(DBConstants.DatabaseColumns.CPC_L5_LAN),
            pric(""),
            ch(""),
            chp(""),
            kpi(""),
            kpiid_1(DBConstants.DatabaseColumns.KPI_DEFINITION),
            kpiid_2(DBConstants.DatabaseColumns.KPI_DEFINITION),
            kpival_1(DBConstants.DatabaseColumns.KPI_VALUE),
            kpival_2(DBConstants.DatabaseColumns.KPI_VALUE),
            growthAct(""),
            growthMin(""),
            growthMax(""),
            assets("TOTAL_ASSETS_TEMP"),
            assetsMin("TOTAL_ASSETS_TEMP"),
            assetsMax("TOTAL_ASSETS_TEMP"),
            assetsActYear(""),
            assetsBktYear(""),
            assets_year("TOTAL_ASSETS_YEAR_TEMP"),
            liabilities("TOTAL_LIABILITIES_TEMP"),
            liabilitiesMin("TOTAL_LIABILITIES_TEMP"),
            liabilitiesMax("TOTAL_LIABILITIES_TEMP"),
            liabilitiesActYear(""),
            liabilitiesBktYear(""),
            liabilities_year("TOTAL_LIABILITIES_YEAR_TEMP"),
            revenue("TOTAL_REVENUE_TEMP"),
            revenueMin("TOTAL_REVENUE_TEMP"),
            revenueMax("TOTAL_REVENUE_TEMP"),
            revenueActYear(""),
            revenueBktYear(""),
            revenue_year("TOTAL_REVENUE_YEAR_TEMP"),
            income("NET_INCOME_TEMP"),
            incomeMin("NET_INCOME_TEMP"),
            incomeMax("NET_INCOME_TEMP"),
            incomeActYear(""),
            incomeBktYear(""),
            income_year("NET_INCOME_YEAR_TEMP"),
            symbol("");

            private String defaultValue;

            private CompanyScreenerParamsColumnMap(String defValue) {
                this.defaultValue = defValue;
            }

            public String getDefaultValue() {
                return defaultValue;
            }
        }
    }

    /**
     * enum for company owners info types
     */
    public enum CompanyOwnersInfoTypes {
        OWN_IND("OWN_IND"),
        OWN_CMP("OWN_CMP"),
        OWN_CMP_COP("OWN_CMP_COP"),
        OWN_CMP_NON_COP("OWN_CMP_NON_COP"),
        FNINAL("FNINAL"),
        CMOWH("CMOWH"),
        INOWH("INOWH");

        private String defaultType;

        CompanyOwnersInfoTypes(String type) {
            this.defaultType = type;
        }

        public static CompanyOwnersInfoTypes getEnum(String name) {
            CompanyOwnersInfoTypes type = null;
            for (CompanyOwnersInfoTypes c : CompanyOwnersInfoTypes.values()) {
                if (c.name().equals(name)) {
                    type = c;
                }
            }
            return type;
        }
    }

    /**
     * company home page constants
     */
    public static final class CompanyHome {
        public static final String DEFAULT_REGION = "MENA-20";
        public static final String DEFUALT_COUNTRY = "*";
        public static final String COMPANY_AGGREGATES_COUNTRIES = "AE,SA,EG,QA,KW";
        public static final String COMAPNY_SIZE_MCAP_COUNTRIES = "BH,EG,IQ,JO,KW,LB,LY,MA,OM,PS,QA,SA,TN,AE";
        public static final String COMPANY_NAME = "comp";
        public static final String INDUSTRY = "industry";
        public static final String MCAP = "mcap";
        public static final String CHANGE = "change";
        public static final String CID = "cid";
        public static final String GICSL2 = "gicsL2";
        public static final String COUNTRY_CODE = "countryCode";
        public static final String MCAP_CHANGE = "CHANGE";
        public static final String COMPANY_SIZE_TYPE = "COMPANY_SIZE_TYPE";
        public static final String LISTED_TYPE = "listedType";
        public static final String AGGRE_CONTAINER = "aggreContainer";
        public static final String COMPANY_COUNT = "Company Count";
        public static final String COMPANY_TOTAL = "Company Total";
        public static final String IRAN = "IR";
        public final static String COMPANY_HOME_PAGE_URL = "/{0}/company-home/{1}/{2}";
        public final static String COMPANY_SIZE_URL = "/{0}/company-home/companySize/{1}";
        public final static String COMPANY_LIST_URL = "/company-list/";
        public final static String PEOPLE_LIST_URL = "/people-list/";

        private CompanyHome() {
        }
    }

    /**
     * Region Coverage Enum
     * Coverage types - country coverage, industry coverage
     */
    public enum Coverage {
        COUNTRY,
        INDUSTRY
    }

    /**
     * company industry aggregates types
     */
    public enum CompanyAggregatesTypes {
        MCAP("STOCK_MARKET_CAP_USD"),
        TOTAL_REVENUE("TOTAL_REVENUE_USD"),
        NET_INCOME("NET_INCOME_USD"),
        TOTAL_ASSETS("TOTAL_ASSETS_USD"),
        TOTAL_LIABILITIES("TOTAL_LIABILITIES_USD");

        private String columnName;
        private static Map<String, CompanyAggregatesTypes> columnMap = new HashMap<String, CompanyAggregatesTypes>();
        private static Map<String, CompanyAggregatesTypes> typeMap = new HashMap<String, CompanyAggregatesTypes>();

        static {
            for (CompanyAggregatesTypes type : values()) {
                columnMap.put(type.columnName, type);
            }
        }

        static {
            for (CompanyAggregatesTypes type : values()) {
                typeMap.put(type.toString(), type);
            }
        }

        CompanyAggregatesTypes(String columnName) {
            this.columnName = columnName;
        }

        public static CompanyAggregatesTypes getSourceType(String column) {
            return columnMap.get(column);
        }

        public static CompanyAggregatesTypes getType(String column) {
            return typeMap.get(column);
        }

        public String getDefaultValue() {
            return columnName;
        }
    }

    public static final class periodLengths{

        private periodLengths() {
        }

        public static final String QUARTER_1 = "3";
        public static final String QUARTER_1_2 = "6";
        public static final String QUARTER_1_3 = "9";
        public static final String QUARTER_1_4 = "12";
    }


    public enum AverageRatioBasis {
        MEDIAN(5),
        MEAN(4),
        AGGREGATED_AVERAGE(6),
        GEOMETRIC_MEAN(7);

        private static Map<Integer, AverageRatioBasis> typeMap = new HashMap<Integer, AverageRatioBasis>();

        static {
            for (AverageRatioBasis type : values()) {
                typeMap.put(type.defaultValue, type);
            }
        }

        private int defaultValue;

        public static AverageRatioBasis getAverageRatioBasis(int type) {
            return typeMap.get(type);
        }

        private AverageRatioBasis(int defValue) {
            this.defaultValue = defValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * data import methods-solr
     */
    public enum SolrDataImportType {
        FULL_DATA_IMPORT("full-import"),
        DELTA_IMPORT("delta-import");

        private String defaultValue;

        SolrDataImportType(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum PurchaseItemType{
        REPORT(1),
        OTHER(2);

        private int code;

        private PurchaseItemType(int code) {
            this.code = code;
        }

        public static PurchaseItemType valueOf(int code) {
            switch (code) {
                case 1:
                    return REPORT;
                case 2:
                    return OTHER;
                default:
                    return OTHER;
            }
        }

        public int getCode(){
            return this.code;
        }

        public static String getItemTypeDesc(PurchaseItemType type){
            switch (type){
                case REPORT:
                    return "Report";
                case OTHER:
                    return "Other";
                default :
                    return "Other";
            }
        }
    }

    public static final class UserPurchase{
        public static final String REFERENCE_CODE = "REF";
        public static final String PURCHASE_ID = "PURCHASE_ID";
        public static final String USER_NAME = "USER_NAME";
        public static final String COMP_NAME = "COMP_NAME";
        public static final String USER_ID = "USER_ID";
        public static final String ITEM_ID = "ITEM_ID";
        public static final String EMAIL = "EMAIL";
        public static final String PHONE = "PHONE";
        public static final String PAYMENT_METHOD = "PAYMENT_METHOD";
        public static final String ITEM_TYPE = "ITEM_TYPE";
        public static final String ITEM_DES = "ITEM_DES";
        public static final String REQ_DATE = "REQ_DATE";
        public static final String COM_DATE = "COM_DATE";
        public static final String STATUS = "STATUS";
        public static final String MODERATOR_NAME = "MODERATOR_NAME";
        public static final String PRICE_USD = "PRICE_USD";

        private UserPurchase(){}

    }

    /**
     * calendar events constants
     */
    public static final class CalendarEvent {
        public static final String COMPANY_EVENTS_SORT_FIELD = "EVENT_START_DATE";
        public static final String EVENT_START_DATE = "EVENT_START_DATE";
        public static final String MAIN_GROUP_ID = "MAIN_GROUP_ID";
        public static final String ALL = "ALL";
        public static final String CALENDER_EVENTS_PAGE_SIZE = "20";
        public static final String CALENDER_EVENTS_DEFAULT_PAGE_INDEX = "0";
        public static final String CALENDER_EVENTS_DEFAULT_SORT_ORDER = IConstants.MIXDataField.DESC.toUpperCase();
        public static final String SYMBOL = "SYMBOL";
        public static final String EXCHANGE = "EXCHANGE";
        public static final String EVENT_TITLE_ID = "EVENT_TITLE_ID";
        public static final String CALENDAR_EVENTS_PREMIUM_URL = "/{0}/calendar-events/all/{1}/{2}";

        private CalendarEvent() {
        }
    }

    /**
     * corporate actions constants
     */
    public static final class CorporateAction {
        public static final String SYMBOL_DESC = "SYMBOL_DESC";
        public static final String EXCHANGE_DESC = "EXCHANGE_DESC";
        public static final String EXCHANGE_DE = "EXCHANGE_DE";
        public static final String SYMBOL = "SYMBOL";
        public static final String EXCHANGE = "EXCHANGE";
        public static final String STORY = "STORY";
        public static final String FORMATTED_DIVIDEND_AMOUNT = "FORMATTED_DIVIDEND_AMOUNT";
        public static final String DIVIDEND_AMOUNT = "DIVIDEND_AMOUNT";
        public static final String NEWS_ID = "NEWS_ID";
        public static final String ANNOUNCEMENT_ID = "ANNOUNCEMENT_ID";
        public static final String CURRENCY = "CURRENCY";
        public static final String DEFAULT_PAGE_SIZE = "10";
        public static final String DEFAULT_PAGE_ID = "0";
        public static final String DEFAULT_SORT_ORDER = "DESC";
        public static final String DEFAULT_SORT_FIELD = "EFFECTIVE_DATE";
        public static final String USE_SUB_UNIT = "USE_SUB_UNIT";
        public static final String CORPORATE_ACTION_PATH = "CORPORATE_ACTION_PATH";
        public static final String SUBCATEGORY = "SUBCATEGORY";
        public static final String SEARCH_DATA = "SEARCH_DATA";
        public static final int NUMBER_OF_SYMBOL_CORPORATE_ACTIONS = 5;
        public static final int NUMBER_OF_EXCHANGE_CORPORATE_ACTIONS = 10;
        public static final String EMAIL_EXCEL_TO_FRIEND_RECORDED = " **** Corporate Action email excel to friend - ";
        public static final String CORPORATE_ACTIONS_ALL_FREE_URL = "/{0}/corporate-actions/all/{1}/{2}";

        private CorporateAction() {
        }
    }

    public enum NewsSupportedLanguages{
        EN,AR
    }

    public class MAConstants{
        private MAConstants(){
        }

        public enum MALeagueColumnMap{
            dealSizeUsd("DEAL_SIZE_USD"),
            noOfDeals("NO_OF_DEALS"),
            countryCode("COUNTRY"),
            companyName("FINANCIAL_ADVISOR");

            private String defaultValue;

            private MALeagueColumnMap(String defValue) {
                this.defaultValue = defValue;
            }

            public String getDefaultValue() {
                return defaultValue;
            }
        }
    }

    public final class HTTPHeaders{
        private HTTPHeaders(){}
        public final static String X_FORWARDED_FOR = "X-FORWARDED-FOR";
    }

    public final class OwnershipLimitsInfoTypes {
        public OwnershipLimitsInfoTypes() {
        }

        public final static String OWLM_SER = "OWLM_SER";
        public final static String OWLM_STK = "OWLM_STK";
        public final static String OWLM_VAL = "OWLM_VAL";
        public final static String OWLM_BLKV = "OWLM_BLKV";
    }
}
