package com.dfn.alerts.beans.ownershipLimits;

import com.dfn.alerts.beans.tickers.EquityTickerDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hasarindat
 * Date: 10/9/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockOwnershipDTO extends EquityTickerDTO {

    private StockOwnershipDTO() {
    }

    public StockOwnershipDTO(long tickerSerial) {
        this.setTickerSerial(tickerSerial);
    }

    private Map<String, StockOwnershipLimitSeriesDTO> types = new HashMap<String, StockOwnershipLimitSeriesDTO>();

    public Map<String, StockOwnershipLimitSeriesDTO> getTypes() {
        return types;
    }

    public void addType(StockOwnershipLimitSeriesDTO stockOwnershipLimitSeriesDTO) {
        getTypes().put(stockOwnershipLimitSeriesDTO.getSeriesId() + "", stockOwnershipLimitSeriesDTO);
    }

}
