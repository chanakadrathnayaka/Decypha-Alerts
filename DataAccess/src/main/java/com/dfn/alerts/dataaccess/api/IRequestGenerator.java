package com.dfn.alerts.dataaccess.api;

import com.dfn.alerts.constants.IConstants;

import java.util.Map;

/**
 * This interface maintains the request generation for market back end services.
 * Implement this interface when it requires a new request format for the back end service.
 *
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 3/27/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IRequestGenerator {

    /**
     * Method to generate the request for the request format (json/query string)
     * @param request request map
     * @param paramEnum type
     * @return request string
     */
     String generateRequest(Map<String, String> request, IConstants.RequestDataType paramEnum);
}
