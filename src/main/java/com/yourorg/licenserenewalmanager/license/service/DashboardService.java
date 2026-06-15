package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.license.dto.DashboardMetricsDto;
import com.yourorg.licenserenewalmanager.license.dto.UpcomingRenewalViewDto;
import com.yourorg.licenserenewalmanager.license.dto.VendorSummaryDto;

import java.util.List;

public interface DashboardService {

    DashboardMetricsDto getDashboardMetrics();

    List<UpcomingRenewalViewDto> getUpcomingRenewals(int daysAhead);

    List<VendorSummaryDto> getVendorSummary();
}