package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.beans.TopNewsDataDTO;
import com.dfn.alerts.beans.news.NewsDTO;
import com.dfn.alerts.constants.DBConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: aravinda
 * Date: 10/09/14.
 * Time: 18:46 PM
 */
public class NewsDBHelper {

    /**
     * Get TopNewsDataDTO object from result set
     *
     * @param results result set
     * @return TopNewsDataDTO object
     * @throws SQLException
     */
    public static TopNewsDataDTO getTopNewsDataDTO(ResultSet results) throws SQLException {

        TopNewsDataDTO topNewsDataDTO = new TopNewsDataDTO();
        topNewsDataDTO.setNewsId(results.getInt(DBConstants.DatabaseColumns.NEWS_ID));
        topNewsDataDTO.setIsTopStory(results.getInt(DBConstants.DatabaseColumns.IS_TOP_STORY));

        return topNewsDataDTO;
    }

    /**
     * Get NewsDTO object from result set
     *
     * @param results result set
     * @return NewsDTO object
     * @throws SQLException
     */
    public static NewsDTO getNewsDTO(ResultSet results) throws SQLException {

        NewsDTO newsDTO = new NewsDTO(results.getInt(DBConstants.DatabaseColumns.NEWS_ID));

        return newsDTO;
    }
}
