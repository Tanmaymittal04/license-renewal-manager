package com.yourorg.licenserenewalmanager.license.controller;

import com.yourorg.licenserenewalmanager.license.dto.DepartmentDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto;
import com.yourorg.licenserenewalmanager.license.dto.ProductDto;
import com.yourorg.licenserenewalmanager.license.enums.BillingCycle;
import com.yourorg.licenserenewalmanager.license.service.DepartmentService;
import com.yourorg.licenserenewalmanager.license.service.LicenseService;
import com.yourorg.licenserenewalmanager.license.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ui/licenses")
public class LicenseUiController {

    private final LicenseService licenseService;
    private final ProductService productService;
    private final DepartmentService departmentService;

    public LicenseUiController(LicenseService licenseService,
                               ProductService productService,
                               DepartmentService departmentService) {
        this.licenseService = licenseService;
        this.productService = productService;
        this.departmentService = departmentService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class,
                new org.springframework.beans.propertyeditors.CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.springframework.beans.propertyeditors.CustomNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(String.class, new org.springframework.beans.propertyeditors.StringTrimmerEditor(true));
    }

    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q,
                       Model model) {
        List<LicenseResponseDto> licenses = (q != null && !q.isBlank())
                ? licenseService.search(q)
                : licenseService.getAll();

        model.addAttribute("licenses", licenses);
        model.addAttribute("activeMenu", "licenses");
        model.addAttribute("q", q);

        return "license/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        LicenseRequestDto form = new LicenseRequestDto(
                null, null, null,
                null, null,
                null, null,
                null, Boolean.TRUE,
                null, "INR", null,
                null // ✅ NEW: vendorName
        );
        populateFormModel(model, form, "create", null);
        return "license/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") LicenseRequestDto form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            populateFormModel(model, form, "create", null);
            return "license/form";
        }

        sanitizeLicenseRequestDto(form);

        licenseService.create(form);
        redirectAttributes.addFlashAttribute("successMessage", "License created successfully.");
        return "redirect:/ui/licenses";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        LicenseResponseDto license = licenseService.getById(id);

        LicenseRequestDto form = new LicenseRequestDto(
                license.getProductId(),
                license.getDepartmentId(),
                license.getLicenseKeyOrContractId(),
                license.getSeatsPurchased(),
                license.getSeatsUsed(),
                license.getStartDate(),
                license.getExpiryDate(),
                license.getBillingCycle(),
                license.getAutoRenew(),
                license.getCostPerCycle(),
                license.getCurrency(),
                license.getTenure(),
                license.getVendorName() // ✅ NEW
        );

        populateFormModel(model, form, "edit", id);
        model.addAttribute("license", license);
        return "license/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") LicenseRequestDto form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            populateFormModel(model, form, "edit", id);
            return "license/form";
        }

        sanitizeLicenseRequestDto(form);

        licenseService.update(id, form);
        redirectAttributes.addFlashAttribute("successMessage", "License updated successfully.");
        return "redirect:/ui/licenses";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        licenseService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "License cancelled.");
        return "redirect:/ui/licenses";
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=licenses.xlsx");

        List<LicenseResponseDto> licenses = licenseService.getAll();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Licenses");

            String[] columns = {
                    "ID", "Product", "Vendor", "Department", "License Key",
                    "Seats Purchased", "Seats Used", "Start Date", "Expiry Date",
                    "Tenure (Months)", "Billing Cycle", "Auto Renew", "Status",
                    "Cost per Cycle", "Currency"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (LicenseResponseDto l : licenses) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(l.getId() != null ? l.getId() : 0);
                row.createCell(1).setCellValue(l.getProductName() != null ? l.getProductName() : "");
                row.createCell(2).setCellValue(l.getVendorName() != null ? l.getVendorName() : ""); // ✅ uses license vendor
                row.createCell(3).setCellValue(l.getDepartmentName() != null ? l.getDepartmentName() : "");
                row.createCell(4).setCellValue(l.getLicenseKeyOrContractId() != null ? l.getLicenseKeyOrContractId() : "");
                row.createCell(5).setCellValue(l.getSeatsPurchased() != null ? l.getSeatsPurchased() : 0);
                row.createCell(6).setCellValue(l.getSeatsUsed() != null ? l.getSeatsUsed() : 0);
                row.createCell(7).setCellValue(l.getStartDate() != null ? l.getStartDate().toString() : "");
                row.createCell(8).setCellValue(l.getExpiryDate() != null ? l.getExpiryDate().toString() : "");
                row.createCell(9).setCellValue(l.getTenure() != null ? l.getTenure() : 0);
                row.createCell(10).setCellValue(l.getBillingCycle() != null ? l.getBillingCycle().name() : "");
                row.createCell(11).setCellValue(l.getAutoRenew() != null && l.getAutoRenew() ? "Yes" : "No");
                row.createCell(12).setCellValue(l.getStatus() != null ? l.getStatus().name() : "");
                row.createCell(13).setCellValue(l.getCostPerCycle() != null ? l.getCostPerCycle().doubleValue() : 0.0);
                row.createCell(14).setCellValue(l.getCurrency() != null ? l.getCurrency() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }

    private void populateFormModel(Model model,
                                   LicenseRequestDto form,
                                   String mode,
                                   Long licenseId) {
        List<ProductDto> products = productService.getAll();
        List<DepartmentDto> departments = departmentService.getAll();
        List<BillingCycle> billingCycles = Arrays.asList(BillingCycle.values());

        model.addAttribute("form", form);
        model.addAttribute("mode", mode);
        model.addAttribute("licenseId", licenseId);
        model.addAttribute("products", products);
        model.addAttribute("departments", departments);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("activeMenu", "licenses");
    }

    private void sanitizeLicenseRequestDto(LicenseRequestDto form) {
        if (form.getLicenseKeyOrContractId() != null && form.getLicenseKeyOrContractId().isBlank()) {
            form.setLicenseKeyOrContractId(null);
        }
        if (form.getCurrency() != null) {
            form.setCurrency(form.getCurrency().trim());
        }
        // ✅ NEW: trim vendor name
        if (form.getVendorName() != null) {
            form.setVendorName(form.getVendorName().trim());
            if (form.getVendorName().isEmpty()) {
                form.setVendorName(null);
            }
        }
    }
}