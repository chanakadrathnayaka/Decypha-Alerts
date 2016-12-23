package com.dfn.alerts.dataaccess.utils;


import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.api.IRequestGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/29/12
 * Time: 2:07 PM
 */
public class SocketRequestGenerator implements IRequestGenerator {

    public String generateRequest(Map<String, String> request, IConstants.RequestDataType paramEnum) {

        Map<String, String[]> requestData = new HashMap<String, String[]>();

        switch (paramEnum) {
            case PRICE_HISTORY:
                for (IConstants.PriceHistoryParameters historyParameter : IConstants.PriceHistoryParameters.values()) {
                    requestData.put(historyParameter.toString(), toArray(request.get(historyParameter.toString()), historyParameter.getDefaultValue()));
                }
                break;
            case PRICE_SNAPSHOT:
                for (IConstants.PriceSnapshotParameters snapshotParameters : IConstants.PriceSnapshotParameters.values()) {
                    requestData.put(snapshotParameters.toString(), toArray(request.get(snapshotParameters.toString()), snapshotParameters.getDefaultValue()));
                }
                break;
            case INSIDER_TRANSACTION:
                for (IConstants.InsiderTransactionParameters insiderTransactionParameters : IConstants.InsiderTransactionParameters.values()) {
                    requestData.put(insiderTransactionParameters.toString(), toArray(request.get(insiderTransactionParameters.toString()), insiderTransactionParameters.getDefaultValue()));
                }
                break;
            case FAIR_VALUES:
                for (IConstants.FairValuesParameters fairValuesParameters : IConstants.FairValuesParameters.values()) {
                    requestData.put(fairValuesParameters.toString(), toArray(request.get(fairValuesParameters.toString()), fairValuesParameters.getDefaultValue()));
                }
                break;
            case NEWS_DETAILS:
                for (IConstants.NewsDetailsParameters newsDetailsParameters : IConstants.NewsDetailsParameters.values()) {
                    requestData.put(newsDetailsParameters.toString(), toArray(request.get(newsDetailsParameters.toString()), newsDetailsParameters.getDefaultValue()));
                }
                break;
            case NEWS:
                for (IConstants.NewsParameters newsParameters : IConstants.NewsParameters.values()) {
                    requestData.put(newsParameters.toString(), toArray(request.get(newsParameters.toString()), newsParameters.getDefaultValue()));
                }
                break;
            case TOP_STOCK:
                for (IConstants.TopStockParameters topStockParameters : IConstants.TopStockParameters.values()) {
                    requestData.put(topStockParameters.toString(), toArray(request.get(topStockParameters.toString()), topStockParameters.getDefaultValue()));
                }
                break;
            case ANNOUNCEMENT:
                for (IConstants.AnnouncementParameters announcementParameters : IConstants.AnnouncementParameters.values()) {
                    requestData.put(announcementParameters.toString(), toArray(request.get(announcementParameters.toString()), announcementParameters.getDefaultValue()));
                }
                break;
            case SYMBOL_DATA:
                for (IConstants.SymbolDataParameters symbolDataParameters : IConstants.SymbolDataParameters.values()) {
                    requestData.put(symbolDataParameters.toString(), toArray(request.get(symbolDataParameters.toString()), symbolDataParameters.getDefaultValue()));
                }
                break;
            case CORPORATE_ACTIONS:
                for (IConstants.CorporateActionsParameters corporateActionsParameters : IConstants.CorporateActionsParameters.values()) {
                    requestData.put(corporateActionsParameters.toString(), toArray(request.get(corporateActionsParameters.toString()), corporateActionsParameters.getDefaultValue()));
                }
                break;
            case FINANCIAL_DATA:
                for (IConstants.FinancialParameters financialParameters : IConstants.FinancialParameters.values()) {
                    requestData.put(financialParameters.toString(), toArray(request.get(financialParameters.toString()), financialParameters.getDefaultValue()));
                }
                break;
            case CALENDAR_EVENTS:
                for (IConstants.CalendarEventParameters calendarEventParameters : IConstants.CalendarEventParameters.values()) {
                    requestData.put(calendarEventParameters.toString(), toArray(request.get(calendarEventParameters.toString()), calendarEventParameters.getDefaultValue()));
                }
                break;
            case CALENDAR_EVENTS_META_DATA:
                for (IConstants.CalendarEventMetaDataParams calendarEventParams : IConstants.CalendarEventMetaDataParams.values()) {
                    requestData.put(calendarEventParams.toString(), toArray(request.get(calendarEventParams.toString()), calendarEventParams.getDefaultValue()));
                }
                break;
            case INVESTMENTS:
                for (IConstants.InvestmentsParameters investmentsParameters : IConstants.InvestmentsParameters.values()) {
                    requestData.put(investmentsParameters.toString(), toArray(request.get(investmentsParameters.toString()), investmentsParameters.getDefaultValue()));
                }
                break;
            case COMPANY_PROFILE:
                for (IConstants.CompanyProfileParameters companyProfileParameters : IConstants.CompanyProfileParameters.values()) {
                    requestData.put(companyProfileParameters.toString(), toArray(request.get(companyProfileParameters.toString()), companyProfileParameters.getDefaultValue()));
                }
                break;
            case FUND_PROFILE:
                for (IConstants.FundProfileParameters fundProfileParameters : IConstants.FundProfileParameters.values()) {
                    requestData.put(fundProfileParameters.toString(), toArray(request.get(fundProfileParameters.toString()), fundProfileParameters.getDefaultValue()));
                }
                break;
            case BOND_PROFILE:
                for (IConstants.BondProfileParameters bondProfileParameters : IConstants.BondProfileParameters.values()) {
                    requestData.put(bondProfileParameters.toString(), toArray(request.get(bondProfileParameters.toString()), bondProfileParameters.getDefaultValue()));
                }
                break;
            case REPORT_DATA:
                String value;
                for (IConstants.ReportSearchParameters reportParameter : IConstants.ReportSearchParameters.values()) {
                    value = (request.get(reportParameter.toString()) == null) ? reportParameter.getDefaultValue() : request.get(reportParameter.toString());
                    requestData.put(reportParameter.toString(), toArray(request.get(reportParameter.toString()), value));
                }
                break;
            case EARNINGS:
                for (IConstants.EarningsParameters earningsParameters : IConstants.EarningsParameters.values()) {
                    requestData.put(earningsParameters.toString(), toArray(request.get(earningsParameters.toString()), earningsParameters.getDefaultValues()));
                }
                break;
            case INDIVIDUALS_SEARCH:
                for (IConstants.IndividualsSearchParameters individualsSearchParameters : IConstants.IndividualsSearchParameters.values()) {
                    requestData.put(individualsSearchParameters.toString(), toArray(request.get(individualsSearchParameters.toString()), individualsSearchParameters.getDefaultValues()));
                }
                break;
            case INDIVIDUAL_PROFILE:
                for (IConstants.IndividualParameters individualParameters : IConstants.IndividualParameters.values()) {
                    requestData.put(individualParameters.toString(), toArray(request.get(individualParameters.toString()), individualParameters.getDefaultValues()));
                }
                break;
            case COMPANY_MANAGEMENT:
                for (IConstants.CompanyManagementParameters companyManagementParameters : IConstants.CompanyManagementParameters.values()) {
                    requestData.put(companyManagementParameters.toString(), toArray(request.get(companyManagementParameters.toString()), companyManagementParameters.getDefaultValue()));
                }
                break;
            case INDIVIDUAL_OWNSHP_HISTRY:
                for (IConstants.IndividualParameters individualParameters : IConstants.IndividualParameters.values()) {
                    requestData.put(individualParameters.toString(), toArray(request.get(individualParameters.toString()), individualParameters.getDefaultValues()));
                }
                break;
            case COMPANY_OWNERS:
                for (IConstants.CompanyOwnersParameters companyOwnersParameters : IConstants.CompanyOwnersParameters.values()) {
                    requestData.put(companyOwnersParameters.toString(), toArray(request.get(companyOwnersParameters.toString()), companyOwnersParameters.getDefaultValue()));
                }
                break;
            case MACRO_ECONOMY:
                for (IConstants.MacroEconomyParameters macroEconomyParameters : IConstants.MacroEconomyParameters.values()) {
                    requestData.put(macroEconomyParameters.toString(), toArray(request.get(macroEconomyParameters.toString()), macroEconomyParameters.getDefaultValue()));
                }
                break;
            case RELATED_INSTRUMENTS:
                for (IConstants.RelatedInstrumentParameters relatedInstrumentParameters : IConstants.RelatedInstrumentParameters.values()) {
                    requestData.put(relatedInstrumentParameters.toString(), toArray(request.get(relatedInstrumentParameters.toString()), relatedInstrumentParameters.getDefaultValue()));
                }
                break;
            case PEER_COMPANY:
                for (IConstants.PeerCompaniesParameters peerCompaniesParameters : IConstants.PeerCompaniesParameters.values()) {
                    requestData.put(peerCompaniesParameters.toString(), toArray(request.get(peerCompaniesParameters.toString()), peerCompaniesParameters.getDefaultValues()));
                }
                break;
            case COMPANY_LIST:
                for (IConstants.CompaniesListingParameters companiesListingParameters : IConstants.CompaniesListingParameters.values()) {
                    requestData.put(companiesListingParameters.toString(), toArray(request.get(companiesListingParameters.toString()), companiesListingParameters.getDefaultValues()));
                }
                break;
            case CITY_LIST:
                for (IConstants.CityListParameters cityListParameters : IConstants.CityListParameters.values()) {
                    requestData.put(cityListParameters.toString(), toArray(request.get(cityListParameters.toString()), cityListParameters.getDefaultValues()));
                }
                break;
            case MULTIMEDIA:
                for (IConstants.MultimediaParameters multimediaParameters : IConstants.MultimediaParameters.values()) {
                    requestData.put(multimediaParameters.toString(), toArray(request.get(multimediaParameters.toString()), multimediaParameters.getDefaultValues()));
                }
                break;
            case SYMBOL_SEARCH:
                for (IConstants.SymbolSearchParameters symbolSearchParameters : IConstants.SymbolSearchParameters.values()) {
                    requestData.put(symbolSearchParameters.toString(), toArray(request.get(symbolSearchParameters.toString()), symbolSearchParameters.getDefaultValue()));
                }
                break;
            case TECHNICAL_SCANNER:
                for (IConstants.TechnicalScannerParameters technicalScannerParameters : IConstants.TechnicalScannerParameters.values()) {
                    requestData.put(technicalScannerParameters.toString(), toArray(request.get(technicalScannerParameters.toString()), technicalScannerParameters.getDefaultValue()));
                }
                break;
            case TREASURY_BILLS:
                for (IConstants.TreasuryBillsParameters treasuryBillsParameters : IConstants.TreasuryBillsParameters.values()) {
                    requestData.put(treasuryBillsParameters.toString(), toArray(request.get(treasuryBillsParameters.toString()), treasuryBillsParameters.getDefaultValues()));
                }
                break;
            case ANNOUNCEMENT_DETAILS:
                for (IConstants.AnnouncementDetailsParameters announcementDetailsParameters : IConstants.AnnouncementDetailsParameters.values()) {
                    requestData.put(announcementDetailsParameters.toString(), toArray(request.get(announcementDetailsParameters.toString()), announcementDetailsParameters.getDefaultValue()));
                }
                break;
            case FUND_SCREENER:
                for (IConstants.FundamentalScreenerParameters fundamentalScreenerParameters : IConstants.FundamentalScreenerParameters.values()) {
                    requestData.put(fundamentalScreenerParameters.toString(), toArray(request.get(fundamentalScreenerParameters.toString()), fundamentalScreenerParameters.getDefaultValues()));
                }
                break;
            case UPDATE_NOTIFICATION:
                for (IConstants.NotificationParameters notificationParameters : IConstants.NotificationParameters.values()) {
                    requestData.put(notificationParameters.toString(), toArray(request.get(notificationParameters.toString()), notificationParameters.getDefaultValue()));
                }
                break;
            case FUND_POSITION:
                for (IConstants.FundPositionParameters fundPositionParameters : IConstants.FundPositionParameters.values()) {
                    requestData.put(fundPositionParameters.toString(), toArray(request.get(fundPositionParameters.toString()), fundPositionParameters.getDefaultValue()));
                }
                break;
            case MULTIMEDIA_PROVIDERS:
                for (IConstants.MultimediaProviders providerParameters : IConstants.MultimediaProviders.values()) {
                    requestData.put(providerParameters.toString(), toArray(request.get(providerParameters.toString()), providerParameters.getDefaultValues()));
                }
                break;
            case COUPON_FREQUENCY:
                for (IConstants.CouponFrequency cFParameters : IConstants.CouponFrequency.values()) {
                    requestData.put(cFParameters.toString(), toArray(request.get(cFParameters.toString()), cFParameters.getDefaultValues()));
                }
                break;
            case MARKET_HIGHLOW_CHART:
                for (IConstants.MarketHighLowChart providerParameters : IConstants.MarketHighLowChart.values()) {
                    requestData.put(providerParameters.toString(), toArray(request.get(providerParameters.toString()), providerParameters.getDefaultValues()));
                }
                break;
            case FUND_ACTION:
                for(IConstants.FundAction fundAction: IConstants.FundAction.values()){
                    requestData.put(fundAction.toString(), toArray(request.get(fundAction.toString()), fundAction.getDefaultValues()));
                }
                break;
            case UPDATE_FREQ:
                for(IConstants.InvestorUpdateFreq investorUpdateFreq: IConstants.InvestorUpdateFreq.values()){
                    requestData.put(investorUpdateFreq.toString(), toArray(request.get(investorUpdateFreq.toString()), investorUpdateFreq.getDefaultValues()));
                }
                break;
            case INVESTOR_TYPES:
                for(IConstants.InvestorTypes investorTypes: IConstants.InvestorTypes.values()){
                    requestData.put(investorTypes.toString(), toArray(request.get(investorTypes.toString()), investorTypes.getDefaultValues()));
                }
                break;
            case INVESTOR_NATIONALITIES:
                for(IConstants.InvestorNationalities investorNationalities: IConstants.InvestorNationalities.values()){
                    requestData.put(investorNationalities.toString(), toArray(request.get(investorNationalities.toString()), investorNationalities.getDefaultValues()));
                }
                break;
            case NEWS_TOP:
                for(IConstants.TopNewsParameters topNewsParameters: IConstants.TopNewsParameters.values()){
                    requestData.put(topNewsParameters.toString(), toArray(request.get(topNewsParameters.toString()), topNewsParameters.getDefaultValue()));
                }
                break;
            case FUND_ELIGIBILITY:
                for(IConstants.FundEligibility fundEligibility: IConstants.FundEligibility.values()){
                    requestData.put(fundEligibility.toString(), toArray(request.get(fundEligibility.toString()), fundEligibility.getDefaultValues()));
                }
                break;       
            case FUND_NAV_FREQ:
                for(IConstants.FundNavFreq fundNavFreq: IConstants.FundNavFreq.values()){
                    requestData.put(fundNavFreq.toString(), toArray(request.get(fundNavFreq.toString()), fundNavFreq.getDefaultValues()));
                }
                break;
            case MULTI_SYMBOL_HISTORY:
                for(IConstants.MultiSymbolHistoryRequestParameters multiSymbolParameters: IConstants.MultiSymbolHistoryRequestParameters.values()){
                    requestData.put(multiSymbolParameters.toString(), toArray(request.get(multiSymbolParameters.toString()), multiSymbolParameters.getDefaultValue()));
                }
                break;
            case FINANCIAL_AVG_RATIO:
                for(IConstants.FinancialAverageRatioParameters financialAverageRatioParameters: IConstants.FinancialAverageRatioParameters.values()){
                    requestData.put(financialAverageRatioParameters.toString(), toArray(request.get(financialAverageRatioParameters.toString()), financialAverageRatioParameters.getDefaultValue()));
                }
                break;
            case INDIVIDUAL_DATA_BY_COUNTRY:
                for(IConstants.IndividualDataByCountryParameters individualParameters : IConstants.IndividualDataByCountryParameters.values()){
                    requestData.put(individualParameters.toString(), toArray(request.get(individualParameters.toString()), individualParameters.getDefaultValues()));
                }
                break;
            case KEY_OFFICERS:
                for(IConstants.KeyOfficersParameters keyOfficersParameters : IConstants.KeyOfficersParameters.values()){
                    requestData.put(keyOfficersParameters.toString(), toArray(request.get(keyOfficersParameters.toString()), keyOfficersParameters.getDefaultValue()));
                }
                break;
            default:
                break;
        }

        return ClientRequestParser.handleRequest(requestData);
    }

    protected String[] toArray(String data, String defaultValue) {
        String[] requestData;

        if (data == null) {
            requestData = new String[]{defaultValue};
        } else {
            requestData = new String[]{data};
        }


        return requestData;
    }

}
