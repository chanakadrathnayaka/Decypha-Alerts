package com.dfn.alerts.beans;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 3/25/14
 * Time: 3:56 PM
 */
@SuppressWarnings("unused")
public class MarketFundInvestmentsDTO implements Serializable{

    private static final Logger log = LogManager.getLogger(MarketFundInvestmentsDTO.class);

    private Map<String, Map<String, String>> stockInvestments;

    private Map<String, List<String>> segmentedByCountry;
    private Map<String, List<String>> segmentedBySector;
    private Map<String, List<String>> segmentedByIndustry;

    private Map<String, String> countryDescriptions;
    private Map<String, String> industryDescriptions;
    private Map<String, String> sectorDescriptions;

    public MarketFundInvestmentsDTO() {
        stockInvestments = new HashMap<String, Map<String, String>>(5);
        segmentedByCountry = new HashMap<String, List<String>>(5);
        segmentedBySector = new HashMap<String, List<String>>(5);
        segmentedByIndustry = new HashMap<String, List<String>>(5);

        countryDescriptions = new HashMap<String,String>(5);
        industryDescriptions = new HashMap<String, String>(5);
        sectorDescriptions = new HashMap<String, String>(5);
    }

    public Map<String, Map<String, String>> getStockInvestments() {
        return stockInvestments;
    }

    public Map<String, List<String>> getSegmentedByCountry() {
        return segmentedByCountry;
    }

    public Map<String, List<String>> getSegmentedBySector() {
        return segmentedBySector;
    }

    public Map<String, List<String>> getSegmentedByIndustry() {
        return segmentedByIndustry;
    }

    public Map<String, String> getCountryDescriptions() {
        return countryDescriptions;
    }

    public Map<String, String> getIndustryDescriptions() {
        return industryDescriptions;
    }

    public Map<String, String> getSectorDescriptions() {
        return sectorDescriptions;
    }

    public boolean addStockInvestment(Map<String, String> stockInvestment, String language){
        String industry = stockInvestment.get(IConstants.MarketFundInvestments.GICS_L3_CODE);
        String country = stockInvestment.get(IConstants.MarketFundInvestments.COUNTRY_CODE);
        String sector = stockInvestment.get(IConstants.MarketFundInvestments.SECTOR_ID);

        String allocationId = stockInvestment.get(IConstants.MarketFundInvestments.ALLOCATION_ID);

        String fundName = stockInvestment.get(IConstants.MarketFundInvestments.FUND_NAME);
        if(fundName != null && !fundName.isEmpty()){
            stockInvestment.put(IConstants.MarketFundInvestments.FUND_NAME, CommonUtils.getLanguageDescriptionMap(fundName).get(language));
        }

        String symbolName = stockInvestment.get(IConstants.MarketFundInvestments.TICKER_DESCRIPTION);
        if(symbolName != null && !symbolName.isEmpty()){
            stockInvestment.put(IConstants.MarketFundInvestments.TICKER_DESCRIPTION, CommonUtils.getLanguageDescriptionMap(symbolName).get(language));
        }

        if(country != null && !country.isEmpty()){
            if(!segmentedByCountry.containsKey(country)){
                segmentedByCountry.put(country, new ArrayList<String>(5));

                String countryDesc = stockInvestment.get(IConstants.MarketFundInvestments.COUNTRY_DESC);
                if(countryDesc != null && !countryDesc.isEmpty()){
                    String countryDescLang = CommonUtils.getLanguageDescriptionMap(countryDesc).get(language);
                    if(countryDescLang != null && !countryDescLang.isEmpty()){
                        countryDescriptions.put(country, countryDescLang);
                    }
                }
            }
            segmentedByCountry.get(country).add(allocationId);
        }

        if(industry != null && !industry.isEmpty()){
            industry = industry.substring(0,6); //GICSL4 to L3
            if(!segmentedByIndustry.containsKey(industry)){
                segmentedByIndustry.put(industry, new ArrayList<String>(5));

                String industryDesc = stockInvestment.get(IConstants.MarketFundInvestments.GICS_L3);
                if(industryDesc != null && !industryDesc.isEmpty()){
                    String industryDescLang = CommonUtils.getLanguageDescriptionMap(industryDesc).get(language);
                    if(industryDescLang != null && !industryDescLang.isEmpty()){
                        industryDescriptions.put(industry, industryDescLang);
                    }
                }
            }
            segmentedByIndustry.get(industry).add(allocationId);
        }

        if(sector != null){
            if(!segmentedBySector.containsKey(sector)){
                segmentedBySector.put(sector, new ArrayList<String>(5));

                String sectorDesc = stockInvestment.get(IConstants.MarketFundInvestments.SECTOR_NAME);
                if(sectorDesc != null && !sectorDesc.isEmpty()){
                    String sectorDescLang = CommonUtils.getLanguageDescriptionMap(sectorDesc).get(language);
                    if(sectorDescLang != null && !sectorDescLang.isEmpty()){
                        sectorDescriptions.put(sector, sectorDescLang);
                    }
                }
            }
            segmentedBySector.get(sector).add(allocationId);
        }

        stockInvestments.put(allocationId, stockInvestment);

        return true;
    }

}
