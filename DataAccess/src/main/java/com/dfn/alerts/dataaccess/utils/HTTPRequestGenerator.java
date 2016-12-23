package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.api.IRequestGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/7/13
 * Time: 5:17 PM
 */
public class HTTPRequestGenerator implements IRequestGenerator {

    private static final Logger log = LogManager.getLogger(HTTPRequestGenerator.class);

    public String generateRequest(Map<String, String> request, IConstants.RequestDataType paramEnum) {
        StringBuilder sb = new StringBuilder("");
        int index = 1;
        String value;
        String defaultVal;
        try {
            switch (paramEnum) {
                case REPORT_DATA:
                    String isDirect = request.get(IConstants.CustomDataField.DIRECT_DATA);
                    Boolean isCountryOnly = (request.get(IConstants.CustomDataField.IS_CONTRY_ONLY) != null && request.get(IConstants.CustomDataField.IS_CONTRY_ONLY).equals("1"));
                    for (IConstants.ReportSearchParameters reportParameter : IConstants.ReportSearchParameters.values()) {
                        if (isDirect == null && (reportParameter.toString().equals(IConstants.MIXDataField.RSD))) {
                            continue;
                        }
                        if((reportParameter.toString().equals(IConstants.MIXDataField.CON)) && !isCountryOnly) {
                            continue;
                        }

                        sb.append(reportParameter);
                        sb.append("=");
                        value = request.get(reportParameter.toString());
                        value = (value == null) ? reportParameter.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case TOP_STOCK:
                    for (IConstants.TopStockParameters topStockParameter : IConstants.TopStockParameters.values()) {
                        sb.append(topStockParameter);
                        sb.append("=");
                        value = request.get(topStockParameter.toString());
                        value = (value == null) ? topStockParameter.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case USER_PROFILE_GET:
                    for (IConstants.ProfileGetParameters profileParameter : IConstants.ProfileGetParameters.values()) {
                        sb.append(profileParameter);
                        sb.append("=");
                        value = request.get(profileParameter.toString());
                        value = (value == null) ? profileParameter.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case USER_PROFILE_SET:
                    for (IConstants.ProfileSetParameters profileParameter : IConstants.ProfileSetParameters.values()) {
                        sb.append(profileParameter);
                        sb.append("=");
                        value = request.get(profileParameter.toString());
                        value = (value == null) ? profileParameter.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case TECHNICAL_SCANNER:
                    for (IConstants.TechnicalScannerParameters technicalScannerParameters : IConstants.TechnicalScannerParameters.values()) {
                        sb.append(technicalScannerParameters);
                        sb.append("=");
                        value = request.get(technicalScannerParameters.toString());
                        value = (value == null) ? technicalScannerParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));

                        if(index < IConstants.TechnicalScannerParameters.values().length){
                            sb.append("&");
                        }

                        index++;
                    }
                    break;
                case ANNOUNCEMENT:
                    for(IConstants.AnnouncementParameters announceParam : IConstants.AnnouncementParameters.values()){
                        sb.append(announceParam);
                        sb.append("=");
                        value = request.get(announceParam.toString());
                        value = (value == null) ? announceParam.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case ANNOUNCEMENT_DETAILS:
                    for(IConstants.AnnouncementDetailsParameters announceParam : IConstants.AnnouncementDetailsParameters.values()){
                        sb.append(announceParam);
                        sb.append("=");
                        value = request.get(announceParam.toString());
                        value = (value == null) ? announceParam.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case AUTHENTICATION:
                    for(IConstants.AuthenticationParameters authenticationParameters : IConstants.AuthenticationParameters.values()){
                        sb.append(authenticationParameters);
                        sb.append("=");
                        value = request.get(authenticationParameters.toString());
                        value = (value == null) ? authenticationParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case PRICE_USER_LOGOUT:
                    for(IConstants.PriceUserLogOutParameters userLogOutParameters : IConstants.PriceUserLogOutParameters.values()){
                        sb.append(userLogOutParameters);
                        sb.append("=");
                        value = request.get(userLogOutParameters.toString());
                        value = (value == null) ? userLogOutParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case USER_DATA:
                    for(IConstants.UserDataParameters userDataParameters : IConstants.UserDataParameters.values()){
                        sb.append(userDataParameters);
                        sb.append("=");
                        value = request.get(userDataParameters.toString());
                        value = (value == null) ? userDataParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case PRICE_HISTORY:
                    for (IConstants.PriceHistoryParameters historyParameter : IConstants.PriceHistoryParameters.values()) {
                        sb.append(historyParameter);
                        sb.append("=");
                        value = request.get(historyParameter.toString());
                        value = (value == null) ? historyParameter.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case UPDATE_NOTIFICATION:
                    for (IConstants.NotificationParameters notificationParameters : IConstants.NotificationParameters.values()) {
                        sb.append(notificationParameters);
                        sb.append("=");
                        value = request.get(notificationParameters.toString());
                        value = (value == null) ? notificationParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FUND_POSITION:
                    for (IConstants.FundPositionParameters fundPositionParameters : IConstants.FundPositionParameters.values()) {
                        sb.append(fundPositionParameters);
                        sb.append("=");
                        value = request.get(fundPositionParameters.toString());
                        value = (value == null) ? fundPositionParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case MARKET_HIGHLIGHTS:
                    for(IConstants.MarketHighlights mHParameters : IConstants.MarketHighlights.values()){
                        if(request.get(mHParameters.toString()) != null || !mHParameters.getDefaultValues().isEmpty()) {
                            sb.append(mHParameters);
                            sb.append("=");
                            value = request.get(mHParameters.toString());
                            value = (value == null) ? mHParameters.getDefaultValues() : value;
                            sb.append(URLEncoder.encode(value, "UTF-8"));
                            sb.append("&");
                        }
                    }
                    break;
                case MARKET_HIGHLOW_CHART:
                    for(IConstants.MarketHighLowChart mHLParameters : IConstants.MarketHighLowChart.values()){
                        sb.append(mHLParameters);
                        sb.append("=");
                        value = request.get(mHLParameters.toString());
                        value = (value == null) ? mHLParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case COMPANY_LIST:
                    for (IConstants.CompaniesListingParameters companiesListingParameters : IConstants.CompaniesListingParameters.values()) {
                        sb.append(companiesListingParameters);
                        sb.append("=");
                        value = request.get(companiesListingParameters.toString());
                        value = (value == null) ? companiesListingParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case CITY_LIST:
                    for (IConstants.CityListParameters cityListParameters : IConstants.CityListParameters.values()) {
                        sb.append(cityListParameters);
                        sb.append("=");
                        value = request.get(cityListParameters.toString());
                        value = (value == null) ? cityListParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FINANCIAL_DATA:
                    for (IConstants.FinancialParameters financialParameters : IConstants.FinancialParameters.values()) {
                        sb.append(financialParameters);
                        sb.append("=");
                        value = request.get(financialParameters.toString());
                        value = (value == null) ? financialParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FAIR_VALUES:
                    for (IConstants.FairValuesParameters fairValuesParameters : IConstants.FairValuesParameters.values()) {
                        sb.append(fairValuesParameters);
                        sb.append("=");
                        value = request.get(fairValuesParameters.toString());
                        value = (value == null) ? fairValuesParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case BOND_PROFILE:
                    for (IConstants.BondProfileParameters bondProfileParameters : IConstants.BondProfileParameters.values()) {
                        sb.append(bondProfileParameters);
                        sb.append("=");
                        value = request.get(bondProfileParameters.toString());
                        value = (value == null) ? bondProfileParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case COMPANY_OWNERS:
                    for (IConstants.CompanyOwnersParameters companyOwnersParameters : IConstants.CompanyOwnersParameters.values()) {
                        sb.append(companyOwnersParameters);
                        sb.append("=");
                        value = request.get(companyOwnersParameters.toString());
                        value = (value == null) ? companyOwnersParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case NEWS_DETAILS:
                    for (IConstants.NewsDetailsParameters newsDetailsParameters : IConstants.NewsDetailsParameters.values()) {
                        sb.append(newsDetailsParameters);
                        sb.append("=");
                        value = request.get(newsDetailsParameters.toString());
                        value = (value == null) ? newsDetailsParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case NEWS:
                    for (IConstants.NewsParameters newsParameters : IConstants.NewsParameters.values()) {
                        value = request.get(newsParameters.toString());
                        defaultVal = newsParameters.getDefaultValue();
                        if ((value != null) || (!defaultVal.trim().isEmpty())) {
                            sb.append(newsParameters);
                            sb.append("=");
                            value = (value == null) ? newsParameters.getDefaultValue() : value;
                            sb.append(URLEncoder.encode(value, "UTF-8"));
                            sb.append("&");
                        }
                    }
                    break;
                case NEWS_TOP:
                    for (IConstants.TopNewsParameters topNewsParameters : IConstants.TopNewsParameters.values()) {
                        sb.append(topNewsParameters);
                        sb.append("=");
                        value = request.get(topNewsParameters.toString());
                        value = (value == null) ? topNewsParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INVESTMENTS:
                    for (IConstants.InvestmentsParameters investmentsParameters : IConstants.InvestmentsParameters.values()) {
                        sb.append(investmentsParameters);
                        sb.append("=");
                        value = request.get(investmentsParameters.toString());
                        value = (value == null) ? investmentsParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case COMPANY_PROFILE:
                    for (IConstants.CompanyProfileParameters companyProfileParameters : IConstants.CompanyProfileParameters.values()) {
                        sb.append(companyProfileParameters);
                        sb.append("=");
                        value = request.get(companyProfileParameters.toString());
                        value = (value == null) ? companyProfileParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INDIVIDUAL_PROFILE:
                    for (IConstants.IndividualParameters individualParameters : IConstants.IndividualParameters.values()) {
                        sb.append(individualParameters);
                        sb.append("=");
                        value = request.get(individualParameters.toString());
                        value = (value == null) ? individualParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case RELATED_INSTRUMENTS:
                    for (IConstants.RelatedInstrumentParameters relatedInstrumentParameters : IConstants.RelatedInstrumentParameters.values()) {
                        sb.append(relatedInstrumentParameters);
                        sb.append("=");
                        value = request.get(relatedInstrumentParameters.toString());
                        value = (value == null) ? relatedInstrumentParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case MULTI_SYMBOL_HISTORY:
                    for (IConstants.MultiSymbolHistoryRequestParameters multiSymbolParameter : IConstants.MultiSymbolHistoryRequestParameters.values()) {
                        sb.append(multiSymbolParameter);
                        sb.append("=");
                        value = request.get(multiSymbolParameter.toString());
                        value = (value == null) ? multiSymbolParameter.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FUND_PROFILE:
                    for (IConstants.FundProfileParameters fundProfileParameters : IConstants.FundProfileParameters.values()) {
                        sb.append(fundProfileParameters);
                        sb.append("=");
                        value = request.get(fundProfileParameters.toString());
                        value = (value == null) ? fundProfileParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FUND_ELIGIBILITY:
                    for (IConstants.FundEligibility fundEligibility : IConstants.FundEligibility.values()) {
                        sb.append(fundEligibility);
                        sb.append("=");
                        value = request.get(fundEligibility.toString());
                        value = (value == null) ? fundEligibility.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FUND_NAV_FREQ:
                    for (IConstants.FundNavFreq fundNavFreq : IConstants.FundNavFreq.values()) {
                        sb.append(fundNavFreq);
                        sb.append("=");
                        value = request.get(fundNavFreq.toString());
                        value = (value == null) ? fundNavFreq.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case SYMBOL_SEARCH:
                    for (IConstants.SymbolSearchParameters symbolSearchParam : IConstants.SymbolSearchParameters.values()) {
                        sb.append(symbolSearchParam);
                        sb.append("=");
                        value = request.get(symbolSearchParam.toString());
                        value = (value == null) ? symbolSearchParam.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case MULTIMEDIA:
                    for (IConstants.MultimediaParameters multimediaParameters : IConstants.MultimediaParameters.values()) {
                        sb.append(multimediaParameters);
                        sb.append("=");
                        value = request.get(multimediaParameters.toString());
                        value = (value == null) ? multimediaParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case MULTIMEDIA_PROVIDERS:
                    for (IConstants.MultimediaProviders mediaProviders : IConstants.MultimediaProviders.values()) {
                        sb.append(mediaProviders);
                        sb.append("=");
                        value = request.get(mediaProviders.toString());
                        value = (value == null) ? mediaProviders.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FUND_SCREENER:
                    for (IConstants.FundamentalScreenerParameters screenerParameters : IConstants.FundamentalScreenerParameters.values()) {
                        sb.append(screenerParameters);
                        sb.append("=");
                        value = request.get(screenerParameters.toString());
                        value = (value == null) ? screenerParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INDIVIDUAL_OWNSHP_HISTRY:
                    for (IConstants.IndividualParameters individualParameters : IConstants.IndividualParameters.values()) {
                        sb.append(individualParameters);
                        sb.append("=");
                        value = request.get(individualParameters.toString());
                        value = (value == null) ? individualParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case CALENDAR_EVENTS:
                    for (IConstants.CalendarEventParameters calendarEventParameters : IConstants.CalendarEventParameters.values()) {
                        sb.append(calendarEventParameters);
                        sb.append("=");
                        value = request.get(calendarEventParameters.toString());
                        value = (value == null) ? calendarEventParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case ZOHO_DATA:
                    for (String key : request.keySet()) {
                        sb.append(URLEncoder.encode(key, "UTF-8"));
                        sb.append("=");
                        value = request.get(key);
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case PEER_COMPANY:
                    log.error("----------- HTTP REQUEST GENERATOR :: NOT SUPPORTED REQUEST :: PEER_COMPANY");
                    break;
                case EARNINGS:
                    for (IConstants.EarningsParameters earningsParameters : IConstants.EarningsParameters.values()) {
                        sb.append(earningsParameters);
                        sb.append("=");
                        value = request.get(earningsParameters.toString());
                        value = (value == null) ? earningsParameters.getDefaultValues() : value;
//                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append(value);
                        sb.append("&");
                    }
                    break;
                case CORPORATE_ACTIONS:
                    for (IConstants.CorporateActionsParameters corporateActionsParameters : IConstants.CorporateActionsParameters.values()) {
                        sb.append(corporateActionsParameters);
                        sb.append("=");
                        value = request.get(corporateActionsParameters.toString());
                        value = (value == null) ? corporateActionsParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case CALENDAR_EVENTS_META_DATA:
                    for (IConstants.CalendarEventMetaDataParams calendarEventMetaDataParams : IConstants.CalendarEventMetaDataParams.values()) {
                        sb.append(calendarEventMetaDataParams);
                        sb.append("=");
                        value = request.get(calendarEventMetaDataParams.toString());
                        value = (value == null) ? calendarEventMetaDataParams.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case COMPANY_MANAGEMENT:
                    for (IConstants.CompanyManagementParameters companyManagementParameters : IConstants.CompanyManagementParameters.values()) {
                        sb.append(companyManagementParameters);
                        sb.append("=");
                        value = request.get(companyManagementParameters.toString());
                        value = (value == null) ? companyManagementParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case MACRO_ECONOMY:
                    for (IConstants.MacroEconomyParameters macroEconomyParameters : IConstants.MacroEconomyParameters.values()) {
                        sb.append(macroEconomyParameters);
                        sb.append("=");
                        value = request.get(macroEconomyParameters.toString());
                        value = (value == null) ? macroEconomyParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INSIDER_TRANSACTION:
                    for (IConstants.InsiderTransactionParameters insiderTransactionParameters : IConstants.InsiderTransactionParameters.values()) {
                        sb.append(insiderTransactionParameters);
                        sb.append("=");
                        value = request.get(insiderTransactionParameters.toString());
                        value = (value == null) ? insiderTransactionParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FINANCIAL_AVG_RATIO:
                    for (IConstants.FinancialAverageRatioParameters financialAverageRatioParameters : IConstants.FinancialAverageRatioParameters.values()) {
                        sb.append(financialAverageRatioParameters);
                        sb.append("=");
                        value = request.get(financialAverageRatioParameters.toString());
                        value = (value == null) ? financialAverageRatioParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INDIVIDUALS_SEARCH:
                    for (IConstants.IndividualsSearchParameters individualsSearchParameters : IConstants.IndividualsSearchParameters.values()) {
                        sb.append(individualsSearchParameters);
                        sb.append("=");
                        value = request.get(individualsSearchParameters.toString());
                        value = (value == null) ? individualsSearchParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INDIVIDUAL_DATA_BY_COUNTRY:
                    for (IConstants.IndividualDataByCountryParameters individualParameters : IConstants.IndividualDataByCountryParameters.values()) {
                        sb.append(individualParameters);
                        sb.append("=");
                        value = request.get(individualParameters.toString());
                        value = (value == null) ? individualParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                 case KEY_OFFICERS:
                    for (IConstants.KeyOfficersParameters keyOfficersParameters : IConstants.KeyOfficersParameters.values()) {
                        sb.append(keyOfficersParameters);
                        sb.append("=");
                        value = request.get(keyOfficersParameters.toString());
                        value = (value == null) ? keyOfficersParameters.getDefaultValue() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case COUPON_FREQUENCY:
                    for (IConstants.CouponFrequency cFParameters : IConstants.CouponFrequency.values()) {
                        sb.append(cFParameters);
                        sb.append("=");
                        value = request.get(cFParameters.toString());
                        value = (value == null) ? cFParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case FUND_ACTION:
                    for (IConstants.FundAction fundActionsParameters : IConstants.FundAction.values()) {
                        sb.append(fundActionsParameters);
                        sb.append("=");
                        value = request.get(fundActionsParameters.toString());
                        value = (value == null) ? fundActionsParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INVESTOR_TYPES:
                    for (IConstants.InvestorTypes investorTypesParameters : IConstants.InvestorTypes.values()) {
                        sb.append(investorTypesParameters);
                        sb.append("=");
                        value = request.get(investorTypesParameters.toString());
                        value = (value == null) ? investorTypesParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case INVESTOR_NATIONALITIES:
                    for (IConstants.InvestorNationalities investorNationalitiesParameters : IConstants.InvestorNationalities.values()) {
                        sb.append(investorNationalitiesParameters);
                        sb.append("=");
                        value = request.get(investorNationalitiesParameters.toString());
                        value = (value == null) ? investorNationalitiesParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                case UPDATE_FREQ:
                    for (IConstants.InvestorUpdateFreq investorUpdateFreqParameters : IConstants.InvestorUpdateFreq.values()) {
                        sb.append(investorUpdateFreqParameters);
                        sb.append("=");
                        value = request.get(investorUpdateFreqParameters.toString());
                        value = (value == null) ? investorUpdateFreqParameters.getDefaultValues() : value;
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    break;
                default:
                    log.error("----------- HTTP REQUEST GENERATOR :: NOT SUPPORTED REQUEST :: " + paramEnum.toString());
                    break;
            }
        } catch (UnsupportedEncodingException uee) {
            log.error("generateRequest : " + paramEnum.toString() + "::" + uee);
        }

        return sb.toString();
    }
}
