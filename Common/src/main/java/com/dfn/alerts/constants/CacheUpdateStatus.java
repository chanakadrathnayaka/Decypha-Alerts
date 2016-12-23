package com.dfn.alerts.constants;

/**
 *
 * Public class to keep cache update status
 *
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 12/21/12
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public final class CacheUpdateStatus {
    private CacheUpdateStatus(){}

    public static final int TO_BE_UPDATED       = 0 ;

    public static final int UPDATE_REQUIRED     = 1 ;

    public static final int ALREADY_UPDATED     = 2 ;

    public static final int UPDATE_SUCCEEDED    = 3 ;

    public static final int UPDATE_FAILED       = 4 ;

    public static final int UPDATE_IN_PROGRESS  = 5 ;

    public static final int NOTHING_TO_UPDATE   = 6 ;
    
    public static final int EOD_PROCESS_NOT_FINISHED = 7;

    public static final int DELETE_SUCCEEDED = 8;

    public static final int EOD_PROCESS_COMPLETED = 9;

    public static final int EOD_PROCESS_IN_PROGRESS = 10;
}
