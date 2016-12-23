package com.dfn.alerts.beans;

import com.dfn.alerts.constants.IConstants;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 10/24/13
 * Time: 5:20 PM
 */
@SuppressWarnings("unused")
public class FundInvestment implements Serializable{

    /**
     * data object for progress charts
     * see HighCharts
     */
    class ChartData implements Serializable{
        private String name;
        private Double[] data;

        ChartData(String name, Double[] data) {

            Double[] dataCopy = new Double[data.length];
            System.arraycopy(data, 0, dataCopy, 0, data.length);

            this.name = name;
            this.data = dataCopy;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        Double[] getData() {
            return data;
        }

        void setData(Double[] data) {
            Double[] dataCopy = new Double[data.length];
            System.arraycopy(data, 0, dataCopy, 0, data.length);
            this.data = dataCopy;
        }
    }

    /**
     * label key for other
     */
    private static final String labelOther = "label.other";

    /**
     * current stock allocation
     * see HighCharts pie
     */
    private List<Object[]> currentStockAllocation = new ArrayList<Object[]>();

    /**
     * raw data set for stock allocation
     */
    private Map<String, List> stockAllocationProgress = new LinkedHashMap<String, List>();

    /**
     * stock allocation progress map
     */
    private Map<String, Map<String, Double>> stockAllocationProgressMap = new LinkedHashMap<String, Map<String, Double>>();

    /**
     * current asset allocation
     * see HighCharts pie
     */
    private List<Object[]> currentAssetAllocation = new ArrayList<Object[]>();

    /**
     * raw data set for asset allocation
     */
    private Map<String, List> assetAllocationProgress = new LinkedHashMap<String, List>();

    /**
     * asset allocation progress map
     */
    private Map<String, Map<String, Double>> assetAllocationProgressMap = new LinkedHashMap<String, Map<String, Double>>();

    /**
     * current fund allocation
     * see HighCharts pie
     */
    private List<Object[]> currentFundAllocation = new ArrayList<Object[]>();

    /**
     * raw data set for fund allocation
     */
    private Map<String, List> fundAllocationProgress = new LinkedHashMap<String, List>();

    /**
     * fund allocation progress map
     */
    private Map<String, Map<String, Double>> fundAllocationProgressMap = new LinkedHashMap<String, Map<String, Double>>();

    /**
     * current commodity allocation
     * see HighCharts pie
     */
    private List<Object[]> currentCommodityAllocation = new ArrayList<Object[]>();

    /**
     * raw data set for commodity allocation
     */
    private Map<String, List> commodityAllocationProgress = new LinkedHashMap<String, List>();

    /**
     * commodity allocation progress map
     */
    private Map<String, Map<String, Double>> commodityAllocationProgressMap = new LinkedHashMap<String, Map<String, Double>>();

    /**
     * current sector allocation
     * see HighCharts pie
     */
    private List<Object[]> currentSectorAllocation = new ArrayList<Object[]>();

    /**
     * raw data set for sector allocation
     */
    private Map<String, List> sectorAllocationProgress = new LinkedHashMap<String, List>();

    /**
     * sector allocation progress map
     */
    private Map<String, Map<String, Double>> sectorAllocationProgressMap = new LinkedHashMap<String, Map<String, Double>>();

    /**
     * current country allocation
     * see HighCharts pie
     */
    private List<Object[]> currentCountryAllocation = new ArrayList<Object[]>();

    /**
     * raw data set for country allocation
     */
    private Map<String, List> countryAllocationProgress = new LinkedHashMap<String, List>();

    /**
     * country allocation progress map
     */
    private Map<String, Map<String, Double>> countryAllocationProgressMap = new LinkedHashMap<String, Map<String, Double>>();

    /**
     * used for country map
     * see HighCharts GeoMap
     */
    private Map<String, Object[]> allCountryAllocation = new HashMap<String, Object[]>();

    /**
     * sum of all country investments
     */
    private double sumCountryInvestments = 0;

    /**
     * latest updated(TXN) date
     */
    private String lastUpdatedDate;

    /**
     * latest updated(TXN) date for stocks
     */
    private String stkLastUpdatedDate = null;

    /**
     * latest updated(TXN) date for assets
     */
    private String astLastUpdatedDate = null;

    /**
     * latest updated(TXN) date for funds
     */
    private String fundLastUpdatedDate = null;

    /**
     * latest updated(TXN) date for commodities
     */
    private String commodityLastUpdatedDate = null;

    /**
     * latest updated(TXN) date for sectors
     */
    private String sectorLastUpdatedDate = null;

    /**
     * latest updated(TXN) date for country
     */
    private String countryLastUpdatedDate = null;

    /**
     * fact sheet id for latest updated(TXN)
     */
    private int fileId;

    /**
     * file id with language for latest updated(TXN)
     */
    private String fileGuids;

