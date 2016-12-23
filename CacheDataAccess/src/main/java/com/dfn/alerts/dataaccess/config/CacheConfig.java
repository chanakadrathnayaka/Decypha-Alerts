package com.dfn.alerts.dataaccess.config;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 2/11/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheConfig {

    /**
     * The amount of time for the element to live, in seconds. 0 indicates unlimited.
     */
    private int timeToLive = 0;

    /**
     * The amount of time for the element to idle, in seconds. 0 indicates unlimited.
     */
    private int timeToIdle = 0;


    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getTimeToIdle() {
        return timeToIdle;
    }

    public void setTimeToIdle(int timeToIdle) {
        this.timeToIdle = timeToIdle;
    }
}
