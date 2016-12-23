package com.dfn.alerts.beans;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 5/27/13
 * Time: 6:56 PM
 */
public class CountryTickerSnapshotDTO extends CountryTickerDTO{
    
    private String historyData;

    public String getHistoryData() {
        return historyData;
    }

    public void setHistoryData(String historyData) {
        this.historyData = historyData;
    }

    /**
     * Get History data String
     * @param historyDataList history ArrayList
     * @param dateIndex array list index for the date field
     * @param valueIndex arraylist index for the value field
     */
    public void setHistoryData(List<List> historyDataList, int dateIndex, int valueIndex)
    {
        NumberFormat formatter = new DecimalFormat("###.#####");
        StringBuilder historyDataBuilder = new StringBuilder();
        historyDataBuilder.append("[");
        for (int index = 0; index < historyDataList.size(); index++)
        {
            if (index > 0)
                historyDataBuilder.append(",[");
            else
                historyDataBuilder.append("[");
            historyDataBuilder.append(formatter.format(Double.parseDouble(((historyDataList.get(index))).get(dateIndex).toString())));
            historyDataBuilder.append(",");
            historyDataBuilder.append(((historyDataList.get(index))).get(valueIndex));
            historyDataBuilder.append("]");
        }
        historyDataBuilder.append("]");
        this.historyData = historyDataBuilder.toString();
    }
}
