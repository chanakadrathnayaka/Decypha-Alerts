package com.dfn.alerts.dataaccess.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 2/11/13
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheConfigs {
    
    private static final int CACHE_TYPES = 8;

    private static final String CACHE_DEFAULT_CONFIG = "defaultCacheConfig";

    private Map<String,CacheConfig> cacheConfigMap = new HashMap<String, CacheConfig>(CACHE_TYPES);

    public CacheConfig getCacheConfig(String configKey){
        CacheConfig cacheConfig = this.cacheConfigMap.get(configKey);

        if(cacheConfig == null){
            cacheConfig = this.cacheConfigMap.get(CACHE_DEFAULT_CONFIG);
        }

        return cacheConfig;
    }

    public void setCacheConfigMap(Map<String, CacheConfig> cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }
}
