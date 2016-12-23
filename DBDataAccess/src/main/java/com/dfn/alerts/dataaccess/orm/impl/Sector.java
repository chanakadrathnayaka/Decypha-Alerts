package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** DAO for Sector Master Data
 * Created by IntelliJ IDEA.
 * User: Duminda A
 * Date: 3/07/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SECTORS")
public class Sector implements Serializable{

    private Integer id;

    private String sectorType;

    private String sectorCode;

    private String shortDescriptionLan;

    private Map<String, String> shortDescription;

    private Long associatedTickerSerial;

    private Set<SectorSnapshot> sectorSnapshots = new HashSet<SectorSnapshot>(0);

    @Id
    @Column(name = "ID", length = 10)
    public Integer getSectorId() {
        return id;
    }

    public void setSectorId(Integer id) {
        this.id = id;
    }

    @Column(name = "SECTOR_TYPE", length = 10)  //Gics1,2,3,4,or exchange
    public String getSectorType() {
        return sectorType;
    }

   public void setSectorType(String sectoryType) {
       this.sectorType = sectoryType;
    }

    @Column(name = "SECTOR_CODE", length = 20)
    public String getSectorCode() {
        return sectorCode;
    }

   public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    @Column(name = "SHORT_DESCRIPTION_LAN", length = 1000, nullable = false)
    public String getShortDescriptionLan() {
          return shortDescriptionLan;
    }

    public void setShortDescriptionLan(String shortDescriptionLan) {
        this.shortDescriptionLan =  shortDescriptionLan;
        this.shortDescription = DataFormatter.GetLanguageSpecificDescriptionMap(shortDescriptionLan);
     }

    @Transient
    public Map<String, String> getShortDescription() {
        return shortDescription;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sector")
    public Set<SectorSnapshot> getSectorSnapshots() {
        return this.sectorSnapshots;
    }

    public void setSectorSnapshots(Set<SectorSnapshot> sectorSnapshots) {
        this.sectorSnapshots = sectorSnapshots;
    }

    @Column(name = "ASSOCIATED_TICKER_SERIAL", length = 12)
    public Long getAssociatedTickerSerial() {
        return associatedTickerSerial;
    }

    public void setAssociatedTickerSerial(Long associatedTickerSerial) {
        this.associatedTickerSerial = associatedTickerSerial;
    }
}
