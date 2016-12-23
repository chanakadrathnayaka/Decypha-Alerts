package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/23/13
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "SECURITY_WIDGET_USR_PERMISSION", uniqueConstraints = @UniqueConstraint(columnNames ={ "ID" }))
public class SecurityWidgetUsrPermission {

    private Long id;

    private String widgetId;

    private Integer widgetType;

    private String userRole;

    private String widgetDescription;

    private String serviceType;



    @Id
    @Column( name = "ID")
    @SequenceGenerator(name="HIBERNATE_SEQUENCE", sequenceName="HIBERNATE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="HIBERNATE_SEQUENCE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column( name = "WIDGET_ID" ,length = 128)
    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    @Column( name = "WIDGET_TYPE")
    public Integer getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(Integer widgetType) {
        this.widgetType = widgetType;
    }

    @Column( name = "USER_ROLE" ,length = 32)
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Column( name = "WIDGET_DESCRIPTION" ,length = 512)
    public String getWidgetDescription() {
        return widgetDescription;
    }

    public void setWidgetDescription(String widgetDescription) {
        this.widgetDescription = widgetDescription;
    }


    @Column( name = "SERVICE_TYPE" ,length = 64)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
