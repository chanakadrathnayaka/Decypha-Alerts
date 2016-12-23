package com.dfn.alerts.dataaccess.api;

import com.dfn.alerts.dataaccess.config.CacheConfigs;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.search.Results;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 11/26/12
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICacheManager {
    boolean put(String key, final Object o);
    boolean put(String key, final Object o, int timeToLiveSeconds);
    Object get(String key);
    Object get(String key, Class aClass);
    Object getQuiet(final Object key);
    Element getQuietElement(final Object key);
    boolean delete(String key);
    boolean modify(String key, int timeToLiveSeconds, final Object o);
    boolean setElements(Collection<Element> elements);
    Object getElements(String key);
    List<String> getKeys();
    List<String> getKeys(String cacheName);
    Results search(String searchString);
    boolean shutDown();
    boolean put(String key, Object o, int timeToLiveSeconds, int timeToIdleSeconds);
    boolean registerListener(CacheEventListener cacheEventListener);
    CacheConfigs getCacheConfigs();
    void setCacheConfigs(CacheConfigs cacheConfigs);
    void removeAll();
}
