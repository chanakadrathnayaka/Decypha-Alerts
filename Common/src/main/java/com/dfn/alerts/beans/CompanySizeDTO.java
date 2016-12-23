package com.dfn.alerts.beans;

/**
 * User: chathurag
 * Date: 12/30/2015
 * Time: 11.02 AM
 */
public class CompanySizeDTO extends CompanyDTO{

    private Double totalRevenue;
    private Double totalRevenueMin;
    private Double totalRevenueMinCompanyCurrency;
    private Double totalRevenueMax;
    private Double totalRevenueMaxCompanyCurrency;
    private Integer totalRevenueActYear;
    private Integer totalRevenueBktYear;
    private Integer totalRevenueEffectiveYear;

    private Double totalAssets;
    private Double totalAssetsMin;
    private Double totalAssetsMax;
    private Integer totalAssetsActYear;
    private Integer totalAssetsBktYear;
    private Integer totalAssetsEffectiveYear;

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getTotalRevenueMin() {
        return totalRevenueMin;
    }

    public void setTotalRevenueMin(Double totalRevenueMin) {
        this.totalRevenueMin = totalRevenueMin;
    }

    public Double getTotalRevenueMax() {
        return totalRevenueMax;
    }

    public void setTotalRevenueMax(Double totalRevenueMax) {
        this.totalRevenueMax = totalRevenueMax;
    }

    public Integer getTotalRevenueActYear() {
        return totalRevenueActYear;
    }

    public void setTotalRevenueActYear(Integer totalRevenueActYear) {
        this.totalRevenueActYear = totalRevenueActYear;
    }

    public Integer getTotalRevenueBktYear() {
        return totalRevenueBktYear;
    }

    public void setTotalRevenueBktYear(Integer totalRevenueBktYear) {
        this.totalRevenueBktYear = totalRevenueBktYear;
    }

    public Integer getTotalRevenueEffectiveYear() {
        return totalRevenueEffectiveYear;
    }

    public void setTotalRevenueEffectiveYear(Integer totalRevenueEffectiveYear) {
        this.totalRevenueEffectiveYear = totalRevenueEffectiveYear;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Double getTotalAssetsMin() {
        return totalAssetsMin;
    }

    public void setTotalAssetsMin(Double totalAssetsMin) {
        this.totalAssetsMin = totalAssetsMin;
    }

    public Double getTotalAssetsMax() {
        return totalAssetsMax;
    }

    public void setTotalAssetsMax(Double totalAssetsMax) {
        this.totalAssetsMax = totalAssetsMax;
    }

    public Integer getTotalAssetsActYear() {
        return totalAssetsActYear;
    }

    public void setTotalAssetsActYear(Integer totalAssetsActYear) {
        this.totalAssetsActYear = totalAssetsActYear;
    }

    public Integer getTotalAssetsBktYear() {
        return totalAssetsBktYear;
    }

    public void setTotalAssetsBktYear(Integer totalAssetsBktYear) {
        this.totalAssetsBktYear = totalAssetsBktYear;
    }

    public Integer getTotalAssetsEffectiveYear() {
        return totalAssetsEffectiveYear;
    }

    public void setTotalAssetsEffectiveYear(Integer totalAssetsEffectiveYear) {
        this.totalAssetsEffectiveYear = totalAssetsEffectiveYear;
    }

    public Double getTotalRevenueMinCompanyCurrency() {
        return totalRevenueMinCompanyCurrency;
    }

    public void setTotalRevenueMinCompanyCurrency(Double totalRevenueMinCompanyCurrency) {
        this.totalRevenueMinCompanyCurrency = totalRevenueMinCompanyCurrency;
    }

    public Double getTotalRevenueMaxCompanyCurrency() {
        return totalRevenueMaxCompanyCurrency;
    }

    public void setTotalRevenueMaxCompanyCurrency(Double totalRevenueMaxCompanyCurrency) {
        this.totalRevenueMaxCompanyCurrency = totalRevenueMaxCompanyCurrency;
    }
}
