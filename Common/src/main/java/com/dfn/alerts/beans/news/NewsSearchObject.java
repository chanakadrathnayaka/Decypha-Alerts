package com.dfn.alerts.beans.news;

import java.util.Collections;
import java.util.List;

/**
 * for news searching
 * Created by hasarindat on 9/12/2015.
 */
public class NewsSearchObject{

    private List<NewsDTO> news;
    private boolean hasNext;
    private boolean imdbNewsLimitReached;
    private int imdbPageLimit = -1;
    private int OracleDboffsetValue = -1;
    private int totalNewsCount = 0;

    public NewsSearchObject(List<NewsDTO> news) {
        this.news = news;
    }

    public NewsSearchObject(int totalNewsCount) {
        this.totalNewsCount = totalNewsCount;
    }

    public List<NewsDTO> getNews() {
        return Collections.unmodifiableList(news);
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isImdbNewsLimitReached() {
        return imdbNewsLimitReached;
    }

    public int getImdbPageLimit() {
        return imdbPageLimit;
    }

    public int getOracleDboffsetValue() {
        return OracleDboffsetValue;
    }

    public int getTotalNewsCount() {
        return totalNewsCount;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setImdbNewsLimitReached(boolean imdbNewsLimitReached) {
        this.imdbNewsLimitReached = imdbNewsLimitReached;
    }

    public void setImdbPageLimit(int imdbPageLimit) {
        this.imdbPageLimit = imdbPageLimit;
    }

    public void setOracleDboffsetValue(int oracleDboffsetValue) {
        OracleDboffsetValue = oracleDboffsetValue;
    }

    public void setTotalNewsCount(int totalNewsCount) {
        this.totalNewsCount = totalNewsCount;
    }
}
