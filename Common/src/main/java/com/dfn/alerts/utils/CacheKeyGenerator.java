package com.dfn.alerts.utils;


import com.dfn.alerts.constants.CacheKeyConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/24/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public final class CacheKeyGenerator {
    
    private static final String DELAYED_INDICATOR  = "D";
    private static final String SNAPSHOT_INDICATOR = "SNAP";

    private CacheKeyGenerator(){}
 
    public static String getSnapshotCacheKey(String symbol,String exchange,boolean isDelayed){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_SNAPSHOT );
        if(isDelayed){
        sb.append(DELAYED_INDICATOR);
        }
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(symbol);


        return sb.toString();
    }

    public static String getMarketDataCacheKey(String exchange){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_MARKET_DATA);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);

        return sb.toString();
    }

    public static String getMarketSnapshotCacheKey(String exchange,boolean isDelayed){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_MARKET_DATA);
        if(isDelayed){
            sb.append(DELAYED_INDICATOR);
        }
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(SNAPSHOT_INDICATOR);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);

        return sb.toString();
    }


    public static String getIndexCacheKey(String index , String exchange ,boolean isDelayed){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_INDEX);
        if(isDelayed){
            sb.append(DELAYED_INDICATOR);
        }
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(index);

        return sb.toString();
    }
    


    public static String getCurrencyCacheKey(String symbol,String exchange,boolean isDelayed){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_SNAPSHOT );
        if(isDelayed){
            sb.append(DELAYED_INDICATOR);
        }
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(symbol);


        return sb.toString();
    }
    public static String getCommodityCacheKey(String symbol,String exchange,boolean isDelayed){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_SNAPSHOT );
        if(isDelayed){
            sb.append(DELAYED_INDICATOR);
        }
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(symbol);


        return sb.toString();
    }


    public static String getTickerDataCacheKey(String symbol, String exchange){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_TICKER_DATA);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(symbol);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(exchange);

        return sb.toString();
    }


    public static String getCompanyDataCacheKey(String companyID){

        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_COMPANY_DATA);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(companyID);

        return sb.toString();
    }  


    /* get cache key for individual snapshot object
    * */
    public static String getIndividualSnapshotCacheKey(String countryList , String language )
    {
        StringBuilder sb = new StringBuilder(CacheKeyConstant.CACHE_KEY_PREFIX_INDIVIDUAL_SNAP);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(countryList);
        sb.append(CacheKeyConstant.UNDERSCORE_SIGN);
        sb.append(language);
        return sb.toString();
    }


    
}
