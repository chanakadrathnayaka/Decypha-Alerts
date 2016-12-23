package com.dfn.alerts.impl;

import com.dfn.alerts.api.AlertManager;
import com.dfn.alerts.api.IBusinessManager;
import com.dfn.alerts.beans.EarningsAnnouncement;
import com.dfn.alerts.beans.tickers.TickerDTO;
import com.dfn.alerts.constants.CustomResponseKey;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.impl.EarningsAnnouncementDataAccess;
import com.dfn.alerts.dataaccess.impl.MasterDataAccess;
import com.dfn.alerts.dataaccess.impl.TickerDataAccess;
import com.dfn.alerts.dataaccess.orm.impl.PeriodDescriptions;
import com.dfn.alerts.dataaccess.orm.impl.earnings.EarningNotification;
import com.dfn.alerts.utils.CommonUtils;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chathurag
 * On 12/5/2016
 */
public class EarningsAnnouncementManager implements IBusinessManager,AlertManager {

    /**
     * Keep data access object to retrieve earnings data from socket or cache
     */
    private EarningsAnnouncementDataAccess earningsAnnouncementDataAccess;

    private TickerDataAccess tickerDataAccess;

    private MasterDataAccess masterDataAccess;

    /*==========================================Setters=================================================================*/

    public void setEarningsAnnouncementDataAccess(EarningsAnnouncementDataAccess earningsAnnouncementDataAccess) {
        this.earningsAnnouncementDataAccess = earningsAnnouncementDataAccess;
    }

    public void setTickerDataAccess(TickerDataAccess tickerDataAccess) {
        this.tickerDataAccess = tickerDataAccess;
    }

    public void setMasterDataAccess(MasterDataAccess masterDataAccess) {
        this.masterDataAccess = masterDataAccess;
    }

    /*=================================Implement Methods For IBusinessManager===========================================*/

    /**
     * Method to get earnings model data
     *
     * @param request         request
     * @param isJasonResponse true if response type is JSON
     * @return earnings
     */
    public Object getData(Object request, boolean isJasonResponse) {

        Map<String, String> requestData = (Map<String, String>) request;
        EarningsAnnouncement earnings;
        boolean isDirectData = false;
        List<String> tickerSerials;
        Map<String, TickerDTO> tickerDTOMap = null;
        Gson gson = new Gson();
//        Map<String, Map> exchangeDesMap = this.masterDataAccess.getExchangeDes();
        Map<String, Map> exchangeDesMap = new HashMap<>();
//        Map<String, String> displayExchanges = this.masterDataAccess.getDisplayExgList();
        Map<String, String> displayExchanges = new HashMap<>();


        //Only first page of the default request is caching
        if ((requestData.get(IConstants.MIXDataField.PGI) != null && !requestData.get(IConstants.MIXDataField.PGI).equals(IConstants.FIRST_PAGE_INDEX))
                || requestData.get(IConstants.MIXDataField.PGS) != null && !requestData.get(IConstants.MIXDataField.PGS).equals(IConstants.DEFAULT_PAGE_SIZE)) {
            isDirectData = true;
        }

        String language = requestData.get(IConstants.MIXDataField.L);
        earnings = (EarningsAnnouncement) this.earningsAnnouncementDataAccess.getData(requestData, isDirectData, isJasonResponse);

        if (earnings != null && earnings.getEarningsAnnouncements() != null && !earnings.getEarningsAnnouncements().isEmpty()) {
            tickerSerials = new ArrayList<String>();
            for (Map<String, String> earning : earnings.getEarningsAnnouncements()) {
                String tS = earning.get(CustomResponseKey.TICKER_SERIAL);
                if (tS != null && !tS.trim().isEmpty() && !tickerSerials.contains(tS)) {
                    tickerSerials.add(tS.trim());
                }
            }

            if (!tickerSerials.isEmpty()) {
                tickerDTOMap = this.tickerDataAccess.getEquityTickersFromTickerSerial(tickerSerials);
            }

            List<Map<String, String>> updatedDataList = new ArrayList<Map<String, String>>();
            for (Map<String, String> event : earnings.getEarningsAnnouncements()) {
                String tickerSerial;
                String exchange;
                TickerDTO ticker;
                if (event.get(CustomResponseKey.TICKER_SERIAL) != null && !(event.get(CustomResponseKey.TICKER_SERIAL)).trim().isEmpty() && tickerDTOMap != null) {
                    tickerSerial = (event.get(CustomResponseKey.TICKER_SERIAL)).trim();

                    ticker = tickerDTOMap.get(tickerSerial);

                    if (ticker != null) {

                        if (ticker.getTickerShortDescriptions() != null) {
                            event.put(CustomResponseKey.SYMBOL_DES, gson.toJson(ticker.getTickerShortDescriptions()));
                        }

                        if (ticker.getTickerLongDescriptions() != null) {
                            event.put(CustomResponseKey.SYMBOL_LONG_DES, gson.toJson(ticker.getTickerLongDescriptions()));
                        }

                        if (ticker.getTickerLongDescriptions() != null) {
                            event.put(CustomResponseKey.COMP_NAME, ticker.getCompanyLangDTOMap().get(language).getCompanyName());
                        }

                        String currency = !CommonUtils.isNullOrEmptyString(ticker.getDefaultCurrencyId()) ? ticker.getDefaultCurrencyId() :
                                !CommonUtils.isNullOrEmptyString(ticker.getCurrencyId()) ? ticker.getCurrencyId() : IConstants.USD;
                        if (!CommonUtils.isNullOrEmptyString(currency)) {
                            event.put(CustomResponseKey.CURRENCY, currency);
                        }

                        if (ticker.getPer() != null) {
                            event.put(CustomResponseKey.PER, Double.toString(ticker.getPer()));
                        }
                    }
                }
                if (event.get(CustomResponseKey.EXCHANGE) != null && !(event.get(CustomResponseKey.EXCHANGE)).trim().isEmpty()) {
                    exchange = (event.get(CustomResponseKey.EXCHANGE)).trim();
                    if (exchangeDesMap != null && displayExchanges != null) {
                        event.put(CustomResponseKey.EXCHANGE_DES, gson.toJson(exchangeDesMap.get(exchange)));
                        event.put(CustomResponseKey.DISPLAY_EXCHANGE, displayExchanges.get(exchange));
                    }
                }

                updatedDataList.add(event);
            }

            earnings.setEarningsAnnouncements(updatedDataList);
        }

        return earnings;
    }

