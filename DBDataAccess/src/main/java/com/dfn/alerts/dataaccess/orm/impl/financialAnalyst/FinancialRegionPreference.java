package com.dfn.alerts.dataaccess.orm.impl.financialAnalyst;

import com.dfn.alerts.constants.IConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by chathurag on 6/20/2016.
 */
@Entity
@Table(name = "ANALYST_FN_NOTIFICATION_PREF")
public class FinancialRegionPreference {

    @Id
    @Column(name = "EMAIL", nullable = false, updatable = false)
    private String email;

    @Column(name = "ANALYST_NAME")
    private String name;

    @Column(name = "INTERESTED_COUNTRIES")
    private String countries;

    @Column(name = "INTERESTED_LISTING_TYPES")
    private String listingStatus;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String region) {
        this.countries = region;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinancialRegionPreference that = (FinancialRegionPreference) o;

        return !(email != null && !email.equals(IConstants.EMPTY) ? !email.equals(that.email) :
                (that.email != null && !that.email.equals(IConstants.EMPTY)));
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result;
        return result;
    }
}