    /**
     * set allocation data to investment object
     * allocations grouped by TXN sequence & asset type in asc order
     * fills fund, commodity, sector, country allocation from the data for latest TXN
     * fills progress & latest allocation(for latest TXN) for stocks & assets
     * @param data Map<Integer, Map<Integer, List<FundInvestmentAllocationDTO>>>
     */
    public void setData(Map<Integer, Map<Integer, List<FundInvestmentAllocationDTO>>> data) {
        if(data != null){
            int i = 0;
            for(int sequence : data.keySet()){
                Map<Integer, List<FundInvestmentAllocationDTO>> allocations = data.get(sequence);
                boolean isLatest = (i == data.size() - 1);

                if(allocations.containsKey(IConstants.InvestmentType.STOCK.getTypeId())){
                    fillCurrentAllocationWithProgress(allocations.get(IConstants.InvestmentType.STOCK.getTypeId()), IConstants.InvestmentType.STOCK, isLatest);
                }

                if(allocations.containsKey(IConstants.InvestmentType.ASSET.getTypeId())){
                    fillCurrentAllocationWithProgress(allocations.get(IConstants.InvestmentType.ASSET.getTypeId()), IConstants.InvestmentType.ASSET, isLatest);
                }

                if(allocations.containsKey(IConstants.InvestmentType.FUNDS.getTypeId())){

                    fillCurrentAllocationWithProgress(allocations.get(IConstants.InvestmentType.FUNDS.getTypeId()), IConstants.InvestmentType.FUNDS, isLatest);
                }

                if(allocations.containsKey(IConstants.InvestmentType.COMMODITIES.getTypeId())){
                    fillCurrentAllocationWithProgress(allocations.get(IConstants.InvestmentType.COMMODITIES.getTypeId()), IConstants.InvestmentType.COMMODITIES, isLatest);
                }

                if(allocations.containsKey(IConstants.InvestmentType.SECTOR.getTypeId())){
                    fillCurrentAllocationWithProgress(allocations.get(IConstants.InvestmentType.SECTOR.getTypeId()), IConstants.InvestmentType.SECTOR, isLatest);
                }

                if(allocations.containsKey(IConstants.InvestmentType.COUNTRY.getTypeId())){
                    fillCurrentAllocationWithProgress(allocations.get(IConstants.InvestmentType.COUNTRY.getTypeId()), IConstants.InvestmentType.COUNTRY, isLatest);
                }
                i++;
            }

            setAllocationProgress(stockAllocationProgressMap, stockAllocationProgress, stkLastUpdatedDate);//stock
            setAllocationProgress(assetAllocationProgressMap, assetAllocationProgress, astLastUpdatedDate);//asset
            setAllocationProgress(fundAllocationProgressMap, fundAllocationProgress, fundLastUpdatedDate);//fund
            setAllocationProgress(commodityAllocationProgressMap, commodityAllocationProgress, commodityLastUpdatedDate);//commodity
            setAllocationProgress(sectorAllocationProgressMap, sectorAllocationProgress, sectorLastUpdatedDate);//sector
            setAllocationProgress(countryAllocationProgressMap, countryAllocationProgress, countryLastUpdatedDate);//country
        }
    }

    public List<Object[]> getCurrentStockAllocation(){
         return currentStockAllocation;
    }

    public Map<String, List> getStockAllocationProgress() {
        return stockAllocationProgress;
    }

    private void setAllocationProgress(Map<String, Map<String, Double>> allocationProgressMap,
                                      Map<String, List> allocationProgress, String lastUpdatedDate){
        List<String> keyList = new ArrayList<String>(allocationProgressMap.size());
        List<ChartData> chartDataList = new ArrayList<ChartData>(allocationProgressMap.size());

        if(lastUpdatedDate != null){
            Map<String, Double[]> values = new LinkedHashMap<String, Double[]>();
            boolean addOthers = false;

            int size = allocationProgressMap.keySet().size();

            Map<String, Double> mm = allocationProgressMap.get(lastUpdatedDate);

            for(String k : mm.keySet()){
                values.put(k, getDefaultArray(size));
            }
            values.put(labelOther, getDefaultArray(size));

            int i = 0;
            for(String key : allocationProgressMap.keySet()){
                keyList.add(key);
                Map<String, Double> m = allocationProgressMap.get(key);
                double sumOthers = 1;
                for(String k : m.keySet()){
                    if(values.containsKey(k)){
                        values.get(k)[i] = m.get(k);
                        sumOthers -= m.get(k);
                    }
                }
                addOthers = !(!addOthers && sumOthers == 0);
                values.get(labelOther)[i] = sumOthers <= 0 ? 0 : sumOthers;
                i++;
            }
            if(!addOthers){
                values.remove(labelOther);
            }
            for(String k : values.keySet()){
                chartDataList.add(new ChartData(k, values.get(k)));
            }
        }
        allocationProgress.put(IConstants.categories, keyList);
        allocationProgress.put(IConstants.data, chartDataList);
    }

