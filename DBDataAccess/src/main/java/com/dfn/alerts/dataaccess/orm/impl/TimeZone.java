package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;
import com.dfn.alerts.utils.CommonUtils;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 6/19/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "TIME_ZONE", uniqueConstraints = @UniqueConstraint(columnNames ={ "TIME_ZONE_ID" }))
public class TimeZone implements Serializable{
    private Integer timeZoneId;
    private String timeZoneDescriptionLan;
    private Map<String,String> timeZoneDescription;
    private String timezoneOffset;
    private String adjustedTimezoneOffset;

    private String correctedTimezoneOffset;

    @Id
    @Column(name = "TIME_ZONE_ID")
    public Integer getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(Integer timeZoneId) {
        this.timeZoneId = timeZoneId;
    }


    @Column(name = "TIME_ZONE_DESC")
    public String getTimeZoneDescriptionLan() {
        return timeZoneDescriptionLan;
    }

    public void setTimeZoneDescriptionLan(String timeZoneDescriptionLan) {
        this.timeZoneDescriptionLan = timeZoneDescriptionLan;
        this.timeZoneDescription = DataFormatter.GetLanguageSpecificDescriptionMap(timeZoneDescriptionLan);
    }

    @Column(name = "TIMEZONE_OFFSET")
    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    @Column(name = "ADJUSTED_OFFSET")
    public String getAdjustedTimezoneOffset() {
        return adjustedTimezoneOffset;
    }

    public void setAdjustedTimezoneOffset(String adjustedTimezoneOffset) {
        this.adjustedTimezoneOffset = adjustedTimezoneOffset;
    }

    @Formula("CONCAT(CONCAT( TIMEZONE_OFFSET,','), ADJUSTED_OFFSET)")
    public String getCorrectedTimezoneOffset() {
        return correctedTimezoneOffset;
    }

    public void setCorrectedTimezoneOffset(String correctedTimezoneOffset) {
        String[] offsets = correctedTimezoneOffset.split(",");
        if(offsets.length>=2){
            this.correctedTimezoneOffset =  CommonUtils.getCorrectedTimezoneOffset(offsets[0], offsets[1]);
        }
    }

    @Transient
    public Map<String, String> getTimeZoneDescription() {
        return timeZoneDescription;
    }

}