    /*=================================Implement Methods For ICacheUpdateListener======================================*/

    /**
     * Method to delete earnings caches
     *
     * @param notifications notifications
     * @return status
     */
    public int deleteCache(Object notifications) {
        return this.earningsAnnouncementDataAccess.deleteCache(notifications);

    }

    public int updateCache() {
        return 0;
    }

    public void initCache() {
        //

    }

    /**
     * formatMarketDescription
     *
     * @param exchangeDesc    exchange desc
     * @param displayExchange display exchange
     * @param language        language
     * @return market desc
     */
    private String formatMarketDescription(String exchangeDesc, String displayExchange, String language) {
        String market = displayExchange;
        JSONObject exchangeObject;

        if (!CommonUtils.isNullOrEmptyString(exchangeDesc)) {
            exchangeObject = JSONObject.fromObject(exchangeDesc);
            if (exchangeObject.get(language.toUpperCase()) != null) {
                market = exchangeObject.get(language.toUpperCase()).toString();
            }
        }

        return market;
    }

    /**
     * formatSymbolDescription
     *
     * @param symbol   symbol
     * @param symDesc  symbol desc
     * @param language language
     * @return symbol desc
     */
    private String formatSymbolDescription(String symbol, String symDesc, String language) {
        String symbolDesc = symbol;
        JSONObject symbolDescObject;

        if (!CommonUtils.isNullOrEmptyString(symDesc)) {
            symbolDescObject = JSONObject.fromObject(symDesc);
            if (symbolDescObject.get(language.toUpperCase()) != null) {
                symbolDesc = symbolDescObject.get(language.toUpperCase()).toString();
            }
        }

        return symbolDesc;
    }

    /**
     * formatPeriodDescription
     *
     * @param periodDescriptionsMap periodDescriptionsMap
     * @param periodId              periodId
     * @param lang                  language
     * @return period description
     */
    private String formatPeriodDescription(Map<String, PeriodDescriptions> periodDescriptionsMap, String periodId, String lang) {
        String periodDesc = periodId;
        if (!periodDescriptionsMap.isEmpty() && periodDescriptionsMap.containsKey(periodId)) {
            periodDesc = periodDescriptionsMap.get(periodId).getDescription().get(lang.toUpperCase());
        }
        return periodDesc;
    }
    @Override
    public int saveAlerts(Object data) {

        EarningNotification announcement = new EarningNotification();
        announcement.setAlertId(5);
        announcement.setStatus(1);
        announcement.setAlertText("3 Alert");

        List<EarningNotification> earningNotifications = new ArrayList<>();
        earningNotifications.add(announcement);
        earningsAnnouncementDataAccess.insertAlerts(earningNotifications);

        return 0;
    }

    @Override
    public Object searchAlerts() {
        return null;
    }
}
