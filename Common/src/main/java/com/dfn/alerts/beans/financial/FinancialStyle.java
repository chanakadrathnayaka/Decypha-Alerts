package com.dfn.alerts.beans.financial;

import com.dfn.alerts.constants.IConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Danuran on 4/1/2015.
 */
public class FinancialStyle {
    private int level;
    private int orderInGroup;
    private int decimalPlaces;
    private String headDescription;
    private int groupId;
    private String groupDescription;

    public FinancialStyle(String styleString) {
        //~~,~~ |level~order in group~decimal places , head desc~groupId~group head desc|
        this.groupId = 0;
        this.groupDescription = IConstants.EMPTY;
        this.headDescription = IConstants.EMPTY;
        this.level = -1;
        this.orderInGroup = -1;
        this.decimalPlaces = -1;
        if(styleString != null && styleString.contains(",")) {
            int index = styleString.indexOf(",");
            String[] style1 = styleString.substring(0, index).split("~");
            String[] style2 = styleString.substring(index + 1).split("~");
            for (int i = 0; i < style1.length; i++) {
                switch (i) {
                    case 0:
                        if (StringUtils.isNumeric(style1[i])) {
                            this.level = Integer.parseInt(style1[i]);
                        }
                        break;
                    case 1:
                        if (StringUtils.isNumeric(style1[i])) {
                            this.orderInGroup = Integer.parseInt(style1[i]);
                        }
                        break;
                    case 2:
                        if (StringUtils.isNumeric(style1[i])) {
                            this.decimalPlaces = Integer.parseInt(style1[i]);
                        }
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < style2.length; i++) {
                switch (i) {
                    case 0:
                        this.headDescription = style2[i];
                        break;
                    case 1:
                        if (StringUtils.isNumeric(style2[i])) {
                            this.groupId = Integer.parseInt(style2[i]);
                        }
                        break;
                    case 2:
                        this.groupDescription = style2[i];
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public int getOrderInGroup() {
        return orderInGroup;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public String getHeadDescription() {
        return headDescription;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupDescription() {
        return groupDescription;
    }
}
