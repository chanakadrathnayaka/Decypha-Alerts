package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.dataaccess.api.ICacheManager;
import com.dfn.alerts.dataaccess.config.CacheConfigs;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Results;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * BigMemoryCacheImpl concrete implementation of  <tt>ICacheManager</tt> interface
 * <p/>
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/6/12
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BigMemoryCacheImpl implements ICacheManager {

    /**
     * Log4j instance
     */
    private static final Logger log = LogManager.getLogger(BigMemoryCacheImpl.class);

    /**
     * Cache manager  to manage eh cache
     */
    private CacheManager manager;

    /**
     * Eh cache object
     */
    private Ehcache cache;

    private CacheConfigs cacheConfigs;

    private  int defaultCacheInMillis = 86400;


    /**
     * Create EH cache object for given memory sizes
     *
     * @param localHeap    local heap size by megabytes
     * @param localOffHeap local off heap size by megabytes
     * @param timeToLive   time to leave by seconds
     * @param timeToIdle   time to idle by seconds
     */
    public BigMemoryCacheImpl(long localHeap, long localOffHeap, long timeToLive, long timeToIdle) {

        if (localHeap <= 0) {
            throw new IllegalArgumentException("Illegal local heap size : " + localHeap);
        }

        if (localOffHeap <= 0) {
            throw new IllegalArgumentException("Illegal local Off Heap size : " + localOffHeap);
        }

        if (timeToLive <= 0) {
            throw new IllegalArgumentException("Illegal time To Live : " + timeToLive);
        }

        if (timeToIdle <= 0) {
            throw new IllegalArgumentException("Illegal time To Idle : " + timeToIdle);
        }


        Configuration managerConfiguration = new Configuration()
                .name("bigmemory-config")
                .cache(new CacheConfiguration()
                        .name("BigMemory")
                        .maxBytesLocalHeap(localHeap, MemoryUnit.MEGABYTES)
                        .maxBytesLocalOffHeap(localOffHeap, MemoryUnit.MEGABYTES)
                        .copyOnRead(true)
                        .statistics(true)
                        .eternal(false).timeToLiveSeconds(timeToLive).timeToIdleSeconds(timeToIdle).overflowToDisk(true)
               /* ).cache(new CacheConfiguration()
                        .name(CacheNames.METHOD_CACHE_NON_EXPIRE)
                        .maxBytesLocalHeap(localHeap, MemoryUnit.MEGABYTES)
                        .maxBytesLocalOffHeap(localOffHeap, MemoryUnit.MEGABYTES)
                        .copyOnRead(true)
                        .statistics(true)
                        .eternal(false).timeToLiveSeconds(0).timeToIdleSeconds(0).overflowToDisk(true)*/
                );

        this.manager = CacheManager.create(managerConfiguration);
        this.cache = this.manager.getEhcache("BigMemory");

        if(this.cache == null){
            throw new IllegalArgumentException(" Cache object can not be null ");
        }

        if (log.isInfoEnabled()) {
            log.info("Created BigMemoryCacheImpl object localHeap : " + localHeap + " localOffHeap : " + localOffHeap + " timeToLive : " + timeToLive + "timeToIdle : " + timeToIdle);
        }


    }

    /**
     * Adds a listener to the notification service. No guarantee is made that listeners will be
     * notified in the order they were added.
     *
     * @param cacheEventListener
     * @return true if the listener is being added and was not already added
     */
    public final boolean registerListener(CacheEventListener cacheEventListener) {
        if (cacheEventListener == null) {
            return false;
        }
        return cache.getCacheEventNotificationService().registerListener(cacheEventListener);
    }

    /**
     * method to put object to cache. this will keep object in cache for one day.
     * No expire
     *
     * @param key cache key
     * @param o   cache object
     * @return true if transaction success
     */
    public boolean put(String key, final Object o) {
        try {
            Element element = new Element(key, o);
            element.setTimeToLive(defaultCacheInMillis);  //timeToLiveSeconds
            if (cache != null) {
                cache.put(element);
                return true;
            }
        } catch (CacheException ce) {
            log.error("Exception while putting data to cache cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //TODO send alert
            //Do not propagate to upper layer. If any issue with ca
        }

        return false;
    }

    /**
     * method to put object to cache.
     *
     * @param key               cache key
     * @param o                 cache object
     * @param timeToLiveSeconds 0 for unlimited
     * @return true if transaction success
     */

    public boolean put(String key, Object o, int timeToLiveSeconds ) {
        Element element = new Element(key, o);
        element.setTimeToLive(timeToLiveSeconds);
        try {
            if (cache != null) {
                cache.put(element);
                return true;
            }

        } catch (CacheException ce) {
            log.error("Exception while putting data to cache cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //TODO send alert
            //Do not propagate to upper layer. If any issue with ca
        }

        return false;
    }

    /**
     * method to put object to cache.
     *
     * @param key               cache key
     * @param o                 cache object
     * @param timeToLiveSeconds 0 for unlimited
     * @param timeToIdleSeconds 0 for unlimited
     * @return true if transaction success
     */

    public boolean put(String key, Object o, int timeToLiveSeconds, int timeToIdleSeconds) {
        Element element = new Element(key, o);
        element.setTimeToIdle(timeToIdleSeconds);
        element.setTimeToLive(timeToLiveSeconds);
        try {
            if (cache != null) {
                cache.put(element);
                return true;
            }

        } catch (CacheException ce) {
            log.error("Exception while putting data to cache cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //TODO send alert
            //Do not propagate to upper layer. If any issue with ca
        }

        return false;
    }


    /**
     * all multiple elements to cache
     *
     * @param elements Collection of elements
     * @return true if transaction success
     */
    public boolean setElements(Collection<Element> elements) {
        try {
            if (cache != null) {
                cache.putAll(elements);
                return true;
            }
        } catch (CacheException ce) {
            log.error("Cache exception ");
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca
        }

        return false;
    }

    /**
     * get cached element for given key
     *
     * @param key cache key
     * @return cached object
     */
    public Object get(String key) {
        try {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            }
        } catch (CacheException ce) {
            log.error("Exception while getting data from cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca

        }
        return null;
    }

    @Override
    public Object get(String key, Class aClass) {
        return null;
    }

    /**
     * Get row Element object from cache
     *
     * @param key cache key
     * @return Element object
     */
    public Element getElements(String key) {
        try {
                return cache.get(key);

        } catch (CacheException ce) {
            log.error("Exception while getting data from cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca

        }
        return null;
    }


    /**
     * Gets an Object from the cache, without updating Element statistics. Cache statistics are
     * also not updated.
     * <p/>
     *
     * @param key a serializable value
     * @return the element, or null, if it does not exist.
     */
    public Object getQuiet(Object key) {
        try {
            Element element = cache.getQuiet(key);
            if (element != null) {
                return element.getObjectValue();
            }
        } catch (CacheException ce) {
            log.error("Exception while getting data from cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca

        }
        return null;
    }


    /**
     * Gets an element from the cache, without updating Element statistics. Cache statistics are
     * also not updated.
     * <p/>
     *
     * @param key a serializable value
     * @return the element, or null, if it does not exist.
     */
    public Element getQuietElement(Object key) {
        try {
            return cache.getQuiet(key);

        } catch (CacheException ce) {
            log.error("Exception while getting data from cache - key :  " + key);
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca

        }
        return null;
    }

    /**
     * Method to get all cache keys. This is for cache monitoring
     *
     * @return List of cache keys
     */
    public List<String> getKeys(String cacheName) {
        try {
            if(cacheName == null){
                return (List<String>) cache.getKeys();
            }else {
                Ehcache cache =  this.getCache(cacheName);
                return cache != null ? this.getCache(cacheName).getKeys() : null;
            }
        } catch (CacheException ce) {
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca
            return null;
        }
    }

    /**
     * Method to get all cache keys. This is for cache monitoring
     *
     * @return List of cache keys
     */
    public List<String> getKeys() {
        return  getKeys(null);
    }

    /**
     * Delete cache object from cache
     *
     * @param key cache key
     * @return true if transaction success
     */
    public boolean delete(String key) {
        try {
            if (cache != null) {
                cache.remove(key);
                return true;
            }
        } catch (CacheException ce) {
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca
        }

        return false;
    }

    public Results search(String searchString) {
        Attribute<String> headline = cache.getSearchAttribute("headline");
        Attribute<String> details = cache.getSearchAttribute("details");

        Query query = cache.createQuery();
        query.includeKeys();
        query.includeValues();
        query.addCriteria(headline.ilike("*" + searchString + "*").or(details.ilike("*" + searchString + "*")));
        return query.execute();
    }

    /**
     * Shut down cache manager
     *
     * @return true if shout down success
     */
    public boolean shutDown() {
        if (manager != null) {
            manager.shutdown();
            return true;
        }

        return false;
    }


    public boolean modify(String key, int timeToLiveSeconds, Object o) {

        Element element = new Element(key, o);
        element.setTimeToIdle(timeToLiveSeconds);
        try {
            cache.replace(element);
            return true;
        } catch (CacheException ce) {
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca
        }

        return false;
    }

    /**
     * Method to remove all elements from cache
     */
    public void removeAll() {
        try {
            cache.removeAll();
        } catch (CacheException ce) {
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca
        }

    }

    public void getAll() {
        try {
            Map<Object , Element> map =  cache.getAll((List<String>) cache.getKeys());
        } catch (CacheException ce) {
            log.error(ce.getMessage(), ce.getCause());
            //Do not propagate to upper layer. If any issue with ca
        }

    }
    
    
    private Ehcache getCache(String cacheName){
        return  this.manager.getEhcache(cacheName);
    }


    public CacheConfigs getCacheConfigs() {
        return cacheConfigs;
    }

    public void setCacheConfigs(CacheConfigs cacheConfigs) {
        this.cacheConfigs = cacheConfigs;
    }

    public void setDefaultCacheInMillis(int defaultCacheInMillis) {
        this.defaultCacheInMillis = defaultCacheInMillis;
    }

}
