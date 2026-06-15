package com.yourorg.licenserenewalmanager.license.controller;

import com.yourorg.licenserenewalmanager.license.dto.DashboardMetricsDto;
import com.yourorg.licenserenewalmanager.license.dto.UpcomingRenewalViewDto;
import com.yourorg.licenserenewalmanager.license.dto.VendorSummaryDto;
import com.yourorg.licenserenewalmanager.license.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ui")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardMetricsDto metrics = dashboardService.getDashboardMetrics();
        List<UpcomingRenewalViewDto> upcoming = dashboardService.getUpcomingRenewals(60);
        List<VendorSummaryDto> vendorSummary = dashboardService.getVendorSummary();

        model.addAttribute("metrics", metrics);
        model.addAttribute("upcomingRenewals", upcoming);
        model.addAttribute("vendorSummary", vendorSummary);

        return "dashboard";
    }
}
