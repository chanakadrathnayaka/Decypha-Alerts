package com.dfn.alerts.dataaccess.orm.impl;

import com.dfn.alerts.beans.TopNewsEditionDTO;
import com.dfn.alerts.dataaccess.utils.DataFormatter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 12/11/13
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "EDITION_MASTER", uniqueConstraints = @UniqueConstraint(columnNames ={ "EDITION" }))
public class TopNewsEdition implements Serializable {

    private String edition;
    private Integer editionType;
    private Integer newsEditionId;
    private String editionDescriptionLan;

    private TopNewsEditionDTO topNewsEditionDTO = new TopNewsEditionDTO();

    @Id
    @Column(name = "EDITION")
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
        topNewsEditionDTO.setEdition(edition);
    }

    @Column(name = "EDITION_TYPE")
    public Integer getEditionType() {
        return editionType;
    }

    public void setEditionType(Integer editionType) {
        this.editionType = editionType;
        topNewsEditionDTO.setEditionType(editionType);
    }

    @Column(name = "NEWS_EDITION_ID")
    public Integer getNewsEditionId() {
        return newsEditionId;
    }

    public void setNewsEditionId(Integer newsEditionId) {
        this.newsEditionId = newsEditionId;
        topNewsEditionDTO.setNewsEditionId(newsEditionId);
    }

    @Column(name = "EDITION_DESC")
    public String getEditionDescriptionLan() {
        return editionDescriptionLan;
    }

    public void setEditionDescriptionLan(String editionDescriptionLan) {
        this.editionDescriptionLan = editionDescriptionLan;
        topNewsEditionDTO.setEditionDescriptionLan(editionDescriptionLan);
        topNewsEditionDTO.setEditionDescription(DataFormatter.GetLanguageSpecificDescriptionMap(editionDescriptionLan));
    }

    @Transient
    public TopNewsEditionDTO getTopNewsEditionDTO(){
        return topNewsEditionDTO;
    }

}
