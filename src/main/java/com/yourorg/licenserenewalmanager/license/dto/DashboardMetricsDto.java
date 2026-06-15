package com.yourorg.licenserenewalmanager.license.dto;

import java.math.BigDecimal;

public class DashboardMetricsDto {

    private final int activeLicenses;
    private final int expiringSoon;
    private final BigDecimal annualSpend;
    private final BigDecimal utilizationPercent;
    private final BigDecimal activeGrowth; // NEW

    public DashboardMetricsDto(int activeLicenses,
                               int expiringSoon,
                               BigDecimal annualSpend,
                               BigDecimal utilizationPercent,
                               BigDecimal activeGrowth) {
        this.activeLicenses = activeLicenses;
        this.expiringSoon = expiringSoon;
        this.annualSpend = annualSpend;
        this.utilizationPercent = utilizationPercent;
        this.activeGrowth = activeGrowth;
    }

    public int getActiveLicenses() {
        return activeLicenses;
    }

    public int getExpiringSoon() {
        return expiringSoon;
    }

    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }

    public BigDecimal getUtilizationPercent() {
        return utilizationPercent;
    }

    public BigDecimal getActiveGrowth() {
        return activeGrowth;
    }
}