    /**
     * util method to create array with value 0 for given size
     * @param size size of array
     * @return array
     */
    private Double[] getDefaultArray(int size){
        Double[] array = new Double[size];
        for(int i = 0; i < size; i++){
            array[i] = 0d;
        }
        return array;
    }

    public List<Object[]> getCurrentAssetAllocation() {
        return currentAssetAllocation;
    }

    public Map<String, List> getAssetAllocationProgress() {
        return assetAllocationProgress;
    }

    public List<Object[]> getCurrentFundAllocation() {
        return currentFundAllocation;
    }

    public List<Object[]> getCurrentCommodityAllocation() {
        return currentCommodityAllocation;
    }

    public List<Object[]> getCurrentSectorAllocation() {
        return currentSectorAllocation;
    }

    public List<Object[]> getCurrentCountryAllocation() {
        return currentCountryAllocation;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public int getFileId() {
        return fileId;
    }

    public String getFileGuids() {
        return fileGuids;
    }

    /**
     * fill allocation data for STOCK, ASSET
     * @param allocationDTOList data
     * @param type InvestmentType
     * @param isLatest latest
     */
    private void fillCurrentAllocationWithProgress(List<FundInvestmentAllocationDTO> allocationDTOList, IConstants.InvestmentType type, boolean isLatest){
        int size = allocationDTOList.size();
        List<Object[]> allocations = null;
        Map<String, Map<String, Double>> progress = null;
        double sumOthers = 1;
        for(int j = 0; j < size; j++){
            String desc = null;
            FundInvestmentAllocationDTO allocationDTO = allocationDTOList.get(j);
            double value = allocationDTO.getInvestmentPercentage();
            String date = allocationDTO.getTransactionDate();
            switch (type){
                case STOCK:
                    desc = allocationDTO.getInvestedTickerDesc();
                    allocations = currentStockAllocation;
                    progress = stockAllocationProgressMap;
                    stkLastUpdatedDate = date;
                    break;
                case ASSET:
                    desc = allocationDTO.getFundAssetDesc();
                    allocations = currentAssetAllocation;
                    progress = assetAllocationProgressMap;
                    astLastUpdatedDate = date;
                    break;
                case FUNDS:
                    desc = allocationDTO.getInvestedTickerDesc();
                    allocations = currentFundAllocation;
                    progress = fundAllocationProgressMap;
                    fundLastUpdatedDate = date;
                    break;
                case SECTOR:
                    desc = allocationDTO.getInvestedSectorDesc();
                    allocations = currentSectorAllocation;
                    progress = sectorAllocationProgressMap;
                    sectorLastUpdatedDate = date;
                    break;
                case COMMODITIES:
                    desc = allocationDTO.getInvestedTickerDesc();
                    allocations = currentCommodityAllocation;
                    progress = commodityAllocationProgressMap;
                    commodityLastUpdatedDate = date;
                    break;
                case COUNTRY:
                    desc = allocationDTO.getInvestedCountryDesc();
                    allocations = currentCountryAllocation;
                    progress = countryAllocationProgressMap;
                    countryLastUpdatedDate = date;
                    if(isLatest){
                        allCountryAllocation.put(allocationDTO.getInvestedCountryCode(), new Object[]{desc, value});
                        sumCountryInvestments += value;
                    }
                    break;
            }
            if(isLatest){
                if(j < 4){
                    allocations.add(new Object[]{desc, value});
                    sumOthers -= value;
                }
                if(j == size - 1 && sumOthers > 0.000001){//java arithmatic error
                    allocations.add(new Object[]{labelOther, sumOthers});
                    lastUpdatedDate = date;
                    fileId = allocationDTO.getFactSheetId();
                    fileGuids = allocationDTO.getFileGUIDs();
                }
            }
            if(!progress.containsKey(date)){
                progress.put(date, new HashMap<String, Double>());
            }
            progress.get(date).put(desc, value);
        }
    }

    public Map<String, Object[]> getAllCountryAllocation() {
        return allCountryAllocation;
    }

    public double getSumCountryInvestments() {
        return sumCountryInvestments;
    }


    //region NOT USED

    /**
     * set progress data
     * categories are the assets for where the investment data are available in latest TXN
     * all others are added to others
     * uses linked hash map to make sure others is the last element
     * value for others is derived by subtracting the values for known assets from 1(100%)
     */
    public void setAssetAllocationProgress(){
        List<String> keyList = new ArrayList<String>(assetAllocationProgressMap.size());
        List<ChartData> chartDataList = new ArrayList<ChartData>(assetAllocationProgressMap.size());

        if(astLastUpdatedDate != null){
            Map<String, Double[]> values = new LinkedHashMap<String, Double[]>();
            boolean addOthers = false;

            int size = assetAllocationProgressMap.keySet().size();

            Map<String, Double> mm = assetAllocationProgressMap.get(astLastUpdatedDate);

            for(String k : mm.keySet()){
                values.put(k, getDefaultArray(size));
            }
            values.put(labelOther, getDefaultArray(size));

            int i = 0;
            for(String key : assetAllocationProgressMap.keySet()){
                keyList.add(key);
                Map<String, Double> m = assetAllocationProgressMap.get(key);
                double sumOthers = 1;
                for(String asset : m.keySet()){
                    if(values.containsKey(asset)){
                        values.get(asset)[i] = m.get(asset);
                        sumOthers -= m.get(asset);
                    }
                }
                addOthers = !(!addOthers && sumOthers < 0.000001);//java arithmatic error
                values.get(labelOther)[i] = sumOthers;
                i++;
            }
            if(!addOthers){
                values.remove(labelOther);
            }
            for(String k : values.keySet()){
                chartDataList.add(new ChartData(k, values.get(k)));
            }
        }
        assetAllocationProgress.put(IConstants.categories, keyList);
        assetAllocationProgress.put(IConstants.data, chartDataList);
    }

    /**
     * set progress data
     * categories are the stocks for where the investment data are available in latest TXN
     * all others are added to others
     * uses linked hash map to make sure others is the last element
     * value for others is derived by subtracting the values for known stocks from 1(100%)
     */
    public void setStockAllocationProgress(){
        List<String> keyList = new ArrayList<String>(stockAllocationProgressMap.size());
        List<ChartData> chartDataList = new ArrayList<ChartData>(stockAllocationProgressMap.size());

        if(stkLastUpdatedDate != null){
            Map<String, Double[]> values = new LinkedHashMap<String, Double[]>();
            int size = stockAllocationProgressMap.keySet().size();
            boolean addOthers = false;

            Map<String, Double> mm = stockAllocationProgressMap.get(stkLastUpdatedDate);

            for(String k : mm.keySet()){
                values.put(k, getDefaultArray(size));
            }
            values.put(labelOther, getDefaultArray(size));

            int i = 0;
            for(String key : stockAllocationProgressMap.keySet()){
                keyList.add(key);
                Map<String, Double> m = stockAllocationProgressMap.get(key);
                double sumOthers = 1;
                for(String sym : m.keySet()){
                    if(values.containsKey(sym)){
                        values.get(sym)[i] = m.get(sym);
                        sumOthers -= m.get(sym);
                    }
                }
                addOthers = !(!addOthers && sumOthers == 0);
                values.get(labelOther)[i] = sumOthers;
                i++;
            }
            if(!addOthers){
                values.remove(labelOther);
            }
            for(String k : values.keySet()){
                chartDataList.add(new ChartData(k, values.get(k)));
            }
        }

        stockAllocationProgress.put(IConstants.categories, keyList);
        stockAllocationProgress.put(IConstants.data, chartDataList);
    }

    /**
     * fill current allocation data for FUNDS, COMMODITIES, SECTOR, COUNTRY
     * @param allocationDTOList allocation data
     * @param type InvestmentType
     */
    private void  fillCurrentAllocationData(List<FundInvestmentAllocationDTO> allocationDTOList, IConstants.InvestmentType type){
        int size = allocationDTOList.size();
        List<Object[]> allocations = null;
        double sumOthers = 1;
        for(int j = 0; j < size; j++){
            String desc = null;
            FundInvestmentAllocationDTO allocationDTO = allocationDTOList.get(j);
            double value = allocationDTO.getInvestmentPercentage();
            switch (type){
                case FUNDS:
                    desc = allocationDTO.getInvestedTickerDesc();
                    allocations = currentFundAllocation;
                    break;
                case COMMODITIES:
                    desc = allocationDTO.getInvestedTickerDesc();
                    allocations = currentCommodityAllocation;
                    break;
                case SECTOR:
                    desc = allocationDTO.getInvestedSectorDesc();
                    allocations = currentSectorAllocation;
                    break;
                case COUNTRY:
                    desc = allocationDTO.getInvestedCountryDesc();
                    allocations = currentCountryAllocation;
                    allCountryAllocation.put(allocationDTO.getInvestedCountryCode(), new Object[]{desc, value});
                    sumCountryInvestments += value;
                    break;
            }
            if(j < 4){
                allocations.add(new Object[]{desc, value});
                sumOthers -= value;
            }
            if(j == size - 1){
                allocations.add(new Object[]{labelOther, sumOthers});
                lastUpdatedDate = allocationDTO.getTransactionDate();
                fileId = allocationDTO.getFactSheetId();
                fileGuids = allocationDTO.getFileGUIDs();
            }
        }
    }

    //#endregion
}
