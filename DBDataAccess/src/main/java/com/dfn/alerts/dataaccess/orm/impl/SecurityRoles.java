package com.dfn.alerts.dataaccess.orm.impl;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/3/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "SECURITY_ROLES", uniqueConstraints = @UniqueConstraint(columnNames ={ "ID" }))
public class SecurityRoles implements Comparable<SecurityRoles>,Serializable {

    private Long id;
    
    private String roleId;

    private String roleDescription;

    private Integer roleWeight;

    private String roleType;

    @Id
    @Column( name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column( name = "ROLE_ID" ,length = 128)
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Column( name = "ROLE_DESCRIPTION" ,length = 1024)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Column( name = "ROLE_WEIGHT")
    public Integer getRoleWeight() {
        return roleWeight;
    }

    public void setRoleWeight(Integer roleWeight) {
        this.roleWeight = roleWeight;
    }

    @Column( name = "ROLE_TYPE" ,length = 10)
    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public int compareTo(SecurityRoles o) {
        int weight = o.getRoleWeight();

        //ascending order
        return this.roleWeight - weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        SecurityRoles that = (SecurityRoles) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
