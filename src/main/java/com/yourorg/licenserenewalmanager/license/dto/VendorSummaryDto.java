package com.yourorg.licenserenewalmanager.license.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class VendorSummaryDto {

    private final String name;
    private final int productCount;
    private final BigDecimal spend;
    private final String currency;
    private final BigDecimal sharePercent;

    public VendorSummaryDto(String name,
                            int productCount,
                            BigDecimal spend,
                            String currency,
                            BigDecimal sharePercent) {
        this.name = name;
        this.productCount = productCount;
        this.spend = spend;
        this.currency = currency;
        this.sharePercent = sharePercent;
    }

}