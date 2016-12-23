package com.dfn.alerts.dataaccess.orm.impl.tickers;

import com.dfn.alerts.utils.CommonUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * Created by hasarindat on 6/16/2015.
 */
public class TickerClassLevels implements Serializable{

    private final int level1;
    private final int level2;
    private final int level3;
    private final Map<String, String> level1DescMap;
    private final Map<String, String> level2DescMap;
    private final Map<String, String> level3DescMap;

    public static class Builder {
        // Required parameters
        private int level1;
        private int level2;
        private int level3;

        //optional
        private Map<String, String> level1DescMap = Collections.emptyMap();
        private Map<String, String> level2DescMap = Collections.emptyMap();
        private Map<String, String> level3DescMap = Collections.emptyMap();

        public Builder(int level1, int level2, int level3) {
            this.level1 = level1;
            this.level2 = level2;
            this.level3 = level3;
        }

        public Builder level1Description(String description) {
            if (!CommonUtils.isNullOrEmptyString(description)) {
                level1DescMap = CommonUtils.getLanguageDescriptionMap(description);
            }
            return this;
        }

        public Builder level2Description(String description) {
            if (!CommonUtils.isNullOrEmptyString(description)) {
                level2DescMap = CommonUtils.getLanguageDescriptionMap(description);
            }
            return this;
        }

        public Builder level3Description(String description) {
            if (!CommonUtils.isNullOrEmptyString(description)) {
                level3DescMap = CommonUtils.getLanguageDescriptionMap(description);
            }
            return this;
        }

        public TickerClassLevels build() {
            return new TickerClassLevels(this);
        }
    }

    private TickerClassLevels(Builder builder) {
        level1 = builder.level1;
        level2 = builder.level2;
        level3 = builder.level3;
        level1DescMap = builder.level1DescMap;
        level2DescMap = builder.level2DescMap;
        level3DescMap = builder.level3DescMap;
    }

    public boolean isEquity() {
        return level1 == 3 && level2 == 18;
    }

    public boolean isEquityCommonStock() {
        return level1 == 3 && level2 == 18 && level3 == 62;
    }

    public boolean isIndex(){
        return level1 == 6;
    }

    public int getLevel1() {
        return level1;
    }

    public int getLevel2() {
        return level2;
    }

    public int getLevel3() {
        return level3;
    }

    public String getLevel1Description(String language) {
        return level1DescMap.get(language);
    }

    public String getLevel2Description(String language) {
        return level2DescMap.get(language);
    }

    public String getLevel3Description(String language) {
        return level3DescMap.get(language);
    }

}
