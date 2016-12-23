package com.dfn.alerts.dataaccess.utils;


import com.dfn.alerts.constants.IConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/** Formatter for Data
 * Created by Duminda A
 * User: All formatter methods for DFN Plus Data
 * Date: 2/12/13
 * Time: 11:49 AM
 */
public final class DataFormatter {

    private DataFormatter(){}

     /**
     * Get language map from formatted language string
     * @param description language specific description string in |LAN:DESC|LAN:DES.. format
     * return Map<String,String>
     * */
        public static Map<String,String> GetLanguageSpecificDescriptionMap(String description) {
            Map<String,String> descriptionMap = null;
            if(description != null && description.contains(Character.toString(IConstants.Delimiter.VL))){
                descriptionMap = new HashMap<String, String>(4);
                StringTokenizer strTkn = new StringTokenizer(description, Character.toString(IConstants.Delimiter.VL));
                while (strTkn.hasMoreTokens()){
                    String [] keyVal = strTkn.nextToken().split(":");
                    if(keyVal != null && keyVal.length == 2)
                    {
                        descriptionMap.put(keyVal[0],keyVal[1]);
                    }
                }
            }
            return descriptionMap;
        }
  }
