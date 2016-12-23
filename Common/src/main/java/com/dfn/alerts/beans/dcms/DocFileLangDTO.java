package com.dfn.alerts.beans.dcms;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: udaras
 * Date: 11/28/13
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocFileLangDTO implements Serializable{

    private String displayName;
    private String summary;
    private String fileGuid;
    private String industryCodesDesc;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFileGuid() {
        return fileGuid;
    }

    public void setFileGuid(String fileGuid) {
        this.fileGuid = fileGuid;
    }

    public String getIndustryCodesDesc() {
        return industryCodesDesc;
    }

    public void setIndustryCodesDesc(String industryCodesDesc) {
        this.industryCodesDesc = industryCodesDesc;
    }
}
