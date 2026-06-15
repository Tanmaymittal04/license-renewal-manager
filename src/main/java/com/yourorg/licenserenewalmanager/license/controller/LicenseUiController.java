package com.yourorg.licenserenewalmanager.license.controller;

import com.yourorg.licenserenewalmanager.license.dto.DepartmentDto;
import com.yourorg.licenserenewalmanager.license.service.DepartmentService;
import com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto;
import com.yourorg.licenserenewalmanager.license.enums.BillingCycle;
import com.yourorg.licenserenewalmanager.license.service.LicenseService;
import com.yourorg.licenserenewalmanager.license.dto.ProductDto;
import com.yourorg.licenserenewalmanager.license.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q,
                       Model model) {
        // For now ignore q or implement search in service.
        List<LicenseResponseDto> licenses = licenseService.getAll();

        model.addAttribute("licenses", licenses);
        model.addAttribute("activeMenu", "licenses");
        model.addAttribute("q", q);

        return "license/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        LicenseRequestDto form = new LicenseRequestDto(
                null, null, null,
                0, 0,
                null, null,
                null, Boolean.TRUE,
                null, "INR"
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
                license.getCurrency()
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
}