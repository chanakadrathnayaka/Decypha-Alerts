package com.dfn.alerts.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dushani
 * Date: 2/13/13
 * Time: 5:10 PM
 */
public class SectorDTO implements Serializable {
    private String sectorCode;
    private String sectorType;
    private Long associatedTickerSerial;
    private String associatedTickerId = null;
    private String associatedTickerSourceId = null;
    private Map<String, String> shortDescription;
    private Map<String, SectorSnapshotDTO> sectorSnapshotDTOs;

    public SectorDTO(String sectorCode, String sectorType, Map<String, String> shortDescription, Map<String, SectorSnapshotDTO> sectorSnapshotDTOs, Long associatedTickerSerial){
        this.sectorCode = sectorCode;
        this.sectorType = sectorType;
        this.shortDescription = shortDescription;
        this.sectorSnapshotDTOs = sectorSnapshotDTOs;
        this.associatedTickerSerial = associatedTickerSerial;
    }

    public String getSectorType() {
        return sectorType;
    }

    public void setSectorType(String sectorType) {
        this.sectorType = sectorType;
    }

    public Map<String, SectorSnapshotDTO> getSectorSnapshotDTOs() {
        return sectorSnapshotDTOs;
    }

    public void setSectorSnapshotDTOs(Map<String, SectorSnapshotDTO>sectorSnapshotDTOs) {
        this.sectorSnapshotDTOs = sectorSnapshotDTOs;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Map<String, String> shortDescription) {
        this.shortDescription =  shortDescription;
    }

    public Long getAssociatedTickerSerial() {
        return associatedTickerSerial;
    }

    public void setAssociatedTickerSerial(Long associatedTickerSerial) {
        this.associatedTickerSerial = associatedTickerSerial;
    }

    public String getAssociatedTickerId() {
        return associatedTickerId;
    }

    public void setAssociatedTickerId(String associatedTickerId) {
        this.associatedTickerId = associatedTickerId;
    }

    public String getAssociatedTickerSourceId() {
        return associatedTickerSourceId;
    }

    public void setAssociatedTickerSourceId(String associatedTickerSourceId) {
        this.associatedTickerSourceId = associatedTickerSourceId;
    }
}
