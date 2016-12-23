package com.dfn.alerts.api;

import com.dfn.alerts.exception.ApplicationException;

/**
 *
 * This interface maintain connection between servlet layer and business layer
 * implement this if there are multiple/collective responsibilty eg : Inside transaction + Symbol snapshot
 * @see IBusinessManager for single responsibilty
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 12/20/12
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */


public interface IMultiBusinessManager {

    /** Return related model attributes for request
     *
     * @param requestData request data to create request object
     * @return response object
     * @throws ApplicationException throw application exception  if any error or checked exception
     */

Object getModelData(Object requestData) throws ApplicationException;
}
