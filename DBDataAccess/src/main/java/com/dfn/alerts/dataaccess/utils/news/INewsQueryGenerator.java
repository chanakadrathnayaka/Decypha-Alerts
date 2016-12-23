package com.dfn.alerts.dataaccess.utils.news;

import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.TopNewsDataDTO;
import com.dfn.alerts.beans.news.NewsDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * news query generation
 * Created by hasarindat on 7/3/2015.
 */
public interface INewsQueryGenerator {

    RequestDBDTO getAvailableNewsItems(Set<Long> newsIdList);

    RequestDBDTO getDeleteQuery(int count);

    RequestDBDTO getEODDeleteQuery();

    RequestDBDTO getInsertQuery(List<String> languages);

    RequestDBDTO getUpdateQuery(List<String> languages);

    void setNewsUpdateColumnValues(PreparedStatement preparedStatement, NewsDTO newsDTO, List<String> languages) throws SQLException;

    void setNewsInsertColumnValues(PreparedStatement preparedStatement, NewsDTO newsDTO, List<String> languages) throws SQLException;

    NewsDTO getNewsDTO(ResultSet resultSet, List<String> languages) throws SQLException;

    RequestDBDTO getTopNewsUpdateQuery();

    void setTopNewsUpdateColumnValues(PreparedStatement preparedStatement, TopNewsDataDTO newsDTO) throws SQLException;

}
