package com.dfn.alerts.dataaccess.utils;

import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.beans.DataNotification;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: hasarindat
 * Date: 11/22/12
 * Time: 5:18 PM
 */
public class GsonHelper {

    private static Logger log = LogManager.getLogger(GsonHelper.class);

    public static Map<String, ResponseObj> getObject(Map<String, String> jsonList) {
        Map<String, ResponseObj> responseObjMap = new HashMap<String, ResponseObj>(jsonList.size());

        for(String id : jsonList.keySet()){
            responseObjMap.put(id, getObject(jsonList.get(id)));
        }
        return responseObjMap;
    }

    public static ResponseObj getObject(String json) {

        if (json == null) {
            log.info("json string is null");
            return null;
        }

        ResponseObj responseObj;
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, Object.class);

        HashMap<String, Object> map = null;

        if(obj instanceof HashMap && ((HashMap) obj).get("RES") instanceof HashMap){
             map = (HashMap<String, Object>) ((HashMap) obj).get("RES");
        }
        if (map != null) {
            responseObj = new ResponseObj();
            //TO-DO validate before process
            responseObj.setRT(map.get("RT") != null ? Integer.parseInt(map.get("RT").toString()) : 0);
            responseObj.setHED((Map<String, Object>) map.get(IConstants.ResponseTypes.HED));
            responseObj.setDAT((Map<String, Object>) map.get(IConstants.ResponseTypes.DAT));
            responseObj.setSTAT( map.get(IConstants.ResponseTypes.STAT));
            responseObj.setROW((Map<String, Object>) map.get(IConstants.ResponseTypes.ROW));
            responseObj.setSTYLE((Map<String, Object>) map.get(IConstants.ResponseTypes.STYLE));
            if(map.get(IConstants.ResponseTypes.LONGDES) != null ){
                responseObj.setLONGDES((Map<String, Object>) map.get(IConstants.ResponseTypes.LONGDES));
            }
            if(map.get(IConstants.ResponseTypes.AVG) != null ){
                responseObj.setAVG((Map<String, Object>) map.get(IConstants.ResponseTypes.AVG));
            }
            responseObj.setPGS((Map<String, Object>) map.get(IConstants.ResponseTypes.PGS));
            responseObj.setPGI((Map<String, Object>) map.get(IConstants.ResponseTypes.PGI));
            responseObj.setFDN((DataNotification[]) map.get(IConstants.ResponseTypes.FUNDAMENTAL_DATA_NOTIFICATION));
            responseObj.setLTSID((Integer)map.get(IConstants.ResponseTypes.LTSID));
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Object.class, new GsonNaturalDeserializer());
            Gson mixGson = gsonBuilder.create();
            responseObj = mixGson.fromJson(json, ResponseObj.class);
        }

        return responseObj;
    }

    public String getJsonString(String json) {

        if (json == null) {
            return null;
        }

        Gson gson = new Gson();
        Object obj = gson.fromJson(json, Object.class);

        return gson.toJson(((LinkedHashMap) obj).get(IConstants.ResponseTypes.RES));

    }

    public static String getJson(Object object) {
        if (object == null) {
            return null;
        }

        Gson gson = new Gson();

        return gson.toJson(object);
    }

}
