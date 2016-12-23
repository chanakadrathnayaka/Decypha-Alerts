package com.dfn.alerts.api;


/**
 * This interface maintain connection between servlet layer and business layer
 * implement this if there is only single responsibility : eg News only , Top stocks only
 * @see IMultiBusinessManager for multiple/collective responsibilty
 *
 * Created by IntelliJ IDEA.
 * User: nimilaa
 * Date: 12/3/12
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IBusinessManager {
    /** Return related model attributes for request
     *
     *
     * @param requestData request data to create request object
     * @param isJasonResponse true if response type is JSON
     * @return response object
     */
    Object getData(Object requestData, boolean isJasonResponse);

}
