package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nimilaa on 4/3/14.
 */
public class CurrencyMasterDataDTO implements Serializable {
    private String currencyCode;
    private Map<String, CurrencyLangDTO> currencyLangDTOMap = null;

    public CurrencyMasterDataDTO() {
        setCurrencyLangDTOMap(new HashMap<String, CurrencyLangDTO>());
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Map<String, CurrencyLangDTO> getCurrencyLangDTOMap() {
        return currencyLangDTOMap;
    }

    public void setCurrencyLangDTOMap(Map<String, CurrencyLangDTO> currencyLangDTOMap) {
        this.currencyLangDTOMap = currencyLangDTOMap;
    }
}
