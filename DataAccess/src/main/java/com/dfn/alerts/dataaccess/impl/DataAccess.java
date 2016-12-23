package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.beans.DataAccessRequestDTO;
import com.dfn.alerts.dataaccess.api.ICacheManager;
import com.dfn.alerts.dataaccess.api.IHttp;
import com.dfn.alerts.dataaccess.api.IMultipleSocket;
import com.dfn.alerts.dataaccess.api.ISocket;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.config.CacheConfig;
import com.dfn.alerts.dataaccess.dao.impl.DAOFactory;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/26/12
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataAccess {
    protected ICacheManager cacheManager;
    protected ISocket socketManager;
    protected IMultipleSocket multipleSocketManager;
    protected DAOFactory daoFactory;
    protected ICacheManager memoryCache;
    protected IHttp httpConnectionManager;
    protected String cacheConfigKey = "defaultCacheConfig";

    protected Boolean isCacheEnabled = true;

    public void putData(String key, Object data, int timeToLiveSeconds) {
        cacheManager.put(key, data, timeToLiveSeconds);
    }

    /* Abstract method to take the data directly from in memory cache instead of default cache.
    * This is using when we need to cache a set of frequently using data in a single object */
    public abstract Object getMemoryData(Map<String, String> requestData);

    /* Put frequently used to data into in memory cache */
    public void putMemoryData(Map<String, String> requestData, Object data) {
        memoryCache.put(generateCacheKey(requestData), data);
    }

    /* Abstract method to generate the cache key dynamically using the request data*/
    protected abstract String generateCacheKey(Map<String, String> requestData);

    /**
     * Method to get all or requested amount of data of certain type
     *
     * @param requestData
     * @param isDirectData
     * @param isJsonResponse
     * @return returns the result for requested data
     *         *
     */
    public abstract Object getData(Map<String, String> requestData, boolean isDirectData, boolean isJsonResponse);

    /**
     * Abstract method to send the request to the selected socket and get the response and
     * process with corresponding process request method
     *
     * @param request
     * @param isJsonResponse
     * @return
     * @throws
     */
    protected abstract Object getSocketResponse(String request, boolean isJsonResponse);

    /**
     * Abstract method to send the request to the selected socket and get the response and
     * process with corresponding process request method
     *
     * @param request
     * @param isJsonResponse
     * @return
     * @throws
     */
    protected abstract Object getSocketResponse(String request,int timeout, boolean isJsonResponse);

    /* Abstract method to process the response and convert the data into a reusable object to cache */
    protected abstract Object processResponse(ResponseObj response);

    public int delete(Object key) {
        if (key instanceof String) {
            cacheManager.delete((String) key);
        }
        return 1;
    }

    public void setCacheManager(ICacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setSocketManager(ISocket socketManager) {
        this.socketManager = socketManager;
    }

    public void setHttpConnectionManager(IHttp httpConnectionManager) {
        this.httpConnectionManager = httpConnectionManager;
    }

    /**
     * abstract method to update cache data
     * This method will invoke by particular manager
     *
     *
     *
     *
     *
     * @param requestDTO map of request data
     * @return status
     */
    public abstract int updateData(DataAccessRequestDTO requestDTO);

    /**
     * initialize cache update process
     */
    public abstract void initUpdateData();

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void setCacheEnabled(Boolean cacheEnabled) {
        isCacheEnabled = cacheEnabled;
    }

   /* public void setMemoryCache(ICacheManager memoryCache) {
        this.memoryCache = memoryCache;
    }
*/
    /* Set cacheConfigKey to your data access class. refer info-cache-data-access.xml */
    protected int getTimeToLive() {

        CacheConfig cacheConfig = cacheManager.getCacheConfigs().getCacheConfig(this.cacheConfigKey);

        return cacheConfig.getTimeToLive();
    }

    /* Set cacheConfigKey to your data access class by given time. refer info-cache-data-access.xml */
    protected int getTimeToLive(String key) {

        CacheConfig cacheConfig = cacheManager.getCacheConfigs().getCacheConfig(key);

        return cacheConfig.getTimeToLive();
    }

    protected int getTimeToIdle() {

        CacheConfig cacheConfigCalendarEvents = cacheManager.getCacheConfigs().getCacheConfig(this.cacheConfigKey);

        return cacheConfigCalendarEvents.getTimeToIdle();
    }

    public void setCacheConfigKey(String cacheConfigKey) {
        this.cacheConfigKey = cacheConfigKey;
    }

    public ICacheManager getCacheManager() {
        return cacheManager;
    }

    public void setMultipleSocketManager(IMultipleSocket multipleSocketManager) {
        this.multipleSocketManager = multipleSocketManager;
    }
}
